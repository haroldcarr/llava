/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Feb 03 (Thu) 19:31:28 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class Prim_GE
    extends
	PrimitiveProcedure
{
    public Prim_GE ()
    {
    }

    public Prim_GE newPrim_GE ()
    {
	return new Prim_GE();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOpBoolean(Prim_NumHelp.GE, args);
    }
}

// End of file.