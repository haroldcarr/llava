/**
 * Created       : 2000 Jan 17 (Mon) 02:47:11 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:39:25 by Harold Carr.
 */

package libLava.r1.exceptions;

import java.io.PrintWriter;

import lava.F;
import lava.lang.exceptions.BacktraceHandler;
import lava.lang.types.Pair;
import libLava.r1.env.ActivationFrameImpl;

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

