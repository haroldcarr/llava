/**
 * Created       : 2000 Jan 07 (Fri) 06:18:21 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:50:38 by Harold Carr.
 */

package lavaProfile.runtime.syntax;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.compiler.Compiler;
import lavaProfile.compiler.EnvironmentLexical;
import lava.runtime.LavaRuntime;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.code.Code;

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
