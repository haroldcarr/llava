/**
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2000 Feb 04 (Fri) 06:55:12 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimList_
    extends
	PrimitiveProcedure
{
    public PrimList_ ()
    {
    }

    public PrimList_ newPrimList_ ()
    {
	return new PrimList_();
    }

    public Object apply (Pair args, Engine engine)
    {
	return args;
    }
}

// End of file.
