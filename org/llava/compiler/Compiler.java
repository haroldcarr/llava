/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/

/**
 * Created       : 1999 Dec 23 (Thu) 03:37:52 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 18:51:01 by Harold Carr.
 */

package org.llava.compiler;

import org.llava.compiler.EnvironmentLexical; // REVISIT
import org.llava.runtime.LlavaRuntime; // REVISIT

import org.llava.impl.runtime.Code; // REVISIT

public interface Compiler
{
    public Compiler newCompiler ();

    public Object compile (Object x, LlavaRuntime runtime);

    /**
     * This one so the compiler can call itself back non locally.
     */
    // REVISIT - code and EnvironmentLexical
    public Code compile (Object x, EnvironmentLexical env, LlavaRuntime runtime);

}

// End of file.
