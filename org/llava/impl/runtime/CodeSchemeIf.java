/**
 * Created       : 2000 Jan 10 (Mon) 02:55:04 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:42:31 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

public class CodeSchemeIf
    extends CodeIf
{
    public CodeSchemeIf ()
    {
    }

    private CodeSchemeIf (Object source, Code testCode, Code thenCode, Code elseCode)
    {
	super(source, testCode, thenCode, elseCode);
    }

    public CodeSchemeIf newCodeSchemeIf (Object source, Code testCode, Code thenCode, Code elseCode)
    {
	return new CodeSchemeIf(source, testCode, thenCode, elseCode);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	Object test = engine.run(testCode, frame);
	Boolean tb;

	if (! (test instanceof Boolean) || 
	    ((Boolean)test).booleanValue() == true) {
	    // Anything not false is true;
	    return engine.tailCall(thenCode, frame);
	}
	return engine.tailCall(elseCode, frame);
    }
}

// End of file.

