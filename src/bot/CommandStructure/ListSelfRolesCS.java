package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.Map;

/**
 * The type List self roles cs.
 */
public class ListSelfRolesCS extends CommandStructure {

    /**
     * Instantiates a new List self roles cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public ListSelfRolesCS(SharedContainer container, String commandName, int commandID,
                           int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
                        Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();
        Guild guild = author.getGuild();

        if (hasPermission(author)) {
            Map<Long, Integer> listOfSelfRoles = dbMan.getListOfRoles(guildID);
            EmbedBuilder embed = new EmbedBuilder();

            String roleName = "";
            String groupID = "";
            String isExclusive = "";

            //TODO Limit how large a list can get
            for (Long roleID : listOfSelfRoles.keySet()) {
                if (roleName == "") {
                    // TODO: localize role name?
                    roleName = guild.getRoleById(roleID).getName();
                    groupID = listOfSelfRoles.get(roleID).toString();
                    isExclusive = stringYesNo(guildID, roleID, channel);
                } else {
                    roleName = roleName + System.lineSeparator() + guild.getRoleById(roleID).getName();
                    groupID = groupID + System.lineSeparator() + listOfSelfRoles.get(roleID).toString();
                    isExclusive = isExclusive + System.lineSeparator() + stringYesNo(guildID, roleID, channel);
                }

            }

            embed.addField(localize(channel, "command.list_self_roles.role_name"), roleName, true);
            embed.addField(localize(channel, "command.list_self_roles.group_id"), groupID, true);
            embed.addField(localize(channel, "command.list_self_roles.exclusive"), isExclusive, true);

            channel.sendMessage(embed.build()).queue();
        }

    }

    private String stringYesNo(Long guildID, Long roleID, MessageChannel channel) {
        return localize(channel, dbMan.isRoleExclusive(guildID, roleID) ? "command.list_self_roles.exclusive.yes" : "command.list_self_roles.exclusive.no");
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.list_self_roles.help");
    }

}
