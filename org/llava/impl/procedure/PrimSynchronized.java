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
 * Created       : 2000 Jan 16 (Sun) 19:38:28 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:25:22 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.Procedure;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;
import org.llava.impl.util.List;

public class PrimSynchronized
    extends
	PrimitiveProcedure
{
    public PrimSynchronized ()
    {
    }

    public PrimSynchronized newPrimSynchronized ()
    {
	return new PrimSynchronized();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 2);
	Object    lock  = args.first();
	Procedure thunk = (Procedure) args.second();
	synchronized (lock) {
	    return engine.apply(thunk, null);
	}
    }
}

// End of file.
