/**
 * Created       : 1999 Dec 24 (Fri) 19:20:36 by Harold Carr.
 * Last Modified : 2000 Jan 25 (Tue) 17:10:16 by Harold Carr.
 */

package lava.lang.types;

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

