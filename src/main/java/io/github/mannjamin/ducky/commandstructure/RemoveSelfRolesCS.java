package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * The type Self roles cs.
 */
public class RemoveSelfRolesCS extends CommandStructure {

    /**
     * Instantiates a new Self roles cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public RemoveSelfRolesCS(SharedContainer container, String commandName, int commandID,
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
        List<Role> addRoleList = new ArrayList<Role>();
        List<Role> removeRoleList = new ArrayList<Role>();
        Member selfMember = guild.getSelfMember();
        String roleName = parameters.trim();

        if (hasPermission(author)) {
            if (mentionedRoles.size() > 0) {
                //grab the first one only
                selfAssignRole = mentionedRoles.get(0);
            } else {
                List<Role> searchList = guild.getRolesByName(roleName, true);
                if (searchList.isEmpty()) {
                    selfAssignRole = null;
                    channel.sendMessage("Cannot find any role by the name '" + roleName + "'").queue();
                    message.addReaction("❌").queue();
                } else if (searchList.size() > 1) {
                    selfAssignRole = null;
                    channel.sendMessage("I've found more than one role by the name: '" + roleName + "'. Try using the exact role name").queue();
                    message.addReaction("❌").queue();

                } else if (searchList.size() == 1) {
                    selfAssignRole = searchList.get(0);
                }

                if (!selfMember.hasPermission(Permission.MANAGE_ROLES)) {
                    selfAssignRole = null;
                    channel.sendMessage("I cannot assign any roles due to lack of Manage Roles permission.").queue();
                    message.addReaction("❌").queue();
                }
            }

            if (selfAssignRole != null) {
                Long roleID = selfAssignRole.getIdLong();
                if (dbMan.isRoleSelfAssignable(guildID, roleID)) {
                    GuildController controller = guild.getController();
                    message.addReaction("✅").queue();

                    controller.removeSingleRoleFromMember(author, selfAssignRole).queue(
                        success -> successRoleChange(success, channel, author, addRoleList, removeRoleList),
                        failure -> errorRoleChange(failure, channel, author, addRoleList, removeRoleList));
                } else {
                    channel.sendMessage("Role: '" + roleName + "' cannot be self assigned.").queue();
                    message.addReaction("❌").queue();
                }
            }

        }

    }

    private void errorRoleChange(Throwable failure, MessageChannel channel, Member member, List<Role> addRoleList) {
        errorRoleChange(failure, channel, member, addRoleList, null);
    }

    private void successRoleChange(Void success, MessageChannel channel, Member member, List<Role> addRoleList) {
        successRoleChange(success, channel, member, addRoleList, null);
    }

    private void errorRoleChange(Throwable failure, MessageChannel channel, Member member, List<Role> addRoleList, List<Role> removeRoleList) {
        if (failure instanceof PermissionException) {
//            PermissionException pe = (PermissionException) failure;
//            Permission missingPermission = pe.getPermission();  //If you want to know exactly what permission is missing, this is how.
            //Note: some PermissionExceptions have no permission provided, only an error message!

            channel.sendMessage("PermissionError giving roles to [" + member.getEffectiveName()
                + "]: " + failure.getMessage()).queue(); //TODO LIST ROLES
        } else {
            channel.sendMessage("Unknown error while giving/removing roles to [" + member.getEffectiveName()
                + "]: " + "<" + failure.getClass().getSimpleName() + ">: " + failure.getMessage()).queue(); //TODO List Roles - word this better
        }
    }

    private void successRoleChange(Void success, MessageChannel channel, Member member, List<Role> addRoleList, List<Role> removeRoleList) {
        String listOfRolesRemoved = null;
        String listOfRolesAdded = null;

        if (removeRoleList != null) {
            if (!removeRoleList.isEmpty()) {
                for (Role thisRole : removeRoleList) {
                    if (listOfRolesRemoved == null) {
                        listOfRolesRemoved = thisRole.getName();
                    } else {
                        listOfRolesRemoved = listOfRolesRemoved + ", " + thisRole.getName();
                    }
                }
            }
        }

        if (addRoleList != null) {
            if (!addRoleList.isEmpty()) {
                for (Role thisRole : addRoleList) {
                    if (listOfRolesAdded == null) {
                        listOfRolesAdded = thisRole.getName();
                    } else {
                        listOfRolesAdded = listOfRolesAdded + ", " + thisRole.getName();
                    }
                }
            }
        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return "self assign a role, make sure the role name is spelt out correctly";
    }

}
