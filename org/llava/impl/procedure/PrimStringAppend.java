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
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:11 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava;

import org.llava.lang.types.Pair;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimStringAppend
    extends
	PrimitiveProcedure
{
    public PrimStringAppend ()
    {
    }

    public PrimStringAppend newPrimStringAppend ()
    {
	return new PrimStringAppend();
    }

    public Object apply (Pair args, Engine engine)
    {
	String result = "";
	for (Pair p = args; p != null; p = (Pair)p.cdr()) {
	    result = result + (String)p.car();
	}
	return result;
    }
}

// End of file.
