package bot.database.manager.data;

import java.util.HashMap;
import java.util.Map;

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
	public String getLevelNames(Integer permissionLevelID) {
		return getPermissionValues(permissionLevelID).getPermissionLevelName();
	}
	
	public boolean hasUserID(Integer permissionLevelID, Long userID)
	{
		return getPermissionValues(permissionLevelID).hasUserID(userID);
	}
	
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
	public void removeUserID(Integer permissionLevelID, Long userID)
	{
		getPermissionValues(permissionLevelID).removeUserID(userID);
	}
	
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
	
	public boolean hasRoleID(Integer permissionLevelID, Long roleID)
	{
		return getPermissionValues(permissionLevelID).hasRoleID(roleID);
	}
	
	public void removeRoleID(Integer permissionLevelID, Long roleID)
	{
		getPermissionValues(permissionLevelID).removeRoleID(roleID);
	}

	public void setLevelNames(Integer permissionLevel, String permissionLevelNames) {
		ListOfPermissions lop = getPermissionValues(permissionLevel);
		lop.setPermissionLevelName(permissionLevelNames);
		listOfPermissions.put(permissionLevel, lop);
	}

	public void setLevel(Integer commandID, Integer commandLevel)
	{
		listOfCommandsLevel.put(commandID, commandLevel);
	}
	
	public Integer getLevel(Integer commandID)
	{
		return listOfCommandsLevel.get(commandID);
	}
	
}
