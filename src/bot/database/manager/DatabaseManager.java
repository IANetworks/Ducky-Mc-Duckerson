package bot.database.manager;

import java.sql.Connection;
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

	//TODO I'm pretty sure this function can be improved even more
	private void setDatabaseValue(String tableName, String searchColName,  String searchValue, String columnName, Object columnValue) throws SQLException
	{
		String values;
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM "+ tableName + " WHERE " + searchColName + " = " + searchValue; 
		ResultSet rs = stmt.executeQuery(sql);
		String sql2;
		Integer rowCount = 0;
		
		if(columnValue instanceof String)
		{
			values = "'" + columnValue.toString() + "'";
		} else if (columnValue instanceof Integer)
		{
			values = columnValue.toString();
		} else if(columnValue instanceof Boolean)
		{
			if(((Boolean) columnValue).booleanValue())
			{
				values = "1";
			} else {
				values = "0";
			}
		} else {
			values = "";
			System.err.println("Unrecognised Type");
			return;
		}
		
		while(rs.next()) 
		{
			rowCount = rs.getInt(1);	
		}
		if (rowCount > 0)
		{
			sql2 = "UPDATE "+ tableName +" SET "+ columnName + " = '" + columnValue + "' WHERE "+ searchColName + " = " + searchValue;	
		} else {
			 sql2 = "INSERT INTO " + tableName + "(" + searchColName + ", " +  columnName + ") VALUES (" + searchValue + ", '" + values + "');";
		}
		
		stmt.execute(sql2);
	}
	
	public void setCommandLevel(Long guildID, Integer commandID, Integer commandLevel) throws SQLException {
		//Set the levels of the commands
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM permission_commands WHERE guild_id =" + guildID.toString() + " AND command_id = " + commandID.toString();
		ResultSet rs = stmt.executeQuery(sql);
		String sql2;
		Integer rowCount = 0;
		
		while(rs.next()) 
		{
			rowCount = rs.getInt(1);	
		}
		if (rowCount > 0)
		{
			sql2 = "UPDATE permission_commands SET level_id = " + commandLevel.toString() + " WHERE guild_id =" + guildID.toString() + " AND command_id = " + commandID.toString();	
		} else {
			 sql2 = "INSERT INTO permission_commands (guild_id, command_id, level_id) VALUES (" + guildID.toString() + ", " + commandID.toString() + ", " + commandLevel.toString()
			 + ");"; 
		}
		
		stmt.execute(sql2);
		
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
		//Fetch Database, if it exists, update, insert otherwise
		String tableName = "variables"; 
		String searchColName = "guild_id";  
		String searchValue = guildID.toString(); 
		String columnName = "logging_on"; 
		Boolean columnValue = loggingOn;
		
		setDatabaseValue(tableName, searchColName, searchValue, columnName, columnValue);
		
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
		//Fetch Database, if it exists, update, insert otherwise
		String tableName = "variables"; 
		String searchColName = "guild_id";  
		String searchValue = guildID.toString(); 
		String columnName = "greet_on"; 
		Boolean columnValue = greetOn;
		
		setDatabaseValue(tableName, searchColName, searchValue, columnName, columnValue);
		
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
		//Fetch Database, if it exists, update, insert otherwise
		String tableName = "variables"; 
		String searchColName = "guild_id";  
		String searchValue = guildID.toString(); 
		String columnName = "prefix"; 
		String columnValue = prefix;
		
		setDatabaseValue(tableName, searchColName, searchValue, columnName, columnValue);
		
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
		String tableName = "variables"; 
		String searchColName = "guild_id";  
		String searchValue = guildID.toString(); 
		String columnName = "greeting"; 
		String columnValue = greeting;
		
		setDatabaseValue(tableName, searchColName, searchValue, columnName, columnValue);
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
		//Fetch Database, if it exists, update, insert otherwise
		String tableName = "variables"; 
		String searchColName = "guild_id";  
		String searchValue = guildID.toString();; 
		String columnName = "greetingChannel"; 
		String columnValue = greetingChannel;
		
		setDatabaseValue(tableName, searchColName, searchValue, columnName, columnValue);
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
		//Fetch Database, if it exists, update, insert otherwise
		String tableName = "variables"; 
		String searchColName = "guild_id";  
		String searchValue = guildID.toString();; 
		String columnName = "logging_channel"; 
		String columnValue = loggingChannel;
		
		setDatabaseValue(tableName, searchColName, searchValue, columnName, columnValue);
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
		//Set the levels of the commands
		
		if(permissions == null)
		{
			return null;
		}
		
		userLevel = permissions.getUserLevel(userID);
		System.out.println("User Level is: " + userLevel);
		if(userLevel != null) return userLevel; //userLevel take prioty over roleLevel
		
		for(Role r : roleList)
		{
			Integer tempRole = permissions.getRoleLevel(r.getIdLong());
			if(tempRole !=null );
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
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM permission_group WHERE guild_id =" + guildID.toString() + " AND user_role_id = " + userID.toString()
		+ " AND is_user = 1"; 
		ResultSet rs = stmt.executeQuery(sql);
		String sql2;
		Integer rowCount = 0;
		
		while(rs.next()) 
		{
			rowCount = rs.getInt(1);	
		}
		if (rowCount > 0)
		{
			sql2 = "UPDATE permission_group SET level_id = " + levelID + " WHERE guild_id =" + guildID.toString() + " AND user_role_id = " + userID.toString()
			+ " AND is_user = 1";	
		} else {
			 sql2 = "INSERT INTO permission_group (guild_id, level_id, user_role_id, is_user) VALUES (" + guildID.toString() + ", " + levelID.toString() + ", " + userID.toString() 
			 + ", 1);";
		}
		
		stmt.execute(sql2);
		
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

		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM permission_group WHERE guild_id =" + guildID.toString() + " AND user_role_id = " + roleID.toString()
		+ " AND is_user = 0"; 
		ResultSet rs = stmt.executeQuery(sql);
		String sql2;
		Integer rowCount = 0;
		
		while(rs.next()) 
		{
			rowCount = rs.getInt(1);	
		}
		if (rowCount > 0)
		{
			sql2 = "UPDATE permission_group SET level_id = " + levelID + " WHERE guild_id =" + guildID.toString() + " AND user_role_id = " + roleID.toString()
			+ " AND is_user = 0";	
		} else {
			 sql2 = "INSERT INTO permission_group (guild_id, level_id, user_role_id, is_user) VALUES (" + guildID.toString() + ", " + levelID.toString() + ", " + roleID.toString() 
			 + ", 0);";
		}
		
		stmt.execute(sql2);
		
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
		//Set the levels of the commands
		//TODO FIX THIS, USE AND IN WHERE CLAUSE
		String tableName = "permission_commands"; 
		String searchColName = "guild_id, level_id";  
		String searchValue = guildID.toString() + ", " + levelID.toString(); 
		String columnName = "level_id"; 
		String columnValue = "'" + levelName + "'";
		
		setDatabaseValue(tableName, searchColName, searchValue, columnName, columnValue);
		
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
