package bot;

import net.dv8tion.jda.core.events.*;
import net.dv8tion.jda.core.events.channel.priv.PrivateChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.priv.PrivateChannelDeleteEvent;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.core.events.channel.text.update.*;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.core.events.channel.voice.update.*;
import net.dv8tion.jda.core.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.core.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.core.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.core.events.emote.update.EmoteUpdateRolesEvent;
import net.dv8tion.jda.core.events.guild.*;
import net.dv8tion.jda.core.events.guild.member.*;
import net.dv8tion.jda.core.events.guild.update.*;
import net.dv8tion.jda.core.events.guild.voice.*;
import net.dv8tion.jda.core.events.message.guild.*;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveAllEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.message.priv.*;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionRemoveAllEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.events.role.RoleDeleteEvent;
import net.dv8tion.jda.core.events.role.update.*;
import net.dv8tion.jda.core.events.self.*;
import net.dv8tion.jda.core.events.user.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * The type Debug listener.
 */
public class DebugListener extends ListenerAdapter {
	
	/**
	 * We'll put all the debug output log here. As there's a lot of event to look at, we'll tuck it all here for a tidier code.
	 * Here we'll define what level debug each event is.
	 * 
	 * TODO Finish off the debugs events
	 */
	private DebugLevel debugLevel;

    /**
     * Instantiates a new Debug listener.
     *
     * @param debugLevel the debug level
     */
    public DebugListener(DebugLevel debugLevel) {
		this.setDebugLevel(debugLevel);
	}
	
	
	//Hook onGenericEvent - this is fired every time an event is fired. 
	@Override
	public void onGenericEvent(Event event)
	{
        //JDA Events
        if (event instanceof ReadyEvent && debugOutput(DebugLevel.MED)){
            ReadyEvent readyEvent = (ReadyEvent) event;
            System.out.printf("ReadyEvent <%s>\n", readyEvent.getResponseNumber());
        }   
        else if (event instanceof ResumedEvent && debugOutput(DebugLevel.MED)) {
        	ResumedEvent resumedEvent = (ResumedEvent) event;
        	System.out.printf("ResumedEvent <%s> \n", resumedEvent.getResponseNumber());
        }
            
        else if (event instanceof ReconnectedEvent && debugOutput(DebugLevel.MED)) {
        	ReconnectedEvent reconnectedEvent = (ReconnectedEvent) event;
        	System.out.printf("ReconnectedEvent <%s>\n", reconnectedEvent.getResponseNumber());
        }       	
            
        else if (event instanceof DisconnectEvent && debugOutput(DebugLevel.MED)) {
        	DisconnectEvent disconnectEvent = (DisconnectEvent) event;
        	System.out.printf("DisconnectEvent, by server[%s] at [%s] <%s>\n",disconnectEvent.isClosedByServer(), disconnectEvent.getDisconnectTime(), disconnectEvent.getResponseNumber());
        	
        }
            
        else if (event instanceof ShutdownEvent && debugOutput(DebugLevel.MED)) {
        	ShutdownEvent shutDownEvent = (ShutdownEvent) event;
        	System.out.printf("ShutDownEvent at [%s] <%s>\n", shutDownEvent.getShutdownTime(), shutDownEvent.getResponseNumber());
        }
            
        else if (event instanceof StatusChangeEvent && debugOutput(DebugLevel.MED)) {
        	StatusChangeEvent statusChangeEvent = (StatusChangeEvent) event;
        	System.out.printf("StatusChangeEvent Status [%s] to [%s] <%s>\n", statusChangeEvent.getOldStatus(), statusChangeEvent.getStatus(), statusChangeEvent.getResponseNumber());
        }
            
        else if (event instanceof ExceptionEvent && debugOutput(DebugLevel.MED)) {
        	ExceptionEvent exceptionEvent = (ExceptionEvent) event;
        	System.out.printf("ExceptionEvent cause: %s <%s>\n", exceptionEvent.getCause().toString(), exceptionEvent.getResponseNumber());
        	

        }
            

        //Message Events
        //Guild (TextChannel) Message Events
        else if (event instanceof GuildMessageReceivedEvent && debugOutput(DebugLevel.LOW)) {
        	GuildMessageReceivedEvent guildMessageReceivedEvent= (GuildMessageReceivedEvent) event;
        	System.out.printf("GuildMessageReceivedEvent (%s)[%s]<%s> %s <%s>\n", guildMessageReceivedEvent.getGuild().getName(), guildMessageReceivedEvent.getChannel().getName(), 
        			guildMessageReceivedEvent.getAuthor().getName(), guildMessageReceivedEvent.getMessage().getContent(), guildMessageReceivedEvent.getResponseNumber());

       }
        else if (event instanceof GuildMessageUpdateEvent && debugOutput(DebugLevel.LOW)) {
        	GuildMessageUpdateEvent guildMessageUpdateEvent = (GuildMessageUpdateEvent) event;
        	System.out.printf("GuildMessageUpdateEvent (%s)[%s]<%s> %s <%s>\n", guildMessageUpdateEvent.getGuild().getName(), guildMessageUpdateEvent.getChannel().getName(), 
        			guildMessageUpdateEvent.getAuthor().getName(), guildMessageUpdateEvent.getMessage().getContent(), guildMessageUpdateEvent.getResponseNumber());

        }
        else if (event instanceof GuildMessageDeleteEvent && debugOutput(DebugLevel.MED)) {
        	GuildMessageDeleteEvent guildMessageDeleteEvent = (GuildMessageDeleteEvent) event;
        	System.out.printf("GuildMessageDeleteEvent (%s)[%s]<%s>\n", guildMessageDeleteEvent.getGuild().getName(), guildMessageDeleteEvent.getChannel().getName()
        			, guildMessageDeleteEvent.getResponseNumber());

        }
        else if (event instanceof GuildMessageEmbedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageEmbedEvent guildMessageEmbedEvent = (GuildMessageEmbedEvent) event;
        	System.out.printf("GuildMessageEmbedEvent (%s)[%s]<%s>\n", guildMessageEmbedEvent.getGuild().getName(), guildMessageEmbedEvent.getChannel().getName()
        			, guildMessageEmbedEvent.getResponseNumber());


        }
        else if (event instanceof GuildMessageReactionAddEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageReactionAddEvent guildMessageReactionAddEvent = (GuildMessageReactionAddEvent) event;
        	System.out.printf("GuildMessageReactionAddEvent (%s)[%s]<%s>\n", guildMessageReactionAddEvent.getGuild().getName(), guildMessageReactionAddEvent.getChannel().getName()
        			, guildMessageReactionAddEvent.getResponseNumber());

    	}
        else if (event instanceof GuildMessageReactionRemoveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageReactionRemoveEvent guildMessageReactionRemoveEvent = (GuildMessageReactionRemoveEvent) event;
        	System.out.printf("GuildMessageReactionRemoveEvent (%s)[%s]<%s>\n", guildMessageReactionRemoveEvent.getGuild().getName(), guildMessageReactionRemoveEvent.getChannel().getName()
        			, guildMessageReactionRemoveEvent.getResponseNumber());

        }
        else if (event instanceof GuildMessageReactionRemoveAllEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageReactionRemoveAllEvent guildMessageReactionRemoveAllEvent = (GuildMessageReactionRemoveAllEvent) event;
        	System.out.printf("GuildMessageReactionRemoveAllEvent (%s)[%s]<%s>\n", guildMessageReactionRemoveAllEvent.getGuild().getName(), guildMessageReactionRemoveAllEvent.getChannel().getName()
        			, guildMessageReactionRemoveAllEvent.getResponseNumber());

        }

        //Private Message Events
        else if (event instanceof PrivateMessageReceivedEvent && debugOutput(DebugLevel.LOW)) {
        	PrivateMessageReceivedEvent privateMessageReceivedEvent= (PrivateMessageReceivedEvent) event;
        	System.out.printf("PrivateMessageReceivedEvent [%s] %s <%s>\n", privateMessageReceivedEvent.getAuthor().getName(), privateMessageReceivedEvent.getMessage().getContent(), 
        			privateMessageReceivedEvent.getResponseNumber());

        }
        else if (event instanceof PrivateMessageUpdateEvent && debugOutput(DebugLevel.LOW)) {
        	PrivateMessageUpdateEvent privateMessageUpdateEvent = (PrivateMessageUpdateEvent) event;
        	System.out.printf("PrivateMessageUpdateEvent [%s] %s <%s>\n", privateMessageUpdateEvent.getAuthor().getName(), privateMessageUpdateEvent.getMessage().getContent(), 
        			privateMessageUpdateEvent.getResponseNumber());

        }
        else if (event instanceof PrivateMessageDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageDeleteEvent privateMessageDeleteEvent = (PrivateMessageDeleteEvent) event;
        	System.out.printf("PrivateMessageDeleteEvent <%s>\n",	privateMessageDeleteEvent.getResponseNumber());

        }
        else if (event instanceof PrivateMessageEmbedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageEmbedEvent privateMessageEmbedEvent = (PrivateMessageEmbedEvent) event;
        	System.out.printf("PrivateMessageEmbedEvent <%s>\n",	privateMessageEmbedEvent.getResponseNumber());

        }
        else if (event instanceof PrivateMessageReactionAddEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageReactionAddEvent privateMessageReactionAddEvent = (PrivateMessageReactionAddEvent) event;
        	System.out.printf("PrivateMessageReactionAddEvent <%s>\n",	privateMessageReactionAddEvent.getResponseNumber());

        }
        else if (event instanceof PrivateMessageReactionRemoveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageReactionRemoveEvent privateMessageReactionRemoveEvent = (PrivateMessageReactionRemoveEvent) event;
        	System.out.printf("PrivateMessageReactionRemoveEvent <%s>\n",	privateMessageReactionRemoveEvent.getResponseNumber());

        }
        else if (event instanceof PrivateMessageReactionRemoveAllEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageReactionRemoveAllEvent privateMessageReactionRemoveAllEvent = (PrivateMessageReactionRemoveAllEvent) event;
        	System.out.printf("PrivateMessageReactionRemoveAllEvent <%s>\n",	privateMessageReactionRemoveAllEvent.getResponseNumber());

        }

        //User Events
        else if (event instanceof UserNameUpdateEvent && debugOutput(DebugLevel.MED)) {
        	UserNameUpdateEvent userNameUpdateEvent = (UserNameUpdateEvent) event;
        	System.out.printf("UserNameUpdateEvent [%s] to [%s] <%s>\n", userNameUpdateEvent.getOldName(), userNameUpdateEvent.getUser().getName(), userNameUpdateEvent.getResponseNumber());

        }
        else if (event instanceof UserAvatarUpdateEvent && debugOutput(DebugLevel.MED)) {
        	UserAvatarUpdateEvent userAvatarUpdateEvent = (UserAvatarUpdateEvent) event;
        	System.out.printf("UserAvatarUpdateEvent [%s] <%s>\n", userAvatarUpdateEvent.getUser().getName(), userAvatarUpdateEvent.getResponseNumber());

        }
        else if (event instanceof UserGameUpdateEvent && debugOutput(DebugLevel.MED)) {
        	UserGameUpdateEvent userGameUpdateEvent = (UserGameUpdateEvent) event;
        	System.out.printf("UserGameUpdateEvent [%s] <%s>\n", userGameUpdateEvent.getUser().getName(), userGameUpdateEvent.getResponseNumber());

        }
        else if (event instanceof UserOnlineStatusUpdateEvent && debugOutput(DebugLevel.MED)) {
        	UserOnlineStatusUpdateEvent userOnlineStatusUpdateEvent = (UserOnlineStatusUpdateEvent) event;
        	System.out.printf("UserOnlineStatusUpdateEvent [%s] {%s} <%s>\n", userOnlineStatusUpdateEvent.getUser().getName(), userOnlineStatusUpdateEvent.getPreviousOnlineStatus(), 
        			userOnlineStatusUpdateEvent.getResponseNumber());
        }
        else if (event instanceof UserTypingEvent && debugOutput(DebugLevel.VERBOSE)) {
        	UserTypingEvent userTypingEvent = (UserTypingEvent) event;
        	System.out.printf("UserTypingEvent [%s] <%s>\n", userTypingEvent.getUser().getName(), userTypingEvent.getResponseNumber());

        }

        //Self Events
        else if (event instanceof SelfUpdateAvatarEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateAvatarEvent selfUpdateAvatarEvent = (SelfUpdateAvatarEvent) event;
        	System.out.printf("SelfUpdateAvatarEvent <%s>\n", selfUpdateAvatarEvent.getResponseNumber());

        }
        else if (event instanceof SelfUpdateEmailEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateEmailEvent selfUpdateEmailEvent = (SelfUpdateEmailEvent) event;
        	System.out.printf("SelfUpdateEmailEvent <%s>\n", selfUpdateEmailEvent.getResponseNumber());

        }
        else if (event instanceof SelfUpdateMFAEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateMFAEvent selfUpdateMFAEvent = (SelfUpdateMFAEvent) event;
        	System.out.printf("SelfUpdateMFAEvent <%s>\n", selfUpdateMFAEvent.getResponseNumber());

        }
        else if (event instanceof SelfUpdateNameEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateNameEvent selfUpdateNameEvent = (SelfUpdateNameEvent) event;
        	System.out.printf("SelfUpdateNameEvent <%s>\n", selfUpdateNameEvent.getResponseNumber());

        }
        else if (event instanceof SelfUpdateVerifiedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateVerifiedEvent selfUpdateVerifiedEvent = (SelfUpdateVerifiedEvent) event;
        	System.out.printf("SelfUpdateVerifiedEvent <%s>\n", selfUpdateVerifiedEvent.getResponseNumber());
        }

        //TextChannel Events
        else if (event instanceof TextChannelCreateEvent && debugOutput(DebugLevel.MED)) {
        	TextChannelCreateEvent textChannelCreateEvent = (TextChannelCreateEvent) event;
        	System.out.printf("TextChannelCreateEvent (%s) [%s] <%s>\n", textChannelCreateEvent.getGuild().getName(), textChannelCreateEvent.getChannel().getName(),
        			textChannelCreateEvent.getResponseNumber());

        }
        else if (event instanceof TextChannelUpdateNameEvent && debugOutput(DebugLevel.MED)) {
        	TextChannelUpdateNameEvent textChannelUpdateNameEvent = (TextChannelUpdateNameEvent) event;
        	System.out.printf("TextChannelUpdateNameEvent (%s) [%s] <%s>\n", textChannelUpdateNameEvent.getGuild().getName(), textChannelUpdateNameEvent.getChannel().getName(),
        			textChannelUpdateNameEvent.getResponseNumber());

        }
        else if (event instanceof TextChannelUpdateTopicEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelUpdateTopicEvent textChannelUpdateTopicEvent = (TextChannelUpdateTopicEvent) event;
        	System.out.printf("TextChannelUpdateTopicEvent (%s) [%s] <%s>\n", textChannelUpdateTopicEvent.getGuild().getName(), textChannelUpdateTopicEvent.getChannel().getName(),
        			textChannelUpdateTopicEvent.getResponseNumber());

        }
        else if (event instanceof TextChannelUpdatePositionEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelUpdatePositionEvent textChannelUpdatePositionEvent = (TextChannelUpdatePositionEvent) event;
        	System.out.printf("TextChannelUpdatePositionEvent (%s) [%s] <%s>\n", textChannelUpdatePositionEvent.getGuild().getName(), textChannelUpdatePositionEvent.getChannel().getName(),
        			textChannelUpdatePositionEvent.getResponseNumber());

        }
        else if (event instanceof TextChannelDeleteEvent && debugOutput(DebugLevel.MED)) {
        	TextChannelDeleteEvent textChannelDeleteEvent = (TextChannelDeleteEvent) event;
        	System.out.printf("TextChannelDeleteEvent (%s) [%s] <%s>\n", textChannelDeleteEvent.getGuild().getName(), textChannelDeleteEvent.getChannel().getName(),
        			textChannelDeleteEvent.getResponseNumber());

        }
        else if (event instanceof TextChannelUpdatePermissionsEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelUpdatePermissionsEvent textChannelUpdatePermissionsEvent = (TextChannelUpdatePermissionsEvent) event;
        	System.out.printf("TextChannelUpdatePermissionsEvent (%s) [%s] <%s>\n", textChannelUpdatePermissionsEvent.getGuild().getName(), textChannelUpdatePermissionsEvent.getChannel().getName(),
        			textChannelUpdatePermissionsEvent.getResponseNumber());

        }

        //VoiceChannel Events
        else if (event instanceof VoiceChannelCreateEvent && debugOutput(DebugLevel.MED)) {
        	VoiceChannelCreateEvent voiceChannelCreateEvent = (VoiceChannelCreateEvent) event;
        	System.out.printf("VoiceChannelCreateEvent (%s) [%s] <%s>\n", voiceChannelCreateEvent.getGuild().getName(), voiceChannelCreateEvent.getChannel().getName(),
        			voiceChannelCreateEvent.getResponseNumber());

        }
        else if (event instanceof VoiceChannelUpdateNameEvent && debugOutput(DebugLevel.MED)) {
        	VoiceChannelUpdateNameEvent voiceChannelUpdateNameEvent = (VoiceChannelUpdateNameEvent) event;
        	System.out.printf("VoiceChannelUpdateNameEvent (%s) [%s] <%s>\n", voiceChannelUpdateNameEvent.getGuild().getName(), voiceChannelUpdateNameEvent.getChannel().getName(),
        			voiceChannelUpdateNameEvent.getResponseNumber());

        }
        else if (event instanceof VoiceChannelUpdatePositionEvent && debugOutput(DebugLevel.MED)) {
        	VoiceChannelUpdatePositionEvent voiceChannelUpdatePositionEvent= (VoiceChannelUpdatePositionEvent) event;
        	System.out.printf("VoiceChannelUpdatePositionEvent (%s) [%s] <%s>\n", voiceChannelUpdatePositionEvent.getGuild().getName(), voiceChannelUpdatePositionEvent.getChannel().getName(),
        			voiceChannelUpdatePositionEvent.getResponseNumber());

        }
        else if (event instanceof VoiceChannelUpdateUserLimitEvent && debugOutput(DebugLevel.MED)) {
        	VoiceChannelUpdateUserLimitEvent voiceChannelUpdateUserLimitEvent = (VoiceChannelUpdateUserLimitEvent) event;
        	System.out.printf("VoiceChannelUpdateUserLimitEvent (%s) [%s] <%s>\n", voiceChannelUpdateUserLimitEvent.getGuild().getName(), voiceChannelUpdateUserLimitEvent.getChannel().getName(),
        			voiceChannelUpdateUserLimitEvent.getResponseNumber());

        }
        else if (event instanceof VoiceChannelUpdateBitrateEvent && debugOutput(DebugLevel.MED)) {
        	VoiceChannelUpdateBitrateEvent voiceChannelUpdateBitrateEvent = (VoiceChannelUpdateBitrateEvent) event;
        	System.out.printf("VoiceChannelUpdateBitrateEvent (%s) [%s] <%s>\n", voiceChannelUpdateBitrateEvent.getGuild().getName(), voiceChannelUpdateBitrateEvent.getChannel().getName(),
        			voiceChannelUpdateBitrateEvent.getResponseNumber());

        }
        else if (event instanceof VoiceChannelUpdatePermissionsEvent && debugOutput(DebugLevel.MED)) {
        	VoiceChannelUpdatePermissionsEvent voiceChannelUpdatePermissionsEvent = (VoiceChannelUpdatePermissionsEvent) event;
        	System.out.printf("VoiceChannelUpdatePermissionsEvent (%s) [%s] <%s>\n", voiceChannelUpdatePermissionsEvent.getGuild().getName(), voiceChannelUpdatePermissionsEvent.getChannel().getName(),
        			voiceChannelUpdatePermissionsEvent.getResponseNumber());

        }
        else if (event instanceof VoiceChannelDeleteEvent && debugOutput(DebugLevel.MED)) {
        	VoiceChannelDeleteEvent voiceChannelDeleteEvent= (VoiceChannelDeleteEvent) event;
        	System.out.printf("VoiceChannelDeleteEvent (%s) [%s] <%s>\n", voiceChannelDeleteEvent.getGuild().getName(), voiceChannelDeleteEvent.getChannel().getName(),
        			voiceChannelDeleteEvent.getResponseNumber());

        }

        //PrivateChannel Events
        else if (event instanceof PrivateChannelCreateEvent && debugOutput(DebugLevel.LOW)) {
        	PrivateChannelCreateEvent privateChannelCreateEvent= (PrivateChannelCreateEvent) event;
        	System.out.printf("PrivateChannelCreateEvent (%s) [%s] <%s>\n", privateChannelCreateEvent.getPrivateChannel().getName(), privateChannelCreateEvent.getUser().getName(),
        			privateChannelCreateEvent.getResponseNumber());

        }
        else if (event instanceof PrivateChannelDeleteEvent && debugOutput(DebugLevel.LOW)) {
        	PrivateChannelDeleteEvent privateChannelDeleteEvent = (PrivateChannelDeleteEvent) event;
        	System.out.printf("PrivateChannelDeleteEvent (%s) [%s] <%s>\n", privateChannelDeleteEvent.getPrivateChannel().getName(), privateChannelDeleteEvent.getUser().getName(),
        			privateChannelDeleteEvent.getResponseNumber());

        }

        //Guild Events
        else if (event instanceof GuildJoinEvent && debugOutput(DebugLevel.LOW)) {
        	GuildJoinEvent guildJoinEvent = (GuildJoinEvent) event;
        	System.out.printf("GuildJoinEvent (%s) <%s>\n", guildJoinEvent.getGuild().getName(), guildJoinEvent.getResponseNumber());

        }
        else if (event instanceof GuildLeaveEvent && debugOutput(DebugLevel.LOW)) {
        	GuildLeaveEvent guildLeaveEvent = (GuildLeaveEvent) event;
        	System.out.printf("GuildLeaveEvent (%s) <%s>\n", guildLeaveEvent.getGuild().getName(), guildLeaveEvent.getResponseNumber());

        }
        else if (event instanceof GuildAvailableEvent && debugOutput(DebugLevel.MED)) {
        	GuildAvailableEvent guildAvailableEvent = (GuildAvailableEvent) event;
        	System.out.printf("GuildAvailableEvent (%s) <%s>\n", guildAvailableEvent.getGuild().getName(), guildAvailableEvent.getResponseNumber());

        }
        else if (event instanceof GuildUnavailableEvent && debugOutput(DebugLevel.MED)) {
        	GuildUnavailableEvent guildUnavailableEvent = (GuildUnavailableEvent) event;
        	System.out.printf("GuildUnavailableEvent (%s) <%s>\n", guildUnavailableEvent.getGuild().getName(), guildUnavailableEvent.getResponseNumber());

        }
        else if (event instanceof UnavailableGuildJoinedEvent && debugOutput(DebugLevel.MED)) {
        	UnavailableGuildJoinedEvent unavailableGuildJoinedEvent = (UnavailableGuildJoinedEvent) event;
        	System.out.printf("UnavailableGuildJoinedEvent (%s) <%s>\n", unavailableGuildJoinedEvent.getGuildId(), unavailableGuildJoinedEvent.getResponseNumber());

        }
        else if (event instanceof GuildBanEvent && debugOutput(DebugLevel.LOW)) {
        	GuildBanEvent guildBanEvent = (GuildBanEvent) event;
        	System.out.printf("GuildBanEvent (%s) <%s>\n", guildBanEvent.getGuild().getName(), guildBanEvent.getResponseNumber());

        }
        else if (event instanceof GuildUnbanEvent && debugOutput(DebugLevel.LOW)) {
        	GuildUnbanEvent guildUnbanEvent = (GuildUnbanEvent) event;
        	System.out.printf("GuildUnbanEvent (%s) <%s>\n", guildUnbanEvent.getGuild().getName(), guildUnbanEvent.getResponseNumber());

        }

        //Guild Update Events
        else if (event instanceof GuildUpdateAfkChannelEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateAfkChannelEvent guildUpdateAfkChannelEvent= (GuildUpdateAfkChannelEvent) event;
        	System.out.printf("GuildUpdateAfkChannelEvent (%s) <%s>\n", guildUpdateAfkChannelEvent.getGuild().getName(), guildUpdateAfkChannelEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateAfkTimeoutEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateAfkTimeoutEvent guildUpdateAfkTimeoutEvent = (GuildUpdateAfkTimeoutEvent) event;
        	System.out.printf("GuildUpdateAfkTimeoutEvent (%s) <%s>\n", guildUpdateAfkTimeoutEvent.getGuild().getName(), guildUpdateAfkTimeoutEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateIconEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateIconEvent guildUpdateIconEvent = (GuildUpdateIconEvent) event;
        	System.out.printf("GuildUpdateIconEvent (%s) <%s>\n", guildUpdateIconEvent.getGuild().getName(), guildUpdateIconEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateMFALevelEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateMFALevelEvent guildUpdateMFALevelEvent= (GuildUpdateMFALevelEvent) event;
        	System.out.printf("GuildUpdateMFALevelEvent (%s) <%s>\n", guildUpdateMFALevelEvent.getGuild().getName(), guildUpdateMFALevelEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateNameEvent && debugOutput(DebugLevel.MED)) {
        	GuildUpdateNameEvent guildUpdateNameEvent = (GuildUpdateNameEvent) event;
        	System.out.printf("GuildUpdateNameEvent (%s) <%s>\n", guildUpdateNameEvent.getGuild().getName(), guildUpdateNameEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateNotificationLevelEvent && debugOutput(DebugLevel.MED)) {
        	GuildUpdateNotificationLevelEvent guildUpdateNotificationLevelEvent = (GuildUpdateNotificationLevelEvent) event;
        	System.out.printf("GuildUpdateNotificationLevelEvent (%s) <%s>\n", guildUpdateNotificationLevelEvent.getGuild().getName(), guildUpdateNotificationLevelEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateOwnerEvent && debugOutput(DebugLevel.LOW)) {
        	GuildUpdateOwnerEvent guildUpdateOwnerEvent = (GuildUpdateOwnerEvent) event;
        	System.out.printf("GuildUpdateOwnerEvent (%s) <%s>\n", guildUpdateOwnerEvent.getGuild().getName(), guildUpdateOwnerEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateRegionEvent && debugOutput(DebugLevel.MED)) {
        	GuildUpdateRegionEvent guildUpdateRegionEvent = (GuildUpdateRegionEvent) event;
        	System.out.printf("GuildUpdateRegionEvent (%s) <%s>\n", guildUpdateRegionEvent.getGuild().getName(), guildUpdateRegionEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateSplashEvent && debugOutput(DebugLevel.MED)) {
        	GuildUpdateSplashEvent guildUpdateSplashEvent = (GuildUpdateSplashEvent) event;
        	System.out.printf("GuildUpdateSplashEvent (%s) <%s>\n", guildUpdateSplashEvent.getGuild().getName(), guildUpdateSplashEvent.getResponseNumber());

        }
        else if (event instanceof GuildUpdateVerificationLevelEvent && debugOutput(DebugLevel.MED)) {
        	GuildUpdateVerificationLevelEvent guildUpdateVerificationLevelEvent = (GuildUpdateVerificationLevelEvent) event;
        	System.out.printf("GuildUpdateVerificationLevelEvent (%s) <%s>\n", guildUpdateVerificationLevelEvent.getGuild().getName(), guildUpdateVerificationLevelEvent.getResponseNumber());

        }

        //Guild Member Events
        else if (event instanceof GuildMemberJoinEvent && debugOutput(DebugLevel.LOW)) {
        	GuildMemberJoinEvent guildMemberJoinEvent = (GuildMemberJoinEvent) event;
        	System.out.printf("GuildMemberJoinEvent (%s) [%s] <%s>\n", guildMemberJoinEvent.getGuild().getName(), guildMemberJoinEvent.getMember().getEffectiveName(), 
        			guildMemberJoinEvent.getResponseNumber());

        }
        else if (event instanceof GuildMemberLeaveEvent && debugOutput(DebugLevel.LOW)) {
        	GuildMemberLeaveEvent guildMemberLeaveEvent = (GuildMemberLeaveEvent) event;
        	System.out.printf("GuildMemberLeaveEvent (%s) [%s] <%s>\n", guildMemberLeaveEvent.getGuild().getName(), guildMemberLeaveEvent.getMember().getEffectiveName(), 
        			guildMemberLeaveEvent.getResponseNumber());

        }
        else if (event instanceof GuildMemberRoleAddEvent && debugOutput(DebugLevel.LOW)) {
        	GuildMemberRoleAddEvent guildMemberRoleAddEvent = (GuildMemberRoleAddEvent) event;
        	System.out.printf("GuildMemberRoleAddEvent (%s) [%s] <%s>\n", guildMemberRoleAddEvent.getGuild().getName(), guildMemberRoleAddEvent.getMember().getEffectiveName(), 
        			guildMemberRoleAddEvent.getResponseNumber());

        }
        else if (event instanceof GuildMemberRoleRemoveEvent && debugOutput(DebugLevel.LOW)) {
        	GuildMemberRoleRemoveEvent guildMemberRoleRemoveEvent = (GuildMemberRoleRemoveEvent) event;
        	System.out.printf("GuildMemberRoleRemoveEvent (%s) [%s] <%s>\n", guildMemberRoleRemoveEvent.getGuild().getName(), guildMemberRoleRemoveEvent.getMember().getEffectiveName(), 
        			guildMemberRoleRemoveEvent.getResponseNumber());

        }
        else if (event instanceof GuildMemberNickChangeEvent && debugOutput(DebugLevel.LOW)) {
        	GuildMemberNickChangeEvent guildMemberNickChangeEvent =(GuildMemberNickChangeEvent) event;
        	System.out.printf("GuildMemberNickChangeEvent (%s) [%s] <%s>\n", guildMemberNickChangeEvent.getGuild().getName(), guildMemberNickChangeEvent.getMember().getEffectiveName(), 
        			guildMemberNickChangeEvent.getResponseNumber());

        }

        //Guild Voice Events
        else if (event instanceof GuildVoiceJoinEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceJoinEvent guildVoiceJoinEvent = (GuildVoiceJoinEvent) event;
        	System.out.printf("GuildVoiceJoinEvent (%s) [%s] <%s>\n", guildVoiceJoinEvent.getGuild().getName(), guildVoiceJoinEvent.getMember().getEffectiveName(), 
        			guildVoiceJoinEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceMoveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceMoveEvent guildVoiceMoveEvent = (GuildVoiceMoveEvent) event;
        	System.out.printf("GuildVoiceMoveEvent (%s) [%s] <%s>\n", guildVoiceMoveEvent.getGuild().getName(), guildVoiceMoveEvent.getMember().getEffectiveName(), 
        			guildVoiceMoveEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceLeaveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceLeaveEvent guildVoiceLeaveEvent= (GuildVoiceLeaveEvent) event;
        	System.out.printf("GuildVoiceLeaveEvent (%s) [%s] <%s>\n", guildVoiceLeaveEvent.getGuild().getName(), guildVoiceLeaveEvent.getMember().getEffectiveName(), 
        			guildVoiceLeaveEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceMuteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceMuteEvent guildVoiceMuteEvent = (GuildVoiceMuteEvent) event;
        	System.out.printf("GuildVoiceMuteEvent (%s) [%s] <%s>\n", guildVoiceMuteEvent.getGuild().getName(), guildVoiceMuteEvent.getMember().getEffectiveName(), 
        			guildVoiceMuteEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceDeafenEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceDeafenEvent guildVoiceDeafenEvent = (GuildVoiceDeafenEvent) event;
        	System.out.printf("GuildVoiceDeafenEvent (%s) [%s] <%s>\n", guildVoiceDeafenEvent.getGuild().getName(), guildVoiceDeafenEvent.getMember().getEffectiveName(), 
        			guildVoiceDeafenEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceGuildMuteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceGuildMuteEvent guildVoiceGuildMuteEvent = (GuildVoiceGuildMuteEvent) event;
        	System.out.printf("GuildVoiceGuildMuteEvent (%s) [%s] <%s>\n", guildVoiceGuildMuteEvent.getGuild().getName(), guildVoiceGuildMuteEvent.getMember().getEffectiveName(), 
        			guildVoiceGuildMuteEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceGuildDeafenEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceGuildDeafenEvent guildVoiceGuildDeafenEvent = (GuildVoiceGuildDeafenEvent) event;
        	System.out.printf("GuildVoiceGuildDeafenEvent (%s) [%s] <%s>\n", guildVoiceGuildDeafenEvent.getGuild().getName(), guildVoiceGuildDeafenEvent.getMember().getEffectiveName(), 
        			guildVoiceGuildDeafenEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceSelfMuteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceSelfMuteEvent guildVoiceSelfMuteEvent = (GuildVoiceSelfMuteEvent) event;
        	System.out.printf("GuildVoiceSelfMuteEvent (%s) [%s] <%s>\n", guildVoiceSelfMuteEvent.getGuild().getName(), guildVoiceSelfMuteEvent.getMember().getEffectiveName(), 
        			guildVoiceSelfMuteEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceSelfDeafenEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceSelfDeafenEvent guildVoiceSelfDeafenEvent = (GuildVoiceSelfDeafenEvent) event;
        	System.out.printf("GuildVoiceSelfDeafenEvent (%s) [%s] <%s>\n", guildVoiceSelfDeafenEvent.getGuild().getName(), guildVoiceSelfDeafenEvent.getMember().getEffectiveName(), 
        			guildVoiceSelfDeafenEvent.getResponseNumber());

        }
        else if (event instanceof GuildVoiceSuppressEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceSuppressEvent guildVoiceSuppressEvent = (GuildVoiceSuppressEvent) event;
        	System.out.printf("GuildVoiceSuppressEvent (%s) [%s] <%s>\n", guildVoiceSuppressEvent.getGuild().getName(), guildVoiceSuppressEvent.getMember().getEffectiveName(), 
        			guildVoiceSuppressEvent.getResponseNumber());

        }

        //Role Events
        else if (event instanceof RoleCreateEvent && debugOutput(DebugLevel.MED)) {
        	RoleCreateEvent roleCreateEvent = (RoleCreateEvent) event;
        	System.out.printf("RoleCreateEvent (%s) [%s] <%s>\n", roleCreateEvent.getGuild(), roleCreateEvent.getRole().getName(), roleCreateEvent.getResponseNumber());

        }
        else if (event instanceof RoleDeleteEvent && debugOutput(DebugLevel.MED)) {
        	RoleDeleteEvent roleDeleteEvent = (RoleDeleteEvent) event;
        	System.out.printf("RoleDeleteEvent (%s) [%s] <%s>\n", roleDeleteEvent.getGuild(), roleDeleteEvent.getRole().getName(), roleDeleteEvent.getResponseNumber());

        }

        //Role Update Events
        else if (event instanceof RoleUpdateColorEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdateColorEvent roleUpdateColorEvent = (RoleUpdateColorEvent) event;
        	System.out.printf("RoleUpdateColorEvent (%s) [%s] <%s>\n", roleUpdateColorEvent.getGuild(), roleUpdateColorEvent.getRole().getName(), roleUpdateColorEvent.getResponseNumber());

        }
        else if (event instanceof RoleUpdateHoistedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdateHoistedEvent roleUpdateHoistedEvent = (RoleUpdateHoistedEvent) event;
        	System.out.printf("RoleUpdateHoistedEvent (%s) [%s] <%s>\n", roleUpdateHoistedEvent.getGuild(), roleUpdateHoistedEvent.getRole().getName(), roleUpdateHoistedEvent.getResponseNumber());

        }
        else if (event instanceof RoleUpdateMentionableEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdateMentionableEvent roleUpdateMentionableEvent = (RoleUpdateMentionableEvent) event;
        	System.out.printf("RoleUpdateMentionableEvent (%s) [%s] <%s>\n", roleUpdateMentionableEvent.getGuild(), roleUpdateMentionableEvent.getRole().getName(), roleUpdateMentionableEvent.getResponseNumber());

        }
        else if (event instanceof RoleUpdateNameEvent && debugOutput(DebugLevel.MED)) {
        	RoleUpdateNameEvent roleUpdateNameEvent = (RoleUpdateNameEvent) event;
        	System.out.printf("RoleUpdateNameEvent (%s) [%s] <%s>\n", roleUpdateNameEvent.getGuild(), roleUpdateNameEvent.getRole().getName(), roleUpdateNameEvent.getResponseNumber());

        }
        else if (event instanceof RoleUpdatePermissionsEvent && debugOutput(DebugLevel.MED)) {
        	RoleUpdatePermissionsEvent roleUpdatePermissionsEvent = (RoleUpdatePermissionsEvent) event;
        	System.out.printf("RoleUpdatePermissionsEvent (%s) [%s] <%s>\n", roleUpdatePermissionsEvent.getGuild(), roleUpdatePermissionsEvent.getRole().getName(), roleUpdatePermissionsEvent.getResponseNumber());

        }
        else if (event instanceof RoleUpdatePositionEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdatePositionEvent roleUpdatePositionEvent = (RoleUpdatePositionEvent) event;
        	System.out.printf("RoleUpdatePositionEvent (%s) [%s] <%s>\n", roleUpdatePositionEvent.getGuild(), roleUpdatePositionEvent.getRole().getName(), roleUpdatePositionEvent.getResponseNumber());

        }

        //Emote Events
        else if (event instanceof EmoteAddedEvent && debugOutput(DebugLevel.MED)) {
        	EmoteAddedEvent emoteAddedEvent = (EmoteAddedEvent) event;
        	System.out.printf("EmoteAddedEvent, (%s) [%s] <%s>\n", emoteAddedEvent.getGuild().getName(), emoteAddedEvent.getEmote().getName(), emoteAddedEvent.getResponseNumber());

        }
        else if (event instanceof EmoteRemovedEvent && debugOutput(DebugLevel.MED)) {
        	EmoteRemovedEvent emoteRemovedEvent = (EmoteRemovedEvent) event;
        	System.out.printf("EmoteRemovedEvent, (%s) [%s] <%s>\n", emoteRemovedEvent.getGuild().getName(), emoteRemovedEvent.getEmote().getName(), emoteRemovedEvent.getResponseNumber());

        }

        //Emote Update Events
        else if (event instanceof EmoteUpdateNameEvent && debugOutput(DebugLevel.MED)) {
        	EmoteUpdateNameEvent emoteUpdateNameEvent = (EmoteUpdateNameEvent) event;
        	System.out.printf("EmoteUpdateNameEvent, (%s) [%s] <%s>\n", emoteUpdateNameEvent.getGuild().getName(), emoteUpdateNameEvent.getEmote().getName(), emoteUpdateNameEvent.getResponseNumber());

        }
        else if (event instanceof EmoteUpdateRolesEvent && debugOutput(DebugLevel.MED)) {
        	EmoteUpdateRolesEvent emoteUpdateRolesEvent = (EmoteUpdateRolesEvent) event;
        	System.out.printf("EmoteUpdateRolesEvent, (%s) [%s] <%s>\n", emoteUpdateRolesEvent.getGuild().getName(), emoteUpdateRolesEvent.getEmote().getName(), emoteUpdateRolesEvent.getResponseNumber());

        }

    }

    /**
     * Gets debug level.
     *
     * @return the debug level
     */
    public DebugLevel getDebugLevel() {
		return debugLevel;
	}

    /**
     * Sets debug level.
     *
     * @param debugLevel the debug level
     */
    public void setDebugLevel(DebugLevel debugLevel) {
		this.debugLevel = debugLevel;
	}
	
	private boolean debugOutput(DebugLevel eventLevel) {
        return eventLevel.level() <= debugLevel.level();
		
	}
}
