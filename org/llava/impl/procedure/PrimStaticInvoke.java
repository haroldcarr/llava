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
 * Created       : 1999 Dec 30 (Thu) 17:43:07 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:08 by Harold Carr.
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
	    throw F.newLlavaException(t);
	}
    }
}

// End of file.
