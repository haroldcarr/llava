/**
 * Created       : 1999 Dec 23 (Thu) 18:33:09 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:44:13 by Harold Carr.
 */

package lavaProfile.runtime.procedure;

import lava.lang.types.Procedure;
import lavaProfile.runtime.code.Code;
import lavaProfile.runtime.env.ActivationFrame;

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
