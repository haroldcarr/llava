/**
 * Created       : 1999 Dec 30 (Thu) 04:18:02 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:10:01 by Harold Carr.
 */

package lavaProfile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import lava.Repl;
import lava.lang.exceptions.LavaException;

import lava.compiler.Compiler;

import lava.runtime.EnvironmentTopLevel;
import lava.runtime.Evaluator;
import lava.runtime.LavaRuntime;

import lavaProfile.F;

public class Lava
{
    private Repl repl;

    public static void main (String[] av)
    {
	long start = System.currentTimeMillis();
	Lava lava  = new Lava();
	long end   = System.currentTimeMillis();

	lava.getRepl().outputVersionMessage(end - start);

	lava.loadUserLavaRC(System.err);

	//newReplOnPort(4444); // REVISIT

	lava.getRepl().loop();
    }

    public Lava ()
    {
	repl = F.newRepl();
    }

    public Lava (InputStream in, OutputStream out, OutputStream err)

    {
	repl = F.newRepl(in, out, err);
    }

    public Object loadUserLavaRC (OutputStream err)
    {
	String homeDir = java.lang.System.getProperty("user.home");
	try {
	    return getRepl().loadFile(homeDir + "/.lavarc");
	} catch (java.lang.Throwable t) {
	    if (t instanceof LavaException &&
		((LavaException)t).getThrowable() instanceof FileNotFoundException) {
		; // OK - it does not exist
	    } else {
		new PrintWriter(err)
		    .println("Error while loading: " + homeDir + "/.lavarc: " +
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

    public Compiler    getCompiler  ()         { return repl.getCompiler(); }
    public EnvironmentTopLevel 
            getEnvironmentTopLevel () { return repl.getEnvironmentTopLevel(); }
    public Evaluator   getEvaluator ()         { return repl.getEvaluator(); }
    public LavaRuntime getLavaRuntime ()       { return repl.getLavaRuntime();}
    public Repl        getRepl ()              { return repl; }

    public static Thread newReplOnPort (int port)
    {
	// N.B.: This assumes you have the directory where the examples
	//       are located on your Lava load-path.

	Lava lava = new Lava();
	Repl repl = lava.getRepl();
	// REVISIT - .lavarc to get examples on path
	lava.loadUserLavaRC(System.err);
	repl.readCompileEval("(load-library 'examples/net/server)");
	repl.readCompileEval("(define *repl-server* #t)");
	return (Thread)
	    repl.readCompileEval("(start-new-thread (lambda () (repl-server 'single " + port + ")))");
    }
}
	    
// End of file.
