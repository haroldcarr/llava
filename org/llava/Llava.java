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
 * Created       : 1999 Dec 30 (Thu) 04:18:02 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 10:28:33 by Harold Carr.
 */

package org.llava;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.llava.F;
import org.llava.LlavaException;
import org.llava.Repl;
import org.llava.compiler.Compiler;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.runtime.LlavaRuntime;

public class Llava
{
    protected Repl repl;

    public static void main (String[] av)
    {
	long start  = System.currentTimeMillis();
	Llava llava = new Llava();
	long end    = System.currentTimeMillis();

	llava.getRepl().outputVersionMessage(end - start);

	llava.loadUserLlavaRC(System.err);

	//newReplOnPort(4444); // REVISIT

	llava.getRepl().loop();
    }

    public Llava ()
    {
	repl = F.newRepl();
    }

    public Llava (InputStream in, OutputStream out, OutputStream err)

    {
	repl = F.newRepl(in, out, err);
    }

    public Object loadUserLlavaRC (OutputStream err)
    {
	String homeDir = java.lang.System.getProperty("user.home");
	try {
	    return getRepl().loadFile(homeDir + "/.llavarc");
	} catch (java.lang.Throwable t) {
	    if (t instanceof LlavaException &&
		((LlavaException)t).getThrowable() instanceof FileNotFoundException) {
		; // OK - it does not exist
	    } else {
		new PrintWriter(err)
		    .println("Error while loading: " + homeDir + "/.llavarc: " +
			     t);
		try {
		    err.flush();
		} catch (IOException ioe) {
		    ; // REVISIT - what to do?
		}
	    }
	}
	return null; // For javac.
    }

    public Compiler     getCompiler ()        { return repl.getCompiler(); }
    public EnvironmentTopLevel 
            getEnvironmentTopLevel () { return repl.getEnvironmentTopLevel(); }
    public Evaluator    getEvaluator ()       { return repl.getEvaluator(); }
    public LlavaRuntime getLlavaRuntime ()    { return repl.getLlavaRuntime();}
    public Repl         getRepl ()            { return repl; }

    public static Thread newReplOnPort (int port)
    {
	// N.B.: This assumes you have the directory where the examples
	//       are located on your Llava load-path.

	Llava llava = new Llava();
	Repl repl = llava.getRepl();
	// REVISIT - .llavarc to get examples on path
	llava.loadUserLlavaRC(System.err);
	repl.readCompileEval("(load-library 'examples/net/server)");
	repl.readCompileEval("(define *repl-server* #t)");
	return (Thread)
	    repl.readCompileEval("(start-new-thread (lambda () (repl-server 'single " + port + ")))");
    }
}
	    
// End of file.
