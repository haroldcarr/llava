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
 * Created       : 1999 Dec 28 (Tue) 03:08:36 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 18:49:49 by Harold Carr.
 */

package org.llava;

import org.llava.Pair;
import org.llava.runtime.Engine; // REVISIT

public interface Procedure
{
    public Object apply (Pair args, Engine engine);

    public String getName ();

    public String setName (String name);
}

// End of file.
