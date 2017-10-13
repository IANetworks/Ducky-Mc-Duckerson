package werewolf.data;

import java.util.Comparator;

/**
 * The type Player no comparator.
 */
public class PlayerNoComparator implements Comparator<Player> {
    /**
     * Instantiates a new Player no comparator.
     */
    public PlayerNoComparator() {
    }

    public int compare(Player playerInfo1, Player playerInfo2) {
        if (playerInfo2.getPlayerNo() < playerInfo1.getPlayerNo()) {
            return 1;
        }

        return playerInfo2.getPlayerNo() <= playerInfo1.getPlayerNo() ? 0 : -1;
    }

}