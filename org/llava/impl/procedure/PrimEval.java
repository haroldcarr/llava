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
 * Created       : 2000 Jan 08 (Sat) 16:45:49 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:02 by Harold Carr.
 */

package org.llava.impl.runtime.procedure.primitive.llava;

import org.llava.lang.types.Pair;
import org.llava.compiler.Compiler;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.impl.runtime.FR;
import org.llava.runtime.LlavaRuntime;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.env.ActivationFrame;
import org.llava.impl.runtime.exceptions.UndefinedIdException;
import org.llava.impl.runtime.procedure.generic.GenericProcedureImpl;

public class PrimEval
    extends
	GenericProcedureImpl
{
    private EnvironmentTopLevel environmentTopLevel;
    private Evaluator           evaluator;
    private Compiler            compiler;
    private LlavaRuntime         runtime;

    public PrimEval ()
    {
    }

    public PrimEval (EnvironmentTopLevel environmentTopLevel,
		     Evaluator           evaluator,
		     Compiler            compiler)
    {
	this.environmentTopLevel = environmentTopLevel;
	this.evaluator = evaluator;
	this.compiler = compiler;
	this.runtime = FR.newLlavaRuntime(environmentTopLevel, evaluator);
    }

    public PrimEval newPrimEval (EnvironmentTopLevel environmentTopLevel,
				 Evaluator           evaluator,
				 Compiler            compiler)
    {
	return new PrimEval(environmentTopLevel, evaluator, compiler);
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    return super.apply(args, engine);
	} catch (UndefinedIdException e) {
	    int argLen;

	    // REVISIT - explicit environment
	    if (args == null || (argLen = args.length()) > 2) {
		throw FR.newWrongNumberOfArgumentsException(name);
	    } else if (argLen == 1) {
		return evaluator.eval(compiler.compile(args.car(), runtime),
				      environmentTopLevel);
	    } 
	    return evaluator.eval(compiler.compile(args.car(), runtime),
				  (ActivationFrame)args.cadr());
	}
    }
}

// End of file.
