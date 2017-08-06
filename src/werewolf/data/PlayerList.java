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
public class PlayerList 
{
	/** Creates a new instance of PlayerList */
    Map<Long, Player> playerList = new HashMap<Long, Player>(); // Hold List of objects
    List<PlayerVote> playerVoteList = new ArrayList<PlayerVote>();
   // DatabaseControl db = null;
    Player dyingVoice = null; //Store Dying Voice
    Player toSee = null; //Store Seers choice
    int playerCount = 1;
    
    /**
     * 
     * @param name
     */
    public void setSeerViewing(Long playerID)
    {
    	if(playerList.containsKey(playerID)) {
    		toSee = playerList.get(playerID);
    	}
    }
    
    /**
     * 
     * @param name
     */
    public void setDyingVoice(Long playerID)
    {
    	if(playerList.containsKey(playerID)) 
    	{
    		dyingVoice = playerList.get(playerID);
    	}    	
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public boolean hasDyingVoice(Long playerID)
    {
    	
    	if(dyingVoice != null && dyingVoice.getUserID().equals(playerID))
    	{
    		return true;
    	} else {
    		return false;
    	}    	
    }
    
    /**
     * 
     * @return
     */
    public boolean isSeerViewEmpty()
    {
    	if(toSee == null)
    	{
    		return true;
    	} else {
    		return false;
    	}     	
    }
    
    /**
     * 
     * @return
     */
    public boolean isDyingVoiceEmpty()
    {
    	if(dyingVoice == null)
    	{
    		return true;
    	} else {
    		return false;
    	}    	
    }
        
    /**
     * 
     */
    public String getDyingVoice()
    {
    	return dyingVoice.getEffectiveName();
    }
    
    /**
     * 
     * @return
     */
    public String getSeerView()
    {
    	if(toSee == null)
    	{
    		return null;
    	} else {
    		return toSee.getEffectiveName();
    	}
    }
    
    /**
     * 
     */
    public void clearSeerView()
    {
    	toSee = null;
    }
    
    /**
     * 
     */
    public void clearDyingVoice()
    {
    	dyingVoice = null;
    }

    /**
     * 
     * @return
     */
    public List<PlayerVote> getPlayerVotesList()
    {
    	return playerVoteList;
    }
    
    /**
     * 
     */
    public void addNoLynch()
    {
    	Player noLynch = new Player(null, 0);
    	
    	noLynch.setRole(Role.NOLYNCH);
    	noLynch.setAlive(Alive.NOLYNCH);
    	noLynch.setId(0); //Special NOLYNCH Id
    	
    	playerList.put((long) 0, noLynch);
    	
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Player getPlayer(Long playerID)
    {
    	Player player = null;
    	
    	if(playerList.containsKey(playerID)) {
    		player = playerList.get(playerID);
    	}
    	
    	return player;
    }
    
   /**
    * 
    * @param name
    */
    public void addPlayer(Member user)
    {
    	Player player = new Player(user, playerCount);
        //Get Player ID
        //player.setId(db.sql_getPlayerID(name, hostname));
        playerList.put(player.getUserID(), player);
        playerCount ++;

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
     * 
     * @param name
     * @param alive
     */
    public void setPlayerAlive(Long playerID, Alive alive)
    {
    	if(playerList.containsKey(playerID)) {
    		Player player = playerList.get(playerID);
    		player.setAlive(alive);
    	}
    }
    
    /**
     * 
     * @param name
     * @param isBanned
     */
    public void setPlayerBanned(Long playerID, boolean isBanned)
    {
    	if(playerList.containsKey(playerID)) {
    		Player player = playerList.get(playerID);
    		player.setIsBanned(isBanned);
    	}
    }
    
    /**
     * 
     * @param PlayerNo
     * @return
     */
    public String getPlayerNameByPlayerName(int playerNo)
    {
    	for(Player player : playerList.values())
    	{
    		if (player.getPlayerNo() == playerNo)
    		{
    			return player.getEffectiveName();
    		}
    	}
    	
    	return null;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public int getPlayerNo(Long playerID)
    {
    	if(playerList.containsKey(playerID)) {
    		Player player = playerList.get(playerID);
    		return player.getPlayerNo();
    	}
    	
    	return -1;
    }

    /**
     * 
     * @param name
     * @param role
     */  
    public void setPlayerRole(Long playerID, Role role)
    {
    	if(playerList.containsKey(playerID)) {
    		Player player = playerList.get(playerID);
    		player.setRole(role);
    	}
    }
    
    /**
     * 
     * @param name
     * @param vote
     */
    public void setPlayerVote(Long playerID, Long voteForID)
    {
    	if(playerList.containsKey(playerID)){
    		Player voter = playerList.get(playerID);
    		if(playerList.containsKey(voteForID))
    		{
    			voter.setVoteFor(playerList.get(voteForID));
    		}
    			
    	}	
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public int getPlayerID(Long playerID)
    {
        if(playerList.containsKey(playerID)) {
        	return playerList.get(playerID).getPlayerId();
        }
        
        return -1;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Member getPlayerMember(Long playerID)
    {
        if(playerList.containsKey(playerID)) {
        	return playerList.get(playerID).getMember();
        }
        
        return null;
    }
    
    public int getPlayerNickCount(Long playerID)
    {
        if(playerList.containsKey(playerID)) {
        	return playerList.get(playerID).getNickCount();
        }
        
        return 0;
    }
    
    /**
     * 
     * @param name
     * @param id
     */
    public void setPlayerID(Long playerID, int id)
    {
    	if(playerList.containsKey(playerID)) {
    		Player player = playerList.get(playerID);
    		player.setId(id);
    	}
    }
    

    /**
     * 
     * @param name
     * @param nonVoteCount
     */
    public void setPlayerNonVoteCount(Long playerID, int nonVoteCount)
    {
    	if(playerList.containsKey(playerID)) {
    		Player player = playerList.get(playerID);
    		player.setNonVoteCount(nonVoteCount);
    	}
    }

    /**
     * 
     * @param name
     */
    public void removePlayer(String name)
    {
    	if(playerList.containsKey(name)) {
    		playerList.remove(name);
    	}
    }
    
    /**
     * Checks to see if player Exists in the player Objects      
     * @param playerID
     * @return 
     * 
     */
    public boolean hasPlayer(long playerID) 
    {
    	return playerList.containsKey(playerID);
    }
    
    /**
     * Return size of PlayerList
     * @return
     */
    public int getPlayerSize()
    {
    	//Take one off, as NoLynch is not a player, just a pretend one
        return playerList.size() - 1;
    }
    
    /**
     * 
     * @return
     */
    // getPlayerList -> 
    public List<Player> getPlayerMemberList()
    {
    	Map<Long, Player> removeNoLynch = new HashMap<Long, Player>(playerList);
    	removeNoLynch.remove((long) 0);
    	
    	List<Player> playerNames = new ArrayList<Player>(removeNoLynch.values());
    	return playerNames;
    }

    /**
     * 
     * @param name
     * @return
     */
    public Role getPlayerRole(String name)
    {
        if(playerList.containsKey(name)) {
        	return playerList.get(name).getRole();
        }
        
        return Role.ERR; // Return ERR for Error
    }
    
    /**
     * 
     * @param playerID
     * @return
     */
    		
    public int getPlayerNonVoteCount(Long playerID)
    {
        if(playerList.containsKey(playerID)) {
        	return playerList.get(playerID).getNonVoteCount();
        }
        
        return -1; // Return -1 for Error
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public String getPlayerVotee(Long playerID)
    {
        if(playerList.containsKey(playerID)) {
        	if(playerList.get(playerID).getVoteFor() == null)
        	{
        		return null;
        	} else {
        		return playerList.get(playerID).getVoteFor().getEffectiveName();
        	}
        }
        
        return null; // Return -1 for Error
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Alive getPlayerAlive(Long playerID)
    {
        if(playerList.containsKey(playerID)) {
        	return playerList.get(playerID).getAlive();
        }
        
        return Alive.ERR; // Return -1 for Error
    }

   /**
    * 
    * @param role
    * @return
    */
    public List<Player> getPlayerListByRole(Role role)
    {
    	List<Player> byRole = new ArrayList<Player>();
    	
        for(Player player : playerList.values()) {
        	if(player.getRole() == role) {
        		byRole.add(player);
        	}
        }
        
        return byRole;
    }
    
    /**
     * 
     * @return
     */
    public List<Player> getBannedList()
    {
    	List<Player> isBanned = new ArrayList<Player>();
    	
        for(Player player : playerList.values()) {
        	if(player.getIsBanned()) {
        		isBanned.add(player);
        	}
        }
        
        return isBanned;
    }
    
    /**
     * 
     * @param nonVoteCount
     * @return
     */
    public List<Player> getPlayerListByNonVoteCount(int nonVoteCount)
    {
    	List<Player> byNonVoteCount = new ArrayList<Player>();
    	
        for(Player player : playerList.values()) {
        	if(player.getNonVoteCount() == nonVoteCount && player.getAlive() == Alive.ALIVE) {
        		byNonVoteCount.add(player);
        	}
        }
        
        return byNonVoteCount;
    }

    public List<Player> getPlayerListByAlive(Alive alive)
    {
    	return getPlayerListByAlive(alive, alive);
    }
    
   /**
    * 
    * @param alive
    * @param alive2
    * @return
    */
    public List<Player> getPlayerListByAlive(Alive alive, Alive alive2)
    {
    	List<Player> byAlive = new ArrayList<Player>();
    	List<Player> ourPlayerList = new ArrayList<Player>(this.playerList.values());
    	Collections.sort(ourPlayerList, new PlayerNoComparator());
    	
        for(Player player : ourPlayerList) {
        	if(player.getAlive() == alive || player.getAlive() == alive2) {
        		byAlive.add(player);
        	}
        }
        
        return byAlive;
    }
    
   /**
    * 
    * @param alive
    * @return
    */
    public int getNumberOfPlayerByAlive(Alive alive)
    {
    	return getPlayerListByAlive(alive).size();
    }

    /**
     *
     * @param nonVoteCount
     * @return
     */
    public int getNumberOfPlayerByNonVoteCount(int nonVoteCount)
    {
	   return getPlayerListByNonVoteCount(nonVoteCount).size();
    }

   /**
    * 
    * @param role
    * @return
    */
    public int getNumberOfPlayerByRole(Role role)
    {
    	return getPlayerListByRole(role).size();
    }

   /**
    * 
    * @return
    */
    public List<Player> randomOrder()
    {
    	List<Player> shuffleMe = getPlayerMemberList();
        Collections.shuffle(shuffleMe);
        Collections.shuffle(shuffleMe);// Double Shuffle - FOR THE WIN!!!
        return shuffleMe;
    }
    
    /**
     * 
     * @param role
     * @param alive
     * @return
     */
    public int getNumberOfPlayerByRoleAlive(Role role, Alive alive)
    {
    	int count = 0;
    	
        for(Player player : playerList.values()) {
        	if(player.getRole() == role && player.getAlive() == Alive.ALIVE) {
        		count ++;
        	}
        }
        
        return count;
    }

    /**
     * 
     * @param role
     * @param alive
     * @return
     */
    public List<Player> getPlayerListByRoleAlive(Role role, Alive alive)
    {
    	
    	List<Player> byRoleAlive = new ArrayList<Player>();
    	
        for(Player player : playerList.values()) {
        	if(player.getRole() == role && player.getAlive() == Alive.ALIVE) {
        		byRoleAlive.add(player);
        	}
        }
        
        return byRoleAlive;
    
    }
    
    /**
     * 
     * @param name
     */
    public void playerFled(Long playerID)
    {
    	this.setPlayerAlive(playerID, Alive.FLED);
    	//Check and removes player from all votes
    	
    	for(Player player : this.getPlayerListByAlive(Alive.ALIVE))
    	{
    		if(playerList.get(player.getUserID()).getVoteFor() != null && playerList.get(player.getUserID()).getVoteFor().getUserID() == playerID )
    		{
    			playerList.get(player.getUserID()).setVoteFor(null);
    		}
    	}
    }
    
    /**
     * Call this after tallyingVotes up to add the number of times the player has been voted for to the player Object
     */
//    public void playerVoteCount()
//    {
//    	for(Player name : this.getPlayerListByAlive(Alive.ALIVE))
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
     * 
     * @param tally
     * @return
     */
    public List<Long> voteCount(boolean tally, int voteTheadhold)
    {	
    	//Reset VoteCount
    	this.resetVoteCount();
    	
    	for (Player player : this.getPlayerListByAlive(Alive.ALIVE, Alive.NOLYNCH))
    	{
    		if(this.playerList.get(player).getVoteFor() != null)
    		{
    			this.playerList.get(player).getVoteFor().addVoteCount();
    			if(tally)
    			{
    				//reset NonVoteCount
    				this.setPlayerNonVoteCount(player.getUserID(), 0);
    				
    				//Add Vote record
    				PlayerVote playerVote = new PlayerVote(playerList.get(player).getPlayerId(), playerList.get(player).getVoteFor().getPlayerId());
    				playerVoteList.add(playerVote);
    				
    				//Check to see if they voted correct or not
//    				if(playerList.get(player).getVoteFor().getRole() == Role.WOLF)
//    				{
//    					playerList.get(player).addNoWolfVote(1);
//    				} else if(playerList.get(player).getVoteFor().getRole() != Role.NOLYNCH) {
//    					//If they voted for something other then NoLynch, it's counts against them
//    					playerList.get(player).addNoNonWolfVote(1);
//    				}
    				
    			}
    		} else if(tally && playerList.get(player).getAlive() != Alive.NOLYNCH) {
    			int nonVoteCountAdd = this.getPlayerNonVoteCount(player.getUserID());
    			nonVoteCountAdd ++;
    			this.setPlayerNonVoteCount(player.getUserID(), nonVoteCountAdd);
    		}	
    	}
    	
//		if(tally)
//		{
//			this.playerVoteCount();
//		}   
		
    	List<Player> ourPlayerList = new ArrayList<Player>(this.playerList.values());
    	Collections.sort(ourPlayerList, new VoteComparator());
    	List<Long> strPlayerList = new ArrayList<Long>();
    	
    	for(Player player : ourPlayerList)
    	{
    		if((player.getAlive() == Alive.ALIVE || player.getAlive() == Alive.NOLYNCH) && player.getVoteCount() >= voteTheadhold)
    		{
    			strPlayerList.add(player.getUserID());
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
     * 
     * @param playerID
     * @return
     */
    public int getPlayerVoteCount(Long playerID)
    {
        if(playerList.containsKey(playerID)) {
        	return playerList.get(playerID).getVoteCount();
        }
        
        return -1;
    }
    
    /**
     * 
     * @param playerID
     * @return
     */
    public boolean playerHasVoted(Long playerID)
    {
    	if(playerList.containsKey(playerID))
    	{
    		if (playerList.get(playerID).getVoteFor() != null)
    		{
    			return true;
    		} else {
    			return false;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * 
     */
	public void resetVoteCount()
	{
    	for(Player players : playerList.values())
    	{
    		players.resetVoteCount();
    	}
		
	}
	
	/**
	 * 
	 */
    public void resetVotes()
    {
    	for(Player players : playerList.values())
    	{
    		players.resetVote();
    	}
    }
    
    /**
     * 
     */
    public void resetList()
    {
    	playerList.clear();
    	playerVoteList.clear();
    	playerCount = 1;
    	addNoLynch();
    }
    
}