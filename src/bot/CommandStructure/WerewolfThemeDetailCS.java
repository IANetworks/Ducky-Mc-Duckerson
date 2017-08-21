package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.util.Map;

public class WerewolfThemeDetailCS extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public WerewolfThemeDetailCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();
        if (!dbMan.isWerewolfOn(guildID)) {
            //Werewolf is turned off for this guild, so ignore the command
            return;
        }
        if (hasPermission(author)) {
            if (parameters.isEmpty()) {
                channel.sendMessage("Syntax is " + dbMan.getPrefix(guildID) + commandName + " [themeID]").queue();
            } else if (isInteger(parameters)) {
                Integer id = Integer.parseInt(parameters);
                MessageEmbed embed = ww.displayTheme(id);
                if (embed == null) {
                    channel.sendMessage("Invalid Theme ID. I don't have a theme by the ID " + id.toString()).queue();
                } else {
                    channel.sendMessage(embed).queue();
                }
            }

        }
    }

    @Override
    public String help(Long guildID) {
        return "Display theme infomation. Syntax is " + dbMan.getPrefix(guildID) + commandName + " [themeID]";
    }
}
