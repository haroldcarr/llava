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
 * Last Modified : 2004 Dec 08 (Wed) 10:26:25 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.F;
import org.llava.Symbol;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.UndefinedIdHandler;

public class UndefinedIdHandlerImpl
    implements
	UndefinedIdHandler
{
    private UndefinedIdHandler singleton;

    public UndefinedIdHandlerImpl ()
    {
    }

    public UndefinedIdHandler newUndefinedIdHandler ()
    {
	if (singleton == null) {
	    singleton = new UndefinedIdHandlerImpl();
	}
	return singleton;
    }

    public Object handle (EnvironmentTopLevel env, Symbol identifier)
    {
	throw F.newUndefinedIdException(identifier);
    }
}

// End of file.
