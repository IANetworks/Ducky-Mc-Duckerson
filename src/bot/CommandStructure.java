package bot;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public abstract class CommandStructure {
	public class SetPrefixCS {

	}

	public String cmdName;
	public String botAdmin;
	public User botOwner;
	
	public CommandStructure(String cmdName, String botAdmin, User botOwner)
	{
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
		//TODO Expand check to include custom permissions
		if(isBotAdminOwner(author.getUser()) || author.isOwner())
			return true;
		else {
			return false;
		}
	}
	
	public void excute(DatabaseManager dbMan, Member author, MessageChannel channel)
	{
		this.excute(dbMan, author, channel, null);
	}
	
	abstract void excute(DatabaseManager dbMan, Member author, MessageChannel channel, String parameters);
}
