/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:51 by Harold Carr.
 */

package libLava.r1.code;

import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;
import libLava.r1.FR1;

public class CodeLambda
    extends Code
{
    private int numRequired;
    private boolean isDotted;
    private Code codeSequence;

    public CodeLambda ()
    {
    }

    private CodeLambda (Object source, 
			int numRequired,
			boolean isDotted,
			Code codeSequence)
    {
	super(source);
	this.numRequired = numRequired;
	this.isDotted = isDotted;
	this.codeSequence = codeSequence;
    }

    public CodeLambda newCodeLambda (Object source, 
				     int numRequired,
				     boolean isDotted,
				     Code codeSequence)
    {
	return new CodeLambda(source, 
			      numRequired,
			      isDotted,
			      codeSequence);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return FR1.newLambda(numRequired, isDotted, codeSequence, frame);
    }
}

// End of file.

