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
 * Created       : 1999 Dec 30 (Thu) 19:28:20 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:03:42 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.UndefinedIdException;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;
import org.llava.impl.util.List;

public class PrimList_
    extends
	PrimitiveProcedure
{
    public PrimList_ ()
    {
    }

    public PrimList_ newPrimList_ ()
    {
	return new PrimList_();
    }

    public Object apply (Pair args, Engine engine)
    {
	return args;
    }
}

// End of file.
