package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.*;

import java.util.List;
import java.util.Map;

public class WerewolfKickPlayerCS extends CommandStructure {

    /**
     * Instantiates a new Command structure.
     *
     * @param container           the container - Contains varies object a command might need for operation
     * @param commandName         the command name - The name of the command
     * @param commandID           the command id - unique ID.
     * @param commandDefaultLevel the command default level
     */
    public WerewolfKickPlayerCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
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
            //figure out how to kick user)
            //We need to figure out who the player voting for, first we'll see if the parameter, has an at mention, a String or a number
            List<User> mentionedUsers = message.getMentionedUsers();
            if (mentionedUsers.isEmpty()) {
                parameters = parameters.trim();
                //No mention, let test for number
                if (isInteger(parameters)) {
                    Integer playerNo = Integer.valueOf(parameters);
                    if (!ww.leaveGame(guildID, playerNo)) {
                        channel.sendMessage("Don't have that player by that Player Number").queue();
                    }
                } else {
                    //Ok, let attempt to search for the player using the string
                    List<Member> listOfMembers = guild.getMembersByName(parameters, false);
                    Integer count = 0;
                    Member lastSearchedMember = null;
                    for (Member thisMember : listOfMembers) {
                        if (ww.hasPlayer(guildID, thisMember)) {
                            lastSearchedMember = thisMember;
                            count++;
                        }
                    }

                    if (count == 1) {
                        ww.leaveGame(guildID, lastSearchedMember);
                    } else if (count > 1) {
                        channel.sendMessage("I've found more than one player by *'" + parameters + "'*. Try using a more exact name or use player number.");
                    } else {
                        channel.sendMessage("I've found no players by the name *'" + parameters + "'*. Try using a more exact name or use player number.");
                    }
                }
            } else {
                //we have a mention, now we need to get the member for this user, let get the first one only
                User user = mentionedUsers.get(0);
                //we check to see if the user is not us.
                Member userMember = guild.getMember(user);
                //Check to see if the mentioned user is a a player as well
                if (ww.hasPlayer(guildID, userMember)) {
                    ww.leaveGame(guildID, userMember);
                } else {
                    channel.sendMessage(userMember.getEffectiveName() + " is not playing.");
                }
            }
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return "Kick a users from current werewolf game";
    }
}
