/**
 * Created       : 2000 Jan 16 (Sun) 19:38:28 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:40:25 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

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
