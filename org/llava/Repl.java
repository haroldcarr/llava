/**
 * Created       : 1999 Dec 29 (Wed) 20:09:32 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:05:44 by Harold Carr.
 */

package lava;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import lava.io.LavaEOF;
import lava.io.LavaReader;

import lava.lang.exceptions.LavaException;

import lava.compiler.Compiler;

import lava.runtime.EnvironmentTopLevel;
import lava.runtime.Evaluator;
import lava.runtime.LavaRuntime;

public interface Repl
{
    public Repl newRepl (LavaReader  reader,
			 PrintWriter out,
			 PrintWriter err,
			 LavaRuntime runtime,
			 Compiler    compiler);

    public Repl newRepl ();

    public Repl newRepl (InputStream in, OutputStream out, OutputStream err);

    public void loop ();

    // REVISIT: Callback object to set prompt before each loop.
    public void prompt ();

    // REVISIT: Callback object handles actual output.
    public void prompt (String prompt);

    public Object readCompileEval ();

    public Object readCompileEvalUntilEOF (Reader in);

    public Object readCompileEval (String form);

    public Object read ();

    public Object read (Reader in);

    public Object read (String form);

    public Object compile (Object expr);

    public Object eval (Object code);

    public Object printResult (Object result);

    public Object loadFile (String filename);

    public void loadResource (String loaderClass, String resource);

    public void informAboutException ();

    public LavaException getLastException ();

    public void setTelnetCRLFOutput (boolean on);

    public void outputVersionMessage ();

    public void outputVersionMessage (long startupTime);

    public Compiler            getCompiler            ();
    public EnvironmentTopLevel getEnvironmentTopLevel ();
    public Evaluator           getEvaluator           ();
    public LavaRuntime         getLavaRuntime         ();

}

// End of file.
