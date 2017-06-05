package bot;

public enum DebugLevel {
	/**
	*	This to make it easier to read what level debug level is required. 
	*	Again, Abby is terrible at comments/documentating	*
	*
	*
	*/
	OFF(0), LOW(1), MED(2), HIGH(3), VERBOSE(4);
	private int level;
	
	private DebugLevel(int level)
	{
		this.level = level;
	}
	
		
	public int level()
	{
		return this.level;
	}
	
	//Abby as forgotten how to do this correctly and do this terrible thing. I hope someone will correct this
	public static DebugLevel getDebugLevel(int level) {
		switch(level) 
		{
			case 0:
				return OFF;
			case 1:
				return LOW;
			case 2:
				return MED;
			case 3:
				return HIGH;
			case 4:
				return VERBOSE;
			default:
				return OFF;
		}
			
		
	}
}