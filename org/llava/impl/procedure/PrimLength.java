/**
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:12 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;

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
