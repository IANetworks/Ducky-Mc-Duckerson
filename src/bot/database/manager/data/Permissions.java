package bot.database.manager.data;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Permissions.
 */
public class Permissions {
	private Map<Integer, Integer> listOfCommandsLevel = new HashMap<Integer, Integer>(); //Command ID - Command Level
	private Map<Integer, ListOfPermissions> listOfPermissions = new HashMap<Integer, ListOfPermissions>(); //permission level ID - Level Name, list of usersID, list of roleID
	
	private ListOfPermissions getPermissionValues(Integer levelID)
	{
		if(listOfPermissions.containsKey(levelID)) {
			return listOfPermissions.get(levelID);
		} else {
			return new ListOfPermissions();
		}
	}

    /**
     * Gets level names.
     *
     * @param permissionLevelID the permission level id
     * @return the level names
     */
    public String getLevelNames(Integer permissionLevelID) {
		return getPermissionValues(permissionLevelID).getPermissionLevelName();
	}

    /**
     * Has user id boolean.
     *
     * @param permissionLevelID the permission level id
     * @param userID            the user id
     * @return the boolean
     */
    public boolean hasUserID(Integer permissionLevelID, Long userID)
	{
		return getPermissionValues(permissionLevelID).hasUserID(userID);
	}

    /**
     * Gets user level.
     *
     * @param userID the user id
     * @return the user level
     */
    public Integer getUserLevel(Long userID)
	{
		Integer pLevel = null;
		//System.out.println("Permsission Size: " + listOfPermissions.size());
		for(Integer permissionLevel : listOfPermissions.keySet())
		{
			//System.out.println("permissionLevel " + permissionLevel + " is: "+ getPermissionValues(permissionLevel).hasUserID(userID));
			if (getPermissionValues(permissionLevel).hasUserID(userID))
			{
				//System.out.println("If statement is true: setting: " + permissionLevel);
				pLevel = permissionLevel;
			}
		}
		
		//System.out.println("Permission is: " + pLevel);
		return pLevel;
	}

    /**
     * Gets role level.
     *
     * @param roleID the role id
     * @return the role level
     */
    public Integer getRoleLevel(Long roleID)
	{
		Integer pLevel = null;
		for(Integer permissionLevel : listOfPermissions.keySet())
		{
			if (getPermissionValues(permissionLevel).hasRoleID(roleID))
			{
				pLevel = permissionLevel;
			}
		}
		
		return pLevel;
	}

    /**
     * Remove user id.
     *
     * @param permissionLevelID the permission level id
     * @param userID            the user id
     */
    public void removeUserID(Integer permissionLevelID, Long userID)
	{
		getPermissionValues(permissionLevelID).removeUserID(userID);
	}

    /**
     * Sets user id.
     *
     * @param permissionLevelID the permission level id
     * @param userID            the user id
     */
    public void setUserID(Integer permissionLevelID, Long userID)
	{
				
		for(Integer permissionLevel : listOfPermissions.keySet()) //remove old user
		{
			ListOfPermissions tempLop = listOfPermissions.get(permissionLevel);
			//System.out.println(temp_lop.hasUserID(userID));
			if(tempLop.hasUserID(userID));
			{
				tempLop.removeUserID(userID);
			}
		}
		
		ListOfPermissions lop = getPermissionValues(permissionLevelID);
		lop.setUserID(userID);
		listOfPermissions.put(permissionLevelID, lop);
	}

    /**
     * Sets role id.
     *
     * @param permissionLevelID the permission level id
     * @param roleID            the role id
     */
    public void setRoleID(Integer permissionLevelID, Long roleID)
	{
		for(Integer permissionLevel : listOfPermissions.keySet()) //remove old role
		{
			ListOfPermissions tempLop = listOfPermissions.get(permissionLevel);
			
			if(tempLop.hasRoleID(roleID));
			{
				tempLop.removeRoleID(roleID);
			}
		}
		
		ListOfPermissions lop = getPermissionValues(permissionLevelID);
		lop.setRoleID(roleID);
		listOfPermissions.put(permissionLevelID, lop);
	}

    /**
     * Has role id boolean.
     *
     * @param permissionLevelID the permission level id
     * @param roleID            the role id
     * @return the boolean
     */
    public boolean hasRoleID(Integer permissionLevelID, Long roleID)
	{
		return getPermissionValues(permissionLevelID).hasRoleID(roleID);
	}

    /**
     * Remove role id.
     *
     * @param permissionLevelID the permission level id
     * @param roleID            the role id
     */
    public void removeRoleID(Integer permissionLevelID, Long roleID)
	{
		getPermissionValues(permissionLevelID).removeRoleID(roleID);
	}

    /**
     * Sets level names.
     *
     * @param permissionLevel      the permission level
     * @param permissionLevelNames the permission level names
     */
    public void setLevelNames(Integer permissionLevel, String permissionLevelNames) {
		ListOfPermissions lop = getPermissionValues(permissionLevel);
		lop.setPermissionLevelName(permissionLevelNames);
		listOfPermissions.put(permissionLevel, lop);
	}

    /**
     * Sets level.
     *
     * @param commandID    the command id
     * @param commandLevel the command level
     */
    public void setLevel(Integer commandID, Integer commandLevel)
	{
		listOfCommandsLevel.put(commandID, commandLevel);
	}

    /**
     * Gets level.
     *
     * @param commandID the command id
     * @return the level
     */
    public Integer getLevel(Integer commandID)
	{
		return listOfCommandsLevel.get(commandID);
	}
	
}
