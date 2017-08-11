package bot.database.manager.data;

/**
 * The type User profile.
 */
public class UserProfile {

    /**
     * The Balance.
     */
    Long balance;
    /**
     * The Points.
     */
    Long points;
    /**
     * The Flipped.
     */
    Long flipped;
    /**
     * The Unflipped.
     */
    Long unflipped;
    /**
     * The Level.
     */
    Long level;
    /**
     * The Rank.
     */
    Integer rank;
    /**
     * The Rank name.
     */
    String rankName;

    /**
     * Gets rank name.
     *
     * @return the rank name
     */
    public String getRankName() {
		return rankName;
	}

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public Long getBalance() {
		return balance;
	}

    /**
     * Gets points.
     *
     * @return the points
     */
    public Long getPoints() {
		return points;
	}

    /**
     * Gets flipped.
     *
     * @return the flipped
     */
    public Long getFlipped() {
		return flipped;
	}

    /**
     * Gets unflipped.
     *
     * @return the unflipped
     */
    public Long getUnflipped() {
		return unflipped;
	}

    /**
     * Gets level.
     *
     * @return the level
     */
    public Long getLevel() {
		return level;
	}

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public Integer getRank() {
		return rank;
	}

    /**
     * Sets rank name.
     *
     * @param rankName the rank name
     */
    public void setRankName(String rankName) {
		this.rankName = rankName;
	}

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(long balance) {
		this.balance = balance;
		
	}

    /**
     * Sets points.
     *
     * @param points the points
     */
    public void setPoints(Long points) {
		this.points = points;
		
	}

    /**
     * Sets flipped.
     *
     * @param flipped the flipped
     */
    public void setFlipped(long flipped) {
		this.flipped = flipped;
		
	}

    /**
     * Sets unflipped.
     *
     * @param unflipped the unflipped
     */
    public void setUnflipped(long unflipped) {
		this.unflipped = unflipped;
		
	}

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(long level) {
		this.level = level;
	}

    /**
     * Sets rank.
     *
     * @param rank the rank
     */
    public void setRank(int rank) {
		this.rank = rank;
	}

}
