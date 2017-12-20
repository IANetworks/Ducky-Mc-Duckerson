package io.github.mannjamin.ducky.CommandStructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.sql.SQLException;
import java.util.Map;

public class UnflipCS extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public UnflipCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();
        if (hasPermission(author)) {
            channel.sendMessage(author.getEffectiveName() + ": ┬─┬\uFEFF ノ( ゜-゜ノ)").queue();
            try {
                dbMan.incUserUnflipped(guildID, author.getUser().getIdLong());
            } catch (SQLException e) {
                channel.sendMessage("Ouchie.").queue();
            }
        }
    }

    @Override
    public String help(Long guildID) {
        return "unflip tables for you";
    }
}
