/**
 * Created       : 1999 Dec 25 (Sat) 01:47:11 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:42:24 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

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

