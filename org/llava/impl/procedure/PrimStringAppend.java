/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:49:47 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
