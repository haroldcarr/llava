/**
 * Created       : 2000 Jan 07 (Fri) 18:17:59 by Harold Carr.
 * Last Modified : 2000 Jan 10 (Mon) 18:42:31 by Harold Carr.
 */

package libLava.rt;

import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;

/**
 * This interface exists so a representation of the runtime system
 * may be passed to the compiler for use during macro handling.
 */

public interface LavaRuntime
{
    public LavaRuntime newLavaRuntime(EnvironmentTopLevel environmentTopLevel,
				      Evaluator           evaluator);

    public EnvironmentTopLevel getEnvironment ();

    public Evaluator getEvaluator ();
}

// End of file.
