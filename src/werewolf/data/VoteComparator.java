/**
 * 
 */
package werewolf.data;

import java.util.Comparator;

/**
 * @author AdTheRat
 *
 */
public class VoteComparator implements Comparator<Player> {
	public VoteComparator()
	{
	}
	
	public int compare(Player playerInfo1, Player playerInfo2)
	{
		if(playerInfo1.getVoteCount() < playerInfo2.getVoteCount())
		{
			return 1;
		}
		
		return playerInfo1.getVoteCount() <= playerInfo2.getVoteCount() ? 0 : -1;
	}

}
