package bot;

//Input/Output Imports
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.*;

/**
 * Tool to run database scripts
 */
public class ScriptRunner {
    private static final String DEFAULT_DELIMITER = ";";

    private Connection connection;
    private boolean stopOnError;
    private String delimiter;
    private boolean fullLineDelimiter;

    /**
     * Constructor
     *
     * @param aConnection SQL connection
     * @param aStopOnError Whether or not to stop when an error occurs
     */
    public ScriptRunner(Connection aConnection, boolean aStopOnError) {
        initScriptRunner(aConnection, aStopOnError, DEFAULT_DELIMITER, false);
    }

    /**
     * Constructor
     *
     * @param aConnection SQL connection
     * @param aStopOnError Whether or not to stop when an error occurs
     * @param aDelimiter SQL statement delimiter char
     */
    public ScriptRunner(Connection aConnection, boolean aStopOnError, String aDelimiter) {
        initScriptRunner(aConnection, aStopOnError, aDelimiter, false);
    }

    /**
     * Constructor
     *
     * @param aConnection SQL connection
     * @param aStopOnError Whether or not to stop when an error occurs
     * @param aDelimiter SQL statement delimiter char
     * @param aFullLineDelimiter Whether the delimiter ends the full line (true), or only the statement preceding it (false)
     */
    public ScriptRunner(Connection aConnection, boolean aStopOnError, String aDelimiter, boolean aFullLineDelimiter) {
        initScriptRunner(aConnection, aStopOnError, aDelimiter, aFullLineDelimiter);
    }

    /**
     * Script runner initialization method
     *
     * @param aConnection SQL connection
     * @param aStopOnError Whether or not to stop when an error occurs
     * @param aDelimiter SQL statement delimiter char
     * @param aFullLineDelimiter Whether the delimiter ends the full line (true), or only the statement preceding it (false)
     */
    private void initScriptRunner(Connection aConnection, boolean aStopOnError, String aDelimiter, boolean aFullLineDelimiter) {
        connection = aConnection;
        stopOnError = aStopOnError;
        delimiter = aDelimiter;
        fullLineDelimiter = aFullLineDelimiter;
    }

    /**
     * Change the SQL statement delimiter
     *
     * @param aDelimiter SQL statement delimiter char
     */
    public void setDelimiter(String aDelimiter) {
        delimiter = aDelimiter;
    }

    /**
     * Set wether or not the delimiter ends the full line
     *
     * @param aFullLineDelimiter Whether the delimiter ends the full line (true), or only the statement preceding it (false)
     */
    public void setFullLineDelimiter(boolean aFullLineDelimiter) {
        fullLineDelimiter = aFullLineDelimiter;
    }

    /**
     * Retrieve the delimiter
     *
     * @return The delimiter
     */
    private String getDelimiter() {
        return delimiter;
    }

    /**
     * Runs an SQL script (read in using the Reader parameter)
     *
     * @param reader File reader that holds the script file
     * @throws IOException
     * @throws SQLException
     */
    public void runScript(Reader reader) throws IOException, SQLException {
        runScript(connection, reader);
    }

    /**
     * Runs an SQL script (read in using the Reader parameter) using the connection passed in
     *
     * @param conn SQL connection
     * @param reader File reader that holds the script file
     * @throws IOException
     * @throws SQLException
     */
    private void runScript(Connection conn, Reader reader) throws IOException, SQLException {
        StringBuffer command = null;

        try {
            LineNumberReader lineReader = new LineNumberReader(reader);
            String line;

            while ((line = lineReader.readLine()) != null) {
                if (command == null)
                    command = new StringBuffer();

                String trimmedLine = line.trim();

                if (trimmedLine.length() < 1 || trimmedLine.startsWith("//") || trimmedLine.startsWith("--")) {
                    // Do nothing
                } else if (!fullLineDelimiter && trimmedLine.endsWith(getDelimiter())
                           || fullLineDelimiter && trimmedLine.equals(getDelimiter())) {
                    command.append(line.substring(0, line .lastIndexOf(getDelimiter())));
                    command.append(" ");
                    Statement statement = conn.createStatement();
                    boolean hasResults = false;

                    if (stopOnError) {
                        hasResults = statement.execute(command.toString());
                    } else {
                        try {
                            statement.execute(command.toString());
                        } catch (SQLException e) {
                            e.fillInStackTrace();
                            e.printStackTrace();
                        }
                    }

                    if (!conn.getAutoCommit())
                        conn.commit();

                    ResultSet rs = statement.getResultSet();

                    if (hasResults && rs != null) {
                        ResultSetMetaData md = rs.getMetaData();
                        int cols = md.getColumnCount();

                        for (int i = 0; i < cols; i++) {
                            String name = md.getColumnLabel(i);
                            System.out.print(name + "\t");
                        }

                        System.out.print("");

                        while (rs.next()) {
                            for (int i = 0; i < cols; i++) {
                                String value = rs.getString(i);
                                System.out.print(value + "\t");
                            }

                            System.out.print("");
                        }
                    }

                    command = null;

                    try {
                        statement.close();
                    } catch (Exception e) {
                        // Ignore to workaround a bug in Jakarta DBCP
                    }

                    Thread.yield();
                } else {
                    command.append(line);
                    command.append(" ");
                }
            }

            if (!conn.getAutoCommit())
                conn.commit();
        } catch (SQLException ex) {
            System.out.print("Error executing: " + command);
            ex.fillInStackTrace();
            ex.printStackTrace();
            conn.rollback();

            throw ex;
        } catch (IOException ex) {
            System.out.print("Error executing: " + command);
            ex.fillInStackTrace();
            ex.printStackTrace();
            conn.rollback();

            throw ex;
        }
    }
}
