/**
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 22:33:39 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava.opt;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.generic.GenericProcedureImpl;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

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
