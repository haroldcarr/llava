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
 * Last Modified : 2004 Sep 03 (Fri) 15:33:11 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.runtime.LlavaRuntime;

public class LlavaRuntimeImpl
    implements
	LlavaRuntime
{
    private EnvironmentTopLevel environmentTopLevel;
    private Evaluator           evaluator;

    public LlavaRuntimeImpl ()
    {
    }

    private LlavaRuntimeImpl (EnvironmentTopLevel environmentTopLevel,
			 Evaluator           evaluator)
    {
	this.environmentTopLevel = environmentTopLevel;
	this.evaluator           = evaluator;
    }

    public LlavaRuntime newLlavaRuntime(EnvironmentTopLevel environmentTopLevel,
				      Evaluator           evaluator)
    {
	return new LlavaRuntimeImpl(environmentTopLevel, evaluator);
    }
	
    public EnvironmentTopLevel getEnvironment ()
    {
	return environmentTopLevel;
    }

    public Evaluator getEvaluator ()
    {
	return evaluator;
    }
}

// End of file.
