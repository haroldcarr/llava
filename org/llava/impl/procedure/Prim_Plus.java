/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Feb 03 (Thu) 19:31:37 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

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
