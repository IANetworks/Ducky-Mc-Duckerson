package io.github.mannjamin.ducky;

import io.github.mannjamin.ducky.database.manager.DatabaseManager;
import io.github.mannjamin.ducky.eventmanager.EventManager;
import io.github.mannjamin.ducky.items.ItemDatabase;
import io.github.mannjamin.ducky.commandstructure.*;
import io.socket.client.Socket;
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
import io.github.mannjamin.ducky.werewolf.GameState;
import io.github.mannjamin.ducky.werewolf.Werewolf;

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
    Socket socket;

    /**
     * Localization manager
     */
    private I18N i18n;

    /**
     * Instantiates a new Event listener.
     *
     * @param dbMan the db man
     */
    public EventListener(DatabaseManager dbMan, Socket socket) {
        selfStart(dbMan, null, socket);
    }

    /**
     * Instantiates a new Event listener.
     *
     * @param dbMan    the db man
     * @param botAdmin the bot admin
     */
    public EventListener(DatabaseManager dbMan, String botAdmin, Socket socket) {
        selfStart(dbMan, botAdmin, socket);
    }

    private void selfStart(DatabaseManager dbMan, String botAdmin, Socket socket) {
        this.dbMan = dbMan;
        this.botAdmin = botAdmin;
        this.socket = socket;
        this.i18n = I18N.getInstance();
    }

    private void setupCommandList(ApplicationInfo info) {
        botOwner = info.getOwner();
        container.dbMan = dbMan;
        container.botAdmin = botAdmin;
        container.botOwner = botOwner;
        container.ww = new Werewolf(dbMan, info.getJDA().getSelfUser());
        container.itemDB = new ItemDatabase(dbMan);

        setupCommand(new SetPrefixCS(container, "set_prefix", 1, 1));
        setupCommand(new SetPermissionByUserCS(container, "set_level_for_user", 2, 1));
        setupCommand(new SetCommandLevelCS(container, "set_command_level", 3, 1));
        setupCommand(new ProfileCS(container, "profile", 4, 999));
        setupCommand(new PreloadCS(container, "preload", 5, 1));
        setupCommand(new SetPermissionsByRoleCS(container, "set_level_for_role", 6, 1));
        setupCommand(new HelpCS(container, "help", 7, 999));
        setupCommand(new SelfRolesCS(container, "iam", 8, 999));
        setupCommand(new SetSelfRoleCS(container, "self_assign_role", 9, 1));
        setupCommand(new ListSelfRolesCS(container, "list_self_roles", 10, 999));
        setupCommand(new RemoveSelfRoleCS(container, "remove_self_assign_role", 11, 1));
        setupCommand(new SetSelfRoleGroupCS(container, "set_self_assign_group", 12, 1));
        setupCommand(new SetSelfRoleGroupExculsive(container, "toggle_group_exclusive", 13, 1));
        setupCommand(new RemoveSelfRoleGroup(container, "remove_self_assign_group", 14, 999));
        setupCommand(new WerewolfJoinCS(container, "join", 15, 999));
        setupCommand(new WerewolfStartCS(container, "start_ww", 16, 999));
        setupCommand(new WerewolfKillCS(container, "kill", 17, 999));
        setupCommand(new WerewolfVoteCS(container, "vote", 18, 999));
        setupCommand(new SetWerewolfOnOffCS(container, "toggle_werewolf", 20, 1));
        setupCommand(new SetGameChannelCS(container, "set_game_channel", 21, 1));
        setupCommand(new WerewolfAliveCS(container, "alive", 22, 1));
        setupCommand(new WerewolfKickPlayerCS(container, "kick_player", 23, 1));
        setupCommand(new WerewolfLeaveCS(container, "flee", 24, 999));
        setupCommand(new WerewolfThemeDetailCS(container, "theme", 25, 999));
        setupCommand(new WerewolfListThemeCS(container, "list_theme", 26, 999));
        setupCommand(new WerewolfStopCS(container, "stop_ww", 27, 1));
        setupCommand(new UnflipCS(container, "unflip", 28, 999));
        setupCommand(new TableFlipCS(container, "tableflip", 29, 999));
        setupCommand(new DeleteCommandCS(container, "toggle_delete", 30, 1));
        setupCommand(new ToggleEventCS(container, "toggle_event", 31, 1));
        setupCommand(new SetEventChannel(container, "set_event", 32, 1));
        setupCommand(new StoreCS(container, "store", 33, 999));
        setupCommand(new StartCalendarCS(container, "start_event", 34, 1));
        setupCommand(new FetchCalendarCS(container, "fetch_event", 34, 1));
        setupCommand(new BuyCS(container, "buy", 35, 999));
        setupCommand(new GiveItemCS(container, "give_item", 36, 999));
        setupCommand(new ListUserInvCS(container, "inv", 37, 999));
        setupCommand(new UseItemCS(container, "use_item", 38, 999));
        setupCommand(new RemoveSelfRolesCS(container, "iamnot", 39, 999));
        setupCommand(new NewProfileCS(container, "new_profile", 40, 999));
        setupCommand(new AliasCS(container, "alias", 40, 1, 41, 999));


        //********* PrivateMessage Commands *********//
        setupPrivateCommand(new WerewolfSeeCS(container, "see", 19, 999));


        // Setup aliases
        try {
            dbMan.getAliases().forEach((alias, mapping) -> {
                AliasMappingCS command = new AliasMappingCS(container, alias, 41, 999);
                mapping.forEach((guildID, commandName) -> {
                    command.commandMapping.put(guildID, cmdList.get(commandName));
                });
                setupCommand(command);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setupCommand(CommandStructure command) {
        cmdList.put(command.commandName, command);
    }

    private void setupPrivateCommand(CommandStructure command) {
        privCmdList.put(command.commandName, command);
    }

    public void onGenericEvent(Event event) {
        EventManager thisEvent = new EventManager(socket);
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
                tc.sendMessage(i18n.localize(dbMan, tc, "member_joined_guild", member.getEffectiveName(), guild.getName())).queue();
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
                tc.sendMessage(i18n.localize(dbMan, tc, "member_left_guild", member.getEffectiveName(), guild.getName())).queue();
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
      String msg = message.getContentDisplay(); // String readable content of message
        Guild guild = event.getGuild(); //Get info about the server this message is recieved on
        Long guildID = guild.getIdLong(); //guild unique id

        String guildPrefix = dbMan.getPrefix(guildID);

        //Check to make sure our commands are setup (async can be a bitch)

        //Check Prefix
        if (msg.length() > 0) {
            String msgPrefix = msg.substring(0, guildPrefix.length());
            String msgFullCommand = msg.substring(guildPrefix.length()).toLowerCase();
            String msgCommand = msgFullCommand.split(" ", 2)[0];

            if (msgPrefix.equals(guildPrefix)) {
                if (cmdList.isEmpty()) {
                    //Our commands list have not setup yet, we're still waiting for infomation from Discord
                    channel.sendMessage(i18n.localize(dbMan, channel, "error.commandlist_not_initiated")).queue();
                } else {
                    if (cmdList.containsKey(msgCommand)) {
                        Integer cmdCharCount = guildPrefix.length() + msgCommand.length();
                        String parameters = msg.substring(cmdCharCount);
                        CommandStructure callCommand = cmdList.get(msgCommand);
                        callCommand.execute(author, channel, message, parameters, cmdList);

                        if (deleteCommand(guildID)) {
                          if (callCommand.commandID != 40)
                            message.delete().reason("Clearing commands").queue();
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
      String msg = message.getContentDisplay().trim().toLowerCase(); // String readable content of message
        //Guild guild = event.getGuild(); //Get info about the server this message is recieved on
        //Long guildID = guild.getIdLong(); //guild unique id
        if (msg.length() > 0) {
            if (privCmdList.isEmpty()) {
                channel.sendMessage(i18n.localize(dbMan, channel, "error.commandlist_not_initiated")).queue();
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
        JDA jda = event.getJDA();                       //JDA, the core of the api.


        //long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.

      String msg = message.getContentDisplay();              //This returns a human readable version of the Message. Similar to
        // what you would see in the client.
        boolean isBotAdminOwner = isBotAdminOwner(author);

        if (message.isFromType(ChannelType.TEXT)) {
            if (message.isMentioned(jda.getSelfUser())) {
                if (msg.contains("shutdown")) {
                    //Make sure we have permission
                    if (isBotAdminOwner) {
                        closeMeDown(author);
                    }
                }
            }
        } else if (message.isFromType(ChannelType.PRIVATE)) {
            if (msg.contains("shutdown")) {
                //Make sure we have permission
                if (isBotAdminOwner) {
                    closeMeDown(author);
                }
            }
        }


    }

    private void closeMeDown(User author) {
        if (container.ww != null)
            container.ww.shutDown();
        if (HTMLParse.get_instance() != null)
            HTMLParse.get_instance().ShutDown();
        author.openPrivateChannel().queue((channel) -> sendChannelMessageAndShutdown(channel, i18n.localize(dbMan, channel, "shutdown.success")));
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

    public void onConsoleMessage(TextChannel channel, Object[] args) {
        for (Object obj : args) {
            if (obj instanceof String) {
                String message = (String) obj;
                if (!message.isEmpty()) {
                    channel.sendMessage(message).queue();
                }
            }
        }

    }
}
