package bot.CommandStructure;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import bot.SharedContainer;
import bot.database.manager.data.UserProfile;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import java.awt.Color;
import java.sql.SQLException;

/**
 * The type Profile cs.
 */
public class ProfileCS extends CommandStructure {

	/**
	 * Instantiates a new Profile cs.
	 *
	 * @param container           the container
	 * @param commandName         the command name
	 * @param commandID           the command id
	 * @param commandDefaultLevel the command default level
	 */
	public ProfileCS(SharedContainer container, String commandName, int commandID,
			int commandDefaultLevel) {
		super(container, commandName, commandID, commandDefaultLevel);
	}

	@Override
	public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
						Map<String, CommandStructure> commandList) {
		Long guildID = author.getGuild().getIdLong();
		String prefix = dbMan.getPrefix(guildID);
		 
		
		
		List<User> mentionedUsers = message.getMentionedUsers();
		if(hasPermission(author))
		{
			if(mentionedUsers.isEmpty())
			{
				Integer userLevel = getPermissionLevel(author);
				String userLevelName = dbMan.getLevelName(guildID, userLevel);
                Color color = new Color(200, 100, 100);
                sendProfile(author, channel, prefix, userLevelName, color);
			} else {
				//We can't loop through the whole list of mentioned users, so we only going to grab the first mentioned user and ignored the rest
				User user = mentionedUsers.get(0); 
				Color color = new Color(100, 40, 240);
				Member userMember = author.getGuild().getMemberById(user.getIdLong());
				
				if (userMember == null) {
					channel.sendMessage("I cannot find " + user.getAsMention() + " on this server. I can only show profiles that are on this server.").queue();
				} else {
					Integer userLevel = getPermissionLevel(userMember);
					String userLevelName = dbMan.getLevelName(guildID, userLevel);
                    sendProfile(userMember, channel, prefix, userLevelName, color, author);
                }
			}
		}
	}

    private void sendProfile(Member member, MessageChannel channel, String prefix, String userLevelName, Color color) {
        sendProfile(member, channel, prefix, userLevelName, color, null);
    }

    private void sendProfile(Member member, MessageChannel channel, String prefix, String userLevelName, Color color, Member requestedBy) {
        EmbedBuilder embed = new EmbedBuilder();
		Long guildID = member.getGuild().getIdLong();
		Long userID = member.getUser().getIdLong();
        UserProfile up;

        try {
			up = dbMan.getUserProfile(guildID, userID);
		
		
			embed.setColor(color);
			embed.setAuthor("Profile of " + member.getEffectiveName(), null, member.getUser().getAvatarUrl());
            embed.addField("Rank:", up.getRankName(), false);
            embed.addField("Current Exp:", up.getLevel().toString(), true);
            if (up.getRankExp() != null)
                embed.addField("Exp Required:", up.getRankExp().toString(), true);
            else
                embed.addField("Exp Required:", "\uD83D\uDCAF Max Rank \uD83D\uDCAF", true);
            embed.addField("Points:", up.getPoints().toString(), true);
			embed.addField("Gold:" , up.getBalance().toString() + " :moneybag:", true);
			embed.addField("Table Flipped:", up.getFlipped().toString(), true);
			embed.addField("Table Unflipped:", up.getUnflipped().toString(), true);
            embed.addField("Werewolf Games:", up.getWerewolfGames().toString(), true);
            embed.addField("Werewolf Wins:", up.getWerewolfWins().toString(), true);
            if (requestedBy != null)
                embed.addField("", "Profile requested by: " + requestedBy.getEffectiveName(), false);
            embed.setFooter("To see your profile, use " + prefix + "profile", null);
            embed.setTimestamp(Instant.now());

            embed.setThumbnail(member.getUser().getAvatarUrl());
			embed.setDescription(userLevelName);
			channel.sendMessage(embed.build()).queue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String help(Long guildID) {
		return "Return users profiles. No mention, will return your profile.  @mention user will return their profile. " + dbMan.getPrefix(guildID) + commandName + " <@mention>";
		
	}

}
