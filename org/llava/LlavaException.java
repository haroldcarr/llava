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
 * Created       : 1999 Dec 26 (Sun) 18:03:05 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:44:15 by Harold Carr.
 */

package org.llava;

import java.io.PrintWriter;

import org.llava.runtime.BacktraceHandler;

public class LlavaException
    extends
	RuntimeException
{
    private Throwable throwable;
    private Object    backtrace;

    public LlavaException ()
    {
    }

    private LlavaException (Throwable throwable)
    {
	this.throwable = throwable;
	//System.out.println("*****: " + throwable);
    }

    protected LlavaException (String msg)
    {
	this(new Exception(msg));
    }

    public LlavaException newLlavaException (Throwable throwable)
    {
	return new LlavaException(throwable);
    }

    public LlavaException newLlavaException (String msg)
    {
	return new LlavaException(msg);
    }

    public Throwable getThrowable ()
    {
	return throwable;
    }

    public Object addToBacktrace (Object x, BacktraceHandler backtraceHandler)
    {
	return backtrace = backtraceHandler.addToBacktrace(x, backtrace);
    }

    public void printBacktrace (BacktraceHandler backtraceHandler,
				PrintWriter out)
    {
	backtraceHandler.printBacktrace(backtrace, out);
    }

    public String toString ()
    {
	String s = throwable.getMessage();
	//return this.getClass().getName() + " " + (s != null ? s : "");
	return throwable.getClass().getName() + " " + (s != null ? s : "");
    }
}

// End of file.

