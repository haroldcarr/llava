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
 * Created       : 1999 Dec 30 (Thu) 06:31:28 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:40 by Harold Carr.
 */

package org.llava.runtime;

import org.llava.Repl;

public interface EnvTopLevelInit
{
    public EnvTopLevelInit newEnvTopLevelInit (Repl repl);

    public void init ();

    public void loadDerived ();
}

// End of file.
