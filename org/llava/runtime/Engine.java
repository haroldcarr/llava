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
 * Created       : 1999 Dec 23 (Thu) 03:48:44 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:33:04 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.lang.types.Pair;
import org.llava.runtime.Evaluator;
import org.llava.impl.runtime.code.Code;
import org.llava.impl.runtime.env.ActivationFrame;

public interface Engine
    extends
	Evaluator

{
    public Object tailCall (Code code, ActivationFrame frame);

    public Object run ();

    public Object run (Code code, ActivationFrame frame);

    public Object apply (Object x, Pair args);

    public ActivationFrame getFrame ();
}

// End of file.
