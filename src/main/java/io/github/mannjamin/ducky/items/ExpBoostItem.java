package io.github.mannjamin.ducky.items;

import io.github.mannjamin.ducky.database.manager.DatabaseManager;
import io.github.mannjamin.ducky.database.manager.data.RankUp;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;

public class ExpBoostItem extends Item {
    private static Long expGain = 50L;

    public ExpBoostItem(DatabaseManager dbMan, Integer invID) {
      this(dbMan, invID, null);
    }

    public ExpBoostItem(DatabaseManager dbMan, Integer invID, Long itemID) {
        super(
            dbMan,
            "exp_boost",
            invID,
            true,
            1000,
            true,
            ItemType.INVITEM,
            itemID
        );
    }

    @Override
    public String getDescription(MessageChannel channel) {
        return localize(channel, String.format("item.%s.description", name), expGain);
    }

    @Override
    public void execute(Guild guild, Member member, MessageChannel channel, Integer amount) {
        Long guildID = guild.getIdLong();
        Long userID = member.getUser().getIdLong();

        if (amount == null || amount == 1) {
            try {
                dbMan.addItemToUser(guildID, userID, this);
              channel.sendMessage(
                localize(channel, "item.exp_boost.buy",
                  member.getEffectiveName(), amount, this.getLocalizedName(channel))
              ).queue();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (amount > 1) {
            try {
                for (int i = 0; i < amount; i++) {
                    dbMan.addItemToUser(guildID, userID, this);
                }
                channel.sendMessage(
                    localize(channel, "item.exp_boost.buy",
                        member.getEffectiveName(), amount, this.getLocalizedName(channel))
                ).queue();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void executeUse(Guild guild, Member member, MessageChannel channel) {
        Long guildID = guild.getIdLong();
        Long userID = member.getUser().getIdLong();

        LinkedList<RankUp> rankUps;
        try {
            rankUps = dbMan.addUserExp(guildID, userID, expGain);

            if (!rankUps.isEmpty()) {
                EmbedBuilder newEmbed = new EmbedBuilder();
                Color color = new Color(50, 255, 50);
                newEmbed.setAuthor(member.getEffectiveName(), null, member.getUser().getAvatarUrl());
                newEmbed.setTitle(localize(channel, "item.exp_boost.rank_up"));
                newEmbed.setColor(color);

                for (RankUp newRank : rankUps) {
                    newEmbed.addField(member.getEffectiveName(), localize(channel, "item.exp_boost.field.rank_up", newRank.rankName), false);
                    if (newRank.expRequired != null) {
                        newEmbed.addField("", localize(channel, "item.exp_boost.field.xp_needed", newRank.expRequired), false);
                    } else {
                        newEmbed.addField("", localize(channel, "item.exp_boost.field.max_reached"), false);
                    }
                }

                channel.sendMessage(newEmbed.build()).queue();

            }

            dbMan.removeItemFromUser(this.getItemID());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
