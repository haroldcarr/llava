/**
 * Created       : 2000 Jan 18 (Tue) 04:24:10 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 23:21:59 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java.opt;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

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
