/**
 * Created       : 1999 Dec 25 (Sat) 01:47:11 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:46 by Harold Carr.
 */

package libLava.r1.code;

import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

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

