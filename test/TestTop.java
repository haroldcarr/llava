/**
 * Created       : 2000 Jan 11 (Tue) 21:23:51 by Harold Carr.
 * Last Modified : 2000 Mar 19 (Sun) 19:16:30 by .
 */

package testLava;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import lava.Lava;
import lava.Repl;
import lava.lang.exceptions.LavaException;
import lava.lang.types.Symbol;

public class TestTop
{
    public static int testNum = 1;

    public static String userHome = System.getProperty("user.home");
    public static String loadDir = 
	userHome + "/.sync/.esync/java/lava/testLava";

    public static String libDir  = loadDir + "/lib";

    public static void testTop ()
    {
	try {

	    // An "easy" way to step into the Java implementation code.
	    // Just put the form here as a string and start stepping.
	    // Examples:

	    /* REVISIT .i broke
	    eval("(.i 'floatValue (new 'java.lang.Integer 123))");
	    */
	    eval("(floatValue (new 'java.lang.Integer 123))");
	    eval("(.si 'getProperty 'java.lang.System (toString 'java.version))");
	    eval("(.sf 'TYPE 'java.lang.Double)");


	    eval("(> (new 'java.lang.Long \"949639300427\") (new 'java.lang.Long \"949639300426\"))");


	    eval("(run (new-thread (lambda () (.print 'XXXX))))");

	    // A way to evaluate several forms in sequence
	    // in the same Lava instance.

	    //evalShared("(define x (quote x))");
	    //evalShared("x");

	    // Test Repl

	    repl("(/ 4 1)");
	    //repl("(/ 4 0)");

	    // Check that symbols are EQ between Lava instances.

	    Symbol s1 = 
		(Symbol) new Lava().getRepl().readCompileEval("(quote a)");
	    Symbol s2 = 
		(Symbol) new Lava().getRepl().readCompileEval("(quote a)");
	    Test.check("symbol eq?", 
		       new Boolean(true),
		       new Boolean(s1 == s2));

	    // Test built ins.

	    libTest(loadDir + "/testBuiltIns.lva");

	    // Run the library tests.

	    libTest(libDir + "/cl/control/dotimes.lva");
	    libTest(libDir + "/cl/control/setq.lva");
	    libTest(libDir + "/cl/macros/defmacro.lva");
	    libTest(libDir + "/cl/symbols/gensym.lva");

	    libTest(libDir + "/lava/control/aif.lva");
	    libTest(libDir + "/lava/control/case-eval-l.lva");
	    libTest(libDir + "/lava/control/case-type.lva");
	    libTest(libDir + "/lava/control/for.lva");
	    libTest(libDir + "/lava/control/map3.lva");
	    libTest(libDir + "/lava/control/tlet.lva");
	    libTest(libDir + "/lava/control/while.lva");

	    libTest(libDir + "/lava/lists/add-between.lva");
	    libTest(libDir + "/lava/program/define-with-keywords.lva");
	    libTest(libDir + "/lava/program/define-with-exc-hand.lva");
	    libTest(libDir + "/lava/strings/list2string-with-space.lva");
	    libTest(libDir + "/lava/vectors/vector-map.lva");

	} catch (Throwable t) {
	    Test.bad("top", "this should not happen", t);
	    if (t instanceof LavaException) {
		((LavaException)t).getThrowable().printStackTrace(System.err);
	    } else {
		t.printStackTrace(System.err);
	    }
	}
    }

    // This one evals in a shared instance.

    private static Lava lava;
    private static Repl repl;
    public static Object evalShared (String x)
    {
	if (lava == null) {
	    lava = new Lava();
	    repl = lava.getRepl();
	}
	return repl.readCompileEval(x);
    }

    // All of the following deliberately create a new Lava instance
    // to run the test in a fresh environment.

    public static void libTest (String filename)
    {
	Test.check("top" + testNum++,
		   null,
		   load(filename));
    }

    public static Object load (String filename)
    {
	Lava lava = new Lava();     // Not functional so you can step over.
	return lava.loadFile(filename);
    }

    public static Object eval (String x)
    {
	Lava lava = new Lava();     // Not functional so you can step over.
	Repl repl = lava.getRepl(); // Ditto.
	return repl.readCompileEval(x);
    }

    public static void repl(String x)
    {
	ByteArrayInputStream  in  = new ByteArrayInputStream(x.getBytes());
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	Lava lava = new Lava(in, out, out);
	lava.getRepl().loop();
	String output = out.toString();
	System.out.println(output);
    }
}

// End of file.
