/**
 * Created       : 1999 Dec 23 (Thu) 18:22:20 by Harold Carr.
 * Last Modified : 2000 Feb 18 (Fri) 18:29:26 by Harold Carr.
 */

package libLava.r1.code;

import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

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

