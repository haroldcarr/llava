/**
 * Created       : 1999 Dec 30 (Thu) 06:31:28 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 16:52:12 by Harold Carr.
 */

package libLava.rt;

import lava.Repl;

public interface EnvTopLevelInit
{
    public EnvTopLevelInit newEnvTopLevelInit ();

    public void init (Repl repl);

    public void loadDerived (Repl repl);
}

// End of file.
