/**
 *
 */
package io.github.mannjamin.ducky.werewolf.data;

/**
 * @author AdTheRat
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Theme.
 */
public class Theme {

    /**
     * The Theme name id.
     */
//Varibles
    //Theme Details
    int themeNameID = -1; //-1 Unknown ID, as the ID from DB starts at 0, using -1 to refer to theme not collected from DB
    /**
     * The Theme played count.
     */
    int themePlayedCount = 0;

    /**
     * The Theme name.
     */
    String themeName = "";
    /**
     * The Theme desc.
     */
    String themeDesc = "";
    /**
     * The Theme author.
     */
    String themeAuthor = "";

    /**
     * The Theme created.
     */
    java.util.Date themeCreated = null;
    /**
     * The Theme modified.
     */
    java.util.Date themeModified = null;

    /**
     *
     */
    String themeAvatar = null;

    /**
     * The Theme db.
     */
//Theme
    HashMap<String, List<String>> themeDB = new HashMap<String, List<String>>();
    /**
     * The Theme default.
     */
    HashMap<String, String> themeDefault = new HashMap<String, String>();


    /**
     * Instantiates a new Theme.
     */
    public Theme() {
        //Populate the Defaults
        themeDefault.put("ONEWOLF", "Bad Person");
        themeDefault.put("MANY_WOLVES", "Bad People");
        themeDefault.put("ROLE_WOLF", "Enemy");
        themeDefault.put("ROLE_SEER", "Seer");
        themeDefault.put("ROLE_VILL", "Good Person");
        themeDefault.put("ROLE_MASON", "Siblings");
        themeDefault.put("WOLF_INSTRUCTIONS", "To Kill someone, type /msg BOTNAME kill <playername>");
        themeDefault.put("SEER_INSTRUCTIONS", "To find out about someone's role type /msg BOTNAME see <playername>");
        themeDefault.put("WOLVES_INSTRUCTIONS", "You need to confer with PLAYER1 and come to a disicion on who to kill");
        themeDefault.put("VILL_DESCRIPTION", "You are a peaceful person.");
        themeDefault.put("WOLF_DESCRIPTION", "You are a very bad person and want to kill everyone");
        themeDefault.put("SEER_DESCRIPTION", "You are gifted with the ablilty to see how evil a person is");
        themeDefault.put("MASON_DESCRIPTION", "You are part of a family, your siblings are PLAYER2");
        themeDefault.put("WOLVES_DESCRIPTION", "You are part of a group of evil people trying to kill everyone else off, you made a pact with the other bad person.");
        themeDefault.put("OTHER_WOLF", "Your other bad person is PLAYER1");
        themeDefault.put("OTHER_MASONS", "Your other siblings are PLAYER1");
        themeDefault.put("NO_LYNCH", "There has been no votes. There for no Lynch");
        themeDefault.put("WOLF_LYNCH", "This bad person has found and killed");
        themeDefault.put("SEER_LYNCH", "Sadly the peaceful person gifted with the ablilty to tell how evil a person is has been killed");
        themeDefault.put("VILL_LYNCH", "A peaceful person has been killed. The Angels cry");
        themeDefault.put("START_GAME", "PLAYER1 is Starting a game. There are NUMBER seconds to join. Use !join to join the game.");
        themeDefault.put("START_GAME_NOTICE", "PLAYER1 is starting a game");
        themeDefault.put("ADDED", "You have been added");
        themeDefault.put("GAME_STARTED", "There's a game in sign up. Type '!join' to join the game");
        themeDefault.put("GAME_PLAYING", "There is a game playing. Once it's over, you be able to talk again. Please wait");
        themeDefault.put("NOT_ENOUGH", "There's not enough players to start a game");
        themeDefault.put("TWO_WOLVES", "There are Two bad people");
        themeDefault.put("JOIN", "PLAYER1 has joined the game");
        themeDefault.put("FLEE", "PLAYER1 has left the game");
        themeDefault.put("FLEE_ROLE", "PLAYER1 has left them game, they were a ROLE");
        themeDefault.put("FIRST_NIGHT", "The first night. It's only NUMBER seconds long");
        themeDefault.put("NIGHT_TIME", "Night time falls, everyone goes to sleep for NUMBER seconds");
        themeDefault.put("DAY_TIME", "Dawn breaks, everyone has NUMBER seconds to discuss who is an evil person");
        themeDefault.put("VOTE_TIME", "The Sun is now on the Horizion, there's only NUMBER seconds left, time to vote. To vote for someone use '!vote <playername>'");
        themeDefault.put("WOLF_CHOICE", "You have chosen to kill PLAYER1");
        themeDefault.put("WOLVES_CHOICE", "You have chosen to kill PLAYER1, let see who the other bad person has voted for...");
        themeDefault.put("WOLVES_OTHER_CHOICE", "PLAYER1 has chosen to kill PLAYER2");
        themeDefault.put("WOLF_TARGET_DEAD", "PLAYER1 is already dead");
        themeDefault.put("ROLE_IS_KILLED", "PLAYER1 has been killed, they were a ROLE");
        themeDefault.put("SEER_KILL", "The seer has been killed");
        themeDefault.put("VILL_KILL", "A Peaceful person has been killed");
        themeDefault.put("NO_KILL", "The WOLFPURAL couldn't bring themselves to kill anyone.");
        themeDefault.put("NOT_VOTED", "PLAYER1 has been killed for not voting for so many rounds");
        themeDefault.put("NOT_VOTED_NOTICE", "You have been removed for not voting");
        themeDefault.put("VOTED_FOR", "PLAYER1 has voted for PLAYER2");
        themeDefault.put("CHANGE_VOTE", "PLAYER1 has changed their vote to PLAYER2");
        themeDefault.put("VOTE_TARGET_DEAD", "PLAYER1 is already dead");
        themeDefault.put("WOLF_WIN", "The bad person wins");
        themeDefault.put("WOLVES_WIN", "The bad people has won");
        themeDefault.put("VILL_WIN", "The Peaceful people wins the day, Angels rejoice");
        themeDefault.put("CONGR_VILL", "Well done! Peaceful people");
        themeDefault.put("CONGR_WOLF", "Well done, Bad person");
        themeDefault.put("CONGR_WOLVES", "Well done. bad peoples");
        themeDefault.put("SEER_DEAD", "You can't use your gift, as you're dead");
        themeDefault.put("WOLF_DEAD", "You can't kill if you're dead");
        themeDefault.put("NOT_WOLF", "You aren't a bad person");
        themeDefault.put("NOT_SEER", "You don't have the gifts");
        themeDefault.put("WILL_SEE", "You will see PLAYER1 tomorrow");
        themeDefault.put("SEER_SEE", "PLAYER1 is a ROLE");
        themeDefault.put("SEER_GOT_KILLED", "Sadly the evil people have got you");
        themeDefault.put("SEER_TARGET_KILLED", "Sadly PLAYER1 has been killed, thus confirming their role as ROLE");
        themeDefault.put("SEER_TARGET_DEAD", "PLAYER1 is already dead");
        themeDefault.put("TALLY", "Tallying up vote");
        themeDefault.put("TIE", "There's a NUMBER way tie");
        themeDefault.put("DYING_BREATH", "PLAYER1 has a dying breath");
        themeDefault.put("TIE_GAME", "The game is a tie");
        themeDefault.put("FELLOW_WOLF", "You can't break your pact with PLAYER1");
        themeDefault.put("KILL_SELF", "You can't kill yourself");
        themeDefault.put("SEE_SELF", "You already know yourself. You just look in the mirrow");
        themeDefault.put("YOURE_DEAD", "You are dead, you can't vote");
        themeDefault.put("YOUVE_FLED", "You have fled so you can't vote");
        themeDefault.put("YOUR_ROLE", "Your Role is ROLE");
        themeDefault.put("TEN_WARNING_JOIN", "You have 10 seconds to join");
        themeDefault.put("TEN_WARNING_VOTE", "You have 10 seconds to finish voting");
        themeDefault.put("TEN_WARNING_WOLF", "You have 10 seconds to choose someone to kill");
        themeDefault.put("STOP_GAME", "Game Stopped");
        themeDefault.put("VOTED_FOR_NOLYNCH", "PLAYER1 has voted for No Lynching");
        themeDefault.put("CHANGE_VOTE_NOLYNCH", "PLAYER1 has changed their vote for No Lynching");
        themeDefault.put("VOTED_NO_LYNCH", "Everyone goes away for the night, having voted not to lynch anyone");
        themeDefault.put("ROLE_IS_LYNCHED", "PLAYER1 has been killed, they were a ROLE");
        themeDefault.put("STATE_ALIVE", "Still Alive");
        themeDefault.put("STATE_KILLED", "Has been killed");
        themeDefault.put("STATE_FLED", "Ran away");
        themeDefault.put("AVATAR_GAME", null);
        themeDefault.put("AVATAR_NOTICE", null);
        themeDefault.put("AVATAR_NARR", null);
    }

    /**
     * Gets played count.
     *
     * @return played count
     */
    public Integer getPlayedCount() {
        return themePlayedCount;
    }

    /**
     * Gets name.
     *
     * @return name
     */
    public String getName() {
        return themeName;
    }

    /**
     * Gets author.
     *
     * @return author
     */
    public String getAuthor() {
        return themeAuthor;
    }

    /**
     * Gets desc.
     *
     * @return desc
     */
    public String getDesc() {
        return themeDesc;
    }

    /**
     * Gets date created.
     *
     * @return date created
     */
    public Date getDateCreated() {
        return themeCreated;
    }

    /**
     * Gets date modified.
     *
     * @return date modified
     */
    public Date getDateModified() {
        return themeModified;
    }

    /**
     * Gets theme id.
     *
     * @return the theme id
     */
    public int getThemeID() {
        return themeNameID;
    }

    /**
     * Gets text.
     *
     * @param themeID the theme id
     * @return the text
     */
    public String getText(String themeID) {
        String textReturn = "";
        if (themeDB.containsKey(themeID)) {
            Random rnd = new Random();
            List<String> tempList = themeDB.get(themeID);
            textReturn = tempList.get(rnd.nextInt(tempList.size()));

        } else if (themeDefault.containsKey(themeID)) {
            textReturn = themeDefault.get(themeID) + " (Default " + themeID + " )";
            if (themeID.equals("AVATAR")) {
                textReturn = null;
            }
        } else {
            textReturn = themeID + " is an Unknown ThemeID";
        }

        return textReturn;
    }

    /**
     * Sets details.
     *
     * @param rs the rs
     */
    public void setDetails(ResultSet rs) {
        String dateCreated = "";
        String dateModified = "";
        String dateFormat = "yyyy-MM-dd HH:mm:ss";

        try {
            while (rs.next()) {
                themeNameID = rs.getInt("id_key");
                themeName = rs.getString("theme_name");
                themeDesc = rs.getString("theme_disc");
                themeAuthor = rs.getString("theme_author");
                dateCreated = rs.getString("theme_created");
                dateModified = rs.getString("theme_modifed");
                themePlayedCount = rs.getInt("played_count");
                themeAvatar = rs.getString("avatar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            themeCreated = df.parse(dateCreated);
            themeModified = df.parse(dateModified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets text.
     *
     * @param rs the rs
     */
    public void setText(ResultSet rs) {
        try {
            while (rs.next()) {
                if (themeDB.containsKey(rs.getString(3))) {
                    themeDB.get(rs.getString(3)).add(rs.getString(4));
                } else {
                    List<String> tempList = new ArrayList<String>();
                    tempList.add(rs.getString(4));
                    themeDB.put(rs.getString(3), tempList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clear.
     */
    public void clear() {
        themeNameID = -1;
        themePlayedCount = 0;

        themeName = "";
        themeDesc = "";
        themeAuthor = "";

        themeCreated = null;
        themeModified = null;

        themeDB.clear();
    }

    public String getAvatar() {
        return this.themeAvatar;
    }
}
