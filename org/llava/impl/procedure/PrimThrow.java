/**
 * Created       : 2000 Jan 16 (Sun) 18:36:16 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:54:52 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.F;
import lava.lang.exceptions.LavaException;
import lava.lang.types.Pair;
import libLava.r1.FR1;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimThrow
    extends
	PrimitiveProcedure
{
    public PrimThrow ()
    {
    }

    public PrimThrow newPrimThrow ()
    {
	return new PrimThrow();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	Object exc = args.car();
	if (exc instanceof LavaException) {
	    throw (LavaException) exc;
	}
	throw F.newLavaException((Throwable) exc);
    }
}

// End of file.
