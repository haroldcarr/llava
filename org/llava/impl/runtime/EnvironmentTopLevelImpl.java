/**
 * Created       : 1999 Dec 25 (Sat) 19:08:58 by Harold Carr.
 * Last Modified : 2000 Feb 17 (Thu) 00:38:42 by Harold Carr.
 */

package libLava.r1.env;

import lava.F;
import lava.lang.types.Procedure;
import lava.lang.types.Symbol;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.FR;
import libLava.rt.UndefinedIdHandler;

public class EnvironmentTopLevelImpl 
    implements
	EnvironmentTopLevel
{
    private static final int   INITIAL_SIZE = 400;
    private Object[]           values;
    private static int         nextAvailableIndex = 0;
    private UndefinedIdHandler undefinedIdHandler;
    private Object             UNDEFINED = new Object();

    public EnvironmentTopLevelImpl ()
    {
    }

    private EnvironmentTopLevelImpl (int size)
    {
	this.undefinedIdHandler = FR.newUndefinedIdHandler();
	values = new Object[size];
	fillWithUndefined(values, 0, size);
    }

    public EnvironmentTopLevel newEnvironmentTopLevel ()
    {
	return new EnvironmentTopLevelImpl(INITIAL_SIZE);
    }

    private synchronized Object get (Symbol symbol, boolean ignoreUndefined)
    {
	int index = symbol.getEnvTopLevelIndex();
	Object result;
	if (index == -1 || 
	    index >= values.length || 
	    ((result = values[index]) == UNDEFINED)) {
	    return ignoreUndefined ? null :
		                     undefinedIdHandler.handle(this, symbol);
	}
	return values[index];
    }

    public Object get (Symbol symbol)
    {
	return get(symbol, false);
    }

    public Object getNoError (Symbol symbol)
    {
	return get(symbol, true);
    }

    // REVISIT: Define before set?
    public synchronized Object set (Symbol symbol, Object v)
    {
	int index = symbol.getEnvTopLevelIndex();
	if (index == -1 || index >= values.length) {
	    // It is either undefined, or undefined in this
	    // instance of Lava.
	    if (index == -1) {
		index = symbol.setEnvTopLevelIndex(nextAvailableIndex++);
	    }
	    if (index > values.length - 1) {
		System.out.print("resizing from " + values.length + " to ");
		resize(index);
		System.out.println(values.length);
	    }
	}
	Object current = values[index];
	if (current instanceof Procedure && current != v) {
	    Procedure cp = (Procedure)current;
	    cp.setName("from " + cp.getName());
	}
	if (v instanceof Procedure) {
	    ((Procedure)v).setName(symbol.toString());
	}
	return values[index] = v;
    }

    public UndefinedIdHandler getUndefinedIdHandler ()
    {
	return undefinedIdHandler;
    }

    public UndefinedIdHandler setUndefinedIdHandler (UndefinedIdHandler handler)
    {
	return undefinedIdHandler = handler;
    }

    public String toString ()
    {
	String result = "\nEnvironmentTopLevelImpl:";
	for (int i = 0; i < nextAvailableIndex; ++i) {
	    result += "\n\t" + i + " : " + values[i];
	}
	return result;
    }

    private void resize (int index)
    {
	
	int oldLength = values.length;
	// Try doubling the size.
	int newLen = oldLength * 2;
	// But if the index is still off the end then make big enough to fit.
	// This is necessary since symbols are EQ between instances
	// of Lava - so a symbol may have been set in a Lava with
	// many more top level variables than the current instance.
	// A waste of space here, but oh well ...
	newLen = newLen > index ? newLen : newLen + (index - (newLen - 1));
	System.out.print(" (" + oldLength + " - " + newLen + ") ");
	Object[] newValues = new Object[newLen];
	System.arraycopy(values, 0, newValues, 0, oldLength);
	fillWithUndefined(newValues, oldLength, newLen);
	values = newValues;
    }

    private void fillWithUndefined (Object[] a, int start, int length)
    {
	for (int i = start; i < length; i++) {
	    a[i] = UNDEFINED;
	}
    }
}

// End of file.

