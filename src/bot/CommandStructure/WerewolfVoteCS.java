package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.*;
import werewolf.GameState;

import java.util.List;
import java.util.Map;

public class WerewolfVoteCS extends CommandStructure {
    public WerewolfVoteCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        Guild guild = author.getGuild();
        Long guildID = guild.getIdLong();


        if(hasPermission(author))
        {
            //check to see if this is from a channel other than the guild townchannel
            if(!ww.getTownChannel(guildID).equals(channel))
            {
                return; //ignore it
            }
            //check to make sure that we're during a votetime
            GameState gs = ww.getWerewolfGameState(guildID);
            if(gs != null && gs == GameState.VOTETIME);
            {
                //We need to figure out who the player voting for, first we'll see if the parameter, has an at mention, a String or a number
                List<User> mentionedUsers = message.getMentionedUsers();
                if(mentionedUsers.isEmpty())
                {
                    parameters = parameters.trim();
                    //No mention, let test for number
                    if(isInteger(parameters))
                    {
                        Integer playerNo = Integer.valueOf(parameters);
                        ww.setVote(guildID, author, playerNo);
                    } else {
                        //Ok, let attempt to search for the player using the string
                        List<Member> listOfMembers = guild.getMembersByName(parameters, false);
                        Integer count = 0;
                        Member lastSearchedMember = null;
                        for(Member thisMember : listOfMembers)
                        {
                            if(ww.hasPlayer(guildID, thisMember))
                            {
                                lastSearchedMember = thisMember;
                                count++;
                            }
                        }

                        if(count == 1)
                        {
                            ww.setVote(guildID, author, lastSearchedMember);
                        } else if(count > 1)
                        {
                            channel.sendMessage("I've found more than one player by *'" + parameters + "'*. Try using a more exact name or use player number.");
                        } else {
                            channel.sendMessage("I've found no players by the name *'" + parameters + "'*. Try using a more exact name or use player number.");
                        }
                    }
                } else {
                    //we have a mention, now we need to get the member for this user, let get the first one only
                    User user = mentionedUsers.get(0);
                    //we check to see if the user is not us.
                    Member userMember =  guild.getMember(user);
                    if (userMember.equals(guild.getSelfMember()))
                    {
                        //Next we check to see if userMember is not us
                        channel.sendMessage("Hey " + author.getAsMention() + ", Screw you, I didn't do anything.");
                    } else {
                        //Check to see if the mentioned user is a a player as well
                        if(ww.hasPlayer(guildID, userMember))
                        {
                            ww.setVote(guildID, author, userMember);
                        } else {
                            channel.sendMessage(userMember.getEffectiveName() + " is not playing.");
                        }
                    }
                }
            }
        }
    }

    @Override
    public String help(Long guildID) {
        return "During a game of werewolf, vote who you think is the weakest link, goodbye. Syntax: " + commandName + " [Number/@Mention/Nickname]";
    }
}
