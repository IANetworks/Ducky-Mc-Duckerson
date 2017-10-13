/*
 * PlayerList.java
 *
 * Created on 15 September 2007, 16:49
 *
 *Object PlayerList to manage Player Objects
 */

package werewolf.data;

import java.util.*;
//import enums.*;

import net.dv8tion.jda.core.entities.Member;

/**
 * Class for managing Players objects. Key is based on Nickname
 */
public class PlayerList {
    /**
     * Creates a new instance of PlayerList
     */
    Map<Long, Player> playerList = new HashMap<Long, Player>(); // Hold List of objects
    /**
     * The Player vote list.
     */
    List<PlayerVote> playerVoteList = new ArrayList<PlayerVote>();
    /**
     * The Dying voice.
     */
// DatabaseControl db = null;
    Player dyingVoice = null; //Store Dying Voice
    /**
     * The To see.
     */
    Player toSee = null; //Store Seers choice
    /**
     * The Player count.
     */
    int playerCount = 1;

    /**
     * Instantiates a new Player list.
     */
    public PlayerList() {
        this.playerCount = 1;
        addNoLynch();
    }

    /**
     * Sets seer viewing.
     *
     * @param playerID the player id
     */
    public void setSeerViewing(Long playerID) {
        if (playerList.containsKey(playerID)) {
            toSee = playerList.get(playerID);
        }
    }

    /**
     * Sets dying voice.
     *
     * @param playerID the player id
     */
    public void setDyingVoice(Long playerID) {
        if (playerList.containsKey(playerID)) {
            dyingVoice = playerList.get(playerID);
        }
    }

    /**
     * Has dying voice boolean.
     *
     * @param playerID the player id
     * @return boolean
     */
    public boolean hasDyingVoice(Long playerID) {

        return dyingVoice != null && dyingVoice.getUserID().equals(playerID);
    }

    /**
     * Is seer view empty boolean.
     *
     * @return boolean
     */
    public boolean isSeerViewEmpty() {
        return toSee == null;
    }

    /**
     * Is dying voice empty boolean.
     *
     * @return boolean
     */
    public boolean isDyingVoiceEmpty() {
        return dyingVoice == null;
    }

    /**
     * Gets dying voice.
     *
     * @return the dying voice
     */
    public Player getDyingVoice() {
        return dyingVoice;
    }

    /**
     * Gets seer view.
     *
     * @return seer view
     */
    public Player getSeerView() {
        return toSee;
    }

    /**
     * Clear seer view.
     */
    public void clearSeerView() {
        toSee = null;
    }

    /**
     * Clear dying voice.
     */
    public void clearDyingVoice() {
        dyingVoice = null;
    }

    /**
     * Gets player votes list.
     *
     * @return player votes list
     */
    public List<PlayerVote> getPlayerVotesList() {
        return playerVoteList;
    }

    /**
     * Add no lynch.
     */
    public void addNoLynch() {
        Player noLynch = new Player(null, 0);

        noLynch.setRole(Role.NOLYNCH);
        noLynch.setPlayerState(PlayerState.NOLYNCH);
        //noLynch.setId(0); //Special NOLYNCH Id
        noLynch.setRoleRecieved(true);

        playerList.put((long) 0, noLynch);
    }

    /**
     * Gets player.
     *
     * @param playerID the player id
     * @return player
     */
    public Player getPlayer(Long playerID) {
        Player player = null;

        if (playerList.containsKey(playerID)) {
            player = playerList.get(playerID);
        }

        return player;
    }

    /**
     * Add player player.
     *
     * @param user the user
     * @return the player
     */
    public Player addPlayer(Member user) {
        Player player = new Player(user, playerCount);
        //Get Player ID
        //player.setId(db.sql_getPlayerID(name, hostname));
        playerList.put(player.getUserID(), player);
        playerCount++;
        return player;

    }

//   /**
//    * 
//    * @param oldNick
//    * @param newNick
//    */
//    public void setPlayerName(String oldNick, String newNick)
//    {
//    	if(playerList.containsKey(oldNick)) {
//    		Player player = playerList.remove(oldNick);
//    		player.setName(newNick);
//    		playerList.put(newNick, player);
//    	}
//    }

    /**
     * Sets player state.
     *
     * @param playerID    the player id
     * @param playerState the player state
     */
    public void setPlayerState(Long playerID, PlayerState playerState) {
        if (playerList.containsKey(playerID)) {
            Player player = playerList.get(playerID);
            player.setPlayerState(playerState);
        }
    }

    /**
     * Sets player banned.
     *
     * @param playerID the player id
     * @param isBanned the is banned
     */
    public void setPlayerBanned(Long playerID, boolean isBanned) {
        if (playerList.containsKey(playerID)) {
            Player player = playerList.get(playerID);
            player.setIsBanned(isBanned);
        }
    }

    /**
     * Gets player name by player id.
     *
     * @param playerNo the player no
     * @return player name by player id
     */
    public Player getPlayerByPlayerID(int playerNo) {
        for (Player player : playerList.values()) {
            if (player.getPlayerNo() == playerNo) {
                return player;
            }
        }

        return null;
    }

    /**
     * Gets player no.
     *
     * @param playerID the player id
     * @return player no
     */
    public int getPlayerNo(Long playerID) {
        if (playerList.containsKey(playerID)) {
            Player player = playerList.get(playerID);
            return player.getPlayerNo();
        }

        return -1;
    }

    /**
     * Sets player role.
     *
     * @param playerID the player id
     * @param role     the role
     */
    public void setPlayerRole(Long playerID, Role role) {
        if (playerList.containsKey(playerID)) {
            Player player = playerList.get(playerID);
            player.setRole(role);
        }
    }

    /**
     * Sets player vote.
     *
     * @param playerID  the player id
     * @param voteForID the vote for id
     */
    public void setPlayerVote(Long playerID, Long voteForID) {
        if (playerList.containsKey(playerID)) {
            Player voter = playerList.get(playerID);
            if (playerList.containsKey(voteForID)) {
                voter.setVoteFor(playerList.get(voteForID));
            }

        }
    }

    /**
     * 
     * @param playerID
     * @return
     */
//    public int getPlayerID(Long playerID)
//    {
//        if(playerList.containsKey(playerID)) {
//        	return playerList.get(playerID).getPlayerId();
//        }
//
//        return -1;
//    }

    /**
     * Gets player member.
     *
     * @param playerID the player id
     * @return player member
     */
    public Member getPlayerMember(Long playerID) {
        if (playerList.containsKey(playerID)) {
            return playerList.get(playerID).getMember();
        }

        return null;
    }

    /**
     * Gets player nick count.
     *
     * @param playerID the player id
     * @return the player nick count
     */
    public int getPlayerNickCount(Long playerID) {
        if (playerList.containsKey(playerID)) {
            return playerList.get(playerID).getNickCount();
        }

        return 0;
    }

    /**
     * 
     * @param playerID
     * @param id
     */
//    public void setPlayerID(Long playerID, int id)
//    {
//    	if(playerList.containsKey(playerID)) {
//    		Player player = playerList.get(playerID);
//    		player.setId(id);
//    	}
//    }


    /**
     * Sets player non vote count.
     *
     * @param playerID     the player id
     * @param nonVoteCount the non vote count
     */
    public void setPlayerNonVoteCount(Long playerID, int nonVoteCount) {
        if (playerList.containsKey(playerID)) {
            Player player = playerList.get(playerID);
            player.setNonVoteCount(nonVoteCount);
        }
    }

    /**
     * Remove player.
     *
     * @param userID the name
     */
    public void removePlayer(Long userID) {
        if (playerList.containsKey(userID)) {
            playerList.remove(userID);
        }
    }

    /**
     * Checks to see if player Exists in the player Objects
     *
     * @param playerID the player id
     * @return boolean
     */
    public boolean hasPlayer(long playerID) {
        return playerList.containsKey(playerID);
    }

    /**
     * Return size of PlayerList
     *
     * @return player size
     */
    public int getPlayerSize() {
        //Take one off, as NoLynch is not a player, just a pretend one
        return playerList.size() - 1;
    }

    /**
     * Gets player list.
     *
     * @return player list
     */
// getPlayerList ->
    public List<Player> getPlayerList() {
        Map<Long, Player> removeNoLynch = new HashMap<Long, Player>(playerList);
        removeNoLynch.remove((long) 0);

        List<Player> playerNames = new ArrayList<Player>(removeNoLynch.values());
        return playerNames;
    }

    public List<Member> getMemberList() {
        Map<Long, Player> removeNoLynch = new HashMap<Long, Player>(playerList);
        removeNoLynch.remove((long) 0);

        List<Member> playerNames = new ArrayList<Member>();

        for (Player player : removeNoLynch.values()) {
            playerNames.add(player.getMember());
        }
        return playerNames;
    }

    /**
     * Gets player role.
     *
     * @param playerID the player id
     * @return player role
     */
    public Role getPlayerRole(Long playerID) {
        if (playerList.containsKey(playerID)) {
            return playerList.get(playerID).getRole();
        }

        return Role.ERR; // Return ERR for Error
    }

    /**
     * Gets player non vote count.
     *
     * @param playerID the player id
     * @return player non vote count
     */
    public int getPlayerNonVoteCount(Long playerID) {
        if (playerList.containsKey(playerID)) {
            return playerList.get(playerID).getNonVoteCount();
        }

        return -1; // Return -1 for Error
    }

    /**
     * Gets player votee.
     *
     * @param playerID the player id
     * @return player votee
     */
    public Player getPlayerVotee(Long playerID) {
        if (playerList.containsKey(playerID)) {
            if (playerList.get(playerID).getVoteFor() == null) {
                return null;
            } else {
                return playerList.get(playerID).getVoteFor();
            }
        }

        return null;
    }

    /**
     * Gets player state.
     *
     * @param playerID the player id
     * @return player state
     */
    public PlayerState getPlayerState(Long playerID) {
        if (playerList.containsKey(playerID)) {
            return playerList.get(playerID).getPlayerState();
        }

        return PlayerState.ERR;
    }

    /**
     * Gets player list by role.
     *
     * @param role the role
     * @return player list by role
     */
    public List<Player> getPlayerListByRole(Role role) {
        List<Player> byRole = new ArrayList<Player>();

        for (Player player : playerList.values()) {
            if (player.getRole() == role) {
                byRole.add(player);
            }
        }

        return byRole;
    }

    /**
     * Gets banned list.
     *
     * @return banned list
     */
    public List<Player> getBannedList() {
        List<Player> isBanned = new ArrayList<Player>();

        for (Player player : playerList.values()) {
            if (player.getIsBanned()) {
                isBanned.add(player);
            }
        }

        return isBanned;
    }

    /**
     * Gets player list by non vote count.
     *
     * @param nonVoteCount the non vote count
     * @return player list by non vote count
     */
    public List<Player> getPlayerListByNonVoteCount(int nonVoteCount) {
        List<Player> byNonVoteCount = new ArrayList<Player>();

        for (Player player : playerList.values()) {
            if (player.getNonVoteCount() == nonVoteCount && player.getPlayerState() == PlayerState.ALIVE) {
                byNonVoteCount.add(player);
            }
        }

        return byNonVoteCount;
    }

    /**
     * Gets player list by state.
     *
     * @param playerState the player state
     * @return the player list by state
     */
    public List<Player> getPlayerListByState(PlayerState playerState) {
        return getPlayerListByState(playerState, playerState);
    }

    /**
     * Gets player list by state.
     *
     * @param playerState  the player state
     * @param playerState2 the player state 2
     * @return player list by state
     */
    public List<Player> getPlayerListByState(PlayerState playerState, PlayerState playerState2) {
        List<Player> byAlive = new ArrayList<Player>();
        List<Player> ourPlayerList = new ArrayList<Player>(this.playerList.values());
        Collections.sort(ourPlayerList, new PlayerNoComparator());

        for (Player player : ourPlayerList) {
            if (player.getPlayerState() == playerState || player.getPlayerState() == playerState2) {
                byAlive.add(player);
            }
        }

        return byAlive;
    }

    /**
     * Gets number of player by state.
     *
     * @param playerState the player state
     * @return number of player by state
     */
    public int getNumberOfPlayerByState(PlayerState playerState) {
        return getPlayerListByState(playerState).size();
    }

    /**
     * Gets number of player by non vote count.
     *
     * @param nonVoteCount the non vote count
     * @return number of player by non vote count
     */
    public int getNumberOfPlayerByNonVoteCount(int nonVoteCount) {
        return getPlayerListByNonVoteCount(nonVoteCount).size();
    }

    /**
     * Gets number of player by role.
     *
     * @param role the role
     * @return number of player by role
     */
    public int getNumberOfPlayerByRole(Role role) {
        return getPlayerListByRole(role).size();
    }

    /**
     * Random order list.
     *
     * @return list
     */
    public List<Player> randomOrder() {
        List<Player> shuffleMe = getPlayerList();
        Collections.shuffle(shuffleMe);
        Collections.shuffle(shuffleMe);// Double Shuffle - FOR THE WIN!!!
        return shuffleMe;
    }

    /**
     * Gets number of player by role player state.
     *
     * @param role        the role
     * @param playerState the player state
     * @return number of player by role player state
     */
    public int getNumberOfPlayerByRolePlayerState(Role role, PlayerState playerState) {
        int count = 0;

        for (Player player : playerList.values()) {
            if (player.getRole() == role && player.getPlayerState() == playerState) {
                count++;
            }
        }

        return count;
    }

    /**
     * Gets player list by role player state.
     *
     * @param role        the role
     * @param playerState the player state
     * @return player list by role player state
     */
    public List<Player> getPlayerListByRolePlayerState(Role role, PlayerState playerState) {

        List<Player> byRoleAlive = new ArrayList<Player>();

        for (Player player : playerList.values()) {
            if (player.getRole() == role && player.getPlayerState() == playerState) {
                byRoleAlive.add(player);
            }
        }

        return byRoleAlive;

    }

    /**
     * Player fled.
     *
     * @param playerID the player id
     */
    public void playerFled(Long playerID) {
        this.setPlayerState(playerID, PlayerState.FLED);
        //Check and removes player from all votes

        for (Player player : this.getPlayerListByState(PlayerState.ALIVE)) {
            if (playerList.get(player.getUserID()).getVoteFor() != null && playerList.get(player.getUserID()).getVoteFor().getUserID() == playerID) {
                playerList.get(player.getUserID()).setVoteFor(null);
            }
        }
    }

    /**
     * Call this after tallyingVotes up to add the number of times the player has been voted for to the player Object
     */
//    public void playerVoteCount()
//    {
//    	for(Player name : this.getPlayerListByState(PlayerState.ALIVE))
//    	{
//    		playerList.get(name).addNoVoted(playerList.get(name).getVoteCount());
//    	}
//    }

    /**
     * 
     */
//    public void setNoRoleCount()
//    {
//    	for(Player player : playerList.values())
//    	{
//    		player.setNoRole(player.getRole());
//    	}
//    }

    /**
     * Vote count list.
     *
     * @param tally         the tally
     * @param voteTheadhold the vote theadhold
     * @return list
     */
    public List<Player> voteCount(boolean tally, int voteTheadhold) {
        //Reset VoteCount
        this.resetVoteCount();

        for (Player player : this.getPlayerListByState(PlayerState.ALIVE, PlayerState.NOLYNCH)) {
            if (player.getVoteFor() != null) {
                player.getVoteFor().addVoteCount();
                if (tally) {
                    //reset NonVoteCount
                    this.setPlayerNonVoteCount(player.getUserID(), 0);

                    //Add Vote record
                    PlayerVote playerVote = new PlayerVote(player.getPlayerNo(), player.getVoteFor().getPlayerNo());
                    playerVoteList.add(playerVote);

                }
            } else if (tally && player.getPlayerState() != PlayerState.NOLYNCH) {
                int nonVoteCountAdd = this.getPlayerNonVoteCount(player.getUserID());
                nonVoteCountAdd++;
                this.setPlayerNonVoteCount(player.getUserID(), nonVoteCountAdd);
            }
        }

//		if(tally)
//		{
//			this.playerVoteCount();
//		}   

        List<Player> ourPlayerList = new ArrayList<Player>(this.playerList.values());
        Collections.sort(ourPlayerList, new VoteComparator());
        List<Player> strPlayerList = new ArrayList<Player>();

        for (Player player : ourPlayerList) {
            if ((player.getPlayerState() == PlayerState.ALIVE || player.getPlayerState() == PlayerState.NOLYNCH) && player.getVoteCount() >= voteTheadhold) {
                strPlayerList.add(player);
            }
        }

        return strPlayerList;
    }


    /**
     * Check to see we have player, ignoring case
     * @param name
     * @return returns the correct players name 
     */
//    public String hasPlayerIgnoreCase(Long playerID)
//    {
//    	for (String name : playerList.keySet())
//    	{
//    		if(name.equalsIgnoreCase(playerName))
//    		{
//    			return name;
//    		}
//    	}
//    	
//    	//If we get here, we don't have player
//    	return null;
//    }

    /**
     * Gets player vote count.
     *
     * @param playerID the player id
     * @return player vote count
     */
    public int getPlayerVoteCount(Long playerID) {
        if (playerList.containsKey(playerID)) {
            return playerList.get(playerID).getVoteCount();
        }

        return -1;
    }

    /**
     * Player has voted boolean.
     *
     * @param playerID the player id
     * @return boolean
     */
    public boolean playerHasVoted(Long playerID) {
        if (playerList.containsKey(playerID)) {
            return playerList.get(playerID).getVoteFor() != null;
        }

        return false;
    }

    /**
     * Reset vote count.
     */
    public void resetVoteCount() {
        for (Player players : playerList.values()) {
            players.resetVoteCount();
        }

    }

    /**
     * Reset votes.
     */
    public void resetVotes() {
        for (Player players : playerList.values()) {
            players.resetVote();
        }
    }

    /**
     * Reset list.
     */
    public void resetList() {
        playerList.clear();
        playerVoteList.clear();
        playerCount = 1;
        addNoLynch();
    }

    /**
     * Has all player received roles boolean.
     *
     * @return the boolean
     */
    public boolean hasAllPlayerReceivedRoles() {
        for (Player player : playerList.values()) {
            if (!player.isRoleRecieved()) {
                return false;
            }
        }
        return true;
    }
}