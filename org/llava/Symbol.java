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
 * Last Modified : 2004 Sep 03 (Fri) 15:35:24 by Harold Carr.
 */

package org.llava.lang.types;

public interface Symbol
{
    public Symbol newSymbol (String name);

    public Symbol newSymbolUninterned (String name);

    public int getEnvTopLevelIndex ();

    public int setEnvTopLevelIndex (int index);

    public boolean isFinal ();

    public boolean setIsFinal (boolean isFinal);
}

// End of file.

