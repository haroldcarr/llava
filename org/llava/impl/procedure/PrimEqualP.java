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
 * Last Modified : 2004 Dec 07 (Tue) 19:01:19 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;

public class PrimEqualP
    extends
	PrimitiveProcedure
{
    public PrimEqualP ()
    {
    }

    public PrimEqualP newPrimEqualP ()
    {
	return new PrimEqualP();
    }

    public Object apply (Pair args, Engine engine)
    {
	// REVISIT
	if (args == null || args.length() != 2) {
	    throw F.newLlavaException(name + " currently only handles 2 args");
	}
	Object first = args.first();
	Object second = args.second();
	if (first == second) {
	    return F.newBoolean(true);
	}
	return F.newBoolean(first == null ? false : first.equals(second));
    }
}

// End of file.
