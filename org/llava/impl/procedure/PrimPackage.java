/**
 * Created       : 2001 Mar 05 (Mon) 10:55:19 by Harold Carr.
 * Last Modified : 2001 Mar 05 (Mon) 11:52:26 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.env.Namespace;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimPackage
    extends
	PrimitiveProcedure
{
    private Namespace namespace;

    public PrimPackage ()
    {
    }

    public PrimPackage (Namespace namespace)
    {
	this.namespace = namespace;
    }

    public PrimPackage newPrimPackage (Namespace namespace)
    {
	return new PrimPackage(namespace);
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 2);
	return namespace._package(args.first(), args.second());
    }
}

// End of file.
