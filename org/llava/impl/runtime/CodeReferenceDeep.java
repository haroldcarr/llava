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
 * Created       : 1999 Dec 25 (Sat) 01:47:11 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:32:25 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

// REVISIT: It would be good to make this a subclass of CodeReference
//          to share the slot member, but Java does not allow muliple
//          implementation inheritance.

public class CodeReferenceDeep
    extends Code
{
    private int level;
    private int slot;

    public CodeReferenceDeep ()
    {
    }

    private CodeReferenceDeep (Object source, int level, int slot)
    {
	super(source);
	this.level = level;
	this.slot  = slot;
    }

    public CodeReferenceDeep newCodeReferenceDeep (Object source, int level, int slot)
    {
	return new CodeReferenceDeep(source, level, slot);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return frame.get(level, slot);
    }
}

// End of file.

