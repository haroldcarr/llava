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
 * Created       : 1999 Dec 23 (Thu) 03:48:44 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:33:05 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.impl.F;
import org.llava.lang.exceptions.BacktraceHandler;
import org.llava.lang.exceptions.LlavaException;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Procedure;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.code.Code;
import org.llava.impl.runtime.env.ActivationFrame;

public class EngineImpl
    implements
	Engine
{
    protected Code code;
    protected ActivationFrame frame;
    protected BacktraceHandler backtraceHandler;

    public EngineImpl ()
    {
    }

    public Evaluator newEvaluator ()
    {
	EngineImpl engineImpl = new EngineImpl();
	engineImpl.backtraceHandler = FR.newBacktraceHandler();
	return engineImpl;
    }

    public Object eval (Object form, EnvironmentTopLevel env)
    {
	// REVISIT: do not create null frame on each eval.
	ActivationFrame nullFrame = FR.newActivationFrame(env);
	return run((Code)form, nullFrame);
    }

    public Object eval (Object form, ActivationFrame frame)
    {
	return run((Code)form, frame);
    }

    public Object tailCall (Code code, ActivationFrame frame)
    {
	this.code = code;
	this.frame = frame;
	return this;
    }

    public Object run (Code code, ActivationFrame frame)
    {
	try {
	    this.code = code;
	    this.frame = frame;
	    Object result = code.run(frame, this); // REVISIT
	    while (result == this) {
		result = this.code.run(this.frame, this);
	    }
	    return result;
	} catch (ThreadDeath e) {
	    // REVISIT - do not wrap so it shows up in automatic
	    // jvm stacktrace.  See GenericProcedureImpl.
	    throw e;
	} catch (LlavaException e) {
	    e.addToBacktrace(F.cons(code, frame), backtraceHandler);
	    throw e;
	} catch (Throwable e) {
	    LlavaException le = F.newLlavaException(e);
	    le.addToBacktrace(F.cons(code, frame), backtraceHandler);
	    throw le;
	}
    }

    // This is used during macro expansion and when applying procedures.
    public Object run ()
    {
	Object result = this.code.run(this.frame, this);
	while (result == this)
	    result = this.code.run(this.frame, this);
	return result;
    }

    public Object apply (Object x, Pair args)
    {
	Object result = ((Procedure)x).apply(args, this);
	if (result == this) {
	    return this.run();
	}
	return result;
    }

    public ActivationFrame getFrame ()
    {
	return frame;
    }
}

// End of file.
