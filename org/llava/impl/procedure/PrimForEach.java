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
 * Created       : 2004 Nov 30 (Tue) 06:41:40 by Harold Carr.
 * Last Modified : 2004 Dec 01 (Wed) 22:21:53 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava.opt;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Procedure;
import org.llava.impl.util.List;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.exceptions.UndefinedIdException;
import org.llava.impl.runtime.procedure.generic.GenericProcedureImpl;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimForEach
    extends
	PrimitiveProcedure
{
    // NOTE: This implementation is tightly coupled with PrimMap.
    PrimMap map = new PrimMap();

    public PrimForEach ()
    {
    }

    public PrimForEach newPrimForEach ()
    {
	return new PrimForEach();
    }

    public Object apply (Pair args, Engine engine)
    {
	Procedure procedure = (Procedure) args.first();
	Pair lists = (Pair) args.rest();
	if (lists == null) {
	    return null;
	}
	if (lists.cdr() == null) {
	    return map.mapone(false, procedure, (Pair)lists.car(), engine);
	}
	return map.mapmany(false, procedure, lists, engine);
    }
}

// End of file.
