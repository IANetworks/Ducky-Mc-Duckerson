package bot.database.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import bot.database.manager.data.ConfigDB;


public class DatabaseManager {
	private Connection conn;
	private Map<Long, ConfigDB> listConfigDB = new HashMap<Long, ConfigDB>();

	public DatabaseManager(Connection conn) throws SQLException {
		this.conn = conn;
		fetchConfigDB();
	}
	
	private void fetchConfigDB() throws SQLException
	{
		Statement stmt = this.conn.createStatement();
		String sql = "SELECT * FROM variables";
		ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next()) {
				ConfigDB configDB = new ConfigDB();
				configDB.setGuildId(rs.getLong("guild_id"));
				configDB.setGreeting(rs.getString("greeting_msg"));
				configDB.setGreetingChannel(rs.getString("greeting_channel"));
				configDB.setLoggingChannel(rs.getString("logging_channel"));
				configDB.setPrefix(rs.getString("prefix"));
				configDB.isStored = true;
				
				listConfigDB.put(configDB.getGuildId(), configDB);
			}
	}
	
//	public ConfigDB getConfigDB(Long guildID) {
//		if(listConfigDB.containsKey(guildID))
//		{
//			//we'll return the one we have stored
//			return this.listConfigDB.get(guildID);
//		} else {
//			//We'll return a new ConfigDB so we'll never return null
//			return new ConfigDB();
//		}
//	}
	
//	public void setConfigDB(ConfigDB configDB, Long guildID) {
//		this.listConfigDB.put(guildID, configDB);
//	}
	
	private ConfigDB getValues(Long guildID) {
		
		if(listConfigDB.containsKey(guildID))
		{
			//we'll return the one we have stored
			return this.listConfigDB.get(guildID);
		} else {
			return new ConfigDB();
		}
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
	public boolean isStored(Long guildID) {
		return getValues(guildID).isStored;
	}
	
	//TODO break out repeated codes into a function
	public void setPrefix(String prefix, Long guildID) throws SQLException {
		//Fetch Database, if it exists, update, insert otherwise
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = " + guildID.toString();
		ResultSet rs = stmt.executeQuery(sql);
		String sql2;
		Integer rowCount = 0;
		
		while(rs.next())
		{
			rowCount = rs.getInt(1);	
		}
		if (rowCount > 0)
		{
			sql2 = "UPDATE variables SET prefix = '" + prefix + "' WHERE guild_id = " + guildID.toString();	
		} else {
			 sql2 = "INSERT INTO variables (guild_id, prefix) VALUES (" + guildID.toString() + ", '" + prefix + "');";
		}
		
		stmt.execute(sql2);
		//Check List
		if(listConfigDB.containsKey(guildID))
		{
			//if it does, fetch configDB from list, update values and store
			listConfigDB.get(guildID).setPrefix(prefix);
		} else {
			//If it doesn't exist, create new configDB for guildID, set value, update
			ConfigDB configDB = new ConfigDB();
			configDB.setPrefix(prefix);
			listConfigDB.put(guildID, configDB);
		}
		
	}
	
	public void setGreeting(String greeting, Long guildID) throws SQLException {
		//Fetch Database, if it exists, update, insert otherwise
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = " + guildID.toString();
		ResultSet rs = stmt.executeQuery(sql);
		String sql2;
		Integer rowCount = 0;
		
		while(rs.next())
		{
			rowCount = rs.getInt(1);	
		}
		if (rowCount > 0)
		{
			sql2 = "UPDATE variables SET greeting_msg = '" + greeting + "' WHERE guild_id = " + guildID.toString();	
		} else {
			 sql2 = "INSERT INTO variables (guild_id, greeting_msg) VALUES (" + guildID.toString() + ", '" + greeting + "');";
		}
		
		stmt.execute(sql2);
		//Check List
		if(listConfigDB.containsKey(guildID))
		{
			//if it does, fetch configDB from list, update values and store
			listConfigDB.get(guildID).setGreeting(greeting);
		} else {
			//If it doesn't exist, create new configDB for guildID, set value, update
			ConfigDB configDB = new ConfigDB();
			configDB.setGreeting(greeting);
			listConfigDB.put(guildID, configDB);
		}
	}
	
	public void setGreetingChannel(String greetingChannel, Long guildID) throws SQLException {
		//Fetch Database, if it exists, update, insert otherwise
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = " + guildID.toString();
		ResultSet rs = stmt.executeQuery(sql);
		String sql2;
		Integer rowCount = 0;
		
		while(rs.next())
		{
			rowCount = rs.getInt(1);	
		}
		if (rowCount > 0)
		{
			sql2 = "UPDATE variables SET greeting_channel = '" + greetingChannel + "' WHERE guild_id = " + guildID.toString();	
		} else {
			 sql2 = "INSERT INTO variables (guild_id, greeting_channel) VALUES (" + guildID.toString() + ", '" + greetingChannel + "');";
		}
		
		stmt.execute(sql2);
		//Check List
		if(listConfigDB.containsKey(guildID))
		{
			//if it does, fetch configDB from list, update values and store
			listConfigDB.get(guildID).setGreetingChannel(greetingChannel);
		} else {
			//If it doesn't exist, create new configDB for guildID, set value, update
			ConfigDB configDB = new ConfigDB();
			configDB.setGreetingChannel(greetingChannel);
			listConfigDB.put(guildID, configDB);
		}
	}
	
	public void setLoggingChannel(String loggingChannel, Long guildID) throws SQLException {
		//Fetch Database, if it exists, update, insert otherwise
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = " + guildID.toString();
		ResultSet rs = stmt.executeQuery(sql);
		String sql2;
		Integer rowCount = 0;
		
		while(rs.next())
		{
			rowCount = rs.getInt(1);	
		}
		if (rowCount > 0)
		{
			sql2 = "UPDATE variables SET logging_channel = '" + loggingChannel + "' WHERE guild_id = " + guildID.toString();	
		} else {
			 sql2 = "INSERT INTO variables (guild_id, logging_channel) VALUES (" + guildID.toString() + ", '" + loggingChannel + "');";
		}
		
		stmt.execute(sql2);
		//Check List
		if(listConfigDB.containsKey(guildID))
		{
			//if it does, fetch configDB from list, update values and store
			listConfigDB.get(guildID).setLoggingChannel(loggingChannel);
		} else {
			//If it doesn't exist, create new configDB for guildID, set value, update
			ConfigDB configDB = new ConfigDB();
			configDB.setLoggingChannel(loggingChannel);
			listConfigDB.put(guildID, configDB);
		}
	}
	
}
