package bot.CommandStructure;

import bot.SharedContainer;
import bot.database.manager.RankUp;
import bot.database.manager.data.UserProfile;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;
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
            Integer quantity = 0;
            Integer item = null;

            UserProfile up = null;
            try {
                up = dbMan.getUserProfile(guildID, userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String[] paraList = parameters.trim().split(" ", 2);
            if (paraList.length > 1 && isInteger(paraList[0]) && isInteger(paraList[1])) {
                item = Integer.valueOf(paraList[0]);
                quantity = Integer.valueOf(paraList[1]);
            } else {
                channel.sendMessage(localize(channel, "command.buy.error.syntax", dbMan.getPrefix(guildID) + commandName)).queue();
            }
            //TODO remove Hardcode here
            if (item == 1) {
                Integer itemCost = 1000;
                Integer totalCost = quantity * itemCost;
                if (totalCost > up.getBalance()) {
                    channel.sendMessage(localize(channel, "command.buy.error.not_enough_gold")).queue();
                } else {
                    Integer expGain = 50;
                    Integer totalExpGain = expGain * quantity;
                    channel.sendMessage(localize(channel, "command.buy.success", totalExpGain)).queue();
                    try {
                        LinkedList<RankUp> rankUps = dbMan.addUserExp(guildID, userID, totalExpGain.longValue());
                        dbMan.remUserBalance(guildID, userID, totalCost.longValue());
                        //TODO refactor this block of code to a function to be used by other commands and locations in code
                        if (!rankUps.isEmpty()) {
                            for (RankUp newRank : rankUps) {
                                EmbedBuilder newEmbed = new EmbedBuilder();
                                Color color = new Color(50, 255, 50);
                                newEmbed.setColor(color);
                                newEmbed.setAuthor(author.getEffectiveName(), null, author.getUser().getAvatarUrl());
                                newEmbed.setTitle(localize(channel, "rankup.title"));
                                newEmbed.setDescription(localize(channel, "rankup.text", author.getEffectiveName(), newRank.rankName));
                                newEmbed.addBlankField(false);
                                if (newRank.expRequired != null) {
                                    newEmbed.setFooter(localize(channel, "rankup.xp_required", newRank.expRequired), null);
                                } else {
                                    newEmbed.setFooter(localize(channel, "rankup.max_rank_reached"), null);
                                }

                                channel.sendMessage(newEmbed.build()).queue();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.buy.help", dbMan.getPrefix(guildID) + commandName);
    }
}
