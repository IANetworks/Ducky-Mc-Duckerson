package bot.CommandStructure;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

import bot.SharedContainer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * The type Remove self role group.
 */
public class RemoveSelfRoleGroup extends CommandStructure {

    /**
     * Instantiates a new Remove self role group.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public RemoveSelfRoleGroup(SharedContainer container, String commandName, int commandID,
			int commandDefaultLevel) {
		super(container, commandName, commandID, commandDefaultLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
						Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		Guild guild = author.getGuild();
		Integer groupID; //0 is default group
		
		if(hasPermission(author))
		{
			Member selfMember = guild.getSelfMember();
			if(selfMember.hasPermission(Permission.MANAGE_ROLES))
			{
				if(isInteger(parameters.trim()))
				{
					groupID = Integer.valueOf(parameters.trim());
					if(dbMan.hasGroup(guildID, groupID))
					{
						try {
							HashSet<Long> removedRoles = dbMan.removeGroup(guildID, groupID);
							String formatting = "";
							for(Long roleID : removedRoles)
							{
								if(formatting.equals(""))
								{
									formatting = guild.getRoleById(roleID).getName();
								} else {
									formatting = formatting + ", " + guild.getRoleById(roleID).getName(); 
								}
							}
							channel.sendMessage("The group " + groupID + "has been removed with the role(s): " + formatting).queue();
						
						} catch (SQLException e) {
							channel.sendMessage("Ah, I borked here. I need tinkering here.").queue();
						}
					} else {
						channel.sendMessage("I do not have any group by that ID").queue();
					}
					
				} else {
					channel.sendMessage("Something doesn't look right, the Syntax is: " + dbMan.getPrefix(guildID) + commandName + " [group id]");
				}
			} else {
				channel.sendMessage("I do not have Manage Roles permission.").queue();
			}
			
		}

	}

	@Override
	public String help(Long guildID) {
		return "set all roles in a group as not self assignable: " + dbMan.getPrefix(guildID) + commandName + " [group id]";
	}

}
