/**
 * Created       : 2001 Mar 05 (Mon) 10:55:19 by Harold Carr.
 * Last Modified : 2001 Mar 05 (Mon) 20:15:58 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import lava.lang.types.Pair;
import libLava.r1.Engine;
import libLava.r1.env.Namespace;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimImport
    extends
	PrimitiveProcedure
{
    private Namespace namespace;

    public PrimImport ()
    {
    }

    public PrimImport (Namespace namespace)
    {
	this.namespace = namespace;
    }

    public PrimImport newPrimImport (Namespace namespace)
    {
	return new PrimImport(namespace);
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	return namespace._import(args.first());
    }
}

// End of file.
