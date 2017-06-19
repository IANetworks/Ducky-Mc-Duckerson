package bot.CommandStructure;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class SetPermissionByUserCS extends CommandStructure {
	
	public SetPermissionByUserCS(DatabaseManager dbMan, String botAdmin, User botOwner, String commandName, int commandID,
			int commandDefaultLevel) {
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
		    	channel.sendMessage("Expecting an @mention").queue(); //Should I think about breaking this out to make localizion doable? 
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
					if(hasPermission(author, levelID-1))
					{
						
						if(!mentionedUsers.isEmpty()) 
						{
							for(User user : mentionedUsers)
							{
								try {
									String userLevelName = dbMan.getLevelName(guildID, levelID);
									dbMan.setUserLevel(guildID, levelID, user.getIdLong());
									channel.sendMessage("I've assigned level: " + userLevelName + " to the user: " + user.getAsMention()).queue();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									channel.sendMessage("Ouch, Setting users broke me.").queue();
								}
							}
						}
					} else {
						channel.sendMessage("You cannot set permission to equal or higher than your level").queue();
					}
						
				} else if (levelID < 2) {
					channel.sendMessage("I cannot assign the special level: "+ dbMan.getLevelName(guildID, levelID) + " to roles ").queue();
				} else {
					channel.sendMessage("I do not know what level to assign.").queue();
				}
			}
		}

	}

	@Override
	public String help(Long guildID) {
		return "Set Permissions by User, use @mention to assign permission to users, " + dbMan.getPrefix(guildID) + commandName + " [level] [@mention]";
	}

}
