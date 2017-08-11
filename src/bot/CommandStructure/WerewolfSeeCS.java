package bot.CommandStructure;

import bot.SharedContainer;
import net.dv8tion.jda.core.entities.*;
import werewolf.GameState;

import java.util.List;
import java.util.Map;

public class WerewolfSeeCS extends CommandStructure {

    public WerewolfSeeCS(SharedContainer container, String commandName, int commandID, int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters, Map<String, CommandStructure> commandList) {
        //If author is null, then this is a private message
        if(author == null)
        {
            //Let attempt to see if we can find any mutual guilds.
            //TODO parameters - create a gameID so that players can select the correct game they are requesting to see
            Integer count = 0;
            Long lastGuildID = null;
            Guild lastGuild = null;
            for(Guild guild : authorUser.getMutualGuilds()) {
                //and for each mutual guild, are they in any games
                Long guildID = guild.getIdLong();
                if(ww.hasPlayer(guildID, authorUser.getIdLong()))
                {
                    count++;
                    lastGuildID = guildID;
                    lastGuild = guild;
                }
            }

            if(count == 0)
            {
                //Not playing in any guild/or has any mutual guilds
                channel.sendMessage("I cannot find you in any games").queue();
            } else if(count > 1)
            {
                channel.sendMessage("Multi-game Function to be implemented soon.").queue();
            } else {
                //Let check that this person have permission to use see command
                author = lastGuild.getMember(authorUser);
                if(hasPermission(author)) {
                    //If we have a game, let make sure this is during nighttime.
                    GameState gs = ww.getWerewolfGameState(lastGuildID);
                    if (gs == GameState.NIGHTTIME)
                    {
                        List<User> mentionedUsers = message.getMentionedUsers();
                        if(mentionedUsers.isEmpty())
                        {
                            parameters = parameters.trim();
                            //No mention, let test for number
                            if(isInteger(parameters))
                            {
                                Integer playerNo = Integer.valueOf(parameters);
                                ww.setSeerView(lastGuildID, author, playerNo);
                            } else {
                                //Ok, let attempt to search for the player using the string
                                List<Member> listOfMembers = lastGuild.getMembersByName(parameters, false);
                                count = 0;
                                Member lastSearchedMember = null;
                                for(Member thisMember : listOfMembers)
                                {
                                    if(ww.hasPlayer(lastGuildID, thisMember))
                                    {
                                        lastSearchedMember = thisMember;
                                        count++;
                                    }
                                }

                                if(count == 1)
                                {
                                    ww.setSeerView(lastGuildID, author, lastSearchedMember);
                                } else if(count > 1)
                                {
                                    channel.sendMessage("I've found more than one player by *'" + parameters + "'*. Try using a more exact name or use player number.").queue();
                                } else {
                                    channel.sendMessage("I've found no players by the name *'" + parameters + "'*. Try using a more exact name or use player number.").queue();
                                }
                            }
                        } else {
                            //we have a mention, now we need to get the member for this user, let get the first one only
                            User user = mentionedUsers.get(0);
                            //we check to see if the user is not us.
                            Member userMember =  lastGuild.getMember(user);
                            if (userMember.equals(lastGuild.getSelfMember()))
                            {
                                //Next we check to see if userMember is not us
                                channel.sendMessage("Hey " + author.getAsMention() + ", maybe try using see on an actual player instead of the gameMaster.").queue();
                            } else {
                                //Check to see if the mentioned user is a a player as well
                                if(ww.hasPlayer(lastGuildID, userMember))
                                {
                                    ww.setSeerView(lastGuildID, author, userMember);
                                } else {
                                    channel.sendMessage(userMember.getEffectiveName() + " is not playing.").queue();
                                }
                            }
                        }
                    }
                } else {
                    channel.sendMessage("You do not have permission to use See command on this server.").queue();
                }
            }
        }

    }

    @Override
    public String help(Long guildID) {
        return "During a game, use this command to select a person to see.";
    }
}
