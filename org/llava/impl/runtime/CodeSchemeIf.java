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
 * Created       : 2000 Jan 10 (Mon) 02:55:04 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:32:34 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

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

