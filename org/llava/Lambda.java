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
 * Created       : 1999 Dec 23 (Thu) 18:33:09 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:33:54 by Harold Carr.
 */

package org.llava.impl.runtime.procedure;

import org.llava.lang.types.Procedure;
import org.llava.impl.runtime.code.Code;
import org.llava.impl.runtime.env.ActivationFrame;

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
