/**
 * Created       : 1999 Dec 30 (Thu) 18:17:06 by Harold Carr.
 * Last Modified : 2000 Feb 16 (Wed) 21:36:04 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import libLava.r1.Engine;
import libLava.r1.procedure.generic.DI;
import libLava.r1.procedure.generic.GenericProcedureImpl; // REVISIT
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimStaticField
    extends
	PrimitiveProcedure
{
    public PrimStaticField ()
    {
    }

    public PrimStaticField newPrimStaticField ()
    {
	return new PrimStaticField();
    }

    public Object apply (Pair args, Engine engine)
    {
	String fieldName        = ((Symbol)args.car()).toString();
	Object classOrClassName = args.second();
	Object fieldArg         = args.cddr();
	Class targetClass;
	if (classOrClassName instanceof Symbol) {
	    try {
		targetClass = Class.forName(classOrClassName.toString());
	    } catch (Throwable t) {
		throw F.newLavaException(t);
	    }
	} else {
	    targetClass = (Class) classOrClassName;
	}

	try {
	    if (fieldArg != null) {
		Object value = ((Pair)fieldArg).car();
		return DI.staticFieldSet(fieldName, targetClass, value);
	    }
	    Object result = DI.staticFieldRef(fieldName, targetClass);
	    return GenericProcedureImpl.getWrapJavaPrimitive().wrapJavaPrimitive(result);
	} catch (Throwable t) {
	    throw F.newLavaException(t);
	}
    }
}

// End of file.
