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
 * Created       : 1999 Dec 30 (Thu) 06:34:36 by Harold Carr.
 * Last Modified : 2005 May 19 (Thu) 14:05:26 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.F;
import org.llava.Pair; // For derived.
import org.llava.Repl;
import org.llava.Symbol;
import org.llava.compiler.Compiler;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.EnvTopLevelInit;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.runtime.Namespace;

public class EnvTopLevelInitImpl
    implements
	EnvTopLevelInit
{

    private Repl repl;

    public EnvTopLevelInitImpl ()
    {
    }

    public EnvTopLevelInitImpl (Repl repl)
    {
	this.repl = repl;
    }

    public EnvTopLevelInit newEnvTopLevelInit (Repl repl)
    {
	return new EnvTopLevelInitImpl(repl);
    }

    public void init ()
    {
	EnvironmentTopLevel env = repl.getEnvironmentTopLevel();
	env.setRepl(repl);

	//
	// Install primitive Java procedures.
	//

	set(env, "_%boot-namespace",env);

	set(env, "_%import",        F.newPrimImport((Namespace)env));
	set(env, "new*",            F.newPrimNew());
	set(env, "_%package",       F.newPrimPackage((Namespace)env));

	set(env, "eq?",             F.newPrimEqP());
	set(env, "equal?",          F.newPrimEqualP());
	set(env, "_%synchronized",  F.newPrimSynchronized());
	set(env, "throw",           F.newPrimThrow());
	set(env, "_%try",           F.newPrimTryCatchFinally());

	set(env, "&",               F.newPrim_BitAnd());
	set(env, "|",               F.newPrim_BitOr());
	set(env, "/",               F.newPrim_Divide());
	set(env, "=",               F.newPrim_EQ());
	set(env, ">=",              F.newPrim_GE());
	set(env, ">",               F.newPrim_GT());
	set(env, "<",               F.newPrim_LT());
	set(env, "<=",              F.newPrim_LE());
	set(env, "-",               F.newPrim_Minus());
	set(env, "+",               F.newPrim_Plus());
	set(env, "*",               F.newPrim_Times());
	set(env, "modulo",          F.newPrim_Modulo());

	// opt (optional or optimized)
	// REVISIT - put this in different package?
	set(env, "current-time-millis",F.newPrimCurrentTimeMillis());
	set(env, "instanceof",      F.newPrimInstanceof((Namespace)env));
	set(env, "!",               F.newPrimNot());
	set(env, "not",             F.newGenNot());
	set(env, "_%reader",        repl.getLlavaReader());
	set(env, "_%writer",        repl.getLlavaWriter());

	//
	// Install primitive Llava procedures.
	//

	set(env, "append",          F.newPrimAppend());
	set(env, "apply",           F.newPrimApply()); // REVISIT .apply
	set(env, "call/cc",         F.newPrimCallCC());
	set(env, "call-with-current-continuation",
	    F.newPrimCallCC()); // REVISIT - use exact object as call/cc.
	set(env, "cons",            F.newPrimCons());
	set(env, "_%defGenInternal",F.newPrimDefGenInternal());
	set(env, "eval",            F.newPrimEval(env, 
						    repl.getEvaluator(),
						    repl.getCompiler()));
	set(env, "-f",              F.newPrimField());
	set(env, "-i",              F.newPrimInvoke());
	set(env, "length",          F.newPrimLength());
	set(env, "list",            F.newPrimList());
	set(env, "-list",           F.newPrimList_());
	set(env, "load",            F.newPrimLoad(repl));
	set(env, "new-thread",      F.newPrimNewThread());
	set(env, "-sf",             F.newPrimStaticField());
	set(env, "-si",             F.newPrimStaticInvoke());
	set(env, "string-append",   F.newPrimStringAppend());

	// opt (optional or optimized)
	set(env, "for-each",        F.newPrimForEach());
	set(env, "map",             F.newPrimMap());
	set(env, "null?",           F.newPrimNullP());
	set(env, "pair?",           F.newPrimPairP());
	set(env, "string->symbol",  F.newPrimString2Symbol());
	set(env, "symbol?",         F.newPrimSymbolP());


	//
	// N.B.: Order is VERY important from here on out.
	//

	set(env, "define-syntax",   F.newSyntaxDefineSyntax());

	//
	// Seal the root namespace.
	//

	/* REVISIT - this is put in Llava.main for now.
	if (env instanceof Namespace) {
	    ((Namespace)env).setIsSealed(true);
	}
	*/
    }

    public void loadDerived ()
    {
	installMiscDerived(repl);
	loadDerived(repl,          F.newDerivedAll());
    }

    private void set(EnvironmentTopLevel env, String name, Object value)
    {
	env.set(F.newSymbol(name), value);
    }

    private void loadDerived (Repl repl, Pair p) 
    {
	//System.out.println((String) p.cdr());
	repl.loadResource((String) p.car(), (String) p.cdr());
    }

    private void installMiscDerived (Repl repl)
    {
	try {

	    // You need to define each in its own rce in case
	    // you reference macros just defined.  Otherwise, if
	    // in a big begin it says undefined id.
	    // So just do each one.

	    repl.readCompileEval(
"(define (error msg)" +
"  (throw (-si 'newLlavaException 'org.llava.F msg)))"
                                );

	    repl.readCompileEval(
"(define-syntax -comment-" +
"  (lambda args '()))"
                                );

	    repl.readCompileEval(
"(define-syntax -doc-" +
"  (lambda args '()))"
                                );

	    repl.readCompileEval(
"(define-syntax -package-" +
"  (lambda args '()))"
                                );

/*
	    repl.readCompileEval(
"(define-syntax s+" +
"  (lambda args" +
"    (if (null? args)" +
"	\"\" " +
"	`(apply string-append (map toString ',args)))))"
                                );
*/

            repl.readCompileEval(
"(define s+" +
"  (lambda args" +
"    (apply string-append (map toString args))))"
                                );

	} catch (Throwable t) {
	    System.err.println("This should never happen.");
	    System.err.println(t.getMessage());
	    t.printStackTrace(System.err);
	    throw new RuntimeException(t.getMessage());
	}
    }


}

// End of file.
