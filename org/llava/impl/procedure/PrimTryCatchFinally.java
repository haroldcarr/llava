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
 * Created       : 2000 Jan 16 (Sun) 19:38:28 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:34:23 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java;

import org.llava.lang.types.Pair;
import org.llava.lang.types.Procedure;
import org.llava.impl.util.List;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimTryCatchFinally
    extends
	PrimitiveProcedure
{
    public PrimTryCatchFinally ()
    {
    }

    public PrimTryCatchFinally newPrimTryCatchFinally ()
    {
	return new PrimTryCatchFinally();
    }

    public Object apply (Pair args, Engine engine)
    {
	Procedure tryProcedure = null;
	Procedure catchProcedure = null;
	Procedure finallyProcedure = null;

	switch (List.length(args)) {
	case 3 : finallyProcedure = (Procedure) args.third();
	case 2 : catchProcedure   = (Procedure) args.second();
	case 1 : tryProcedure     = (Procedure) args.first();
	    break;
	default : return null;
	}
	try {
	    return engine.apply(tryProcedure, null);
	} catch (Throwable t) {
	    return engine.apply(catchProcedure, List.list(t));
	} finally {
	    if (finallyProcedure != null) {
		engine.apply(finallyProcedure, null);
	    }
	}
    }
}

// End of file.
