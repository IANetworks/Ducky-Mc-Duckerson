package bot.CommandStructure;

import java.util.Map;

import bot.SharedContainer;
import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class SetGreetMessageCS extends CommandStructure {

	public SetGreetMessageCS(SharedContainer container, String commandName, int commandID,
			int commandDefaultLevel) {
		super(container, commandName, commandID, commandDefaultLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters,
			Map<String, CommandStructure> commandList) {
		// TODO Auto-generated method stub

	}

	@Override
	public String help(Long guildID) {
		// TODO Auto-generated method stub
		return null;
	}

}
