/**
 * Created       : 1999 Dec 23 (Thu) 03:37:52 by Harold Carr.
 * Last Modified : 2000 Feb 15 (Tue) 21:56:27 by Harold Carr.
 */

package libLava.co;

import libLava.c1.EnvironmentLexical; // REVISIT
import libLava.rt.LavaRuntime;
import libLava.r1.code.Code; // REVISIT

public interface Compiler
{
    public Compiler newCompiler ();

    public Object compile (Object x, LavaRuntime runtime);

    /**
     * This one so the compiler can call itself back non locally.
     */
    // REVISIT - code and EnvironmentLexical
    public Code compile (Object x, EnvironmentLexical env, LavaRuntime runtime);

}

// End of file.
