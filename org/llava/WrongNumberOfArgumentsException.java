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
 * Created       : 2000 Jan 08 (Sat) 16:27:35 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 18:50:45 by Harold Carr.
 */

package org.llava;

import org.llava.LlavaException;
import org.llava.Symbol;

public class WrongNumberOfArgumentsException
    extends
	LlavaException
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
