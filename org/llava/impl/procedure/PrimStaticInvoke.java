/**
 * Created       : 1999 Dec 30 (Thu) 17:43:07 by Harold Carr.
 * Last Modified : 2000 Feb 16 (Wed) 20:25:32 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.procedure.generic.DI;
import libLava.r1.procedure.generic.GenericProcedureImpl; // REVISIT
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimStaticInvoke
    extends
	PrimitiveProcedure
{
    public PrimStaticInvoke ()
    {
    }

    public PrimStaticInvoke newPrimStaticInvoke ()
    {
	return new PrimStaticInvoke();
    }

    public Object apply (Pair args, Engine engine)
    {
	String methodName       = ((Symbol)args.car()).toString();
	Object classOrClassName = args.second();
	Object[] methodArgs     = List.toArray((Pair)args.cddr());

	try {
	    Object result;
	    if (classOrClassName instanceof Symbol) {
		result = DI.invokeStatic(methodName,
					 ((Symbol)classOrClassName).toString(),
					 methodArgs);
	    } else {
		result = DI.invokeStatic(methodName,
					 (Class)classOrClassName,
					 methodArgs);
	    }
	    return GenericProcedureImpl.getWrapJavaPrimitive().wrapJavaPrimitive(result);
	} catch (Throwable t) {
	    throw F.newLavaException(t);
	}
    }
}

// End of file.
