/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:01 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class Prim_LT
    extends
	PrimitiveProcedure
{
    public Prim_LT ()
    {
    }

    public Prim_LT newPrim_LT ()
    {
	return new Prim_LT();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOpBoolean(Prim_NumHelp.LT, args);
    }
}

// End of file.
