/**
 * Created       : 1999 Dec 23 (Thu) 04:54:03 by Harold Carr.
 * Last Modified : 2000 Feb 15 (Tue) 21:48:12 by Harold Carr.
 */

package testLava;

import java.io.PrintWriter;

import lava.F;
import lava.io.LavaReader;
import lava.lang.exceptions.LavaException;
import lava.util.List;
import lava.Repl;
import libLava.co.Compiler;
import libLava.co.FC;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.rt.FR;
import libLava.rt.LavaRuntime;
import libLava.r1.FR1;
import libLava.r1.syntax.SyntaxDefineSyntax;

public class TestCompilerAndEngine
{
    /* Used by other test files. */
    public static EnvironmentTopLevel topEnvironment =
	FR.newEnvironmentTopLevel();
    static Evaluator evaluator = FR.newEvaluator();
    static LavaRuntime runtime = FR.newLavaRuntime(topEnvironment, evaluator);
    static Compiler compiler   = FC.newCompiler();
    static LavaReader reader   = F.newLavaReader();

    static Repl repl = 
	F.newRepl(reader, 
		  new PrintWriter(System.out), 
		  new PrintWriter(System.err),
		  runtime, 
		  compiler);

    public static void testCompilerAndEngine ()
    {

	topEnvironment.set(F.newSymbol("list"), FR1.newPrimList());


	Test.check("eval1", new Integer(1), eval("(if true 1 0)"));
	Test.check("eval2", new Integer(0), eval("(if false 1 0)"));
	Test.check("eval3", new Double(0.1), eval("(begin -1 0 0.1)"));
	// REVISIT
	//Test.check("eval4", new Double(0.1), eval("(lambda () 0.2)"));
	Test.check("eval5", 
		   "Anonymous lambda with body: {libLava.r1.code.CodeLiteral 3}: too many arguments",
		   eval("((lambda () 3) 4)"));
	Test.check("eval6", new Double(5.0), eval("((lambda (x) x) 5.0)"));
	Test.check("eval6.1", 
		   eval("'(((1) (2)))"),
		   eval("((lambda args (list args)) (list 1) '(2))"));
	Test.check("eval7", 
		   new Integer(-1),
		   eval("((lambda (x) (set! x -1) x) 5.0)"));
	Test.check("eval8", 
		   new Double(6.9),
		   eval("((lambda (x)" +
                            "((lambda (y)" +
                                "(set! x (lambda () y)))" +
                             "6.9)" +
                            "(x))" +
                         "-1)"
			));
	Test.check("eval9",
		   new Integer(45),
		   eval(
"(begin" +
  "(set! test true)" +
  "(set! makeAlternate" +
     "(lambda (x y)" +
       "(lambda () (if test x y))))" +
  "(set! alt (makeAlternate -33 45))" +
  "(alt)" +
  "(alt)" +
  "(set! test false)" +
  "(alt)" +
")"
                       ));
	Test.check("eval9.1", 
		   "Anonymous lambda with body: {libLava.r1.code.CodeReference y}: not enough arguments",
		   eval("((lambda (x y) y) -1)"));
	Test.check("eval10", 
		   "Anonymous lambda with body: {libLava.r1.code.CodeReference y}: too many arguments",
		   eval("((lambda (x y) y) 1 2 3)"));
	Test.check("eval11", 
		   "Undefined top level variable: z",
		   eval("((lambda (x y) z) 1 2)"));
	Test.check("eval12", 
		   List.list(new Integer(3), new Integer(4)),
		   eval("((lambda (x y . z) z) 1 2 3 4)"));


	topEnvironment.set(F.newSymbol("define-syntax"),
			   new SyntaxDefineSyntax()); // REVISIT
	Test.check("eval13",
		   F.newSymbol("a"),
		   eval("(begin (define-syntax foo (lambda (x y) (list 'quote x))) (foo a b))")
		   );
    }

    public static Object eval(String stringExpr)
    {
	try {
	    Object expr = TestReader.readFromString(stringExpr);
	    Object code = repl.compile(expr);
	    Test.dsop(code.toString());
	    return repl.eval(code);
	} catch (LavaException le) {
	    Test.printStackTrace(le.getThrowable());
	    return le.getThrowable().getMessage();
	} catch (Throwable t) {
	    Test.printStackTrace(t);
	    return "TestCompilerAndEngine.eval: This should not happen.";
	}
    }
}

// End of file.