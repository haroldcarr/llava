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
 * Created       : 2000 Jan 16 (Sun) 18:36:16 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:33:07 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.LlavaException;
import org.llava.Pair;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;

public class PrimThrow
    extends
	PrimitiveProcedure
{
    public PrimThrow ()
    {
    }

    public PrimThrow newPrimThrow ()
    {
	return new PrimThrow();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	Object exc = args.car();
	if (exc instanceof LlavaException) {
	    throw (LlavaException) exc;
	}
	throw F.newLlavaException((Throwable) exc);
    }
}

// End of file.
