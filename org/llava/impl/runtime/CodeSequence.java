/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:39:01 by Harold Carr.
 */

package libLava.r1.code;

import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

public class CodeSequence
    extends Code
{
    private Code codeFirst;
    private Code codeRest;

    public CodeSequence ()
    {
    }

    private CodeSequence (Object source, Code codeFirst, Code codeRest)
    {
	super(source);
	this.codeFirst = codeFirst;
	this.codeRest  = codeRest;
    }

    public CodeSequence newCodeSequence (Object source, Code codeFirst, Code codeRest)
    {
	return new CodeSequence(source, codeFirst, codeRest);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	engine.run(codeFirst, frame);
	return engine.tailCall(codeRest, frame);
    }
}

// End of file.

