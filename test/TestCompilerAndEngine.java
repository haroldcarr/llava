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
 * Created       : 1999 Dec 23 (Thu) 04:54:03 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:39:32 by Harold Carr.
 */

package test;

import org.llava.F;
import org.llava.LlavaException;
import org.llava.Repl;
import org.llava.compiler.Compiler;
import org.llava.io.LlavaReader;
import org.llava.io.LlavaWriter;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.runtime.LlavaRuntime;

import org.llava.impl.syntax.SyntaxDefineSyntax;
import org.llava.impl.util.List;

public class TestCompilerAndEngine
{
    /* Used by other test files. */
    public static EnvironmentTopLevel topEnvironment =
	F.newEnvironmentTopLevel();
    static Evaluator evaluator  = F.newEvaluator();
    static LlavaRuntime runtime = F.newLlavaRuntime(topEnvironment, evaluator);
    static Compiler compiler    = F.newCompiler();
    static LlavaReader reader   = F.newLlavaReader();
    static LlavaWriter writer   = F.newLlavaWriter();

    static Repl repl = 
	F.newRepl(reader, 
		  writer,
		  writer,
		  runtime, 
		  compiler);

    public static void testCompilerAndEngine ()
    {
	Test.dsop("begin: testCompilerAndEngine");

	topEnvironment.set(F.newSymbol("list"), F.newPrimList());

	Test.check("eval0", eval("'(1)"), eval("(list 1)"));
	Test.check("eval1", new Integer(1), eval("(if true 1 0)"));
	Test.check("eval2", new Integer(0), eval("(if false 1 0)"));
	Test.check("eval3", new Double(0.1), eval("(begin -1 0 0.1)"));
	// REVISIT
	//Test.check("eval4", new Double(0.1), eval("(lambda () 0.2)"));
	Test.check("eval5", 
		   "Anonymous lambda with body: 3: too many arguments",
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
		   "Anonymous lambda with body: y: not enough arguments",
		   eval("((lambda (x y) y) -1)"));
	Test.check("eval10", 
		   "Anonymous lambda with body: y: too many arguments",
		   eval("((lambda (x y) y) 1 2 3)"));
	/* REVISIT : returns generic undef handler when DI in use.
	Test.check("eval11", 
		   "Undefined top level variable: z",
		   eval("((lambda (x y) z) 1 2)"));
	*/
	Test.check("eval12", 
		   List.list(new Integer(3), new Integer(4)),
		   eval("((lambda (x y . z) z) 1 2 3 4)"));


	topEnvironment.set(F.newSymbol("define-syntax"),
			   new SyntaxDefineSyntax()); // REVISIT
	Test.check("eval13",
		   F.newSymbol("a"),
		   eval("(begin (define-syntax foo (lambda (x y) (list 'quote x))) (foo a b))")
		   );
	Test.dsop("end: testCompilerAndEngine");
    }

    public static Object eval(String stringExpr)
    {
	try {
	    Object expr = TestReader.readFromString(stringExpr);
	    Object code = repl.compile(expr);
	    Test.dsop(code.toString());
	    Object result = repl.eval(code);
	    return result;
	} catch (LlavaException le) {
	    Test.printStackTrace(le.getThrowable());
	    return le.getThrowable().getMessage();
	} catch (Throwable t) {
	    Test.printStackTrace(t);
	    return "TestCompilerAndEngine.eval: This should not happen.";
	}
    }
}

// End of file.
