/**
 * Created       : 1999 Dec 25 (Sat) 01:44:42 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:42:21 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

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

