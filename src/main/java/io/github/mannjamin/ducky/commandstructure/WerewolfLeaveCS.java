package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.*;
import io.github.mannjamin.ducky.werewolf.GameState;

import java.util.Map;

/**
 * The type Werewolf leave cs.
 */
public class WerewolfLeaveCS extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public WerewolfLeaveCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();
        if (!dbMan.isWerewolfOn(guildID)) {
            //Werewolf is turned off for this guild, so ignore the command
            return;
        }
        if (hasPermission(author)) {
            if (!ww.getWerewolfGameState(guildID).equals(GameState.IDLE)) {
                ww.leaveGame(guildID, author);
            } else {
            }
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return "Run away from a game of werewolf";
    }
}
