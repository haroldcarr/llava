/**
 * Created       : 1999 Dec 28 (Tue) 03:08:36 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:05:06 by Harold Carr.
 */

package lava.lang.types;

import lava.lang.types.Pair;
import lavaProfile.runtime.Engine; // REVISIT

public interface Procedure
{
    public Object apply (Pair args, Engine engine);

    public String getName ();

    public String setName (String name);
}

// End of file.
