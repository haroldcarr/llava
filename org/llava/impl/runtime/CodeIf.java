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
 * Last Modified : 2004 Dec 07 (Tue) 19:31:34 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.F;
import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

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
	    throw F.newLlavaException("if: expecting boolean, got: " + test);
	}

	Boolean tb = (Boolean) test;

	if (tb.booleanValue() == true) {
	    return engine.tailCall(thenCode, frame);
	} else if (tb.booleanValue() == false) {
	    return engine.tailCall(elseCode, frame);
	} else {
	    throw F.newLlavaException("if: should never happen: " + test);
	}
    }
}

// End of file.

