/**
 * Created       : 2000 Jan 08 (Sat) 16:27:35 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:44:03 by Harold Carr.
 */

package lavaProfile.runtime.exceptions;

import lava.lang.exceptions.LavaException;
import lava.lang.types.Symbol;

public class WrongNumberOfArgumentsException
    extends
	LavaException
{
    public WrongNumberOfArgumentsException (Symbol symbol)
    {
	this(symbol.toString());
    }

    public WrongNumberOfArgumentsException (String name)
    {
	super(name + ": Wrong number of arguments");
    }
}


// End of file.
