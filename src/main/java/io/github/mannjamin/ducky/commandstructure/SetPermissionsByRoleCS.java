package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The type Set permissions by role cs.
 */
public class SetPermissionsByRoleCS extends CommandStructure {

    /**
     * Instantiates a new Set permissions by role cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetPermissionsByRoleCS(SharedContainer container, String commandName, int commandID,
                                  int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();
        Guild guild = author.getGuild();

        if (hasPermission(author)) {
            //if we don't have any parameters, we're resetting to default
            if (parameters.isEmpty()) {
                //check to make sure we're actually changing a default
                channel.sendMessage(localize(channel, "command.set_level_for_role.error.mention_missing")).queue();
            } else {
                parameters = parameters.trim();
                Integer levelID = null;
                List<Role> mentionedRoles = message.getMentionedRoles();

                for (String para : parameters.split(" ")) {
                    if (isInteger(para)) {
                        levelID = Integer.valueOf(para);
                    }
                }
                if (levelID != null && levelID > 1) {
                    if (hasPermission(author, levelID - 1)) {
                        if (!mentionedRoles.isEmpty()) {
                            for (Role role : mentionedRoles) {
                                try {
                                    String userLevelName = dbMan.getLevelName(guildID, levelID);
                                    dbMan.setRoleLevel(guildID, levelID, role.getIdLong());
                                    channel.sendMessage(localize(channel, "command.set_level_for_role.success", userLevelName, role.getName())).queue();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    channel.sendMessage(localize(channel, "command.set_level_for_role.error.sql")).queue();
                                }
                            }
                        } else {
                            int firstSpace = parameters.indexOf(" "); //Ok we don't have an At Mention for Roles, let see if we can find a valid role name instead
                            if (firstSpace > -1) {
                                if (firstSpace >= parameters.length()) {
                                    channel.sendMessage(localize(channel, "command.set_level_for_role.error.syntax", dbMan.getPrefix(guildID) + commandName)).queue();
                                } else {
                                    String roleName = parameters.substring(firstSpace).trim();
                                    List<Role> searchRole = guild.getRolesByName(roleName, false);
                                    if (searchRole.isEmpty()) {
                                        channel.sendMessage(localize(channel, "command.set_level_for_role.error.unknown_role", roleName)).queue();
                                    } else {
                                        for (Role thisRole : searchRole) {
                                            try {
                                                String userLevelName = dbMan.getLevelName(guildID, levelID);
                                                dbMan.setRoleLevel(guildID, levelID, thisRole.getIdLong());
                                                channel.sendMessage(localize(channel, "command.set_level_for_role.success", userLevelName, thisRole.getName())).queue();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                                channel.sendMessage(localize(channel, "command.set_level_for_role.error.sql")).queue();
                                            }
                                        }
                                    }
                                }

                            } else {
                                channel.sendMessage(localize(channel, "command.set_level_for_role.error.syntax", dbMan.getPrefix(guildID) + commandName)).queue();
                            }
                        }
                    } else {
                        channel.sendMessage(localize(channel, "command.set_level_for_role.error.permission")).queue();
                    }
                } else if (levelID < 2) {
                    channel.sendMessage(localize(channel, "command.set_level_for_role.error.not_assignable", dbMan.getLevelName(guildID, levelID))).queue();
                } else {
                    channel.sendMessage(localize(channel, "command.set_level_for_role.error.level_missing")).queue();
                }
            }
        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.set_level_for_role.help", dbMan.getPrefix(guildID) + commandName);
    }

}