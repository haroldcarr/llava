/**
 * Created       : 2000 Jan 07 (Fri) 06:39:08 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:50:32 by Harold Carr.
 */

package lavaProfile.runtime.syntax;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.lang.types.Symbol;
import lava.compiler.Compiler;
import lavaProfile.compiler.EnvironmentLexical;
import lava.runtime.EnvironmentTopLevel;
import lavaProfile.runtime.FR;
import lava.runtime.LavaRuntime;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.code.Code;
import lavaProfile.runtime.env.ActivationFrame;

public class SyntaxDefineSyntax
    extends 
	Syntax
{
    public SyntaxDefineSyntax ()
    {
	super("define-syntax");
    }

    public SyntaxDefineSyntax newSyntaxDefineSyntax ()
    {
	return new SyntaxDefineSyntax();
    }

    public Code compile (Compiler           compiler,
			 Pair               x,
			 EnvironmentLexical e, 
			 LavaRuntime        runtime)
    {
	Object macrobody = x.caddr();
	Code code = compiler.compile(macrobody, e, runtime);

	// REVISIT - relationship between activation frame and top.
	ActivationFrame topFrame 
	    = FR.newActivationFrame(runtime.getEnvironment());
	Procedure  macrolambda = 
	    (Procedure) ((Engine)runtime.getEvaluator()).run(code, topFrame);

	Symbol nameAsSymbol = (Symbol) x.cadr();
	String nameAsString = nameAsSymbol.toString();

	macrolambda.setName(nameAsString); // for user debugging

	// REVISIT - a separate macro environment?
	UserSyntax syntax = new UserSyntax(nameAsString, macrolambda);
	runtime.getEnvironment().set(nameAsSymbol, syntax);

	// REVISIT - return code to set (for embedded - but then need comp).
	// See test.lva for more comments on this.
	return compiler.compile(null, e, runtime);
    }
}

// End of file.
