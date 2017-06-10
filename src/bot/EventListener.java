package bot;

import java.sql.Connection;
import java.util.List;

import bot.database.manager.DatabaseManager;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.*;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class EventListener extends ListenerAdapter {
	DatabaseManager dbMan;
	String botAdmin;
	String prefix;
	
	//TODO STERILIZE USER INPUT! LIKE I WILL BITE FACES AND FLIP TABLES IF THIS ISN'T DONE
	
	//TODO Lookup, how to defer down the constructors
	public EventListener(DatabaseManager dbMan) {
		selfStart(dbMan, null);
	}
	
	public EventListener(DatabaseManager dbMan, String botAdmin) {
		selfStart(dbMan, botAdmin);
	}
	
	private void selfStart(DatabaseManager dbMan, String botAdmin)
	{
		this.dbMan = dbMan;
		this.botAdmin = botAdmin;
		
		//Here we fetch infomation from database as needed
	}

	/**
     * NOTE THE @Override!
     * This method is actually overriding a method in the ListenerAdapter class! We place an @Override annotation
     *  right before any method that is overriding another to guarantee to ourselves that it is actually overriding
     *  a method from a super class properly. You should do this every time you override a method!
     *
     * As stated above, this method is overriding a hook method in the
     * {@link net.dv8tion.jda.core.hooks.ListenerAdapter ListenerAdapter} class. It has convience methods for all JDA events!
     * Consider looking through the events it offers if you plan to use the ListenerAdapter.
     *
     * In this example, when a message is received it is printed to the console.
     *
     * @param event
     *          An event containing information about a {@link net.dv8tion.jda.core.entities.Message Message} that was
     *          sent in a channel.
     */
	
	//We Want To See All Users Joinning the server(Called Guilds by Discord, why, I dunno)
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event)
	{
		Member member = event.getMember();
        Guild guild = event.getGuild();
        //TODO Update this to be adjustable for now, we'll use event-log
        
        List<TextChannel> ltc = guild.getTextChannelsByName("event-log", true);
        if (ltc.isEmpty())
        {
        	System.out.printf("Found no text channels with event-log");
        	return;
        }
        
        for(TextChannel tc : ltc)
        {
        	tc.sendMessage("Member " + member.getAsMention() + " has joined " + guild.getName() + ".").queue();
   		}
	}
	
	//Users leaving server
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event)
	{
		Member member = event.getMember();
        Guild guild = event.getGuild();
        //TODO Update this to be adjustable for now, we'll use event-log
        List<TextChannel> ltc=  guild.getTextChannelsByName("event-log", true);

        if (ltc.isEmpty())
        {
        	System.out.printf("Found no text channels with event-log");
        	return;
        }
        
        for(TextChannel tc : ltc)
        {
        	tc.sendMessage("Member `" + member.getEffectiveName() + "` has left " + guild.getName() + ".").queue();
   		}
	}
	
	
	
	//
	
	//All Messages recieved, from Private channels (DM), Public Channels(server/guild), Groups (Client only, we're using bot account so we can't do groups)
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        //These are provided with every event in JDA
        JDA jda = event.getJDA();                       //JDA, the core of the api.
        //long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        
        String msg = message.getContent();              //This returns a human readable version of the Message. Similar to
                                                        // what you would see in the client.

        

        if (msg.equals("!shutdown"))
        {
        	//Make sure bot owner is set
            jda.shutdown();
        }
    }
}
