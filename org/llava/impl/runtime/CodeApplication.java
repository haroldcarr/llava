/**
 * Created       : 1999 Dec 24 (Fri) 16:53:03 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:18:16 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

public class CodeApplication
    extends
	Code
{
    private Code procCode;
    private Code argsCode;

    public CodeApplication ()
    {
    }

    private CodeApplication (Object source, Code procCode, Code argCode)
    {
	super(source);
	this.procCode = procCode;
	this.argsCode  = argCode;
    }

    public CodeApplication newCodeApplication (Object source, Code procCode, Code argCode)
    {
	return new CodeApplication(source, procCode, argCode);
    }

    public Object run (ActivationFrame frame, Engine engine)
    {
	Object proc = engine.run(procCode, frame);
	if (!(proc instanceof Procedure)) {
	    throw F.newLavaException("procedure expected but got : " + proc);
	}
	return ((Procedure)proc).apply((Pair)engine.run(argsCode, frame),
				       engine);
    }
}

// End of file.
