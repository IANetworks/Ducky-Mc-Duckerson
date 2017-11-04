package bot;

import bot.CommandStructure.*;
import bot.database.manager.DatabaseManager;
import bot.eventmanager.EventManager;
import net.dv8tion.jda.bot.entities.ApplicationInfo;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RestAction;
import werewolf.GameState;
import werewolf.Werewolf;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;


/**
 * The type Event listener.
 */
public class EventListener extends ListenerAdapter {
    /**
     * The Container.
     */
    SharedContainer container = new SharedContainer();
    /**
     * The Db man.
     */
    DatabaseManager dbMan;
    /**
     * The Bot admin.
     */
    String botAdmin;
    /**
     * The Bot owner.
     */
    User botOwner; //We'll hold the botOwner so we don't have to keep asking Discord for this
    /**
     * The Cmd list.
     */
    Map<String, CommandStructure> cmdList = new HashMap<String, CommandStructure>();
    /**
     * The Priv cmd list.
     */
    Map<String, CommandStructure> privCmdList = new HashMap<String, CommandStructure>();
    private Integer commandID = null;

    /**
     * Instantiates a new Event listener.
     *
     * @param dbMan the db man
     */
    public EventListener(DatabaseManager dbMan) {
        selfStart(dbMan, null);
    }

    /**
     * Instantiates a new Event listener.
     *
     * @param dbMan    the db man
     * @param botAdmin the bot admin
     */
    public EventListener(DatabaseManager dbMan, String botAdmin) {
        selfStart(dbMan, botAdmin);
    }

    private void selfStart(DatabaseManager dbMan, String botAdmin) {
        this.dbMan = dbMan;
        this.botAdmin = botAdmin;

    }

    private void setupCommandList(ApplicationInfo info) {
        botOwner = info.getOwner();
        container.dbMan = dbMan;
        container.botAdmin = botAdmin;
        container.botOwner = botOwner;
        container.ww = new Werewolf(dbMan, info.getJDA().getSelfUser());

        //HTMLParse.get_instance().CalenderStart();

        String name = "set_prefix";
        cmdList.put(name, new SetPrefixCS(container, name, 1, 1));

        name = "set_level_for_user";
        cmdList.put(name, new SetPermissionByUserCS(container, name, 2, 1));

        name = "set_command_level";
        cmdList.put(name, new SetCommandLevelCS(container, name, 3, 1));

        name = "profile";
        cmdList.put(name, new ProfileCS(container, name, 4, 999));

        name = "preload";
        cmdList.put(name, new PreloadCS(container, name, 5, 1));

        name = "set_level_for_role";
        cmdList.put(name, new SetPermissionsByRoleCS(container, name, 6, 1));

        name = "help";
        cmdList.put(name, new HelpCS(container, name, 7, 999));

        name = "iam";
        cmdList.put(name, new SelfRolesCS(container, name, 8, 999));

        name = "self_assign_role";
        cmdList.put(name, new SetSelfRoleCS(container, name, 9, 1));

        name = "list_self_roles";
        cmdList.put(name, new ListSelfRolesCS(container, name, 10, 999));

        name = "remove_self_assign_role";
        cmdList.put(name, new RemoveSelfRoleCS(container, name, 11, 1));

        name = "set_self_assign_group";
        cmdList.put(name, new SetSelfRoleGroupCS(container, name, 12, 1));

        name = "toggle_group_exclusive";
        cmdList.put(name, new SetSelfRoleGroupExculsive(container, name, 13, 1));

        name = "remove_self_assign_group";
        cmdList.put(name, new RemoveSelfRoleGroup(container, name, 14, 999));

        name = "join";
        cmdList.put(name, new WerewolfJoinCS(container, name, 15, 999));

        name = "start_ww";
        cmdList.put(name, new WerewolfStartCS(container, name, 16, 999));

        name = "kill";
        cmdList.put(name, new WerewolfKillCS(container, name, 17, 999));

        name = "vote";
        cmdList.put(name, new WerewolfVoteCS(container, name, 18, 999));

        name = "toggle_werewolf";
        cmdList.put(name, new SetWerewolfOnOffCS(container, name, 20, 1));

        name = "set_game_channel";
        cmdList.put(name, new SetGameChannelCS(container, name, 21, 1));

        name = "alive";
        cmdList.put(name, new WerewolfAliveCS(container, name, 22, 1));

        name = "kick_player";
        cmdList.put(name, new WerewolfKickPlayerCS(container, name, 23, 1));

        name = "flee";
        cmdList.put(name, new WerewolfLeaveCS(container, name, 24, 999));

        name = "theme";
        cmdList.put(name, new WerewolfThemeDetailCS(container, name, 25, 999));

        name = "list_theme";
        cmdList.put(name, new WerewolfListThemeCS(container, name, 26, 999));

        name = "stop_ww";
        cmdList.put(name, new WerewolfStopCS(container, name, 27, 1));

        name = "unflip";
        cmdList.put(name, new UnflipCS(container, name, 28, 999));

        name = "tableflip";
        cmdList.put(name, new TableFlipCS(container, name, 29, 999));

        name = "toggle_delete";
        cmdList.put(name, new DeleteCommandCS(container, name, 30, 1));

        name = "toggle_event";
        cmdList.put(name, new ToggleEventCS(container, name, 31, 1));

        name = "set_event";
        cmdList.put(name, new SetEventChannel(container, name, 32, 1));

        //name = "buy";
        //cmdList.put(name, new StoreCS(container, name, 33, 999));

        //********* PrivateMessage Commands *********//
        name = "see";
        privCmdList.put(name, new WerewolfSeeCS(container, name, 19, 999));

    }

    public void onGenericEvent(Event event) {
        EventManager thisEvent = new EventManager();
        thisEvent.onJDAEvent(event);
    }


    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        RestAction<ApplicationInfo> ra = jda.asBot().getApplicationInfo();
        //fetch botOwner;
        Consumer<ApplicationInfo> callback = (info) -> setupCommandList(info);
        ra.queue(callback);
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {

        Long guildID = event.getGuild().getIdLong();
        try {
            dbMan.setNewPermissionNames(guildID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //We Want To See All Users Joining the server(Called Guilds by Discord, why, I dunno)
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Long guildID = guild.getIdLong();
        String name = dbMan.getLoggingChannel(guildID);
        if (dbMan.isLoggingOn(guildID) || name != null) {
            List<TextChannel> ltc = guild.getTextChannelsByName(name, true);

            if (ltc.isEmpty()) {
                System.out.printf("Found no text channels with event-log");
                return;
            }

            for (TextChannel tc : ltc) {
                tc.sendMessage("Member `" + member.getEffectiveName() + "` has left " + guild.getName() + ".").queue();
            }
        }
    }

    //Users leaving server
    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Long guildID = guild.getIdLong();
        String name = dbMan.getLoggingChannel(guildID);
        if (dbMan.isLoggingOn(guildID) || name != null) {
            List<TextChannel> ltc = guild.getTextChannelsByName(name, true);

            if (ltc.isEmpty()) {
                System.out.printf("Found no text channels with event-log");
                return;
            }

            for (TextChannel tc : ltc) {
                tc.sendMessage("Member `" + member.getEffectiveName() + "` has left " + guild.getName() + ".").queue();
            }
        }
    }


    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Member author = event.getMember(); //User who sent message, member of guild
        if (author == null) return; //We ignore messages without any authors, most likely from webhooks
        Long userID = author.getUser().getIdLong();
        MessageChannel channel = event.getChannel();
        Message message = event.getMessage(); //Message recieved
        String msg = message.getContent(); // String readable content of message
        Guild guild = event.getGuild(); //Get info about the server this message is recieved on
        Long guildID = guild.getIdLong(); //guild unique id

        String guildPrefix = "!"; //Command prefix, Default to ! <- break this out?

        if (dbMan.getPrefix(guildID) != null) {
            guildPrefix = dbMan.getPrefix(guildID);
        }

        //Check to make sure our commands are setup (async can be a bitch)

        //Check Prefix
        if (msg.length() > 0) {
            String msgPrefix = msg.substring(0, guildPrefix.length());
            String msgCommand = msg.substring(guildPrefix.length()).toLowerCase();

            if (msgPrefix.equals(guildPrefix)) {
                if (cmdList.isEmpty()) {
                    //Our commands list have not setup yet, we're still waiting for infomation from Discord
                    channel.sendMessage("My Command List has not been initiated yet. Still waiting on information from Discord. (If this taking more than a minute, there's something wrong)").queue();
                } else {
                    //Loop through our commands
                    for (String commandName : cmdList.keySet()) {
                        if (msgCommand.startsWith(commandName)) {
                            Integer cmdCharCount = guildPrefix.length() + commandName.length();
                            String parameters = msg.substring(cmdCharCount);

                            cmdList.get(commandName).execute(author, channel, message, parameters, cmdList);
                            if (deleteCommand(guildID)) {
                                message.delete().reason("Clearing commands").queue();
                            }
                            break; //We found a matching command, let break out of the loop
                        }
                    }
                }
            } else {
                //No prefix found, we'll look for table flip/unflip and inc counts for that user
                //Commands are ignored for currency checks
                if (msg.contains("(╯°□°）╯︵ ┻━┻")) {
                    try {
                        dbMan.incUserFlipped(guildID, userID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (msg.contains("┬─┬﻿ ノ( ゜-゜ノ)")) {
                    try {
                        dbMan.incUserUnflipped(guildID, userID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                //Special case, looking for someone who has "dying voice during a game
                if (container != null && container.ww != null) {
                    if (!container.ww.getWerewolfGameState(guildID).equals(GameState.IDLE)) {
                        container.ww.dyingVoice(guildID, author);
                    }
                }

                try {
                    if (dbMan.isCooldownOver(guildID, userID)) {
                        Random newRandom = new Random();
                        Integer gained = newRandom.nextInt((40 - 10) + 1) + 10; //10 to 40
                        dbMan.addUserBalance(guildID, userID, gained.longValue());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean deleteCommand(Long guildID) {
        return dbMan.getDeleteCommand(guildID);
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        //Member author = event.getMember(); //User who sent message, member of guild
        User author = event.getAuthor();
        MessageChannel channel = event.getChannel();
        Message message = event.getMessage(); //Message recieved
        String msg = message.getContent().trim().toLowerCase(); // String readable content of message
        //Guild guild = event.getGuild(); //Get info about the server this message is recieved on
        //Long guildID = guild.getIdLong(); //guild unique id
        if (msg.length() > 0) {
            if (privCmdList.isEmpty()) {
                channel.sendMessage("My Command List has not been initiated yet. Still waiting on information from Discord. (If this taking more than a minute, there's something wrong)").queue();
            } else {
                for (String commandName : privCmdList.keySet()) {
                    if (msg.startsWith(commandName)) {
                        Integer cmdCount = commandName.length();
                        String parameters = msg.substring(cmdCount);

                        privCmdList.get(commandName).execute(author, channel, message, parameters, privCmdList);
                    }

                }
            }
        }
    }

    /**
     * NOTE THE @Override!
     * This method is actually overriding a method in the ListenerAdapter class! We place an @Override annotation
     * right before any method that is overriding another to guarantee to ourselves that it is actually overriding
     * a method from a super class properly. You should do this every time you override a method!
     * <p>
     * As stated above, this method is overriding a hook method in the
     * {@link net.dv8tion.jda.core.hooks.ListenerAdapter ListenerAdapter} class. It has convience methods for all JDA events!
     * Consider looking through the events it offers if you plan to use the ListenerAdapter.
     * <p>
     * In this example, when a message is received it is printed to the console.
     *
     * @param event An event containing information about a {@link net.dv8tion.jda.core.entities.Message Message} that was
     *              sent in a channel.
     */

    //All Messages recieved, from Private channels (DM), Public Channels(server/guild), Groups (Client only, we're using bot account so we can't do groups)
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //These are provided with every event in JDA
        //JDA jda = event.getJDA();                       //JDA, the core of the api.


        //long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.

        String msg = message.getContent();              //This returns a human readable version of the Message. Similar to
        // what you would see in the client.
        boolean isBotAdminOwner = isBotAdminOwner(author);

        if (msg.equals("!!shutdown")) {
            //Make sure we have permission
            if (isBotAdminOwner) {
                if (container.ww != null)
                    container.ww.shutDown();
                if (HTMLParse.get_instance() != null)
                    HTMLParse.get_instance().ShutDown();
                author.openPrivateChannel().queue((channel) -> sendChannelMessageAndShutdown(channel, "Bye bye, I'm closing down"));
            }
        }
    }

    private boolean isBotAdminOwner(User author) {
        String userwithDiscriminator = author.getName() + "#" + author.getDiscriminator(); //the libarey don't include a readily used readable username with descriminator
        if (botOwner != null) {
            return (botAdmin != null && userwithDiscriminator.equals(botAdmin)) || (botOwner.getIdLong() == author.getIdLong());
        }

        return false;
    }


    /**
     * Send channel message and shutdown.
     *
     * @param channel the channel
     * @param message the message
     */
    public void sendChannelMessageAndShutdown(MessageChannel channel, String message) {
        Consumer<Message> callback = (response) -> response.getJDA().shutdown();
        channel.sendMessage(message).queue(callback);
    }


    public void onLineIn(TextChannel channel, String message) {
        if (!message.isEmpty()) {
            channel.sendMessage(message).queue();
        }

    }
}
