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
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:34:30 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class Prim_EQ
    extends
	PrimitiveProcedure
{
    public Prim_EQ ()
    {
    }

    public Prim_EQ newPrim_EQ ()
    {
	return new Prim_EQ();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOpBoolean(Prim_NumHelp.EQ, args);
    }
}

// End of file.
