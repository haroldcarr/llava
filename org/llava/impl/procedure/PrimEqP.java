/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:42:20 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimEqP
    extends
	PrimitiveProcedure
{
    public PrimEqP ()
    {
    }

    public PrimEqP newPrimEqP ()
    {
	return new PrimEqP();
    }

    public Object apply (Pair args, Engine engine)
    {
	// REVISIT
	if (args == null || args.length() != 2) {
	    throw F.newLavaException(name + " currently only handles 2 args");
	}
	return F.newBoolean(args.first() == args.second());
    }
}

// End of file.
