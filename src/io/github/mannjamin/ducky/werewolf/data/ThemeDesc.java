/**
 *
 */
package io.github.mannjamin.ducky.werewolf.data;

/**
 * @author AdTheRat
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Theme.
 */
public class ThemeDesc {

    /**
     * The Theme name id.
     */
//Varibles
    //Theme Details
    private int themeNameID = -1; //-1 Unknown ID, as the ID from DB starts at 0, using -1 to refer to theme not collected from DB
    /**
     * The Theme played count.
     */
    private int themePlayedCount = 0;

    /**
     * The Theme name.
     */
    private String themeName = "";
    /**
     * The Theme desc.
     */
    private String themeDesc = "";
    /**
     * The Theme author.
     */
    private String themeAuthor = "";

    /**
     * The Theme created.
     */
    private Date themeCreated = null;
    /**
     * The Theme modified.
     */
    private Date themeModified = null;

    /**
     *
     */
    private String themeAvatar = null;


    public ThemeDesc(Integer id, String name, String desc, String author, String created, String modified, Integer playCount, String avatar) {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        themeNameID = id;
        themeName = name;
        themeDesc = desc;
        themeAuthor = author;
        themePlayedCount = playCount;
        themeAvatar = avatar;

        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            themeCreated = df.parse(created);
            themeModified = df.parse(modified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public String getAvatar() {
        return this.themeAvatar;
    }
}
