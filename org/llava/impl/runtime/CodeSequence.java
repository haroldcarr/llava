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
 * Last Modified : 2004 Sep 03 (Fri) 15:33:36 by Harold Carr.
 */

package org.llava.impl.runtime.code;

import org.llava.impl.runtime.env.ActivationFrame;
import org.llava.impl.runtime.Engine;

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

