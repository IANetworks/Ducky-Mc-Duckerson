package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.EmoteImpl;

import java.sql.SQLException;
import java.util.Map;

public class AliasCS extends CommandStructure {

    private SharedContainer container;

    private int aliasCommandID;
    private int aliasDefaultLevel;

    public AliasCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel,
                   int aliasCommandID, int aliasDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
        this.container = container;
        this.aliasCommandID = aliasCommandID;
        this.aliasDefaultLevel = aliasDefaultLevel;
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
        Map<String, CommandStructure> commandList) {
        if (!hasPermission(author)) {
            return;
        }

        Long guildID = author.getGuild().getIdLong();

        String[] paramsParsed = parameters.trim().split("\\s", 3);
        if (paramsParsed.length != 2) {
            if (paramsParsed.length != 1 || paramsParsed[0].isEmpty()) {
                channel.sendMessage(
                    localize(channel, "command.alias.error.syntax", dbMan.getPrefix(guildID) + commandName)
                ).queue();
                return;
            }
            String alias = paramsParsed[0];
            // second parameter omitted => remove alias if exists
            CommandStructure oldCommand = commandList.get(alias);
            if (!(oldCommand instanceof AliasMappingCS)) {
                channel.sendMessage(localize(channel, "command.alias.error.alias_not_found", alias)).queue();
                return;
            }
            AliasMappingCS aliasedCommand = (AliasMappingCS) oldCommand;
            if (!aliasedCommand.commandMapping.containsKey(guildID)) {
                channel.sendMessage(localize(channel, "command.alias.error.alias_not_found", alias)).queue();
                return;
            }
            // Remove mapping
            try {
                dbMan.removeAlias(guildID, alias);
                aliasedCommand.commandMapping.remove(guildID);
                channel.sendMessage(localize(channel, "command.alias.removed")).queue();
                return;
            } catch (SQLException e) {
                channel.sendMessage(localize(channel, "command.alias.error.sql")).queue();
                return;
            }
        }

        // Both parameters set => create or extend alias mapping
        String alias = paramsParsed[0];
        String targetCommandName = paramsParsed[1];
        CommandStructure targetCommand = commandList.get(targetCommandName);
        CommandStructure oldCommand = commandList.get(alias);
        if (targetCommand == null) {
            channel.sendMessage(
                localize(channel, "command.alias.error.command_not_found", targetCommandName)
            ).queue();
            return;
        }
        if (targetCommand instanceof AliasMappingCS) {
            channel.sendMessage(localize(channel, "command.alias.error.recursion")).queue();
            return;
        }
        if (oldCommand == null) {
            // create new alias
            try {
                dbMan.setAlias(guildID, alias, targetCommandName);
                AliasMappingCS aliasedCommand = new AliasMappingCS(container, alias, aliasCommandID, aliasDefaultLevel);
                aliasedCommand.commandMapping.put(guildID, targetCommand);
                commandList.put(alias, aliasedCommand);
                message.addReaction("✅").queue();
            } catch (SQLException e) {
                channel.sendMessage(localize(channel, "command.alias.error.sql")).queue();
            }
            return;
        }
        if (!(oldCommand instanceof AliasMappingCS)) {
            channel.sendMessage(localize(channel, "command.alias.error.already_used", alias)).queue();
            return;
        }
        AliasMappingCS aliasedCommand = (AliasMappingCS) oldCommand;
        if (aliasedCommand.commandMapping.containsKey(guildID)) {
            channel.sendMessage(localize(channel, "command.alias.error.already_used", alias)).queue();
            return;
        }

        try {
            dbMan.setAlias(guildID, alias, targetCommandName);
            aliasedCommand.commandMapping.put(guildID, targetCommand);
            message.addReaction("✅").queue();
        } catch (SQLException e) {
            channel.sendMessage(localize(channel, "command.alias.error.sql")).queue();
        }
    }

  @Override
  public String help(Long guildID, MessageChannel channel) {
      return localize(channel, "command.alias.help", dbMan.getPrefix(guildID) + commandName);
  }
}
