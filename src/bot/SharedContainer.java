package bot;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.User;
import werewolf.Werewolf;

/**
 * The type Shared container.
 */
public class SharedContainer {
    /**
     * The Db man.
     */
    public DatabaseManager dbMan;
    /**
     * The Bot admin.
     */
    public String botAdmin;
    /**
     * The Bot owner.
     */
    public User botOwner;
    /**
     * The Ww.
     */
    public Werewolf ww;

}
