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
 * Last Modified : 2004 Dec 07 (Tue) 19:02:40 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.Pair;
import org.llava.Symbol;
import org.llava.runtime.Engine;
import org.llava.runtime.Namespace;

import org.llava.impl.procedure.PrimitiveProcedure;

public class PrimImport
    extends
	PrimitiveProcedure
{
    private Namespace namespace;

    public PrimImport ()
    {
    }

    public PrimImport (Namespace namespace)
    {
	this.namespace = namespace;
    }

    public PrimImport newPrimImport (Namespace namespace)
    {
	return new PrimImport(namespace);
    }

    public Object apply (Pair args, Engine engine)
    {
	checkNumArgs(args, 1);
	return namespace._import((Symbol)args.first());
    }
}

// End of file.
