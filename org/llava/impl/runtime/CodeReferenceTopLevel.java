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
 * Last Modified : 2004 Dec 07 (Tue) 19:50:22 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.Symbol;
import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

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

