/**
 * Created       : 2000 Jan 07 (Fri) 18:17:59 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:34 by Harold Carr.
 */

package libLava.r1;

import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.rt.LavaRuntime;

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
