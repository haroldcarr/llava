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
 * Last Modified : 2004 Dec 22 (Wed) 15:52:24 by Harold Carr.
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
	GenericProcedure gp = F.newGenericProcedure();
	// REVISIT - performance?
	if (env instanceof Namespace &&
	    // REVISIT: NamespaceImpl
	    ((NamespaceImpl)env).isDottedP(identifier.toString())) 
	{
	    ;
	} else {
	    // REVISIT - maybe do not set
	    
	    // REVISIT - cannot set when using package
	    // system.  Setting causes slots to have
	    // values in packages rather than being
	    // undefined so it falls through to
	    // next package of ref list.
	    
	    // REVISIT - but if you do not set
	    // it breaks - still need to investigate.
	    // For now there is a workaround in
	    // NamespaceImpl.  See WORKAROUND/SET/UNDEFINED.
	    env.set(identifier, gp);
	}
	return gp;
    }
}

// End of file.
