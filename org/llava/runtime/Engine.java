/**
 * Created       : 1999 Dec 23 (Thu) 03:48:44 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:08 by Harold Carr.
 */

package libLava.r1;

import lava.lang.types.Pair;
import libLava.rt.Evaluator;
import libLava.r1.code.Code;
import libLava.r1.env.ActivationFrame;

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
