/**
 * Created       : 1999 Dec 21 (Tue) 00:04:24 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:42:51 by Harold Carr.
 */

package lavaProfile.runtime.env;

import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lava.runtime.EnvironmentTopLevel;
import lavaProfile.runtime.env.Namespace;

/**
 * Runtime lexical environment.
 */

public interface ActivationFrame
{
    public ActivationFrame newActivationFrame (EnvironmentTopLevel top);

    public ActivationFrame newActivationFrame (int size);

    public ActivationFrame extend (ActivationFrame frame);

    public ActivationFrame extend (Pair args);
    public ActivationFrame extend (Pair args, Namespace namespace);

    public Object get (Symbol symbol);
    public Object get (int slot);
    public Object get (int level, int slot);

    public Object set (Symbol symbol, Object value);
    public Object set (int slot, Object v);
    public Object set (int level, int slot, Object v);

    public EnvironmentTopLevel getEnvironmentTopLevel ();
    public Namespace getNamespace ();
}

// End of file.

