package bot.CommandStructure;

import java.sql.SQLException;
import java.util.Map;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class setCommandLevelCS extends CommandStructure {
	final static int commandID = 3;
	final static int commandDefaultLevel = 1;
	final static String commandName = "setcommandlevel";
	
	public setCommandLevelCS(DatabaseManager dbMan, String botAdmin, User botOwner) {
		super(dbMan, botAdmin, botOwner, commandName, commandID, commandDefaultLevel);
	}

	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		
		if(hasPermission(author))
		{
			if(parameters.isEmpty())
			{
				channel.sendMessage("I need a command name to assign a new level. Syntax is: setcommandlevel [command name] [level name]").queue();
			}
			String[] paraList = parameters.split(" ");
			String cmdID = paraList[1];
			if(isInteger(paraList[2])){
				Integer commandLevel = Integer.valueOf(paraList[2]);
				//TODO Check people's userlevel to make sure they can't assign a value higher than what they have.
				if(commandList.containsKey(cmdID))
				{
					try {
						dbMan.setCommandLevel(guildID, commandList.get(cmdID).cmdID, commandLevel);
						channel.sendMessage("I have assigned level: "+ commandLevel.toString() + " to this " + cmdID).queue();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						channel.sendMessage("I had a hicup, excuse me. Someone get Mistress to look at setting Command Level").queue();
					}
				} else {
					
				}	
			} else {
				channel.sendMessage("This doesn't look quiet right. Syntax is: setcommandlevel [command name] [level name]").queue();
			}
		}

	}

}
