/**
 * Created       : 2000 Jan 03 (Mon) 21:10:17 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:55:55 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.procedure.Lambda;
import libLava.r1.env.ActivationFrame;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimDefGenInternal
    extends
	PrimitiveProcedure
{
    public PrimDefGenInternal ()
    {
    }

    public PrimDefGenInternal newPrimDefGenInternal ()
    {
	return new PrimDefGenInternal();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	Symbol identifier = (Symbol)args.car();
	ActivationFrame frame = engine.getFrame();
	Object currentValue = frame.get(identifier);
	if (!(currentValue instanceof Lambda)) {
	    throw F.newLavaException(".%defGenInternal expected existing value to be a lambda");
	}
	return frame.set(identifier, 
			 FR1.newGenericProcedure((Lambda)currentValue));
    }
}

// End of file.
