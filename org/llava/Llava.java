/**
 * Created       : 1999 Dec 30 (Thu) 04:18:02 by Harold Carr.
 * Last Modified : 2000 Feb 16 (Wed) 16:48:37 by Harold Carr.
 */

package lava;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import lava.F;
import lava.Repl;
import lava.io.LavaReader;
import lava.lang.exceptions.BacktraceHandler;
import lava.lang.exceptions.LavaException;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import libLava.co.Compiler;
import libLava.co.FC;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.rt.EnvTopLevelInit;
import libLava.rt.LavaRuntime;
import libLava.rt.FR;
import libLava.r1.Engine; // REVISIT
import libLava.r1.FR1; // REVISIT

public class Lava
{
    private Evaluator           evaluator;
    private Compiler            compiler;
    private EnvironmentTopLevel environment;
    private LavaRuntime         runtime;
    private Repl                repl;
    private BacktraceHandler    backtraceHandler;
    private PrintWriter         err;

    public static void main (String[] av)
    {
	long start = System.currentTimeMillis();
	Lava lava  = new Lava();
	long end   = System.currentTimeMillis();

	lava.getRepl().outputVersionMessage(end - start);

	lava.loadUserLavaRC();

	//newReplOnPort(4444); // REVISIT

	lava.getRepl().loop();
    }

    public Lava ()
    {
	this(System.in, System.out, System.err);
    }

    public Lava (InputStream in, OutputStream out, OutputStream err)

    {
	// REVISIT: compiler is passed to handle system derived procedures.
	// However this means the system cannot run without the compiler.
	// But the compiler is so small who cares?

	evaluator   = FR.newEvaluator();
	compiler    = FC.newCompiler();
	environment = FR.newEnvironmentTopLevel();
	runtime     = FR.newLavaRuntime(environment, evaluator);

	this.err    = new PrintWriter(err);
	repl        = F.newRepl(F.newLavaReader(new InputStreamReader(in)),
				new PrintWriter(out),
				this.err,
				runtime,
				compiler);

	EnvTopLevelInit init = FR.newEnvTopLevelInit();
	init.init(repl);
	init.loadDerived(repl);

	backtraceHandler = FR1.newBacktraceHandler(); // REVISIT
	environment.set(F.newSymbol(".jbt"), new JavaBacktrace());
	environment.set(F.newSymbol(".bt"), new LavaBacktrace());
    }

    public Object loadUserLavaRC ()
    {
	String homeDir = java.lang.System.getProperty("user.home");
	try {
	    return loadFile(homeDir + "/.lavarc");
	} catch (java.lang.Throwable t) {
	    if (t instanceof LavaException &&
		((LavaException)t).getThrowable() instanceof FileNotFoundException) {
		; // OK - it does not exist
	    } else {
		err.println("Error while loading: " + homeDir + "/.lavarc: " +
			    t);
		err.flush();
	    }
	}
	return null; // For javac.
    }

    public Object loadFile (String filename)
    {
	return repl.readCompileEval("(load \"" + filename + "\")");
    }

    public Evaluator           getEvaluator ()           { return evaluator; }
    public Compiler            getCompiler ()            { return compiler; }
    public EnvironmentTopLevel getEnvironmentTopLevel () { return environment;}
    public LavaRuntime         getLavaRuntime ()         { return runtime; }
    public Repl                getRepl ()                { return repl; }

    public class JavaBacktrace
	implements Procedure
    {
	private String name;
	public Object apply (Pair args, Engine engine)
	{
	    if (repl.getLastException() != null) {
		repl.getLastException().getThrowable().printStackTrace(err);
		err.flush();
	    }
	    return null;
	}
	public String getName ()            { return name; }
	public String setName (String name) { return this.name = name; }
    }

    public class LavaBacktrace
	implements Procedure
    {
	private String name;
	public Object apply (Pair args, Engine engine)
	{
	    if (repl.getLastException() != null) {
		repl.getLastException().printBacktrace(backtraceHandler, err);
		err.flush();
	    }
	    return null;
	}
	public String getName ()            { return name; }
	public String setName (String name) { return this.name = name; }
    }

    public static Thread newReplOnPort (int port)
    {
	// N.B.: This assumes you have the directory where the examples
	//       are located on your Lava load-path.

	Lava lava = new Lava();
	Repl repl = lava.getRepl();
	lava.loadUserLavaRC(); // REVISIT - .lavarc to get examples on path
	repl.readCompileEval("(load-library 'examples/net/server)");
	repl.readCompileEval("(define *repl-server* #t)");
	return (Thread)
	    repl.readCompileEval("(start-new-thread (lambda () (repl-server 'single " + port + ")))");
    }
}
	    
// End of file.
