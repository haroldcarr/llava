/**
 * Created       : 2000 Jan 07 (Fri) 22:40:51 by Harold Carr.
 * Last Modified : 2002 Oct 12 (Sat) 06:54:17 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lava.lang.types.Pair;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;

public class PrimAppend
    extends
	GenericProcedureImpl
{
    public PrimAppend ()
    {
    }

    public PrimAppend newPrimAppend ()
    {
	return new PrimAppend();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    if (args == null) {
		return null;
	    }
	    Pair lists = List.reverse(args);
	    Pair result = (Pair)lists.car();
	    for (Pair p = (Pair)lists.cdr(); p != null; p = (Pair)p.cdr()) {
		result = List.append((Pair)p.car(), result);
	    }
	    return result;
	}
    }
}

// End of file.
