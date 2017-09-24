package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.Map;

public class DeleteCommandCS extends CommandStructure {
    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public DeleteCommandCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();
        if (hasPermission(author)) {
            //toggle command
            Boolean delCmd = !dbMan.getDeleteCommand(guildID);
            try {
                dbMan.setDeleteCommand(guildID, delCmd);
                if (delCmd) {
                    message.delete().reason("Clearing Command").queue();
                } else {
                    message.addReaction("white_check_mark").queue();
                    if (delCmd) {
                        message.addReaction("white_circle").queue();
                    } else {
                        message.addReaction("red_circle").queue();
                    }
                }
            } catch (SQLException e) {
                channel.sendMessage("This hurts, ow").queue();
            }


        }

    }

    @Override
    public String help(Long guildID) {
        return "Set whether the commands are deleted or not. Some commands will not be deleted such as game commands.";
    }
}
