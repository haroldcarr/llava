/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:40:59 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimStringAppend
    extends
	PrimitiveProcedure
{
    public PrimStringAppend ()
    {
    }

    public PrimStringAppend newPrimStringAppend ()
    {
	return new PrimStringAppend();
    }

    public Object apply (Pair args, Engine engine)
    {
	String result = "";
	for (Pair p = args; p != null; p = (Pair)p.cdr()) {
	    result = result + (String)p.car();
	}
	return result;
    }
}

// End of file.
