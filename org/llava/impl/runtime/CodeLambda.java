/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/

/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:25:35 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.F;
import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

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
	return F.newLambda(numRequired, isDotted, codeSequence, frame);
    }
}

// End of file.

