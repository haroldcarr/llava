/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/

//
// Created       : 2003 Nov 20 (Thu) 16:38:00 by Harold Carr.
// Last Modified : 2004 Dec 07 (Tue) 19:07:21 by Harold Carr.
//

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;

public class Prim_BitAnd
    extends
	PrimitiveProcedure
{
    public Prim_BitAnd ()
    {
    }

    public Prim_BitAnd newPrim_BitAnd ()
    {
	return new Prim_BitAnd();
    }

    public Object apply (Pair args, Engine engine)
    {
	return Prim_NumHelp.doOp(Prim_NumHelp.BITAND, 1, args);
    }
}

// End of file.

