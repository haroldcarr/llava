/**
 * Created       : 1999 Dec 30 (Thu) 16:16:57 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:45:01 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.lang.types.Symbol;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;

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
