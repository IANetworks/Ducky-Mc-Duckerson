package bot.CommandStructure;

import java.util.Map;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import werewolf.GameState;

public class WerewolfStartCS extends CommandStructure {

	public WerewolfStartCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
		super(container, commandName, commandID, commandDefaultLevel);
		
	}

	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters,
			Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		
		if(hasPermission(author))
		{
			//Check to make sure we do not have a game running (null GameState or IDLE)
			GameState gs = ww.getWerewolfGameState(guildID);
			if(gs == null || gs == GameState.IDLE)
			{
				parameters = parameters.trim();
				if(isInteger(parameters))
				{
					Integer themeID = Integer.valueOf(parameters);
					if(!ww.getIsRulesSent(guildID))
					{
						//TODO Remind players how to find out about the rules
						ww.sendRuleToChannel(channel, guildID);
					}
					
					if(ww.startTheme(guildID, themeID)) {
						//TODO start game - we have theme setup
						ww.startGame(author.getGuild(), author, channel);
					} else {
						//TODO Alert players to invalid theme ID;
						
					}
					
				} else {
					//We need an id to select a theme
				}
			} else {
				//A Game is currently running, user will need to wait until the game is finished
				//TODO alert player
			}
		}

	}

	@Override
	public String help(Long guildID) {
		return "Start a game of werewolf, Syntax: " + dbMan.getPrefix(guildID) + commandName + " [themeID]";
	}

}
