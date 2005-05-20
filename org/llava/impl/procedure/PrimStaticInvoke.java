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
 * Last Modified : 2005 May 20 (Fri) 13:37:49 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.Symbol;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.RI;
import org.llava.impl.procedure.GenericProcedureImpl; // REVISIT
import org.llava.impl.procedure.PrimitiveProcedure;
import org.llava.impl.util.List;

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
		result = RI.invokeStatic(methodName,
					 ((Symbol)classOrClassName).toString(),
					 methodArgs);
	    } else {
		result = RI.invokeStatic(methodName,
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
