/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:06 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class Prim_Times
    extends
	PrimitiveProcedure
{
    public Prim_Times ()
    {
    }

    public Prim_Times newPrim_Times ()
    {
	return new Prim_Times();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOp(Prim_NumHelp.TIMES, 1, args);
    }
}

// End of file.
