/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:10 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java.opt;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;

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
