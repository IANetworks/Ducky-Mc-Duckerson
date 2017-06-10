package bot.database.manager.data;

public class ConfigDB {
	private Long guildId = null;
	private String prefix = null;
	private String greeting = null;
	private String greetingChannel = null;
	private String loggingChannel = null;
	
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
