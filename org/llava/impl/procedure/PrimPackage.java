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
 * Created       : 2001 Mar 05 (Mon) 10:55:19 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:34:16 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.java;

import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.env.Namespace;
import org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure;

public class PrimPackage
    extends
	PrimitiveProcedure
{
    private Namespace namespace;

    public PrimPackage ()
    {
    }

    public PrimPackage (Namespace namespace)
    {
	this.namespace = namespace;
    }

    public PrimPackage newPrimPackage (Namespace namespace)
    {
	return new PrimPackage(namespace);
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	return namespace._package((Symbol)args.first());
    }
}

// End of file.
