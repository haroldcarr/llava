/**
 * Created       : 2000 Jan 17 (Mon) 19:58:59 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:13 by Harold Carr.
 */

package libLava.r1;

import lava.F;
import lava.lang.exceptions.BacktraceHandler;
import lava.lang.exceptions.LavaException;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.r1.EngineImpl;
import libLava.r1.FR1;
import libLava.r1.code.Code;
import libLava.r1.env.ActivationFrame;

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
	engineImpl.backtraceHandler = FR1.newBacktraceHandler();
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
	} catch (LavaException e) {
	    e.addToBacktrace(F.cons(code, frame), backtraceHandler);
	    throw e;
	} catch (Throwable e) {
	    LavaException le = F.newLavaException(e);
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
