package bot.CommandStructure;

import java.util.Map;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * The type Set greet message cs.
 */
public class SetGreetMessageCS extends CommandStructure {

    /**
     * Instantiates a new Set greet message cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetGreetMessageCS(SharedContainer container, String commandName, int commandID,
			int commandDefaultLevel) {
		super(container, commandName, commandID, commandDefaultLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
						Map<String, CommandStructure> commandList) {
		// TODO Auto-generated method stub

	}

	@Override
	public String help(Long guildID) {
		// TODO Auto-generated method stub
		return null;
	}

}
