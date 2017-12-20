package io.github.mannjamin.ducky.database.manager.data;

/**
 * The type Guild setting.
 */
public class GuildSetting {
    private Long guildId = null;
    private String prefix = null;
    private String greeting = null;
    private String greetingChannel = null;
    private String loggingChannel = null;
    private Boolean loggingOn = false;
    private Boolean greetOn = false;
    private String gameChannel = null;
    private Boolean wwOn = true;
    private Boolean deleteCommand = false;
    /**
     * The Is stored.
     */
    public boolean isStored = false;
    private Boolean eventOn = false;
    private String eventChannel = null;

    /**
     * Is ww on boolean.
     *
     * @return the boolean
     */
    public Boolean isWWOn() {
        return wwOn;
    }

    /**
     * Set ww on.
     *
     * @param wwOn the ww on
     */
    public void setWWOn(Boolean wwOn) {
        this.wwOn = wwOn;
    }

    /**
     * Gets channel.
     *
     * @return the channel
     */
    public String getgameChannel() {
        return gameChannel;
    }

    /**
     * Sets game channel.
     *
     * @param gameChannel the game channel
     */
    public void setGameChannel(String gameChannel) {
        this.gameChannel = gameChannel;
    }

    /**
     * Is logging on boolean.
     *
     * @return the boolean
     */
    public Boolean isLoggingOn() {
        return loggingOn;
    }

    /**
     * Sets logging on.
     *
     * @param loggingOn the logging on
     */
    public void setLoggingOn(Boolean loggingOn) {
        this.loggingOn = loggingOn;
    }

    /**
     * Is greet on boolean.
     *
     * @return the boolean
     */
    public Boolean isGreetOn() {
        return greetOn;
    }

    /**
     * Sets greet on.
     *
     * @param greetOn the greet on
     */
    public void setGreetOn(Boolean greetOn) {
        this.greetOn = greetOn;
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets prefix.
     *
     * @param prefix the prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Gets greeting.
     *
     * @return the greeting
     */
    public String getGreeting() {
        return greeting;
    }

    /**
     * Sets greeting.
     *
     * @param greeting the greeting
     */
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    /**
     * Gets greeting channel.
     *
     * @return the greeting channel
     */
    public String getGreetingChannel() {
        return greetingChannel;
    }

    /**
     * Sets greeting channel.
     *
     * @param greetingChannel the greeting channel
     */
    public void setGreetingChannel(String greetingChannel) {
        this.greetingChannel = greetingChannel;
    }

    /**
     * Gets logging channel.
     *
     * @return the logging channel
     */
    public String getLoggingChannel() {
        return loggingChannel;
    }

    /**
     * Sets logging channel.
     *
     * @param loggingChannel the logging channel
     */
    public void setLoggingChannel(String loggingChannel) {
        this.loggingChannel = loggingChannel;
    }

    /**
     * Gets guild id.
     *
     * @return the guild id
     */
    public Long getGuildId() {
        return guildId;
    }

    /**
     * Sets guild id.
     *
     * @param guildId the guild id
     */
    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public void setDeleteCommand(boolean deleteCommand) {
        this.deleteCommand = deleteCommand;
    }

    public Boolean getDeleteCommand() {
        return deleteCommand;
    }

    public void setEventChannel(String eventChannel) {
        this.eventChannel = eventChannel;
    }

    public void setEventOn(Boolean eventOn) {
        this.eventOn = eventOn;
    }

    public String getEventChannel() {
        return eventChannel;
    }

    public Boolean isEventOn() {
        return eventOn;
    }
}
