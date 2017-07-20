package bot.database.manager.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SelfRoles {
	//List of roles with group level
	Map<Long, Integer> listOfRoles = new HashMap<Long,Integer>();
	Map<Integer, Boolean> groupExculsive =  new HashMap<Integer,Boolean>();
	Map<Integer, HashSet<Long>> rolesByGroup = new HashMap<Integer,HashSet<Long>>();
	//Fetch list of roles by group level
	
	///fetch group level and exclusive by role id
	
	//Getters
	public boolean hasGroup(Integer groupID)
	{
		return rolesByGroup.containsKey(groupID);
	}
	
	public Boolean isRoleExclusive(Long roleID)
	{
		if(listOfRoles.containsKey(roleID))
		{
			Integer groupID = getRoleGroup(roleID);
			//Ok, each role should have a groupID TODO - throw error if there's a missing groupID to roleID
			return isGroupExclusive(groupID);
			
		}
		else {
			//We don't have any roles by this ID so we return null cause we're dumbass
			return null;
		}
	}
	
	public Map<Long, Integer> getListOfRoles()
	{
		return listOfRoles;
	}

	public Boolean isGroupExclusive(Integer groupID) {
		return groupExculsive.get(groupID);
	}
	
	public Integer getRoleGroup(Long roleID)
	{
		return listOfRoles.get(roleID);
	}
	
	public HashSet<Long> getListOfRolesByGroup(Integer groupID)
	{
		if(rolesByGroup.containsKey(groupID))
		{
			return rolesByGroup.get(groupID);
		} else {
			//we don't have a list to do shit with so let tell them to piss off with a null
			return null;
		}
	}
	
	public boolean isRoleSelfAssignable(Long roleID)
	{
		return listOfRoles.containsKey(roleID);
	}
	//Setters
	//Add a new role - make role self assignable
	public boolean setNewRole(Long roleID, Integer groupID)
	{
		//Check to make sure we do not have an existing role
		if(listOfRoles.containsKey(roleID))
		{
			return false; //We'll return false to say that the new role was not added.
		} else {
			listOfRoles.put(roleID, groupID);
			//See if we have an existing groupID
			addGroupID(roleID, groupID, false);
			
			return true; //let calling know we've successfully added the new role
		}
	}
	
	public boolean setNewRole(Long roleID, Integer groupID, Boolean isExculsive)
	{
		//Check to make sure we do not have an existing role
		if(listOfRoles.containsKey(roleID))
		{
			return false; //We'll return false to say that the new role was not added.
		} else {
			listOfRoles.put(roleID, groupID);
			//See if we have an existing groupID
			addGroupID(roleID, groupID, isExculsive);
			
			return true; //let calling know we've successfully added the new role
		}
	}
	
	//This method will be called whenever we need to add a role to a group
	private void addGroupID(Long roleID, Integer groupID, Boolean isExculsive) {
		if(rolesByGroup.containsKey(groupID))
		{
			//we have an existing group so add role to the existing list
			rolesByGroup.get(groupID).add(roleID);
		} else {
			//create a new group
			HashSet<Long> newList = new HashSet<Long>();
			newList.add(roleID);
			rolesByGroup.put(groupID, newList);
			if(isExculsive != null)
			{
				groupExculsive.put(groupID, isExculsive);
			} else {
				groupExculsive.put(groupID, false); // default to not exculsive
			}
		}
	}
	
	public Boolean setRoleExculsive(Long roleID, Boolean isExculsive)
	{
		if(!listOfRoles.containsKey(roleID))
		{
			return false; //Can't set exculsive if there's no self-assignable role
		} else {
			return setGroupExculsive(listOfRoles.get(roleID), isExculsive);
		}
	}
	
	public Boolean setGroupExculsive(Integer groupID, Boolean isExculsive)
	{
		
		if(!groupExculsive.containsKey(groupID))
		{
			return false; //Can't set exculsive if there's no existing group
		} else {
			groupExculsive.put(groupID, isExculsive);
			return true;
		}
	}
	
	public Boolean setRoleGroup(Long roleID, Integer newGroup)
	{
		Integer oldGroup;
		//Changing the role group
		//check to see if we have an existing roleID
		if(!listOfRoles.containsKey(roleID))
		{
			return false;
		} else {
			//get old groupID
			oldGroup = listOfRoles.get(roleID);
			//upgrade role List
			listOfRoles.put(roleID, newGroup);
			//upgrade group List
			if(rolesByGroup.containsKey(oldGroup))
			{
				rolesByGroup.get(oldGroup).remove(roleID);
			}
			addGroupID(roleID, newGroup, false);
			return true;
		}
	}
	
	//setting a role as not self-assignable
	public Boolean removeRole(Long roleID)
	{
		if(listOfRoles.containsKey(roleID))
		{
			Integer oldGroup = listOfRoles.get(roleID);
			listOfRoles.remove(roleID);
			rolesByGroup.get(oldGroup).remove(roleID);
			return true;
		} else {
			return false; //inform calling code that we didn't remove any users
		}
	}
	
	//Remove group, WARNING - removing group will also set all roles in that group as not self assignable, returns all roles that were removed
	public HashSet<Long> removeGroup(Integer groupID)
	{
		if(rolesByGroup.containsKey(groupID))
		{
			HashSet<Long> oldList = rolesByGroup.get(groupID);
			rolesByGroup.remove(groupID);
			for(Long roleID : oldList)
			{
				listOfRoles.remove(roleID);//remove from self-assigned list
			}
			return oldList;
			
		} else {
			return null; //we'll return null if group ID doesn't exist, otherwise we'll return an empty list
		}
	}
}