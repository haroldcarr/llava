/**
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:58:09 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.generic.GenericProcedureImpl;

public class PrimLength
    extends
	GenericProcedureImpl
{
    public PrimLength ()
    {
    }

    public PrimLength newPrimLength ()
    {
	return new PrimLength();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    checkNumArgs(args, 1);
	    if (args.car() != null) {
		throw F.newLavaException("length: unexpected argument: " + args.car().toString());
	    }
	    // Length of null is 0;
	    return F.newInteger(0);
	}
    }
}

// End of file.
