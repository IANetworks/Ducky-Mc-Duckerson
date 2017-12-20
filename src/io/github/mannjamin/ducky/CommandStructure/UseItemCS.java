package io.github.mannjamin.ducky.CommandStructure;

import io.github.mannjamin.ducky.SharedContainer;
import io.github.mannjamin.ducky.database.manager.data.UserProfile;
import io.github.mannjamin.ducky.items.Item;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UseItemCS extends CommandStructure {
    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public UseItemCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();
        Long userID = author.getUser().getIdLong();
        if (hasPermission(author)) {
            Long itemID = null;

            parameters = parameters.trim();
            if (!parameters.isEmpty() && isInteger(parameters)) {
                itemID = Long.valueOf(parameters);

                try {
                    UserProfile up = dbMan.getUserProfile(guildID, userID);
                    HashMap<Long, Item> inv = up.getInv();
                    if (inv.containsKey(itemID)) {
                        Item item = inv.get(itemID);
                        item.executeUse(guild, author, channel);
                    } else {
                        channel.sendMessage("You do not have this item to use").queue();
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                channel.sendMessage("This syntax looks wrong, Syntax: " + dbMan.getPrefix(guildID) + commandName + " [items id]").queue();
            }
        }
    }

    @Override
    public String help(Long guildID) {
        return "Use Item from your inventory";
    }
}
