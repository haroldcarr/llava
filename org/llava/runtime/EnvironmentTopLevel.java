/**
 * Created       : 1999 Dec 25 (Sat) 19:08:29 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:05:46 by Harold Carr.
 */

package lava.runtime;

import lava.Repl;
import lava.lang.types.Symbol;

public interface EnvironmentTopLevel
{
    public EnvironmentTopLevel newEnvironmentTopLevel ();

    public Object get (Symbol identifier);

    public Object getNoError (Symbol identifier);

    public Object set (Symbol identifier, Object v);

    public UndefinedIdHandler getUndefinedIdHandler ();

    public UndefinedIdHandler setUndefinedIdHandler (UndefinedIdHandler handler);

    /**
     * This is used to support interactive features like "import", "load".
     */
    public Repl setRepl (Repl repl);
}

// End of file.

