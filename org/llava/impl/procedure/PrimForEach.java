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
 * Last Modified : 2004 Dec 07 (Tue) 19:17:21 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.Procedure;
import org.llava.UndefinedIdException;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.GenericProcedureImpl;
import org.llava.impl.procedure.PrimitiveProcedure;
import org.llava.impl.util.List;

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
