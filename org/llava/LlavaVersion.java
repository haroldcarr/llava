/**
 * Created       : 2000 Jan 19 (Wed) 17:30:46 by Harold Carr.
 * Last Modified : 2000 Jan 19 (Wed) 17:54:18 by Harold Carr.
 */

package lava;

public interface LavaVersion
{
    public LavaVersion newLavaVersion ();

    public String getName ();

    public int    getMajor ();
    
    public int    getMinor ();

    public String getDate ();
}

// End of file.
