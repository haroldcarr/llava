/**
 * Created       : 1999 Dec 29 (Wed) 20:09:32 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 17:18:05 by Harold Carr.
 */

package lavaProfile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import lavaProfile.F;
import lava.Repl;

import lava.io.LavaEOF;
import lava.io.LavaReader;

import lava.lang.exceptions.BacktraceHandler;
import lava.lang.exceptions.LavaException;

import lava.lang.types.Pair;
import lava.lang.types.Procedure;

import lava.compiler.Compiler;
import lavaProfile.compiler.FC;

import lava.runtime.EnvironmentTopLevel;
import lava.runtime.EnvTopLevelInit;
import lava.runtime.Evaluator;
import lavaProfile.runtime.FR;
import lava.runtime.LavaRuntime;

import lavaProfile.runtime.Engine; // REVISIT
import lavaProfile.runtime.FR; // REVISIT
import lavaProfile.runtime.env.Namespace; // REVISIT

public class ReplImpl
    implements
	Repl
{
    private LavaReader          reader;
    private PrintWriter         out;
    private PrintWriter         err;
    private LavaRuntime         runtime;
    private EnvironmentTopLevel env;
    private Evaluator           evaluator;
    private Compiler            compiler;

    private LavaException       lastException;
    private LavaEOF             EOF;

    private BacktraceHandler    backtraceHandler;
    
    private boolean             telnetCRLFOutput = false;

    public ReplImpl ()
    {
    }

    // REVISIT:
    // order: promptCallback, reader, env, compiler, evaluator, printCallback
    // printCallback handles actual output.
    private ReplImpl (LavaReader  reader,
		      PrintWriter out,
		      PrintWriter err,
		      LavaRuntime runtime,
		      Compiler    compiler)
    {
	this.reader    = reader;
	this.out       = out;
	this.err       = err;
	this.compiler  = compiler;
	this.env       = runtime.getEnvironment();
	this.evaluator = runtime.getEvaluator();
	this.runtime   = runtime;
	this.EOF       = F.newLavaEOF();

	init();
    }

    private ReplImpl (InputStream in, OutputStream out, OutputStream err)
    {

	//You can override defaults via -D or setting them here.
	//System.setProperty("lava.io.LavaEOFClassName", "Bad");

	// REVISIT: compiler is passed to handle system derived procedures.
	// However this means the system cannot run without the compiler.
	// But the compiler is so small who cares?

	this.reader    = F.newLavaReader(new InputStreamReader(in));
	this.out       = new PrintWriter(out);
	this.err       = new PrintWriter(err);
	this.compiler  = FC.newCompiler();
	this.env       = FR.newEnvironmentTopLevel();
	this.evaluator = FR.newEvaluator();
	this.runtime   = FR.newLavaRuntime(env, evaluator);
	this.EOF       = F.newLavaEOF();

	init();
    }

    private void init ()
    {
	EnvTopLevelInit init = FR.newEnvTopLevelInit(this);
	init.init();
	init.loadDerived();

	backtraceHandler = FR.newBacktraceHandler(); // REVISIT
	env.set(F.newSymbol("_jbt"), new JavaBacktrace());
	env.set(F.newSymbol("_bt"), new LavaBacktrace());

	if (env instanceof Namespace) {
	    Namespace ns = (Namespace)env;
	    // The root namespace is immutable.
	    ns.findNamespace(F.newSymbol("lava.Lava")).setIsSealed(true);
	    // The REPL starts in its own namespace.
	    ns._package(F.newSymbol("lava"), F.newSymbol("Repl"));
	}
    }

    public Repl newRepl (LavaReader  reader,
			 PrintWriter out,
			 PrintWriter err,
			 LavaRuntime runtime,
			 Compiler    compiler)
    {
	return new ReplImpl(reader, out, err, runtime, compiler);
    }

    public Repl newRepl ()
    {
	return new ReplImpl(System.in, System.out, System.err);
    }

    public Repl newRepl (InputStream in, OutputStream out, OutputStream err)
    {
	return new ReplImpl(in, out, err);
    }

    public void loop ()
    {
	while (true) {
	    try {
		while (true) {
		    prompt();
		    Object sexpr = read();
		    if (sexpr.equals(EOF)) {
			return;
		    }
		    printResult(eval(compile(sexpr)));
		} 
	    } catch (LavaException e) {
		lastException = e;
		informAboutException();
	    } catch (Throwable t) {
		lastException = F.newLavaException(t);
		informAboutException();
	    }
	}
    }

    // REVISIT: Callback object to set prompt before each loop.
    //          Or callback object given out to do (or not do) the prompt.
    public void prompt ()
    {
	if (env instanceof Namespace) {
	    prompt("\n"
		   + ((Namespace)env).getCurrentNamespace().getName() 
		   + "> ");
	} else {
	    prompt("\nlava> ");
	}
    }

    // REVISIT: Callback object handles actual output.
    public void prompt (String prompt)
    {
	out.print(prompt);
	out.flush();
    }

    public Object readCompileEval ()
    {
	return eval(compile(read()));
    }

    public Object readCompileEvalUntilEOF (Reader in)
    {
	for (;;) {
	    Object sexpr = read(in);
	    if (sexpr.equals(EOF)) {
		break;
	    }
	    eval(compile(sexpr));
	}
	return null;
    }

    public Object readCompileEval (String form)
    {
	return eval(compile(read(form)));
    }

    public Object read ()
    {
	return reader.read();
    }

    public Object read (Reader in)
    {
	return reader.read(in);
    }

    public Object read (String form)
    {
	return reader.read(form);
    }

    public Object compile (Object expr)
    {
	return compiler.compile(expr, runtime);
    }

    public Object eval (Object code)
    {
        return evaluator.eval(code, env);
    }

    public Object printResult (Object result)
    {
	if (telnetCRLFOutput) {
	    out.println("\r");
	}
	out.print(result);
	if (telnetCRLFOutput) {
	    out.print("\r");
	}
	return result;
    }

    public Object loadFile (String filename)
    {
	return readCompileEval("(load \"" + filename + "\")");
    }

    public void loadResource (String loaderClass, String resource)
    {
	try {
	    Class lclass = Class.forName(loaderClass);
	    InputStream in = lclass.getResourceAsStream(resource);
	    if (in != null) {
		readCompileEvalUntilEOF(new InputStreamReader(in));
	    } else {
		throw F.newLavaException("Resource file not found: " +
					 loaderClass + " " + resource);
	    }
	} catch (ClassNotFoundException e) {
	    throw F.newLavaException("Resource load class not found: " +
				     loaderClass + " " + resource);
	}
    }

    public void informAboutException ()
    {
	Throwable t = lastException.getThrowable();
	err.println("Error: " + t.toString());
	err.flush();
    }

    public LavaException getLastException ()
    {
	return lastException;
    }

    public void setTelnetCRLFOutput (boolean on)
    {
	telnetCRLFOutput = on;
    }

    public void outputVersionMessage ()
    {
	out.println(F.newLavaVersion());
	out.flush();
    }

    public void outputVersionMessage (long startupTime)
    {
	outputVersionMessage();
	out.println("Startup time: " + startupTime + " ms.");
	out.flush();
    }

    public Compiler            getCompiler            () { return compiler; }
    public EnvironmentTopLevel getEnvironmentTopLevel () { return env; }
    public Evaluator           getEvaluator           () { return evaluator; }
    public LavaRuntime         getLavaRuntime         () { return runtime; }

    public class JavaBacktrace
	implements Procedure
    {
	private String name;
	public Object apply (Pair args, Engine engine)
	{
	    if (getLastException() != null) {
		getLastException().getThrowable().printStackTrace(err);
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
	    if (getLastException() != null) {
		getLastException().printBacktrace(backtraceHandler, err);
		err.flush();
	    }
	    return null;
	}
	public String getName ()            { return name; }
	public String setName (String name) { return this.name = name; }
    }
}

// End of file.
