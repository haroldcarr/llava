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
 * Created       : 1999 Dec 24 (Fri) 16:53:03 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:30:47 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.F;
import org.llava.Pair;
import org.llava.Procedure;
import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

public class CodeApplication
    extends
	Code
{
    private Code procCode;
    private Code argsCode;

    public CodeApplication ()
    {
    }

    private CodeApplication (Object source, Code procCode, Code argCode)
    {
	super(source);
	this.procCode = procCode;
	this.argsCode  = argCode;
    }

    public CodeApplication newCodeApplication (Object source, Code procCode, Code argCode)
    {
	return new CodeApplication(source, procCode, argCode);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	Object proc = engine.run(procCode, frame);
	if (!(proc instanceof Procedure)) {
	    throw F.newLlavaException("procedure expected but got : " + proc);
	}
	return ((Procedure)proc).apply((Pair)engine.run(argsCode, frame),
				       engine);
    }
}

// End of file.
