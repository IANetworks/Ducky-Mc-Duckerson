package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import werewolf.data.ThemeDesc;

import java.util.Map;

public class WerewolfListThemeCS extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public WerewolfListThemeCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
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
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("List of Themes", null, null);
            for (ThemeDesc thisTheme : dbMan.sqlGetThemeList()) {
                embed.addField(thisTheme.getThemeID() + ": " + thisTheme.getName(), thisTheme.getDesc(), false);
            }
            channel.sendMessage(embed.build()).queue();
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return "Display a list of themes.";
    }
}

