/**
 * Created       : 2000 Jan 18 (Tue) 04:24:10 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:08 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java.opt;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimInstanceof
    extends
	PrimitiveProcedure
{
    public PrimInstanceof ()
    {
    }

    public PrimInstanceof newPrimInstanceof ()
    {
	return new PrimInstanceof();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 2);
	try {
	    return F.newBoolean(Class.forName(args.second().toString())
				.isInstance(args.first()));
	} catch (Throwable e) {
	    throw F.newLavaException(e);
	}
    }
}

// End of file.
