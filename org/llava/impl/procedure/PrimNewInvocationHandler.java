/**
 * Created       : 2000 Feb 10 (Thu) 22:11:38 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:23:58 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lava.lang.types.Pair;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.procedure.Lambda;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
	checkNumArgs(args, 1);
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
