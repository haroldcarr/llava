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
 * Created       : 2000 Jan 17 (Mon) 19:58:59 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:33:52 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.F;
import org.llava.LlavaException;
import org.llava.Pair;
import org.llava.Procedure;
import org.llava.runtime.ActivationFrame;
import org.llava.runtime.BacktraceHandler;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;

import org.llava.impl.runtime.Code;
import org.llava.impl.runtime.EngineImpl;

public class EngineStepImpl
    extends
	EngineImpl
{
    public EngineStepImpl ()
    {
    }

    public Evaluator newEvaluator ()
    {
	EngineStepImpl engineImpl = new EngineStepImpl();
	engineImpl.backtraceHandler = F.newBacktraceHandler();
	return engineImpl;
    }

    public Object run (Code code, ActivationFrame frame)
    {
	try {
	    this.code = code;
	    this.frame = frame;
	    System.out.println(code);
	    System.out.println(frame);
	    System.in.read();
	    Object result = code.run(frame, this); // REVISIT
	    while (result == this) {
		result = this.code.run(this.frame, this);
	    }
	    return result;
	} catch (LlavaException e) {
	    e.addToBacktrace(F.cons(code, frame), backtraceHandler);
	    throw e;
	} catch (Throwable e) {
	    LlavaException le = F.newLlavaException(e);
	    le.addToBacktrace(F.cons(code, frame), backtraceHandler);
	    throw le;
	}
    }

    // This is used during macro expansion.
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
}

// End of file.
