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
 * Last Modified : 2004 Sep 03 (Fri) 15:34:52 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java.opt;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.generic.GenericProcedureImpl;

public class PrimNot
    extends
	GenericProcedureImpl
{
    public PrimNot ()
    {
    }

    public PrimNot newPrimNot ()
    {
	return new PrimNot();
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
