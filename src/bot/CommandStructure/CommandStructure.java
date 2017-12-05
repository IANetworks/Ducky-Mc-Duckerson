package bot.CommandStructure;

import bot.SharedContainer;
import bot.database.manager.DatabaseManager;
import bot.items.ItemDatabase;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import werewolf.Werewolf;

import java.util.Map;

/**
 * The type Command structure. An abstract class for discord commands to override
 * <p>
 * Holds command related functions
 */
public abstract class CommandStructure {
    /**
     * The Command name. Defines the name used to call the command. e.g "SetPrefix"
     */
    public String commandName; //ItemDatabase the command name here.
    /**
     * The Command unique id defined at the commands creation. A command may have several commmand name,
     * but will be defined with a unique ID.
     * <p>
     * Example@
     * A SetPrefixCS, could have a set ID of 4, but could be set with "setprefix", "sp" "s_prefix"
     */
    public int commandID;
    /**
     * The Bot admin. Hold any bot admin defined in config. Allows for a second "bot owner"
     * If botAdmin is set, we'll hold it here since botAdmin and Owner have highest level (0)
     */
    public String botAdmin;
    /**
     * The Bot owner.
     * Reference botOwner is held here
     */
    public User botOwner; //
    /**
     * The Permission level.
     * Defined at object creation, the default permission level that user need to be to use a command. If no permission
     * level is defined in database, the default permission level is used.
     */
    public int permissionLevel; //Defaut permission level
    /**
     * The Databatebase Manager. Holds the Database object for Database interactions by any commands objects
     */
    DatabaseManager dbMan;
    /**
     * The Werewolf cbject. Holds Werewolf game interactions
     */
    Werewolf ww;

    /**
     * The Item Database.
     */
    ItemDatabase itemDB;

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public CommandStructure(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        this.dbMan = container.dbMan;
        this.permissionLevel = commandDefaultLevel;
        this.commandID = commandID;
        this.commandName = commandName;
        this.botAdmin = container.botAdmin;
        this.botOwner = container.botOwner;
        this.ww = container.ww;
        this.itemDB = container.itemDB;
    }

    /**
     * Tests a String for wether it's able to be parsed into a Integer, returns true
     * if it's able to be parsed, false otherwise.
     *
     * @param str to be tested for whether it can be parsed into an Integer
     * @return Returns true if String can be parsed into an Integer
     */
    protected static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }


    private boolean isBotAdminOwner(User author) {
        String userwithDiscriminator = author.getName() + "#" + author.getDiscriminator(); //the libarey don't include a readily used readable username with descriminator
        return (userwithDiscriminator.equals(botAdmin) && botAdmin != null) || (botOwner.getIdLong() == author.getIdLong());
    }

    /**
     * Returns the permission level that a given member has for their guild. Defaults 999, unless the Member has
     * a level set, is the server owner or bot owner/admin
     * <p>
     * Returns in order of highest level they have.
     * <p>
     * Bot owner/admin will always return 0
     * Server Owner will return 1, unless they are the bot owner/admin
     * Set Level unless they have the above
     * Default 999, if none of the above matches.
     *
     * @param author Member object for a their permission level
     * @return the permission level
     */
    protected Integer getPermissionLevel(Member author) {
        Integer userLevel = dbMan.getUserLevel(author.getGuild().getIdLong(), author.getUser().getIdLong(), author.getRoles());

        if (userLevel == null) {
            userLevel = 999; //default everyone level
        }

        if (author.isOwner()) {
            userLevel = 1; //guild owner
        }

        if (isBotAdminOwner(author.getUser())) {
            userLevel = 0; //bot Admin/Owner
        }

        return userLevel;
    }

    /**
     * Takes the given Member and figures out if the Member has permission to use the object based on
     * their level from getPermissionLevel and the commands level.
     * <p>
     * Command level is defined by their default level or if a different one is set, by that level
     *
     * @param author the Member to check permission on
     * @return true if Member has correct permission, false if not
     */
    protected boolean hasPermission(Member author) {
        Integer commandLevel = dbMan.getCommandLevel(author.getGuild().getIdLong(), commandID);

        boolean hasPermission = false;
        if (commandLevel == null) //No Custom permission was set, so we'll use the self assigned level
        {
            commandLevel = this.permissionLevel;
        }

        Integer userLevel = getPermissionLevel(author);

        if (userLevel <= commandLevel) //Is User level lower than command level? (lower number = higher ranked)
        {
            hasPermission = true;
        }

        return hasPermission;
    }

    /**
     * Check if a given user has permission for a given level. Used in situation to check that a member can set permissionn
     * and prevent them from setting permission beyond what they can give.
     *
     * @param author  Member to check they have permission for
     * @param levelID the level id to check a Membe against
     * @return true if Member has correct permission, false if not
     */
    protected boolean hasPermission(Member author, Integer levelID) {
        boolean hasPermission = false;
        if (levelID == null) //No Custom permission was set, so we'll use the self assigned level
        {
            levelID = this.permissionLevel;
        }

        Integer userLevel = getPermissionLevel(author);

        if (userLevel <= levelID) //Is User level lower than command level? (lower number = higher ranked)
        {
            hasPermission = true;
        }

        return hasPermission;
    }


    /**
     * Execute. Method is called in EvenListener when a Member object is avaiablie, such as a channel message from server
     *
     * @param author      the author
     * @param channel     the channel
     * @param message     the message
     * @param parameters  the parameters
     * @param commandList the command list
     */
    public void execute(Member author, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        execute(author, null, channel, message, parameters, commandList);
    }

    /**
     * Execute - Called when Member object is not avaiable. Such as in situation of private messages.
     *
     * @param authorUser  the author user
     * @param channel     the channel
     * @param message     the message
     * @param parameters  the parameters
     * @param commandList the command list
     */
    public void execute(User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        execute(null, authorUser, channel, message, parameters, commandList);
    }

    /**
     * Execute. Abstract method for all command objects to override and define their command behavioure.
     *
     * @param author      the author
     * @param authorUser  the author user
     * @param channel     the channel
     * @param message     the message
     * @param parameters  the parameters
     * @param commandList the command list
     */
    public abstract void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList);

    /**
     * Help string. Method called when a user requests help, either in a list or on a given command
     *
     * @param guildID the guild id
     * @return Description of the command object and how to use it
     */
    public abstract String help(Long guildID);
}
