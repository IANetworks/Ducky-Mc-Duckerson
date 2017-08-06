package bot.CommandStructure;

import java.util.Map;

import bot.SharedContainer;
import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import werewolf.Werewolf;

public abstract class CommandStructure {
	public String commandName; //Store the command name here.
	public int commandID;
	public String botAdmin; //If botAdmin is set, we'll hold it here since botAdmin and Owner have highest level (0)
	public User botOwner; //We hold the botOwner here so we don't have to fetch it
	public int permissionLevel; //Defaut permission level
	DatabaseManager dbMan;
	Werewolf ww;
	
	public CommandStructure(SharedContainer container, String commandName, int commandID, int commandDefaultLevel)
	{
		this.dbMan = container.dbMan;
		this.permissionLevel = commandDefaultLevel;
		this.commandID = commandID;
		this.commandName = commandName; 
		this.botAdmin = container.botAdmin;
		this.botOwner = container.botOwner;
	}
	
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
	
	protected Integer getPermissionLevel(Member author)
	{
		Integer userLevel = dbMan.getUserLevel(author.getGuild().getIdLong(), author.getUser().getIdLong(), author.getRoles());

		if(userLevel == null)
		{
			userLevel = 999; //default everyone level
		}
		
		if(author.isOwner())
		{
			userLevel = 1; //guild owner
		}
		
		if(isBotAdminOwner(author.getUser()))
		{
			userLevel = 0; //bot Admin/Owner
		}
		
		return userLevel;
	}
	
	protected boolean hasPermission(Member author)
	{
		Integer commandLevel = dbMan.getCommandLevel(author.getGuild().getIdLong(), commandID);
		
		boolean hasPermission = false;
		if(commandLevel == null) //No Custom permission was set, so we'll use the self assigned level
		{
			commandLevel = this.permissionLevel;
		}
		
		Integer userLevel =  getPermissionLevel(author);
		
		if(userLevel <= commandLevel) //Is User level lower than command level? (lower number = higher ranked)
		{
			hasPermission = true;
		}
		
		return hasPermission;
	}
	
	protected boolean hasPermission(Member author, Integer levelID)
	{		
		boolean hasPermission = false;
		if(levelID == null) //No Custom permission was set, so we'll use the self assigned level
		{
			levelID = this.permissionLevel;
		}
		
		Integer userLevel =  getPermissionLevel(author);
		
		if(userLevel <= levelID) //Is User level lower than command level? (lower number = higher ranked)
		{
			hasPermission = true;
		}
		
		return hasPermission;
	}
	
	
	public abstract void excute(Member author, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList);

	public abstract String help(Long guildID);
}
