package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.Map;

/**
 * The type Set self role group exculsive.
 */
public class SetSelfRoleGroupExculsive extends CommandStructure {

    /**
     * Instantiates a new Set self role group exculsive.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetSelfRoleGroupExculsive(SharedContainer container, String commandName,
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
                        Boolean isExculsive = dbMan.isGroupExculsive(guildID, groupID);
                        try {
                            dbMan.setGroupExculsive(guildID, groupID, !isExculsive);
                            channel.sendMessage("Group " + groupID + " is set to " + stringBlankNot(!isExculsive) + " exculsive").queue();
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

    private String stringBlankNot(Boolean exculsive) {
        return exculsive ? "" : "not";
    }

    @Override
    public String help(Long guildID) {
        return "Toggle a group to be exculsive or not exculsive Syntax: " + dbMan.getPrefix(guildID) + commandName + " [group id]";
    }

}
