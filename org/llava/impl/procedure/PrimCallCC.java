/**
 * Created       : 2000 Jan 14 (Fri) 14:57:59 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:08 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lava.lang.exceptions.LavaException;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lavaProfile.util.List;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.procedure.primitive.PrimitiveProcedure;

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
		throw FR.newWrongNumberOfArgumentsException(name);
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
