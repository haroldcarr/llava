/**
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2000 Feb 21 (Mon) 01:49:32 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava.opt;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

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
