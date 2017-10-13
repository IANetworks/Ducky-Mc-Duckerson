package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The type Set self role cs.
 */
public class SetSelfRoleCS extends CommandStructure {

    /**
     * Instantiates a new Set self role cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetSelfRoleCS(SharedContainer container, String commandName, int commandID,
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

        if (hasPermission(author)) {
            Member selfMember = guild.getSelfMember();
            if (selfMember.hasPermission(Permission.MANAGE_ROLES)) {
                String[] paraList = parameters.trim().split(" ", 2);
                String roleName = "";
                if (isInteger(paraList[0])) {
                    groupID = Integer.valueOf(paraList[0]);
                    roleName = paraList[1];
                } else {
                    roleName = parameters.trim();
                }

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
                    }
                }

                if (selfAssignRole != null && selfMember.canInteract(selfAssignRole)) {
                    Long roleID = selfAssignRole.getIdLong();
                    if (dbMan.isRoleSelfAssignable(guildID, roleID)) {
                        channel.sendMessage("the role '" + selfAssignRole.getName() + "' is already self assignable").queue();
                    } else {

                        try {

                            if (dbMan.setNewSelfAssignableRole(guildID, selfAssignRole.getIdLong(), groupID)) {
                                channel.sendMessage("the role '" + selfAssignRole.getName() + "' is now self assignable").queue();
                            } else {
                                channel.sendMessage("the role '" + selfAssignRole.getName() + "' is already self assignable").queue();
                            }
                        } catch (SQLException e) {
                            channel.sendMessage("I've had a hiccup here, can't set " + selfAssignRole.getName() + " as self assignable.").queue();
                        }
                    }
                } else {
                    if (selfAssignRole == null) {
                        channel.sendMessage("I'm not able to assign that role.'").queue();
                    } else {
                        channel.sendMessage("I do not have enough power to modify '" + selfAssignRole.getName() + "'").queue();
                    }
                }
            } else {
                channel.sendMessage("I do not have Manage Roles permission.").queue();
            }
        }

    }

    @Override
    public String help(Long guildID) {
        return "set a role as self assignable, make sure the role name is spelt out correctly";
    }

}
