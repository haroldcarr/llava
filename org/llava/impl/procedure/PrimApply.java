/**
 * Created       : 2000 Jan 07 (Fri) 22:40:51 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 18:04:50 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.lang.types.Pair;
import libLava.r1.FR1;
import libLava.r1.Engine;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.generic.GenericProcedureImpl;

public class PrimApply
    extends
	GenericProcedureImpl
{
    public PrimApply ()
    {
    }

    public PrimApply newPrimApply ()
    {
	return new PrimApply();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    // REVISIT - handle more list args like CL (and Scheme?).
	    checkNumArgs(args, 2);
	    return engine.apply(args.car(), (Pair)args.cadr());
	}
    }
}

// End of file.
