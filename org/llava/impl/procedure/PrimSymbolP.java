/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:50 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava.opt;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimSymbolP
    extends
	PrimitiveProcedure
{
    public PrimSymbolP ()
    {
    }

    public PrimSymbolP newPrimSymbolP ()
    {
	return new PrimSymbolP();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	return F.newBoolean(args.first() instanceof Symbol);
    }
}

// End of file.
