/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Feb 03 (Thu) 19:54:49 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class Prim_Divide
    extends
	PrimitiveProcedure
{
    public Prim_Divide ()
    {
    }

    public Prim_Divide newPrim_Divide ()
    {
	return new Prim_Divide();
    }

    public Object apply (Pair args, Engine engine)
    {
	if (args.rest() == null) {
	    return Prim_NumHelp.doOp(Prim_NumHelp.DIVIDE, 1, args);
	}
	return Prim_NumHelp.doOp(Prim_NumHelp.DIVIDE,
				args.first(),
				(Pair)args.rest());
    }
}

// End of file.
