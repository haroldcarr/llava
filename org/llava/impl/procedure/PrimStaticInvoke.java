/**
 * Created       : 1999 Dec 30 (Thu) 17:43:07 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:16 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.generic.DI;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl; // REVISIT
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
