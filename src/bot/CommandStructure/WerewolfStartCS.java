package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import werewolf.GameState;

import java.util.Map;

/**
 * The type Werewolf start cs.
 */
public class WerewolfStartCS extends CommandStructure {

    /**
     * Instantiates a new Werewolf start cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public WerewolfStartCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);

    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
                        Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();

        if (!dbMan.isWerewolfOn(guildID)) {
            //Werewolf is turned off for this guild, so ignore the command
            return;
        }

        if (hasPermission(author)) {
            //Check to make sure we do not have a game running (null GameState or IDLE)
            GameState gs = ww.getWerewolfGameState(guildID);
            if (gs == null || gs == GameState.IDLE) {
                parameters = parameters.trim();
                if (isInteger(parameters)) {
                    Integer themeID = Integer.valueOf(parameters);
                    if (!ww.getIsRulesSent(guildID)) {
                        //TODO Remind players how to find out about the rules
                        ww.sendRuleToChannel(channel, guildID);
                    }

                    if (ww.startTheme(guildID, themeID)) {
                        ww.startGame(author.getGuild(), author, channel);
                    } else {
                        //TODO Alert players to invalid theme ID;
                        channel.sendMessage("Invalid theme ID, I don't know any theme by " + themeID + " Syntax: " + dbMan.getPrefix(guildID) + commandName + " [themeID]").queue();
                    }

                } else {
                    //We need an id to select a theme
                    channel.sendMessage("Start a game of werewolf, Syntax: " + dbMan.getPrefix(guildID) + commandName + " [themeID]").queue();
                }
            } else {
                //A Game is currently running, user will need to wait until the game is finished
                channel.sendMessage("There's a game already running, please wait for that game to finish").queue();
            }
        }

    }

    @Override
    public String help(Long guildID) {
        return "Start a game of werewolf, Syntax: " + dbMan.getPrefix(guildID) + commandName + " [themeID]";
    }

}
