/**
 * Created       : 1999 Dec 26 (Sun) 17:21:38 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:48 by Harold Carr.
 */

package libLava.r1.code;

import lava.lang.types.Symbol;
import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

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

