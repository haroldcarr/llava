/**
 * Created       : 1999 Dec 30 (Thu) 06:34:36 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 08:56:20 by Harold Carr.
 */

package lavaProfile.runtime;

import lavaProfile.F;
import lava.Repl;
import lava.lang.types.Pair; // For derived.
import lava.lang.types.Symbol;
import lava.compiler.Compiler;
import lava.runtime.EnvironmentTopLevel;
import lava.runtime.EnvTopLevelInit;
import lava.runtime.EnvironmentTopLevel;
import lava.runtime.Evaluator;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.env.Namespace;
import lavaProfile.runtime.env.NamespaceImpl; // REVISIT - move into interface
import lavaProfile.runtime.env.UndefinedIdHandlerImpl; // REVISIT
import lavaProfile.runtime.procedure.generic.GenericProcedure;
import lavaProfile.runtime.procedure.primitive.java.PrimNewPrim;


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
	// All unbound top level variables become (lazy) generic.
	//

	// REVISIT - via Factory
	env.setUndefinedIdHandler
	    (new lavaProfile.runtime.env.UndefinedIdHandlerImpl() {// REVISIT factory
		    public Object handle(EnvironmentTopLevel env, Symbol id) {
			GenericProcedure gp = FR.newGenericProcedure();
			// REVISIT - performance?
			if (env instanceof Namespace &&
			    // REVISIT: NamespaceImpl
			    ((NamespaceImpl)env).isDottedP(id.toString())) 
			{
			    ;
			} else {
			    // REVISIT - maybe do not set

			    // REVISIT - cannot set when using package
			    // system.  Setting causes slots to have
			    // values in packages rather than being
			    // undefined so it falls through to
			    // next package of ref list.

			    // REVISIT - but if you do not set
			    // it breaks - still need to investigate.
			    // For now there is a workaround in
			    // NamespaceImpl.  See WORKAROUND/SET/UNDEFINED.
			    env.set(id, gp);
			}
			return gp;
		    }
		}
	     );

	//
	// Install primitive Java procedures.
	//

	PrimNewPrim primNewPrim =   FR.newPrimNewPrim();

	if (env instanceof Namespace) {
	    set(env, "_%import",  FR.newPrimImport((Namespace)env));
	    set(env, "_%new",     primNewPrim);
	    set(env, "new",       FR.newPrimNew((Namespace)env, primNewPrim));
	    set(env, "_%package", FR.newPrimPackage((Namespace)env));
	} else {
	    set(env, "new",       primNewPrim);
	}

	set(env, "eq?",             FR.newPrimEqP());
	set(env, "equal?",          FR.newPrimEqualP());
	set(env, "_%synchronized",  FR.newPrimSynchronized());
	set(env, "throw",           FR.newPrimThrow());
	set(env, "_%try",           FR.newPrimTryCatchFinally());

	set(env, "/",               FR.newPrim_Divide());
	set(env, "=",               FR.newPrim_EQ());
	set(env, ">=",              FR.newPrim_GE());
	set(env, ">",               FR.newPrim_GT());
	set(env, "<",               FR.newPrim_LT());
	set(env, "<=",              FR.newPrim_LE());
	set(env, "-",               FR.newPrim_Minus());
	set(env, "+",               FR.newPrim_Plus());
	set(env, "*",               FR.newPrim_Times());
	set(env, "modulo",          FR.newPrim_Modulo());

	// opt (optional or optimized)
	// REVISIT - put this in different package?
	set(env, "currentTimeMillis",FR.newPrimCurrentTimeMillis());
	set(env, "instanceof",      FR.newPrimInstanceof());
	set(env, "not",             FR.newPrimNot());

	//
	// Install primitive Lava procedures.
	//

	set(env, "append",          FR.newPrimAppend());
	set(env, "apply",           FR.newPrimApply()); // REVISIT .apply
	set(env, "call/cc",         FR.newPrimCallCC());
	set(env, "cons",            FR.newPrimCons());
	set(env, "_%defGenInternal",FR.newPrimDefGenInternal());
	set(env, "eval",            FR.newPrimEval(env, 
						    repl.getEvaluator(),
						    repl.getCompiler()));
	set(env, "_f",              FR.newPrimField());
	set(env, "_i",              FR.newPrimInvoke());
	set(env, "length",          FR.newPrimLength());
	set(env, "list",            FR.newPrimList());
	set(env, "_list",           FR.newPrimList_());
	set(env, "load",            FR.newPrimLoad(repl));
	set(env, "new-thread",      FR.newPrimNewThread());
	set(env, "_sf",             FR.newPrimStaticField());
	set(env, "_si",             FR.newPrimStaticInvoke());
	set(env, "string-append",   FR.newPrimStringAppend());

	// opt (optional or optimized)
	set(env, "map",             FR.newPrimMap());
	set(env, "null?",           FR.newPrimNullP());
	set(env, "pair?",           FR.newPrimPairP());
	set(env, "string->symbol",  FR.newPrimString2Symbol());
	set(env, "symbol?",         FR.newPrimSymbolP());


	//
	// N.B.: Order is VERY important from here on out.
	//

	set(env, "define-syntax",   FR.newSyntaxDefineSyntax());

	//
	// Seal the root namespace.
	//

	/* REVISIT - this is put in Lava.main for now.
	if (env instanceof Namespace) {
	    ((Namespace)env).setIsSealed(true);
	}
	*/
    }

    public void loadDerived ()
    {
	installMiscDerived(repl);

	if (false) { // REVISIT - decide from property
	    // N.B.: This order *MUST* be kept in sync with order in
	    //       derived/GNUmakefile
	    loadDerived(repl,          FR.newDerivedJavaFirst());
	    loadDerived(repl,          FR.newDerivedScmTypeProcs());
	    loadDerived(repl,          FR.newDerivedLavaControl());
	    loadDerived(repl,          FR.newDerivedLavaConditional());
	    loadDerived(repl,          FR.newDerivedLavaQuasiquote());
	    loadDerived(repl,          FR.newDerivedLavaBinding());
	    loadDerived(repl,          FR.newDerivedLavaCase());
	    loadDerived(repl,          FR.newDerivedLavaIteration());
	    loadDerived(repl,          FR.newDerivedLavaMember());
	    loadDerived(repl,          FR.newDerivedJavaTry());
	    loadDerived(repl,          FR.newDerivedJavaSecond());
	    loadDerived(repl,          FR.newRequireProvide());
	    loadDerived(repl,          FR.newDerivedTest());
	    loadDerived(repl,          FR.newImport());
	} else {
	    loadDerived(repl,          FR.newDerivedAll());
	}
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
"  (throw (_si 'newLavaException 'lavaProfile.F msg)))"
                                );


	    repl.readCompileEval(
"(define (display msg)" +
"  (print (_sf 'out 'java.lang.System) " +
"	 (if (null? msg)" +
"	     \"null\"" +
"	     (toString msg)))" +
"  null)"
                                );


	    repl.readCompileEval(
"(define (newline)" +
"  (println (_sf 'out 'java.lang.System))" +
"  null)"
                                );


	    repl.readCompileEval(
"(define _println" +
"  (lambda (x)" +
"    (println (_sf 'out 'java.lang.System) (if (null? x) \"null\" x))" +
"    x))"
                                );


	    repl.readCompileEval(
"(define _print" +
"  (lambda (x)" +
"    (print (_sf 'out 'java.lang.System) (if (null? x) \"null\" x))" +
"    x))"
                                );


	    repl.readCompileEval(
"(define-syntax _comment" +
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
