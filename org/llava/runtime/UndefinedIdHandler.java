/**
 * Created       : 1999 Dec 28 (Tue) 00:27:53 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:07:21 by Harold Carr.
 */

package lava.runtime;

import lava.lang.types.Symbol;

public interface UndefinedIdHandler
{
    public UndefinedIdHandler newUndefinedIdHandler ();

    public Object handle (EnvironmentTopLevel env, Symbol identifier);
}

// End of file.
