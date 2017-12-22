package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import io.github.mannjamin.ducky.items.Item;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.HashMap;
import java.util.Map;

public class StoreCS extends CommandStructure {
    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public StoreCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();
        Long userID = author.getUser().getIdLong();
        if (hasPermission(author)) {
            HashMap<Integer, Item> storeList = itemDB.getStoreList();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Store", null);
            for (Item item : storeList.values()) {
                embed.addField(item.getName() + "(ID: " + item.getInvID().toString() + ")", item.getDescription() + System.lineSeparator()
                    + "Cost: " + item.getCost(), true);
            }
            channel.sendMessage(embed.build()).queue();
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return "Display list of items that can be brought with gold";
    }
}
