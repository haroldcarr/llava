/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:18:57 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class Prim_EQ
    extends
	PrimitiveProcedure
{
    public Prim_EQ ()
    {
    }

    public Prim_EQ newPrim_EQ ()
    {
	return new Prim_EQ();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOpBoolean(Prim_NumHelp.EQ, args);
    }
}

// End of file.
