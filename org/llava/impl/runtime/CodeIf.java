/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:49 by Harold Carr.
 */

package libLava.r1.code;

import lava.F;
import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

public class CodeIf
    extends Code
{
    protected Code testCode;
    protected Code thenCode;
    protected Code elseCode;

    public CodeIf ()
    {
    }

    protected CodeIf (Object source, Code testCode, Code thenCode, Code elseCode)
    {
	super(source);
	this.testCode = testCode;
	this.thenCode = thenCode;
	this.elseCode = elseCode;
    }

    public CodeIf newCodeIf (Object source, Code testCode, Code thenCode, Code elseCode)
    {
	return new CodeIf(source, testCode, thenCode, elseCode);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	Object test = engine.run(testCode, frame);

	if (! (test instanceof Boolean)) {
	    throw F.newLavaException("if: expecting boolean, got: " + test);
	}

	Boolean tb = (Boolean) test;

	if (tb.booleanValue() == true) {
	    return engine.tailCall(thenCode, frame);
	} else if (tb.booleanValue() == false) {
	    return engine.tailCall(elseCode, frame);
	} else {
	    throw F.newLavaException("if: should never happen: " + test);
	}
    }
}

// End of file.

