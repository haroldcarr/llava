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
 * Created       : 2000 Feb 10 (Thu) 22:11:38 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:34:13 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.llava.lang.types.Pair;
import org.llava.impl.util.List;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.procedure.Lambda;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

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
	return new LlavaInvocationHandler((Lambda)args.car(), engine);
    }

    public class LlavaInvocationHandler implements InvocationHandler
    {
	public Lambda lambda;
	public Engine engine;

	public LlavaInvocationHandler (Lambda lambda, Engine engine)
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
