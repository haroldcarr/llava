/**
 * Created       : 1999 Dec 26 (Sun) 17:36:17 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:58 by Harold Carr.
 */

package libLava.r1.code;

import lava.lang.types.Symbol;
import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

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

