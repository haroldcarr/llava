/**
 * Created       : 1999 Dec 30 (Thu) 16:16:57 by Harold Carr.
 * Last Modified : 2000 Feb 26 (Sat) 22:53:03 by Harold Carr.
 */

package libLava.r1.procedure.primitive;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.lang.types.Symbol;
import libLava.r1.Engine;
import libLava.r1.FR1;

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
		throw FR1.newWrongNumberOfArgumentsException(getName());
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
