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
 * Last Modified : 2004 Sep 03 (Fri) 15:34:28 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class Prim_Divide
    extends
	PrimitiveProcedure
{
    public Prim_Divide ()
    {
    }

    public Prim_Divide newPrim_Divide ()
    {
	return new Prim_Divide();
    }

    public Object apply (Pair args, Engine engine)
    {
	if (args.rest() == null) {
	    return Prim_NumHelp.doOp(Prim_NumHelp.DIVIDE, 1, args);
	}
	return Prim_NumHelp.doOp(Prim_NumHelp.DIVIDE,
				args.first(),
				(Pair)args.rest());
    }
}

// End of file.
