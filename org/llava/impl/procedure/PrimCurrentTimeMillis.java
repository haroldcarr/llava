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
 * Created       : 2000 Jan 18 (Tue) 04:24:10 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:31:26 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;

public class PrimCurrentTimeMillis
    extends
	PrimitiveProcedure
{
    public PrimCurrentTimeMillis ()
    {
    }

    public PrimCurrentTimeMillis newPrimCurrentTimeMillis ()
    {
	return new PrimCurrentTimeMillis();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 0);
	return F.newLong(System.currentTimeMillis());
    }
}

// End of file.
