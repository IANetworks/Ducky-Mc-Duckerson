package bot.database.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.database.manager.data.GuildSetting;
import bot.database.manager.data.Permissions;
import net.dv8tion.jda.core.entities.Role;

public class DatabaseManager {
	private Connection conn;
	private Map<Long, GuildSetting> listGuildSettings = new HashMap<Long, GuildSetting>();
	private Map<Long, Permissions> listGuildPermissions = new HashMap<Long, Permissions>();

	public DatabaseManager(Connection conn) throws SQLException {
		this.conn = conn;
		fetchGuildSettings(); //Fetch guild settings and store
		fetchCmdLevels(); //Fetch list of custom permissions and store
		fetchPermissionGroup(); //Fetch all the permissions
		fetchPermissioLevel(); //Fetch all permission level names
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
	
	private GuildSetting getGuildValues(Long guildID) {
		
		if(listGuildSettings.containsKey(guildID))
		{
			//we'll return the one we have stored
			return this.listGuildSettings.get(guildID);
		} else {
			return new GuildSetting();
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
		{
			//if it does, fetch guildSetting from list, update values and store
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
			 sql = "INSERT INTO permission_level (level_name, guild_id, level_id) VALUES (?, ?, ?, 0)"; 
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
	
}
