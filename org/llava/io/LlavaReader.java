/**
 * Created       : 1999 Dec 20 (Mon) 02:27:20 by Harold Carr.
 * Last Modified : 2000 Jan 15 (Sat) 18:41:30 by Harold Carr.
 */

package lava.io;

import java.io.IOException;
import java.io.Reader;

public interface LavaReader
{
    /**
     *
     */

    public LavaReader newLavaReader ();

    /**
     *
     */

    public LavaReader newLavaReader (Reader in);

    /**
     *
     */

    public Object read ();

    /**
     *
     */

    public Object read (Reader in);

    /**
     *
     */

    public Object read (String in);

}

// End of file.

