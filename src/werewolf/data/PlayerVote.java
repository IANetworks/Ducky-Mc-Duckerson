/**
 * 
 */
package werewolf.data;

/**
 * @author AdTheRat
 *
 */
public class PlayerVote {
	
	int playerId = 0;
	Integer votedFor = 0;
	int voteCount = 0;

	public PlayerVote(int playerId)
	{
		this.playerId = playerId;
		this.votedFor = null;
	}
	
	public PlayerVote(int playerId, int votedFor)
	{
		this.playerId = playerId;
		this.votedFor = votedFor;
	}
	
	public void setVoteCount(int intVoteCount)
	{
		this.voteCount = intVoteCount;
	}
	
	public int getVoteCount()
	{
		return voteCount;
	}
	
	public int getPlayerId()
	{
		return playerId;
	}
	
	public int getVotedFor()
	{
		return votedFor;
	}

}
