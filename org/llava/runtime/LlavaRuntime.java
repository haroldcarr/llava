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
 * Created       : 2000 Jan 07 (Fri) 18:17:59 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:44 by Harold Carr.
 */

package org.llava.runtime;

import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;

/**
 * This interface exists so a representation of the runtime system
 * may be passed to the compiler for use during macro handling.
 */

public interface LlavaRuntime
{
    public LlavaRuntime newLlavaRuntime(EnvironmentTopLevel environmentTopLevel,
				      Evaluator           evaluator);

    public EnvironmentTopLevel getEnvironment ();

    public Evaluator getEvaluator ();
}

// End of file.
