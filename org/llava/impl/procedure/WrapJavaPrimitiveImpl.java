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
 * Created       : 2000 Jan 10 (Mon) 02:06:49 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:53:02 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.procedure.WrapJavaPrimitive;

public class WrapJavaPrimitiveImpl
    implements
	WrapJavaPrimitive
{
    public WrapJavaPrimitiveImpl ()
    {
    }

    public WrapJavaPrimitive newWrapJavaPrimitive ()
    {
	return new WrapJavaPrimitiveImpl();
    }

    public Object wrapJavaPrimitive (Object x)
    {
	if (x == null) {
	    return x;
	}

	Class clazz = x.getClass();
	
	if (! clazz.isPrimitive()) {
	    return x;
	}

	if (clazz == Boolean.TYPE) {
	    return F.newBoolean((Boolean)x);
	} else if (clazz == Byte.TYPE) {
	    return F.newByte((Byte)x);
	} else if (clazz == Character.TYPE) {
	    return F.newCharacter((Character)x);
	} else if (clazz == Short.TYPE) {
	    return F.newShort((Short)x);
	} else if (clazz == Integer.TYPE) {
	    return F.newInteger((Integer)x);
	} else if (clazz == Long.TYPE) {
	    return F.newLong((Long)x);
	} else if (clazz == Float.TYPE) {
	    return F.newFloat((Float)x);
	} else if (clazz == Double.TYPE) {
	    return F.newDouble((Double)x);
	} else if (clazz == Void.TYPE) {
	    return F.newVoid((Void)x);
	} else {
	    throw F.newLlavaException("this should never happen" + x);
	}
    }
}
