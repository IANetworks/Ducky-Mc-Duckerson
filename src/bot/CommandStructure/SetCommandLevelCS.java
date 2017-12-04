package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.sql.SQLException;
import java.util.Map;

/**
 * The type Set command level cs.
 */
public class SetCommandLevelCS extends CommandStructure {

    /**
     * Instantiates a new Set command level cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public SetCommandLevelCS(SharedContainer container, String commandName, int commandID,
                             int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();

        if (hasPermission(author)) {
            if (parameters.isEmpty()) {
                channel.sendMessage(localize(channel, "command.set_command_level.error.command_missing", dbMan.getPrefix(guildID) + commandName)).queue();
            }
            String[] paraList = parameters.split(" ");
            String cmdID = paraList[1];
            if (isInteger(paraList[2])) {
                Integer commandLevel = Integer.valueOf(paraList[2]);
                //TODO Check people's userlevel to make sure they can't assign a value higher than what they have.
                if (commandList.containsKey(cmdID)) {
                    try {
                        dbMan.setCommandLevel(guildID, commandList.get(cmdID).commandID, commandLevel);
                        channel.sendMessage(localize(channel, "command.set_command_level.success", dbMan.getLevelName(guildID, commandLevel), cmdID)).queue();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        channel.sendMessage(localize(channel, "command.set_command_level.error.sql")).queue();
                    }
                } else {

                }
            } else {
                channel.sendMessage(localize(channel, "command.set_command_level.error.syntax", dbMan.getPrefix(guildID) + commandName)).queue();
            }
        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.set_command_level.help", dbMan.getPrefix(guildID) + commandName);

    }

}
