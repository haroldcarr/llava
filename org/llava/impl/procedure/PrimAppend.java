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
 * Created       : 2000 Jan 07 (Fri) 22:40:51 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:34:54 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava;

import org.llava.lang.types.Pair;
import org.llava.impl.util.List;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.exceptions.UndefinedIdException;
import org.llava.impl.runtime.procedure.generic.GenericProcedureImpl;

public class PrimAppend
    extends
	GenericProcedureImpl
{
    public PrimAppend ()
    {
    }

    public PrimAppend newPrimAppend ()
    {
	return new PrimAppend();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    if (args == null) {
		return null;
	    }
	    Pair lists = List.reverse(args);
	    Pair result = (Pair)lists.car();
	    for (Pair p = (Pair)lists.cdr(); p != null; p = (Pair)p.cdr()) {
		result = List.append((Pair)p.car(), result);
	    }
	    return result;
	}
    }
}

// End of file.
