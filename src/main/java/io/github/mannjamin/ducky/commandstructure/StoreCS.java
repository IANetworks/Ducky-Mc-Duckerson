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
            embed.setTitle(localize(channel, "command.store.title"), null);
            for (Item item : storeList.values()) {
                embed.addField(localize(channel, "command.store.item", item.getLocalizedName(channel), item.getInvID()),
                    localize(channel, "command.store.item_description", item.getDescription(channel), item.getCost()), true);
            }
            channel.sendMessage(embed.build()).queue();
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.store.help");
    }
}
