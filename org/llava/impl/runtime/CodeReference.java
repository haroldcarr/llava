/**
 * Created       : 1999 Dec 25 (Sat) 01:44:42 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:55 by Harold Carr.
 */

package libLava.r1.code;

import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

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

