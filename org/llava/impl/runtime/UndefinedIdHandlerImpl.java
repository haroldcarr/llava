/**
 * Created       : 1999 Dec 28 (Tue) 00:27:53 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:43:38 by Harold Carr.
 */

package lavaProfile.runtime.env;

import lava.lang.types.Symbol;
import lava.runtime.EnvironmentTopLevel;
import lavaProfile.runtime.FR;
import lava.runtime.UndefinedIdHandler;

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
	throw FR.newUndefinedIdException(identifier);
    }
}

// End of file.
