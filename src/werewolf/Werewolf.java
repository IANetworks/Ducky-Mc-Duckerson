package werewolf;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import werewolf.data.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;
import werewolf.data.Role;

/*
    * priority, more = higher,
    x - created command file
    xx - coded
    xxrule - all
    xxlisttheme - all
    xxthemeinfo - all
    xx**start - idle only
    xx**join - gamestart only
    xx**vote - from townchannel(Not idle/gamestart)
    xx**kill - from wolfchannel (Not idle/gamestart)
    xx*alive - during game only (Not idle/gamestart)
    xx*stopww - all times but idle
    x*kickplayer - all times but idle - for removing bad players from game
    xx**see - privatemessage during nighttime
    xx*role - privatemessage/ingame during game only
    xx*leave - all times but idle (during gamestart, remove player, during gametime, mark as fled)
 */

/**
 * The type Werewolf.
 */
public class Werewolf {
    private Map<Long, WWGame> gameList = new HashMap<Long, WWGame>();
    private Map<Long, Theme> themeList = new HashMap<Long, Theme>();
    private Map<Long, Boolean> isRuleSentList = new HashMap<Long, Boolean>();
    private Map<Long, PlayerList> gamesPlayerLists = new HashMap<Long, PlayerList>();
    private Map<Long, TextChannel> gameChannelList = new HashMap<Long, TextChannel>(); //Game channel - we'll hold the actual Channel object to make it easier to refer to
    private String villChannelSuffix = "-town"; //Town channel, bot will attempt to create a town channel if one does not exist.
    private String wolfChannelSuffix = "-wolf"; //Game channel, bot will attempt create a wolf channel if one does not exist.
    private Map<Long, TextChannel> townChannelList = new HashMap<Long, TextChannel>(); //Hold all the server's vill channel. Make it easier to refer to as needed.
    private Map<Long, TextChannel> wolfChannelList = new HashMap<Long, TextChannel>(); //Hold all the server's wolf channel. Make it easier to refer to as needed.
    private Map<Long, Map<Long, ScheduledFuture<?>>> timerThreads = new HashMap<Long, Map<Long, ScheduledFuture<?>>>(); //holds a count for timer tasks
    private Long timerCount = 0L;
    private User thisBot;

    /**
     * The Db man.
     */
    DatabaseManager dbMan = null;


    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    /**
     * Gets town channel.
     *
     * @param guildID the guild id
     * @return the town channel
     */
    public MessageChannel getTownChannel(Long guildID) {
        return townChannelList.get(guildID);
    }

    /**
     * Gets wolf channel.
     *
     * @param guildID the guild id
     * @return the wolf channel
     */
    public MessageChannel getWolfChannel(Long guildID) {
        return wolfChannelList.get(guildID);
    }

    private void addTimer(Long guildID, ScheduledFuture<?> schTasks) {
        if (timerThreads.containsKey(guildID)) {
            timerThreads.get(guildID).put(timerCount, schTasks);
        } else {
            Map<Long, ScheduledFuture<?>> gameTimers = new HashMap<Long, ScheduledFuture<?>>();
            gameTimers.put(timerCount, schTasks);
            timerThreads.put(guildID, gameTimers);
        }
        timerCount++;
    }

    private void removeTimer(Long guildID, Long taskID) {
        if (timerThreads.containsKey(guildID)) {
            if (timerThreads.get(guildID).containsKey(taskID)) {
                timerThreads.get(guildID).remove(taskID);
            }
        }
    }

    private void setGameState(Long guildID, GameState gameState) {
        if (gameList.containsKey(guildID)) {
            gameList.get(guildID).setGameState(gameState);
        } else {
            WWGame newWWGame = new WWGame();
            newWWGame.setGameState(gameState);

            gameList.put(guildID, newWWGame);
        }
    }

    /**
     * Instantiates a new Werewolf.
     *
     * @param dbMan   the db man
     * @param thisBot the this bot
     */
    public Werewolf(DatabaseManager dbMan, User thisBot) {
        this.dbMan = dbMan;
        this.thisBot = thisBot;
    }

    /**
     * Gets werewolf game state.
     *
     * @param guildID the guild id
     * @return the werewolf game state
     */
    public GameState getWerewolfGameState(Long guildID) {
        if (gameList.containsKey(guildID)) {
            return gameList.get(guildID).getGameState();
        }

        return GameState.IDLE;
    }

    /**
     * Gets is rules sent.
     *
     * @param guildID the guild id
     * @return the is rules sent
     */
    public boolean getIsRulesSent(Long guildID) {
        if (isRuleSentList.containsKey(guildID)) {
            return isRuleSentList.containsKey(guildID);
        } else {
            return false;
        }
    }

    /**
     * Start theme boolean.
     *
     * @param guildID the guild id
     * @param themeID the theme id
     * @return the boolean
     */
    public boolean startTheme(Long guildID, Integer themeID) {
        if (dbMan.sqlThemeCheck(themeID)) {
            themeList.put(guildID, dbMan.sqlGetTheme(themeID));
            return true;
        }

        return false;
    }

    /**
     * Clear theme.
     *
     * @param guildID the guild id
     */
    public void clearTheme(Long guildID) {
        themeList.get(guildID).clear();
    }

    /**
     * Send rule to channel.
     *
     * @param channel the channel
     * @param guildID the guild id
     */
    public void sendRuleToChannel(MessageChannel channel, Long guildID) {

        channel.sendMessage("Rules rules rules.. This still need to be implemented");
        scheduler.schedule(new RulesRefresh(guildID), 10, TimeUnit.MINUTES);
    }

    /**
     * Start game.
     *
     * @param guild   the guild
     * @param author  the author
     * @param channel the channel
     */
    public void startGame(Guild guild, Member author, MessageChannel channel) {
        //get GuildID
        Long guildID = guild.getIdLong();
        //

        //Check if town/wolf channel eixist and attempt to create town and wolf channels if not
        if (!setupGameChannels(guild, author, channel)) {
            return; //We weren't able to setup the game channels for some reason. Don't start the game
        }

        //TODO See if we have permission to adjust permissions on channel

        //Set gameStatus to GAMESTART
        setGameState(guildID, GameState.GAMESTART);

        //reset roundNo
        resetRounds(guildID);

        //reset player list
        resetPlayerList(guildID);

        //Send theme start game message
        Integer joinTime = getJoinTime(guildID);

        channel.sendMessage(getTheme("START_GAME", MessType.GAME, guildID, author, joinTime).build()).queue();
        getTownChannel(guildID).sendMessage(getTheme("START_GAME_NOTICE", MessType.GAME, guildID, author).build()).queue();

        //TODO Setup town channel and wolf channel permissions, mute everyone, block everyone from wolf channel


        //Add the person who started the game into the game list.
        joinGame(guildID, author);

        //startTimers
        addTimer(guildID, scheduler.schedule(new TenSecWarning(guildID, timerCount), joinTime - 10, TimeUnit.SECONDS));
        addTimer(guildID, scheduler.schedule(new WereTask(guildID, timerCount), joinTime, TimeUnit.SECONDS));

    }

    private MessageChannel getGameChannel(Long guildID) {
        return gameChannelList.get(guildID); //setupGameChannels should have this setup for us. Let it error if something is wrong by this point. >_>
    }

    private Boolean setupGameChannels(Guild guild, Member author, MessageChannel channel) {
        Long guildID = guild.getIdLong();
        Member selfMember = guild.getSelfMember();
        List<String> setupChannels = new ArrayList<String>();

        //first check to make sure we're not already holding the channel object for this server
        if (gameChannelList.containsKey(guildID) && townChannelList.containsKey(guildID) && wolfChannelList.containsKey(guildID)) {
            return true; //We are already setup
        }

        //ok, let see what we're missing.
        List<TextChannel> channelList;
        String gameGuildChannelName = getGameChannelName(guildID);
        if (gameGuildChannelName == null) {
            //alert users that game channel is not setup
            channel.sendMessage("This game requires an intial game channel to be named and the bot to have Manage Channel role or the channels setup ahead of time").queue();
            return false;
        }

        if (!gameChannelList.containsKey(guildID)) {
            //let attempt to fetch the channel object for gameChannel

            //find the channel
            channelList = guild.getTextChannelsByName(gameGuildChannelName, true);
            if (channelList.isEmpty()) {
                setupChannels.add(gameGuildChannelName);
            } else {
                //grab first on the list
                gameChannelList.put(guildID, channelList.get(0));
            }
        }

        if (!townChannelList.containsKey(guildID)) {
            channelList = guild.getTextChannelsByName(gameGuildChannelName + villChannelSuffix, true);
            if (channelList.isEmpty()) {
                setupChannels.add(gameGuildChannelName + villChannelSuffix);
            } else {
                //grab first on the list
                townChannelList.put(guildID, channelList.get(0));
            }
        }

        if (!wolfChannelList.containsKey(guildID)) {
            channelList = guild.getTextChannelsByName(gameGuildChannelName + wolfChannelSuffix, true);
            if (channelList.isEmpty()) {
                setupChannels.add(gameGuildChannelName + wolfChannelSuffix);
            } else {
                //grab first on the list
                wolfChannelList.put(guildID, channelList.get(0));
            }
        }


        if (setupChannels.isEmpty()) {
            return true;
        } else {
            //attempt to create the channel, check permission
            if (selfMember.hasPermission(Permission.MANAGE_CHANNEL)) {
                GuildController gController = guild.getController();
                for (String name : setupChannels) {
                    ChannelAction thisChannel = gController.createTextChannel(name);

                    thisChannel.setTopic("game channels created by Ducky");
                    thisChannel.queue(success -> channelCreated(name, guild, author, channel));
                }
                channel.sendMessage("Attempting to create channels").queue();
                return false; //return we have to wait for the channels to be created

            } else {
                String format = null;
                for (String name : setupChannels) {
                    if (format == null) {
                        format = name;
                    } else {
                        format = format + ", " + name;
                    }
                }

                channel.sendMessage("I couldn't not find " + format + " and I do not have manage channel permission to create it. Please create these channels").queue();
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
        if (name.length() > 5) {
            int lastFive = name.length() - 5;
            suffixCheck = name.substring(lastFive);
            if (suffixCheck.equals("-town")) {
                whatChannel = 2;
            } else if (suffixCheck.equals("-wolf")) {
                whatChannel = 3;
            } else {
                whatChannel = 1;
            }
        } else {
            whatChannel = 1;
        }

        switch (whatChannel) {
            case 1: //game channel
                if (!gameChannelList.containsKey(guildID)) {
                    //find the channel
                    channelList = guild.getTextChannelsByName(name, true);
                    if (!channelList.isEmpty()) {
                        //grab first on the list
                        gameChannelList.put(guildID, channelList.get(0));
                    }
                }
                break;
            case 2: //vill channel
                if (!townChannelList.containsKey(guildID)) {
                    channelList = guild.getTextChannelsByName(name, true);
                    if (!channelList.isEmpty()) {
                        //grab first on the list
                        townChannelList.put(guildID, channelList.get(0));
                    }
                }
                break;
            case 3: //wolf channel
                if (!wolfChannelList.containsKey(guildID)) {
                    channelList = guild.getTextChannelsByName(name, true);
                    if (!channelList.isEmpty()) {
                        //grab first on the list
                        wolfChannelList.put(guildID, channelList.get(0));
                    }
                }
                break;
            default:  //dunno this shit
                break;
        }

        //do a check
        if (gameChannelList.containsKey(guildID) && townChannelList.containsKey(guildID) && wolfChannelList.containsKey(guildID)) {
            //ok attempt to start game again
            startGame(guild, author, channel);
        }
    }

    private String getGameChannelName(Long guildID) {
        return dbMan.getGameChannel(guildID);
    }

    /**
     * Join game.
     *
     * @param guildID the guild id
     * @param player  the player
     */
    public void joinGame(Long guildID, Member player) {
        //check game status
        if (getWerewolfGameState(guildID) != GameState.GAMESTART) {
            return;//if the game is anything but gamestate.GAMESTART, then don't allow users to join
        }

        //set permission to allow user to talk in town channel
        if (!hasPlayer(guildID, player)) {
            //add player to the list
            Player newPlayer = addPlayer(guildID, player);
            getTownChannel(guildID).sendMessage(getTheme("JOIN", MessType.GAME, guildID, player, gamesPlayerLists.get(guildID).getPlayerSize()).build()).queue();
            sendPrivateMessage(getTheme("ADDED", MessType.NOTICE, guildID).build(), newPlayer);
        } else {
            getTownChannel(guildID).sendMessage(player.getAsMention() + " You're already in game").queue();
        }
        //TODO set channel permission to allow user to talk in channel, if we have permission role
    }

    private Player addPlayer(Long guildID, Member player) {
        return gamesPlayerLists.get(guildID).addPlayer(player);
    }

    /**
     * Has player boolean.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @return the boolean
     */
    public boolean hasPlayer(Long guildID, Long userID) {
        if (gamesPlayerLists.containsKey(guildID)) {
            return gamesPlayerLists.get(guildID).hasPlayer(userID);
        } else {
            return false;
        }
    }

    /**
     * Has player boolean.
     *
     * @param guildID the guild id
     * @param player  the player
     * @return the boolean
     */
    public boolean hasPlayer(Long guildID, Member player) {
        if (gamesPlayerLists.containsKey(guildID)) {
            return gamesPlayerLists.get(guildID).hasPlayer(player.getUser().getIdLong());
        } else {
            return false;
        }
    }

    private Integer getJoinTime(Long guildID) {
        if (gameList.containsKey(guildID)) {
            return gameList.get(guildID).getJoinTime();
        } else {
            return null;
        }

    }

    private void resetPlayerList(Long guildID) {
        if (gamesPlayerLists.containsKey(guildID)) {
            gamesPlayerLists.get(guildID).resetList();
        } else {
            gamesPlayerLists.put(guildID, new PlayerList());
        }
    }

    private void resetRounds(Long guildID) {
        if (gameList.containsKey(guildID)) {
            gameList.get(guildID).setRoundNo(0);
        } else {
            WWGame newWWGame = new WWGame();
            newWWGame.setRoundNo(0);
            gameList.put(guildID, newWWGame);
        }

    }

    /**
     * Gets theme.
     *
     * @param theme_id the theme id
     * @param type     the type
     * @param guildID  the guild id
     * @return the theme
     */
    protected EmbedBuilder getTheme(String theme_id, MessType type, Long guildID) {
        return getTheme(theme_id, type, guildID, null, 0, null, null);
    }

    /**
     * Gets theme.
     *
     * @param theme_id the theme id
     * @param type     the type
     * @param guildID  the guild id
     * @param number   the number
     * @return the theme
     */
    protected EmbedBuilder getTheme(String theme_id, MessType type, Long guildID, int number) {
        return getTheme(theme_id, type, guildID, null, number, null, null);
    }

    /**
     * Gets theme.
     *
     * @param theme_id the theme id
     * @param type     the type
     * @param guildID  the guild id
     * @param player1  the player 1
     * @return the theme
     */
    protected EmbedBuilder getTheme(String theme_id, MessType type, Long guildID, Member player1) {
        return getTheme(theme_id, type, guildID, player1, 0, null, null);
    }

    /**
     * Gets theme.
     *
     * @param theme_id the theme id
     * @param type     the type
     * @param guildID  the guild id
     * @param player1  the player 1
     * @param time     the time
     * @return the theme
     */
    protected EmbedBuilder getTheme(String theme_id, MessType type, Long guildID, Member player1, Integer time) {
        return getTheme(theme_id, type, guildID, player1, time, null, null);
    }

    /**
     * Gets theme.
     *
     * @param theme_id the theme id
     * @param type     the type
     * @param guildID  the guild id
     * @param player1  the player 1
     * @param player2  the player 2
     * @return the theme
     */
    protected EmbedBuilder getTheme(String theme_id, MessType type, Long guildID, Member player1, List<Member> player2) {
        return getTheme(theme_id, type, guildID, player1, 0, player2, null);
    }

    /**
     * Gets theme.
     *
     * @param theme_id the theme id
     * @param type     the type
     * @param guildID  the guild id
     * @param player2  the player 2
     * @return the theme
     */
    protected EmbedBuilder getTheme(String theme_id, MessType type, Long guildID, List<Member> player2) {
        return getTheme(theme_id, type, guildID, null, 0, player2, null);
    }

    /**
     * Gets theme.
     *
     * @param theme_id      the theme id
     * @param type          the type
     * @param guildID       the guild id
     * @param player1       the player 1
     * @param number        the number
     * @param player2       the player 2
     * @param previousEmbed the previous embed
     * @return the theme
     */
    protected EmbedBuilder getTheme(String theme_id, MessType type, Long guildID, Member player1, Integer number, List<Member> player2, EmbedBuilder previousEmbed) {


        String bold = "**";
        String underline = "__";
        String italic = "*";

        String suffix = "";
        String prefix = "";
        String infoPrefix = "";
        String infoSuffix = "";
        String avatarType;
        EmbedBuilder eBuilder;
        if (previousEmbed != null) {
            eBuilder = previousEmbed;
            eBuilder.addField(null, null, false);
        } else {
            eBuilder = new EmbedBuilder();
        }

        String themeText = getThemeText(guildID, theme_id);
        //(Notice is left over from IRC, but that's doesn't mean we can't use it in a different way

        /*StringBuilder listOfNames = new StringBuilder();
        if(player2 != null && !player2.isEmpty()) {
			for (Member player : player2) {
				String mention = player.getEffectiveName();
				if (listOfNames.toString().equals("")) {
					listOfNames = listOfNames.append(mention);
				} else {
					listOfNames.append(", ").append(mention);
				}
			}
		}*/

        String listOfNames = "";
        if (player2 != null && !player2.isEmpty()) {
            for (Member player : player2) {
                String name = "";
                if (player != null)
                    name = player.getEffectiveName();
                else {
                    name = "No Lynch";
                }
                if (listOfNames.equals("")) {
                    listOfNames = name;
                } else {
                    listOfNames = listOfNames + ", " + name;
                }
            }
        }

        //Set up formatting
        switch (type) {
            case NOTICE:
                //No formatting
                avatarType = "AVATAR_NOTICE";
                break;
            case NARRATION:
                //We Want this Itatic with player names bolded and underlined
                suffix = "";
                prefix = "";
                infoPrefix = bold + underline;
                infoSuffix = underline + bold; //make sure to mirror prefix
                avatarType = "AVATAR_NARR";
                break;
            case GAME:
                //Game Text bold with names itatic and underlined
                suffix = "";
                prefix = "";
                infoPrefix = italic + underline;
                infoSuffix = underline + italic; //make sure to mirror prefix
                avatarType = "AVATAR_GAME";
                break;

            case CONTROL:
                avatarType = null;
                break;

            default:
                avatarType = null;
                break;
        }
        if (themeText == null) {
            return null;
        }
        String strPlayer1 = "";
        Role playerRole = null;
        if (player1 != null) {
            strPlayer1 = player1.getEffectiveName();
            playerRole = gamesPlayerLists.get(guildID).getPlayerRole(player1.getUser().getIdLong());
            themeText = themeText.replaceAll("MENTION", player1.getAsMention());
        } else if (player2 != null) {
            String mentions = "";
            for (Member player : player2) {
                String mention = "";
                if (player != null)
                    mention = player.getAsMention();
                else {
                    mention = "No Lynch";
                }
                if (mentions.equals("")) {
                    mentions = mention;
                } else {
                    mentions = mentions + ", " + mention;
                }
            }
            themeText = themeText.replaceAll("MENTION", mentions);
        }
        themeText = themeText.replaceAll("WOLFPURAL", "WOLFPLURAL"); //Fix typo
        themeText = themeText.replaceAll("PLAYER1", suffix + infoPrefix + strPlayer1 + infoSuffix + prefix);
        themeText = themeText.replaceAll("PLAYER2", suffix + infoPrefix + listOfNames.toString() + infoSuffix + prefix);
        themeText = themeText.replaceAll("BOTNAME", suffix + infoPrefix + thisBot.getName() + infoSuffix + prefix);
        themeText = themeText.replaceAll("ROLE", suffix + infoPrefix + getRoleString(guildID, playerRole) + infoSuffix + prefix);
        themeText = themeText.replaceAll("NUMBER", suffix + infoPrefix + number.toString() + infoSuffix + prefix);


        if (manyWolves(guildID)) {
            themeText = themeText.replaceAll("WOLFPLURAL", suffix + infoPrefix + getThemeText(guildID, "ONEWOLF") + infoSuffix + prefix);
        } else {
            themeText = themeText.replaceAll("WOLFPLURAL", suffix + infoPrefix + getThemeText(guildID, "MANY_WOLVES") + infoSuffix + prefix);
        }
		
		/* Return Theme Text */
        //System.out.println(prefix + themeText + suffix);
        eBuilder.setAuthor(null, null, getThemeText(guildID, avatarType));
        eBuilder.addField(null, themeText, false);
        return eBuilder;
    }

    /**
     * Sets wolf vote.
     *
     * @param guildID  the guild id
     * @param wolf     the wolf
     * @param wolfVote the wolf vote
     */
    public void setWolfVote(Long guildID, Member wolf, Member wolfVote) {
        if (!getWerewolfGameState(guildID).equals(GameState.NIGHTTIME))
            return;

        Long userID;
        userID = wolf.getUser().getIdLong();
        Player wolfPlayer = gamesPlayerLists.get(guildID).getPlayer(userID);

        userID = wolfVote.getUser().getIdLong();
        Player wolfKill = gamesPlayerLists.get(guildID).getPlayer(userID);

        if (wolfKill == null) {
            sendPrivateMessage("That person not a player in this game.", wolfPlayer);
        } else {
            setWolfVote(guildID, wolfPlayer, wolfKill);
        }
    }

    /**
     * Sets wolf vote.
     *
     * @param guildID  the guild id
     * @param wolf     the wolf
     * @param wolfVote the wolf vote
     */
    public void setWolfVote(Long guildID, Member wolf, int wolfVote) {
        if (!getWerewolfGameState(guildID).equals(GameState.NIGHTTIME))
            return;
        Player wolfPlayer = gamesPlayerLists.get(guildID).getPlayer(wolf.getUser().getIdLong());
        Player wolfKill = playerNumberToPlayer(guildID, wolfVote);

        if (wolfKill == null) {
            sendPrivateMessage("I don't have any player by that PlayerNumber.", wolfPlayer);
        } else {
            setWolfVote(guildID, wolfPlayer, wolfKill);
        }
    }

    private void setWolfVote(Long guildID, Player wolf, Player wolfVote) {
        if (wolf.equals(wolfVote)) {
            getWolfChannel(guildID).sendMessage(getTheme("KILL_SELF", MessType.NOTICE, guildID).build()).queue();
            return;
        }

        if (wolf.getRole() != Role.WOLF) {
            sendPrivateMessage(getTheme("NOT_WOLF", MessType.NOTICE, guildID).build(), wolf);
        } else if (wolf.getPlayerState() != PlayerState.ALIVE) {
            sendPrivateMessage(getTheme("WOLF_DEAD", MessType.NOTICE, guildID).build(), wolf);
        } else if (wolf.getRole() == Role.WOLF && wolfVote.getPlayerState() != PlayerState.ALIVE && wolfVote.getRole() != Role.WOLF) {
            getWolfChannel(guildID).sendMessage(getTheme("WOLF_TARGET_DEAD", MessType.NOTICE, guildID, wolfVote.getMember()).build()).queue();
        } else if (wolf.getRole() == Role.WOLF && wolfVote.getRole() == Role.WOLF) {
            getWolfChannel(guildID).sendMessage(getTheme("FELLOW_WOLF", MessType.NOTICE, guildID).build()).queue();
        } else if (wolf.getRole() == Role.WOLF && wolfVote.getRole() != Role.WOLF && wolfVote.getPlayerState() == PlayerState.ALIVE) {
            if (manyWolves(guildID)) {
                gamesPlayerLists.get(guildID).setPlayerVote(wolf.getUserID(), wolfVote.getUserID());
                getWolfChannel(guildID).sendMessage(getTheme("WOLVES_CHOICE", MessType.NOTICE, guildID, wolfVote.getMember()).build()).queue();
                if (gamesPlayerLists.get(guildID).getNumberOfPlayerByRolePlayerState(Role.WOLF, PlayerState.ALIVE) > 1) {
                    List<Member> wolfVoteList = new ArrayList<Member>();
                    wolfVoteList.add(wolfVote.getMember());
                    getWolfChannel(guildID).sendMessage(getTheme("WOLVES_CHOICE", MessType.NOTICE, guildID, wolf.getMember(), wolfVoteList).build()).queue();
                }

            } else {
                getWolfChannel(guildID).sendMessage(getTheme("WOLF_CHOICE", MessType.NOTICE, guildID, wolfVote.getMember()).build()).queue();
                gamesPlayerLists.get(guildID).setPlayerVote(wolf.getUserID(), wolfVote.getUserID());
            }
        }
    }

    /**
     * Sets seer view.
     *
     * @param guildID the guild id
     * @param seer    the seer
     * @param toSee   the to see
     */
    public void setSeerView(Long guildID, Member seer, Member toSee) {
        Long userID;
        if (getWerewolfGameState(guildID) != GameState.NIGHTTIME) return;
        userID = seer.getUser().getIdLong();
        Player voterPlayer = gamesPlayerLists.get(guildID).getPlayer(userID);

        userID = toSee.getUser().getIdLong();
        Player voteForPlayer = gamesPlayerLists.get(guildID).getPlayer(userID);

        if (voteForPlayer == null) {
            sendPrivateMessage("That person not a player in this game.", voterPlayer);
        } else {
            setSeerView(guildID, voterPlayer, voteForPlayer);
        }
    }

    /**
     * Sets seer view.
     *
     * @param guildID the guild id
     * @param seer    the seer
     * @param toSee   the to see
     */
    public void setSeerView(Long guildID, Member seer, int toSee) {
        if (getWerewolfGameState(guildID) != GameState.NIGHTTIME)
            return;
        Player voterPlayer = gamesPlayerLists.get(guildID).getPlayer(seer.getUser().getIdLong());
        Player voteForPlayer = playerNumberToPlayer(guildID, toSee);

        if (voteForPlayer == null) {
            sendPrivateMessage("I don't have any player by that PlayerNumber.", voterPlayer);
        } else {
            setSeerView(guildID, voterPlayer, voteForPlayer);
        }
    }

    private void setSeerView(Long guildID, Player seer, Player toSee) {
        //invalid player
        if (seer.equals(toSee)) {
            //SEE_SELF - seer tried to look at themselves
            sendPrivateMessage(getTheme("SEE_SELF", MessType.NOTICE, guildID).build(), seer);
            return;
        }

        if (seer.getRole() != Role.SEER) {
            //NOT_SEER - player not a seer
            sendPrivateMessage(getTheme("NOT_SEER", MessType.NOTICE, guildID).build(), seer);

        } else if (seer.getPlayerState() != PlayerState.ALIVE) {
            //SEER_DEAD -  Seer is dead
            sendPrivateMessage(getTheme("DEAD_SEER", MessType.NOTICE, guildID, toSee.getMember()).build(), seer);
        } else if (seer.getRole() == Role.SEER && toSee.getPlayerState() != PlayerState.ALIVE) {
            //SEER_TARGET_DEAD - target dead/fled
            sendPrivateMessage(getTheme("SEER_TARGET_DEAD", MessType.NOTICE, guildID, toSee.getMember()).build(), seer);
        } else if (seer.getRole() == Role.SEER && toSee.getPlayerState() == PlayerState.ALIVE) {
            gamesPlayerLists.get(guildID).setSeerViewing(toSee.getUserID());
            sendPrivateMessage(getTheme("WILL_SEE", MessType.NOTICE, guildID, toSee.getMember()).build(), seer);
        }
    }

    /**
     * Sets vote.
     *
     * @param guildID the guild id
     * @param voter   the voter
     * @param votee   the votee
     */
    public void setVote(Long guildID, Member voter, Member votee) {
        Long userID;
        if (getWerewolfGameState(guildID) != GameState.VOTETIME) return;
        userID = voter.getUser().getIdLong();
        Player voterPlayer = gamesPlayerLists.get(guildID).getPlayer(userID);

        userID = votee.getUser().getIdLong();
        Player voteForPlayer = gamesPlayerLists.get(guildID).getPlayer(userID);

        if (voteForPlayer == null) {
            getTownChannel(guildID).sendMessage("You can't vote for " + votee.getEffectiveName() + ". They are not a player.").queue();
        } else {
            setVote(guildID, voterPlayer, voteForPlayer);
        }
    }


    /**
     * Sets vote.
     *
     * @param guildID the guild id
     * @param voter   the voter
     * @param votee   the votee
     */
    public void setVote(Long guildID, Member voter, int votee) {
        if (getWerewolfGameState(guildID) != GameState.VOTETIME)
            return;
        Player voterPlayer = gamesPlayerLists.get(guildID).getPlayer(voter.getUser().getIdLong());
        Player voteForPlayer = playerNumberToPlayer(guildID, votee);

        if (voteForPlayer == null) {
            getTownChannel(guildID).sendMessage("I don't have any player by that PlayerNumber.");
        } else {
            setVote(guildID, voterPlayer, voteForPlayer);
        }
    }

    private void setVote(Long guildID, Player voter, Player votee) {
        if (voter.getPlayerState() == PlayerState.DEAD) {
            //Player is dead
            sendPrivateMessage(getTheme("YOURE_DEAD", MessType.NOTICE, guildID).build(), voter);
            return;
        } else if (voter.getPlayerState() == PlayerState.FLED) {
            //player left the game
            sendPrivateMessage(getTheme("YOUVE_FLED", MessType.NOTICE, guildID).build(), voter);
            return;
        }

        //check to see if player is alive or no lynch
        if (votee.getPlayerState() == PlayerState.ALIVE || votee.getPlayerState() == PlayerState.NOLYNCH) {
            assignVote(guildID, voter, votee);
        } else {
            getTownChannel(guildID).sendMessage(getTheme("VOTE_TARGET_DEAD", MessType.NOTICE, guildID).build()).queue();
        }
    }

    private void assignVote(Long guildID, Player voter, Player votee) {
        List<Member> listVotee = new ArrayList<Member>();
        listVotee.add(votee.getMember());

        EmbedBuilder theme;
        if (votee.getRole() == Role.NOLYNCH) {

            if (gamesPlayerLists.get(guildID).playerHasVoted(voter.getUserID())) {
                gamesPlayerLists.get(guildID).setPlayerVote(voter.getUserID(), votee.getUserID());
                //CHANGE_VOTE_NOLYNCH
                theme = getTheme("CHANGE_VOTE_NOLYNCH", MessType.GAME, guildID, voter.getMember(), listVotee);
            } else {
                gamesPlayerLists.get(guildID).setPlayerVote(voter.getUserID(), votee.getUserID());
                //VOTED_FOR_NOLYNCH
                theme = getTheme("VOTED_FOR_NOLYNCH", MessType.GAME, guildID, voter.getMember(), listVotee);
            }
        } else {
            if (gamesPlayerLists.get(guildID).playerHasVoted(voter.getUserID())) {
                gamesPlayerLists.get(guildID).setPlayerVote(voter.getUserID(), votee.getUserID());
                theme = getTheme("CHANGE_VOTE", MessType.GAME, guildID, voter.getMember(), listVotee);
                //CHANGE_VOTE
            } else {
                gamesPlayerLists.get(guildID).setPlayerVote(voter.getUserID(), votee.getUserID());
                theme = getTheme("VOTED_FOR", MessType.GAME, guildID, voter.getMember(), listVotee);
                //VOTED_FOR
            }
        }

        displayVoteCount(guildID, theme);
    }

    /**
     * Display vote count.
     *
     * @param guildID the guild id
     * @param theme   the theme
     */
    public void displayVoteCount(Long guildID, EmbedBuilder theme) {
        //display current vote
        List<Player> voteCount = gamesPlayerLists.get(guildID).voteCount(false, 1);
        if (theme == null) {
            theme = new EmbedBuilder();
        }

        int count = 0;
        final int top = 5;

        theme.addField("Current Count", null, false);
        for (Player player : voteCount) {
            if (player.getVoteCount() > 0 && count <= top) {
                theme.addField(player.getEffectiveName(), player.getVoteCount().toString(), true);
            } else if (count > top) {
                break;
            }
            count++;
        }
        getTownChannel(guildID).sendMessage(theme.build()).queue();
    }

    private Player playerNumberToPlayer(Long guildID, int votee) {
        return gamesPlayerLists.get(guildID).getPlayerByPlayerID(votee);
    }

    private String getThemeText(Long guildID, String theme_id) {
        if (themeList.containsKey(guildID)) {
            return themeList.get(guildID).getText(theme_id);
        } else {
            return "ERR - Server has not obtained Theme Text.";
        }
    }

    private void sendGameRole(PrivateChannel channel, MessageEmbed message, Long guildID, Player player) {
        channel.sendMessage(message).queue((m) -> setMessageReceived(guildID, player));
    }

    private void setMessageReceived(Long guildID, Player player) {
        player.setRoleRecieved(true);
        //System.out.println(player.getEffectiveName() + " received Role");
        if (gamesPlayerLists.get(guildID).hasAllPlayerReceivedRoles()) {
            System.out.println("Starting game");
            gameStarted(guildID);
        }
    }

    private void gameStarted(Long guildID) {

        //check to make sure that during sending of roles gameStatus hasn't changed, like a wolf leaves during sending of roles
        //and caused the checkWin to return true;
        if (!(getWerewolfGameState(guildID) == GameState.DAYTIME || getWerewolfGameState(guildID) == GameState.NIGHTTIME)) {
            System.out.println("GameState moved on");
            return;
        }
        //Ok all the users should have their roles, let conuntine with the the gameeee~ (you've lost the game)
        EmbedBuilder embed;
        if (manyWolves(guildID)) {
            embed = getTheme("TWO_WOLVES", MessType.GAME, guildID, gamesPlayerLists.get(guildID).getNumberOfPlayerByRole(Role.WOLF));
        } else {
            embed = new EmbedBuilder();
        }

        if (gamesPlayerLists.get(guildID).getPlayerSize() == gameList.get(guildID).getMinPlayers()) {
            setGameState(guildID, GameState.DAYTIME);
            startDayTime(guildID, embed);
        } else {
            setGameState(guildID, GameState.NIGHTTIME);
            startNightTime(guildID, embed);
        }
    }

    private void sendPrivateMessage(String message, Player player) {
        sendPrivateMessage(new MessageBuilder().append(message).build(), player);
    }

    private void sendPrivateMessage(MessageEmbed message, Player player) {
        User user = player.getMember().getUser();

        user.openPrivateChannel().queue((channel) -> sendGameMessage(channel, message));
    }

    private void sendPrivateMessage(Message message, Player player) {
        User user = player.getMember().getUser();

        user.openPrivateChannel().queue((channel) -> sendGameMessage(channel, message));
    }

    private void sendGameMessage(PrivateChannel channel, MessageEmbed message) {
        channel.sendMessage(message).queue();
    }

    private void sendGameMessage(PrivateChannel channel, Message message) {
        channel.sendMessage(message).queue();
    }

    private boolean isPlaying(Long guildID) {
        return gameList.get(guildID).getGameState().compareTo(GameState.IDLE) > 0;
    }

    private boolean manyWolves(Long guildID) {
        return gamesPlayerLists.get(guildID).getNumberOfPlayerByRole(Role.WOLF) > 1;
    }

    private void setRoles(Long guildID) {
        //Everyone starts as VILL first
        for (Player player : gamesPlayerLists.get(guildID).getPlayerList()) {
            player.setRole(Role.VILL);
        }

        //Let set up the roles
        //Let check to see how many wolves we're having
        Double noWolves = Math.floor(gamesPlayerLists.get(guildID).getPlayerSize() / 4);
        int numberOfWolves = noWolves.intValue();
        //Work out how many masons we're having
        Double noMasons = Math.floor(gamesPlayerLists.get(guildID).getPlayerSize() / 6);
        int numberOfMasons = noMasons.intValue();

        //Assign roles
        List<Player> roleList = gamesPlayerLists.get(guildID).randomOrder();

        //Set Wolves
        for (int i = 0; i < numberOfWolves; i++) {
            //Assign
            roleList.get(i).setRole(Role.WOLF);
            //set permission
            //TODO set permission
        }

        //Set Masons
        if (numberOfMasons > 1) {
            for (int i = numberOfWolves; i < numberOfMasons + numberOfWolves; ++i) {
                roleList.get(i).setRole(Role.MASON);
            }
        }

        //Set Seer
        roleList.get(roleList.size() - 1).setRole(Role.SEER);

        //send wolves their role description
        if (manyWolves(guildID)) {
            for (Player player : gamesPlayerLists.get(guildID).getPlayerListByRole(Role.WOLF)) {
                sendRole(guildID, player.getMember());
            }
        } else {
            for (Player player : gamesPlayerLists.get(guildID).getPlayerListByRole(Role.WOLF)) {
                sendRole(guildID, player.getMember());
            }
        }

        //Send Masons their role description
        if (numberOfMasons > 1) {
            for (Player player : gamesPlayerLists.get(guildID).getPlayerListByRole(Role.MASON)) {
                sendRole(guildID, player.getMember());
            }
        }

        //Send Seer
        for (Player player : gamesPlayerLists.get(guildID).getPlayerListByRole(Role.SEER)) {
            sendRole(guildID, player.getMember());
        }

        //Send vill text
        for (Player player : gamesPlayerLists.get(guildID).getPlayerListByRole(Role.VILL)) {
            sendRole(guildID, player.getMember());
        }
    }

    /**
     * Send role.
     *
     * @param guildID      the guild id
     * @param playerMember the player member
     */
    public void sendRole(Long guildID, Member playerMember) {
        //Make sure this is during a game
        if (!isPlaying(guildID)) return;
        Player player = gamesPlayerLists.get(guildID).getPlayer(playerMember.getUser().getIdLong());

        String formatting;
        List<Member> otherPlayers = new ArrayList<Member>();

        switch (player.getRole()) {
            case WOLF:
                if (manyWolves(guildID)) {
                    List<Player> wolves = gamesPlayerLists.get(guildID).getPlayerListByRolePlayerState(Role.WOLF, PlayerState.ALIVE);
                    for (Player otherPlayer : wolves) {
                        if (!otherPlayer.equals(player)) {
                            otherPlayers.add(otherPlayer.getMember());
                        }
                    }
                    sendPrivateMessage(getTheme("WOLVES_DESCRIPTION", MessType.GAME, guildID, player.getMember(), otherPlayers).build(), player);
                } else {
                    sendPrivateMessage(getTheme("WOLF_DESCRIPTION", MessType.NOTICE, guildID, player.getMember()).build(), player);
                }
                break;
            case MASON:
                List<Player> masons = gamesPlayerLists.get(guildID).getPlayerListByRolePlayerState(Role.MASON, PlayerState.ALIVE);

                for (Player otherPlayer : masons) {
                    if (!otherPlayer.equals(player)) {
                        otherPlayers.add(otherPlayer.getMember());
                    }
                }
                sendPrivateMessage(getTheme("MASON_DESCRIPTION", MessType.NOTICE, guildID, player.getMember(), otherPlayers).build(), player);
                break;
            case SEER:
                sendPrivateMessage(getTheme("SEER_DESCRIPTION", MessType.NOTICE, guildID, player.getMember()).build(), player);
                break;
            case VILL:
                sendPrivateMessage(getTheme("VILL_DESCRIPTION", MessType.NOTICE, guildID, player.getMember()).build(), player);
                break;
            default:
                getTownChannel(guildID).sendMessage("Error on see. Missing Role").queue();
                break;
        }
        if (player.isRoleRecieved()) {
            sendPrivateMessage(getTheme("YOUR_ROLE", MessType.NOTICE, guildID, player.getMember()).build(), player);
        } else {
            sendPrivateMessageRole(getTheme("YOUR_ROLE", MessType.NOTICE, guildID, player.getMember()).build(), guildID, player);
        }

        if (player.getRole().equals(Role.WOLF) && otherPlayers.size() > 0) {
            sendPrivateMessage(getTheme("OTHER_WOLF", MessType.NOTICE, guildID, otherPlayers).build(), player);
        } else if (player.getRole().equals(Role.MASON) && otherPlayers.size() > 0) {
            sendPrivateMessage(getTheme("OTHER_MASONS", MessType.NOTICE, guildID, otherPlayers).build(), player);
        }
    }

    private void sendPrivateMessageRole(MessageEmbed message, Long guildID, Player player) {
        User user = player.getMember().getUser();
        user.openPrivateChannel().queue((channel) -> sendGameRole(channel, message, guildID, player));
    }

    private void gamePermission(Long guildID) {
        //TODO set up permissions
    }

    private void startVoteTime(Long guildID) {
        Integer noPlayer = gamesPlayerLists.get(guildID).getNumberOfPlayerByState(PlayerState.ALIVE);
        int intTimeSet = gameList.get(guildID).getVoteTimeX() * noPlayer;
        int minVoteTime = gameList.get(guildID).getMinVoteTime();
        int maxVoteTime = gameList.get(guildID).getMaxVoteTime();

        if (intTimeSet < minVoteTime)
            intTimeSet = minVoteTime;
        else if (intTimeSet > maxVoteTime)
            intTimeSet = maxVoteTime;

        getTownChannel(guildID).sendMessage(getTheme("VOTE_TIME", MessType.NARRATION, guildID, intTimeSet).build()).queue();

        addTimer(guildID, scheduler.schedule(new TenSecWarning(guildID, timerCount), intTimeSet - 11, TimeUnit.SECONDS));
        addTimer(guildID, scheduler.schedule(new WereTask(guildID, timerCount), intTimeSet, TimeUnit.SECONDS));
    }

    /**
     * Shut down.
     */
    public void shutDown() {
        scheduler.shutdownNow();
    }

    /**
     * Stop game.
     *
     * @param guildID the guild id
     */
    public void stopGame(Long guildID) //Cause a hard stop of the game on the called from server
    {
        //set gamestate to Idle, this basically tell us this guild has no-games running anymore
        setGameState(guildID, GameState.IDLE);//Either way, as long as gamestate is set to IDLE, the scheduled will eventually stop, instead of right away.
        Map<Long, ScheduledFuture<?>> listOfTimers = timerThreads.get(guildID);
        for (Long taskID : listOfTimers.keySet()) {
            listOfTimers.get(taskID).cancel(true);
            removeTimer(guildID, taskID);
        }

        //TODO Reset Permissions to IDLE gamestate

        //clear theme
        clearTheme(guildID);
        getTownChannel(guildID).sendMessage("Game Over.").queue();
    }

    private boolean checkWin(Long guildID) {
        if (gameList.get(guildID).isCheckingWin()) {
            return true; //Already running, no need to check twice
        }

        gameList.get(guildID).setCheckingWin(true);
        int wolfCount = gamesPlayerLists.get(guildID).getNumberOfPlayerByRolePlayerState(Role.WOLF, PlayerState.ALIVE);
        int seerCount = gamesPlayerLists.get(guildID).getNumberOfPlayerByRolePlayerState(Role.SEER, PlayerState.ALIVE);
        int masonCount = gamesPlayerLists.get(guildID).getNumberOfPlayerByRolePlayerState(Role.MASON, PlayerState.ALIVE);
        int villCount = gamesPlayerLists.get(guildID).getNumberOfPlayerByRolePlayerState(Role.VILL, PlayerState.ALIVE);
        int humanCount = seerCount + villCount + masonCount;

        boolean win = false;
        //TODO Add score to player's profile, 10 for winning + 10 point for staying alive on the winning team
        if (wolfCount == 0 && humanCount != 0) {
            EmbedBuilder thisEmbed = getTheme("VILL_WIN", MessType.NARRATION, guildID);
            getTownChannel(guildID).sendMessage(getTheme("CONGR_VILL", MessType.GAME, guildID, null, null, null, thisEmbed).build()).queue();
            win = true;
        }

        if (wolfCount >= humanCount && wolfCount != 0) {
            if (wolfCount == 1) {
                EmbedBuilder thisEmbed = getTheme("WOLF_WIN", MessType.NARRATION, guildID);
                getTownChannel(guildID).sendMessage(getTheme("CONGR_WOLF", MessType.GAME, guildID, null, null, null, thisEmbed).build()).queue();
            } else {
                EmbedBuilder thisEmbed = getTheme("WOLVES_WIN", MessType.NARRATION, guildID);
                getTownChannel(guildID).sendMessage(getTheme("CONGR_WOLVES", MessType.GAME, guildID, null, null, null, thisEmbed).build()).queue();
            }
            win = true;
        }

        if (wolfCount == 0 && humanCount == 0) {
            getTownChannel(guildID).sendMessage(getTheme("TIE_GAME", MessType.GAME, guildID).build()).queue();
            win = true;
        }

        if (win) {
            showRoles(guildID);
            stopGame(guildID);
        }

        gameList.get(guildID).setCheckingWin(false);

        return win;
    }

    private void showRoles(Long guildID) {
        EmbedBuilder embedRoleList = new EmbedBuilder();
        embedRoleList.setAuthor("Role list", null, null);
        for (Player player : gamesPlayerLists.get(guildID).getPlayerList()) {
            embedRoleList.addField(player.getEffectiveName(), getRoleString(guildID, player.getRole())
                    + System.lineSeparator() + getPlayerStateString(player.getPlayerState(), guildID), true);
        }
        getTownChannel(guildID).sendMessage(embedRoleList.build()).queue();
    }

    /**
     * Gets player state string.
     *
     * @param state   the state
     * @param guildID the guild id
     * @return the player state string
     */
    protected String getPlayerStateString(PlayerState state, Long guildID) {
        switch (state) {
            case ALIVE:
                return getThemeText(guildID, "STATE_ALIVE");
            case DEAD:
                return getThemeText(guildID, "STATE_KILLED");
            case FLED:
                return getThemeText(guildID, "STATE_FLED");
            case NOLYNCH:
                return "No Lynch";
            case ERR:
            default:
                return "Error";
        }
    }

    /**
     * Gets role string.
     *
     * @param guildID the guild id
     * @param role    the role
     * @return the role string
     */
    protected String getRoleString(Long guildID, Role role) {
        if (role == null)
            return "";
        switch (role) {
            case SEER:
                return themeList.get(guildID).getText("ROLE_SEER");
            case WOLF:
                return themeList.get(guildID).getText("ROLE_WOLF");
            case VILL:
                return themeList.get(guildID).getText("ROLE_VILL");
            case MASON:
                return themeList.get(guildID).getText("ROLE_MASON");
            default:
                return "Unknown Role";
        }

    }

    /**
     * Display theme list message embed.
     *
     * @return the message embed
     */
    public MessageEmbed displayThemeList() {
        dbMan.sqlGetThemeList();
    }

    /**
     * Display theme.
     *
     * @param themeID the id
     * @return the message embed
     */
    public MessageEmbed displayTheme(Integer themeID) {

        if (themeID == null || themeID <= 0)
        {
            return null;
        }

        Theme themeDesc = dbMan.sqlGetThemeDesc(themeID);

        EmbedBuilder newEmbed = new EmbedBuilder();
        newEmbed.setAuthor(themeDesc.getName(), null, themeDesc.getAvatar());
        newEmbed.setTitle(themeDesc.getAuthor());
        newEmbed.setDescription(themeDesc.getDesc());
        newEmbed.addField("Created on: ", themeDesc.getDateCreated().toString(), true);
        newEmbed.addField("Last Modified On: ", themeDesc.getDateModified().toString(), true);
        newEmbed.addField("Theme played: ", themeDesc.getPlayedCount().toString(), true);
        newEmbed.setFooter("Theme ID: " + themeID.toString(), null);

        return newEmbed.build();

    }

    private void tallyVotes(Long guildID) {
        List<Player> voteList = gamesPlayerLists.get(guildID).voteCount(true, 1);
        int highestVoteCount;
        List<Player> mjVote = new LinkedList<Player>();

        Player guilty = null;
        EmbedBuilder thisEmbed = new EmbedBuilder();
        thisEmbed = getTheme("TALLY", MessType.GAME, guildID);

        if (voteList.isEmpty()) {
            thisEmbed = getTheme("NO_LYNCH", MessType.NARRATION, guildID, null, null, null, thisEmbed);
        } else {
            getTownChannel(guildID).sendMessage(thisEmbed.build()).queue();
            thisEmbed = new EmbedBuilder();
            //pretend to count
            Random rnd = new Random();
            int countTime = rnd.nextInt(8) + 2;
            try {
                Thread.sleep(countTime * 1000);
            } catch (InterruptedException iex) {
                System.err.println("Could not connect/Thread");
            } //TODO look into different methods, I think holding a thread may not be the best method for this bot

            //Make sure we've not changed state while pretending to count
            if (getWerewolfGameState(guildID) != GameState.NIGHTTIME) {
                return;
            }

            highestVoteCount = gamesPlayerLists.get(guildID).getPlayerVoteCount(voteList.get(0).getUserID());

            for (Player player : voteList) {
                if (gamesPlayerLists.get(guildID).getPlayerVoteCount(player.getUserID()) == highestVoteCount)
                    mjVote.add(player);
                else
                    break; //No-longer anyone with the higehest vote
            }

            if (mjVote.size() == 1) {
                guilty = mjVote.get(0);
            } else {
                Random rndno = new Random();
                thisEmbed = getTheme("TIE", MessType.GAME, guildID, null, mjVote.size(), null, thisEmbed);
                guilty = mjVote.get(rndno.nextInt((mjVote.size())));
            }

            if (guilty.getPlayerNo() != 0) {
                Role guiltyRole = guilty.getRole();
                guilty.setPlayerState(PlayerState.DEAD);
                switch (guiltyRole) {
                    case WOLF:
                        thisEmbed = getTheme("WOLF_LYNCH", MessType.NARRATION, guildID, guilty.getMember(), null, null, thisEmbed);
                        break;
                    case SEER:
                        thisEmbed = getTheme("SEER_LYNCH", MessType.NARRATION, guildID, guilty.getMember(), null, null, thisEmbed);
                        break;
                    case VILL:
                    case MASON:
                        thisEmbed = getTheme("VILL_LYNCH", MessType.NARRATION, guildID, guilty.getMember(), null, null, thisEmbed);
                        break;
                    default:
                        getTownChannel(guildID).sendMessage("Error, unknown Role " + guilty.getEffectiveName() + " is lynched");
                        break;
                }
                thisEmbed = getTheme("ROLE_IS_LYNCHED", MessType.GAME, guildID, guilty.getMember(), null, null, thisEmbed);
                //I think this was a balancing issue, I can't exactly remember why
                if (guilty.getRole() != Role.SEER) {
                    gamesPlayerLists.get(guildID).setDyingVoice(guilty.getUserID());
                } else {
                    //Seers don't get dying voice
                    //TODO permissions
                }

            } else {
                thisEmbed = getTheme("VOTED_NO_LYNCH", MessType.NARRATION, guildID, null, null, null, thisEmbed);
            }
        }
        getTownChannel(guildID).sendMessage(thisEmbed.build()).queue();

        //We'll now see if anyone defied the goddess for too long
        List<Player> badPlayer = gamesPlayerLists.get(guildID).getPlayerListByNonVoteCount(gameList.get(guildID).getNoVoteRounds());

        //check to make sure we haven't won already before killing off the non-voters (to prevent people from idling)
        if (!checkWin(guildID)) {
            for (Player player : badPlayer) {
                EmbedBuilder embed = new EmbedBuilder();
                player.setPlayerState(PlayerState.DEAD);
                sendPrivateMessage(getTheme("NOT_VOTED_NOTICE", MessType.NOTICE, guildID).build(), player);
                embed = getTheme("NOT_VOTED", MessType.GAME, guildID, player.getMember(), null, null, embed);
                embed = getTheme("ROLE_IS_KILLED", MessType.GAME, guildID, player.getMember(), null, null, embed);
                getTownChannel(guildID).sendMessage(embed.build()).queue();
                //TODO Permissions
            }
        }
    }

    private void endNightTime(Long guildID) {
        //Grab the seer
        List<Player> seers = gamesPlayerLists.get(guildID).getPlayerListByRole(Role.SEER);
        //Grab wolves votes
        List<Player> wolvesVote = gamesPlayerLists.get(guildID).voteCount(false, 1);
        Player playerKilled = null;
        //Have the wolves voted.
        if (wolvesVote.size() == 0) {
            //No Votes
            getTownChannel(guildID).sendMessage(getTheme("NO_KILL", MessType.NARRATION, guildID).build()).queue();
        } else if (wolvesVote.size() == 1) {
            //One Vote
            playerKilled = wolvesVote.get(0);
            gamesPlayerLists.get(guildID).setPlayerState(playerKilled.getUserID(), PlayerState.DEAD);

            sendWolfKillRole(guildID, playerKilled); //Send kill to game channel
        } else {
            //More than one vote - pick one at pesdo-random
            playerKilled = wolvesVote.get((int) Math.random() * wolvesVote.size());
            gamesPlayerLists.get(guildID).setPlayerState(playerKilled.getUserID(), PlayerState.DEAD);

            sendWolfKillRole(guildID, playerKilled); //Send kill to game channel
        }
        //Send seer stuff after wolves kill.
        if (!gamesPlayerLists.get(guildID).isSeerViewEmpty()) {
            Player seerVote = gamesPlayerLists.get(guildID).getSeerView();
            Player seer = seers.get(0);
            PlayerState seerState = gamesPlayerLists.get(guildID).getPlayerState(seer.getUserID());
            PlayerState seerVoteState = gamesPlayerLists.get(guildID).getPlayerState(seerVote.getUserID());
            if (seerState == PlayerState.ALIVE && seerVoteState == PlayerState.ALIVE) {
                sendPrivateMessage(getTheme("SEER_SEE", MessType.NARRATION, guildID, seerVote.getMember()).build(), seer);
            } else if (playerKilled != null && playerKilled.equals(seerVote)) {
                sendPrivateMessage(getTheme("SEER_TARGET_KILLED", MessType.NARRATION, guildID, seerVote.getMember()).build(), seer);
            } else if (playerKilled != null && playerKilled.equals(seer)) {
                sendPrivateMessage(getTheme("SEER_GOT_KILLED", MessType.NARRATION, guildID, seerVote.getMember()).build(), seer);
            }
        }
    }

    private void sendWolfKillRole(Long guildID, Player playerKilled) {
        switch (playerKilled.getRole()) {
            case SEER:
                getTownChannel(guildID).sendMessage(getTheme("SEER_KILL", MessType.NARRATION, guildID, playerKilled.getMember()).build()).queue();
                break;
            case VILL:
            case MASON:
                getTownChannel(guildID).sendMessage(getTheme("VILL_KILL", MessType.NARRATION, guildID, playerKilled.getMember()).build()).queue();
                break;
            case WOLF:
                getTownChannel(guildID).sendMessage("Ooops, Wolf got killed, somehow").queue();
                break;
            default:
                getTownChannel(guildID).sendMessage("Unknown role - " + playerKilled.getEffectiveName() + " is killed").queue();
                break;
        }
    }

    private void startNightTime(Long guildID, EmbedBuilder embed) {
        Integer noWolvesLeft = gamesPlayerLists.get(guildID).getNumberOfPlayerByRolePlayerState(Role.WOLF, PlayerState.ALIVE);
        WWGame thisGameSetting = gameList.get(guildID);
        //How long is the night?
        Double powerOf = Math.pow(thisGameSetting.getNightTimePow(), noWolvesLeft);
        Double timeSet = thisGameSetting.getNightTimeX() - (thisGameSetting.getNightTimeY() * powerOf);
        Long lngTimeSet = Math.round(timeSet); //returns a Long type, although the value can fit in an Integer.
        Integer intTimeSet = lngTimeSet.intValue();
        if (embed == null) {
            embed = new EmbedBuilder();
        }

        //Night time, change permission to stop everyone talking expect for anyone with a dying voice
        setPermissions(guildID);

        //reset votes
        gamesPlayerLists.get(guildID).resetVotes();

        //if someone was lynched, allow them their dying voice
        if (!gamesPlayerLists.get(guildID).isDyingVoiceEmpty()) {
            getTownChannel(guildID).sendMessage(getTheme("DYING_BREATH", MessType.NARRATION, guildID, gamesPlayerLists.get(guildID).getDyingVoice().getMember()).build()).queue();
        }

        //Night time description (FIRST_NIGHT/NIGHT_TIME)
        if (thisGameSetting.getRoundNo() == 0) {
            embed = getTheme("FIRST_NIGHT", MessType.NARRATION, guildID, null, intTimeSet, null, embed);
        } else {
            embed = getTheme("NIGHT_TIME", MessType.NARRATION, guildID, null, intTimeSet, null, embed);
        }

        List<Player> seers = gamesPlayerLists.get(guildID).getPlayerListByRolePlayerState(Role.SEER, PlayerState.ALIVE);

        getTownChannel(guildID).sendMessage(embed.build()).queue();

        //Grab Seer/clear choice and give instructions (private message)
        if (seers.size() > 0) {
            Player seer = seers.get(0);

            sendPrivateMessage(getTheme("SEER_INSTRUCTIONS", MessType.NARRATION, guildID, intTimeSet).build(), seer);
            sendPrivateMessage(listAlive(guildID, null).build(), seer);
        }
        gamesPlayerLists.get(guildID).clearSeerView();

        //Grab list of wolf(ves) and send instructions(wolf channel)
        List<Player> wolves = gamesPlayerLists.get(guildID).getPlayerListByRolePlayerState(Role.WOLF, PlayerState.ALIVE);
        if (wolves.size() > 1) {
            List<Member> wolvesList = new ArrayList<Member>();
            for (Player player : wolves) {
                wolvesList.add(player.getMember());
            }
            getWolfChannel(guildID).sendMessage(getTheme("WOLVES_INSTRUCTIONS", MessType.NARRATION, guildID, null, intTimeSet, wolvesList, null).build()).queue();
        } else if (wolves.size() == 1) {
            getWolfChannel(guildID).sendMessage(getTheme("WOLF_INSTRUCTIONS", MessType.NARRATION, guildID, intTimeSet).build()).queue();
        }
        getWolfChannel(guildID).sendMessage((listAlive(guildID, null).build())).queue();

        //start timers
        addTimer(guildID, scheduler.schedule(new TenSecWarning(guildID, timerCount), intTimeSet - 11, TimeUnit.SECONDS));
        addTimer(guildID, scheduler.schedule(new WereTask(guildID, timerCount), intTimeSet, TimeUnit.SECONDS));
    }

    private void setPermissions(Long guildID) {
        //TODO Change permissions as needed
    }

    /**
     * List alive embed builder.
     *
     * @param guildID the guild id
     * @param embed   the embed
     * @return the embed builder
     */
    public EmbedBuilder listAlive(Long guildID, EmbedBuilder embed) {
        EmbedBuilder embedRoleList;
        if (embed == null) {
            embedRoleList = new EmbedBuilder();
        } else {
            embedRoleList = embed;
        }
        embedRoleList.setAuthor("Player list", null, null);
        String list = "";
        for (Player player : gamesPlayerLists.get(guildID).getPlayerListByState(PlayerState.ALIVE)) {
            if (list.equals("")) {
                list = player.getPlayerNo() + " " + player.getEffectiveName();
            } else {
                list = list + System.lineSeparator() + player.getPlayerNo() + " " + player.getEffectiveName();
            }
        }
        embedRoleList.addField(getPlayerStateString(PlayerState.ALIVE, guildID), list, true);

        List<Player> playerList = gamesPlayerLists.get(guildID).getPlayerListByState(PlayerState.DEAD);
        if (playerList.size() > 0) {
            list = "";
            for (Player player : playerList) {
                if (list.equals("")) {
                    list = player.getEffectiveName() + " " + getRoleString(guildID, player.getRole());
                } else {
                    list = list + System.lineSeparator() + player.getEffectiveName() + " " + getRoleString(guildID, player.getRole());
                }
            }
            embedRoleList.addField(getPlayerStateString(PlayerState.DEAD, guildID), list, true);
        }

        playerList = gamesPlayerLists.get(guildID).getPlayerListByState(PlayerState.FLED);
        if (playerList.size() > 0) {
            list = "";
            for (Player player : playerList) {
                if (list.equals("")) {
                    list = player.getEffectiveName() + " " + getRoleString(guildID, player.getRole());
                } else {
                    list = list + System.lineSeparator() + player.getEffectiveName() + " " + getRoleString(guildID, player.getRole());
                }
            }
            embedRoleList.addField(getPlayerStateString(PlayerState.FLED, guildID), list, true);
        }
        Integer numberWolvesAlive = gamesPlayerLists.get(guildID).getNumberOfPlayerByRolePlayerState(Role.WOLF, PlayerState.ALIVE);
        if (numberWolvesAlive > 1) {
            embedRoleList.addField("", "There are " + numberWolvesAlive + " " +
                    getThemeText(guildID, "MANY_WOLVES") + ".", false);
        } else {
            embedRoleList.addField("There is just one ", getThemeText(guildID, "ONEWOLF"), false);
        }

        return embedRoleList;
    }

    private void startDayTime(Long guildID, EmbedBuilder embedBuilder) {
        //reset votes
        gamesPlayerLists.get(guildID).resetVotes();
        //stop dying voice from being saved
        if (!gamesPlayerLists.get(guildID).isDyingVoiceEmpty()) {
            gamesPlayerLists.get(guildID).clearDyingVoice();
        }
        //TODO Remove permission from the person who didn't use their dying breath

        //display people still alive.. STILL ALIVEEEE (Sings along to Still Alive by Jonathan Coulton)
        //getTownChannel(guildID).sendMessage((listAlive(guildID, null).build())).queue();
        //TODO change Permissions to allow people to talk in town channel

        //figure out Daytime
        Integer noPlayer = gamesPlayerLists.get(guildID).getNumberOfPlayerByState(PlayerState.ALIVE);
        int intTimeSet = gameList.get(guildID).getDayTimeX() * noPlayer;
        int maxDayTime = gameList.get(guildID).getMaxDayTime();
        if (intTimeSet > maxDayTime) {
            intTimeSet = maxDayTime;
        }

        //Send Daytime Theme Message DAY_TIME
        MessageEmbed mess;
        if (embedBuilder != null) {
            mess = getTheme("DAY_TIME", MessType.NARRATION, guildID, null, intTimeSet, null, embedBuilder).build();
        } else {
            mess = getTheme("DAY_TIME", MessType.NARRATION, guildID, intTimeSet).build();
        }
        getTownChannel(guildID).sendMessage(mess).queue();

        //Start Timer until Vote time
        addTimer(guildID, scheduler.schedule(new WereTask(guildID, timerCount), intTimeSet, TimeUnit.SECONDS));
    }

    /**
     * Leave game.
     *
     * @param guildID  the guild id
     * @param playerNo the author
     */
    public boolean leaveGame(Long guildID, Integer playerNo) {
        Player kickPlayer = gamesPlayerLists.get(guildID).getPlayerByPlayerID(playerNo);

        if (kickPlayer == null) {
            return false;
        } else {
            leaveGame(guildID, kickPlayer.getMember());
            return true;
        }

    }

    /**
     * Leave game.
     *
     * @param guildID the guild id
     * @param author  the author
     */
    public void leaveGame(Long guildID, Member author) {
        if (!isPlaying(guildID)) {
            if (gamesPlayerLists.get(guildID).hasPlayer(author.getUser().getIdLong())) {
                Long userID = author.getUser().getIdLong();
                if (getGameChannel(guildID).equals(GameState.GAMESTART)) {
                    gamesPlayerLists.get(guildID).removePlayer(userID);
                    getTownChannel(guildID).sendMessage(getTheme("FLEE", MessType.NARRATION, guildID, author, gamesPlayerLists.get(guildID).getPlayerSize()).build()).queue();
                } else {
                    if (gamesPlayerLists.get(guildID).getPlayerState(userID).equals(PlayerState.ALIVE)) {
                        gamesPlayerLists.get(guildID).playerFled(userID);
                        getTownChannel(guildID).sendMessage(getTheme("FLEE_ROLE", MessType.NARRATION, guildID, author).build()).queue();
                        checkWin(guildID);
                    }
                }
            }
        }

    }


    private class TenSecWarning implements Runnable {
        private Long guildID;
        private Long taskID;

        /**
         * Instantiates a new Ten sec warning.
         *
         * @param guildID the guild id
         * @param taskID  the task id
         */
        public TenSecWarning(Long guildID, Long taskID) {
            this.guildID = guildID;
            this.taskID = taskID;
        }

        @Override
        public void run() {
            System.out.println("TenSecPing");
            removeTimer(guildID, taskID);
            if (gameList.containsKey(guildID)) {
                switch (gameList.get(guildID).getGameState()) {
                    case IDLE:
                        break;
                    case GAMESTART:
                        getTownChannel(guildID).sendMessage(getTheme("TEN_WARNING_JOIN", MessType.GAME, guildID).build()).queue();
                        break;
                    case DAYTIME:
                        break;
                    case VOTETIME:
                        getTownChannel(guildID).sendMessage(getTheme("TEN_WARNING_VOTE", MessType.GAME, guildID).build()).queue();
                        break;
                    case NIGHTTIME:
                        List<Player> remindPlayer = gamesPlayerLists.get(guildID).getPlayerListByRolePlayerState(Role.SEER, PlayerState.ALIVE);
                        //Remind seer
                        for (Player player : remindPlayer) {
                            sendPrivateMessage(getTheme("TEN_WARNING_WOLF", MessType.GAME, guildID).build(), player);
                        }
                        getWolfChannel(guildID).sendMessage(getTheme("TEN_WARNING_WOLF", MessType.GAME, guildID).build()).queue();
                        break;
                    default: //Incorrect gameStatus
                        break;
                }

            } else {
                System.err.println("Empty GameState in TenSecRun Timer");
            }

        }
    }

    private class RulesRefresh implements Runnable {
        /**
         * The Guild id.
         */
        private Long guildID;

        /**
         * Instantiates a new Rules refresh.
         *
         * @param guildID the guild id
         */
        public RulesRefresh(Long guildID) {
            this.guildID = guildID;
        }
        @Override
        public void run() {
            isRuleSentList.put(guildID, false);
        }
    }

    private class WereTask implements Runnable {
        private Long guildID;
        private Long taskID;

        /**
         * Instantiates a new Were task.
         *
         * @param guildID the guild id
         * @param taskID  the task id
         */
        public WereTask(Long guildID, Long taskID) {
            this.guildID = guildID;
            this.taskID = taskID;
        }

        @Override
        public void run() {
            //System.out.println("GameState Change");
            removeTimer(guildID, taskID);
            if (gameList.containsKey(guildID)) {
                switch (gameList.get(guildID).getGameState()) {
                    case IDLE:
                        break;
                    case GAMESTART:
                        if (gamesPlayerLists.get(guildID).getPlayerSize() < gameList.get(guildID).getMinPlayers()) {
                            //NOT-ENOUGHT - Theme ID
                            getTownChannel(guildID).sendMessage(getTheme("NOT_ENOUGH", MessType.GAME, guildID).build()).queue();
                            setGameState(guildID, GameState.IDLE);
                            break;
                        }
                        //Set gameState first to prevent people joining while roles are being sent out
                        setGameState(guildID, GameState.DAYTIME);

                        getTownChannel(guildID).sendMessage("Sending Roles, please wait.").queue();
                        gamePermission(guildID); //Set up permissions

                        setRoles(guildID); //Set everyone roles
                        dbMan.sqlIncThemeCount(themeList.get(guildID).getThemeID());
                        //Nothing more to do until all the roles have been recieved
                        break;
                    case DAYTIME:
                        setGameState(guildID, GameState.VOTETIME);
                        //Increment round number
                        gameList.get(guildID).incGameRound();
                        //Start Vote Countdown!
                        startVoteTime(guildID);

                        break;
                    case VOTETIME:
                        setGameState(guildID, GameState.NIGHTTIME);
                        tallyVotes(guildID);

                        //If gamestate has changed after tallyVote, break out (into a dance party dance party)
                        if (!getWerewolfGameState(guildID).equals(GameState.NIGHTTIME)) break;

                        //If we haven't won, move onto Nighttime
                        if (!checkWin(guildID))
                            startNightTime(guildID, null);
                        break;

                    case NIGHTTIME:
                        setGameState(guildID, GameState.DAYTIME);
                        endNightTime(guildID); //Do any wolf kills, seer see'ing, etc
                        //remove dying voice

                        if (!checkWin(guildID)) {
                            startDayTime(guildID, null); //if game hasn't been won, move onto daytime
                        }


                        break;
                    default: //Incorrect gameStatus
                        break;
                }
            } else {
                System.err.println("Missing GameState in WereTask");
            }
        }
    }


}