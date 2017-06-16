package bot.CommandStructure;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

public class setPermissionCS extends CommandStructure {
	final static int commandID = 2;
	final static int commandDefaultLevel = 1;
	final static String commandName = "setpermission";
	
	public setPermissionCS(DatabaseManager dbMan, String botAdmin, User botOwner) {
		super(dbMan, botAdmin, botOwner, commandName, commandID, commandDefaultLevel);
	}
	
	
	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		
		if(hasPermission(author))
		{
			//if we don't have any parameters, we're resetting to default
			if(parameters.isEmpty())
			{
				//check to make sure we're actually changing a default
		    	channel.sendMessage("Expecting an @mention or role name").queue(); //Should I think about breaking this out to make localizion doable? 
		    	//I don't really expect this bot to get popular but this might make the bot popular thing along non-english servers..
			} else {
				parameters = parameters.trim();
				Integer levelID = null; 
				for(String para : parameters.split(" "))
				{
					if(isInteger(para)) {
						levelID = Integer.valueOf(para);
					}
				}
				if(levelID != null && levelID > 1)
				{
					List<User> mentionedUsers = message.getMentionedUsers();
					List<Role> mentionedRoles = message.getMentionedRoles();
					
					if(!mentionedUsers.isEmpty()) 
					{
						for(User user : mentionedUsers)
						{
							try {
								dbMan.setUserLevel(guildID, levelID, user.getIdLong());
								channel.sendMessage("I've assigned level: " + levelID.toString() + " to the user: " + user.getAsMention()).queue();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								channel.sendMessage("Ouch, Setting users broke me.").queue();
							}
						}
					} else if(!mentionedRoles.isEmpty())
					{
						for(Role role : mentionedRoles)
						{
							try {
								dbMan.setRoleLevel(guildID, levelID, role.getIdLong());
								channel.sendMessage("I've assigned level: " + levelID.toString() + " to the role: " + role.getName()).queue();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								channel.sendMessage("I had a booboo, Mistress, fix me please!").queue();
							}
						}
					}
				} else if (levelID < 2) {
					//TODO Make this use the actual names.
					channel.sendMessage("I cannot assign Guild Owner to Users or Role").queue();
				} else {
					channel.sendMessage("I do not know what level to assign.").queue();
				}
			}
		}

	}

}
