/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:33 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;

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
