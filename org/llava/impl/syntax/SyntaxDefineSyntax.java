/**
 * Created       : 2000 Jan 07 (Fri) 06:39:08 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:41:09 by Harold Carr.
 */

package libLava.r1.syntax;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.lang.types.Symbol;
import libLava.co.Compiler;
import libLava.c1.EnvironmentLexical;
import libLava.rt.EnvironmentTopLevel;
import libLava.r1.FR1;
import libLava.rt.LavaRuntime;
import libLava.r1.FR1;
import libLava.r1.Engine;
import libLava.r1.code.Code;
import libLava.r1.env.ActivationFrame;

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
	    = FR1.newActivationFrame(runtime.getEnvironment());
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
