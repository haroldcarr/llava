/**
 * Created       : 2001 Jul 21 (Sat) 01:11:16 by Harold Carr.
 * Last Modified : 2001 Jul 21 (Sat) 01:12:57 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class Prim_Modulo
    extends
	PrimitiveProcedure
{
    public Prim_Modulo ()
    {
    }

    public Prim_Modulo newPrim_Modulo ()
    {
	return new Prim_Modulo();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 2);
	return F.newInteger(((Integer)args.car()).intValue() 
			    % 
			    ((Integer)args.cadr()).intValue());
	    
    }
}

// End of file.
