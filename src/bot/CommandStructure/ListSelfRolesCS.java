package bot.CommandStructure;

import java.util.Map;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class ListSelfRolesCS extends CommandStructure {

	public ListSelfRolesCS(DatabaseManager dbMan, String botAdmin, User botOwner, String commandName, int commandID,
			int commandDefaultLevel) {
		super(dbMan, botAdmin, botOwner, commandName, commandID, commandDefaultLevel);
	}

	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters,
			Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		Guild guild = author.getGuild();
		
		if(hasPermission(author))
		{
			Map<Long, Integer> listOfSelfRoles = dbMan.getListOfRoles(guildID);
			EmbedBuilder embed = new EmbedBuilder();
			
			String roleName = "";
			String groupID = "";
			String isExculsive = "";
			
			//TODO Limit how large a list can get
			for(Long roleID : listOfSelfRoles.keySet())
			{	
				if(roleName == "")
				{
					roleName = guild.getRoleById(roleID).getName();
					groupID = listOfSelfRoles.get(roleID).toString();
					isExculsive = stringYesNo(guildID, roleID);
				} else {
					roleName = roleName + System.lineSeparator() + guild.getRoleById(roleID).getName();
					groupID = groupID + System.lineSeparator() + listOfSelfRoles.get(roleID).toString();
					isExculsive = isExculsive + System.lineSeparator() + stringYesNo(guildID, roleID); 
				}
				
			}
			
			embed.addField("Role Name", roleName, true);
			embed.addField("Group ID", groupID, true);
			embed.addField("Exculsive", isExculsive, true);
			
			channel.sendMessage(embed.build()).queue();
		}

	}

	private String stringYesNo(Long guildID, Long roleID) {
		return dbMan.isRoleExclusive(guildID, roleID) ? "yes" : "no";
	}

	@Override
	public String help(Long GuildID) {
		return "Returns list of roles that can be self assigned";
	}

}
