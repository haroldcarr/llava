/**
 * Created       : 2000 Jan 07 (Fri) 22:40:51 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:40:29 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.lang.types.Pair;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.generic.GenericProcedureImpl;

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
		return null;;
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
