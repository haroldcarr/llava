/**
 * Created       : 1999 Dec 30 (Thu) 06:34:36 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 08:56:20 by Harold Carr.
 */

package libLava.r1;

import lava.F;
import lava.Repl;
import lava.lang.types.Pair; // For derived.
import lava.lang.types.Symbol;
import libLava.co.Compiler;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.EnvTopLevelInit;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.r1.FR1;
import libLava.r1.FR1;
import libLava.r1.env.UndefinedIdHandlerImpl; // REVISIT
import libLava.r1.procedure.generic.GenericProcedure;

public class EnvTopLevelInitImpl
    implements
	EnvTopLevelInit
{
    public EnvTopLevelInitImpl ()
    {
    }

    public EnvTopLevelInit newEnvTopLevelInit ()
    {
	return new EnvTopLevelInitImpl();
    }

    public void init (Repl repl)
    {
	EnvironmentTopLevel env = repl.getEnvironmentTopLevel();

	//
	// All unbound top level variables become (lazy) generic.
	//

	// REVISIT - via Factory
	env.setUndefinedIdHandler
	    (new libLava.r1.env.UndefinedIdHandlerImpl() {// REVISIT factory
		    public Object handle(EnvironmentTopLevel env, Symbol id) {
			GenericProcedure gp = FR1.newGenericProcedure();
			// REVISIT - maybe do not set
			env.set(id, gp);
			return gp;
		    }
		}
	     );

	//
	// Install primitive Java procedures.
	//

	set(env, "eq?",             FR1.newPrimEqP());
	set(env, "equal?",          FR1.newPrimEqualP());
	set(env, "new",             FR1.newPrimNew());
	set(env, ".%synchronized",  FR1.newPrimSynchronized());
	set(env, "throw",           FR1.newPrimThrow());
	set(env, ".%try",           FR1.newPrimTryCatchFinally());

	set(env, "/",               FR1.newPrim_Divide());
	set(env, "=",               FR1.newPrim_EQ());
	set(env, ">=",              FR1.newPrim_GE());
	set(env, ">",               FR1.newPrim_GT());
	set(env, "<",               FR1.newPrim_LT());
	set(env, "-",               FR1.newPrim_Minus());
	set(env, "+",               FR1.newPrim_Plus());
	set(env, "*",               FR1.newPrim_Times());

	// opt (optional or optimized)
	set(env, "java.lang.System.currentTimeMillis",
	                            FR1.newPrimCurrentTimeMillis());
	set(env, "instanceof",      FR1.newPrimInstanceof());
	set(env, "not",             FR1.newPrimNot());

	//
	// Install primitive Lava procedures.
	//

	set(env, "append",          FR1.newPrimAppend());
	set(env, "apply",           FR1.newPrimApply()); // REVISIT .apply
	set(env, "call/cc",         FR1.newPrimCallCC());
	set(env, "cons",            FR1.newPrimCons());
	set(env, ".%defGenInternal",FR1.newPrimDefGenInternal());
	set(env, "eval",            FR1.newPrimEval(env, 
						    repl.getEvaluator(),
						    repl.getCompiler()));
	set(env, ".f",              FR1.newPrimField());
	set(env, ".i",              FR1.newPrimInvoke());
	set(env, "length",          FR1.newPrimLength());
	set(env, "list",            FR1.newPrimList());
	set(env, ".list",           FR1.newPrimList_());
	set(env, "load",            FR1.newPrimLoad(repl));
	set(env, "new-thread",      FR1.newPrimNewThread());
	set(env, ".sf",             FR1.newPrimStaticField());
	set(env, ".si",             FR1.newPrimStaticInvoke());
	set(env, "string-append",   FR1.newPrimStringAppend());

	// opt (optional or optimized)
	set(env, "map",             FR1.newPrimMap());
	set(env, "null?",           FR1.newPrimNullP());
	set(env, "pair?",           FR1.newPrimPairP());
	set(env, "string->symbol",  FR1.newPrimString2Symbol());
	set(env, "symbol?",         FR1.newPrimSymbolP());


	//
	// N.B.: Order is VERY important from here on out.
	//

	set(env, "define-syntax",   FR1.newSyntaxDefineSyntax());
    }

    public void loadDerived (Repl repl)
    {
	installMiscDerived(repl);

	if (false) { // REVISIT - decide from property
	    // N.B.: This order *MUST* be kept in sync with order in
	    //       derived/GNUmakefile
	    loadDerived(repl,          FR1.newDerivedJavaFirst());
	    loadDerived(repl,          FR1.newDerivedScmTypeProcs());
	    loadDerived(repl,          FR1.newDerivedLavaControl());
	    loadDerived(repl,          FR1.newDerivedLavaConditional());
	    loadDerived(repl,          FR1.newDerivedLavaQuasiquote());
	    loadDerived(repl,          FR1.newDerivedLavaBinding());
	    loadDerived(repl,          FR1.newDerivedLavaCase());
	    loadDerived(repl,          FR1.newDerivedLavaIteration());
	    loadDerived(repl,          FR1.newDerivedLavaMember());
	    loadDerived(repl,          FR1.newDerivedJavaTry());
	    loadDerived(repl,          FR1.newDerivedJavaSecond());
	    loadDerived(repl,          FR1.newRequireProvide());
	    loadDerived(repl,          FR1.newDerivedTest());
	} else {
	    loadDerived(repl,          FR1.newDerivedAll());
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
"  (throw (.si 'newLavaException 'lava.F msg)))"
                                );


	    repl.readCompileEval(
"(define (display msg)" +
"  (print (.sf 'out 'java.lang.System) " +
"	 (if (null? msg)" +
"	     \"null\"" +
"	     (toString msg)))" +
"  null)"
                                );


	    repl.readCompileEval(
"(define (newline)" +
"  (println (.sf 'out 'java.lang.System))" +
"  null)"
                                );


	    repl.readCompileEval(
"(define .print" +
"  (lambda (x)" +
"    (println (.sf 'out 'java.lang.System) (if (null? x) \"null\" x))" +
"    x))"
                                );


	    repl.readCompileEval(
"(define-syntax .comment" +
"  (lambda args '()))"
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
