/**
 * Created       : 1999 Dec 23 (Thu) 03:37:52 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:06:29 by Harold Carr.
 */

package lava.compiler;

import lava.runtime.LavaRuntime; // REVISIT

import lavaProfile.compiler.EnvironmentLexical; // REVISIT
import lavaProfile.runtime.code.Code; // REVISIT

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
