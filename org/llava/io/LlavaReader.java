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
 * Created       : 1999 Dec 20 (Mon) 02:27:20 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:20 by Harold Carr.
 */

package org.llava.io;

import java.io.IOException;
import java.io.Reader;

public interface LlavaReader
{
    /**
     *
     */

    public LlavaReader newLlavaReader ();

    /**
     *
     */

    public LlavaReader newLlavaReader (Reader in);

    /**
     *
     */

    public Object read ();

    /**
     *
     */

    public Object read (Reader in);

    /**
     *
     */

    public Object read (String in);

}

// End of file.

