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
 * Created       : 1999 Dec 29 (Wed) 20:09:32 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 17:18:05 by Harold Carr.
 */

package org.llava.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import org.llava.F;
import org.llava.FC;
import org.llava.FR;
import org.llava.LlavaException;
import org.llava.Pair;
import org.llava.Procedure;
import org.llava.Repl;

import org.llava.compiler.Compiler;

import org.llava.io.LlavaEOF;
import org.llava.io.LlavaReader;
import org.llava.io.LlavaWriter;

import org.llava.runtime.BacktraceHandler;
import org.llava.runtime.Engine; // REVISIT
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.EnvTopLevelInit;
import org.llava.runtime.Evaluator;
import org.llava.runtime.LlavaRuntime;
import org.llava.runtime.Namespace; // REVISIT

public class ReplImpl
    implements
	Repl
{
    private LlavaReader         reader;
    private LlavaWriter         writer;
    private LlavaWriter         errWriter;
    private LlavaRuntime        runtime;
    private EnvironmentTopLevel env;
    private Evaluator           evaluator;
    private Compiler            compiler;

    protected LlavaException    lastException;
    protected LlavaEOF          EOF;

    private BacktraceHandler    backtraceHandler;
    
    private boolean             telnetCRLFOutput = false;

    public ReplImpl ()
    {
    }

    // REVISIT:
    // order: promptCallback, reader, env, compiler, evaluator, printCallback
    // printCallback handles actual output.
    private ReplImpl (LlavaReader  reader,
		      LlavaWriter  writer,
		      LlavaWriter  errWriter,
		      LlavaRuntime runtime,
		      Compiler     compiler)
    {
	this.reader    = reader;
	this.writer    = writer;
	this.errWriter = errWriter;
	this.compiler  = compiler;
	this.env       = runtime.getEnvironment();
	this.evaluator = runtime.getEvaluator();
	this.runtime   = runtime;
	this.EOF       = F.newLlavaEOF();

	init();
    }

    protected ReplImpl (InputStream in, OutputStream out, OutputStream err)
    {

	//You can override defaults via -D or setting them here.
	//System.setProperty("llava.io.LlavaEOFClassName", "Bad");

	// REVISIT: compiler is passed to handle system derived procedures.
	// However this means the system cannot run without the compiler.
	// But the compiler is so small who cares?

	this.reader    = F.newLlavaReader(new InputStreamReader(in));
	this.writer    = F.newLlavaWriter(new PrintWriter(out));
	this.errWriter = F.newLlavaWriter(new PrintWriter(err));
	this.compiler  = FC.newCompiler();
	this.env       = FR.newEnvironmentTopLevel();
	this.evaluator = FR.newEvaluator();
	this.runtime   = FR.newLlavaRuntime(env, evaluator);
	this.EOF       = F.newLlavaEOF();

	init();
    }

    private void init ()
    {
	EnvTopLevelInit init = FR.newEnvTopLevelInit(this);
	init.init();
	init.loadDerived();

	backtraceHandler = FR.newBacktraceHandler(); // REVISIT
	env.set(F.newSymbol("-jbt"), new JavaBacktrace());
	env.set(F.newSymbol("-bt"), new LlavaBacktrace());

	if (env instanceof Namespace) {
	    Namespace ns = (Namespace)env;
	    // The root namespace is immutable.
	    // REVISIT : get name from Namespace constant.
	    ns.findNamespace(F.newSymbol(F.llavaPackageName())).setIsSealed(true);
	    // The REPL starts in its own namespace.
	    ns._package(F.newSymbol(F.initialReplPackageName()));
	}
    }

    public Repl newRepl (LlavaReader  reader,
			 LlavaWriter  writer,
			 LlavaWriter  errWriter,
			 LlavaRuntime runtime,
			 Compiler     compiler)
    {
	return new ReplImpl(reader, writer, errWriter, runtime, compiler);
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
	    } catch (LlavaException e) {
		lastException = e;
		informAboutException();
	    } catch (Throwable t) {
		lastException = F.newLlavaException(t);
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
	    prompt("\nllava> ");
	}
    }

    // REVISIT: Callback object handles actual output.
    public void prompt (String prompt)
    {
	writer.getPrintWriter().print(prompt);
	writer.getPrintWriter().flush();
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
	    writer.getPrintWriter().println("\r");
	}
	writer.write(result);
	if (telnetCRLFOutput) {
	    writer.getPrintWriter().print("\r");
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
		throw F.newLlavaException("Resource file not found: " +
					  loaderClass + " " + resource);
	    }
	} catch (ClassNotFoundException e) {
	    throw F.newLlavaException("Resource load class not found: " +
				      loaderClass + " " + resource);
	}
    }

    public void informAboutException ()
    {
	Throwable t = lastException.getThrowable();
	errWriter.getPrintWriter().println("Error: " + t.toString());
	errWriter.getPrintWriter().flush();
    }

    public LlavaException getLastException ()
    {
	return lastException;
    }

    public void setTelnetCRLFOutput (boolean on)
    {
	telnetCRLFOutput = on;
    }

    public void outputVersionMessage ()
    {
	writer.getPrintWriter().println(F.newLlavaVersion());
	writer.getPrintWriter().flush();
    }

    public void outputVersionMessage (long startupTime)
    {
	outputVersionMessage();
	writer.getPrintWriter()
	    .println("Startup time: " + startupTime + " ms.");
	writer.getPrintWriter().flush();
    }

    public Compiler            getCompiler            () { return compiler; }
    public EnvironmentTopLevel getEnvironmentTopLevel () { return env; }
    public Evaluator           getEvaluator           () { return evaluator; }
    public LlavaRuntime        getLlavaRuntime        () { return runtime; }
    public LlavaReader         getLlavaReader         () { return reader; }
    public LlavaWriter         getLlavaWriter         () { return writer; }

    public class JavaBacktrace
	implements Procedure
    {
	private String name;
	public Object apply (Pair args, Engine engine)
	{
	    if (getLastException() != null) {
		getLastException().getThrowable()
		    .printStackTrace(errWriter.getPrintWriter());
		errWriter.getPrintWriter().flush();
	    }
	    return null;
	}
	public String getName ()            { return name; }
	public String setName (String name) { return this.name = name; }
    }

    public class LlavaBacktrace
	implements Procedure
    {
	private String name;
	public Object apply (Pair args, Engine engine)
	{
	    if (getLastException() != null) {
		getLastException().printBacktrace(backtraceHandler, 
						  errWriter.getPrintWriter());
		errWriter.getPrintWriter().flush();
	    }
	    return null;
	}
	public String getName ()            { return name; }
	public String setName (String name) { return this.name = name; }
    }
}

// End of file.
