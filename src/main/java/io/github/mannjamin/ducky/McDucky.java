package io.github.mannjamin.ducky;

//JDA Imports

import io.github.mannjamin.ducky.database.manager.DatabaseManager;
import io.github.mannjamin.ducky.eventmanager.ConsoleMessage;
import io.github.mannjamin.ducky.eventmanager.EventConnect;
import io.github.mannjamin.ducky.util.ConfigUtil;
import io.github.mannjamin.ducky.util.Configuration;
import io.github.mannjamin.ducky.util.UndefinedTokenException;
import io.socket.client.IO;
import io.socket.client.Socket;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * The Main class
 */
public class McDucky {
    private static final String DB_DRIVER = "jdbc";
    private static final String DB_TYPE = "sqlite";

    private final EventListener eventListener;
    private final Configuration config;
    private final Connection databaseConnection;
    private final DatabaseManager databaseManager;
    private final Socket socket;
    private final JDA jda;

    /**
     * Creates a new instance of the bot
     *
     * @throws IOException             The file system failed during setup
     * @throws UndefinedTokenException The bot's token was not defined
     * @throws SQLException            The connection to the database fails
     * @throws URISyntaxException      Socket connection URI could not be built
     * @throws LoginException          The bot could not log into the Discord gateway
     * @throws InterruptedException    The bot was interrupted during login
     * @throws RateLimitedException    The bot was rate-limited by the Discord gateway
     */
    public McDucky() throws IOException, UndefinedTokenException, SQLException, URISyntaxException,
        LoginException, InterruptedException, RateLimitedException {
        // I basically rewrote this file because this method of constructor is a memory leak.
        // the constructor should not be permitted to initialize an object nor continue if it
        // has failed at some point. As such, the exceptions have been added to the method signature.

        // start by loading the config
        config = ConfigUtil.getConfiguration();

        // now the database
        final File databaseFile = new File(config.databaseName + ".db");
        if (!databaseFile.exists()) throw new FileNotFoundException(databaseFile.getAbsolutePath());

        final String url = String.format("%s:%s:%s", McDucky.DB_DRIVER, McDucky.DB_TYPE, databaseFile.getAbsolutePath());
        databaseConnection = DriverManager.getConnection(url);
        setupTables();

        databaseManager = new DatabaseManager(databaseConnection);

        // now for the socket
        final URI uri = new URI(String.format("http://%s:%d", config.socketHost, config.socketPort));
        socket = IO.socket(uri);

        eventListener = new EventListener(databaseManager, config.admin, socket);
        jda = new JDABuilder(AccountType.BOT)
            .setToken(config.token)
            .addEventListener(eventListener)
            .buildBlocking();
        socket.on(Socket.EVENT_CONNECT, new EventConnect(socket, eventListener));

        // lastly, some debug stuff
        // I think...
        final List<TextChannel> textChannelList = jda.getTextChannelsByName("programming-geek", false);
        if (!textChannelList.isEmpty()) {
            final TextChannel channel = textChannelList.get(0);
            System.out.println("Ducky: listening to consoleMessages");
            socket.on("consoleMessage", new ConsoleMessage(socket, eventListener, channel));
        }

        // now connect
        socket.connect();
    }

    /**
     * This is the method where the program starts.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // FIXME Somebody should put some proper logging down here
        try {
            new McDucky();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UndefinedTokenException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup Tables for the first time, to make sure we always have the correct tables upon run
     *
     * @throws SQLException If an error occurs when setting up the tables
     */
    private void setupTables() throws SQLException {
        Reader fileReader;

        // Try to open the file into a fileStream (Reader in Java).
        try {
            URI sqlFile = getClass().getClassLoader().getResource("duckyDB.sql").toURI();
            fileReader = new FileReader(new File(sqlFile));
        } catch (Exception ex) {
            ex.fillInStackTrace();
            ex.printStackTrace();

            throw new SQLException("Unable to open the sql file.");
        }

        // Try to run the script using the script runner class we created.
        try {
            ScriptRunner sqlScriptRunner = new ScriptRunner(databaseConnection, true);
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


}
