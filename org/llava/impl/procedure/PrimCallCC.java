/**
 * Created       : 2000 Jan 14 (Fri) 14:57:59 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:55:34 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.lang.exceptions.LavaException;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.util.List;
import libLava.r1.FR1;
import libLava.r1.Engine;
import libLava.r1.procedure.primitive.PrimitiveProcedure;

public class PrimCallCC
    extends
	PrimitiveProcedure
{
    public PrimCallCC ()
    {
    }

    public PrimCallCC newPrimCallCC ()
    {
	return new PrimCallCC();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);

	Continuation continuation = new Continuation();

	try {

	    return engine.apply(args.car(), List.list(continuation));

	} catch (ContinuationException e) {

	    if (e.getContinuation() == continuation) {
		return e.getValue();
	    }

	    throw e;
	}
    }

    private class Continuation implements Procedure 
    {
	private String name = "Internal Continuation";
	public Continuation () {}
	public Object apply (Pair args, Engine engine) {
	    if (args == null || args.length() != 1) {
		throw FR1.newWrongNumberOfArgumentsException(name);
	    }
	    throw new ContinuationException(this, args.car());
	}
	public String getName ()            { return name; }
	public String setName (String name) { return this.name = name; }
    }

    // ContinuationException must be a LavaException 
    // so it can make it through the engine.

    private class ContinuationException extends LavaException
    {
	private Continuation continuation;
	private Object value;
	public ContinuationException (Continuation continuation,Object value) {
	    super("ContinuationException");
	    this.continuation = continuation;
	    this.value        = value;
	}
	public Object getContinuation () { return continuation; }
	public Object getValue        () { return value; }
    }
}

// End of file.
