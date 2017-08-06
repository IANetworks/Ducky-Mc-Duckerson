package werewolf.data;

import java.util.Comparator;

public class PlayerNoComparator implements Comparator<Player> {
	public PlayerNoComparator()
	{
	}
	
	public int compare(Player playerInfo1, Player playerInfo2)
	{
		if(playerInfo2.getPlayerNo() < playerInfo1.getPlayerNo())
		{
			return 1;
		}
		
		return playerInfo2.getPlayerNo() <= playerInfo1.getPlayerNo() ? 0 : -1;
	}

}