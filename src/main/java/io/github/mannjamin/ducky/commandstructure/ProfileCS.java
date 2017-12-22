package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import io.github.mannjamin.ducky.database.manager.data.UserProfile;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

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
        if (hasPermission(author)) {
            if (mentionedUsers.isEmpty()) {
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
                    channel.sendMessage(i18n.localize(dbMan, channel, "command.profile.user_not_found", user.getAsMention())).queue();
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
            embed.setAuthor(localize(channel, "command.profile.profile_of", member.getEffectiveName()), null, member.getUser().getAvatarUrl());
            embed.addField(localize(channel, "command.profile.rank"), up.getRankName(), false);
            embed.addField(localize(channel, "command.profile.exp"), up.getLevel().toString(), true);
            if (up.getRankExp() != null)
                embed.addField(localize(channel, "command.profile.exp_required"), up.getRankExp().toString(), true);
            else
                embed.addField(localize(channel, "command.profile.exp_required"), localize(channel, "command.profile.max_rank"), true);
            embed.addField(localize(channel, "command.profile.points"), up.getPoints().toString(), true);
            embed.addField(localize(channel, "command.profile.gold"), up.getBalance().toString() + " :moneybag:", true);
            embed.addField(localize(channel, "command.profile.tables_flipped"), up.getFlipped().toString(), true);
            embed.addField(localize(channel, "command.profile.tables_unflipped"), up.getUnflipped().toString(), true);
            embed.addField(localize(channel, "command.profile.werewolf_games"), up.getWerewolfGames().toString(), true);
            embed.addField(localize(channel, "command.profile.werewolf_wins"), up.getWerewolfWins().toString(), true);
            embed.addField(localize(channel, "command.profile.items"), up.getItemCount().toString(), true);
            if (requestedBy != null)
                embed.addField("", localize(channel, "command.profile.requested_by", requestedBy.getEffectiveName()), false);
            embed.setFooter(localize(channel, "command.profile.notice", prefix), null);
            embed.setTimestamp(Instant.now());

            embed.setThumbnail(member.getUser().getAvatarUrl());
            if (up.getTitle() == null || up.getTitle().equals("")) {
                embed.setDescription(userLevelName);
            } else {
                embed.setDescription(userLevelName + System.lineSeparator() + "**Title:** " + up.getTitle());
            }
            channel.sendMessage(embed.build()).queue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.profile.help", dbMan.getPrefix(guildID), commandName);

    }

}
