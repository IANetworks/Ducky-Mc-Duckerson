package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.sql.SQLException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The type Set prefix cs.
 */
public class SetPrefixCS extends CommandStructure {
    /**
     * Instantiates a new Set prefix cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetPrefixCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        //Check to see if we're either botAdminOwner or guild Owner
        Long guildID = author.getGuild().getIdLong();
        String curGuildPrefix = dbMan.getPrefix(guildID);

        if (hasPermission(author)) {
            //if we don't have any parameters, we're resetting to default
            if (parameters.isEmpty()) {
                if (!curGuildPrefix.equals("!")) {
                    //check to make sure we're actually changing a default
                    Consumer<Message> callback = (response) -> {
                        try {
                            dbMan.setPrefix("!", guildID);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            channel.sendMessage(i18n.localize(dbMan, channel, "command.set_prefix.error.sql1")).queue();
                        }
                    };
                    channel.sendMessage(i18n.localize(dbMan, channel, "command.set_prefix.reset")).queue(callback);
                }
            } else {
                parameters = parameters.trim();
                if (parameters.length() > 3) {
                    channel.sendMessage(i18n.localize(dbMan, channel, "command.set_prefix.error.too_long", parameters.length())).queue();
                } else {
                    final String pm = parameters;
                    Consumer<Message> callback = (response) -> {
                        try {
                            dbMan.setPrefix(pm, guildID);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            channel.sendMessage(i18n.localize(dbMan, channel, "command.set_prefix.error.sql2")).queue();
                        }
                    };
                    channel.sendMessage(i18n.localize(dbMan, channel, "command.set_prefix.set", parameters)).queue(callback);
                }
            }
        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return i18n.localize(dbMan, channel, "command.set_prefix.help", dbMan.getPrefix(guildID), commandName);

    }

}
