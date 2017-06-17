package bot.CommandStructure;

import java.util.Map;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import java.awt.Color;

public class Profile extends CommandStructure {
	final static int commandID = 3;
	final static int commandDefaultLevel = 999;
	final static String commandName = "profile";
	
	public Profile(DatabaseManager dbMan, String botAdmin, User botOwner) {
		super(dbMan, botAdmin, botOwner, commandName, commandID, commandDefaultLevel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void excute(Member author, MessageChannel channel, Message message, String parameters,
			Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		
		if(hasPermission(author))
		{
			Color color = new Color(255, 40, 40);
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setColor(color);
			embed.setAuthor("Profile of " + author.getEffectiveName(), author.getUser().getAvatarUrl(), null);
			embed.addField("Rank:", "In Development", true);
			embed.addField("Level:", "0", true);
			embed.addField("Points", "1", true);
			embed.addField("Balance", "-1", true);
			embed.setFooter("To see your profile, use profile", null);
			embed.setThumbnail(author.getUser().getAvatarUrl());
			channel.sendMessage(embed.build()).queue();
		}
	}

}
