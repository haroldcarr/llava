/**
 * Created       : 1999 Dec 28 (Tue) 03:20:33 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:39:45 by Harold Carr.
 */

package libLava.r1.procedure.generic;

import lava.lang.types.Procedure;
import libLava.r1.procedure.Lambda;

public interface GenericProcedure
    extends
	Procedure
{
    public GenericProcedure newGenericProcedure ();

    public GenericProcedure newGenericProcedure (String name);

    public GenericProcedure newGenericProcedure (Lambda lambda);
}

// End of file.
