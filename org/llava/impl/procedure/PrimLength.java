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
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:04 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.impl.util.List;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.exceptions.UndefinedIdException;
import org.llava.impl.runtime.procedure.generic.GenericProcedureImpl;

public class PrimLength
    extends
	GenericProcedureImpl
{
    public PrimLength ()
    {
    }

    public PrimLength newPrimLength ()
    {
	return new PrimLength();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    checkNumArgs(args, 1);
	    if (args.car() != null) {
		throw F.newLlavaException("length: unexpected argument: " + args.car().toString());
	    }
	    // Length of null is 0;
	    return F.newInteger(0);
	}
    }
}

// End of file.
