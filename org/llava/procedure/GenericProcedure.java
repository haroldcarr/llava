/**
 * Created       : 1999 Dec 28 (Tue) 03:20:33 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:44:32 by Harold Carr.
 */

package lavaProfile.runtime.procedure.generic;

import lava.lang.types.Procedure;
import lavaProfile.runtime.procedure.Lambda;

public interface GenericProcedure
    extends
	Procedure
{
    public GenericProcedure newGenericProcedure ();

    public GenericProcedure newGenericProcedure (String name);

    public GenericProcedure newGenericProcedure (Lambda lambda);
}

// End of file.
