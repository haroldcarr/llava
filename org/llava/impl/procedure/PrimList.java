/**
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:40:49 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.generic.GenericProcedureImpl;

public class PrimList
    extends
	GenericProcedureImpl
{
    public PrimList ()
    {
    }

    public PrimList newPrimList ()
    {
	return new PrimList();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    return args;
	}
    }
}

// End of file.
