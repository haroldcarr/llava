/**
 * Created       : 2000 Jan 16 (Sun) 19:38:28 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:01 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
