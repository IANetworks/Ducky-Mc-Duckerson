package io.github.mannjamin.ducky.items;

import io.github.mannjamin.ducky.I18N;
import io.github.mannjamin.ducky.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.sql.SQLException;

/**
 * The type Item.
 */
public abstract class Item implements Cloneable {
    protected DatabaseManager dbMan;
    //Base item info
    protected Integer invID; //invID
    protected String name;
    protected boolean buyable;
    protected Integer cost;
    protected boolean usable;
    protected ItemType type;
    //Unique ID
    private Long itemID;

    public Item(DatabaseManager dbMan, String name, Integer invID, boolean buyable, Integer cost, boolean usable, ItemType type, Long itemID) {
        this.dbMan = dbMan;
        this.name = name;
        this.invID = invID;
        this.buyable = buyable;
        this.cost = cost;
        this.usable = usable;
        this.type = type;
        this.itemID = itemID;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }


    /**
     * Sets item type for this item.
     *
     * @param type the type
     */
    public void setType(ItemType type) {
        this.type = type;
    }

    /**
     * Sets usable. Can this item be used? If not usable, executeUse will not function
     *
     * @param usable the usable
     */
    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    /**
     * Gets description of the item
     *
     * @return the description
     */
    public String getDescription(MessageChannel channel) {
        return localize(channel, String.format("item.%s.description", name));
    }

    /**
     * Gets name of the item
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public String getLocalizedName(MessageChannel channel) {
        return localize(channel, String.format("item.%s.name", getName()));
    }

    /**
     * Sets name of the item
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets inv id, InvID for special
     *
     * @return the inv id
     */
    public Integer getInvID() {
        return invID;
    }

    /**
     * Sets inv id. Set InvIT, this is the ID of the common item
     *
     * @param invID the inv id
     */
    public void setInvID(Integer invID) {
        this.invID = invID;
    }

    /**
     * Is usable boolean. Can this item be used? If not usable, executeUse will not function
     *
     * @return the boolean
     */
    public boolean isUsable() {
        return usable;
    }

    /**
     * Gets type. The Item Type
     *
     * @return the type
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Excute. Call when user obtain the item, either bought or obtained via an in discord game action or given by another use
     *
     * @param guild  the server of the calling request
     * @param member the user that's obtained the item
     * @param amount how much the user obtain this item
     */
    public abstract void execute(Guild guild, Member member, MessageChannel channel, Integer amount);

    /**
     * Excute use. Calls when user user the item
     *
     * @param guild
     * @param member
     * @param channel
     */
    public abstract void executeUse(Guild guild, Member member, MessageChannel channel) throws SQLException;

    /**
     * Gets item id. If an item belongs to a user, this will contains an uniquie itemID
     *
     * @return the item id or null
     */
    public Long getItemID() {
        return itemID;
    }

    /**
     * Sets item id. Set Item ID when assigned to user.
     *
     * @param itemID the item id
     */
    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    /**
     * Is buyable boolean. Can the item be brough from the store
     *
     * @return the boolean
     */
    public boolean isBuyable() {
        return buyable;
    }

    /**
     * Sets buyable.
     *
     * @param buyable the buyable. Can the item be brough from the store
     */
    public void setBuyable(boolean buyable) {
        this.buyable = buyable;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

  /**
   * Helper function as shortcut for <code>i18n.localize(dbMan, ...)</code>
   * @param channel
   * @param key
   * @param args
   * @return
   */
    protected String localize(MessageChannel channel, String key, Object... args) {
        I18N i18n = I18N.getInstance();
        return i18n.localize(dbMan, channel, key, args);
    }

}
