/**
 * Created       : 2000 Jan 07 (Fri) 22:40:51 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:48:31 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lava.lang.types.Pair;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;

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
