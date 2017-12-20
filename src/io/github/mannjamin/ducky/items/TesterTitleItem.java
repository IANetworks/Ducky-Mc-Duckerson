package io.github.mannjamin.ducky.items;

import io.github.mannjamin.ducky.database.manager.DatabaseManager;
import io.github.mannjamin.ducky.database.manager.data.UserProfile;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.sql.SQLException;
import java.util.HashMap;

public class TesterTitleItem extends Item {

    public TesterTitleItem(DatabaseManager dbMan, Integer invID) {
        super(
            dbMan,
            "Tester",
            "Get Tester Title for profile",
            invID,
            false,
            null,
            true,
            ItemType.TITLE,
            null
        );
    }

    public TesterTitleItem(DatabaseManager dbMan, Integer invID, Long itemID) {
        super(
            dbMan,
            "Tester",
            "Get Tester Title for profile",
            invID,
            false,
            null,
            true,
            ItemType.TITLE,
            itemID
        );
    }

    @Override
    public void execute(Guild guild, Member member, MessageChannel channel, Integer amount) {
        Long guildID = guild.getIdLong();
        Long userID = member.getUser().getIdLong();

        try {
            UserProfile up = dbMan.getUserProfile(guildID, userID);

            HashMap<Long, Item> invList = up.getInv();
            boolean hasItem = false;
            for (Item item : invList.values()) {
                if (item.getInvID() == invID) {
                    hasItem = true;
                    break;
                }
            }

            if (hasItem) {
                channel.sendMessage(member.getEffectiveName() + " already have this title.").queue();
            } else {
                dbMan.addItemToUser(guildID, userID, this);
                channel.sendMessage(member.getEffectiveName() + " has gained: " + this.getName()).queue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void executeUse(Guild guild, Member member, MessageChannel channel) {
        Long guildID = guild.getIdLong();
        Long userID = member.getUser().getIdLong();
        try {
            dbMan.setUserTitle(guildID, userID, this.getName());
            channel.sendMessage("Title set to " + this.getName()).queue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
