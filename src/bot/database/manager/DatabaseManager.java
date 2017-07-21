package bot.database.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import bot.database.manager.data.GuildSetting;
import bot.database.manager.data.Permissions;
import bot.database.manager.data.SelfRoles;
import bot.database.manager.data.UserProfile;
import net.dv8tion.jda.core.entities.Role;

public class DatabaseManager {
	private Connection conn;
	private Map<Long, GuildSetting> listGuildSettings = new HashMap<Long, GuildSetting>();
	private Map<Long, Permissions> listGuildPermissions = new HashMap<Long, Permissions>();
	private Map<Long, SelfRoles> listOfSelfRoles = new HashMap<Long, SelfRoles>();

	public DatabaseManager(Connection conn) throws SQLException {
		this.conn = conn;
		fetchGuildSettings(); //Fetch guild settings and store
		fetchCmdLevels(); //Fetch list of custom permissions and store
		fetchPermissionGroup(); //Fetch all the permissions
		fetchPermissioLevel(); //Fetch all permission level names
		fetchSelfRole(); //fetch all self assigned roles
	}
	
	private void fetchGuildSettings() throws SQLException
	{
		Statement stmt = this.conn.createStatement();
		String sql = "SELECT * FROM variables";
		ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next()) {
				GuildSetting guildSetting = new GuildSetting();
				guildSetting.setGuildId(rs.getLong("guild_id"));
				guildSetting.setGreeting(rs.getString("greeting_msg"));
				guildSetting.setGreetOn(rs.getBoolean("greet_on"));
				guildSetting.setGreetingChannel(rs.getString("greeting_channel"));
				guildSetting.setLoggingOn(rs.getBoolean("logging_on"));
				guildSetting.setLoggingChannel(rs.getString("logging_channel"));
				guildSetting.setPrefix(rs.getString("prefix"));
				guildSetting.isStored = true;
				
				listGuildSettings.put(guildSetting.getGuildId(), guildSetting);
			}
	}
	
	private void fetchCmdLevels() throws SQLException
	{
		Statement stmt = this.conn.createStatement();
		String sql = "SELECT * FROM permission_commands";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			Long guildID = rs.getLong("guild_id");
			if(!listGuildPermissions.containsKey(guildID)) {
				listGuildPermissions.put(guildID, new Permissions());
			}
			listGuildPermissions.get(guildID).setLevel(rs.getInt("command_id"), rs.getInt("level_id"));
		}
	}
	
	private void fetchPermissionGroup() throws SQLException
	{
		Statement stmt = this.conn.createStatement();
		String sql = "SELECT * FROM permission_group";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next())
		{
			Long guildID = rs.getLong("guild_id");
			if(!listGuildPermissions.containsKey(guildID)) {
				listGuildPermissions.put(guildID, new Permissions());
			}
			
			Boolean user = rs.getBoolean("is_user");
			if(user)
			{
				listGuildPermissions.get(guildID).setUserID(rs.getInt("level_id"), rs.getLong("user_role_id"));	
			} else {
				listGuildPermissions.get(guildID).setRoleID(rs.getInt("level_id"), rs.getLong("user_role_id"));
			}
		}
	}
	
	private void fetchPermissioLevel() throws SQLException
	{
		//Fetch list of permission name defined by guild
		Statement stmt = this.conn.createStatement();
		String sql = "SELECT * FROM permission_level";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next())
		{
			Long guildID = rs.getLong("guild_id");
			if(!listGuildPermissions.containsKey(guildID)) {
				listGuildPermissions.put(guildID, new Permissions());
			}
			
			listGuildPermissions.get(guildID).setLevelNames(rs.getInt("level_id"), rs.getString("level_name"));
		}
	}
	
	private void fetchSelfRole() throws SQLException
	{
		//fetch list of self assignable roles by guild
		Statement stmt = this.conn.createStatement();
		String sql = "SELECT * FROM self_roles";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next())
		{
			Long guildID = rs.getLong("guild_id");
			if(!listOfSelfRoles.containsKey(guildID))
			{
				listOfSelfRoles.put(guildID, new SelfRoles());
			}
			
			listOfSelfRoles.get(guildID).setNewRole(rs.getLong("role_id"), rs.getInt("role_group_id"), rs.getBoolean("exclusive_on"));
		}
	}
	
	private GuildSetting getGuildValues(Long guildID) {
		
		if(listGuildSettings.containsKey(guildID))
		{
			//we'll return the one we have stored
			return this.listGuildSettings.get(guildID);
		} else {
			return new GuildSetting();
		}
	}
	private SelfRoles getSelfRoles(Long guildID) {
		
		if(listOfSelfRoles.containsKey(guildID))
		{
			//we'll return the one we have stored
			return this.listOfSelfRoles.get(guildID);
		} else {
			return new SelfRoles(); //return fresh object
		}
	}
	
	private Permissions getPermissionsValues(Long guildID)
	{
		if(listGuildPermissions.containsKey(guildID))
		{
			return listGuildPermissions.get(guildID);
		} else {
			return new Permissions();
		}
	}
	public UserProfile getUserProfile(Long guildID, Long userID) throws SQLException
	{
		UserProfile up = new UserProfile();
		String sql = "SELECT * FROM user_profile WHERE user_id = ? AND guild_id = ?"; 
		PreparedStatement prstmt = this.conn.prepareStatement(sql);
		prstmt.setLong(1, userID);
		prstmt.setLong(2, guildID);
		ResultSet rs = prstmt.executeQuery();
		Integer rowCount = 0;
		while(rs.next())
		{
			up.setBalance(rs.getLong("balance"));
			up.setPoints(rs.getLong("points"));
			up.setRank(rs.getInt("rank")); //TODO fetch Rank name from database use sql query JOIN
			up.setFlipped(rs.getLong("flipped"));
			up.setUnflipped(rs.getLong("unflipped"));
			up.setLevel(rs.getLong("level"));
			rowCount++;
		}
		
		if(rowCount == 0)
		{
			setUserProfile(guildID, userID);
			return getUserProfile(guildID, userID);
		}
		
		return up;
	}
	
	private void setUserProfile(Long guildID, Long userID) throws SQLException {
		String sql = "INSERT INTO user_profile (user_id, guild_id) VALUES (?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, userID);
		pstmt.setLong(2, guildID);
		pstmt.execute();
		
	}

	public void incUserFlipped(Long guildID, Long userID) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Long newFlipTotal = up.getFlipped();
		newFlipTotal++;
		
		String sql = "UPDATE user_profile SET flipped = ? WHERE guild_id = ? AND user_id = ?"; //TODO I'm sure there's a SQL query that can inc the count for us.
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newFlipTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void incUserUnflipped(Long guildID, Long userID) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Long newUnflipTotal = up.getUnflipped();
		newUnflipTotal++;
		
		String sql = "UPDATE user_profile SET unflipped = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newUnflipTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void addUserRank(Long guildID, Long userID, Integer value) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Integer newTotal = up.getRank() + value;
		
		String sql = "UPDATE user_profile SET rank = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void addUserLevel(Long guildID, Long userID, Long value) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Long newTotal = up.getLevel() + value;
		
		String sql = "UPDATE user_profile SET level = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void addUserBalance(Long guildID, Long userID, Long value) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Long newTotal = up.getBalance() + value;
		
		String sql = "UPDATE user_profile SET balance = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void addUserPoints(Long guildID, Long userID, Long value) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Long newTotal = up.getPoints() + value;
		
		String sql = "UPDATE user_profile SET points = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void remUserRank(Long guildID, Long userID, Integer value) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Integer newTotal = up.getRank() - value;
		
		String sql = "UPDATE user_profile SET rank = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void remUserLevel(Long guildID, Long userID, Long value) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Long newTotal = up.getLevel() - value;
		
		String sql = "UPDATE user_profile SET level = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
		
	}
	public void remUserBalance(Long guildID, Long userID, Long value) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Long newTotal = up.getBalance() - value;
		
		String sql = "UPDATE user_profile SET balance = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void remUserPoints(Long guildID, Long userID, Long value) throws SQLException
	{
		UserProfile up = getUserProfile(guildID, userID);
		Long newTotal = up.getPoints() - value;
		
		String sql = "UPDATE user_profile SET points = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void setUserRank(Long guildID, Long userID, Integer value) throws SQLException
	{
		getUserProfile(guildID, userID); //This will make sure we have a profile setup, in case of a new users 
		Integer newTotal = value;
		
		String sql = "UPDATE user_profile SET rank = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void setUserLevel(Long guildID, Long userID, Long value) throws SQLException
	{
		getUserProfile(guildID, userID);
		Long newTotal = value;
		
		String sql = "UPDATE user_profile SET level = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void setUserBalance(Long guildID, Long userID, Long value) throws SQLException
	{
		getUserProfile(guildID, userID);
		Long newTotal = value;
		
		String sql = "UPDATE user_profile SET balance = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void setUserPoints(Long guildID, Long userID, Long value) throws SQLException
	{
		getUserProfile(guildID, userID);
		Long newTotal = value;
		
		String sql = "UPDATE user_profile SET points = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void setUserFlipped(Long guildID, Long userID, Long value) throws SQLException
	{
		getUserProfile(guildID, userID);
		Long newTotal = value;
		
		String sql = "UPDATE user_profile SET flipped = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	public void setUserUnflipped(Long guildID, Long userID, Long value) throws SQLException
	{
		getUserProfile(guildID, userID);
		Long newTotal = value;
		
		String sql = "UPDATE user_profile SET unflipped = ? WHERE guild_id = ? AND user_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, newTotal);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
	}
	

	public Integer getCommandLevel(Long guildID, Integer commandID)
	{
		return getPermissionsValues(guildID).getLevel(commandID);
	}
	public String getPrefix(Long guildID) {
		return getGuildValues(guildID).getPrefix();
	}
	public String getGreeting(Long guildID) {
		return getGuildValues(guildID).getGreeting();
	}
	public String getGreetingChannel(Long guildID) {
		return getGuildValues(guildID).getGreetingChannel();
	}
	public String getLoggingChannel(Long guildID) {
		return getGuildValues(guildID).getLoggingChannel();
	}
	public Boolean isLoggingOn(Long guildID) {
		return getGuildValues(guildID).isLoggingOn();
	}
	public Boolean isGreetOn(Long guildID) {
		return getGuildValues(guildID).isGreetOn();
	}
	public boolean isStored(Long guildID) {
		return getGuildValues(guildID).isStored;
	}
	public String getLevelName(Long guildID, Integer levelID)
	{
		String thisThing = getPermissionsValues(guildID).getLevelNames(levelID);
		if (thisThing == null)
		{
			return levelID.toString();
		}
		
		return thisThing;
	}
	public boolean hasGroup(Long guildID, Integer groupID)
	{
		return getSelfRoles(guildID).hasGroup(groupID);
	}
	public Boolean isRoleExclusive(Long guildID, Long roleID)
	{
		return getSelfRoles(guildID).isRoleExclusive(roleID);
	}
	public Boolean isGroupExculsive(Long guildID, Integer groupID)
	{
		return getSelfRoles(guildID).isGroupExclusive(groupID);
	}
	public Integer getRoleGroup(Long guildID, Long roleID)
	{
		return getSelfRoles(guildID).getRoleGroup(roleID);
	}
	public Map<Long, Integer> getListOfRoles(Long guildID)
	{
		return getSelfRoles(guildID).getListOfRoles();
	}
	public HashSet<Long> getListOfRolesByGroup(Long guildID, Integer groupID)
	{
		return getSelfRoles(guildID).getListOfRolesByGroup(groupID);
	}
	public Integer getUserLevel(Long guildID, Long userID, List<Role> roleList) {
		Permissions permissions = listGuildPermissions.get(guildID);
		Integer userLevel = null;
		Integer roleLevel = null;
		
		if(permissions == null)
		{
			return null;
		}
		
		userLevel = permissions.getUserLevel(userID);
		if(userLevel != null) return userLevel; //userLevel take prioty over roleLevel
		Integer tempRole;
		for(Role r : roleList)
		{
			tempRole = permissions.getRoleLevel(r.getIdLong());
			if(tempRole !=null )
			{
				if (roleLevel == null)
				{
					roleLevel = tempRole;
				} else if (tempRole < roleLevel) { //See if tempRole is a higher ranked level
					roleLevel = tempRole;
				} 
			}
		}
		
		return roleLevel;
	}
	public boolean isRoleSelfAssignable(Long guildID, Long roleID)
	{
		if(listOfSelfRoles.containsKey(guildID))
		{
			return listOfSelfRoles.get(guildID).isRoleSelfAssignable(roleID);
		} else {
			return false; //no record for guild so there's no self assignable roles
		}
	}
	public boolean setNewPermissionNames(Long guildID) throws SQLException
	{
		Statement stmt;
		Integer count = null;
		stmt = this.conn.createStatement();
		String sql = "SELECT COUNT(*) FROM permission_level WHERE guild_id = " + guildID; //We're expecting no result returned, if there's nothing then we'll set up predefined list
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			count = rs.getInt(1);
		}
		
		if(count == 0)
		{
			//LOAD 'EM UP
			setLevelName(guildID, 0, "Bot Adminstrator");
			setLevelName(guildID, 1, "Server Owner");
			setLevelName(guildID, 2, "Assigned Adminstrator");
			setLevelName(guildID, 3, "Assigned Moderator");
			setLevelName(guildID, 4, "Assigned Operator");
			setLevelName(guildID, 999, "User");
			setLevelName(guildID, 9999, "Banned");
			return true;
		}
		
		return false;
	}

	public void setCommandLevel(Long guildID, Integer commandID, Integer commandLevel) throws SQLException {
		//Set the levels of the commands
		String sql = "SELECT COUNT(*) FROM permission_commands WHERE guild_id = ? AND command_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		pstmt.setInt(2, commandID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE permission_commands SET level_id = ? WHERE guild_id = ? AND command_id = ?";	
		} else {
			 sql = "INSERT INTO permission_commands (level_id, guild_id, command_id) VALUES (?, ?, ?)"; 
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, commandLevel);
		pstmt.setLong(2, guildID);
		pstmt.setInt(3, commandID);
		
		pstmt.execute();
		
		//check list
		if(listGuildPermissions.containsKey(guildID))
		{
			//if it does update
			listGuildPermissions.get(guildID).setLevel(commandID, commandLevel);
		} else {
			//if it doesn't add new permission
			Permissions newPermission= new Permissions();
			newPermission.setLevel(commandID, commandLevel);
			listGuildPermissions.put(guildID, newPermission);
		}
	}
	
	public void setLoggingOn(Boolean loggingOn, Long guildID) throws SQLException {		
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE variables SET logging_on = ? WHERE guild_id = ?";	
		} else {
			 sql = "INSERT INTO variables (logging_on, guild_id) VALUES (?, ?)"; 
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(2, guildID);
		pstmt.setBoolean(1, loggingOn);
		
		pstmt.execute();
		
		//Check List
		if(listGuildSettings.containsKey(guildID))
		{
			//if it does, fetch guildSetting from list, update values and store
			listGuildSettings.get(guildID).setLoggingOn(loggingOn);
		} else {
			//If it doesn't exist, create new guildSetting for guildID, set value, update
			GuildSetting guildSetting = new GuildSetting();
			guildSetting.setLoggingOn(loggingOn);
			listGuildSettings.put(guildID, guildSetting);
		}
	}
	
	public void setGreetOn(Boolean greetOn, Long guildID) throws SQLException {
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE variables SET greet_on = ? WHERE guild_id = ?";	
		} else {
			 sql = "INSERT INTO variables (greet_on, guild_id) VALUES (?, ?)"; 
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(2, guildID);
		pstmt.setBoolean(1, greetOn);
		
		pstmt.execute();
		
		//Check List
		if(listGuildSettings.containsKey(guildID))
		{
			//if it does, fetch guildSetting from list, update values and store
			listGuildSettings.get(guildID).setGreetOn(greetOn);
		} else {
			//If it doesn't exist, create new guildSetting for guildID, set value, update
			GuildSetting guildSetting = new GuildSetting();
			guildSetting.setGreetOn(greetOn);
			listGuildSettings.put(guildID, guildSetting);
		}
	}
	
	public void setPrefix(String prefix, Long guildID) throws SQLException {
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE variables SET prefix = ? WHERE guild_id = ?";	
		} else {
			 sql = "INSERT INTO variables (prefix, guild_id) VALUES (?, ?)"; 
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(2, guildID);
		pstmt.setString(1, prefix);
		
		pstmt.execute();
		
		//Check List
		if(listGuildSettings.containsKey(guildID))
		{	//if it does, fetch guildSetting from list, update values and store
			listGuildSettings.get(guildID).setPrefix(prefix);
		} else {
			//If it doesn't exist, create new guildSetting for guildID, set value, update
			GuildSetting guildSetting = new GuildSetting();
			guildSetting.setPrefix(prefix);
			listGuildSettings.put(guildID, guildSetting);
		}
	}
	
	public void setGreeting(String greeting, Long guildID) throws SQLException {
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE variables SET greeting_msg = ? WHERE guild_id = ?";	
		} else {
			 sql = "INSERT INTO variables (greeting_msg, guild_id) VALUES (?, ?)"; 
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(2, guildID);
		pstmt.setString(1, greeting);
		
		pstmt.execute();
		
		//Check List
		if(listGuildSettings.containsKey(guildID))
		{
			//if it does, fetch guildSetting from list, update values and store
			listGuildSettings.get(guildID).setGreeting(greeting);
		} else {
			//If it doesn't exist, create new guildSetting for guildID, set value, update
			GuildSetting guildSetting = new GuildSetting();
			guildSetting.setGreeting(greeting);
			listGuildSettings.put(guildID, guildSetting);
		}
	}
	
	public void setGreetingChannel(String greetingChannel, Long guildID) throws SQLException {
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE variables SET greeting_channel = ? WHERE guild_id = ?";	
		} else {
			 sql = "INSERT INTO variables (greeting_channel, guild_id) VALUES (?, ?)"; 
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(2, guildID);
		pstmt.setString(1, greetingChannel);
		
		pstmt.execute();
		//Check List
		if(listGuildSettings.containsKey(guildID))
		{
			//if it does, fetch guildSetting from list, update values and store
			listGuildSettings.get(guildID).setGreetingChannel(greetingChannel);
		} else {
			//If it doesn't exist, create new guildSetting for guildID, set value, update
			GuildSetting guildSetting = new GuildSetting();
			guildSetting.setGreetingChannel(greetingChannel);
			listGuildSettings.put(guildID, guildSetting);
		}
	}
	
	public void setLoggingChannel(String loggingChannel, Long guildID) throws SQLException {
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE variables SET logging_channel = ? WHERE guild_id = ?";	
		} else {
			 sql = "INSERT INTO variables (logging_channel, guild_id) VALUES (?, ?)"; 
		}
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, loggingChannel);
		pstmt.setLong(2, guildID);
		
		pstmt.execute();
		//Check List
		if(listGuildSettings.containsKey(guildID))
		{
			//if it does, fetch guildSetting from list, update values and store
			listGuildSettings.get(guildID).setLoggingChannel(loggingChannel);
		} else {
			//If it doesn't exist, create new guildSetting for guildID, set value, update
			GuildSetting guildSetting = new GuildSetting();
			guildSetting.setLoggingChannel(loggingChannel);
			listGuildSettings.put(guildID, guildSetting);
		}
	}

	public void setUserLevel(Long guildID, Integer levelID, Long userID)  throws SQLException {		
		String sql = "SELECT COUNT(*) FROM permission_group WHERE guild_id = ? AND user_role_id = ? AND is_user = 1";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE permission_group SET level_id = ? WHERE guild_id = ? AND user_role_id = ? AND is_user = 1";	
		} else {
			 sql = "INSERT INTO permission_group (level_id, guild_id, user_role_id, is_user) VALUES (?, ?, ?, 1)"; 
		}
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, levelID);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, userID);
		pstmt.execute();
		
		if(listGuildPermissions.containsKey(guildID))
		{
			listGuildPermissions.get(guildID).setUserID(levelID, userID);
		} else {
			//if it doesn't add new permission
			Permissions newPermission= new Permissions();
			newPermission.setUserID(levelID, userID);
			listGuildPermissions.put(guildID, newPermission);
		}
	}
	
	public void setRoleLevel(Long guildID, Integer levelID, Long roleID)  throws SQLException {
		String sql = "SELECT COUNT(*) FROM permission_group WHERE guild_id = ? AND user_role_id = ? AND is_user = 0";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE permission_group SET level_id = ? WHERE guild_id = ? AND user_role_id = ? AND is_user = 0";	
		} else {
			 sql = "INSERT INTO permission_group (level_id, guild_id, user_role_id, is_user) VALUES (?, ?, ?, 0)"; 
		}
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, levelID);
		pstmt.setLong(2, guildID);
		pstmt.setLong(3, roleID);
		pstmt.execute();
		
		//Set Role level in permissions
		if(listGuildPermissions.containsKey(guildID))
		{
			listGuildPermissions.get(guildID).setUserID(levelID, roleID);
		} else {
			//if it doesn't add new permission
			Permissions newPermission= new Permissions();
			newPermission.setUserID(levelID, roleID);
			listGuildPermissions.put(guildID, newPermission);
		}
	}
	
	public void setLevelName(Long guildID, Integer levelID, String levelName)  throws SQLException {
		String sql = "SELECT COUNT(*) FROM permission_level WHERE guild_id = ? AND level_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
		pstmt.setLong(1, guildID);
		pstmt.setInt(2, levelID);
		ResultSet rs = pstmt.executeQuery();
		Integer rowCount = 0;
		
		while(rs.next()) rowCount = rs.getInt(1);
		if (rowCount > 0)
		{
			sql = "UPDATE permission_level SET level_name = ? WHERE guild_id = ? AND level_id = ?";	
		} else {
			 sql = "INSERT INTO permission_level (level_name, guild_id, level_id) VALUES (?, ?, ?)"; 
		}
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, levelName);
		pstmt.setLong(2, guildID);
		pstmt.setInt(3, levelID);
		pstmt.execute();
		
		//Set Permission Level Name
		if(listGuildPermissions.containsKey(guildID))
		{
			listGuildPermissions.get(guildID).setLevelNames(levelID, levelName);
		} else {
			//if it doesn't add new permission
			Permissions newPermission= new Permissions();
			newPermission.setLevelNames(levelID, levelName);
			listGuildPermissions.put(guildID, newPermission);
		}
	}
	
	public boolean setNewSelfAssignableRole(Long guildID, Long roleID, Integer groupID) throws SQLException
	{
		return setNewSelfAssignableRole(guildID, roleID, groupID, null);
	}
	
	public boolean setNewSelfAssignableRole(Long guildID, Long roleID, Integer groupID, Boolean isExculsive) throws SQLException
	{
		boolean IsRoleNew = false;
		
		//Check List
		if(listOfSelfRoles.containsKey(guildID))
		{	
			if(isExculsive != null)
			{
				//We have an existing guild, so let check to make sure we don't assign an already existing role
				IsRoleNew = listOfSelfRoles.get(guildID).setNewRole(roleID, groupID, isExculsive);
			} else {
				isExculsive = listOfSelfRoles.get(guildID).isGroupExclusive(groupID);
				if(isExculsive == null) isExculsive = false; //this could happen if it's a new group default to false;
				IsRoleNew = listOfSelfRoles.get(guildID).setNewRole(roleID, groupID, isExculsive);
			}
			
		} else {
			//If it doesn't exist, create new guildSetting for guildID, set value, update
			isExculsive = false; //Default to false
			SelfRoles newSelfRoles = new SelfRoles();
			newSelfRoles.setNewRole(roleID, groupID, isExculsive);
			listOfSelfRoles.put(guildID, newSelfRoles);
			IsRoleNew = true;
		}
		
		if(IsRoleNew)
		{
			String sql = "INSERT INTO self_roles (guild_id, role_id, role_group_id, exclusive_on) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, guildID);
			pstmt.setLong(2, roleID);
			pstmt.setInt(3, groupID);
			pstmt.setBoolean(4, isExculsive);
			
			pstmt.execute();
		}
		
		return IsRoleNew;
	}
	
	public Boolean setGroupExculsive(Long guildID, Integer groupID, Boolean isExculsive) throws SQLException
	{
		boolean exculisveChanged = false;
		//Check List
		if(listOfSelfRoles.containsKey(guildID))
		{	//We have an existing guild, so let check to make sure we don't assign an already existing role
			exculisveChanged = listOfSelfRoles.get(guildID).setGroupExculsive(groupID, isExculsive);
		} else {
			exculisveChanged = false; //return false because we can only update self-assignable roles, if there's none already set for guild then there's no self assignable roles
		}
		
		if(exculisveChanged) {
			setExculsiveDatabase(guildID, groupID, isExculsive);
		}
		
		return exculisveChanged;
	}
	
	private void setExculsiveDatabase(Long guildID, Integer groupID, Boolean isExculsive) throws SQLException
	{
		String sql = "UPDATE self_roles SET exclusive_on = ? WHERE guild_id = ? AND role_group_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setBoolean(1, isExculsive);
		pstmt.setLong(2, guildID);
		pstmt.setInt(3, groupID);
		pstmt.execute();
	}

	public Boolean setRoleExculsive(Long guildID, Long roleID, Boolean isExculsive) throws SQLException
	{
		boolean exculisveChanged = false;
		Integer groupID = null; 
		//Check List
		if(listOfSelfRoles.containsKey(guildID))
		{	//We have an existing guild, so let check to make sure we don't assign an already existing role
			exculisveChanged = listOfSelfRoles.get(guildID).setRoleExculsive(roleID, isExculsive);
			groupID = listOfSelfRoles.get(guildID).getRoleGroup(roleID);
		} else {
			exculisveChanged = false; //return false because we can only update self-assignable roles, if there's none already set for guild then there's no self assignable roles
		}
		
		if(exculisveChanged) {
			setExculsiveDatabase(guildID, groupID, isExculsive);
		}
		
		return exculisveChanged;
	}
		
	public Boolean removeRole(Long guildID, Long roleID) throws SQLException
	{
		boolean hasChanged = false;
		if(listOfSelfRoles.containsKey(guildID))
		{	//We have an existing guild, so let check to make sure we don't assign an already existing role
			hasChanged = listOfSelfRoles.get(guildID).removeRole(roleID);
		} else {
			hasChanged = false; //return false, we don't have anything to remove.
		}
		
		if(hasChanged)
		{
			String sql = "DELETE FROM self_roles WHERE guild_id = ? AND role_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, guildID);
			pstmt.setLong(2, roleID);
			pstmt.executeUpdate();
		}
		
		return hasChanged;
	}
	
	public HashSet<Long> removeGroup(Long guildID, Integer groupID) throws SQLException
	{
		HashSet<Long> removedRolesList = null;
		if(listOfSelfRoles.containsKey(guildID))
		{
			removedRolesList = listOfSelfRoles.get(guildID).removeGroup(groupID);
		} else {
			removedRolesList = null;
		}
		
		if(removedRolesList != null)
		{
			if(removedRolesList.size() > 0)
			{
				String sql = "DELETE FROM self_roles WHERE guild_id = ? AND role_group_id = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, guildID);
				pstmt.setLong(2, groupID);
				pstmt.executeUpdate();
			}
		}
		
		return removedRolesList;
	}
	
	public Boolean setRoleGroup(Long guildID, Long roleID, Integer newGroup) throws SQLException
	{
		
		boolean hasChanged = false;
		if(listOfSelfRoles.containsKey(guildID))
		{
			hasChanged = listOfSelfRoles.get(guildID).setRoleGroup(roleID, newGroup);
		} else {
			hasChanged = false;
		}
		
		if(hasChanged)
		{
			String sql = "UPDATE self_roles SET role_group_id = ? WHERE guild_id = ? AND role_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newGroup);
			pstmt.setLong(2, guildID);
			pstmt.setLong(3, roleID);
			pstmt.execute();
		}
		
		return null;
	}
}
