/**
 * Created       : 2000 Jan 11 (Tue) 20:42:14 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:58:37 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import lava.F;
import lava.Repl;
import lava.lang.types.Pair;
import libLava.r1.FR1;
import libLava.r1.Engine;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.generic.GenericProcedureImpl;

public class PrimLoad
    extends
	GenericProcedureImpl
{
    private Repl repl;

    public PrimLoad ()
    {
    }

    public PrimLoad (Repl repl)
    {
	this.repl = repl;
    }

    public PrimLoad newPrimLoad (Repl repl)
    {
	return new PrimLoad(repl);
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException uie) {
	    checkNumArgs(args, 1);
	    String filename = (String)args.car();
	    InputStreamReader isr = null;
	    try {
		isr = new InputStreamReader(new FileInputStream(filename));
		return repl.readCompileEvalUntilEOF(isr);
	    } catch (FileNotFoundException e) {
		throw F.newLavaException(e);
	    } finally {
		try {
		    if (isr != null) isr.close();
		} catch (java.io.IOException e) {
		    throw F.newLavaException(e);
		}
	    }
	}
    }
}

// End of file.
