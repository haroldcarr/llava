/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:54:13 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.generic.GenericProcedureImpl;

public class PrimCons
    extends
	GenericProcedureImpl
{
    public PrimCons ()
    {
    }

    public PrimCons newPrimCons ()
    {
	return new PrimCons();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    checkNumArgs(args, 2);
	    return F.cons(args.first(), args.second());
	}
    }
}

// End of file.
