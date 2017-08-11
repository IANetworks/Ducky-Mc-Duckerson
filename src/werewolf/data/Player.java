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

/**
 * The type Player.
 */
public class Player {
	/**
	 * The Player.
	 */
	Member player = null; //Hold the User object from Discord.

	/**
	 * The Vote for.
	 */
	Player voteFor = null;
	/**
	 * The Role.
	 */
	Role role = Role.NOROLE; // Contain an int for Role
	/**
	 * The Player state.
	 */
	PlayerState playerState = PlayerState.ALIVE; // Contain an int for playerState, 1 playerState, 2 dead, 3 fled

	/**
	 * The Nick count.
	 */
	int nickCount = 0;
	/**
	 * The Non vote count.
	 */
	int nonVoteCount = 0; // Number of times a Player has not voted for someone
	/**
	 * The Player no.
	 */
//int playerID = -1; // PlayerID Number, for easier reference for players ID in Database
	int playerNo = 0;  //Player Number, for giving the player a number to identify with
	/**
	 * The Vote count.
	 */
	int voteCount = 0;

	/**
	 * The Is banned.
	 */
	boolean isBanned = false;
	/**
	 * The Role recieved.
	 */
	boolean roleRecieved = false;

	/**
	 * Is role recieved boolean.
	 *
	 * @return the boolean
	 */
	public boolean isRoleRecieved() {
		return roleRecieved;
	}

	/**
	 * Sets role recieved.
	 *
	 * @param roleRecieved the role recieved
	 */
	public void setRoleRecieved(boolean roleRecieved) {
		this.roleRecieved = roleRecieved;
	}


	
	/** Creates a new instance of Player */
	/**
	 * Instantiates a new Player.
	 *
	 * @param player   the player
	 * @param playerNo the player no
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

	/**
	 * Gets effective name.
	 *
	 * @return the effective name
	 */
	public String getEffectiveName() {
		if(player != null)
			return player.getEffectiveName();
		else
			return "-";
	}

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public Long getUserID() {
		if(player == null)
		{
			return 0L;
		}
		return player.getUser().getIdLong();
	}

	/**
	 * Gets member.
	 *
	 * @return the member
	 */
	public Member getMember() {
		return player;
	}


	/**
	 * Sets is banned.
	 *
	 * @param isBanned the is banned
	 */
	public void setIsBanned(boolean isBanned)
	{
		this.isBanned = isBanned;
	}

	/**
	 * Gets is banned.
	 *
	 * @return the is banned
	 */
	public boolean getIsBanned()
	{
		return isBanned;
	}

	/**
	 * Gets nick count.
	 *
	 * @return nick count
	 */
	public int getNickCount()
	{
		return nickCount; 
	}


	/**
	 * Sets player no.
	 *
	 * @param playerNo the player no
	 */
	public void setPlayerNo(int playerNo)
	{
		this.playerNo = playerNo;
	}

	/**
	 * Gets player no.
	 *
	 * @return player no
	 */
	public int getPlayerNo()
	{
		return this.playerNo;
	}

	/**
	 * Sets vote for.
	 *
	 * @param name the name
	 */
	public void setVoteFor(Player name)
	{
		this.voteFor = name;
	}

	/**
	 * Gets vote for.
	 *
	 * @return vote for
	 */
	public Player getVoteFor()
	{
		return this.voteFor;

	}

	/**
	 * Gets vote count.
	 *
	 * @return the vote count
	 */
	public Integer getVoteCount()
	{
		return this.voteCount;
	}

	/**
	 * Add vote count.
	 */
	public void addVoteCount()
	{
		this.voteCount ++;
	}

	/**
	 * Reset vote.
	 */
	public void resetVote()
	{
		this.voteFor = null;
		this.voteCount = 0;
	}

	/**
	 * Reset vote count.
	 */
	public void resetVoteCount()
	{
		this.voteCount = 0;
	}

	/**
	 * Sets non vote count.
	 *
	 * @param nonVoteCount the non vote count
	 */
	public void setNonVoteCount(int nonVoteCount) {
		this.nonVoteCount = nonVoteCount;
	}

	/**
	 * Sets role.
	 *
	 * @param role the role
	 */
	public void setRole(Role role) {
		this.role = role;

	}

	/**
	 * Sets player state.
	 *
	 * @param playerState the player state
	 */
	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
//		if(playerState == PlayerState.FLED) {
//			this.addNoFled(1);
//		}
	}

	/**
	 * Gets player state.
	 *
	 * @return player state
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
	 * Gets non vote count.
	 *
	 * @return non vote count
	 */
	public int getNonVoteCount() {
		return nonVoteCount;
	}

	/**
	 * Gets role.
	 *
	 * @return role
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
