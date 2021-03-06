package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import io.github.mannjamin.ducky.database.manager.data.UserProfile;
import io.github.mannjamin.ducky.items.Item;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUserInvCS extends CommandStructure {
    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public ListUserInvCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
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
                Color color = new Color(200, 100, 100);
                sendUserInv(author, channel, prefix, color);
            } else {
                //We can't loop through the whole list of mentioned users, so we only going to grab the first mentioned user and ignored the rest
                User user = mentionedUsers.get(0);
                Color color = new Color(100, 40, 240);
                Member userMember = author.getGuild().getMemberById(user.getIdLong());

                if (userMember == null) {
                    channel.sendMessage(localize(channel, "command.inv.error.unknown_user", user.getAsMention())).queue();
                } else {
                    sendUserInv(userMember, channel, prefix, color, author);
                }
            }
        }
    }

    private void sendUserInv(Member member, MessageChannel channel, String prefix, Color color) {
        sendUserInv(member, channel, prefix, color, null);
    }

    private void sendUserInv(Member member, MessageChannel channel, String prefix, Color color, Member requestedBy) {
        EmbedBuilder embed = new EmbedBuilder();
        Long guildID = member.getGuild().getIdLong();
        Long userID = member.getUser().getIdLong();
        UserProfile up;

        try {
            up = dbMan.getUserProfile(guildID, userID);
            HashMap<Long, Item> inv = up.getInv();

            embed.setColor(color);
            embed.setAuthor(localize(channel, "command.inv.title", member.getEffectiveName()), null, member.getUser().getAvatarUrl());
            if (inv.isEmpty()) {
                embed.addField(localize(channel, "command.inc.inventory"), localize(channel, "command.inv.empty"), false);
            } else {
                for (Item item : inv.values()) {
                    embed.addField(localize(channel, "command.inv.item", item.getLocalizedName(channel), item.getItemID()), item.getDescription(channel), true);
                }
            }

            if (requestedBy != null)
                embed.addField("", localize(channel, "command.inv.requested_by", member.getEffectiveName(), requestedBy.getEffectiveName()), false);
            embed.setFooter(localize(channel, "command.inv.usage", prefix + commandName), null);
            embed.setTimestamp(Instant.now());

            embed.setThumbnail(member.getUser().getAvatarUrl());
            channel.sendMessage(embed.build()).queue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.inv.help");
    }
}
