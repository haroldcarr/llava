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
 * Created       : 1999 Dec 26 (Sun) 17:36:17 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:33:33 by Harold Carr.
 */

package org.llava.impl.runtime.code;

import org.llava.lang.types.Symbol;
import org.llava.impl.runtime.env.ActivationFrame;
import org.llava.impl.runtime.Engine;

public class CodeReferenceTopLevel
    extends Code
{

    public CodeReferenceTopLevel ()
    {
    }

    private CodeReferenceTopLevel (Object source)
    {
	super(source);
    }

    public CodeReferenceTopLevel newCodeReferenceTopLevel (Object source)
    {
	return new CodeReferenceTopLevel(source);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return frame.get((Symbol)getSource());
    }
}

// End of file.

