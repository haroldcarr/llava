/**
 * Created       : 1999 Dec 30 (Thu) 06:31:28 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:05:36 by Harold Carr.
 */

package lava.runtime;

import lava.Repl;

public interface EnvTopLevelInit
{
    public EnvTopLevelInit newEnvTopLevelInit (Repl repl);

    public void init ();

    public void loadDerived ();
}

// End of file.
