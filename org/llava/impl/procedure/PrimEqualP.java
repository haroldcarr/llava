/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:16:56 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
