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
 * Last Modified : 2004 Sep 03 (Fri) 15:34:18 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java;

import org.llava.lang.types.Pair;
import org.llava.lang.types.Procedure;
import org.llava.impl.util.List;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

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
