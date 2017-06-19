package bot.CommandStructure;

import java.sql.SQLException;
import java.util.Map;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class PreloadCS extends CommandStructure {

	public PreloadCS(DatabaseManager dbMan, String botAdmin, User botOwner, String commandName, int commandID,
			int commandDefaultLevel) {
		super(dbMan, botAdmin, botOwner, commandName, commandID, commandDefaultLevel);
	}

	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters,
			Map<String, CommandStructure> commandList) {
		
		Long guildID = author.getGuild().getIdLong();

		try {
			boolean setDefault = dbMan.setNewPermissionNames(guildID);
			if(setDefault)
			{
				channel.sendMessage("Yes! I've preloaded some titles").queue(); //
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String help(Long GuildID) {
		return "Special Command for loading default values. Used when bot joins a server and the bot did not automantically assign the default values into database";
		
	}

}
