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
 * Created       : 1999 Dec 25 (Sat) 01:44:42 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:32:21 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

public class CodeReference
    extends Code
{
    private int slot;

    public CodeReference ()
    {
    }

    private CodeReference (Object source, int slot)
    {
	super(source);
	this.slot = slot;
    }

    public CodeReference newCodeReference (Object source, int slot)
    {
	return new CodeReference(source, slot);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return frame.get(slot);
    }
}

// End of file.

