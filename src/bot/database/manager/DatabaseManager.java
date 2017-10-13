package bot.database.manager;

import bot.database.manager.data.GuildSetting;
import bot.database.manager.data.Permissions;
import bot.database.manager.data.SelfRoles;
import bot.database.manager.data.UserProfile;
import net.dv8tion.jda.core.entities.Role;
import werewolf.data.Theme;
import werewolf.data.ThemeDesc;

import java.sql.*;
import java.util.*;

/**
 * The type Database manager.
 */
public class DatabaseManager {
    private Connection conn;
    private Map<Long, GuildSetting> listGuildSettings = new HashMap<Long, GuildSetting>();
    private Map<Long, Permissions> listGuildPermissions = new HashMap<Long, Permissions>();
    private Map<Long, SelfRoles> listOfSelfRoles = new HashMap<Long, SelfRoles>();

    /**
     * Instantiates a new Database manager.
     *
     * @param conn the conn
     * @throws SQLException the sql exception
     */
    public DatabaseManager(Connection conn) throws SQLException {
        this.conn = conn;
        fetchGuildSettings(); //Fetch guild settings and store
        fetchCmdLevels(); //Fetch list of custom permissions and store
        fetchPermissionGroup(); //Fetch all the permissions
        fetchPermissioLevel(); //Fetch all permission level names
        fetchSelfRole(); //fetch all self assigned roles
    }

    /**
     * Gets conn.
     *
     * @return the conn
     */
    public Connection getConn() {
        return this.conn;
    }

    private void fetchGuildSettings() throws SQLException {
        Statement stmt = this.conn.createStatement();
        String sql = "SELECT * FROM variables";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setGuildId(rs.getLong("guild_id"));
            guildSetting.setGreeting(rs.getString("greeting_msg"));
            guildSetting.setGreetOn(rs.getBoolean("greet_on"));
            guildSetting.setGreetingChannel(rs.getString("greeting_channel"));
            guildSetting.setLoggingOn(rs.getBoolean("logging_on"));
            guildSetting.setLoggingChannel(rs.getString("logging_channel"));
            guildSetting.setPrefix(rs.getString("prefix"));
            guildSetting.setWWOn(rs.getBoolean("werewolf_on"));
            guildSetting.setGameChannel(rs.getString("game_channel"));
            guildSetting.setDeleteCommand(rs.getBoolean("delete_command"));
            guildSetting.setEventOn(rs.getBoolean("event_on"));
            guildSetting.setEventChannel(rs.getString("event_channel"));
            guildSetting.isStored = true;

            listGuildSettings.put(guildSetting.getGuildId(), guildSetting);
        }
    }

    private void fetchCmdLevels() throws SQLException {
        Statement stmt = this.conn.createStatement();
        String sql = "SELECT * FROM permission_commands";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Long guildID = rs.getLong("guild_id");
            if (!listGuildPermissions.containsKey(guildID)) {
                listGuildPermissions.put(guildID, new Permissions());
            }
            listGuildPermissions.get(guildID).setLevel(rs.getInt("command_id"), rs.getInt("level_id"));
        }
    }

    private void fetchPermissionGroup() throws SQLException {
        Statement stmt = this.conn.createStatement();
        String sql = "SELECT * FROM permission_group";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Long guildID = rs.getLong("guild_id");
            if (!listGuildPermissions.containsKey(guildID)) {
                listGuildPermissions.put(guildID, new Permissions());
            }

            Boolean user = rs.getBoolean("is_user");
            if (user) {
                listGuildPermissions.get(guildID).setUserID(rs.getInt("level_id"), rs.getLong("user_role_id"));
            } else {
                listGuildPermissions.get(guildID).setRoleID(rs.getInt("level_id"), rs.getLong("user_role_id"));
            }
        }
    }

    private void fetchPermissioLevel() throws SQLException {
        //Fetch list of permission name defined by guild
        Statement stmt = this.conn.createStatement();
        String sql = "SELECT * FROM permission_level";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Long guildID = rs.getLong("guild_id");
            if (!listGuildPermissions.containsKey(guildID)) {
                listGuildPermissions.put(guildID, new Permissions());
            }

            listGuildPermissions.get(guildID).setLevelNames(rs.getInt("level_id"), rs.getString("level_name"));
        }
    }

    private void fetchSelfRole() throws SQLException {
        //fetch list of self assignable roles by guild
        Statement stmt = this.conn.createStatement();
        String sql = "SELECT * FROM self_roles";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Long guildID = rs.getLong("guild_id");
            if (!listOfSelfRoles.containsKey(guildID)) {
                listOfSelfRoles.put(guildID, new SelfRoles());
            }

            listOfSelfRoles.get(guildID).setNewRole(rs.getLong("role_id"), rs.getInt("role_group_id"), rs.getBoolean("exclusive_on"));
        }
    }

    private GuildSetting getGuildValues(Long guildID) {

        if (listGuildSettings.containsKey(guildID)) {
            //we'll return the one we have stored
            return this.listGuildSettings.get(guildID);
        } else {
            return new GuildSetting();
        }
    }

    private SelfRoles getSelfRoles(Long guildID) {

        if (listOfSelfRoles.containsKey(guildID)) {
            //we'll return the one we have stored
            return this.listOfSelfRoles.get(guildID);
        } else {
            return new SelfRoles(); //return fresh object
        }
    }

    private Permissions getPermissionsValues(Long guildID) {
        if (listGuildPermissions.containsKey(guildID)) {
            return listGuildPermissions.get(guildID);
        } else {
            return new Permissions();
        }
    }

    /**
     * Gets user profile.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @return the user profile
     * @throws SQLException the sql exception
     */
    public UserProfile getUserProfile(Long guildID, Long userID) throws SQLException {
        UserProfile up = new UserProfile();
        String sql = "SELECT * FROM user_profile LEFT JOIN rank_titles ON user_profile.rank = rank_titles.rank WHERE user_id = ? AND guild_id = ? ";
        PreparedStatement prstmt = this.conn.prepareStatement(sql);
        prstmt.setLong(1, userID);
        prstmt.setLong(2, guildID);
        ResultSet rs = prstmt.executeQuery();
        Integer rowCount = 0;
        while (rs.next()) {
            up.setBalance(rs.getLong("balance"));
            up.setPoints(rs.getLong("points"));
            up.setRank(rs.getInt("rank"));
            up.setFlipped(rs.getLong("flipped"));
            up.setUnflipped(rs.getLong("unflipped"));
            up.setLevel(rs.getLong("level"));
            up.setWerewolfWins(rs.getLong("werewolf_wins"));
            up.setWerewolfGames(rs.getLong("werewolf_games"));
            up.setRankName(rs.getString("rank_name"));
            up.setRankExp(rs.getLong("rank_exp"));
            if (rs.wasNull()) {
                up.setRankExp(null);
            }
            up.setCooldown(rs.getTimestamp("cooldown"));

            rowCount++;
        }

        if (rowCount == 0) {
            newUserProfile(guildID, userID);
            return getUserProfile(guildID, userID);
        }

        return up;
    }

    public Boolean isCooldownOver(Long guildID, Long userID) throws SQLException {
        String sql = "SELECT cooldown FROM user_profile WHERE user_id = ? AND guild_id = ? ";
        PreparedStatement prstmt = this.conn.prepareStatement(sql);
        prstmt.setLong(1, userID);
        prstmt.setLong(2, guildID);
        ResultSet rs = prstmt.executeQuery();
        Timestamp ts = null;
        Integer rowCount = 0;
        while (rs.next()) {
            ts = rs.getTimestamp("cooldown");
            rowCount++;
        }

        if (rowCount == 0) {
            newUserProfile(guildID, userID);
            return true;
        } else if (rowCount == 1) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.after(ts)) {
                setNextCooldown(guildID, userID);
                return true;
            } else {
                return false;
            }
        } else {
            //Error
            return null;
        }
    }

    private void setNextCooldown(Long guildID, Long userID) throws SQLException {
        String sql = "UPDATE user_profile SET cooldown = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setTimestamp(1, returnNextCooldown());
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    private void newUserProfile(Long guildID, Long userID) throws SQLException {
        String sql = "INSERT INTO user_profile (user_id, guild_id, cooldown) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        Timestamp nextCooldown = returnNextCooldown();
        pstmt.setLong(1, userID);
        pstmt.setLong(2, guildID);
        pstmt.setTimestamp(3, nextCooldown);
        pstmt.execute();

    }

    private Timestamp returnNextCooldown() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Long tmpTime = now.getTime();
        Long tmpDelay = 60000L; //One Minute
        return new Timestamp(tmpTime + tmpDelay);
    }

    /**
     * Inc user flipped.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @throws SQLException the sql exception
     */
    public void incUserFlipped(Long guildID, Long userID) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newFlipTotal = up.getFlipped();
        newFlipTotal++;

        String sql = "UPDATE user_profile SET flipped = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newFlipTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Inc user unflipped.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @throws SQLException the sql exception
     */
    public void incUserUnflipped(Long guildID, Long userID) throws SQLException {
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

    /**
     * Add user rank.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @return new expereince required to level up
     * @throws SQLException the sql exception
     */
    public RankUp addUserRank(Long guildID, Long userID, Integer value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        RankUp newRank = new RankUp();
        Integer newTotal = up.getRank() + value;

        String sql = "UPDATE user_profile SET rank = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();

        sql = "SELECT * FROM rank_titles WHERE rank = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, newTotal);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            newRank.rank = newTotal;
            newRank.expRequired = rs.getLong("rank_exp");
            if (rs.wasNull())
                newRank.expRequired = null;
            newRank.rankName = rs.getString("rank_name");
        }

        return newRank;

    }

    /**
     * Add user level.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public LinkedList<RankUp> addUserExp(Long guildID, Long userID, Long value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getLevel() + value;
        Long expNeeded = up.getRankExp();
        LinkedList<RankUp> newRanks = new LinkedList<RankUp>();

        while (newTotal >= expNeeded) {
            newTotal = newTotal - expNeeded;
            RankUp newRank = addUserRank(guildID, userID, 1);
            expNeeded = newRank.expRequired;
            newRanks.add(newRank);
            if (newRank.expRequired == null) //Max rank, no more rank can be obstained
                break;
        }

        String sql = "UPDATE user_profile SET level = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();

        return newRanks;
    }

    /**
     * Add user balance.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void addUserBalance(Long guildID, Long userID, Long value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getBalance() + value;

        String sql = "UPDATE user_profile SET balance = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Add user points.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void addUserPoints(Long guildID, Long userID, Long value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getPoints() + value;

        String sql = "UPDATE user_profile SET points = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Add Werewolf Wins points.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void addWerewolfWins(Long guildID, Long userID, Long value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getWerewolfWins() + value;

        String sql = "UPDATE user_profile SET werewolf_win = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * @param guildID
     * @param userID
     * @throws SQLException
     */

    public void incWerewolfWins(Long guildID, Long userID) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getWerewolfWins();
        newTotal++;

        String sql = "UPDATE user_profile SET werewolf_wins = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * @param guildID
     * @param userID
     * @throws SQLException
     */
    public void incWerewolfGames(Long guildID, Long userID) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getWerewolfGames();
        newTotal++;

        String sql = "UPDATE user_profile SET werewolf_games = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Rem user rank.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void remUserRank(Long guildID, Long userID, Integer value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Integer newTotal = up.getRank() - value;

        String sql = "UPDATE user_profile SET rank = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Rem user level.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void remUserLevel(Long guildID, Long userID, Long value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getLevel() - value;

        String sql = "UPDATE user_profile SET level = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();

    }

    /**
     * Rem user balance.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void remUserBalance(Long guildID, Long userID, Long value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getBalance() - value;

        String sql = "UPDATE user_profile SET balance = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Rem user points.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void remUserPoints(Long guildID, Long userID, Long value) throws SQLException {
        UserProfile up = getUserProfile(guildID, userID);
        Long newTotal = up.getPoints() - value;

        String sql = "UPDATE user_profile SET points = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Sets user rank.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void setUserRank(Long guildID, Long userID, Integer value) throws SQLException {
        getUserProfile(guildID, userID); //This will make sure we have a profile setup, in case of a new users
        Integer newTotal = value;

        String sql = "UPDATE user_profile SET rank = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Sets user level.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void setUserLevel(Long guildID, Long userID, Long value) throws SQLException {
        getUserProfile(guildID, userID);
        Long newTotal = value;

        String sql = "UPDATE user_profile SET level = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Sets user balance.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void setUserBalance(Long guildID, Long userID, Long value) throws SQLException {
        getUserProfile(guildID, userID);
        Long newTotal = value;

        String sql = "UPDATE user_profile SET balance = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Sets user points.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void setUserPoints(Long guildID, Long userID, Long value) throws SQLException {
        getUserProfile(guildID, userID);
        Long newTotal = value;

        String sql = "UPDATE user_profile SET points = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Sets user flipped.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void setUserFlipped(Long guildID, Long userID, Long value) throws SQLException {
        getUserProfile(guildID, userID);
        Long newTotal = value;

        String sql = "UPDATE user_profile SET flipped = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }

    /**
     * Sets user unflipped.
     *
     * @param guildID the guild id
     * @param userID  the user id
     * @param value   the value
     * @throws SQLException the sql exception
     */
    public void setUserUnflipped(Long guildID, Long userID, Long value) throws SQLException {
        getUserProfile(guildID, userID);
        Long newTotal = value;

        String sql = "UPDATE user_profile SET unflipped = ? WHERE guild_id = ? AND user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, newTotal);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();
    }


    /**
     * Gets command level.
     *
     * @param guildID   the guild id
     * @param commandID the command id
     * @return the command level
     */
    public Integer getCommandLevel(Long guildID, Integer commandID) {
        return getPermissionsValues(guildID).getLevel(commandID);
    }

    /**
     * Get if Delete Command is set on/off
     *
     * @param guildID Long guild ID
     * @return if the bot should delete any command requests
     */
    public boolean getDeleteCommand(Long guildID) {
        return getGuildValues(guildID).getDeleteCommand();
    }

    /**
     * Gets prefix.
     *
     * @param guildID the guild id
     * @return the prefix
     */
    public String getPrefix(Long guildID) {
        return getGuildValues(guildID).getPrefix();
    }

    /**
     * Gets greeting.
     *
     * @param guildID the guild id
     * @return the greeting
     */
    public String getGreeting(Long guildID) {
        return getGuildValues(guildID).getGreeting();
    }

    /**
     * Gets greeting channel.
     *
     * @param guildID the guild id
     * @return the greeting channel
     */
    public String getGreetingChannel(Long guildID) {
        return getGuildValues(guildID).getGreetingChannel();
    }

    /**
     * Gets logging channel.
     *
     * @param guildID the guild id
     * @return the logging channel
     */
    public String getLoggingChannel(Long guildID) {
        return getGuildValues(guildID).getLoggingChannel();
    }

    /**
     * Is logging on boolean.
     *
     * @param guildID the guild id
     * @return the boolean
     */
    public Boolean isLoggingOn(Long guildID) {
        return getGuildValues(guildID).isLoggingOn();
    }

    /**
     * Is greet on boolean.
     *
     * @param guildID the guild id
     * @return the boolean
     */
    public Boolean isGreetOn(Long guildID) {
        return getGuildValues(guildID).isGreetOn();
    }

    /**
     * Is werewolf on boolean.
     *
     * @param guildID the guild id
     * @return the boolean
     */
    public Boolean isWerewolfOn(Long guildID) {
        return getGuildValues(guildID).isWWOn();
    }

    public Boolean isEventOn(Long guildID) {
        return getGuildValues(guildID).isEventOn();
    }

    /**
     * Gets game channel.
     *
     * @param guildID the guild id
     * @return the game channel
     */
    public String getGameChannel(Long guildID) {
        return getGuildValues(guildID).getgameChannel();
    }

    public Boolean getEventOn(Long guildID) {
        return getGuildValues(guildID).isEventOn();
    }

    public String getEventChannel(Long guildID) {
        return getGuildValues(guildID).getEventChannel();
    }

    /**
     * Is stored boolean.
     *
     * @param guildID the guild id
     * @return the boolean
     */
    public boolean isStored(Long guildID) {
        return getGuildValues(guildID).isStored;
    }

    /**
     * Gets level name.
     *
     * @param guildID the guild id
     * @param levelID the level id
     * @return the level name
     */
    public String getLevelName(Long guildID, Integer levelID) {
        String thisThing = getPermissionsValues(guildID).getLevelNames(levelID);
        if (thisThing == null) {
            return levelID.toString();
        }

        return thisThing;
    }

    /**
     * Has group boolean.
     *
     * @param guildID the guild id
     * @param groupID the group id
     * @return the boolean
     */
    public boolean hasGroup(Long guildID, Integer groupID) {
        return getSelfRoles(guildID).hasGroup(groupID);
    }

    /**
     * Is role exclusive boolean.
     *
     * @param guildID the guild id
     * @param roleID  the role id
     * @return the boolean
     */
    public Boolean isRoleExclusive(Long guildID, Long roleID) {
        return getSelfRoles(guildID).isRoleExclusive(roleID);
    }

    /**
     * Is group exculsive boolean.
     *
     * @param guildID the guild id
     * @param groupID the group id
     * @return the boolean
     */
    public Boolean isGroupExculsive(Long guildID, Integer groupID) {
        return getSelfRoles(guildID).isGroupExclusive(groupID);
    }

    /**
     * Gets role group.
     *
     * @param guildID the guild id
     * @param roleID  the role id
     * @return the role group
     */
    public Integer getRoleGroup(Long guildID, Long roleID) {
        return getSelfRoles(guildID).getRoleGroup(roleID);
    }

    /**
     * Gets list of roles.
     *
     * @param guildID the guild id
     * @return the list of roles
     */
    public Map<Long, Integer> getListOfRoles(Long guildID) {
        return getSelfRoles(guildID).getListOfRoles();
    }

    /**
     * Gets list of roles by group.
     *
     * @param guildID the guild id
     * @param groupID the group id
     * @return the list of roles by group
     */
    public HashSet<Long> getListOfRolesByGroup(Long guildID, Integer groupID) {
        return getSelfRoles(guildID).getListOfRolesByGroup(groupID);
    }

    /**
     * Gets user level.
     *
     * @param guildID  the guild id
     * @param userID   the user id
     * @param roleList the role list
     * @return the user level
     */
    public Integer getUserLevel(Long guildID, Long userID, List<Role> roleList) {
        Permissions permissions = listGuildPermissions.get(guildID);
        Integer userLevel = null;
        Integer roleLevel = null;

        if (permissions == null) {
            return null;
        }

        userLevel = permissions.getUserLevel(userID);
        if (userLevel != null) return userLevel; //userLevel take prioty over roleLevel
        Integer tempRole;
        for (Role r : roleList) {
            tempRole = permissions.getRoleLevel(r.getIdLong());
            if (tempRole != null) {
                if (roleLevel == null) {
                    roleLevel = tempRole;
                } else if (tempRole < roleLevel) { //See if tempRole is a higher ranked level
                    roleLevel = tempRole;
                }
            }
        }

        return roleLevel;
    }

    /**
     * Is role self assignable boolean.
     *
     * @param guildID the guild id
     * @param roleID  the role id
     * @return the boolean
     */
    public boolean isRoleSelfAssignable(Long guildID, Long roleID) {
        if (listOfSelfRoles.containsKey(guildID)) {
            return listOfSelfRoles.get(guildID).isRoleSelfAssignable(roleID);
        } else {
            return false; //no record for guild so there's no self assignable roles
        }
    }

    /**
     * Sets new permission names.
     *
     * @param guildID the guild id
     * @return the new permission names
     * @throws SQLException the sql exception
     */
    public boolean setNewPermissionNames(Long guildID) throws SQLException {
        Statement stmt;
        Integer count = null;
        stmt = this.conn.createStatement();
        String sql = "SELECT COUNT(*) FROM permission_level WHERE guild_id = " + guildID; //We're expecting no result returned, if there's nothing then we'll set up predefined list
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            count = rs.getInt(1);
        }

        if (count == 0) {
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

    /**
     * Sets game channel.
     *
     * @param guildID     the guild id
     * @param gameChannel the game channel
     * @throws SQLException the sql exception
     */
    public void setGameChannel(Long guildID, String gameChannel) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET game_channel = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (game_channel, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setString(1, gameChannel);

        pstmt.execute();

        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setGameChannel(gameChannel);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setGameChannel(gameChannel);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets werewolf on.
     *
     * @param guildID the guild id
     * @param wwOn    the ww on
     * @throws SQLException the sql exception
     */
    public void setWerewolfOn(Long guildID, Boolean wwOn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET werewolf_on = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (werewolf_on, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setBoolean(1, wwOn);

        pstmt.execute();

        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setWWOn(wwOn);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setWWOn(wwOn);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets command level.
     *
     * @param guildID      the guild id
     * @param commandID    the command id
     * @param commandLevel the command level
     * @throws SQLException the sql exception
     */
    public void setCommandLevel(Long guildID, Integer commandID, Integer commandLevel) throws SQLException {
        //Set the levels of the commands
        String sql = "SELECT COUNT(*) FROM permission_commands WHERE guild_id = ? AND command_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        pstmt.setInt(2, commandID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
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
        if (listGuildPermissions.containsKey(guildID)) {
            //if it does update
            listGuildPermissions.get(guildID).setLevel(commandID, commandLevel);
        } else {
            //if it doesn't add new permission
            Permissions newPermission = new Permissions();
            newPermission.setLevel(commandID, commandLevel);
            listGuildPermissions.put(guildID, newPermission);
        }
    }

    /**
     * Sets logging on.
     *
     * @param loggingOn the logging on
     * @param guildID   the guild id
     * @throws SQLException the sql exception
     */
    public void setLoggingOn(Boolean loggingOn, Long guildID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET logging_on = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (logging_on, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setBoolean(1, loggingOn);

        pstmt.execute();

        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setLoggingOn(loggingOn);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setLoggingOn(loggingOn);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets event channel.
     *
     * @param eventChannel the logging channel
     * @param guildID      the guild id
     * @throws SQLException the sql exception
     */
    public void setEventChannel(Long guildID, String eventChannel) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET event_channel = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (event_channel, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, eventChannel);
        pstmt.setLong(2, guildID);

        pstmt.execute();
        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setEventChannel(eventChannel);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setEventChannel(eventChannel);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets greet on.
     *
     * @param eventOn the greet on
     * @param guildID the guild id
     * @throws SQLException the sql exception
     */
    public void setEventOn(Long guildID, Boolean eventOn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET event_on = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (event_on, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setBoolean(1, eventOn);

        pstmt.execute();

        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setEventOn(eventOn);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setEventOn(eventOn);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets greet on.
     *
     * @param greetOn the greet on
     * @param guildID the guild id
     * @throws SQLException the sql exception
     */
    public void setGreetOn(Boolean greetOn, Long guildID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET greet_on = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (greet_on, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setBoolean(1, greetOn);

        pstmt.execute();

        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setGreetOn(greetOn);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setGreetOn(greetOn);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets prefix.
     *
     * @param prefix  the prefix
     * @param guildID the guild id
     * @throws SQLException the sql exception
     */
    public void setPrefix(String prefix, Long guildID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET prefix = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (prefix, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setString(1, prefix);

        pstmt.execute();

        //Check List
        if (listGuildSettings.containsKey(guildID)) {    //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setPrefix(prefix);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setPrefix(prefix);
            listGuildSettings.put(guildID, guildSetting);
        }
    }


    /**
     * setDeleteCommand set the wether the bot should delete commands or not. (Not all commands will be deleted)
     *
     * @param guildID guild ID
     * @param onOff   on or off
     * @throws SQLException
     */
    public void setDeleteCommand(Long guildID, boolean onOff) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET delete_command = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (delete_command, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setBoolean(1, onOff);

        pstmt.execute();

        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setDeleteCommand(onOff);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setDeleteCommand(onOff);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets greeting.
     *
     * @param greeting the greeting
     * @param guildID  the guild id
     * @throws SQLException the sql exception
     */
    public void setGreeting(String greeting, Long guildID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET greeting_msg = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (greeting_msg, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setString(1, greeting);

        pstmt.execute();

        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setGreeting(greeting);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setGreeting(greeting);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets greeting channel.
     *
     * @param greetingChannel the greeting channel
     * @param guildID         the guild id
     * @throws SQLException the sql exception
     */
    public void setGreetingChannel(String greetingChannel, Long guildID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET greeting_channel = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (greeting_channel, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(2, guildID);
        pstmt.setString(1, greetingChannel);

        pstmt.execute();
        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setGreetingChannel(greetingChannel);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setGreetingChannel(greetingChannel);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets logging channel.
     *
     * @param loggingChannel the logging channel
     * @param guildID        the guild id
     * @throws SQLException the sql exception
     */
    public void setLoggingChannel(String loggingChannel, Long guildID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM variables WHERE guild_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE variables SET logging_channel = ? WHERE guild_id = ?";
        } else {
            sql = "INSERT INTO variables (logging_channel, guild_id) VALUES (?, ?)";
        }

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, loggingChannel);
        pstmt.setLong(2, guildID);

        pstmt.execute();
        //Check List
        if (listGuildSettings.containsKey(guildID)) {
            //if it does, fetch guildSetting from list, update values and store
            listGuildSettings.get(guildID).setLoggingChannel(loggingChannel);
        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            GuildSetting guildSetting = new GuildSetting();
            guildSetting.setLoggingChannel(loggingChannel);
            listGuildSettings.put(guildID, guildSetting);
        }
    }

    /**
     * Sets user level.
     *
     * @param guildID the guild id
     * @param levelID the level id
     * @param userID  the user id
     * @throws SQLException the sql exception
     */
    public void setUserLevel(Long guildID, Integer levelID, Long userID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM permission_group WHERE guild_id = ? AND user_role_id = ? AND is_user = 1";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
            sql = "UPDATE permission_group SET level_id = ? WHERE guild_id = ? AND user_role_id = ? AND is_user = 1";
        } else {
            sql = "INSERT INTO permission_group (level_id, guild_id, user_role_id, is_user) VALUES (?, ?, ?, 1)";
        }
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, levelID);
        pstmt.setLong(2, guildID);
        pstmt.setLong(3, userID);
        pstmt.execute();

        if (listGuildPermissions.containsKey(guildID)) {
            listGuildPermissions.get(guildID).setUserID(levelID, userID);
        } else {
            //if it doesn't add new permission
            Permissions newPermission = new Permissions();
            newPermission.setUserID(levelID, userID);
            listGuildPermissions.put(guildID, newPermission);
        }
    }

    /**
     * Sets role level.
     *
     * @param guildID the guild id
     * @param levelID the level id
     * @param roleID  the role id
     * @throws SQLException the sql exception
     */
    public void setRoleLevel(Long guildID, Integer levelID, Long roleID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM permission_group WHERE guild_id = ? AND user_role_id = ? AND is_user = 0";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
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
        if (listGuildPermissions.containsKey(guildID)) {
            listGuildPermissions.get(guildID).setUserID(levelID, roleID);
        } else {
            //if it doesn't add new permission
            Permissions newPermission = new Permissions();
            newPermission.setUserID(levelID, roleID);
            listGuildPermissions.put(guildID, newPermission);
        }
    }

    /**
     * Sets level name.
     *
     * @param guildID   the guild id
     * @param levelID   the level id
     * @param levelName the level name
     * @throws SQLException the sql exception
     */
    public void setLevelName(Long guildID, Integer levelID, String levelName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM permission_level WHERE guild_id = ? AND level_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, guildID);
        pstmt.setInt(2, levelID);
        ResultSet rs = pstmt.executeQuery();
        Integer rowCount = 0;

        while (rs.next()) rowCount = rs.getInt(1);
        if (rowCount > 0) {
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
        if (listGuildPermissions.containsKey(guildID)) {
            listGuildPermissions.get(guildID).setLevelNames(levelID, levelName);
        } else {
            //if it doesn't add new permission
            Permissions newPermission = new Permissions();
            newPermission.setLevelNames(levelID, levelName);
            listGuildPermissions.put(guildID, newPermission);
        }
    }

    /**
     * Sets new self assignable role.
     *
     * @param guildID the guild id
     * @param roleID  the role id
     * @param groupID the group id
     * @return the new self assignable role
     * @throws SQLException the sql exception
     */
    public boolean setNewSelfAssignableRole(Long guildID, Long roleID, Integer groupID) throws SQLException {
        return setNewSelfAssignableRole(guildID, roleID, groupID, null);
    }

    /**
     * Sets new self assignable role.
     *
     * @param guildID     the guild id
     * @param roleID      the role id
     * @param groupID     the group id
     * @param isExculsive the is exculsive
     * @return the new self assignable role
     * @throws SQLException the sql exception
     */
    public boolean setNewSelfAssignableRole(Long guildID, Long roleID, Integer groupID, Boolean isExculsive) throws SQLException {
        boolean isRoleNew = false;

        //Check List
        if (listOfSelfRoles.containsKey(guildID)) {
            if (isExculsive != null) {
                //We have an existing guild, so let check to make sure we don't assign an already existing role
                isRoleNew = listOfSelfRoles.get(guildID).setNewRole(roleID, groupID, isExculsive);
            } else {
                isExculsive = listOfSelfRoles.get(guildID).isGroupExclusive(groupID);
                if (isExculsive == null) isExculsive = false; //this could happen if it's a new group default to false;
                isRoleNew = listOfSelfRoles.get(guildID).setNewRole(roleID, groupID, isExculsive);
            }

        } else {
            //If it doesn't exist, create new guildSetting for guildID, set value, update
            isExculsive = false; //Default to false
            SelfRoles newSelfRoles = new SelfRoles();
            newSelfRoles.setNewRole(roleID, groupID, isExculsive);
            listOfSelfRoles.put(guildID, newSelfRoles);
            isRoleNew = true;
        }

        if (isRoleNew) {
            String sql = "INSERT INTO self_roles (guild_id, role_id, role_group_id, exclusive_on) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, guildID);
            pstmt.setLong(2, roleID);
            pstmt.setInt(3, groupID);
            pstmt.setBoolean(4, isExculsive);

            pstmt.execute();
        }

        return isRoleNew;
    }

    /**
     * Sets group exculsive.
     *
     * @param guildID     the guild id
     * @param groupID     the group id
     * @param isExculsive the is exculsive
     * @return the group exculsive
     * @throws SQLException the sql exception
     */
    public Boolean setGroupExculsive(Long guildID, Integer groupID, Boolean isExculsive) throws SQLException {
        boolean exculisveChanged = false;
        //Check List
        if (listOfSelfRoles.containsKey(guildID)) {    //We have an existing guild, so let check to make sure we don't assign an already existing role
            exculisveChanged = listOfSelfRoles.get(guildID).setGroupExculsive(groupID, isExculsive);
        } else {
            exculisveChanged = false; //return false because we can only update self-assignable roles, if there's none already set for guild then there's no self assignable roles
        }

        if (exculisveChanged) {
            setExculsiveDatabase(guildID, groupID, isExculsive);
        }

        return exculisveChanged;
    }

    private void setExculsiveDatabase(Long guildID, Integer groupID, Boolean isExculsive) throws SQLException {
        String sql = "UPDATE self_roles SET exclusive_on = ? WHERE guild_id = ? AND role_group_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setBoolean(1, isExculsive);
        pstmt.setLong(2, guildID);
        pstmt.setInt(3, groupID);
        pstmt.execute();
    }

    /**
     * Sets role exculsive.
     *
     * @param guildID     the guild id
     * @param roleID      the role id
     * @param isExculsive the is exculsive
     * @return the role exculsive
     * @throws SQLException the sql exception
     */
    public Boolean setRoleExculsive(Long guildID, Long roleID, Boolean isExculsive) throws SQLException {
        boolean exculisveChanged = false;
        Integer groupID = null;
        //Check List
        if (listOfSelfRoles.containsKey(guildID)) {    //We have an existing guild, so let check to make sure we don't assign an already existing role
            exculisveChanged = listOfSelfRoles.get(guildID).setRoleExculsive(roleID, isExculsive);
            groupID = listOfSelfRoles.get(guildID).getRoleGroup(roleID);
        } else {
            exculisveChanged = false; //return false because we can only update self-assignable roles, if there's none already set for guild then there's no self assignable roles
        }

        if (exculisveChanged) {
            setExculsiveDatabase(guildID, groupID, isExculsive);
        }

        return exculisveChanged;
    }

    /**
     * Remove role boolean.
     *
     * @param guildID the guild id
     * @param roleID  the role id
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public Boolean removeRole(Long guildID, Long roleID) throws SQLException {
        boolean hasChanged = false;
        if (listOfSelfRoles.containsKey(guildID)) {    //We have an existing guild, so let check to make sure we don't assign an already existing role
            hasChanged = listOfSelfRoles.get(guildID).removeRole(roleID);
        } else {
            hasChanged = false; //return false, we don't have anything to remove.
        }

        if (hasChanged) {
            String sql = "DELETE FROM self_roles WHERE guild_id = ? AND role_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, guildID);
            pstmt.setLong(2, roleID);
            pstmt.executeUpdate();
        }

        return hasChanged;
    }

    /**
     * Remove group hash set.
     *
     * @param guildID the guild id
     * @param groupID the group id
     * @return the hash set
     * @throws SQLException the sql exception
     */
    public HashSet<Long> removeGroup(Long guildID, Integer groupID) throws SQLException {
        HashSet<Long> removedRolesList = null;
        if (listOfSelfRoles.containsKey(guildID)) {
            removedRolesList = listOfSelfRoles.get(guildID).removeGroup(groupID);
        } else {
            removedRolesList = null;
        }

        if (removedRolesList != null) {
            if (removedRolesList.size() > 0) {
                String sql = "DELETE FROM self_roles WHERE guild_id = ? AND role_group_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1, guildID);
                pstmt.setLong(2, groupID);
                pstmt.executeUpdate();
            }
        }

        return removedRolesList;
    }

    /**
     * Sets role group.
     *
     * @param guildID  the guild id
     * @param roleID   the role id
     * @param newGroup the new group
     * @return the role group
     * @throws SQLException the sql exception
     */
    public Boolean setRoleGroup(Long guildID, Long roleID, Integer newGroup) throws SQLException {

        boolean hasChanged = false;
        if (listOfSelfRoles.containsKey(guildID)) {
            hasChanged = listOfSelfRoles.get(guildID).setRoleGroup(roleID, newGroup);
        } else {
            hasChanged = false;
        }

        if (hasChanged) {
            String sql = "UPDATE self_roles SET role_group_id = ? WHERE guild_id = ? AND role_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newGroup);
            pstmt.setLong(2, guildID);
            pstmt.setLong(3, roleID);
            pstmt.execute();
        }

        return null;
    }

    /**
     * Sql get theme desc theme.
     *
     * @param id the id
     * @return the theme
     */
    public Theme sqlGetThemeDesc(int id) {

        String sql = "SELECT * FROM theme_detail WHERE id_key = ?";

        ResultSet dbRS;
        Theme themeDes = new Theme();

        try {
            PreparedStatement dbState = conn.prepareStatement(sql);
            dbState.setInt(1, id);
            dbRS = dbState.executeQuery();

            themeDes.setDetails(dbRS);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return themeDes;
    }

    /**
     * Get List of themes
     *
     * @return the list
     */
    public List<ThemeDesc> sqlGetThemeList() {

        ResultSet dbRs = null;

        List<ThemeDesc> themeList = new ArrayList<ThemeDesc>();

        try {
            Statement dbState = conn.createStatement();

            dbRs = dbState.executeQuery("SELECT * FROM theme_detail WHERE loaded = 1 ORDER BY id_key");


            while (dbRs.next()) {
                ThemeDesc newDec = new ThemeDesc(
                    dbRs.getInt("id_key"),
                    dbRs.getString("theme_name"),
                    dbRs.getString("theme_disc"),
                    dbRs.getString("theme_author"),
                    dbRs.getString("theme_created"),
                    dbRs.getString("theme_modifed"),
                    dbRs.getInt("played_count"),
                    dbRs.getString("avatar")
                );
                themeList.add(newDec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themeList;
    }

    /**
     * Sql theme check boolean.
     *
     * @param id the id
     * @return boolean
     */
    public boolean sqlThemeCheck(int id) {
        String sql = "SELECT count(*) AS themeCount FROM theme_detail WHERE id_key = ? AND loaded = 1";
        boolean exists = false;
        int themeCount = 0;

        try {
            PreparedStatement dbState = conn.prepareStatement(sql);
            ResultSet dbRs = null;

            dbState.setInt(1, id);
            dbRs = dbState.executeQuery();

            while (dbRs.next()) {
                themeCount = dbRs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        exists = themeCount != 0;

        return exists;
    }

    /**
     * Sql inc theme count.
     *
     * @param id the id
     */
    public void sqlIncThemeCount(int id) {
        Statement dbState = null;

        try {
            //db_state = db_cn.createStatement();
            dbState.executeUpdate("UPDATE theme_detail SET played_count=played_count+1 WHERE id_key = " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbState != null) {
                try {
                    dbState.close();
                } catch (SQLException sqlEx) {
                }

                dbState = null;
            }
        }
    }

    /**
     * Get Details from Theme
     *
     * @param id the id
     * @return the theme
     */
    public Theme sqlGetTheme(int id) {
        String sqlDetail = "SELECT * FROM theme_detail WHERE id_key = ?";
        String sqlThemes = "SELECT * FROM theme WHERE theme_id = ?";
        Theme themeObj = new Theme();

        try {
            PreparedStatement dbState = conn.prepareStatement(sqlDetail);
            dbState.setInt(1, id);
            ResultSet dbRs = dbState.executeQuery();
            themeObj.setDetails(dbRs);

            dbState = conn.prepareStatement(sqlThemes);
            dbState.setInt(1, id);
            ResultSet dbRs2 = dbState.executeQuery();
            themeObj.setText(dbRs2);

            return themeObj;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
