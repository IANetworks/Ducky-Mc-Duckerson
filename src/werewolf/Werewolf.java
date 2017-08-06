package werewolf;

import werewolf.data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;

public class Werewolf {
	private Map<Long, WWGame> gameList = new HashMap<Long, WWGame>();
	private Map<Long, Theme> themeList = new HashMap<Long, Theme>();
	Map<Long, Boolean> isRuleSentList = new HashMap<Long, Boolean>();
	Map<Long, PlayerList> gamesPlayerLists = new HashMap<Long, PlayerList>();
	Map<Long, String> gameChannelName = new HashMap<Long, String>();//Game channel - where all games will play or started from
	Map<Long, TextChannel> gameChannelList = new HashMap<Long, TextChannel>(); //Game channel - we'll hold the actual Channel object to make it easier to refer to
	String villChannelSuffix = "-town"; //Town channel, bot will attempt to create a town channel if one does not exist.
	String wolfChannelSuffix = "-wolf"; //Game channel, bot will attempt create a wolf channel if one does not exist.
	Map<Long, TextChannel> villChannelList = new HashMap<Long, TextChannel>(); //Hold all the server's vill channel. Make it easier to refer to as needed.
	Map<Long, TextChannel> wolfChannelList = new HashMap<Long, TextChannel>(); //Hold all the server's wolf channel. Make it easier to refer to as needed.
	
	DatabaseManager dbMan = null;
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
	
	private void setGameState(Long guildID, GameState gameState)
	{
		if(gameList.containsKey(guildID))
		{
			gameList.get(guildID).setGameState(gameState);
		} else {
			WWGame newWWGame = new WWGame();
			newWWGame.setGameState(gameState);
			
			gameList.put(guildID, newWWGame);
		}
	}
	
	public Werewolf(DatabaseManager dbMan) {
		this.dbMan = dbMan;
		
	}
	
	public GameState getWerewolfGameState(Long guildID)
	{
		if(gameList.containsKey(guildID))
		{
			return gameList.get(guildID).getGameState();
		}
		
		return null;
	}
	
	public boolean getIsRulesSent(Long guildID)
	{
		if(isRuleSentList.containsKey(guildID))
		{
			return isRuleSentList.containsKey(guildID);
		} else {
			return false;
		}
	}
	public boolean startTheme(Long guildID, Integer themeID) {
		if(dbMan.sqlThemeCheck(themeID))
		{
			themeList.put(guildID,dbMan.sqlGetTheme(themeID));
			return true;
		}
		
		return false;
	}

	public void sendRuleToChannel(MessageChannel channel, Long guildID) {
		
		channel.sendMessage("Rules rules rules.. This still need to be implemented");
		scheduler.schedule(new RulesRefresh(guildID), 10, TimeUnit.MINUTES);
		
	}
	
	public void startGame(Guild guild, Member author, MessageChannel channel) {
		//get GuildID
		Long guildID = guild.getIdLong();
		
		//Check if town/wolf channel eixist and attempt to create town and wolf channels if not
		if(!setupGameChannels(guild, author, channel))
		{
			return; //We weren't able to setup the game channels for some reason. Don't start the game
		}
		
		//See if we have permission to adjust permissions on channel
		//TODO
				
		//Set gameStatus to GAMESTART
		setGameState(guildID, GameState.GAMESTART);
		
		//reset roundNo
		resetRounds(guildID);
		
		//reset player list
		resetPlayerList(guildID);
		
		//Send theme start game message
		Integer joinTime = getJoinTime(guildID);
		
		channel.sendMessage(getTheme("START_GAME", MessType.GAME, guildID, author, joinTime)).queue();
		getGameChannel(guildID).sendMessage(getTheme("START_GAME_NOTICE", MessType.GAME, guildID, author)).queue();
		
		//Setup town channel and wolf channel permissions, mute everyone, block everyone from wolf channel
		//TODO
		
		//Add the person who started the game into the game list.
		joinGame(guildID, channel, author);
		
		//Set Timers
		scheduler.schedule(new TenSecWarning(guildID), joinTime - 10, TimeUnit.SECONDS);
		scheduler.schedule(new WereTask(guildID), joinTime, TimeUnit.SECONDS);
		
	}
	
	private MessageChannel getGameChannel(Long guildID) {
		return gameChannelList.get(guildID); //setupGameChannels should have this setup for us. Let it error if something is wrong by this point. >_> 
	}

	private Boolean setupGameChannels(Guild guild, Member author, MessageChannel channel) {
		Long guildID = guild.getIdLong();
		Member selfMember = guild.getSelfMember();
		List<String> setupChannels = new ArrayList<String>();
		
		//first check to make sure we're not already holding the channel object for this server		
		if(gameChannelList.containsKey(guildID) && villChannelList.containsKey(guildID) && wolfChannelList.containsKey(guildID)){
			return true; //We are already setup 
		}
		
		//ok, let see what we're missing.
		List<TextChannel> channelList;
		String gameGuildChannelName = getGameChannelName(guildID);
		if(gameGuildChannelName == null)
		{
			//alert users that game channel is not setup
			channel.sendMessage("This game requires an intial game channel to be named and the bot to have Manage Channel role or the channels setup ahead of time");
			return false;
		}
		
		if(!gameChannelList.containsKey(guildID))
		{
			//let attempt to fetch the channel object for gameChannel
			
			//find the channel
			channelList = guild.getTextChannelsByName(gameGuildChannelName, true);
			if(channelList.isEmpty())
			{
				setupChannels.add(gameGuildChannelName);
			} else {
				//grab first on the list
				gameChannelList.put(guildID, channelList.get(0));
			}
		}
		
		if(!villChannelList.containsKey(guildID))
		{
			channelList = guild.getTextChannelsByName(gameGuildChannelName + villChannelSuffix, true);
			if(channelList.isEmpty())
			{
				setupChannels.add(gameGuildChannelName + villChannelSuffix);
			} else {
				//grab first on the list
				villChannelList.put(guildID, channelList.get(0));
			}
		}
		
		if(!wolfChannelList.containsKey(guildID))
		{
			channelList = guild.getTextChannelsByName(gameGuildChannelName + wolfChannelSuffix, true);
			if(channelList.isEmpty())
			{
				setupChannels.add(gameGuildChannelName + wolfChannelSuffix);
			} else {
				//grab first on the list
				wolfChannelList.put(guildID, channelList.get(0));
			}
		}
		
		
		if(setupChannels.isEmpty()) {
			return true;
		} else {
			//attempt to create the channel, check permission
			if(selfMember.hasPermission(Permission.MANAGE_CHANNEL)) {
				GuildController gController = guild.getController();
				for(String name : setupChannels)
				{
					ChannelAction thisChannel = gController.createTextChannel(name);
				
					thisChannel.setTopic("game channels created by Ducky");
					thisChannel.queue(success -> channelCreated(name, guild, author, channel));
				}
				
				return false; //return we have to wait for the channels to be created 
				
			} else {
				String format = null;
				for(String name : setupChannels)
				{
					if(format == null)
					{
						format = name;
					} else {
						format = format + ", " + name;
					}
				}
				
				channel.sendMessage("I couldn't not find " + format + " and I do not have manage channel permission to create it. Please create these channels");
				return false;
			}
		}
		
	}

	private void channelCreated(String name, Guild guild, Member author, MessageChannel channel) {
		Long guildID = guild.getIdLong();
		//Add newly created channel to the list and check to see if we have all the channels ready yet
		
		List<TextChannel> channelList;
		String suffixCheck;
		int whatChannel = -1;
		//check to make sure channel name is better than 5(most likely contain -town/-wolf, smaller is more likely to be game channel)
		if(name.length() > 5)
		{
			int lastFive = name.length() - 5;
			suffixCheck = name.substring(lastFive);
			if(suffixCheck.equals("-town"))
			{
				whatChannel = 2;
			} else if(suffixCheck.equals("-wolf"))
			{
				whatChannel = 3;
			} else {
				whatChannel = 1;
			}
		} else {
			whatChannel = 1;
		}
		
		switch(whatChannel)
		{
			case 1: //game channel
				if(!gameChannelList.containsKey(guildID))
				{
					//find the channel
					channelList = guild.getTextChannelsByName(name, true);
					if(!channelList.isEmpty())
					{
						//grab first on the list
						gameChannelList.put(guildID, channelList.get(0));
					}
				}
				break;
			case 2: //vill channel
				if(!villChannelList.containsKey(guildID))
				{
					channelList = guild.getTextChannelsByName(name, true);
					if(!channelList.isEmpty())
					{
						//grab first on the list
						villChannelList.put(guildID, channelList.get(0));
					}
				}
				break;
			case 3: //wolf channel
				if(!wolfChannelList.containsKey(guildID))
				{
					channelList = guild.getTextChannelsByName(name, true);
					if(!channelList.isEmpty())
					{
						//grab first on the list
						wolfChannelList.put(guildID, channelList.get(0));
					}
				}
				break;
			default:  //dunno this shit
				break;
		}
		
		//do a check
		if(gameChannelList.containsKey(guildID) && villChannelList.containsKey(guildID) && wolfChannelList.containsKey(guildID))
		{
			//ok attempt to start game again
			startGame(guild, author, channel);
		}
	}

	private String getGameChannelName(Long guildID) {
		return gameChannelName.get(guildID);
	}

	public void joinGame(Long guildID, MessageChannel channel, Member player)
	{
		//check game status
		if(getWerewolfGameState(guildID) != GameState.GAMESTART)
		{
			return;//if the game is anything but gamestate, then we don't start
		}
		
		//set permission to allow user to talk in town channel
		if(!hasPlayer(guildID, player))
		{
			//add player to the list
			addPlayer(guildID, player);
		}
		//set channel permission to allow user to talk in channel, if we have permission role
		//TODO
		//alert user that they are in channel and have joined game
		//TODO
	}
	
	private void addPlayer(Long guildID, Member player) {
		gamesPlayerLists.get(guildID).addPlayer(player);
		
	}

	private boolean hasPlayer(Long guildID, Member player) {
		if(gamesPlayerLists.containsKey(guildID))
		{
			return gamesPlayerLists.get(guildID).hasPlayer(player.getUser().getIdLong());
		} else {
			return false;
		}
	}

	private Integer getJoinTime(Long guildID) {
		if(gameList.containsKey(guildID))
		{
			return gameList.get(guildID).getJoinTime();
		} else {
			return null;
		}
			
	}

	private void resetPlayerList(Long guildID) {
		if(gamesPlayerLists.containsKey(guildID))
		{
			gamesPlayerLists.get(guildID).resetList();
		}
	}

	private void resetRounds(Long guildID) {
		if(gameList.containsKey(guildID))
		{
			gameList.get(guildID).setRoundNo(0);
		} else {
			WWGame newWWGame = new WWGame();
			newWWGame.setRoundNo(0);
			gameList.put(guildID, newWWGame);
		}
		
	}
	
	protected Message getTheme(String theme_id, MessType type, Long guildID)
	{
		return getTheme(theme_id, type, guildID, null, 0, null);
	}
	
	protected Message getTheme(String theme_id, MessType type, Long guildID, int number)
	{
		return getTheme(theme_id, type, guildID, null, number, null);
	}
	
	protected Message getTheme(String theme_id, MessType type, Long guildID, Member player1)
	{
		return getTheme(theme_id, type, guildID, player1, 0, null);
	}

	protected Message getTheme(String theme_id, MessType type, Long guildID, Member player1, int time)
	{
		return getTheme(theme_id, type, guildID, player1, time, null);
	}
	
	protected Message getTheme(String theme_id, MessType type, Long guildID, Member player1, Member player2)
	{
		return getTheme(theme_id, type, guildID, player1, 0, player2);
	}
	
	protected Message getTheme(String theme_id, MessType type, Long guildID, Member player1, Integer number, Member player2){
		
//		String prefix = "";
//		String surfix = "";
//		String playerPrefix = "";
//		String playerSurfix = "";
		MessageBuilder msgBuilder = new MessageBuilder();
		
		String themeText = getThemeText(guildID, theme_id);
		
		//TODO Think about allow themes to add random avatars for Narration, Game, and Notice (AVATAR_NOTICE, AVATAR_NARR, and AVATAR_GAME)
		//(Notice is left over from IRC, but that's doesn't mean we can't use it in a different way 

		
		//Set up formatting
		switch(type)
		{
			case NOTICE:
				//No formatting
				break;
			case NARRATION:
				//We Want this normal, with paraments bolded
//				prefix = txtColour(Colours.DARK_BLUE, Colours.WHITE);
//				surfix = txtColour(Colours.DARK_BLUE, Colours.WHITE);
//				playerPrefix = Colors.BOLD + txtColour(Colours.PURPLE, Colours.WHITE);
//				playerSurfix = Colors.BOLD + txtColour(Colours.PURPLE, Colours.WHITE);
				
				break;
			case GAME:
				//Hmm
//				prefix = txtColour(Colours.BROWN, Colours.WHITE);
//				surfix = txtColour(Colours.BROWN, Colours.WHITE);
//				playerPrefix = Colors.BOLD + Colors.UNDERLINE + txtColour(Colours.PURPLE, Colours.WHITE);;
//				playerSurfix = Colors.BOLD + Colors.UNDERLINE + txtColour(Colours.PURPLE, Colours.WHITE);;
				break;
				
			case CONTROL:
				//Not sure
				break;
				
			default:
				break;
		}
		
//		themeText = themeText.replaceAll("WOLFPURAL", "WOLFPLURAL"); //Fix typo
//		themeText = themeText.replaceAll("PLAYER1", surfix + playerPrefix + player1 + playerSurfix + prefix);
//		themeText = themeText.replaceAll("PLAYER2", surfix + playerPrefix + player2 + playerSurfix + prefix);
//		themeText = themeText.replaceAll("BOTNAME", surfix + playerPrefix + this.getNick() + playerSurfix + prefix);
//		themeText = themeText.replaceAll("ROLE", surfix + playerPrefix + getRoleString(playerList.getPlayerRole(player1)) + playerSurfix + prefix);
//		themeText = themeText.replaceAll("NUMBER", surfix + playerPrefix + number.toString() + playerSurfix + prefix);
//		
//		if (manyWolves())
//		{
//			themeText = themeText.replaceAll("WOLFPLURAL", surfix + playerPrefix + themeObj.getText("ONEWOLF") + playerSurfix + prefix);
//		} else 
//		{
//			themeText = themeText.replaceAll("WOLFPLURAL", surfix + playerPrefix + themeObj.getText("MANY_WOLVES") + playerSurfix + prefix);
//		}
		
		//Return Theme Text
//		return prefix + themeText + surfix;
		return msgBuilder.build();
	}

	private String getThemeText(Long guildID, String theme_id) {
		if(themeList.containsKey(guildID))
		{
			return themeList.get(guildID).getText(theme_id);
		} else {
			return null;
		}
	}

	private class RulesRefresh implements Runnable
	{
		Long guildID;
		public RulesRefresh(Long guildID) {
			this.guildID = guildID;
		}
		
		@Override
		public void run() {
			isRuleSentList.put(guildID, false);
		}
	}
	
	private class WereTask implements Runnable
	{
		private Long guildID;
		public WereTask(Long guildID) {
			this.guildID = guildID;
		}
		
		@Override
		public void run() {
			if(gameList.containsKey(guildID))
			{
				switch(gameList.get(guildID).getGameState()) {
					case IDLE:
						break;
					case GAMESTART:
						break;
					case DAYTIME:
						break;
					case VOTETIME:
						break;
					case NIGHTTIME:
						break;
					default: //Incorrect gameStatus
						break;
				}
				
			} else {
				//TODO Do something about an empty gameStatus
			}
		}
	}
	
		
	private class TenSecWarning implements Runnable
	{
		private Long guildID;
		public TenSecWarning(Long guildID) {
			this.guildID = guildID;
		}
		
		@Override
		public void run() 
		{
			if(gameList.containsKey(guildID))
			{
				switch(gameList.get(guildID).getGameState()) {
					case IDLE:
						break;
					case GAMESTART:
						break;
					case DAYTIME:
						break;
					case VOTETIME:
						break;
					case NIGHTTIME:
						break;
					default: //Incorrect gameStatus
						break;
				}
				
			} else {
				//TODO Do something about an empty gameStatus
			}
			
		}
		
	}


	
}