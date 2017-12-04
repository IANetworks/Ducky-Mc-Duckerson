package bot.CommandStructure;

import bot.SharedContainer;
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
public class SelfRolesCS extends CommandStructure {

    /**
     * Instantiates a new Self roles cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SelfRolesCS(SharedContainer container, String commandName, int commandID,
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

        if (hasPermission(author)) {
            if (mentionedRoles.size() > 0) {
                //grab the first one only
                selfAssignRole = mentionedRoles.get(0);
            } else {
                String roleName = parameters.trim();
                List<Role> searchList = guild.getRolesByName(roleName, true);
                if (searchList.isEmpty()) {
                    selfAssignRole = null;
                    channel.sendMessage(localize(channel, "command.iam.error.unknown_role", roleName)).queue();
                    message.addReaction("❌").queue();
                } else if (searchList.size() > 1) {
                    selfAssignRole = null;
                    channel.sendMessage(localize(channel, "command.iam.error.role_ambiguous", roleName)).queue();
                    message.addReaction("❌").queue();

                } else if (searchList.size() == 1) {
                    selfAssignRole = searchList.get(0);
                }

                Member selfMember = guild.getSelfMember();
                if (!selfMember.hasPermission(Permission.MANAGE_ROLES)) {
                    selfAssignRole = null;
                    channel.sendMessage(localize(channel, "command.iam.error.bot_permission_missing")).queue();
                    message.addReaction("❌").queue();
                }


                if (selfAssignRole != null) {
                    addRoleList.add(selfAssignRole);
                    Long roleID = selfAssignRole.getIdLong();
                    if (dbMan.isRoleSelfAssignable(guildID, roleID)) {
                        GuildController controller = guild.getController();

                        if (dbMan.isRoleExclusive(guildID, roleID)) {
                            List<Role> userRoles = author.getRoles();
                            Integer roleGroupID = dbMan.getRoleGroup(guildID, roleID);
                            HashSet<Long> roleList = dbMan.getListOfRolesByGroup(guildID, roleGroupID);

                            //The Role is exclusive so we need to remove any roles that falls under the same role group
                            for (Role userRole : userRoles) {
                                Long userRoleID = userRole.getIdLong();

                                if (!userRoleID.equals(roleID)) {
                                    if (roleList.contains(userRoleID)) {
                                        if (selfMember.canInteract(userRole)) {
                                            removeRoleList.add(userRole);
                                        } else {
                                            //TODO Edit this so it only output one message instead of a spam of messages.
                                            channel.sendMessage(localize(channel, "command.iam.error.permission_denied", userRole.getName(), author.getEffectiveName())).queue();
                                        }
                                    }
                                }

                            }
                        }

                        if (!removeRoleList.isEmpty()) {
                            message.addReaction("✅").queue();
                            controller.modifyMemberRoles(author, addRoleList, removeRoleList).queue(
                                success -> successRoleChange(success, channel, author, addRoleList, removeRoleList),
                                failure -> errorRoleChange(failure, channel, author, addRoleList, removeRoleList));
                        } else {
                            message.addReaction("✅").queue();
                            controller.addRolesToMember(author, addRoleList).queue(
                                success -> successRoleChange(success, channel, author, addRoleList),
                                failure -> errorRoleChange(failure, channel, author, addRoleList));
                        }
                    } else {
                        channel.sendMessage(localize(channel, "command.iam.error.not_self_assignable", roleName)).queue();
                        message.addReaction("❌").queue();
                    }
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

            channel.sendMessage(localize(channel, "command.iam.error.permission_error", member.getEffectiveName(), failure.getMessage())).queue(); //TODO LIST ROLES
        } else {
            channel.sendMessage(localize(channel, "command.iam.error.unknown_error", member.getEffectiveName(), failure.getClass().getSimpleName(), failure.getMessage())).queue(); //TODO List Roles - word this better
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
        return localize(channel, "command.iam.help");
    }

}
