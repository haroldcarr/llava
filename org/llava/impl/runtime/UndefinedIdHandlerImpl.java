/**
 * Created       : 1999 Dec 28 (Tue) 00:27:53 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:39:22 by Harold Carr.
 */

package libLava.r1.env;

import lava.lang.types.Symbol;
import libLava.rt.EnvironmentTopLevel;
import libLava.r1.FR1;
import libLava.rt.UndefinedIdHandler;

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
	throw FR1.newUndefinedIdException(identifier);
    }
}

// End of file.
