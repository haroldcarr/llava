/**
 * Created       : 1999 Dec 23 (Thu) 18:33:09 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:39:36 by Harold Carr.
 */

package libLava.r1.procedure;

import lava.lang.types.Procedure;
import libLava.r1.code.Code;
import libLava.r1.env.ActivationFrame;

public interface Lambda
    extends
	Procedure
{
    public Lambda newLambda (int numRequired,
			     boolean isDotted, 
			     Code code, 
			     ActivationFrame frame);
}

// End of file.
