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
 * Created       : 2000 Jan 17 (Mon) 02:17:23 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:21 by Harold Carr.
 */

package org.llava.lang.exceptions;

import java.io.PrintWriter;

public interface BacktraceHandler
{
    public BacktraceHandler newBacktraceHandler ();

    public Object addToBacktrace (Object x, Object backtrace);

    public void printBacktrace (Object btrace, PrintWriter out);
}

// End of file.

