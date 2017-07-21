package bot.database.manager.data;

public class UserProfile {
	
	Long balance;
	Long points;
	Long flipped;
	Long unflipped;
	Long level;
	Integer rank;
	String rankName;
	
	public String getRankName() {
		return rankName;
	}

	public Long getBalance() {
		return balance;
	}

	public Long getPoints() {
		return points;
	}

	public Long getFlipped() {
		return flipped;
	}

	public Long getUnflipped() {
		return unflipped;
	}

	public Long getLevel() {
		return level;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}	

	public void setBalance(long balance) {
		this.balance = balance;
		
	}

	public void setPoints(Long points) {
		this.points = points;
		
	}

	public void setFlipped(long flipped) {
		this.flipped = flipped;
		
	}

	public void setUnflipped(long unflipped) {
		this.unflipped = unflipped;
		
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
