package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.util.List;
import java.util.Map;

public class GiveItemCS extends CommandStructure {
    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public GiveItemCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();
        Long userID = author.getUser().getIdLong();
        if (hasPermission(author)) {
            Integer quantity = 0;
            Integer item = null;

            String[] paraList = parameters.trim().split(" ");
            if (paraList.length > 1 && isInteger(paraList[0]) && isInteger(paraList[1])) {
                item = Integer.valueOf(paraList[0]);
                quantity = Integer.valueOf(paraList[1]);

                List<User> mentionedUsers = message.getMentionedUsers();

                if (mentionedUsers.size() > 0) {
                    for (User user : mentionedUsers) {
                        Member member = guild.getMember(user);
                        if (member != null) {
                            if (itemDB.hasItem(item)) {
                                itemDB.obtainItem(item, guild, member, channel, quantity);
                            } else {
                                channel.sendMessage("I do not have this item").queue();
                            }
                        }
                    }
                } else {
                    channel.sendMessage("This syntax looks wrong, Syntax: " + dbMan.getPrefix(guildID) + commandName + " [items id] [quantity] [mention]").queue();
                }
            } else {
                channel.sendMessage("This syntax looks wrong, Syntax: " + dbMan.getPrefix(guildID) + commandName + " [items id] [quantity] [mention]").queue();
            }


        }
    }

    @Override
    public String help(Long guildID) {
        return "Give Items Syntax: " + dbMan.getPrefix(guildID) + commandName + " [inv id] [quantity] [mention]";
    }
}