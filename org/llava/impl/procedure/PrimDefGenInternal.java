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
 * Created       : 2000 Jan 03 (Mon) 21:10:17 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:01 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;
import org.llava.impl.util.List;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.procedure.Lambda;
import org.llava.impl.runtime.env.ActivationFrame;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimDefGenInternal
    extends
	PrimitiveProcedure
{
    public PrimDefGenInternal ()
    {
    }

    public PrimDefGenInternal newPrimDefGenInternal ()
    {
	return new PrimDefGenInternal();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	Symbol identifier = (Symbol)args.car();
	ActivationFrame frame = engine.getFrame();
	Object currentValue = frame.get(identifier);
	if (!(currentValue instanceof Lambda)) {
	    throw F.newLlavaException("_%defGenInternal expected existing value to be a lambda but found: " + currentValue);
	}
	return frame.set(identifier, 
			 FR.newGenericProcedure((Lambda)currentValue));
    }
}

// End of file.
