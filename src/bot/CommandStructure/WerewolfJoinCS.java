package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import werewolf.GameState;

import java.util.Map;

/**
 * The type Werewolf join cs.
 */
public class WerewolfJoinCS extends CommandStructure {
    /**
     * Instantiates a new Werewolf join cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public WerewolfJoinCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();

        if(!dbMan.isWerewolfOn(guildID))
        {
            //Werewolf is turned off for this guild, so ignore the command
            return;
        }

        if(hasPermission(author))
        {
            //check to make sure that we're in a gamestarted state
            GameState gs = ww.getWerewolfGameState(guildID);
            if(gs != null && gs == GameState.GAMESTART)
            {
                ww.joinGame(guildID, author);
            }
        }
    }

    @Override
    public String help(Long guildID) {
        return "Join a game of werewolf! Only works during the sign up phase of werewolf";
    }
}
