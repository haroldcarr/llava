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
 * Last Modified : 2004 Sep 03 (Fri) 15:32:57 by Harold Carr.
 */

package org.llava.impl.compiler;

import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;

/**
 * Compiletime lexical environment.
 */

public abstract class EnvironmentLexical
{
    public class LocalVariable
    {
	private int level;
	private int slot;
	LocalVariable (int level, int slot) {
	    this.level = level;
	    this.slot  = slot;
	}
	public int getLevel() { return level; }
	public int getSlot()  { return slot; }
    }

    abstract public EnvironmentLexical newEnvironmentLexical (Pair names);

    abstract public EnvironmentLexical extend (EnvironmentLexical names);

    abstract public EnvironmentLexical extend (Pair names);

    abstract public LocalVariable determineIfLocalVariable (Symbol name);

}

// End of file.

