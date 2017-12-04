package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.sql.SQLException;
import java.util.Map;

/**
 * The type Preload cs.
 */
public class PreloadCS extends CommandStructure {

    /**
     * Instantiates a new Preload cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public PreloadCS(SharedContainer container, String commandName, int commandID,
                     int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
                        Map<String, CommandStructure> commandList) {

        Long guildID = author.getGuild().getIdLong();

        try {
            boolean setDefault = dbMan.setNewPermissionNames(guildID);
            if (setDefault) {
                channel.sendMessage(localize(channel, "command.preload.success")).queue(); //
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.preload.help");
    }

}
