/**
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:13 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;

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
