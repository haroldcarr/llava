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
 * Last Modified : 2004 Dec 22 (Wed) 17:21:43 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.F;
import org.llava.Symbol;
import org.llava.procedure.GenericProcedure;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Namespace;
import org.llava.runtime.UndefinedIdHandler;

//import org.llava.impl.runtime.NamespaceImpl; // REVISIT - move into interface


public class UndefinedIdHandlerGenericImpl
    implements
	UndefinedIdHandler
{
    private UndefinedIdHandler singleton;

    public UndefinedIdHandlerGenericImpl ()
    {
    }

    public UndefinedIdHandler newUndefinedIdHandler ()
    {
	if (singleton == null) {
	    singleton = new UndefinedIdHandlerGenericImpl();
	}
	return singleton;
    }

    public Object handle (EnvironmentTopLevel env, Symbol identifier)
    {
	// You MUST set the name - it is used by DI as the method name.
	return F.newGenericProcedure(identifier.toString());
    }
}

// End of file.
