package io.github.mannjamin.ducky.CommandStructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The type Remove self role cs.
 */
public class RemoveSelfRoleCS extends CommandStructure {

    /**
     * Instantiates a new Remove self role cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public RemoveSelfRoleCS(SharedContainer container, String commandName, int commandID,
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

        if (hasPermission(author)) {
            Member selfMember = guild.getSelfMember();
            if (selfMember.hasPermission(Permission.MANAGE_ROLES)) {
                String roleName = parameters.trim();

                if (mentionedRoles.size() > 0) {
                    //grab the first one only
                    selfAssignRole = mentionedRoles.get(0);
                } else {
                    List<Role> searchList = guild.getRolesByName(roleName, true);
                    if (searchList.isEmpty()) {
                        selfAssignRole = null;
                        channel.sendMessage("Cannot find any role by the name '" + roleName + "'").queue();
                    } else if (searchList.size() > 1) {
                        selfAssignRole = null;
                        channel.sendMessage("I've found more than one role by the name: '" + roleName + "'. Try using the exact role name").queue();
                    } else if (searchList.size() == 1) {
                        selfAssignRole = searchList.get(0);
                        if (selfMember.canInteract(selfAssignRole)) {
                            Long roleID = selfAssignRole.getIdLong();
                            if (dbMan.isRoleSelfAssignable(guildID, roleID)) {
                                try {
                                    dbMan.removeRole(guildID, roleID);
                                } catch (SQLException e) {
                                    channel.sendMessage("I do not like this, it not a good touch. Please tell Creator that there's an SQL issue.").queue();
                                }
                                channel.sendMessage("The role '" + selfAssignRole.getName() + "' is now not self assignable").queue();
                            } else {
                                channel.sendMessage("The role " + selfAssignRole.getName() + " is already not self assignable").queue();
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
        return "set all a role as not self assignable: " + dbMan.getPrefix(guildID) + commandName + " [role name]";
    }

}
