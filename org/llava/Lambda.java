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
 * Last Modified : 2004 Dec 07 (Tue) 18:48:45 by Harold Carr.
 */

package org.llava;

import org.llava.Procedure;
import org.llava.runtime.ActivationFrame;

import org.llava.impl.runtime.Code;

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
