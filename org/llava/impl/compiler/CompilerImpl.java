/**
 * Created       : 1999 Dec 23 (Thu) 03:42:10 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:23:46 by Harold Carr.
 */

package lavaProfile.compiler;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lavaProfile.util.List;
import lava.compiler.Compiler;
import lavaProfile.compiler.EnvironmentLexical;
import lava.runtime.LavaRuntime;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.code.Code;
import lavaProfile.runtime.syntax.Syntax;

// REVISIT - coupling just for speed
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.env.ActivationFrame;

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
	instance.environment = FC.newEnvironmentLexical(null);

	instance.ASSIGNEMENT_SYMBOL    = F.newSymbol("set!");
	instance.BEGIN_SYMBOL          = F.newSymbol("begin");
	instance.DEFINE_SYMBOL         = F.newSymbol("define");
	instance.DEFGENINTERNAL_SYMBOL = F.newSymbol("_%defGenInternal");
	instance.IF_SYMBOL             = F.newSymbol("if");
	instance.LAMBDA_SYMBOL         = F.newSymbol("lambda");
	instance.QUOTE_SYMBOL          = F.newSymbol("quote");
	instance.SCHEME_IF_SYMBOL      = F.newSymbol("_if");

	return instance;
    }

    public Object compile (Object x, LavaRuntime runtime)
    {
	return compile(x, environment, runtime);
    }

    public Code compile (Object x, EnvironmentLexical env, LavaRuntime runtime)
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

    public Code compileApplication (Pair x, EnvironmentLexical env, LavaRuntime runtime)
    {
	// REVISIT: Handle builtins and closed. p 200
	return compileApplicationRegular(x, env, runtime);
    }

    public Code compileApplicationRegular (Pair x, EnvironmentLexical env, LavaRuntime runtime)
    {
	Code procCode = compile(x.car(), env, runtime);
	Code argsCode  = compileApplicationArgs((Pair)x.cdr(), env, runtime);
	return FR.newCodeApplication(x, procCode, argsCode);
    }

    public Code compileApplicationArgs (Pair args, EnvironmentLexical env, LavaRuntime runtime)
    {
	if (args == null) {
	    return compileLiteral(null, null);
	}

	boolean REVISIT = false;
	if (REVISIT) {
	    Code codeFirst = compile(args.car(), env, runtime);
	    Pair rest = (Pair)args.cdr();
	    Code codeRest = compileApplicationArgs(rest, env, runtime);
	    return FR.newCodeApplicationArgs(args, codeFirst, codeRest);
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

    public Code compileAssignment (Pair x, EnvironmentLexical env, LavaRuntime runtime)
    {
	Pair rest      = (Pair)x.cdr(); // Skip "set!".
	Symbol identifier = (Symbol)rest.car();
	Code codeValue = compile(((Pair)rest.cdr()).car(), env, runtime);

	Object kind = env.determineIfLocalVariable(identifier);

	if (kind instanceof EnvironmentLexical.LocalVariable) {
	    int level = ((EnvironmentLexical.LocalVariable)kind).getLevel();
	    int slot  = ((EnvironmentLexical.LocalVariable)kind).getSlot();
	    if (level == 0) {
		return FR.newCodeAssignment(x, slot, codeValue);
	    }
	    return FR.newCodeAssignmentDeep(x, level, slot, codeValue);
	}
	return FR.newCodeAssignmentTopLevel(x, identifier, codeValue);
    }

    public Code compileDefine (Pair x, EnvironmentLexical env, LavaRuntime runtime)
    {
	Pair rest      = (Pair)x.cdr(); // Skip "define"
	Object nameOrNameAndParms = rest.car();
	Symbol identifier;
	if (nameOrNameAndParms instanceof Symbol) {
	    identifier = (Symbol) nameOrNameAndParms;
	    Code codeValue = compile(((Pair)rest.cdr()).car(), env, runtime);
	    return FR.newCodeAssignmentTopLevel(x, identifier, codeValue);
	} else {
	    // Compiles it as:
	    // (begin (define x (lambda () ...)) (_%defGenInternal x))
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

    public Code compileIf (boolean isScheme, Pair x, EnvironmentLexical env, LavaRuntime runtime)
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
	    return FR.newCodeSchemeIf(x, testCode, thenCode, elseCode);
	}
	return FR.newCodeIf(x, testCode, thenCode, elseCode);
    }

    public Code compileLambda (Pair x, EnvironmentLexical env, LavaRuntime runtime)
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
	return FR.newCodeLambda(x, numRequired, isDotted, codeSequence);
    }

    public Code compileLiteral (Object source, Object x)
    {
	return FR.newCodeLiteral(source, x);
    }

    public Code compileReference (Symbol x, EnvironmentLexical env, LavaRuntime runtime)
    {
	Object kind = env.determineIfLocalVariable(x);
	if (kind instanceof EnvironmentLexical.LocalVariable) {
	    int level = ((EnvironmentLexical.LocalVariable)kind).getLevel();
	    int slot  = ((EnvironmentLexical.LocalVariable)kind).getSlot();
	    if (level == 0) {
		return FR.newCodeReference(x, slot);
	    }
	    return FR.newCodeReferenceDeep(x, level, slot);
	}
	return FR.newCodeReferenceTopLevel(x);
    }

    public Code compileSequence (Pair x, EnvironmentLexical env, LavaRuntime runtime)
    {
	return compileSequenceAux((Pair)x.cdr(), env, runtime);
    }

    public Code compileSequenceAux (Pair exprs, EnvironmentLexical env, LavaRuntime runtime)
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
	    return FR.newCodeSequence(exprs, codeFirst, codeRest);
	}
    }
}

// End of file.
