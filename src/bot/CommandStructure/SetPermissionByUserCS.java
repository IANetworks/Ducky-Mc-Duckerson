package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The type Set permission by user cs.
 */
public class SetPermissionByUserCS extends CommandStructure {

    /**
     * Instantiates a new Set permission by user cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetPermissionByUserCS(SharedContainer container, String commandName, int commandID,
                                 int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();

        if (hasPermission(author)) {
            //if we don't have any parameters, we're resetting to default
            if (parameters.isEmpty()) {
                //check to make sure we're actually changing a default
                channel.sendMessage(localize(channel, "command.set_level_for_user.error.mention_missing")).queue(); //Should I think about breaking this out to make localizion doable?
                //I don't really expect this bot to get popular but this might make the bot popular thing along non-english servers..
            } else {
                parameters = parameters.trim();
                Integer levelID = null;
                for (String para : parameters.split(" ")) {
                    if (isInteger(para)) {
                        levelID = Integer.valueOf(para);
                    }
                }
                if (levelID != null && levelID > 1) {
                    List<User> mentionedUsers = message.getMentionedUsers();
                    if (hasPermission(author, levelID - 1)) {

                        if (!mentionedUsers.isEmpty()) {
                            for (User user : mentionedUsers) {
                                try {
                                    String userLevelName = dbMan.getLevelName(guildID, levelID);
                                    dbMan.setUserLevel(guildID, levelID, user.getIdLong());
                                    channel.sendMessage(localize(channel, "command.set_level_for_user.success", userLevelName, user.getAsMention())).queue();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    channel.sendMessage(localize(channel, "command.set_level_for_user.error.sql")).queue();
                                }
                            }
                        }
                    } else {
                        channel.sendMessage(localize(channel, "command.set_level_for_user.error.insufficient_permission_level")).queue();
                    }

                } else if (levelID != null && levelID < 2) {
                    channel.sendMessage(localize(channel, "command.set_level_for_user.error.special_level", dbMan.getLevelName(guildID, levelID))).queue();
                } else {
                    channel.sendMessage(localize(channel, "command.set_level_for_user.error.unknown_level")).queue();
                }
            }
        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.set_level_for_user.help", dbMan.getPrefix(guildID) + commandName);
    }

}
