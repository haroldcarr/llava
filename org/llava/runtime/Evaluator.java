/**
 * Created       : 1999 Dec 30 (Thu) 00:15:52 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:06:07 by Harold Carr.
 */

package lava.runtime;

import lavaProfile.runtime.env.ActivationFrame; // REVISIT

public interface Evaluator
{
    public Evaluator newEvaluator ();

    public Object eval (Object form, EnvironmentTopLevel env);

    public Object eval (Object form, ActivationFrame frame);
}

// End of file.
