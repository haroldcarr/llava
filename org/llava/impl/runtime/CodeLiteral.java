/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:42:17 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

public class CodeLiteral
    extends Code
{
    private Object literal;

    private static CodeLiteral nullLiteral = new CodeLiteral(null, null);

    public CodeLiteral ()
    {
    }

    private CodeLiteral (Object source, Object literal)
    {
	super(source);
	this.literal = literal;
    }

    public CodeLiteral newCodeLiteral (Object source, Object literal)
    {
	if (source == null && literal == null) {
	    return nullLiteral;
	}
	return new CodeLiteral(source, literal);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	return literal;
    }
}

// End of file.

