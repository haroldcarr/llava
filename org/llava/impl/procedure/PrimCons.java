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
 * Last Modified : 2004 Dec 08 (Wed) 10:31:14 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.UndefinedIdException;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.GenericProcedureImpl;

public class PrimCons
    extends
	GenericProcedureImpl
{
    public PrimCons ()
    {
    }

    public PrimCons newPrimCons ()
    {
	return new PrimCons();
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    checkNumArgs(args, 2);
	    return F.cons(args.first(), args.second());
	}
    }
}

// End of file.
