/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2000 Feb 21 (Mon) 01:47:29 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava.opt;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimPairP
    extends
	PrimitiveProcedure
{
    public PrimPairP ()
    {
    }

    public PrimPairP newPrimPairP ()
    {
	return new PrimPairP();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	return F.newBoolean(args.first() instanceof Pair);
    }
}

// End of file.
