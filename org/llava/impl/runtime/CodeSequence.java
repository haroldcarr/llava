/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:42:34 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

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

