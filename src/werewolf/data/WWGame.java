package werewolf.data;

import werewolf.GameState;

import java.time.Instant;

/**
 * The type Ww game.
 */
public class WWGame {
	private GameState gameState = GameState.IDLE;
	
	//Curves setting for Nighttime 
	private Integer nightTimeX = 190;
    private Integer nightTimeY = 110;
    private Double nightTimePow = 0.8;

    //Setting for Daytime
	private Integer maxDayTime = 120;
	private Integer dayTimeX = 12;
	
	//setting for vote time
	private Integer voteTimeX = 10;
	private Integer maxVoteTime = 240;
	private Integer minVoteTime = 120;

	private int joinTime = 120; //time (in seconds) for join up
	private int minPlayers = 5; // min players
	private int noVoteRounds = 3; // number of rounds in a row a player can skip voting, may change this to vary depending on number of people
	private int nickLimt = 2; // Number of times a player can change nickname
	private int upperNickLimit = 4; //Number of times a player can change nickname before being removed from the game
	
	
	private Integer roundNo = 0;
	private boolean tieGame = true; //boolean to determine if there is a random selection during a tied votes
	private boolean voteChange = false; //boolean to show if the bot should allow vote changes during voting

	private boolean checkingWin = false; //boolean to make sure we're not already tallying votes when game switch GameState
	private boolean hasBans = false; //boolean to check to see if someone been banned from the game.

    private Instant gameStart = null;


    /**
     * Gets game state.
     *
     * @return the gameState
     */
    public GameState getGameState() {
		return gameState;
	}

    /**
     * Sets game state.
     *
     * @param gameState the gameState to set
     */
    public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

    /**
     * Gets night time x.
     *
     * @return the nightTimeX
     */
    public Integer getNightTimeX() {
		return nightTimeX;
	}

    /**
     * Sets night time x.
     *
     * @param nightTimeX the nightTimeX to set
     */
    public void setNightTimeX(Integer nightTimeX) {
		this.nightTimeX = nightTimeX;
	}

    /**
     * Gets night time y.
     *
     * @return the nightTimeY
     */
    public Integer getNightTimeY() {
		return nightTimeY;
	}

    /**
     * Sets night time y.
     *
     * @param nightTimeY the nightTimeY to set
     */
    public void setNightTimeY(Integer nightTimeY) {
		this.nightTimeY = nightTimeY;
	}

    /**
     * Gets night time pow.
     *
     * @return the nightTimePow
     */
    public Double getNightTimePow() {
		return nightTimePow;
	}

    /**
     * Sets night time pow.
     *
     * @param nightTimePow the nightTimePow to set
     */
    public void setNightTimePow(Double nightTimePow) {
		this.nightTimePow = nightTimePow;
	}

    /**
     * Gets max day time.
     *
     * @return the maxDayTime
     */
    public Integer getMaxDayTime() {
		return maxDayTime;
	}

    /**
     * Sets max day time.
     *
     * @param maxDayTime the maxDayTime to set
     */
    public void setMaxDayTime(Integer maxDayTime) {
		this.maxDayTime = maxDayTime;
	}

    /**
     * Gets day time x.
     *
     * @return the dayTimeX
     */
    public Integer getDayTimeX() {
		return dayTimeX;
	}

    /**
     * Sets day time x.
     *
     * @param dayTimeX the dayTimeX to set
     */
    public void setDayTimeX(Integer dayTimeX) {
		this.dayTimeX = dayTimeX;
	}

    /**
     * Gets vote time x.
     *
     * @return the voteTimeX
     */
    public Integer getVoteTimeX() {
		return voteTimeX;
	}

    /**
     * Sets vote time x.
     *
     * @param voteTimeX the voteTimeX to set
     */
    public void setVoteTimeX(Integer voteTimeX) {
		this.voteTimeX = voteTimeX;
	}

    /**
     * Gets max vote time.
     *
     * @return the maxVoteTime
     */
    public Integer getMaxVoteTime() {
		return maxVoteTime;
	}

    /**
     * Sets max vote time.
     *
     * @param maxVoteTime the maxVoteTime to set
     */
    public void setMaxVoteTime(Integer maxVoteTime) {
		this.maxVoteTime = maxVoteTime;
	}

    /**
     * Gets min vote time.
     *
     * @return the minVoteTime
     */
    public Integer getMinVoteTime() {
		return minVoteTime;
	}

    /**
     * Sets min vote time.
     *
     * @param minVoteTime the minVoteTime to set
     */
    public void setMinVoteTime(Integer minVoteTime) {
		this.minVoteTime = minVoteTime;
	}

    /**
     * Gets join time.
     *
     * @return the joinTime
     */
    public int getJoinTime() {
		return joinTime;
	}

    /**
     * Sets join time.
     *
     * @param joinTime the joinTime to set
     */
    public void setJoinTime(int joinTime) {
		this.joinTime = joinTime;
	}

    /**
     * Gets min players.
     *
     * @return the minPlayers
     */
    public int getMinPlayers() {
		return minPlayers;
	}

    /**
     * Sets min players.
     *
     * @param minPlayers the minPlayers to set
     */
    public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

    /**
     * Gets no vote rounds.
     *
     * @return the noVoteRounds
     */
    public int getNoVoteRounds() {
		return noVoteRounds;
	}

    /**
     * Sets no vote rounds.
     *
     * @param noVoteRounds the noVoteRounds to set
     */
    public void setNoVoteRounds(int noVoteRounds) {
		this.noVoteRounds = noVoteRounds;
	}

    /**
     * Gets nick limt.
     *
     * @return the nickLimt
     */
    public int getNickLimt() {
		return nickLimt;
	}

    /**
     * Sets nick limt.
     *
     * @param nickLimt the nickLimt to set
     */
    public void setNickLimt(int nickLimt) {
		this.nickLimt = nickLimt;
	}

    /**
     * Gets upper nick limit.
     *
     * @return the upperNickLimit
     */
    public int getUpperNickLimit() {
		return upperNickLimit;
	}

    /**
     * Sets upper nick limit.
     *
     * @param upperNickLimit the upperNickLimit to set
     */
    public void setUpperNickLimit(int upperNickLimit) {
		this.upperNickLimit = upperNickLimit;
	}

    /**
     * Gets round no.
     *
     * @return the roundNo
     */
    public Integer getRoundNo() {
		return roundNo;
	}

    /**
     * Sets round no.
     *
     * @param roundNo the roundNo to set
     */
    public void setRoundNo(Integer roundNo) {
		this.roundNo = roundNo;
	}

    /**
     * Is tie game boolean.
     *
     * @return the tieGame
     */
    public boolean isTieGame() {
		return tieGame;
	}

    /**
     * Sets tie game.
     *
     * @param tieGame the tieGame to set
     */
    public void setTieGame(boolean tieGame) {
		this.tieGame = tieGame;
	}

    /**
     * Is vote change boolean.
     *
     * @return the voteChange
     */
    public boolean isVoteChange() {
		return voteChange;
	}

    /**
     * Sets vote change.
     *
     * @param voteChange the voteChange to set
     */
    public void setVoteChange(boolean voteChange) {
		this.voteChange = voteChange;
	}

    /**
     * Is checking win boolean.
     *
     * @return the checkingWin
     */
    public boolean isCheckingWin() {
		return checkingWin;
	}

    /**
     * Sets checking win.
     *
     * @param checkingWin the checkingWin to set
     */
    public void setCheckingWin(boolean checkingWin) {
		this.checkingWin = checkingWin;
	}

    /**
     * Has bans boolean.
     *
     * @return the hasBans
     */
    public boolean hasBans() {
		return hasBans;
	}

    /**
     * Sets has bans.
     *
     * @param hasBans the hasBans to set
     */
    public void setHasBans(boolean hasBans) {
		this.hasBans = hasBans;
	}

    /**
     * Inc game round.
     */
    public void incGameRound() {
		this.roundNo++;
	}

    public Instant getGameStart() {
        return gameStart;
    }

    public void setGameStart(Instant gameStart) {
        this.gameStart = gameStart;
    }
}
