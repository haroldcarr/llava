/**
 * Created       : 2000 Jan 07 (Fri) 22:40:51 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:26:54 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.Lambda;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimNewThread
    extends
	PrimitiveProcedure
{
    public PrimNewThread ()
    {
    }

    public PrimNewThread newPrimNewThread ()
    {
	return new PrimNewThread();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	Lambda thunk = (Lambda)args.car();
	if (thunk == null) {
	    throw F.newLavaException(name + ": expecting thunk but got: null");
	}
	return new Thread(new LavaRunnable((Lambda)args.car()));
    }

    public class LavaRunnable implements Runnable
    {
	private Lambda lambda;
	LavaRunnable (Lambda lambda) {
	    this.lambda = lambda; 
	}
	public void run () { 
	    // Note: new engine corresponds to new control stack for thread.
	    ((Engine)FR.newEvaluator()).apply(lambda, null);
	}
    }
}

// End of file.
