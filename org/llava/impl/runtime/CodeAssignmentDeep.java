/**
 * Created       : 1999 Dec 25 (Sat) 01:47:11 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:41:59 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

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

