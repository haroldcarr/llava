/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/

/**
 * Created       : 2000 Jan 11 (Tue) 20:42:14 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:32:28 by Harold Carr.
 */

package org.llava.impl.procedure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.llava.F;
import org.llava.Pair;
import org.llava.Repl;
import org.llava.UndefinedIdException;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.GenericProcedureImpl;

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
		throw F.newLlavaException(e);
	    } finally {
		try {
		    if (isr != null) isr.close();
		} catch (java.io.IOException e) {
		    throw F.newLlavaException(e);
		}
	    }
	}
    }
}

// End of file.
