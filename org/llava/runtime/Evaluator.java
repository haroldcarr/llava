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
 * Created       : 1999 Dec 30 (Thu) 00:15:52 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:43 by Harold Carr.
 */

package org.llava.runtime;

import org.llava.impl.runtime.env.ActivationFrame; // REVISIT

public interface Evaluator
{
    public Evaluator newEvaluator ();

    public Object eval (Object form, EnvironmentTopLevel env);

    public Object eval (Object form, ActivationFrame frame);
}

// End of file.
