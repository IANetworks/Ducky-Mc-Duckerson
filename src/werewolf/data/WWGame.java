package werewolf.data;

import werewolf.GameState;

public class WWGame {
	private GameState gameState = GameState.IDLE;
	
	//Curves setting for Nighttime 
	private Integer nightTimeX = 190;
	private Integer nightTimeY = 150;
	private Double nightTimePow = 1.0;
	
	//Setting for Daytime
	private Integer maxDayTime = 120;
	private Integer dayTimeX = 10;
	
	//setting for vote time
	private Integer voteTimeX = 10;
	private Integer maxVoteTime = 200;
	private Integer minVoteTime = 60;
	
	private int joinTime = 90; //time (in seconds) for join up
	private int minPlayers = 5; // min players
	private int noVoteRounds = 2; // number of rounds in a row a player can skip voting, may change this to vary depending on number of people
	private int nickLimt = 2; // Number of times a player can change nickname
	private int upperNickLimit = 4; //Number of times a player can change nickname before being removed from the game
	
	
	private Integer roundNo = 0;
	private boolean tieGame = true; //boolean to determine if there is a random selection during a tied votes
	private boolean voteChange = false; //boolean to show if the bot should allow vote changes during voting

	private boolean checkingWin = false; //boolean to make sure we're not already tallying votes when game switch GameState
	private boolean hasBans = false; //boolean to check to see if someone been banned from the game.

	
	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @param gameState the gameState to set
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * @return the nightTimeX
	 */
	public Integer getNightTimeX() {
		return nightTimeX;
	}

	/**
	 * @param nightTimeX the nightTimeX to set
	 */
	public void setNightTimeX(Integer nightTimeX) {
		this.nightTimeX = nightTimeX;
	}

	/**
	 * @return the nightTimeY
	 */
	public Integer getNightTimeY() {
		return nightTimeY;
	}

	/**
	 * @param nightTimeY the nightTimeY to set
	 */
	public void setNightTimeY(Integer nightTimeY) {
		this.nightTimeY = nightTimeY;
	}

	/**
	 * @return the nightTimePow
	 */
	public Double getNightTimePow() {
		return nightTimePow;
	}

	/**
	 * @param nightTimePow the nightTimePow to set
	 */
	public void setNightTimePow(Double nightTimePow) {
		this.nightTimePow = nightTimePow;
	}

	/**
	 * @return the maxDayTime
	 */
	public Integer getMaxDayTime() {
		return maxDayTime;
	}

	/**
	 * @param maxDayTime the maxDayTime to set
	 */
	public void setMaxDayTime(Integer maxDayTime) {
		this.maxDayTime = maxDayTime;
	}

	/**
	 * @return the dayTimeX
	 */
	public Integer getDayTimeX() {
		return dayTimeX;
	}

	/**
	 * @param dayTimeX the dayTimeX to set
	 */
	public void setDayTimeX(Integer dayTimeX) {
		this.dayTimeX = dayTimeX;
	}

	/**
	 * @return the voteTimeX
	 */
	public Integer getVoteTimeX() {
		return voteTimeX;
	}

	/**
	 * @param voteTimeX the voteTimeX to set
	 */
	public void setVoteTimeX(Integer voteTimeX) {
		this.voteTimeX = voteTimeX;
	}

	/**
	 * @return the maxVoteTime
	 */
	public Integer getMaxVoteTime() {
		return maxVoteTime;
	}

	/**
	 * @param maxVoteTime the maxVoteTime to set
	 */
	public void setMaxVoteTime(Integer maxVoteTime) {
		this.maxVoteTime = maxVoteTime;
	}

	/**
	 * @return the minVoteTime
	 */
	public Integer getMinVoteTime() {
		return minVoteTime;
	}

	/**
	 * @param minVoteTime the minVoteTime to set
	 */
	public void setMinVoteTime(Integer minVoteTime) {
		this.minVoteTime = minVoteTime;
	}

	/**
	 * @return the joinTime
	 */
	public int getJoinTime() {
		return joinTime;
	}

	/**
	 * @param joinTime the joinTime to set
	 */
	public void setJoinTime(int joinTime) {
		this.joinTime = joinTime;
	}

	/**
	 * @return the minPlayers
	 */
	public int getMinPlayers() {
		return minPlayers;
	}

	/**
	 * @param minPlayers the minPlayers to set
	 */
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	/**
	 * @return the noVoteRounds
	 */
	public int getNoVoteRounds() {
		return noVoteRounds;
	}

	/**
	 * @param noVoteRounds the noVoteRounds to set
	 */
	public void setNoVoteRounds(int noVoteRounds) {
		this.noVoteRounds = noVoteRounds;
	}

	/**
	 * @return the nickLimt
	 */
	public int getNickLimt() {
		return nickLimt;
	}

	/**
	 * @param nickLimt the nickLimt to set
	 */
	public void setNickLimt(int nickLimt) {
		this.nickLimt = nickLimt;
	}

	/**
	 * @return the upperNickLimit
	 */
	public int getUpperNickLimit() {
		return upperNickLimit;
	}

	/**
	 * @param upperNickLimit the upperNickLimit to set
	 */
	public void setUpperNickLimit(int upperNickLimit) {
		this.upperNickLimit = upperNickLimit;
	}

	/**
	 * @return the roundNo
	 */
	public Integer getRoundNo() {
		return roundNo;
	}

	/**
	 * @param roundNo the roundNo to set
	 */
	public void setRoundNo(Integer roundNo) {
		this.roundNo = roundNo;
	}

	/**
	 * @return the tieGame
	 */
	public boolean isTieGame() {
		return tieGame;
	}

	/**
	 * @param tieGame the tieGame to set
	 */
	public void setTieGame(boolean tieGame) {
		this.tieGame = tieGame;
	}

	/**
	 * @return the voteChange
	 */
	public boolean isVoteChange() {
		return voteChange;
	}

	/**
	 * @param voteChange the voteChange to set
	 */
	public void setVoteChange(boolean voteChange) {
		this.voteChange = voteChange;
	}

	/**
	 * @return the checkingWin
	 */
	public boolean isCheckingWin() {
		return checkingWin;
	}

	/**
	 * @param checkingWin the checkingWin to set
	 */
	public void setCheckingWin(boolean checkingWin) {
		this.checkingWin = checkingWin;
	}

	/**
	 * @return the hasBans
	 */
	public boolean hasBans() {
		return hasBans;
	}

	/**
	 * @param hasBans the hasBans to set
	 */
	public void setHasBans(boolean hasBans) {
		this.hasBans = hasBans;
	}

	

	public WWGame() {
		// TODO Auto-generated constructor stub
	}

	public void incGameRound() {
		this.roundNo++;
	}
}
