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
 * Last Modified : 2004 Dec 08 (Wed) 10:36:23 by Harold Carr.
 */

package org.llava.impl.syntax;

import org.llava.F;
import org.llava.Pair;
import org.llava.Procedure;
import org.llava.Symbol;
import org.llava.compiler.Compiler;
import org.llava.compiler.EnvironmentLexical;
import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.LlavaRuntime;

import org.llava.impl.runtime.Code;

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
	    = F.newActivationFrame(runtime.getEnvironment());
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
