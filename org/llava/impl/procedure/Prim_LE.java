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
 * Created       : 2002 Jan 12 (Sat) 12:01:47 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:08:03 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;

public class Prim_LE
    extends
	PrimitiveProcedure
{
    public Prim_LE ()
    {
    }

    public Prim_LE newPrim_LE ()
    {
	return new Prim_LE();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOpBoolean(Prim_NumHelp.LE, args);
    }
}

// End of file.
