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
 * Created       : 1999 Dec 25 (Sat) 02:30:22 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:31:00 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

public class CodeAssignment
    extends Code
{
    private int slot;
    private Code codeValue;

    public CodeAssignment ()
    {
    }

    private CodeAssignment (Object source, int slot, Code codeValue)
    {
	super(source);
	this.slot  = slot;
	this.codeValue = codeValue;
    }

    public CodeAssignment newCodeAssignment (Object source, int slot, Code codeValue)
    {
	return new CodeAssignment(source, slot, codeValue);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return frame.set(slot, engine.run(codeValue, frame));
    }
}

// End of file.

