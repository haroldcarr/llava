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
 * Last Modified : 2004 Sep 03 (Fri) 15:34:12 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.env.Namespace;
import org.llava.impl.runtime.procedure.primitive.java.PrimNewPrim;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimNew
    extends
	PrimitiveProcedure
{
    private Namespace namespace;
    private PrimNewPrim primNewPrim;

    public PrimNew ()
    {
    }

    public PrimNew (Namespace namespace, PrimNewPrim primNewPrim)
    {
	this.namespace = namespace;
	this.primNewPrim = primNewPrim;
    }

    public PrimNew newPrimNew (Namespace namespace, PrimNewPrim primNewPrim)
    {
	return new PrimNew(namespace, primNewPrim);
    }

    public Object apply (Pair args, Engine engine)
    {
	String className = args.car().toString();
	className = namespace.getFullNameForClass(className);
	return primNewPrim.apply(F.cons(className, (Pair)args.cdr()),  engine);
    }
}

// End of file.
