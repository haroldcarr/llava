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
 * Created       : 1999 Dec 24 (Fri) 19:24:53 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:24 by Harold Carr.
 */

package org.llava.lang.types;

import java.util.HashMap;

import org.llava.lang.types.Symbol;

public class SymbolImpl
    implements
	Symbol
{
    // REVISIT capacity/load factor.
    private static HashMap symbols = new HashMap();

    private String  name;
    private int     topLevelIndex = -1;
    private boolean isFinal       = false;

    public SymbolImpl ()
    {
    }

    // This should only be called from newSymbol.
    private SymbolImpl (String name)
    {
	this.name = name;
    }

    public Symbol newSymbol (String name)
    {
	String internedName = name.intern();
	// Get and put must be atomic.
	synchronized (this) {
	    Object internedSymbol = symbols.get(internedName);
	    if (internedSymbol != null) {
		return (Symbol)internedSymbol;
	    }
	    Symbol symbol = new SymbolImpl(internedName);
	    symbols.put(internedName, symbol);
	    return symbol;
	}
    }

    public Symbol newSymbolUninterned (String name)
    {
	return new SymbolImpl(name);
    }

    public int getEnvTopLevelIndex()
    {
	return topLevelIndex;
    }

    public int setEnvTopLevelIndex(int index)
    {
	return topLevelIndex = index;
    }

    public boolean isFinal ()
    {
	return isFinal;
    }

    public boolean setIsFinal (boolean isFinal)
    {
	return this.isFinal = isFinal;
    }

    public boolean equals (Object x) 
    {
	if (x == this) {
	    return true;
	}
	return false;
	
	/*
	if (x == this) {
	    return true;
	} else if (!(x instanceof Symbol)) {
	    return false;
	}
	return name.equals(x.toString());
	*/
    }

    public String toString ()
    {
	return name;
    }
}

// End of file.

