/**
 * Created       : 1999 Dec 25 (Sat) 19:08:29 by Harold Carr.
 * Last Modified : 2000 Jan 10 (Mon) 18:41:12 by Harold Carr.
 */

package libLava.rt;

import lava.lang.types.Symbol;

public interface EnvironmentTopLevel
{
    public EnvironmentTopLevel newEnvironmentTopLevel ();

    public Object get (Symbol symbol);

    public Object getNoError (Symbol symbol);

    public Object set (Symbol symbol, Object v);

    public UndefinedIdHandler getUndefinedIdHandler ();

    public UndefinedIdHandler setUndefinedIdHandler (UndefinedIdHandler handler);
}

// End of file.

