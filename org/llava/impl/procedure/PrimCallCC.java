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
 * Created       : 2000 Jan 14 (Fri) 14:57:59 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:22:30 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.LlavaException;
import org.llava.Pair;
import org.llava.Procedure;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;
import org.llava.impl.util.List;

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
		throw F.newWrongNumberOfArgumentsException(name);
	    }
	    throw new ContinuationException(this, args.car());
	}
	public String getName ()            { return name; }
	public String setName (String name) { return this.name = name; }
    }

    // ContinuationException must be a LlavaException 
    // so it can make it through the engine.

    private class ContinuationException extends LlavaException
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
