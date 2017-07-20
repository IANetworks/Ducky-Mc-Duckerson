package bot.CommandStructure;

import java.sql.SQLException;
import java.util.Map;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class SetSelfRoleGroupExculsive extends CommandStructure {

	public SetSelfRoleGroupExculsive(DatabaseManager dbMan, String botAdmin, User botOwner, String commandName,
			int commandID, int commandDefaultLevel) {
		super(dbMan, botAdmin, botOwner, commandName, commandID, commandDefaultLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters,
			Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		Guild guild = author.getGuild();
		Integer groupID; //0 is default group
		
		if(hasPermission(author))
		{
			Member selfMember = guild.getSelfMember();
			if(selfMember.hasPermission(Permission.MANAGE_ROLES))
			{
				if(isInteger(parameters.trim()))
				{
					groupID = Integer.valueOf(parameters.trim());
					if(dbMan.hasGroup(guildID, groupID))
					{
						Boolean isExculsive = dbMan.isGroupExculsive(guildID, groupID);
						try {
							dbMan.setGroupExculsive(guildID, groupID, !isExculsive);
							channel.sendMessage("Group " + groupID + " is set to " + stringBlankNot(!isExculsive) + " exculsive").queue();
						} catch (SQLException e) {
							channel.sendMessage("Ah, I borked here. I need tinkering here.").queue();
						}
					} else {
						channel.sendMessage("I do not have any group by that ID").queue();
					}
					
				} else {
					channel.sendMessage("Something doesn't look right, the Syntax is: " + dbMan.getPrefix(guildID) + commandName + " [group id]");
				}
			} else {
				channel.sendMessage("I do not have Manage Roles permission.").queue();
			}
			
		}

	}
	private String stringBlankNot(Boolean exculsive) {
		return exculsive ? "" : "not";
	}
	@Override
	public String help(Long guildID) {
		// TODO Auto-generated method stub
		return "Toggle a group to be exculsive or not exculsive Syntax: " + dbMan.getPrefix(guildID) + commandName + " [group id]";
	}

}
