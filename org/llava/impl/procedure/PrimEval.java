/**
 * Created       : 2000 Jan 08 (Sat) 16:45:49 by Harold Carr.
 * Last Modified : 2000 Feb 20 (Sun) 17:57:03 by Harold Carr.
 */

package libLava.r1.procedure.primitive.lava;

import lava.lang.types.Pair;
import libLava.co.Compiler;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.rt.FR;
import libLava.r1.FR1;
import libLava.rt.LavaRuntime;
import libLava.r1.Engine;
import libLava.r1.env.ActivationFrame;
import libLava.r1.exceptions.UndefinedIdException;
import libLava.r1.procedure.generic.GenericProcedureImpl;

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
		throw FR1.newWrongNumberOfArgumentsException(name);
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
