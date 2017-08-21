package bot.CommandStructure;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

/**
 * The type Set permissions by role cs.
 */
public class SetPermissionsByRoleCS extends CommandStructure {

    /**
     * Instantiates a new Set permissions by role cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetPermissionsByRoleCS(SharedContainer container, String commandName, int commandID,
			int commandDefaultLevel) {
		super(container, commandName, commandID, commandDefaultLevel);
	}
	
	@Override
	public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		Guild guild = author.getGuild();
		
		if(hasPermission(author))
		{
			//if we don't have any parameters, we're resetting to default
			if(parameters.isEmpty())
			{
				//check to make sure we're actually changing a default
		    	channel.sendMessage("Expecting an @role mention or valid role name").queue(); //Should I think about breaking this out to make localizion doable? 
		    	//I don't really expect this bot to get popular but this might make the bot popular thing along non-english servers..
			} else {
				parameters = parameters.trim();
				Integer levelID = null;
				List<Role> mentionedRoles = message.getMentionedRoles();
				
				for(String para : parameters.split(" "))
				{
					if(isInteger(para)) {
						levelID = Integer.valueOf(para);
					}
				}
				if(levelID != null && levelID > 1)
				{
					if(hasPermission(author, levelID-1))
					{
						if(!mentionedRoles.isEmpty())
						{
							for(Role role : mentionedRoles)
							{
								try {
									String userLevelName = dbMan.getLevelName(guildID, levelID);
									dbMan.setRoleLevel(guildID, levelID, role.getIdLong());
									channel.sendMessage("I've assigned level: " + userLevelName + " to the role: " + role.getName()).queue();
								} catch (SQLException e) {
									e.printStackTrace();
									channel.sendMessage("I had a booboo from setting roles, Mistress, fix me please!").queue();
								}
							}
						} else {
							int firstSpace = parameters.indexOf(" "); //Ok we don't have an At Mention for Roles, let see if we can find a valid role name instead
							if(firstSpace > -1)
							{
								if(firstSpace >= parameters.length())
								{
									channel.sendMessage("This doesn't look right.. !setlevelbyrole [level] [role name]").queue();
								} else {
									String roleName = parameters.substring(firstSpace).trim();
									List<Role> searchRole = guild.getRolesByName(roleName, false);
									if(searchRole.isEmpty())
									{
										channel.sendMessage("Cannot find any role by the name '" + roleName + "' Role name is case sensetive").queue();
									} else {
										for(Role thisRole : searchRole)
										{
											try {
												String userLevelName = dbMan.getLevelName(guildID, levelID);
												dbMan.setRoleLevel(guildID, levelID, thisRole.getIdLong());
												channel.sendMessage("I've assigned level: " + userLevelName + " to the role: " + thisRole.getName()).queue();
											} catch (SQLException e) {
												e.printStackTrace();
												channel.sendMessage("I had a booboo from setting roles, Mistress, fix me please!").queue();
											}
										}
									}
								}
								
							} else {
								channel.sendMessage("This doesn't look right.. !setlevelbyrole [level] [role name]").queue();
							}
						}
					} else {
						channel.sendMessage("You cannot set permission to equal or higher than your level").queue();
					}
				} else if (levelID < 2) {
					channel.sendMessage("I cannot assign "+ dbMan.getLevelName(guildID, levelID) + " to roles ").queue();
				} else {
					channel.sendMessage("I do not know what level to assign.").queue();
				}
			}
		}

	}

	@Override
	public String help(Long guildID) {
		return "Set Permissions by Roles, you can either @ mention role names or use role names, role names are case senetive " + dbMan.getPrefix(guildID) + commandName + " [level] [role name]";
		
	}

}