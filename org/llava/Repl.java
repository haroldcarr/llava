/**
 * Created       : 1999 Dec 29 (Wed) 20:09:32 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 16:24:51 by Harold Carr.
 */

package lava;

import java.io.PrintWriter;
import java.io.Reader;

import lava.F;
import lava.io.LavaEOF;
import lava.io.LavaReader;
import lava.lang.exceptions.LavaException;
import libLava.co.Compiler;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.rt.LavaRuntime;

public interface Repl
{
    public Repl newRepl (LavaReader  reader,
			 PrintWriter out,
			 PrintWriter err,
			 LavaRuntime runtime,
			 Compiler    compiler);

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

    public void informAboutException ();

    public LavaException getLastException ();

    public void setTelnetCRLFOutput (boolean on);

    public void outputVersionMessage ();

    public void outputVersionMessage (long startupTime);

    public void loadResource (String loaderClass, String resource);

    public EnvironmentTopLevel getEnvironmentTopLevel ();
    public Evaluator           getEvaluator           ();
    public Compiler            getCompiler            ();

}

// End of file.
