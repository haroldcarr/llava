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
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:12 by Harold Carr.
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

public class PrimMap
    extends
	GenericProcedureImpl
{
    public PrimMap ()
    {
    }

    public PrimMap newPrimMap ()
    {
	return new PrimMap();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    Procedure procedure = (Procedure) args.first();
	    Pair lists = (Pair) args.rest();
	    if (lists == null) {
		return null;
	    }
	    if (lists.cdr() == null) {
		return mapone(procedure, (Pair)lists.car(), engine);
	    }
	    return mapmany(procedure, lists, engine);
	}
    }

    private Object mapone (Procedure procedure, Pair list, Engine engine)
    {
	if (list == null) {
	    return null;
	}
	// REVISIT - turn into iteration.
	return F.cons(engine.apply(procedure, List.list(list.car())),
		      mapone(procedure, (Pair) list.cdr(), engine));
    }

    private Object mapmany (Procedure procedure, Pair lists, Engine engine)
    {
	if (lists.car() == null) {
	    return null;
	}
	// REVISIT - turn into iteration.
	return F.cons(engine.apply(procedure,
				   (Pair) mapone(car, lists, engine)),
		      mapmany(procedure, 
			      (Pair) mapone(cdr, lists, engine), engine));
    }

    private Procedure car =
	new PrimitiveProcedure() {
		public Object apply (Pair args, Engine engine) {
		    return args.caar();
		}
	    };
    
    private Procedure cdr =
	new PrimitiveProcedure() {
		public Object apply (Pair args, Engine engine) {
		    return args.cdar();
		}
	    };
	    
}

// End of file.
