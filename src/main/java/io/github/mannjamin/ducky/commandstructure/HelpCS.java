package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.Map;

/**
 * The type Help cs.
 */
public class HelpCS extends CommandStructure {

    /**
     * Instantiates a new Help Command.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public HelpCS(SharedContainer container, String commandName, int commandID,
                  int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
                        Map<String, CommandStructure> commandList) {

        Long guildID = author.getGuild().getIdLong();
        User user = author.getUser();

        user.openPrivateChannel().queue((privChannel) -> sendHelpList(privChannel, commandList, guildID));

    }

    private void sendHelpList(PrivateChannel privChannel, Map<String, CommandStructure> commandList, Long guildID) {
        EmbedBuilder embed = new EmbedBuilder();
        int count = 0;
        for (String commandName : commandList.keySet()) {
            String help = commandList.get(commandName).help(guildID, privChannel);
            if (help == null) help = "";
            embed.addField(commandName, help, true);
            count++;
            if (count > 7) {
                count = 0;
                privChannel.sendMessage(embed.build()).queue();
                embed = new EmbedBuilder();
            }
        }

        privChannel.sendMessage(embed.build()).queue();
    }


    @Override
    public String help(Long guildID, MessageChannel channel) {

        return i18n.localize(dbMan, channel, "command.help.help");

    }

}
