package bot.CommandStructure;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bot.SharedContainer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

/**
 * The type Set self role group cs.
 */
public class SetSelfRoleGroupCS extends CommandStructure {

    /**
     * Instantiates a new Set self role group cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetSelfRoleGroupCS(SharedContainer container, String commandName, int commandID,
			int commandDefaultLevel) {
		super(container, commandName, commandID, commandDefaultLevel);
	}

	@Override
	public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
						Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		Guild guild = author.getGuild();
		List<Role> mentionedRoles = message.getMentionedRoles(); //Get List of roles
		Role selfAssignRole = null;
		Integer groupID = 0; //0 is default group
		
		if(hasPermission(author))
		{
			Member selfMember = guild.getSelfMember();
			if(selfMember.hasPermission(Permission.MANAGE_ROLES))
			{
				String[] paraList = parameters.trim().split(" ", 2);
				String roleName = "";
				if(isInteger(paraList[0]) && paraList.length > 1)
				{
					groupID = Integer.valueOf(paraList[0]);
					roleName = paraList[1];
				} else {
					channel.sendMessage("This syntax looks wrong, Syntax: " + dbMan.getPrefix(guildID) + commandName + " [new group id] [role name]").queue();
					return;
				}
				
				
				if(mentionedRoles.size() > 0)
				{
					//grab the first one only
					selfAssignRole = mentionedRoles.get(0);
				} else {	
					List<Role> searchList = guild.getRolesByName(roleName, true);
					if(searchList.isEmpty()) {
						selfAssignRole = null;
						channel.sendMessage("Cannot find any role by the name '" + roleName + "'").queue();
					} else if(searchList.size() > 1) 
					{
						selfAssignRole = null;
						channel.sendMessage("I've found more than one role by the name: '" + roleName + "'. Try using the exact role name").queue();
					} else if(searchList.size() == 1) {
						selfAssignRole = searchList.get(0);
						if(selfMember.canInteract(selfAssignRole))
						{
							Long roleID = selfAssignRole.getIdLong();
							if(dbMan.isRoleSelfAssignable(guildID, roleID)) 
							{
								try {
									dbMan.setRoleGroup(guildID, roleID, groupID);
									channel.sendMessage("The Role '" + selfAssignRole.getName() + "' has been changed to group: " + groupID.toString()).queue();
								} catch (SQLException e) {
									channel.sendMessage("This hurts, please tell Mistress to fix me").queue();
								}
							} else {
							channel.sendMessage("the role '" + selfAssignRole.getName() + "' is not self assignable. I cannot change non-self assignable role's group.").queue();
							}
						} else {
							channel.sendMessage("I do not have enough power to modify '" + selfAssignRole.getName() + "'").queue();
						}
					}
				}
			} else {
				channel.sendMessage("I do not have Manage Roles permission.").queue();
			}
			
		}

	}

	@Override
	public String help(Long guildID) {
		return "Change a self assignable role to a different group Syntax: " + dbMan.getPrefix(guildID) + commandName + " [new group id] [role name]";
	}

}
