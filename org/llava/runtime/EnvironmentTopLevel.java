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
 * Created       : 1999 Dec 25 (Sat) 19:08:29 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 18:54:13 by Harold Carr.
 */

package org.llava.runtime;

import org.llava.Repl;
import org.llava.Symbol;

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

