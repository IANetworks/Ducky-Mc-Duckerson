package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.Map;

public class ToggleEventCS extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public ToggleEventCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();

        if (hasPermission(author)) {
            Boolean newState = !dbMan.isEventOn(guildID);
            try {
                dbMan.setEventOn(guildID, newState);//Toggle state
                channel.sendMessage("Events has now been turned " + stringOnOff(newState)).queue();
            } catch (SQLException e) {
                channel.sendMessage("Oh ow ow, I did a thing, inform Abby please").queue();
            }
        }
    }

    private String stringOnOff(Boolean exclusive) {
        return exclusive ? "On" : "Off";
    }

    @Override
    public String help(Long guildID) {
        return "Toggles event on or off";
    }
}
