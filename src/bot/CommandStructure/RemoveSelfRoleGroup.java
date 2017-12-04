package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

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
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
                        Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();
        Guild guild = author.getGuild();
        Integer groupID; //0 is default group

        if (hasPermission(author)) {
            Member selfMember = guild.getSelfMember();
            if (selfMember.hasPermission(Permission.MANAGE_ROLES)) {
                if (isInteger(parameters.trim())) {
                    groupID = Integer.valueOf(parameters.trim());
                    if (dbMan.hasGroup(guildID, groupID)) {
                        try {
                            HashSet<Long> removedRoles = dbMan.removeGroup(guildID, groupID);
                            String formatting = "";
                            for (Long roleID : removedRoles) {
                                // TODO: localize role names?
                                if (formatting.equals("")) {
                                    formatting = guild.getRoleById(roleID).getName();
                                } else {
                                    formatting = formatting + ", " + guild.getRoleById(roleID).getName();
                                }
                            }
                            channel.sendMessage(localize(channel, "command.remove_self_assign_group.success", groupID, formatting)).queue();

                        } catch (SQLException e) {
                            channel.sendMessage(localize(channel, "command.remove_self_assign_group.error.sql")).queue();
                        }
                    } else {
                        channel.sendMessage(localize(channel, "command.remove_self_assign_group.error.unknown_group")).queue();
                    }

                } else {
                    channel.sendMessage(localize(channel, "command.remove_self_assign_group.error.syntax", dbMan.getPrefix(guildID) + commandName));
                }
            } else {
                channel.sendMessage(localize(channel, "command.remove_self_assign_group.error.bot_permission_missing")).queue();
            }

        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.remove_self_assign_group.help", dbMan.getPrefix(guildID) + commandName);
    }

}
