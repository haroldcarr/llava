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
 * Last Modified : 2004 Sep 03 (Fri) 15:33:15 by Harold Carr.
 */

package org.llava.impl.runtime.code;

import org.llava.impl.F;
import org.llava.impl.runtime.env.ActivationFrame;
import org.llava.impl.runtime.Engine;

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
