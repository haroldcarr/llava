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
 * Created       : 1999 Dec 28 (Tue) 03:20:33 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 18:52:38 by Harold Carr.
 */

package org.llava.procedure;

import org.llava.Lambda;
import org.llava.Procedure;

public interface GenericProcedure
    extends
	Procedure
{
    public GenericProcedure newGenericProcedure ();

    public GenericProcedure newGenericProcedure (String name);

    public GenericProcedure newGenericProcedure (Lambda lambda);
}

// End of file.
