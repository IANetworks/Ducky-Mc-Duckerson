package bot.CommandStructure;

import java.sql.SQLException;
import java.util.Map;
import java.util.function.Consumer;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class SetPrefixCS extends CommandStructure {
	public SetPrefixCS(DatabaseManager dbMan, String botAdmin, User botOwner, String commandName, int commandID,
			int commandDefaultLevel) {
		super(dbMan, botAdmin, botOwner, commandName, commandID, commandDefaultLevel);
	}
	
	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
		//Check to see if we're either botAdminOwner or guild Owner
		String curGuildPrefix = "!";
		Long guildID = author.getGuild().getIdLong();
		if(dbMan.getPrefix(guildID) != null)
		{
			curGuildPrefix = dbMan.getPrefix(guildID);
		}
		
		if(hasPermission(author))
		{
			//if we don't have any parameters, we're resetting to default
			if(parameters.isEmpty())
			{
				if(!curGuildPrefix.equals("!")) {
					//check to make sure we're actually changing a default
					Consumer<Message> callback = (response) -> {
						try {
							dbMan.setPrefix("!", guildID);
						} catch (SQLException e) {
							e.printStackTrace();
							channel.sendMessage("I had an error, am I helpful creator?").queue();
						}
					};
			    	channel.sendMessage("Resetting prefix to default").queue(callback); //Should I think about breaking this out to make localizion doable? 
			    	//I don't really expect this bot to get popular but this might make the bot popular thing along non-english servers..
				}
			} else {
				parameters = parameters.trim();
				if(parameters.length() > 3) {
					channel.sendMessage("I cannot set a prefix of 4 or more, I count " + String.valueOf(parameters.length())).queue();;
				} else {
					final String pm = parameters;
					Consumer<Message> callback = (response) -> {
						try {
							dbMan.setPrefix(pm, guildID);
						} catch (SQLException e) {
							e.printStackTrace();
							channel.sendMessage("I had an error setting Prefix, am I helpful here too creator?").queue();
						}
					};
			    	channel.sendMessage("Setting prefix to " + parameters).queue(callback); //Should I think about breaking this out to make localizion doable?
				}
			}
		}
		
	}

	@Override
	public String help(Long guildID) {
		return "Change the prefix used before a command. Prefix is limited to 3 characters, not applying any prefix will reset to default !" 
	+ dbMan.getPrefix(guildID) + commandName + " <prefix>";
		
	}

}
