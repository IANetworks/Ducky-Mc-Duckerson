package bot;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.bot.entities.ApplicationInfo;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RestAction;


public class EventListener extends ListenerAdapter {
	DatabaseManager dbMan;
	String botAdmin;
	User botOwner; //We'll hold the botOwner so we don't have to keep asking Discord for this
	
	//TODO STERILIZE USER INPUT! LIKE I WILL BITE FACES AND FLIP TABLES IF THIS ISN'T DONE
	//TODO Lookup, how to defer down the constructors
	public EventListener(DatabaseManager dbMan) {
		selfStart(dbMan, null);
	}
	
	public EventListener(DatabaseManager dbMan, String botAdmin) {
		selfStart(dbMan, botAdmin);
	}
	
	private void selfStart(DatabaseManager dbMan, String botAdmin)
	{
		this.dbMan = dbMan;
		this.botAdmin = botAdmin;
		
		//Here we fetch infomation from database as needed
	}

	/**
     * NOTE THE @Override!
     * This method is actually overriding a method in the ListenerAdapter class! We place an @Override annotation
     *  right before any method that is overriding another to guarantee to ourselves that it is actually overriding
     *  a method from a super class properly. You should do this every time you override a method!
     *
     * As stated above, this method is overriding a hook method in the
     * {@link net.dv8tion.jda.core.hooks.ListenerAdapter ListenerAdapter} class. It has convience methods for all JDA events!
     * Consider looking through the events it offers if you plan to use the ListenerAdapter.
     *
     * In this example, when a message is received it is printed to the console.
     *
     * @param event
     *          An event containing information about a {@link net.dv8tion.jda.core.entities.Message Message} that was
     *          sent in a channel.
     */
	@Override
	public void onReady(ReadyEvent event)
	{
		JDA jda = event.getJDA();
		RestAction<ApplicationInfo> ra = jda.asBot().getApplicationInfo();
		//fetch botOwner;
		Consumer<ApplicationInfo> callback = (info) -> botOwner = info.getOwner();
		ra.queue(callback);
	}
	
	//We Want To See All Users Joinning the server(Called Guilds by Discord, why, I dunno)
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event)
	{
		Member member = event.getMember();
        Guild guild = event.getGuild();
        //TODO Update this to be adjustable for now, we'll use event-log
        
        List<TextChannel> ltc = guild.getTextChannelsByName("event-log", true);
        if (ltc.isEmpty())
        {
        	System.out.printf("Found no text channels with event-log");
        	return;
        }
        
        for(TextChannel tc : ltc)
        {
        	tc.sendMessage("Member " + member.getAsMention() + " has joined " + guild.getName() + ".").queue();
   		}
	}
	
	//Users leaving server
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event)
	{
		Member member = event.getMember();
        Guild guild = event.getGuild();
        //TODO Update this to be adjustable for now, we'll use event-log
        List<TextChannel> ltc=  guild.getTextChannelsByName("event-log", true);

        if (ltc.isEmpty())
        {
        	System.out.printf("Found no text channels with event-log");
        	return;
        }
        
        for(TextChannel tc : ltc)
        {
        	tc.sendMessage("Member `" + member.getEffectiveName() + "` has left " + guild.getName() + ".").queue();
   		}
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		Member author = event.getMember(); //User who sent message, member of guild
		MessageChannel channel = event.getChannel();
		Message message = event.getMessage(); //Message recieved
		String msg = message.getContent(); // String readable content of message
		Guild guild = event.getGuild(); //Get info about the server this message is recieved on
		Long guildID = guild.getIdLong(); //guild unique id
		boolean guildOwner = author.isOwner(); // could also do guild.getOwner().isOwner();
		
		String guildPrefix = "!"; //Command prefix, Default to ! <- break this out?
		
		if(dbMan.getPrefix(guildID) != null)
		{
			guildPrefix = dbMan.getPrefix(guildID);
		}
		
		//Check Prefix
		
		//TODO break out to a function
		
		String msgPrefix = msg.substring(0, guildPrefix.length());
		
		String msgCmd = msg.substring(guildPrefix.length()).toLowerCase();
		if(msgPrefix.equals(guildPrefix)) {
			String cmd = "setprefix";
			Integer cmdCharCount = guildPrefix.length() + cmd.length();
			
			if(msgCmd.startsWith(cmd))
			{
	
				//Check to see if we're either botAdminOwner or guild Owner
				//TODO Permissions check
				if(isBotAdminOwner(author.getUser()) || guildOwner)
				{
					//count the chars
					String parameters = msg.substring(cmdCharCount);
					//if we don't have any parameters, we're resetting to default
					if(parameters.isEmpty())
					{
						if(!guildPrefix.equals("!")) {
							//check to make sure we're actually changing a default
							Consumer<Message> callback = (response) -> {
								try {
									dbMan.setPrefix("!", guildID);
								} catch (SQLException e) {
									e.printStackTrace();
									channel.sendMessage("I had an error, am I helpful creator?").queue();
								}
							};
					    	channel.sendMessage("Resetting prefix to default").queue(callback); //Should I think about breaking this out to make localizion doable? 
					    	//I don't really expect this bot to get popular but this might make the bot popular thing along non-english servers..
						}
					} else {
						parameters = parameters.trim();
						if(parameters.length() > 3) {
							channel.sendMessage("I cannot set a prefix of 4 or more, I count " + String.valueOf(parameters.length())).queue();;
						} else {
							final String pm = parameters;
							Consumer<Message> callback = (response) -> {
								try {
									dbMan.setPrefix(pm, guildID);
								} catch (SQLException e) {
									e.printStackTrace();
									channel.sendMessage("I had an error setting Prefix, am I helpful here too creator?").queue();
								}
							};
					    	channel.sendMessage("Setting prefix to " + parameters).queue(callback); //Should I think about breaking this out to make localizion doable?
						}
					}
					//User has the highest level of permission
					
				}
				
			} else { // check for next command
				
			}
		}
		
		
		
	}
	
	
	
	//
	
	//All Messages recieved, from Private channels (DM), Public Channels(server/guild), Groups (Client only, we're using bot account so we can't do groups)
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        //These are provided with every event in JDA
        //JDA jda = event.getJDA();                       //JDA, the core of the api.
        
        
        //long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        
        String msg = message.getContent();              //This returns a human readable version of the Message. Similar to
                                                        // what you would see in the client.
        boolean isBotAdminOwner = isBotAdminOwner(author);
        
        if (msg.equals("!!shutdown"))
        {
        	//Make sure we have permission
        	if(isBotAdminOwner) {
        		author.openPrivateChannel().queue((channel) -> sendChannelMessageAndShutdown(channel, "Bye bye, I'm closing down"));
        	}
        }    
    }

	private boolean isBotAdminOwner(User author) {
		String userwithDiscriminator = author.getName() + "#" + author.getDiscriminator(); //the libarey don't include a readily used readable username with descriminator
		return (userwithDiscriminator.equals(botAdmin) && botAdmin != null) || (botOwner.getIdLong() == author.getIdLong());
	}
	
	    
    public void sendChannelMessageAndShutdown(MessageChannel channel, String message)
    {
    	Consumer<Message> callback = (response) -> response.getJDA().shutdown();
    	channel.sendMessage(message).queue(callback);
    }
    
}
