/**
 * Created       : 1999 Dec 30 (Thu) 18:17:06 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:44 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.generic.DI;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl; // REVISIT
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
