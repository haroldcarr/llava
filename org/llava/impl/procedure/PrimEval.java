/**
 * Created       : 2000 Jan 08 (Sat) 16:45:49 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:26:50 by Harold Carr.
 */

package lavaProfile.runtime.procedure.primitive.lava;

import lava.lang.types.Pair;
import lava.compiler.Compiler;
import lava.runtime.EnvironmentTopLevel;
import lava.runtime.Evaluator;
import lavaProfile.runtime.FR;
import lava.runtime.LavaRuntime;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.exceptions.UndefinedIdException;
import lavaProfile.runtime.procedure.generic.GenericProcedureImpl;

public class PrimEval
    extends
	GenericProcedureImpl
{
    private EnvironmentTopLevel environmentTopLevel;
    private Evaluator           evaluator;
    private Compiler            compiler;
    private LavaRuntime         runtime;

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
	this.runtime = FR.newLavaRuntime(environmentTopLevel, evaluator);
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
