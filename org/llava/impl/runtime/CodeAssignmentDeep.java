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
 * Created       : 1999 Dec 25 (Sat) 01:47:11 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:31:24 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

// REVISIT: It would be good to make this a subclass of CodeAssignment
//          to share the slot member, but Java does not allow muliple
//          implementation inheritance.

public class CodeAssignmentDeep
    extends Code
{
    private int level;
    private int slot;
    private Code codeValue;

    public CodeAssignmentDeep ()
    {
    }

    private CodeAssignmentDeep (Object source, int level, int slot, Code codeValue)
    {
	super(source);
	this.level = level;
	this.slot  = slot;
	this.codeValue = codeValue;
    }

    public CodeAssignmentDeep newCodeAssignmentDeep (Object source, int level, int slot, Code codeValue)
    {
	return new CodeAssignmentDeep(source, level, slot, codeValue);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return frame.set(level, slot, engine.run(codeValue, frame));
    }
}

// End of file.

