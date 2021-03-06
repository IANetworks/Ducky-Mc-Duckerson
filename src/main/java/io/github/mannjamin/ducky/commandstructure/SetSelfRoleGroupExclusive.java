package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.Map;

/**
 * The type Set self role group exclusive.
 */
public class SetSelfRoleGroupExclusive extends CommandStructure {

    /**
     * Instantiates a new Set self role group exclusive.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetSelfRoleGroupExclusive(SharedContainer container, String commandName,
                                     int commandID, int commandDefaultLevel) {
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
                        Boolean isExclusive = dbMan.isGroupExclusive(guildID, groupID);
                        try {
                            dbMan.setGroupExclusive(guildID, groupID, !isExclusive);
                            String newStatus = localize(channel, isExclusive ? "command.toggle_group_exclusive.exclusive" : "command.toggle_group_exclusive.not_exclusive");
                            channel.sendMessage(localize(channel, "command.toggle_group_exclusive.success", groupID, newStatus)).queue();
                        } catch (SQLException e) {
                            channel.sendMessage(localize(channel, "command.toggle_group_exclusive.error.sql")).queue();
                        }
                    } else {
                        channel.sendMessage(localize(channel, "command.toggle_group_exclusive.error.unknown_group")).queue();
                    }

                } else {
                    channel.sendMessage(localize(channel, "command.toggle_group_exclusive.error.syntax", dbMan.getPrefix(guildID) + commandName));
                }
            } else {
                channel.sendMessage(localize(channel, "command.toggle_group_exclusive.error.bot_permission_missing")).queue();
            }

        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.toggle_group_exclusive.help", dbMan.getPrefix(guildID) + commandName);
    }

}
