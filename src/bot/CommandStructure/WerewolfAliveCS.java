package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.*;
import werewolf.GameState;

import java.util.Map;

/**
 * The type Werewolf alive cs.
 */
public class WerewolfAliveCS extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public WerewolfAliveCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
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
            //check that this is from the game channel and during a game
            if (ww.getTownChannel(guildID).equals(channel)) {
                GameState gs = ww.getWerewolfGameState(guildID);
                if (!(gs == GameState.IDLE || gs == GameState.GAMESTART)) {
                    channel.sendMessage(ww.listAlive(guildID, null).build()).queue();
                } else {
                    channel.sendMessage("This only works during a game").queue();
                }
            }

        }
    }

    @Override
    public String help(Long guildID) {
        return "Display a list of people who are alive or dead";
    }
}
