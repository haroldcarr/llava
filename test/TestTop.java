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
 * Created       : 2000 Jan 11 (Tue) 21:23:51 by Harold Carr.
 * Last Modified : 2004 Sep 06 (Mon) 00:25:55 by Harold Carr.
 */

package test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.llava.impl.Llava;
import org.llava.Repl;
import org.llava.lang.exceptions.LlavaException;
import org.llava.lang.types.Symbol;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.env.Namespace;

public class TestTop
{
    public static int testNum = 1;

    public static String userHome = System.getProperty("user.home");
    public static String loadDir = 
	userHome + "/.sync/.llava.org/.system/test";

    public static String libDir  = loadDir + "/lib";

    public static void testTop ()
    {
	Test.dsop("begin: testTop");
	try {

	    // An "easy" way to step into the Java implementation code.
	    // Just put the form here as a string and start stepping.
	    // Examples:

	    eval(
"(begin" +
"(package x.bottom)" +
"(define bottom " +
"  (lambda (x) " +
"    (let ((x (+ x 1)))" +
"      (list 'bottom x))))" +

"(package x.top)" +
"(import x.bottom)" +
"(define top " +
"  (lambda (x) " +
"    (let ((x (+ x 1)))" +
"      (list 'top (bottom x)))))" +

"(package org.llava.lib.Repl)" +
"(import x.top)" +
"(top 1)" +
")");

	    /* REVISIT _i broke
	    eval("(-i 'floatValue (new 'java.lang.Integer 123))");
	    */
	    eval("(floatValue (new 'java.lang.Integer 123))");
	    eval("(-si 'getProperty 'java.lang.System (toString 'java.version))");
	    eval("(-sf 'TYPE 'java.lang.Double)");


	    eval("(> (new 'java.lang.Long \"949639300427\") (new 'java.lang.Long \"949639300426\"))");


	    eval("(run (new-thread (lambda () (-println 'XXXX))))");

	    // A way to evaluate several forms in sequence
	    // in the same Llava instance.

	    //evalShared("(define x (quote x))");
	    //evalShared("x");

	    // Test Repl

	    repl("(/ 4 1)");
	    //repl("(/ 4 0)");

	    // Check that symbols are EQ between Llava instances.

	    Symbol s1 = 
		(Symbol) new Llava().getRepl().readCompileEval("(quote a)");
	    Symbol s2 = 
		(Symbol) new Llava().getRepl().readCompileEval("(quote a)");
	    Test.check("symbol eq?", 
		       new Boolean(true),
		       new Boolean(s1 == s2));

	    // Test built ins.

	    libTest(loadDir + "/testBuiltIns.lva");

	    // Test package/namespace implementation regardless
	    // of whether it is being used as the environment or not.

	    libTest(loadDir + "/testNamespaceInternal.lva");

	    // Run the library tests.

	    libTest(libDir + "/cl/Control.lva");
	    libTest(libDir + "/cl/Macros.lva");
	    libTest(libDir + "/cl/Symbols.lva");

	    libTest(libDir + "/Control.lva");
	    libTest(libDir + "/Lists.lva");
	    libTest(libDir + "/Program.lva");
	    libTest(libDir + "/Strings.lva");
	    libTest(libDir + "/Vectors.lva");

	} catch (Throwable t) {
	    Test.bad("top", "this should not happen", t);
	    if (t instanceof LlavaException) {
		((LlavaException)t).getThrowable().printStackTrace(System.err);
	    } else {
		t.printStackTrace(System.err);
	    }
	}
	Test.dsop("end: testTop");
    }

    // This one evals in a shared instance.

    private static Llava llava;
    private static Repl repl;
    public static Object evalShared (String x)
    {
	if (llava == null) {
	    llava = new Llava();
	    repl = llava.getRepl();
	}
	return repl.readCompileEval(x);
    }

    // All of the following deliberately create a new Llava instance
    // to run the test in a fresh environment.

    public static void libTest (String filename)
    {
	Test.check("top" + testNum++,
		   null,
		   load(filename));
    }

    public static Object load (String filename)
    {
	Llava llava = new Llava();     // Not functional so you can step over.
	return llava.getRepl().loadFile(filename);
    }

    public static Object eval (String x)
    {
	Llava llava = new Llava();     // Not functional so you can step over.
	Repl repl = llava.getRepl(); // Ditto.
	return repl.readCompileEval(x);
    }

    public static void repl(String x)
    {
	ByteArrayInputStream  in  = new ByteArrayInputStream(x.getBytes());
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	Llava llava = new Llava(in, out, out);
	llava.getRepl().loop();
	String output = out.toString();
	System.out.println(output);
    }
}

// End of file.
