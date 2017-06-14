package bot.database.manager.data;

public class GuildSetting {
	private Long guildId = null;
	private String prefix = null;
	private String greeting = null;
	private String greetingChannel = null;
	private String loggingChannel = null;
	private Boolean loggingOn = false;
	private Boolean greetOn = false;
	public boolean isStored = false;
	
	public Boolean isLoggingOn() {
		return loggingOn;
	}
	public void setLoggingOn(Boolean loggingOn) {
		this.loggingOn = loggingOn;
	}
	public Boolean isGreetOn() {
		return greetOn;
	}
	public void setGreetOn(Boolean greetOn) {
		this.greetOn = greetOn;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	public String getGreetingChannel() {
		return greetingChannel;
	}
	public void setGreetingChannel(String greetingChannel) {
		this.greetingChannel = greetingChannel;
	}
	public String getLoggingChannel() {
		return loggingChannel;
	}
	public void setLoggingChannel(String loggingChannel) {
		this.loggingChannel = loggingChannel;
	}
	public Long getGuildId() {
		return guildId;
	}
	public void setGuildId(Long guildId) {
		this.guildId = guildId;
	}
	
}
