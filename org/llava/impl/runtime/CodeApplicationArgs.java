/**
 * Created       : 1999 Dec 24 (Fri) 16:53:03 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:42 by Harold Carr.
 */

package libLava.r1.code;

import lava.F;
import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

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
