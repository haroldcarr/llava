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
 * Created       : 2004 Dec 04 (Sat) 22:27:40 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 08:56:46 by Harold Carr.
 */

package org.llava.io;

import java.io.IOException;
import java.io.PrintWriter;

public interface LlavaWriter
{
    /**
     *
     */

    public LlavaWriter newLlavaWriter ();

    /**
     *
     */

    public LlavaWriter newLlavaWriter (PrintWriter out);

    /**
     *
     */

    public Object write (Object x);

    /**
     *
     */

    public Object write (Object x, PrintWriter out);

    public PrintWriter getPrintWriter ();

    public Object setVectorPrintLength (boolean x);
    public Object setVectorPrintLength (int x);
}

// End of file.

