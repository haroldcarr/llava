/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Feb 03 (Thu) 19:31:34 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class Prim_Minus
    extends
	PrimitiveProcedure
{
    public Prim_Minus ()
    {
    }

    public Prim_Minus newPrim_Minus ()
    {
	return new Prim_Minus();
    }

    public Object apply (Pair args, Engine engine)
    {
	if (args.rest() == null) {
	    return Prim_NumHelp.doOp(Prim_NumHelp.MINUS, 0, args);
	}
	return Prim_NumHelp.doOp(Prim_NumHelp.MINUS,
				args.first(), 
				(Pair)args.rest());
    }
}

// End of file.
