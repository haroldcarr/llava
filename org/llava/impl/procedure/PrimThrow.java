/**
 * Created       : 2000 Jan 16 (Sun) 18:36:16 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:18:54 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lavaProfile.F;
import lava.lang.exceptions.LavaException;
import lava.lang.types.Pair;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
