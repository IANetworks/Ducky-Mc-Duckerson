/**
 *
 */
package io.github.mannjamin.ducky.werewolf.data;

/**
 * The type Player vote.
 *
 * @author AdTheRat
 */
public class PlayerVote {

    /**
     * The Player id.
     */
    int playerId = 0;
    /**
     * The Voted for.
     */
    Integer votedFor = 0;
    /**
     * The Vote count.
     */
    int voteCount = 0;

    /**
     * Instantiates a new Player vote.
     *
     * @param playerId the player id
     */
    public PlayerVote(int playerId) {
        this.playerId = playerId;
        this.votedFor = null;
    }

    /**
     * Instantiates a new Player vote.
     *
     * @param playerId the player id
     * @param votedFor the voted for
     */
    public PlayerVote(int playerId, int votedFor) {
        this.playerId = playerId;
        this.votedFor = votedFor;
    }

    /**
     * Gets vote count.
     *
     * @return the vote count
     */
    public int getVoteCount() {
        return voteCount;
    }

    /**
     * Sets vote count.
     *
     * @param intVoteCount the int vote count
     */
    public void setVoteCount(int intVoteCount) {
        this.voteCount = intVoteCount;
    }

    /**
     * Gets player id.
     *
     * @return the player id
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets voted for.
     *
     * @return the voted for
     */
    public int getVotedFor() {
        return votedFor;
    }

}
