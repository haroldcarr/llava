/**
 * Created       : 2000 Jan 07 (Fri) 06:18:21 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:41:12 by Harold Carr.
 */

package libLava.r1.syntax;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import libLava.co.Compiler;
import libLava.c1.EnvironmentLexical;
import libLava.rt.LavaRuntime;
import libLava.r1.Engine;
import libLava.r1.code.Code;

public class UserSyntax
    extends 
	Syntax 
{
    private Procedure macroLambda;

    UserSyntax (String name, Procedure macroLambda)
    {
	super(name);
	this.macroLambda = macroLambda; 
    }
    
    public Code compile (Compiler           compiler,
			 Pair               y,
			 EnvironmentLexical e1,
			 LavaRuntime        runtime)
    {
	return 
	    compiler.compile(
                ((Engine)runtime.getEvaluator()).apply(macroLambda, 
						       (Pair)y.rest()),
		e1, 
		runtime 
		            );
    }

    public Object apply (Pair args, Engine engine)
    {
	return macroLambda.apply(((Pair)args.rest()), engine);
    }

    public Procedure getMacroLambda ()
    {
	return macroLambda;
    }
}
      
// End of file.
