/**
 * Created       : 2000 Jan 11 (Tue) 22:35:58 by Harold Carr.
 * Last Modified : 2000 Jan 11 (Tue) 23:01:19 by Harold Carr.
 */

package lava.io;

import lava.io.LavaEOF;

public class LavaEOFImpl
    implements
	LavaEOF
{
    private LavaEOF singleton = null;

    public LavaEOFImpl ()
    {
	singleton = this;
    }

    public LavaEOF newLavaEOF ()
    {
	if (singleton == null) {
	    singleton = new LavaEOFImpl();
	}
	return singleton;
    }

    public String toString ()
    {
	return "#!EOF";
    }
}

// End of file.
