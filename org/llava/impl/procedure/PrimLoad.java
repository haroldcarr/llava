/**
 * Created       : 2000 Jan 11 (Tue) 20:42:14 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:19:41 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import lavaProfile.F;
import lava.Repl;
import lava.lang.types.Pair;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;

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
