/**
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:17 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava.opt;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
