/**
 * Created       : 2001 Mar 05 (Mon) 10:55:19 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:45:52 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.env.Namespace;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
	return namespace._package((Symbol)args.first(), (Symbol)args.second());
    }
}

// End of file.
