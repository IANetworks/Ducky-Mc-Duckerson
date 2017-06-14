package bot;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public abstract class CommandStructure {
	public String cmdName; //Store the command name here.
	public int cmdID;
	public String botAdmin; //If botAdmin is set, we'll hold it here since botAdmin and Owner have highest level (0)
	public User botOwner; //We hold the botOwner here so we don't have to fetch it
	public int permissionLevel; //Defaut permission level
	DatabaseManager dbMan;
	
	public CommandStructure(DatabaseManager dbMan, String botAdmin, User botOwner, String cmdName, int cmdID, int permissionLevel)
	{
		this.dbMan = dbMan;
		this.permissionLevel = permissionLevel;
		this.cmdID = cmdID;
		this.cmdName = cmdName; 
		this.botAdmin = botAdmin;
		this.botOwner = botOwner;
	}
	
	private boolean isBotAdminOwner(User author) {
		String userwithDiscriminator = author.getName() + "#" + author.getDiscriminator(); //the libarey don't include a readily used readable username with descriminator
		return (userwithDiscriminator.equals(botAdmin) && botAdmin != null) || (botOwner.getIdLong() == author.getIdLong());
	}
	
	protected boolean hasPermission(Member author)
	{
		Integer cmdLvl = dbMan.getCommandLevel(author.getGuild().getIdLong(), cmdID);
		Integer usrLvl = dbMan.getUserLevel(author.getGuild().getIdLong(), author.getUser().getIdLong(), author.getRoles());
		
		boolean hasPermission = false;
		if(cmdLvl == null) //No Custom permission was set, so we'll use the self assigned level
		{
			cmdLvl = this.permissionLevel;
		}
		
		if(usrLvl == null)
		{
			usrLvl = 999; //default everyone level
		}
		
		if(author.isOwner())
		{
			usrLvl = 1; //guild owner
		}
		
		if(isBotAdminOwner(author.getUser()))
		{
			usrLvl = 0; //bot Admin/Owner
		}
		if(usrLvl <= cmdLvl) //Is User level lower than command level? (lower number = higher ranked)
		{
			hasPermission = true;
		}
		
		return hasPermission;
	}
	
	public void excute(Member author, MessageChannel channel)
	{
		this.excute(author, channel, null);
	}
	
	abstract void excute(Member author, MessageChannel channel, String parameters);
}
