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
 * Created       : 1999 Dec 30 (Thu) 19:23:33 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:13 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava.opt;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimNullP
    extends
	PrimitiveProcedure
{
    public PrimNullP ()
    {
    }

    public PrimNullP newPrimNullP ()
    {
	return new PrimNullP();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	return F.newBoolean(args.first() == null);
    }
}

// End of file.
