/**
 * Created       : 1999 Dec 24 (Fri) 16:53:03 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:38:40 by Harold Carr.
 */

package libLava.r1.code;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

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
