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
 * Created       : 1999 Dec 24 (Fri) 19:20:36 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 13:06:03 by Harold Carr.
 */

package org.llava;

public interface Symbol
{
    public Symbol newSymbol (String name);

    public Symbol newSymbolUninterned (String name);

    public int getEnvTopLevelIndex ();

    public int setEnvTopLevelIndex (int index);
}

// End of file.

