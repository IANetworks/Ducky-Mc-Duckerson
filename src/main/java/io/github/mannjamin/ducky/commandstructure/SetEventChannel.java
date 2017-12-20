package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SetEventChannel extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public SetEventChannel(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();

        //First we check to see if we have a parameter
        if (parameters.isEmpty()) {
            channel.sendMessage("Missing a channel name").queue();
        } else {
            //check to see if we have an existing channel.
            parameters = parameters.trim();
            List<TextChannel> textChannelsByName = guild.getTextChannelsByName(parameters, true);
            if (textChannelsByName.size() == 0) {
                channel.sendMessage("There's no channels by that name").queue();
            } else if (textChannelsByName.size() > 1) {
                channel.sendMessage("There's many channels by that name, narrow the game channel.").queue();
            } else {
                //store channel
                try {
                    dbMan.setEventChannel(guildID, parameters);
                    channel.sendMessage(parameters + " has been set as " + guild.getName() + "'s Event Channel.").queue();
                } catch (SQLException e) {
                    channel.sendMessage("Unhandled booboobooo, contact Abby").queue();
                }
            }

        }
    }

    @Override
    public String help(Long guildID) {
        return "Set Event Channel";
    }
}
