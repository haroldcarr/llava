/**
 * Created       : 2000 Jan 16 (Sun) 19:38:28 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:54:45 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimSynchronized
    extends
	PrimitiveProcedure
{
    public PrimSynchronized ()
    {
    }

    public PrimSynchronized newPrimSynchronized ()
    {
	return new PrimSynchronized();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 2);
	Object    lock  = args.first();
	Procedure thunk = (Procedure) args.second();
	synchronized (lock) {
	    return engine.apply(thunk, null);
	}
    }
}

// End of file.
