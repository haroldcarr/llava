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
 * Last Modified : 2004 Dec 07 (Tue) 19:06:03 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.Symbol;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.DI;
import org.llava.impl.procedure.GenericProcedureImpl; // REVISIT
import org.llava.impl.procedure.PrimitiveProcedure;

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
