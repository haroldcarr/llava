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
 * Created       : 2001 Jul 21 (Sat) 01:11:16 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:08:33 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;

public class Prim_Modulo
    extends
	PrimitiveProcedure
{
    public Prim_Modulo ()
    {
    }

    public Prim_Modulo newPrim_Modulo ()
    {
	return new Prim_Modulo();
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 2);
	return F.newInteger(((Integer)args.car()).intValue() 
			    % 
			    ((Integer)args.cadr()).intValue());
	    
    }
}

// End of file.

