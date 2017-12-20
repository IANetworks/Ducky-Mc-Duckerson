package io.github.mannjamin.ducky;

import io.github.mannjamin.ducky.database.manager.DatabaseManager;
import io.github.mannjamin.ducky.items.ItemDatabase;
import net.dv8tion.jda.core.entities.User;
import io.github.mannjamin.ducky.werewolf.Werewolf;

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
     * The Werewolf object.
     */
    public Werewolf ww;

    /**
     * The Item Database
     */
    public ItemDatabase itemDB;
}
