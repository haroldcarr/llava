/**
 * Created       : 2000 Jan 07 (Fri) 18:17:59 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:41:37 by Harold Carr.
 */

package lavaProfile.runtime;

import lava.runtime.EnvironmentTopLevel;
import lava.runtime.Evaluator;
import lava.runtime.LavaRuntime;

public class LavaRuntimeImpl
    implements
	LavaRuntime
{
    private EnvironmentTopLevel environmentTopLevel;
    private Evaluator           evaluator;

    public LavaRuntimeImpl ()
    {
    }

    private LavaRuntimeImpl (EnvironmentTopLevel environmentTopLevel,
			 Evaluator           evaluator)
    {
	this.environmentTopLevel = environmentTopLevel;
	this.evaluator           = evaluator;
    }

    public LavaRuntime newLavaRuntime(EnvironmentTopLevel environmentTopLevel,
				      Evaluator           evaluator)
    {
	return new LavaRuntimeImpl(environmentTopLevel, evaluator);
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
