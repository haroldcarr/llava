/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:47 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava.opt;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
