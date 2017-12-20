package io.github.mannjamin.ducky.CommandStructure;

import io.github.mannjamin.ducky.SharedContainer;
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
            String isExculsive = "";

            //TODO Limit how large a list can get
            for (Long roleID : listOfSelfRoles.keySet()) {
                if (roleName == "") {
                    roleName = guild.getRoleById(roleID).getName();
                    groupID = listOfSelfRoles.get(roleID).toString();
                    isExculsive = stringYesNo(guildID, roleID);
                } else {
                    roleName = roleName + System.lineSeparator() + guild.getRoleById(roleID).getName();
                    groupID = groupID + System.lineSeparator() + listOfSelfRoles.get(roleID).toString();
                    isExculsive = isExculsive + System.lineSeparator() + stringYesNo(guildID, roleID);
                }

            }

            embed.addField("Role Name", roleName, true);
            embed.addField("Group ID", groupID, true);
            embed.addField("Exculsive", isExculsive, true);

            channel.sendMessage(embed.build()).queue();
        }

    }

    private String stringYesNo(Long guildID, Long roleID) {
        return dbMan.isRoleExclusive(guildID, roleID) ? "yes" : "no";
    }

    @Override
    public String help(Long guildID) {
        return "Returns list of roles that can be self assigned";
    }

}
