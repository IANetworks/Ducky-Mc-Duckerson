package bot.CommandStructure;

import bot.SharedContainer;
import bot.database.manager.data.UserProfile;
import bot.items.Item;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

public class BuyCS extends CommandStructure {
    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public BuyCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();
        Long userID = author.getUser().getIdLong();
        if (hasPermission(author)) {
            Integer quantity = 0;
            Integer invID = null;

            String[] paraList = parameters.trim().split(" ", 2);
            if (paraList.length > 1 && isInteger(paraList[0]) && isInteger(paraList[1])) {
                invID = Integer.valueOf(paraList[0]);
                quantity = Integer.valueOf(paraList[1]);
            } else {
                channel.sendMessage("This syntax looks wrong, Syntax: " + dbMan.getPrefix(guildID) + commandName + " [items id] [quantity]").queue();
            }

            if (itemDB.hasStoreItem(invID)) {
                UserProfile up = null;
                try {
                    up = dbMan.getUserProfile(guildID, userID);
                    Item item = itemDB.getItem(invID);
                    Integer itemCost = item.getCost();
                    Integer totalCost = itemCost * quantity;
                    if (totalCost > up.getBalance()) {
                        channel.sendMessage("\uD83D\uDCB8 Sorry you don't have enough gold to get this. \uD83D\uDCB8").queue();
                    } else {
                        itemDB.obtainItem(invID, guild, author, channel, quantity);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                channel.sendMessage("I do not have this item or it's not for sale").queue();
            }
        }


    }

    @Override
    public String help(Long guildID) {
        return "Buy Items with gold. Syntax: " + dbMan.getPrefix(guildID) + commandName + " [inv id] [quantity]";
    }
}