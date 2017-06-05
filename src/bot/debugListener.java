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
import net.dv8tion.jda.core.events.message.*;
import net.dv8tion.jda.core.events.message.guild.*;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveAllEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.message.priv.*;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionRemoveAllEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.events.role.RoleDeleteEvent;
import net.dv8tion.jda.core.events.role.update.*;
import net.dv8tion.jda.core.events.self.*;
import net.dv8tion.jda.core.events.user.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class debugListener extends ListenerAdapter {
	
	/**
	 * We'll put all the debug output log here. As there's a lot of event to look at, we'll tuck it all here for a tidier code.
	 * Here we'll define what level debug each event is.
	 * 
	 * TODO Finish off the debugs events
	 */
	private DebugLevel debugLevel;

	public debugListener(DebugLevel debugLevel) {
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
            
        else if (event instanceof ReconnectedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	ReconnectedEvent reconnectedEvent = (ReconnectedEvent) event;
        	System.out.printf("ReconnectedEvent <%s>\n", reconnectedEvent.getResponseNumber());
        }       	
            
        else if (event instanceof DisconnectEvent && debugOutput(DebugLevel.VERBOSE)) {
        	DisconnectEvent disconnectEvent = (DisconnectEvent) event;
        	System.out.printf("DisconnectEvent, by server[%s] at [%s] <%s>\n",disconnectEvent.isClosedByServer(), disconnectEvent.getDisconnectTime(), disconnectEvent.getResponseNumber());
        	
        }
            
        else if (event instanceof ShutdownEvent && debugOutput(DebugLevel.VERBOSE)) {
        	ShutdownEvent shutDownEvent = (ShutdownEvent) event;
        	System.out.printf("ShutDownEvent at [%s] <%s>\n", shutDownEvent.getShutdownTime(), shutDownEvent.getResponseNumber());
        }
            
        else if (event instanceof StatusChangeEvent && debugOutput(DebugLevel.VERBOSE)) {
        	StatusChangeEvent statusChangeEvent = (StatusChangeEvent) event;
        	System.out.printf("StatusChangeEvent Status [%s] to [%s] <%s>\n", statusChangeEvent.getOldStatus(), statusChangeEvent.getStatus(), statusChangeEvent.getResponseNumber());
        }
            
        else if (event instanceof ExceptionEvent && debugOutput(DebugLevel.VERBOSE)) {
        	ExceptionEvent exceptionEvent = (ExceptionEvent) event;
        	System.out.printf("ExceptionEvent cause: %s <%s>\n", exceptionEvent.getCause().toString(), exceptionEvent.getResponseNumber());
        	

        }
            

        //Message Events
        //Guild (TextChannel) Message Events
        else if (event instanceof GuildMessageReceivedEvent && debugOutput(DebugLevel.LOW)) {
        	GuildMessageReceivedEvent guildMessageReceivedEvent= (GuildMessageReceivedEvent) event;
        	System.out.printf("guildMessageRecievedEvent (%s)[%s]<%s> %s <%s>", guildMessageReceivedEvent.getGuild().getName(), guildMessageReceivedEvent.getChannel().getName(), 
        			guildMessageReceivedEvent.getAuthor().getName(), guildMessageReceivedEvent.getMessage().getContent());

       }
        else if (event instanceof GuildMessageUpdateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageUpdateEvent guildMessageUpdateEvent = (GuildMessageUpdateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildMessageDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageDeleteEvent guildMessageDeleteEvent = (GuildMessageDeleteEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildMessageEmbedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageEmbedEvent guildMessageEmbedEvent = (GuildMessageEmbedEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildMessageReactionAddEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageReactionAddEvent guildMessageReactionAddEvent = (GuildMessageReactionAddEvent) event;
        	System.out.printf("");

    	}
        else if (event instanceof GuildMessageReactionRemoveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageReactionRemoveEvent guildMessageReactionRemoveEvent = (GuildMessageReactionRemoveEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildMessageReactionRemoveAllEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMessageReactionRemoveAllEvent guildMessageReactionRemoveAllEvent = (GuildMessageReactionRemoveAllEvent) event;
        	System.out.printf("");

        }

        //Private Message Events
        else if (event instanceof PrivateMessageReceivedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageReceivedEvent privateMessageReceivedEvent= (PrivateMessageReceivedEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof PrivateMessageUpdateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageUpdateEvent privateMessageUpdateEvent = (PrivateMessageUpdateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof PrivateMessageDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageDeleteEvent privateMessageDeleteEvent = (PrivateMessageDeleteEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof PrivateMessageEmbedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageEmbedEvent privateMessageEmbedEvent = (PrivateMessageEmbedEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof PrivateMessageReactionAddEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageReactionAddEvent privateMessageReactionAddEvent = (PrivateMessageReactionAddEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof PrivateMessageReactionRemoveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageReactionRemoveEvent privateMessageReactionRemoveEvent = (PrivateMessageReactionRemoveEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof PrivateMessageReactionRemoveAllEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateMessageReactionRemoveAllEvent privateMessageReactionRemoveAllEvent = (PrivateMessageReactionRemoveAllEvent) event;
        	System.out.printf("");

        }

        //Combined Message Events (Combines Guild and Private message into 1 event)
        else if (event instanceof MessageReceivedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof MessageUpdateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	MessageUpdateEvent messageUpdateEvent = (MessageUpdateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof MessageDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	MessageDeleteEvent messageDeleteEvent = (MessageDeleteEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof MessageBulkDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	MessageBulkDeleteEvent messageBulkDeleteEvent = (MessageBulkDeleteEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof MessageEmbedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	MessageEmbedEvent messageEmbedEvent = (MessageEmbedEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof MessageReactionAddEvent && debugOutput(DebugLevel.VERBOSE)) {
        	MessageReactionAddEvent messageReactionAddEvent = (MessageReactionAddEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof MessageReactionRemoveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	MessageReactionRemoveEvent messageReactionRemoveEvent = (MessageReactionRemoveEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof MessageReactionRemoveAllEvent && debugOutput(DebugLevel.VERBOSE)) {
        	MessageReactionRemoveAllEvent messageReactionRemoveAllEvent = (MessageReactionRemoveAllEvent) event;
        	System.out.printf("");

        }
//        //Invite Messages
//        else if (event instanceof InviteReceivedEvent)
//            onInviteReceived(((InviteReceivedEvent) event);

        //User Events
        else if (event instanceof UserNameUpdateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	UserNameUpdateEvent userNameUpdateEvent = (UserNameUpdateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof UserAvatarUpdateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	UserAvatarUpdateEvent userAvatarUpdateEvent = (UserAvatarUpdateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof UserGameUpdateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	UserGameUpdateEvent userGameUpdateEvent = (UserGameUpdateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof UserOnlineStatusUpdateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	UserOnlineStatusUpdateEvent userOnlineStatusUpdateEvent = (UserOnlineStatusUpdateEvent) event;}
        else if (event instanceof UserTypingEvent && debugOutput(DebugLevel.VERBOSE)) {
        	UserTypingEvent userTypingEvent = (UserTypingEvent) event;
        	System.out.printf("");

        }

        //Self Events
        else if (event instanceof SelfUpdateAvatarEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateAvatarEvent selfUpdateAvatarEvent = (SelfUpdateAvatarEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof SelfUpdateEmailEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateEmailEvent selfUpdateEmailEvent = (SelfUpdateEmailEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof SelfUpdateMFAEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateMFAEvent selfUpdateMFAEvent = (SelfUpdateMFAEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof SelfUpdateNameEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateNameEvent selfUpdateNameEvent = (SelfUpdateNameEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof SelfUpdateVerifiedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	SelfUpdateVerifiedEvent selfUpdateVerifiedEvent = (SelfUpdateVerifiedEvent) event;
        	System.out.printf("");

        }

        //TextChannel Events
        else if (event instanceof TextChannelCreateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelCreateEvent textChannelCreateEvent = (TextChannelCreateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof TextChannelUpdateNameEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelUpdateNameEvent textChannelUpdateNameEvent = (TextChannelUpdateNameEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof TextChannelUpdateTopicEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelUpdateTopicEvent textChannelUpdateTopicEvent = (TextChannelUpdateTopicEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof TextChannelUpdatePositionEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelUpdatePositionEvent textChannelUpdatePositionEvent = (TextChannelUpdatePositionEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof TextChannelDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelDeleteEvent textChannelDeleteEvent = (TextChannelDeleteEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof TextChannelUpdatePermissionsEvent && debugOutput(DebugLevel.VERBOSE)) {
        	TextChannelUpdatePermissionsEvent textChannelUpdatePermissionsEvent = (TextChannelUpdatePermissionsEvent) event;
        	System.out.printf("");

        }

        //VoiceChannel Events
        else if (event instanceof VoiceChannelCreateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	VoiceChannelCreateEvent voiceChannelCreateEvent = (VoiceChannelCreateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof VoiceChannelUpdateNameEvent && debugOutput(DebugLevel.VERBOSE)) {
        	VoiceChannelUpdateNameEvent voiceChannelUpdateNameEvent = (VoiceChannelUpdateNameEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof VoiceChannelUpdatePositionEvent && debugOutput(DebugLevel.VERBOSE)) {
        	VoiceChannelUpdatePositionEvent voiceChannelUpdatePositionEvent= (VoiceChannelUpdatePositionEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof VoiceChannelUpdateUserLimitEvent && debugOutput(DebugLevel.VERBOSE)) {
        	VoiceChannelUpdateUserLimitEvent voiceChannelUpdateUserLimitEvent = (VoiceChannelUpdateUserLimitEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof VoiceChannelUpdateBitrateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	VoiceChannelUpdateBitrateEvent voiceChannelUpdateBitrateEvent = (VoiceChannelUpdateBitrateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof VoiceChannelUpdatePermissionsEvent && debugOutput(DebugLevel.VERBOSE)) {
        	VoiceChannelUpdatePermissionsEvent voiceChannelUpdatePermissionsEvent = (VoiceChannelUpdatePermissionsEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof VoiceChannelDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	VoiceChannelDeleteEvent voiceChannelDeleteEvent= (VoiceChannelDeleteEvent) event;
        	System.out.printf("");

        }

        //PrivateChannel Events
        else if (event instanceof PrivateChannelCreateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateChannelCreateEvent privateChannelCreateEvent= (PrivateChannelCreateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof PrivateChannelDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	PrivateChannelDeleteEvent privateChannelDeleteEvent = (PrivateChannelDeleteEvent) event;
        	System.out.printf("");

        }

        //Guild Events
        else if (event instanceof GuildJoinEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildJoinEvent guildJoinEvent = (GuildJoinEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildLeaveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildLeaveEvent guildLeaveEvent = (GuildLeaveEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildAvailableEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildAvailableEvent guildAvailableEvent = (GuildAvailableEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUnavailableEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUnavailableEvent guildUnavailableEvent = (GuildUnavailableEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof UnavailableGuildJoinedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	UnavailableGuildJoinedEvent unavailableGuildJoinedEvent = (UnavailableGuildJoinedEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildBanEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildBanEvent guildBanEvent = (GuildBanEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUnbanEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUnbanEvent guildUnbanEvent = (GuildUnbanEvent) event;
        	System.out.printf("");

        }

        //Guild Update Events
        else if (event instanceof GuildUpdateAfkChannelEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateAfkChannelEvent guildUpdateAfkChannelEvent= (GuildUpdateAfkChannelEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateAfkTimeoutEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateAfkTimeoutEvent guildUpdateAfkTimeoutEvent = (GuildUpdateAfkTimeoutEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateIconEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateIconEvent guildUpdateIconEvent = (GuildUpdateIconEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateMFALevelEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateMFALevelEvent guildUpdateMFALevelEvent= (GuildUpdateMFALevelEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateNameEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateNameEvent guildUpdateNameEvent = (GuildUpdateNameEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateNotificationLevelEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateNotificationLevelEvent guildUpdateNotificationLevelEvent = (GuildUpdateNotificationLevelEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateOwnerEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateOwnerEvent guildUpdateOwnerEvent = (GuildUpdateOwnerEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateRegionEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateRegionEvent guildUpdateRegionEvent = (GuildUpdateRegionEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateSplashEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateSplashEvent guildUpdateSplashEvent = (GuildUpdateSplashEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildUpdateVerificationLevelEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildUpdateVerificationLevelEvent guildUpdateVerificationLevelEvent = (GuildUpdateVerificationLevelEvent) event;
        	System.out.printf("");

        }

        //Guild Member Events
        else if (event instanceof GuildMemberJoinEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMemberJoinEvent guildMemberJoinEvent = (GuildMemberJoinEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildMemberLeaveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMemberLeaveEvent guildMemberLeaveEvent = (GuildMemberLeaveEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildMemberRoleAddEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMemberRoleAddEvent guildMemberRoleAddEvent = (GuildMemberRoleAddEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildMemberRoleRemoveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMemberRoleRemoveEvent guildMemberRoleRemoveEvent = (GuildMemberRoleRemoveEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildMemberNickChangeEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildMemberNickChangeEvent guildMemberNickChangeEvent =(GuildMemberNickChangeEvent) event;
        	System.out.printf("");

        }

        //Guild Voice Events
        else if (event instanceof GuildVoiceJoinEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceJoinEvent guildVoiceJoinEvent = (GuildVoiceJoinEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceMoveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceMoveEvent guildVoiceMoveEvent = (GuildVoiceMoveEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceLeaveEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceLeaveEvent guildVoiceLeaveEvent= (GuildVoiceLeaveEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceMuteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceMuteEvent guildVoiceMuteEvent = (GuildVoiceMuteEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceDeafenEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceDeafenEvent guildVoiceDeafenEvent = (GuildVoiceDeafenEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceGuildMuteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceGuildMuteEvent guildVoiceGuildMuteEvent = (GuildVoiceGuildMuteEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceGuildDeafenEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceGuildDeafenEvent guildVoiceGuildDeafenEvent = (GuildVoiceGuildDeafenEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceSelfMuteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceSelfMuteEvent guildVoiceSelfMuteEvent = (GuildVoiceSelfMuteEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceSelfDeafenEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceSelfDeafenEvent guildVoiceSelfDeafenEvent = (GuildVoiceSelfDeafenEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof GuildVoiceSuppressEvent && debugOutput(DebugLevel.VERBOSE)) {
        	GuildVoiceSuppressEvent guildVoiceSuppressEvent = (GuildVoiceSuppressEvent) event;
        	System.out.printf("");

        }

        //Role Events
        else if (event instanceof RoleCreateEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleCreateEvent roleCreateEvent = (RoleCreateEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof RoleDeleteEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleDeleteEvent roleDeleteEvent = (RoleDeleteEvent) event;
        	System.out.printf("");

        }

        //Role Update Events
        else if (event instanceof RoleUpdateColorEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdateColorEvent roleUpdateColorEvent = (RoleUpdateColorEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof RoleUpdateHoistedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdateHoistedEvent roleUpdateHoistedEvent = (RoleUpdateHoistedEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof RoleUpdateMentionableEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdateMentionableEvent roleUpdateMentionableEvent = (RoleUpdateMentionableEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof RoleUpdateNameEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdateNameEvent roleUpdateNameEvent = (RoleUpdateNameEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof RoleUpdatePermissionsEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdatePermissionsEvent roleUpdatePermissionsEvent = (RoleUpdatePermissionsEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof RoleUpdatePositionEvent && debugOutput(DebugLevel.VERBOSE)) {
        	RoleUpdatePositionEvent roleUpdatePositionEvent = (RoleUpdatePositionEvent) event;
        	System.out.printf("");

        }

        //Emote Events
        else if (event instanceof EmoteAddedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	EmoteAddedEvent emoteAddedEvent = (EmoteAddedEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof EmoteRemovedEvent && debugOutput(DebugLevel.VERBOSE)) {
        	EmoteRemovedEvent emoteRemovedEvent = (EmoteRemovedEvent) event;
        	System.out.printf("");

        }

        //Emote Update Events
        else if (event instanceof EmoteUpdateNameEvent && debugOutput(DebugLevel.VERBOSE)) {
        	EmoteUpdateNameEvent emoteUpdateNameEvent = (EmoteUpdateNameEvent) event;
        	System.out.printf("");

        }
        else if (event instanceof EmoteUpdateRolesEvent && debugOutput(DebugLevel.LOW)) {
        	EmoteUpdateRolesEvent emoteUpdateRolesEvent = (EmoteUpdateRolesEvent) event;
        	System.out.printf("");

        }

        }

	public DebugLevel getDebugLevel() {
		return debugLevel;
	}

	public void setDebugLevel(DebugLevel debugLevel) {
		this.debugLevel = debugLevel;
	}
	
	private boolean debugOutput(DebugLevel eventLevel) {
		if (eventLevel.level() <= debugLevel.level()) {
			return true;
		} else { 
			return false;
		}
		
	}
}
