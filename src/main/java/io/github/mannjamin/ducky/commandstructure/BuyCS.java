package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import io.github.mannjamin.ducky.database.manager.data.UserProfile;
import io.github.mannjamin.ducky.items.Item;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
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
                channel.sendMessage(localize(channel, "command.buy.error.syntax", dbMan.getPrefix(guildID) + commandName)).queue();
            }

            if (itemDB.hasStoreItem(invID)) {
                UserProfile up = null;
                try {
                    up = dbMan.getUserProfile(guildID, userID);
                    Item item = itemDB.getItem(invID);
                    Integer itemCost = item.getCost();
                    Integer totalCost = itemCost * quantity;
                    if (totalCost > up.getBalance()) {
                        channel.sendMessage(localize(channel, "command.buy.error.not_enough_gold")).queue();
                    } else {
                        itemDB.obtainItem(invID, guild, author, channel, quantity);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                channel.sendMessage(localize(channel, "command.buy.error.item_not_available")).queue();
            }
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.buy.help", dbMan.getPrefix(guildID) + commandName);
    }
}