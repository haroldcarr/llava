/**
 * Created       : 2000 Jan 03 (Mon) 21:10:17 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:10 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.procedure.Lambda;
import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
	    throw F.newLavaException("_%defGenInternal expected existing value to be a lambda but found: " + currentValue);
	}
	return frame.set(identifier, 
			 FR.newGenericProcedure((Lambda)currentValue));
    }
}

// End of file.
