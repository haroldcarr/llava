/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 23:22:05 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java.opt;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import libLava.r1.Engine;
import libLava.r1.procedure.generic.GenericProcedureImpl;

public class PrimNot
    extends
	GenericProcedureImpl
{
    public PrimNot ()
    {
    }

    public PrimNot newPrimNot ()
    {
	return new PrimNot();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	// N.B.: This is Scheme not - anything not false is true.
	Object first = args.first();
	if (first instanceof Boolean &&
	    ((Boolean)first).booleanValue() == false) {
	    return F.newBoolean(true);
	}
	return F.newBoolean(false);
    }
}

// End of file.
