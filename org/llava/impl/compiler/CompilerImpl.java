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
 * Created       : 1999 Dec 23 (Thu) 03:42:10 by Harold Carr.
 * Last Modified : 2005 Mar 12 (Sat) 17:16:24 by Harold Carr.
 */

package org.llava.impl.compiler;

import org.llava.F;
import org.llava.Pair;
import org.llava.Symbol;
import org.llava.Syntax;
import org.llava.compiler.Compiler;
import org.llava.compiler.EnvironmentLexical;
import org.llava.runtime.LlavaRuntime;

import org.llava.impl.runtime.Code;
import org.llava.impl.util.List;

// REVISIT - coupling just for speed
import org.llava.runtime.Engine;
import org.llava.runtime.ActivationFrame;

// REVISIT: Add syntax checking

public class CompilerImpl
    implements
	Compiler
{
    private EnvironmentLexical environment;

    private Symbol ASSIGNEMENT_SYMBOL;
    private Symbol BEGIN_SYMBOL;
    private Symbol DEFINE_SYMBOL;
    private Symbol DEFGENINTERNAL_SYMBOL;
    private Symbol IF_SYMBOL;
    private Symbol LAMBDA_SYMBOL;
    private Symbol QUOTE_SYMBOL;
    private Symbol SCHEME_IF_SYMBOL;

    public CompilerImpl ()
    {
    }

    public Compiler newCompiler ()
    {
	CompilerImpl instance = new CompilerImpl();
	instance.environment = F.newEnvironmentLexical(null);

	instance.ASSIGNEMENT_SYMBOL    = F.newSymbol("set!");
	instance.BEGIN_SYMBOL          = F.newSymbol("begin");
	instance.DEFINE_SYMBOL         = F.newSymbol("define");
	instance.DEFGENINTERNAL_SYMBOL = F.newSymbol("_%defGenInternal");
	instance.IF_SYMBOL             = F.newSymbol("if");
	instance.LAMBDA_SYMBOL         = F.newSymbol("lambda");
	instance.QUOTE_SYMBOL          = F.newSymbol("quote");
	instance.SCHEME_IF_SYMBOL      = F.newSymbol("-if");

	return instance;
    }

    public Object compile (Object x, LlavaRuntime runtime)
    {
	return compile(x, environment, runtime);
    }

    public Code compile (Object x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	boolean isPair = x instanceof Pair;
	
	if (isPair) {
	    Pair xx = (Pair)x;
	    Object first = xx.car();
	    if (first instanceof Symbol) {
		Object maybeSyntax = 
		    runtime.getEnvironment().getNoError((Symbol)first);
		if (maybeSyntax instanceof Syntax) {
		    return 
			((Syntax)maybeSyntax).compile(this, xx, env, runtime);
		}
	    } 
	    // REVISIT: make data driven
	    if (first.equals(ASSIGNEMENT_SYMBOL)) {
		return compileAssignment(xx, env, runtime);
	    } else if (first.equals(BEGIN_SYMBOL)) {
		return compileSequence(xx, env, runtime);
	    } else if (first.equals(DEFINE_SYMBOL)) {
		return compileDefine(xx, env, runtime);
	    } else if (first.equals(IF_SYMBOL)) {
		return compileIf(false, xx, env, runtime);
	    } else if (first.equals(LAMBDA_SYMBOL)) {
		return compileLambda(xx, env, runtime);
	    } else if (first.equals(QUOTE_SYMBOL)) {
		return compileLiteral(xx, xx.second());
	    } else if (first.equals(SCHEME_IF_SYMBOL)) {
		return compileIf(true, xx, env, runtime);
	    } else {
		return compileApplication(xx, env, runtime);
	    }
	} else if (x instanceof Symbol) {
	    return compileReference((Symbol)x, env, runtime);
	}
	return compileLiteral(x, x);
    }

    public Code compileApplication (Pair x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	// REVISIT: Handle builtins and closed. p 200
	return compileApplicationRegular(x, env, runtime);
    }

    public Code compileApplicationRegular (Pair x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	Code procCode = compile(x.car(), env, runtime);
	Code argsCode  = compileApplicationArgs((Pair)x.cdr(), env, runtime);
	return F.newCodeApplication(x, procCode, argsCode);
    }

    public Code compileApplicationArgs (Pair args, EnvironmentLexical env, LlavaRuntime runtime)
    {
	if (args == null) {
	    return compileLiteral(null, null);
	}

	boolean REVISIT = false;
	if (REVISIT) {
	    Code codeFirst = compile(args.car(), env, runtime);
	    Pair rest = (Pair)args.cdr();
	    Code codeRest = compileApplicationArgs(rest, env, runtime);
	    return F.newCodeApplicationArgs(args, codeFirst, codeRest);
	}

	Pair allArgs = args;
	Engine engine = (Engine) runtime.getEvaluator();

	final Code code1 = compile(args.car(), env, runtime);
	args = (Pair) args.cdr();
	if (args == null)
	    return new Code(allArgs) {
		    public Object run(ActivationFrame f, Engine engine) {
			return F.cons(engine.run(code1, f), null);
		    }
		};
	
	final Code code2 = compile(args.car(), env, runtime);
	args = (Pair) args.cdr();
	if (args == null)
	    return new Code(allArgs) {
		    public Object run(ActivationFrame f, Engine engine) {
			return F.cons(engine.run(code1, f), 
				      F.cons(engine.run(code2, f), null));
		    }
		};

	final Code code3 = compile(args.car(), env, runtime);
	args = (Pair) args.cdr();
	if (args == null)
	    return new Code (allArgs) {
		    public Object run(ActivationFrame f, Engine engine) {
			return F.cons(engine.run(code1, f),
				      F.cons(engine.run(code2, f),
					     F.cons(engine.run(code3, f), null))); 
		    }
		};
    
	final Code codeRest = compileApplicationArgs(args, env, runtime);
	return new Code(args) {
		public Object run(ActivationFrame f, Engine engine) {
		    return F.cons(engine.run(code1, f),
				  F.cons(engine.run(code2, f),
					 F.cons(engine.run(code3, f), 
						engine.run(codeRest,f)))); 
		}
	    }; 
    }

    public Code compileAssignment (Pair x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	Pair rest      = (Pair)x.cdr(); // Skip "set!".
	Symbol identifier = (Symbol)rest.car();
	Code codeValue = compile(((Pair)rest.cdr()).car(), env, runtime);

	Object kind = env.determineIfLocalVariable(identifier);

	if (kind instanceof EnvironmentLexical.LocalVariable) {
	    int level = ((EnvironmentLexical.LocalVariable)kind).getLevel();
	    int slot  = ((EnvironmentLexical.LocalVariable)kind).getSlot();
	    if (level == 0) {
		return F.newCodeAssignment(x, slot, codeValue);
	    }
	    return F.newCodeAssignmentDeep(x, level, slot, codeValue);
	}
	return F.newCodeAssignmentTopLevel(x, identifier, codeValue);
    }

    public Code compileDefine (Pair x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	Pair rest      = (Pair)x.cdr(); // Skip "define"
	Object nameOrNameAndParms = rest.car();
	Symbol identifier;
	if (nameOrNameAndParms instanceof Symbol) {
	    identifier = (Symbol) nameOrNameAndParms;
	    Code codeValue = compile(((Pair)rest.cdr()).car(), env, runtime);
	    return F.newCodeAssignmentTopLevel(x, identifier, codeValue);
	} else {
	    // Compiles it as:
	    // (begin (define x (lambda () ...)) (-%defGenInternal x))
	    Pair nameAndParms = (Pair) nameOrNameAndParms;
	    identifier = (Symbol) nameAndParms.car();
	    Object parms = nameAndParms.cdr();
	    Object body = rest.cdr();
	    Pair lambda = F.cons(LAMBDA_SYMBOL, F.cons(parms, body));
	    Pair define = List.list(DEFINE_SYMBOL, identifier, lambda);
	    Pair defGenInternal = List.list(DEFGENINTERNAL_SYMBOL,
					    List.list(QUOTE_SYMBOL,
						      identifier));
	    Pair sequence = List.list(define, defGenInternal);
	    return compileSequenceAux(sequence, env, runtime);
	}
    }

    public Code compileIf (boolean isScheme, Pair x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	Pair z = (Pair)x.cdr(); // Skip "if"
	Code testCode = compile(z.first(), env, runtime);
	Code thenCode = compile(z.second(), env, runtime);
	// REVISIT: if no else return null - check r5rs
	boolean hasElse = z.cddr() != null;
	Code elseCode = 
	    hasElse ? compile(z.third(), env, runtime) :
	              compileLiteral(null, null);

	if (isScheme) {
	    return F.newCodeSchemeIf(x, testCode, thenCode, elseCode);
	}
	return F.newCodeIf(x, testCode, thenCode, elseCode);
    }

    public Code compileLambda (Pair x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	Pair rest    = (Pair)x.cdr();
	Object parms = rest.car();
	Pair body    = (Pair)rest.cdr();

	Pair parameters;
	boolean isDotted = false;
	int numRequired  = 0;

	if (parms instanceof Pair || parms == null) {
	    parameters = (Pair)parms;
	    Pair requiredArgs = null;
	    Symbol restArg = null;
	    for (Pair p = parameters; p != null; p =(Pair)p.cdr()) {
		numRequired++;
		requiredArgs = F.cons(p.car(), requiredArgs);
		if (p.cdr() != null && !(p.cdr() instanceof Pair)) {
		    isDotted = true;
		    restArg = (Symbol) p.cdr();
		    break;
		}
	    }
	    parameters 
		= isDotted ? List.reverse(F.cons(restArg, requiredArgs)) :
		             List.reverse(requiredArgs);
	} else {
	    // Handle (lambda args ...)
	    isDotted = true;
	    numRequired = 0;
	    parameters = List.list(parms);
	}

	EnvironmentLexical lambdaEnv = env.extend(parameters);
	Code codeSequence = compileSequenceAux(body, lambdaEnv, runtime);
	return F.newCodeLambda(x, numRequired, isDotted, codeSequence);
    }

    public Code compileLiteral (Object source, Object x)
    {
	return F.newCodeLiteral(source, x);
    }

    public Code compileReference (Symbol x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	Object kind = env.determineIfLocalVariable(x);
	if (kind instanceof EnvironmentLexical.LocalVariable) {
	    int level = ((EnvironmentLexical.LocalVariable)kind).getLevel();
	    int slot  = ((EnvironmentLexical.LocalVariable)kind).getSlot();
	    if (level == 0) {
		return F.newCodeReference(x, slot);
	    }
	    return F.newCodeReferenceDeep(x, level, slot);
	}
	return F.newCodeReferenceTopLevel(x);
    }

    public Code compileSequence (Pair x, EnvironmentLexical env, LlavaRuntime runtime)
    {
	return compileSequenceAux((Pair)x.cdr(), env, runtime);
    }

    public Code compileSequenceAux (Pair exprs, EnvironmentLexical env, LlavaRuntime runtime)
    {
	if (exprs == null) {
	    // (begin)
	    return compileLiteral(null, null);
	} else if (exprs.cdr() == null) {
	    // (begin e1)
	    return compile(exprs.car(), env, runtime);
	} else {
	    // (begin e1 ... en)
	    Code codeFirst =compile(exprs.car(), env, runtime);
	    Code codeRest  =compileSequenceAux((Pair)exprs.cdr(), env, runtime);
	    return F.newCodeSequence(exprs, codeFirst, codeRest);
	}
    }
}

// End of file.
