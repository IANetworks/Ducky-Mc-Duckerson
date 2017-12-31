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
import java.util.Scanner;

/**
 * The Main class
 */
public class McDucky {
    private static final String DB_DRIVER = "jdbc";
    private static final String DB_TYPE = "sqlite";
    private static final String DB_FILE = "duckyDB.sql";

    private final EventListener eventListener;
    private final Configuration config;
    private final Connection databaseConnection;
    private final DatabaseManager databaseManager;
    private static Socket socket = null;
    private static JDA jda = null;
    private static boolean shutdown = false;

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
        if (shutdown) {
            databaseManager = null;
            databaseConnection = null;
            eventListener = null;
            return;
        }


        // now the database
        final File databaseFile = new File(config.databaseName + ".db");
        boolean newDatabase = false;
        if (!databaseFile.exists()) newDatabase = true;
        final String url = String.format("%s:%s:%s", McDucky.DB_DRIVER, McDucky.DB_TYPE, databaseFile.getAbsolutePath());
        databaseConnection = DriverManager.getConnection(url);

        if (newDatabase) setupTables();

        if (shutdown) {
            databaseManager = null;
            eventListener = null;
            databaseConnection.close();
            return;
        }

        databaseManager = new DatabaseManager(databaseConnection);

        // now for the socket
        final URI uri = new URI(String.format("http://%s:%d", config.socketHost, config.socketPort));
        socket = IO.socket(uri);

        eventListener = new EventListener(databaseManager, config.admin, socket);
        //if(shutdown) return;
        jda = new JDABuilder(AccountType.BOT)
            .setToken(config.token)
            .addEventListener(eventListener)
            .buildBlocking();

        if (shutdown) {
            jda.shutdown();
            return;
        }
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

        if (shutdown) {
            jda.shutdown();
            socket.disconnect();
        }

    }

    /**
     * This is the method where the program starts.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutting down...");
            // do things on shut down here
            shutdown = true;
            if (jda != null)
                jda.shutdown();
            if (socket != null)
                socket.close();
        }));

        // FIXME Somebody should put some proper logging down here
        // TODO SLF4J in general (Logging)
        try {
            new McDucky();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup Tables for the first time, to make sure we always have the correct tables upon run
     *
     * @throws SQLException If an error occurs when setting up the tables
     */
    private void setupTables() throws SQLException, IOException {
        Reader fileReader;

        // Try to open the file into a fileStream (Reader in Java).
        fileReader = new InputStreamReader(McDucky.class.getResourceAsStream("/" + DB_FILE));

        // Try to run the script using the script runner class we created.
        ScriptRunner sqlScriptRunner = new ScriptRunner(databaseConnection, true);
        sqlScriptRunner.runScript(fileReader);
        System.out.println("Default database tables have been created.");
    }


}
