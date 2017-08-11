package bot.CommandStructure;

import java.util.Map;

import bot.SharedContainer;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * The type Help cs.
 */
public class HelpCS extends CommandStructure {

    /**
     * Instantiates a new Help Command.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public HelpCS(SharedContainer container, String commandName, int commandID,
			int commandDefaultLevel) {
		super(container, commandName, commandID, commandDefaultLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
						Map<String, CommandStructure> commandList) {
		
		Long guildID = author.getGuild().getIdLong();
		User user = author.getUser();
		
		user.openPrivateChannel().queue((privChannel) -> sendHelpList(privChannel, commandList, guildID));
			
	}
	
private void sendHelpList(PrivateChannel privChannel, Map<String, CommandStructure> commandList, Long guildID) {
			EmbedBuilder embed = new EmbedBuilder();
			int count = 0;
			for(String commandName : commandList.keySet())
			{
				String help = commandList.get(commandName).help(guildID);
				if(help == null) help = "";
				embed.addField(commandName, help, true);
				count++;
				if(count > 7)
				{
					count=0;
					privChannel.sendMessage(embed.build()).queue();
					embed = new EmbedBuilder();
				}
			}
			
			privChannel.sendMessage(embed.build()).queue();
	}

	
	@Override
	public String help(Long guildID) {
		
		return "returns a list of commands";
		// TODO Auto-generated method stub

	}

}
