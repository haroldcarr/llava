/**
 * Created       : 1999 Dec 24 (Fri) 16:53:03 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:18:19 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.F;
import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

public class CodeApplicationArgs
    extends
	Code
{
    Code codeFirst;
    Code codeRest;

    public CodeApplicationArgs ()
    {
    }

    private CodeApplicationArgs (Object source, Code codeFirst, Code codeRest)
    {
	super(source);
	this.codeFirst = codeFirst;
	this.codeRest  = codeRest;
    }

    public CodeApplicationArgs newCodeApplicationArgs (Object source, Code codeFirst, Code codeRest)
    {
	return new CodeApplicationArgs(source, codeFirst, codeRest);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return F.cons(engine.run(codeFirst, frame),
		      engine.run(codeRest,  frame));
    }
}

// End of file.
