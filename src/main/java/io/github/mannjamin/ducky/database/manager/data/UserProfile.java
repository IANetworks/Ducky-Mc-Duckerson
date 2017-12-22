package io.github.mannjamin.ducky.database.manager.data;

import java.sql.Timestamp;
import java.util.HashMap;

import io.github.mannjamin.ducky.items.Item;

/**
 * The type User profile.
 */
public class UserProfile {

    /**
     * The Balance.
     */
    private Long balance;
    /**
     * The Points.
     */
    private Long points;
    /**
     * The Flipped.
     */
    private Long flipped;
    /**
     * The Unflipped.
     */
    private Long unflipped;
    /**
     * The Level.
     */
    private Long level;
    /**
     * The Rank.
     */
    private Integer rank;
    /**
     * The Rank name.
     */
    private String rankName;
    /**
     * Number of Wins in a game of Werewolf
     */
    private Long werewolfWins;
    /**
     * Number of werewolf games played
     */
    private Long werewolfGames;

    /**
     * Experience needed to rank up
     */
    private Long rankExp;

    /**
     * Cooldown for when the user next gain currency
     */
    private Timestamp cooldown;

    /**
     * Current profile Title
     */
    private String title;

    /**
     * User's Inventory ItemID, Item
     */
    private HashMap<Long, Item> userInv = new HashMap<>();

    /**
     * Gets rank name.
     *
     * @return the rank name
     */
    public String getRankName() {
        if (rankName == null)
            return "**This Rank is Not Defined**";
        return rankName;
    }

    public void addItem(Item item) {
        userInv.put(item.getItemID(), item);
    }

    public HashMap<Long, Item> getInv() {
        return userInv;
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
     * Gets balance.
     *
     * @return the balance
     */
    public Long getBalance() {
        return balance;
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
     * Gets points.
     *
     * @return the points
     */
    public Long getPoints() {
        return points;
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
     * Gets flipped.
     *
     * @return the flipped
     */
    public Long getFlipped() {
        return flipped;
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
     * Gets unflipped.
     *
     * @return the unflipped
     */
    public Long getUnflipped() {
        return unflipped;
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
     * Gets level.
     *
     * @return the level
     */
    public Long getLevel() {
        return level;
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
     * Gets rank.
     *
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Sets rank.
     *
     * @param rank the rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public Long getWerewolfWins() {
        return werewolfWins;
    }

    public void setWerewolfWins(long werewolfWins) {
        this.werewolfWins = werewolfWins;
    }

    public Long getWerewolfGames() {
        return werewolfGames;
    }

    public void setWerewolfGames(Long werewolfGames) {
        this.werewolfGames = werewolfGames;
    }

    public Long getRankExp() {
        return rankExp;
    }

    public void setRankExp(Long rankExp) {
        this.rankExp = rankExp;
    }

    public Timestamp getCooldown() {
        return cooldown;
    }

    public void setCooldown(Timestamp cooldown) {
        this.cooldown = cooldown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getItemCount() {
        return userInv.size();
    }
}
