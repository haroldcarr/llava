/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:36:51 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimEqualP
    extends
	PrimitiveProcedure
{
    public PrimEqualP ()
    {
    }

    public PrimEqualP newPrimEqualP ()
    {
	return new PrimEqualP();
    }

    public Object apply (Pair args, Engine engine)
    {
	// REVISIT
	if (args == null || args.length() != 2) {
	    throw F.newLavaException(name + " currently only handles 2 args");
	}
	Object first = args.first();
	Object second = args.second();
	if (first == second) {
	    return F.newBoolean(true);
	}
	return F.newBoolean(first == null ? false : first.equals(second));
    }
}

// End of file.
