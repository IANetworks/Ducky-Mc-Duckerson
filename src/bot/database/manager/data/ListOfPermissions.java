package bot.database.manager.data;

import java.util.HashSet;

/**
 * The type List of permissions.
 */
public class ListOfPermissions {
    private Integer permissionLevel;
    private String permissionLevelName;
    private HashSet<Long> userID = new HashSet<Long>();
    private HashSet<Long> roleID = new HashSet<Long>();

    /**
     * Gets permission level name.
     *
     * @return the permission level name
     */
    public String getPermissionLevelName() {
        return permissionLevelName;
    }

    /**
     * Sets permission level name.
     *
     * @param permissionLevelName the permission level name
     */
    public void setPermissionLevelName(String permissionLevelName) {
        this.permissionLevelName = permissionLevelName;
    }

    /**
     * Has user id boolean.
     *
     * @param userID the user id
     * @return the boolean
     */
    public boolean hasUserID(Long userID) {
        return this.userID.contains(userID);
    }

    /**
     * Remove user id.
     *
     * @param userID the user id
     */
    public void removeUserID(Long userID) {
        this.userID.remove(userID);
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(Long userID) {
        this.userID.add(userID);
    }

    /**
     * Remove role id.
     *
     * @param roleID the role id
     */
    public void removeRoleID(Long roleID) {
        this.roleID.remove(roleID);
    }

    /**
     * Has role id boolean.
     *
     * @param roleID the role id
     * @return the boolean
     */
    public boolean hasRoleID(Long roleID) {
        return this.roleID.contains(roleID);
    }

    /**
     * Sets role id.
     *
     * @param roleID the role id
     */
    public void setRoleID(Long roleID) {
        this.roleID.add(roleID);
    }

    /**
     * Gets permission level.
     *
     * @return the permission level
     */
    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * Sets permission level.
     *
     * @param permissionLevel the permission level
     */
    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }


}
