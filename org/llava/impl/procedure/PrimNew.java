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
 * Created       : 2001 Mar 05 (Mon) 21:04:59 by Harold Carr.
 * Last Modified : 2005 Feb 06 (Sun) 11:47:16 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Pair;
import org.llava.runtime.Engine;
import org.llava.runtime.Namespace;

import org.llava.impl.procedure.DI;
import org.llava.impl.procedure.PrimitiveProcedure;
import org.llava.impl.util.List;

public class PrimNew
    extends
	PrimitiveProcedure
{
    private Namespace namespace;

    public PrimNew ()
    {
    }

    public PrimNew (Namespace namespace)
    {
	this.namespace = namespace;
    }

    public PrimNew newPrimNew (Namespace namespace)
    {
	return new PrimNew(namespace);
    }

    public Object apply (Pair args, Engine engine)
    {
	String className = args.car().toString();
	className = namespace.getFullNameForClass(className);
	try {
	    return DI.newInstance(className, List.toArray((Pair)args.cdr()));
	} catch (Throwable t) {
	    throw F.newLlavaException(t);
	}
    }
}

// End of file.
