/**
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:14 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
