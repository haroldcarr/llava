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
 * Last Modified : 2004 Nov 30 (Tue) 06:50:09 by Harold Carr.
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
		return mapone(true, procedure, (Pair)lists.car(), engine);
	    }
	    return mapmany(true, procedure, lists, engine);
	}
    }

    // NOTE: This is used by PrimForEach.
    Object mapone (boolean isMap,
		   Procedure procedure, Pair list, Engine engine)
    {
	if (list == null) {
	    return null;
	}
	// REVISIT - turn into iteration.
	Object _car = engine.apply(procedure, List.list(list.car()));
	Object _cdr = mapone(isMap, procedure, (Pair) list.cdr(), engine);
	if (isMap) {
	    return F.cons(_car, _cdr);
	} else {
	    return null;
	}
    }

    // NOTE: This is used by PrimForEach.
    Object mapmany (boolean isMap,
		    Procedure procedure, Pair lists, Engine engine)
    {
	if (lists.car() == null) {
	    return null;
	}
	// REVISIT - turn into iteration.
	Object _car = 
	    engine.apply(procedure, (Pair) mapone(isMap, car, lists, engine));
	Object _cdr = 
	    mapmany(isMap,
		    procedure, (Pair) mapone(isMap, cdr, lists, engine), engine);
	if (isMap) {
	    return F.cons(_car, _cdr);
	} else {
	    return null;
	}
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
