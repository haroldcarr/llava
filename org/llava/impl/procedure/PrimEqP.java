/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:16:51 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
