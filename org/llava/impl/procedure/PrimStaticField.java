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
 * Last Modified : 2004 Sep 03 (Fri) 15:35:07 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.generic.DI;
import org.llava.impl.runtime.procedure.generic.GenericProcedureImpl; // REVISIT
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

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
		throw F.newLlavaException(t);
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
	    throw F.newLlavaException(t);
	}
    }
}

// End of file.
