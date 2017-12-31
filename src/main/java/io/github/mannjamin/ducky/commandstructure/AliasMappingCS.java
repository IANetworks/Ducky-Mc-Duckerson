package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.Map;

public class AliasMappingCS extends CommandStructure {

  public Map<Long, CommandStructure> commandMapping;

  public AliasMappingCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
    super(container, commandName, commandID, commandDefaultLevel);
    commandMapping = new HashMap<>();
  }

  @Override
  public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
    Long guildID = author.getGuild().getIdLong();
    CommandStructure command = commandMapping.get(guildID);
    if (command != null) {
        command.execute(author, authorUser, channel, message, parameters, commandList);
    }
  }

  @Override
  public String help(Long guildID, MessageChannel channel) {
    CommandStructure command = commandMapping.get(guildID);
    if (command == null) {
        return "";
    } else {
        return command.help(guildID, channel);
    }
  }
}
