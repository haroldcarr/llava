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
 * Created       : 1999 Dec 30 (Thu) 16:16:57 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:34:06 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive;

import org.llava.lang.types.Pair;
import org.llava.lang.types.Procedure;
import org.llava.lang.types.Symbol;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.FR;

public abstract class PrimitiveProcedure
    implements
	Procedure
{
    protected String             name;

    /* REVISIT
    protected PrimitiveProcedure ()
    {
    }
    */

    abstract public Object apply (Pair args, Engine engine);

    // Duplicated in GenericProcedureImpl.
    protected void checkNumArgs (Pair args, int n)
    {
	if (((n == 0) && (args != null)) ||
	    ((n != 0) && ((args == null) || (args.length() != n))))
        {
		throw FR.newWrongNumberOfArgumentsException(getName());
	}
    }

    public String getName ()
    {
	return name;
    }

    public String setName (String name)
    {
	return this.name = name;
    }

    public String toString ()
    {
	// REVISIT - duplicate of code in LambdaImpl.
	return "{" + getClass().getName() + " " + name + "}";
    }
}

// End of file.
