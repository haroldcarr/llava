/**
 * Created       : 2000 Jan 18 (Tue) 04:24:10 by Harold Carr.
 * Last Modified : 2000 Feb 26 (Sat) 22:41:07 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java.opt;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimCurrentTimeMillis
    extends
	PrimitiveProcedure
{
    public PrimCurrentTimeMillis ()
    {
    }

    public PrimCurrentTimeMillis newPrimCurrentTimeMillis ()
    {
	return new PrimCurrentTimeMillis();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 0);
	return F.newLong(System.currentTimeMillis());
    }
}

// End of file.
