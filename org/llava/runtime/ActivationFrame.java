/**
 * Created       : 1999 Dec 21 (Tue) 00:04:24 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:39:13 by Harold Carr.
 */

package libLava.r1.env;

import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import libLava.rt.EnvironmentTopLevel;

/**
 * Runtime lexical environment.
 */

public interface ActivationFrame
{
    public ActivationFrame newActivationFrame (EnvironmentTopLevel top);

    public ActivationFrame newActivationFrame (int size);

    public ActivationFrame extend (ActivationFrame frame);

    public ActivationFrame extend (Pair args);

    public Object get (Symbol symbol);
    public Object get (int slot);
    public Object get (int level, int slot);

    public Object set (Symbol symbol, Object value);
    public Object set (int slot, Object v);
    public Object set (int level, int slot, Object v);
}

// End of file.

