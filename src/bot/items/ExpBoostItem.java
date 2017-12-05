package bot.items;

import bot.database.manager.DatabaseManager;
import bot.database.manager.data.RankUp;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;

public class ExpBoostItem extends Item {
    private static Long expGain = 50L;

    public ExpBoostItem(DatabaseManager dbMan, Integer invID) {
        super(
            dbMan,
            "Experience Boost",
            "Gain " + expGain.toString() + " + experience points",
            invID,
            true,
            1000,
            true,
            ItemType.INVITEM,
            null
        );
    }

    public ExpBoostItem(DatabaseManager dbMan, Integer invID, Long itemID) {
        super(
            dbMan,
            "Experience Boost",
            "Gain " + expGain.toString() + " + experience points",
            invID,
            true,
            1000,
            true,
            ItemType.INVITEM,
            itemID
        );
    }

    @Override
    public void execute(Guild guild, Member member, MessageChannel channel, Integer amount) {
        Long guildID = guild.getIdLong();
        Long userID = member.getUser().getIdLong();

        if (amount == null || amount == 1) {
            try {
                dbMan.addItemToUser(guildID, userID, this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (amount > 1) {
            try {
                for (int i = 0; i < amount; i++) {
                    dbMan.addItemToUser(guildID, userID, this);
                }
                channel.sendMessage(member.getEffectiveName() + " has gained: " + amount.toString() + " " + this.getName()).queue();
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
                newEmbed.setTitle("\uD83D\uDD3C RANK UP \uD83D\uDD3C");
                newEmbed.setColor(color);

                for (RankUp newRank : rankUps) {
                    newEmbed.addField(member.getEffectiveName(), " has ranked up to " + newRank.rankName, false);
                    if (newRank.expRequired != null) {
                        newEmbed.addField("", "They need " + newRank.expRequired + " to rank reach the next rank", false);
                    } else {
                        newEmbed.addField("", "Reached max rank defined. Way to go!", false);
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
