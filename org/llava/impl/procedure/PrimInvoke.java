/**
 * Created       : 2000 Jan 17 (Mon) 17:21:01 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:11 by Harold Carr.
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

public class PrimInvoke
    extends
	PrimitiveProcedure
{
    public PrimInvoke ()
    {
    }

    public PrimInvoke newPrimInvoke ()
    {
	return new PrimInvoke();
    }

    public Object apply (Pair args, Engine engine)
    {
	String methodName   = ((Symbol)args.car()).toString();
	Object instance     = args.second();
	Object[] methodArgs = List.toArray((Pair)args.cddr());

	try {
	    Object result = DI.invoke(methodName, instance, methodArgs);

	    return GenericProcedureImpl.getWrapJavaPrimitive().wrapJavaPrimitive(result);
	} catch (Throwable t) {
	    throw F.newLavaException(t);
	}
    }
}

// End of file.
