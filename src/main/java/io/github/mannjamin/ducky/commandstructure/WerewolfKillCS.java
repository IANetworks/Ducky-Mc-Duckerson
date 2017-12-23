package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import net.dv8tion.jda.core.entities.*;
import io.github.mannjamin.ducky.werewolf.GameState;

import java.util.List;
import java.util.Map;

/**
 * The type Werewolf kill cs.
 */
public class WerewolfKillCS extends CommandStructure {
    /**
     * Instantiates a new Werewolf kill cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public WerewolfKillCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        //TODO code functionality to allow privatemessage kills
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();

        if (!dbMan.isWerewolfOn(guildID)) {
            //Werewolf is turned off for this guild, so ignore the command
            return;
        }

        if (hasPermission(author)) {
            //check to see if this is from a channel other than the guild wolf channel
            if (!ww.getWolfChannel(guildID).equals(channel)) {
                return; //ignore it
            }
            //check to make sure that we're during a votetime
            GameState gs = ww.getWerewolfGameState(guildID);
            if (gs != null && gs == GameState.NIGHTTIME) ;
            {
                //We need to figure out who the player voting for, first we'll see if the parameter, has an at mention, a String or a number
                List<User> mentionedUsers = message.getMentionedUsers();
                if (mentionedUsers.isEmpty()) {
                    parameters = parameters.trim();
                    //No mention, let test for number
                    if (isInteger(parameters)) {
                        Integer playerNo = Integer.valueOf(parameters);
                        ww.setWolfVote(guildID, author, playerNo);
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
                            ww.setWolfVote(guildID, author, lastSearchedMember);
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
                    if (userMember.equals(guild.getSelfMember())) {
                        //Next we check to see if userMember is not us
                        channel.sendMessage("Hey " + author.getAsMention() + ", I'm the most amazing, you can't eat me.");
                    } else {
                        //Check to see if the mentioned user is a a player as well
                        if (ww.hasPlayer(guildID, userMember)) {
                            ww.setWolfVote(guildID, author, userMember);
                        } else {
                            channel.sendMessage(userMember.getEffectiveName() + " is not playing.");
                        }
                    }
                }
            }
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return "If you're a wolf, then you select who to kill.";
    }
}
