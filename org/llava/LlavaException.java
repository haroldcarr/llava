/**
 * Created       : 1999 Dec 26 (Sun) 18:03:05 by Harold Carr.
 * Last Modified : 2000 Feb 01 (Tue) 06:25:15 by Harold Carr.
 */

package lava.lang.exceptions;

import java.io.PrintWriter;

public class LavaException
    extends
	RuntimeException
{
    private Throwable throwable;
    private Object    backtrace;

    public LavaException ()
    {
    }

    private LavaException (Throwable throwable)
    {
	this.throwable = throwable;
	//System.out.println("*****: " + throwable);
    }

    protected LavaException (String msg)
    {
	this(new Exception(msg));
    }

    public LavaException newLavaException (Throwable throwable)
    {
	return new LavaException(throwable);
    }

    public LavaException newLavaException (String msg)
    {
	return new LavaException(msg);
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

