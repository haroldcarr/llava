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
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:32:15 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

public class CodeLiteral
    extends Code
{
    private Object literal;

    private static CodeLiteral nullLiteral = new CodeLiteral(null, null);

    public CodeLiteral ()
    {
    }

    private CodeLiteral (Object source, Object literal)
    {
	super(source);
	this.literal = literal;
    }

    public CodeLiteral newCodeLiteral (Object source, Object literal)
    {
	if (source == null && literal == null) {
	    return nullLiteral;
	}
	return new CodeLiteral(source, literal);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return literal;
    }
}

// End of file.

