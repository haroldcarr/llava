/**
 * Created       : 2000 Jan 19 (Wed) 17:30:46 by Harold Carr.
 * Last Modified : 2000 Jan 26 (Wed) 17:05:21 by Harold Carr.
 */

package lava;

public class LavaVersionImpl
    implements
	LavaVersion
{
    public LavaVersionImpl ()
    {
    }

    public LavaVersion newLavaVersion ()
    {
	return new LavaVersionImpl();
    }

    public String getName    ()  { return "Lava (alpha)"; }

    public int    getMajor   ()  { return 0; }
    
    public int    getMinor   ()  { return 1; }

    public String getDate    ()  { return "January 26, 2000"; }

    public String toString ()
    {
	return 
	    getName() + 
	    " version " + 
	    getMajor() + "." + getMinor() +
	    ", " +
	    getDate();
    }
}

// End of file.
