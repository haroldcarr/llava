/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:42:14 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;

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
	return FR.newLambda(numRequired, isDotted, codeSequence, frame);
    }
}

// End of file.

