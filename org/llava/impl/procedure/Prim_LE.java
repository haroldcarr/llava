/**
 * Created       : 2002 Jan 12 (Sat) 12:01:47 by Harold Carr.
 * Last Modified : 2002 Jan 12 (Sat) 12:02:12 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class Prim_LE
    extends
	PrimitiveProcedure
{
    public Prim_LE ()
    {
    }

    public Prim_LE newPrim_LE ()
    {
	return new Prim_LE();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOpBoolean(Prim_NumHelp.LT, args);
    }
}

// End of file.
