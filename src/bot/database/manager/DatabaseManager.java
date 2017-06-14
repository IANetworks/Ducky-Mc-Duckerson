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
		String sql = "SELECT * FROM permission";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			Long guildId = rs.getLong("guild_id");
			if(!listGuildPermissions.containsKey(guildId)) {
				listGuildPermissions.put(guildId, new Permissions());
			}
			listGuildPermissions.get(guildId).setLevel(rs.getInt("command_id"), rs.getInt("level_id"));
		}
	}
	
	private void fetchPermissionGroup(Long guildID)
	{
		//Fetch list of permissions byUser and byLevel
	}
	
	private void fetchPermissioLevel(Long guildID)
	{
		//Fetch list of permission name defined by guild
	}
	
	private GuildSetting getValues(Long guildID) {
		
		if(listGuildSettings.containsKey(guildID))
		{
			//we'll return the one we have stored
			return this.listGuildSettings.get(guildID);
		} else {
			return new GuildSetting();
		}
	}
	
	public Integer getCommandLevel(Long guildID, Integer cmdID)
	{
		return listGuildPermissions.get(guildID).getLevel(cmdID);
	}
	
	public String getPrefix(Long guildID) {
		return getValues(guildID).getPrefix();
	}
	
	public String getGreeting(Long guildID) {
		return getValues(guildID).getGreeting();
	}
	public String getGreetingChannel(Long guildID) {
		return getValues(guildID).getGreetingChannel();
	}
	public String getLoggingChannel(Long guildID) {
		return getValues(guildID).getLoggingChannel();
	}
	public Boolean isLoggingOn(Long guildID) {
		return getValues(guildID).isLoggingOn();
	}
	public Boolean isGreetOn(Long guildID) {
		return getValues(guildID).isGreetOn();
	}
	public boolean isStored(Long guildID) {
		return getValues(guildID).isStored;
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
			sql2 = "UPDATE variables SET "+ columnName + " = '" + columnValue + "' WHERE "+ searchColName + " = " + searchValue;	
		} else {
			 sql2 = "INSERT INTO " + tableName + "(" + searchColName + ", " +  columnName + ") VALUES (" + searchValue + ", '" + values + "');";
		}
		
		stmt.execute(sql2);
	}
	
	public void setCommandLevel(Long guildID, Integer cmdID, Integer cmdLvl) throws SQLException {
		String tableName = "permission"; 
		String searchColName = "guild_id, command_id";  
		String searchValue = guildID.toString() + ", " + cmdID; 
		String columnName = "level_id"; 
		Integer columnValue = cmdLvl;
		
		setDatabaseValue(tableName, searchColName, searchValue, columnName, columnValue);
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setUserLevel(Long guildID, Integer levelID, Long userID) {
		//Set User level in Permissions
	}
	
	public void setRoleLevel(Long guildID, Integer levelID, Long roleID) {
		//Set Role level in permissions
	}
	
	public void setLevelName(Long guildID, Integer levelID, String levelName) {
		//Set Permission Level Name
	}
	
}
