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
 * Created       : 2000 Jan 07 (Fri) 22:40:51 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:32:49 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Lambda;
import org.llava.Pair;
import org.llava.UndefinedIdException;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;
import org.llava.impl.util.List;

public class PrimNewThread
    extends
	PrimitiveProcedure
{
    public PrimNewThread ()
    {
    }

    public PrimNewThread newPrimNewThread ()
    {
	return new PrimNewThread();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	Lambda thunk = (Lambda)args.car();
	if (thunk == null) {
	    throw F.newLlavaException(name + ": expecting thunk but got: null");
	}
	return new Thread(new LlavaRunnable((Lambda)args.car()));
    }

    public class LlavaRunnable implements Runnable
    {
	private Lambda lambda;
	LlavaRunnable (Lambda lambda) {
	    this.lambda = lambda; 
	}
	public void run () { 
	    // Note: new engine corresponds to new control stack for thread.
	    ((Engine)F.newEvaluator()).apply(lambda, null);
	}
    }
}

// End of file.
