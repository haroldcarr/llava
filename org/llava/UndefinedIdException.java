/**
 * Created       : 1999 Dec 25 (Sat) 19:49:18 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:39:30 by Harold Carr.
 */

package libLava.r1.exceptions;

import lava.lang.exceptions.LavaException;
import lava.lang.types.Symbol;

public class UndefinedIdException
    extends
	LavaException
{
    public UndefinedIdException (Symbol symbol)
    {
	this(symbol.toString());
    }

    public UndefinedIdException (String name)
    {
	super("Undefined top level variable: " + name);
    }
}


// End of file.
