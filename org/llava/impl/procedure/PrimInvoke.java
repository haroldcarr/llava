/**
 * Created       : 2000 Jan 17 (Mon) 17:21:01 by Harold Carr.
 * Last Modified : 2000 Feb 16 (Wed) 20:27:05 by Harold Carr.
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
