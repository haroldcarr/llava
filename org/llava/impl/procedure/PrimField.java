/**
 * Created       : 1999 Dec 30 (Thu) 18:17:06 by Harold Carr.
 * Last Modified : 2000 Feb 16 (Wed) 21:32:05 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import libLava.r1.Engine;
import libLava.r1.procedure.generic.DI;
import libLava.r1.procedure.generic.GenericProcedureImpl; // REVISIT
import libLava.r1.procedure.primitive.PrimitiveProcedure;

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
