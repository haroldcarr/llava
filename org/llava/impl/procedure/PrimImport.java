/**
 * Created       : 2001 Mar 05 (Mon) 10:55:19 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:45:33 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.env.Namespace;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
	return namespace._import((Symbol)args.first());
    }
}

// End of file.
