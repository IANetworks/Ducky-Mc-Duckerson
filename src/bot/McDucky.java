package bot;

//JDA Imports
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

import bot.database.manager.DatabaseManager;

//Input/Output Imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Properties;

//Database Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;

// Use UserID to store unquie ID of each enitity
//TODO Logging (to echo certain logs that's not tracked in Aduit. Such as quit/joins.
//TODO Permissions, set custom permissions, tie into role, so for example someone with the role "BotHandler" 
//		can do everything while someone with indepentent role can do games only. 
//TODO Welcome after someone select .iam for the first time
//TODO Role assistment 
//TODO Greeting Message - Greet someone joinning for the first time  
//TODO Custom Reaction - !fb/!faq and so on, allow this to be defined at runtime instead of hard code
//TODO FAQ - allow someone to request a FAQ link to Guns of Icarus Online   
//TODO Forum Tie in - Scrape the forum of user profiles. (This is just about the only thing we can do, without hacking the game client.  <- We might do this.
//TODO Music - The way Discord handles Bots, is that we can have different programs use the same token so we can easily have another bot running just for 
//		Music and not worry about coding it into this program

/**
 * The type Mc ducky.
 */
public class McDucky
{
	//Contains private keys, for bot Tokens, Database and other configs
	private File configFile = new File("config.properties");
	private Properties configProps;
    /**
     * The Conn.
     */
    Connection conn = null;
		
	private void loadProperties() throws IOException {
		configProps = new Properties();
		
		// loads properties from file
		InputStream inputStream = new FileInputStream(configFile);
		configProps.load(inputStream);
		inputStream.close();
	}
	
	private void setupProperties() throws IOException
	{
		//List all the config we need to successful run this bot
		configProps.setProperty("bot_token", "");
		configProps.setProperty("debug_level", "0"); //Set to Off
		configProps.setProperty("database_name", "default");
		
		//Save to file		
		OutputStream outputStream = new FileOutputStream(configFile);
		configProps.store(outputStream, "Set your configs here");
		outputStream.close();
	}

	/**
	 * Setup Tables for the first time, to make sure we always have the correct tables upon run
	 *
	 * @throws SQLException
	 */
	private void setupTables() throws SQLException
	{
		//Shouldn't happen but let make sure we have conn
		if(conn == null) //ERROR ERROR WILL ROBINSON
			throw new SQLException("SQL connection failed.");

		Reader fileReader;

		// Try to open the file into a fileStream (Reader in Java).
		try {
			fileReader = new FileReader("duckyDB_Rev2.sql");
		} catch (Exception ex) {
			ex.fillInStackTrace();
			ex.printStackTrace();

			throw new SQLException("Unable to open the sql file.");
		}

		// Try to run the script using the script runner class we created.
		try {
			ScriptRunner sqlScriptRunner = new ScriptRunner(conn, true);
			sqlScriptRunner.runScript(fileReader);
			System.out.printf("Default database tables have been created.\n");
		} catch (SQLException ex) {
			// Rethrow SQLExceptions for later catching.
			throw ex;
		} catch (Exception ex) {
			// If any exotic exception occurred, print stack to console
			ex.fillInStackTrace();
			ex.printStackTrace();
		} finally {
			// Gotta clean up after our selves
			try {
				fileReader.close();
			} catch (Exception ex) {
				// Safe to ignore, should only throw an error if the file handle is NULL
			}
		}
	}

    /**
     * Instantiates a new Mc ducky.
     */
//Our setup. My god, Abby is terrible at commenting
	public McDucky() {

    	//Load properties from file
    	try
    	{
    		loadProperties();
    	} catch (IOException ex) {
    		System.out.printf("there's no config file, creating file.");
			try {
				setupProperties();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Alert the user that, hey, we need values to run. 
			System.out.printf("Set values in config.propteries before running bot.");
			return;
    		
    	}
    	
		//Load Config Values into program memory
		String botToken = configProps.getProperty("bot_token"); //Bot Token that is used to log into Discord
		String databaseName = configProps.getProperty("database_name", "default"); //Database Name
		String botAdmin = configProps.getProperty("bot_admin"); //In case Bot Owner is different from the creator of the bot token, this is rare but in the program creator situation this has happened. 
		DatabaseManager dbMan = null;
		
		int debugLevel;
		try {
			 debugLevel = Integer.parseInt(configProps.getProperty("debug_level"));
		} catch (NumberFormatException e)
		{
			System.out.printf("debug_level is not a valid number. switching to debug off\n");
			debugLevel = 0;
		}
		DebugLevel dbug = DebugLevel.getDebugLevel(debugLevel);
		
		
		//Let make sure we don't have an empty value
		if (botToken.isEmpty())
		{
			//We GOT to have this value
			System.out.printf("Bot Token Cannot be empty. Set bot token in the config.properties\n");
			return;
		}
		
		String fileName = databaseName + ".db";
		//We'll check to see if our database exisit or not, if doesn't we need to make sure the tables are setup.
		final File dbFile = new File(fileName);
		boolean dbExists = false; 
		
		if(dbFile.exists())
		{
			dbExists = true;
		}
			
		//connect to our Database
		
		try {
			String url = "jdbc:sqlite:" + fileName;
			conn = DriverManager.getConnection(url);
			System.out.printf("Connection to SQLite database: %s has been established.\n", databaseName);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		if(!dbExists)
		{
			try {
				setupTables();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// Destory the database so that it'll reset up the bot next time
				dbFile.delete();
				e.printStackTrace();
				return;
			}
		}
		
		//We setup a class for managing database infomation
		try {
			dbMan = new DatabaseManager(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    		
        //We construct a builder for a BOT account. If we wanted to use a CLIENT account
        // we would use AccountType.CLIENT
        try
        {
            new JDABuilder(AccountType.BOT)
                .setToken(botToken)  //The token of the account that is logging in.
                .addEventListener(new DebugListener(dbug)) //An instance of a class to handle verbose event logging
                .addEventListener(new EventListener(dbMan, botAdmin))  //An instance of a class that will handle events.
                .buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
        }
        catch (LoginException e)
        {
            //If anything goes wrong in terms of authentication, this is the exception that will represent it
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            //Due to the fact that buildBlocking is a blocking method, one which waits until JDA is fully loaded,
            // the waiting can be interrupted. This is the exception that would fire in that situation.
            //As a note: in this extremely simplified example this will never occur. In fact, this will never occur unless
            // you use buildBlocking in a thread that has the possibility of being interrupted (async thread usage and interrupts)
            e.printStackTrace();
        }
        catch (RateLimitedException e)
        {
            //The login process is one which can be ratelimited. If you attempt to login in multiple times, in rapid succession
            // (multiple times a second), you would hit the ratelimit, and would see this exception.
            //As a note: It is highly unlikely that you will ever see the exception here due to how infrequent login is.
            e.printStackTrace();
        }
	}

    /**
     * This is the method where the program starts.
     *
     * @param args the input arguments
     */
    public static void main(String[] args)
    {
    	new McDucky();
    }

    
}