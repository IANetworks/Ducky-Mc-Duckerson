package bot;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.User;
import werewolf.Werewolf;

public class SharedContainer {
	public DatabaseManager dbMan;
	public String botAdmin;
	public User botOwner;
	public Werewolf ww;

}
