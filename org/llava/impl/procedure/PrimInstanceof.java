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
 * Created       : 2000 Jan 18 (Tue) 04:24:10 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:34:50 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java.opt;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimInstanceof
    extends
	PrimitiveProcedure
{
    public PrimInstanceof ()
    {
    }

    public PrimInstanceof newPrimInstanceof ()
    {
	return new PrimInstanceof();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 2);
	try {
	    return F.newBoolean(Class.forName(args.second().toString())
				.isInstance(args.first()));
	} catch (Throwable e) {
	    throw F.newLlavaException(e);
	}
    }
}

// End of file.
