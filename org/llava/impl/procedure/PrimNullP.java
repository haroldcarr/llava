/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2000 Feb 21 (Mon) 01:40:23 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava.opt;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimNullP
    extends
	PrimitiveProcedure
{
    public PrimNullP ()
    {
    }

    public PrimNullP newPrimNullP ()
    {
	return new PrimNullP();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	return F.newBoolean(args.first() == null);
    }
}

// End of file.
