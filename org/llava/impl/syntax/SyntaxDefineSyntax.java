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
 * Created       : 2000 Jan 07 (Fri) 06:39:08 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:17 by Harold Carr.
 */

package org.llava.impl.runtime.syntax;

import org.llava.lang.types.Pair;
import org.llava.lang.types.Procedure;
import org.llava.lang.types.Symbol;
import org.llava.compiler.Compiler;
import org.llava.impl.compiler.EnvironmentLexical;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.impl.runtime.FR;
import org.llava.runtime.LlavaRuntime;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.Engine;
import org.llava.impl.runtime.code.Code;
import org.llava.impl.runtime.env.ActivationFrame;

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
			 LlavaRuntime        runtime)
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
