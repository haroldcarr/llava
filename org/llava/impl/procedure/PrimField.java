/**
 * Created       : 1999 Dec 30 (Thu) 18:17:06 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:36 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.generic.DI;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl; // REVISIT
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimField
    extends
	PrimitiveProcedure
{
    public PrimField ()
    {
    }

    public PrimField newPrimField ()
    {
	return new PrimField();
    }

    public Object apply (Pair args, Engine engine)
    {
	String fieldName    = ((Symbol)args.car()).toString();
	Object targetObject = args.second();
	Object fieldArg     = args.cddr();

	try {
	    if (fieldArg != null) {
		Object value = ((Pair)fieldArg).car();
		return DI.fieldSet(fieldName, targetObject, value);
	    }
	    Object result = DI.fieldRef(fieldName, targetObject);
	    return GenericProcedureImpl.getWrapJavaPrimitive().wrapJavaPrimitive(result);
	} catch (Throwable t) {
	    throw F.newLavaException(t);
	}
    }
}

// End of file.
