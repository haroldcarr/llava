/**
 * Created       : 1999 Dec 23 (Thu) 03:48:44 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:18:05 by Harold Carr.
 */

package lavaProfile.runtime;

import lavaProfile.F;
import lava.lang.exceptions.BacktraceHandler;
import lava.lang.exceptions.LavaException;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.runtime.EnvironmentTopLevel;
import lava.runtime.Evaluator;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.code.Code;
import lavaProfile.runtime.env.ActivationFrame;

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
	} catch (LavaException e) {
	    e.addToBacktrace(F.cons(code, frame), backtraceHandler);
	    throw e;
	} catch (Throwable e) {
	    LavaException le = F.newLavaException(e);
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
