/**
 * Created       : 2000 Jan 19 (Wed) 17:30:46 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:15:16 by Harold Carr.
 */

package lavaProfile;

import lava.LavaVersion;

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
    
    public int    getMinor   ()  { return 2; }

    public String getDate    ()  { return "March 26, 2001"; }

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
