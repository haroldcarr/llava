/**
 * Created       : 2000 Feb 10 (Thu) 22:11:38 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:54:31 by Harold Carr.
 */

package libLava.r1.procedure.primitive.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lava.lang.types.Pair;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.procedure.Lambda;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

// For Java 1.3 beta

public class PrimNewInvocationHandler
    extends
	PrimitiveProcedure
{
    public PrimNewInvocationHandler ()
    {
    }

    public PrimNewInvocationHandler newPrimNewInvocationHandler ()
    {
	return new PrimNewInvocationHandler();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNuumArgs(args, 1);
	return new LavaInvocationHandler((Lambda)args.car(), engine);
    }

    public class LavaInvocationHandler implements InvocationHandler
    {
	public Lambda lambda;
	public Engine engine;

	public LavaInvocationHandler (Lambda lambda, Engine engine)
	{
	    this.lambda = lambda;
	    this.engine = engine;
	}

	public Object invoke (Object proxy, Method method, Object[] args)
	{
	    return engine.apply(lambda, List.list(proxy, method, args));
	}
    }
}

// End of file.
