/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/


/**
 * Created       : 2000 Jan 17 (Mon) 17:21:01 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:03 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;
import org.llava.impl.util.List;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.generic.DI;
import org.llava.impl.runtime.procedure.generic.GenericProcedureImpl; // REVISIT
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

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
	    throw F.newLlavaException(t);
	}
    }
}

// End of file.
