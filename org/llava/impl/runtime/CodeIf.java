/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:18:20 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.F;
import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

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

