/**
 * Created       : 1999 Dec 29 (Wed) 20:09:32 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 17:18:05 by Harold Carr.
 */

package lava;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import lava.F;
import lava.Repl;
import lava.io.LavaEOF;
import lava.io.LavaReader;
import lava.lang.exceptions.LavaException;
import libLava.co.Compiler;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.rt.LavaRuntime;

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
	this.runtime   = runtime;
	this.env       = runtime.getEnvironment();
	this.evaluator = runtime.getEvaluator();
	this.EOF       = F.newLavaEOF();
    }

    public Repl newRepl (LavaReader  reader,
			 PrintWriter out,
			 PrintWriter err,
			 LavaRuntime runtime,
			 Compiler    compiler)
    {
	return new ReplImpl(reader, out, err, runtime, compiler);
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
	prompt("\nlava> ");
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

    public EnvironmentTopLevel getEnvironmentTopLevel () { return env; }
    public Evaluator           getEvaluator           () { return evaluator; }
    public Compiler            getCompiler            () { return compiler; }

}

// End of file.
