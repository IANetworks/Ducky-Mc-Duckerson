package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.I18N;
import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.sql.SQLException;
import java.util.Map;

public class SetChannelLocaleCS extends CommandStructure {

    public SetChannelLocaleCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
                        Map<String, CommandStructure> commandList
    ) {
        if (!hasPermission(author) || !(channel instanceof TextChannel)) {
            return;
        }
        String locale = parameters.trim();
        if (locale.isEmpty() || locale.contains(" ")) {
            long guildID = author.getGuild().getIdLong();
            channel.sendMessage(localize(channel, "command.set_language.error.syntax",
                dbMan.getPrefix(guildID) + commandName)).queue();
            return;
        }

        // Test that the locale exists; this key must be present in every language file
        if (i18n.localize(locale, I18N.KEY_LOCALE_NAME).equals(i18n.localize(I18N.ROOT_LOCALE, I18N.KEY_LOCALE_NAME))) {
            channel.sendMessage(localize(channel, "command.set_language.error.unknown_locale", locale)).queue();
            return;
        }

        try {
            dbMan.setLocaleForChannel((TextChannel) channel, locale);
        } catch (SQLException e) {
            channel.sendMessage(localize(channel, "command.set_language.error.sql")).queue();
            return;
        }
        message.addReaction("✅").queue();
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.set_language.help", dbMan.getPrefix(guildID) + commandName);
    }
}
