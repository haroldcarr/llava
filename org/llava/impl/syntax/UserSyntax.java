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
 * Created       : 2000 Jan 07 (Fri) 06:18:21 by Harold Carr.
 * Last Modified : 2005 Mar 12 (Sat) 17:02:59 by Harold Carr.
 */

package org.llava.impl.syntax;

import org.llava.Pair;
import org.llava.Procedure;
import org.llava.compiler.Compiler;
import org.llava.compiler.EnvironmentLexical;
import org.llava.runtime.LlavaRuntime;
import org.llava.runtime.Engine;
import org.llava.impl.runtime.Code;

public class UserSyntax
    extends 
	SyntaxImpl
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
			 LlavaRuntime        runtime)
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
