/**
 * Created       : 1999 Dec 25 (Sat) 02:30:22 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:44 by Harold Carr.
 */

package libLava.r1.code;

import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

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

