package io.github.mannjamin.ducky.CommandStructure;

import io.github.mannjamin.ducky.HTMLParse;
import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.Map;

public class StartCalendarCS extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public StartCalendarCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {

        if (hasPermission(author)) {
            HTMLParse.get_instance().CalenderStart(channel);
        }
    }

    @Override
    public String help(Long guildID) {
        return "Fetch any events happening today";
    }
}
