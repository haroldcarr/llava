/**
 * Created       : 2001 Mar 05 (Mon) 21:04:59 by Harold Carr.
 * Last Modified : 2001 Mar 05 (Mon) 21:25:21 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.F;
import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.env.Namespace;
import libLava.r1.procedure.primitive.java.PrimNewPrim;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimNew
    extends
	PrimitiveProcedure
{
    private Namespace namespace;
    private PrimNewPrim primNewPrim;

    public PrimNew ()
    {
    }

    public PrimNew (Namespace namespace, PrimNewPrim primNewPrim)
    {
	this.namespace = namespace;
	this.primNewPrim = primNewPrim;
    }

    public PrimNew newPrimNew (Namespace namespace, PrimNewPrim primNewPrim)
    {
	return new PrimNew(namespace, primNewPrim);
    }

    public Object apply (Pair args, Engine engine)
    {
	String className = args.car().toString();
	className = namespace.getFullNameForClass(className);
	return primNewPrim.apply(F.cons(className, (Pair)args.cdr()),  engine);
    }
}

// End of file.
