package werewolf.data;

import net.dv8tion.jda.core.entities.Member;

/*
 * Player.java
 *
 * Created on 15 September 2007, 16:43
 *
 *Player Object to store information about each player
 */

//import enums.*;

public class Player {
	Member player = null; //Hold the User object from Discord. 
	
	Player voteFor = null;
	Role role = Role.NOROLE; // Contain an int for Role
	PlayerState playerState = PlayerState.ALIVE; // Contain an int for playerState, 1 playerState, 2 dead, 3 fled
	
	int nickCount = 0;
	int nonVoteCount = 0; // Number of times a Player has not voted for someone
	//int playerID = -1; // PlayerID Number, for easier reference for players ID in Database
	int playerNo = 0;  //Player Number, for giving the player a number to identify with
	int voteCount = 0;
	
	boolean isBanned = false;
	boolean roleRecieved = false;

	public boolean isRoleRecieved() {
		return roleRecieved;
	}

	public void setRoleRecieved(boolean roleRecieved) {
		this.roleRecieved = roleRecieved;
	}


	
	/** Creates a new instance of Player */
	/**
	 * 
	 * @param player
	 * @param playerNo
	 */
	public Player(Member player, int playerNo) {
		setPlayer(player);
		setPlayerNo(playerNo);
		setRole(Role.NOROLE);
		setPlayerState(PlayerState.ALIVE);
		setNonVoteCount(0);
	}
	
	
	private void setPlayer(Member player) {
		this.player = player;
		
	}
	
	public String getEffectiveName() {
		if(player != null)
			return player.getEffectiveName();
		else
			return "-";
	}
	
	public Long getUserID() {
		if(player == null)
		{
			return 0L;
		}
		return player.getUser().getIdLong();
	}
	
	public Member getMember() {
		return player;
	}


	public void setIsBanned(boolean isBanned)
	{
		this.isBanned = isBanned;
	}
	
	public boolean getIsBanned()
	{
		return isBanned;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNickCount()
	{
		return nickCount; 
	}
	
	
	/**
	 * 
	 * @param playerNo
	 */
	public void setPlayerNo(int playerNo)
	{
		this.playerNo = playerNo;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPlayerNo()
	{
		return this.playerNo;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setVoteFor(Player name)
	{
		this.voteFor = name;
	}
	
	/**
	 * 
	 * @return
	 */
	public Player getVoteFor()
	{
		return this.voteFor;

	}
	
	public Integer getVoteCount()
	{
		return this.voteCount;
	}
	
	public void addVoteCount()
	{
		this.voteCount ++;
	}
	
	/**
	 * 
	 */
	public void resetVote()
	{
		this.voteFor = null;
		this.voteCount = 0;
	}
	
	public void resetVoteCount()
	{
		this.voteCount = 0;
	}
	
	/**
	 * 
	 * @param nonVoteCount
	 */
	public void setNonVoteCount(int nonVoteCount) {
		this.nonVoteCount = nonVoteCount;
	}

	/**
	 * 
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;

	}

	/**
	 * 
	 * @param playerState
	 */
	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
//		if(playerState == PlayerState.FLED) {
//			this.addNoFled(1);
//		}
	}

	/**
	 * 
	 * @return
	 */
	public PlayerState getPlayerState() {
		return playerState;
	}
	
	/**
	 * 
	 * @param playerID
	 */
//	public void setId(int playerID) {
//		this.playerID = playerID;
//	}
	
	/**
	 * 
	 * @return
	 */
//	public int getPlayerId()
//	{
//		return playerID;
//	}

	/**
	 * 
	 * @return
	 */
	public int getNonVoteCount() {
		return nonVoteCount;
	}

	/**
	 * 
	 * @return
	 */
	public Role getRole() {
		return role;
	}

	 

//	/**
//	 * 
//	 * @param noSeer
//	 */
//	public void addNoSeer(int intNoSeer)
//	{
//		this.noSeer = this.noSeer + intNoSeer;
//	}
//	
//	/**
//	 * 
//	 * @param noVill
//	 */
//	public void addNoVill(int intNoVill)
//	{
//		this.noVill = this.noVill + intNoVill;
//	}
//	
//	/**
//	 * 
//	 * @param noWolf
//	 */
//	public void addNoWolf(int intNoWolf)
//	{
//		this.noWolf = this.noWolf + intNoWolf;
//	}
//	
//	public void addNoMason(int intNoMason)
//	{
//		this.noMason = this.noMason + intNoMason;
//	}
//	/**
//	 * 
//	 * @param noKilled
//	 */
//	public void addNoKilled(int intNoKilled)
//	{
//		this.noKilled = this.noKilled + intNoKilled;
//	}
//	
//	/**
//	 * 
//	 * @param noLynced
//	 */
//	public void addNoLynched(int intNoLynced)
//	{
//		this.noLynced = this.noLynced + intNoLynced;
//	}
//	
//	/**
//	 * 
//	 * @param noWolfVote
//	 */
//	public void addNoWolfVote(int intNoWolfVote)
//	{
//		this.noWolfVote = this.noWolfVote + intNoWolfVote;
//	}
//	
//	/**
//	 * 
//	 * @param noNonWolfVote
//	 */
//	public void addNoNonWolfVote(int intNoNonWolfVote)
//	{
//		this.noNonWolfVote = this.noNonWolfVote + intNoNonWolfVote;
//	}
//		
//	/**
//	 * 
//	 * @param noVoted
//	 */
//	public void addNoVoted(int intVoteCount)
//	{
//		this.noVoted = this.noVoted + intVoteCount;
//	}
//	
//	/**
//	 * 
//	 * @param noFled
//	 */
//	public void addNoFled(int intNoFled)
//	{
//		this.noFled = this.noFled + intNoFled;
//	}
//	
//	public void setNoRole(Role role)
//	{
//		switch(role)
//		{
//			case SEER:
//				this.addNoSeer(1);
//				break;
//				
//			case WOLF:
//				this.addNoWolf(1);
//				break;
//				
//			case VILL:
//				this.addNoVill(1);
//				break;
//			case MASON:
//				this.addNoMason(1);
//				break;
//			default:
//				break;
//		}
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoSeer()
//	{
//		return this.noSeer;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoVill()
//	{
//		return this.noVill;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoWolf()
//	{
//		return this.noWolf;
//	}
//
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoMason()
//	{
//		return this.noMason;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoKilled()
//	{
//		return this.noKilled;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoLynched()
//	{
//		return noLynced;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoNonWolfVote()
//	{
//		return noNonWolfVote;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoWolfVote()
//	{
//		return noWolfVote;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoVoted()
//	{
//		return noVoted;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public int getNoFled()
//	{
//		return noFled;
//	}
}
