/**
 * Created       : 1999 Dec 30 (Thu) 00:15:52 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:05 by Harold Carr.
 */

package libLava.rt;

import libLava.r1.env.ActivationFrame; // REVISIT

public interface Evaluator
{
    public Evaluator newEvaluator ();

    public Object eval (Object form, EnvironmentTopLevel env);

    public Object eval (Object form, ActivationFrame frame);
}

// End of file.
