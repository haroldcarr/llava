/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:49 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava.opt;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimString2Symbol
    extends
	PrimitiveProcedure
{
    public PrimString2Symbol ()
    {
    }

    public PrimString2Symbol newPrimString2Symbol ()
    {
	return new PrimString2Symbol();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	return F.newSymbol((String) args.first());
    }
}

// End of file.
