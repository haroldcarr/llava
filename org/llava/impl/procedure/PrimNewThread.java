/**
 * Created       : 2000 Jan 07 (Fri) 22:40:51 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:58:59 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import lava.util.List;
import libLava.rt.FR;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.Lambda;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

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
