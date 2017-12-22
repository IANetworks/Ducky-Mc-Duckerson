package io.github.mannjamin.ducky.items;

import io.github.mannjamin.ducky.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.HashMap;

public class ItemDatabase {
    private HashMap<Integer, Item> storeList = new HashMap<>();
    private HashMap<Integer, Item> fullInv = new HashMap<>();
    private DatabaseManager dbMan;


    public ItemDatabase(DatabaseManager dbMan) {
        this.dbMan = dbMan;
        addItem(new ExpBoostItem(dbMan, 0));
        addItem(new TesterTitleItem(dbMan, 1));
    }

    public HashMap<Integer, Item> getStoreList() {
        return storeList;
    }

    private void addItem(Item item) {
        fullInv.put(item.getInvID(), item);
        if (item.isBuyable()) {
            storeList.put(item.getInvID(), item);
        }
    }

    public Item getNewItem(Integer invID) {
        if (fullInv.containsKey(invID)) {
            try {
                return (Item) fullInv.get(invID).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public boolean hasStoreItem(Integer InvID) {
        return storeList.containsKey(InvID);
    }

    public boolean hasItem(Integer invID) {
        return fullInv.containsKey(invID);
    }

    public void obtainItem(Integer invID, Guild guild, Member member, MessageChannel channel) {
        obtainItem(invID, guild, member, channel, null);
    }

    public void obtainItem(Integer invID, Guild guild, Member member, MessageChannel channel, Integer amount) {
        if (fullInv.containsKey(invID)) {
            fullInv.get(invID).execute(guild, member, channel, amount);
        } else {
            //TODO ERROR
        }
    }

    public Item getItem(Integer invID) {
        return fullInv.getOrDefault(invID, null);
    }
}
