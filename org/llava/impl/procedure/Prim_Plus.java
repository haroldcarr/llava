/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:46:33 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class Prim_Plus
    extends
	PrimitiveProcedure
{
    public Prim_Plus ()
    {
    }

    public Prim_Plus newPrim_Plus ()
    {
	return new Prim_Plus();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOp(Prim_NumHelp.PLUS, 0, args);
    }
}

// End of file.
