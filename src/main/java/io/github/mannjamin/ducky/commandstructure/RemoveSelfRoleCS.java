package io.github.mannjamin.ducky.commandstructure;

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
                        channel.sendMessage(localize(channel, "command.remove_self_assign_role.error.unknown_role", roleName)).queue();
                    } else if (searchList.size() > 1) {
                        selfAssignRole = null;
                        channel.sendMessage(localize(channel, "command.remove_self_assign_role.error.ambiguous_role", roleName)).queue();
                    } else if (searchList.size() == 1) {
                        selfAssignRole = searchList.get(0);
                        if (selfMember.canInteract(selfAssignRole)) {
                            Long roleID = selfAssignRole.getIdLong();
                            if (dbMan.isRoleSelfAssignable(guildID, roleID)) {
                                try {
                                    dbMan.removeRole(guildID, roleID);
                                } catch (SQLException e) {
                                    channel.sendMessage(localize(channel, "command.remove_self_assign_role.error.sql")).queue();
                                }
                                channel.sendMessage(localize(channel, "command.remove_self_assign_role.success", selfAssignRole.getName())).queue();
                            } else {
                                channel.sendMessage(localize(channel, "command.remove_self_assign_role.error.already_self_assignable", selfAssignRole.getName())).queue();
                            }
                        } else {
                            channel.sendMessage(localize(channel, "command.remove_self_assign_role.error.permission_denied", selfAssignRole.getName())).queue();
                        }
                    }
                }
            } else {
                channel.sendMessage(localize(channel, "command.remove_self_assign_role.error.bot_permission_missing")).queue();
            }
        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.remove_self_assign_role.help", dbMan.getPrefix(guildID) + commandName);
    }

}
