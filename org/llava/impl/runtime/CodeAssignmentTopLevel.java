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
 * Created       : 1999 Dec 26 (Sun) 17:21:38 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:31:29 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.Symbol;
import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

public class CodeAssignmentTopLevel
    extends Code
{
    private Symbol identifier;
    private Code   codeValue;

    public CodeAssignmentTopLevel ()
    {
    }

    private CodeAssignmentTopLevel (Object source, Symbol identifier, Code codeValue)
    {
	super(source);
	this.identifier = identifier;
	this.codeValue = codeValue;
    }

    public CodeAssignmentTopLevel newCodeAssignmentTopLevel (Object source, Symbol identifier, Code codeValue)
    {
	return new CodeAssignmentTopLevel(source, identifier, codeValue);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return frame.set(identifier, engine.run(codeValue, frame));
    }
}

// End of file.

