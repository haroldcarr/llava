/**
 * Created       : 1999 Dec 28 (Tue) 00:27:53 by Harold Carr.
 * Last Modified : 2000 Jan 10 (Mon) 18:43:13 by Harold Carr.
 */

package libLava.rt;

import lava.lang.types.Symbol;

public interface UndefinedIdHandler
{
    public UndefinedIdHandler newUndefinedIdHandler ();

    public Object handle (EnvironmentTopLevel env, Symbol identifier);
}

// End of file.
