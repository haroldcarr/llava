/**
 * Created       : 1999 Dec 25 (Sat) 02:30:22 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:41:56 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

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

