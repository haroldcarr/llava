/**
 * Created       : 2000 Jan 17 (Mon) 02:17:23 by Harold Carr.
 * Last Modified : 2000 Jan 23 (Sun) 06:02:44 by Harold Carr.
 */

package lava.lang.exceptions;

import java.io.PrintWriter;

public interface BacktraceHandler
{
    public BacktraceHandler newBacktraceHandler ();

    public Object addToBacktrace (Object x, Object backtrace);

    public void printBacktrace (Object btrace, PrintWriter out);
}

// End of file.

