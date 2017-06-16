package bot.database.manager.data;

import java.util.HashSet;

public class ListOfPermissions {
	private Integer permissionLevel;
	private String permissionLevelName;
	private HashSet<Long> userID = new HashSet<Long>();
	private HashSet<Long> roleID = new HashSet<Long>();
	
	public String getPermissionLevelName() {
		return permissionLevelName;
	}
	public void setPermissionLevelName(String permissionLevelName) {
		this.permissionLevelName = permissionLevelName;
	}
	public boolean hasUserID(Long userID) {
		return this.userID.contains(userID);
	}
	
	public void removeUserID(Long userID)
	{
		this.userID.remove(userID);
	}
	
	public void setUserID(Long userID) {
		this.userID.add(userID);
	}
	public void removeRoleID(Long roleID)
	{
		this.roleID.remove(roleID);
	}
	public boolean hasRoleID(Long roleID) {
		return this.roleID.contains(roleID);
	}
	public void setRoleID(Long roleID) {
		this.roleID.add(roleID);
	}
	public Integer getPermissionLevel() {
		return permissionLevel;
	}
	public void setPermissionLevel(Integer permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
	
	
}
