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
 * Last Modified : 2004 Dec 07 (Tue) 18:50:11 by Harold Carr.
 */

package org.llava;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import org.llava.LlavaException;

import org.llava.compiler.Compiler;

import org.llava.io.LlavaEOF;
import org.llava.io.LlavaReader;
import org.llava.io.LlavaWriter;

import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.runtime.LlavaRuntime;

public interface Repl
{
    public Repl newRepl (LlavaReader  reader,
			 LlavaWriter  out,
			 LlavaWriter  err,
			 LlavaRuntime runtime,
			 Compiler     compiler);

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

    public LlavaException getLastException ();

    public void setTelnetCRLFOutput (boolean on);

    public void outputVersionMessage ();

    public void outputVersionMessage (long startupTime);

    public Compiler            getCompiler            ();
    public EnvironmentTopLevel getEnvironmentTopLevel ();
    public Evaluator           getEvaluator           ();
    public LlavaRuntime        getLlavaRuntime        ();
    public LlavaReader         getLlavaReader         ();
    public LlavaWriter         getLlavaWriter         ();
}

// End of file.
