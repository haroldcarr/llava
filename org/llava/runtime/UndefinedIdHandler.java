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
 * Created       : 1999 Dec 28 (Tue) 00:27:53 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 18:54:45 by Harold Carr.
 */

package org.llava.runtime;

import org.llava.Symbol;

public interface UndefinedIdHandler
{
    public UndefinedIdHandler newUndefinedIdHandler ();

    public Object handle (EnvironmentTopLevel env, Symbol identifier);
}

// End of file.
