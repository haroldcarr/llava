/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:48 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava.opt;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
