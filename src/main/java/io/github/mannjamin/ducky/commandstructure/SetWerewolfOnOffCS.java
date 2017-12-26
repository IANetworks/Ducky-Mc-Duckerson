package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.Map;

/**
 * The type Set io.github.mannjamin.ducky.werewolf on off cs.
 */
public class SetWerewolfOnOffCS extends CommandStructure {
    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public SetWerewolfOnOffCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();

        if (hasPermission(author)) {
            Boolean newState = !dbMan.isWerewolfOn(guildID);
            try {
                dbMan.setWerewolfOn(guildID, newState);//Toggle state
                channel.sendMessage("Werewolf has now been turned " + stringOnOff(newState)).queue();
            } catch (SQLException e) {
                channel.sendMessage("Ouch ouch, I broke, inform Abby please").queue();
            }
        }
    }

    private String stringOnOff(Boolean exclusive) {
        return exclusive ? "On" : "Off";
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return "Toggles werewolf game on/off";
    }
}
