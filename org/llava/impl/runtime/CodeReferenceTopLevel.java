/**
 * Created       : 1999 Dec 26 (Sun) 17:36:17 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:42:27 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lava.lang.types.Symbol;
import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

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

