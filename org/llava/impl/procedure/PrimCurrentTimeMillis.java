/**
 * Created       : 2000 Jan 18 (Tue) 04:24:10 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:07 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java.opt;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
