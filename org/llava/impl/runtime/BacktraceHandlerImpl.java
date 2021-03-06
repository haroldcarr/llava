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
 * Created       : 2000 Jan 17 (Mon) 02:47:11 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:30:32 by Harold Carr.
 */

package org.llava.impl.runtime;

import java.io.PrintWriter;

import org.llava.F;
import org.llava.Pair;
import org.llava.runtime.BacktraceHandler;

import org.llava.impl.runtime.ActivationFrameImpl;

public class BacktraceHandlerImpl
    implements
	BacktraceHandler
{
    public BacktraceHandlerImpl ()
    {
    }

    public BacktraceHandler newBacktraceHandler ()
    {
	return new BacktraceHandlerImpl();
    }

    public Object addToBacktrace (Object x, Object backtrace)
    {
	return F.cons(x, backtrace);
    }

    public void printBacktrace (Object btrace, PrintWriter out)
    {
	// REVISIT: order of printing opposite Java's.
	Pair backtrace = (Pair)btrace;
	int level = backtrace == null ? 0 : backtrace.length() - 1;
	for (Pair p = backtrace; p != null; p = (Pair)p.cdr(), --level) {
	    out.print(" " + level + "> ");
	    out.println(p.caar());
	    out.println(((ActivationFrameImpl)p.cdar()).toStringForBacktrace());
	    out.flush();
	}
    }
}

// End of file.

