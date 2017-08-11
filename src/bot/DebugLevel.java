package bot;

/**
 * The enum Debug level.
 */
public enum DebugLevel {
    /**
     * This to make it easier to read what level debug level is required.
     * Again, Abby is terrible at comments/documentating	*
     */
    OFF(0), /**
     * Low debug level.
     */
    LOW(1), /**
     * Med debug level.
     */
    MED(2), /**
     * High debug level.
     */
    HIGH(3), /**
     * Verbose debug level.
     */
    VERBOSE(4);
	private int level;
	
	DebugLevel(int level)
	{
		this.level = level;
	}


    /**
     * Level int.
     *
     * @return the int
     */
    public int level()
	{
		return this.level;
	}

    /**
     * Gets debug level.
     *
     * @param level the level
     * @return the debug level
     */
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