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
 * Created       : 1999 Dec 30 (Thu) 18:17:06 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:03 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.generic.DI;
import org.llava.impl.runtime.procedure.generic.GenericProcedureImpl; // REVISIT
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

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
	    throw F.newLlavaException(t);
	}
    }
}

// End of file.
