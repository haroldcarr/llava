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
 * Created       : 1999 Dec 21 (Tue) 00:04:24 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 18:53:32 by Harold Carr.
 */

package org.llava.runtime;

import org.llava.Pair;
import org.llava.Symbol;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Namespace;

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

