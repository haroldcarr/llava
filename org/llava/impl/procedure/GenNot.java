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
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2005 May 19 (Thu) 06:21:01 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.Symbol;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.GenericProcedureImpl;

public class GenNot
    extends
	GenericProcedureImpl
{
    public GenNot ()
    {
    }

    public GenNot newGenNot ()
    {
	return new GenNot();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	// N.B.: This is Scheme not - anything not false is true.
	Object first = args.first();
	if (first instanceof Boolean &&
	    ((Boolean)first).booleanValue() == false) {
	    return F.newBoolean(true);
	}
	return F.newBoolean(false);
    }
}

// End of file.
