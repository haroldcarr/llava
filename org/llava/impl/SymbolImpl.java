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
 * Last Modified : 2004 Dec 08 (Wed) 13:05:49 by Harold Carr.
 */

package org.llava.impl;

import java.util.HashMap;

import org.llava.Symbol;

public class SymbolImpl
    implements
	Symbol
{
    // NOTE: a static is OK since a Symbol is quasi-atomic.  Thus they
    // can be shared between independent instances of llava.
    // quasi-atomic: get/setEnvTopLevelIndex works across llava instances.
    // See EnvironmenTopLevelImp for why.
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

    public boolean equals (Object x) 
    {
	// Interned, so simple test.
	if (x == this) {
	    return true;
	}
	return false;
    }

    public String toString ()
    {
	return name;
    }
}

// End of file.

