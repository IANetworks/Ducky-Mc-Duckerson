package bot.database.manager.data;

import java.util.HashMap;
import java.util.Map;

public class Permissions {
	private Map<Integer, Integer> listOfLevels = new HashMap<Integer, Integer>();
	
	public void setLevel(Integer cmdID, Integer cmdLevel)
	{
		listOfLevels.put(cmdID, cmdLevel);
	}
	
	public Integer getLevel(Integer cmdID)
	{
		return listOfLevels.get(cmdID);
	}
	
}
