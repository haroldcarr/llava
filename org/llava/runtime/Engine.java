/**
 * Created       : 1999 Dec 23 (Thu) 03:48:44 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:39:02 by Harold Carr.
 */

package lavaProfile.runtime;

import lava.lang.types.Pair;
import lava.runtime.Evaluator;
import lavaProfile.runtime.code.Code;
import lavaProfile.runtime.env.ActivationFrame;

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
