/**
 * Created       : 2000 Jan 07 (Fri) 18:17:59 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:07:10 by Harold Carr.
 */

package lava.runtime;

import lava.runtime.EnvironmentTopLevel;
import lava.runtime.Evaluator;

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
