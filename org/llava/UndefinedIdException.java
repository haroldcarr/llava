/**
 * Created       : 1999 Dec 25 (Sat) 19:49:18 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:43:57 by Harold Carr.
 */

package lavaProfile.runtime.exceptions;

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
