/**
 * Created       : 1999 Dec 21 (Tue) 00:12:36 by Harold Carr.
 * Last Modified : 2000 Jan 29 (Sat) 15:39:14 by Harold Carr.
 */

package libLava.r1.env;

import lava.lang.types.Pair;
import lava.lang.types.Symbol;
import lava.util.List;
import libLava.rt.EnvironmentTopLevel;
import libLava.r1.FR1;

public class ActivationFrameImpl 
    implements
	ActivationFrame
{
    private ActivationFrame     next = null;
    private Object[]            args;
    private EnvironmentTopLevel top;

    public ActivationFrameImpl ()
    {
    }

    private ActivationFrameImpl (EnvironmentTopLevel top)
    {
	this.top = top;
	this.args = new Object[0]; // So no null pointers.
    }

    private ActivationFrameImpl (int size)
    {
	args = new Object[size];
    }

    private ActivationFrameImpl (Pair values)
    {
	args = List.toArray(values);
    }

    /**
     * This to create an empty activation frame containing
     * the top level environment.
     */

    public ActivationFrame newActivationFrame (EnvironmentTopLevel top)
    {
	return new ActivationFrameImpl(top);
    }

    public ActivationFrame newActivationFrame (int size)
    {
	return new ActivationFrameImpl(size);
    }

    public ActivationFrame extend (Pair args)
    {
	return extend(new ActivationFrameImpl(args));
    }

    public ActivationFrame extend (ActivationFrame frame)
    {
	((ActivationFrameImpl)frame).next = this;
	((ActivationFrameImpl)frame).top  = top;
	return frame;
    }

    public Object get (Symbol symbol)
    {
	return top.get(symbol);
    }

    public Object get (int slot)
    {
	return args[slot];
    }

    public Object get (int level, int slot)
    {
	if (level == 0) {
	    return get(slot);
	} else {
	    return next.get(level - 1, slot);
	}
    }

    public Object set (Symbol symbol, Object value)
    {
	return top.set(symbol, value);
    }

    public Object set (int slot, Object v)
    {
	return args[slot] = v;
    }

    public Object set (int level, int slot, Object v)
    {
	if (level == 0) {
	    return set(slot, v);
	} else {
	    return next.set(level - 1, slot, v);
	}
    }

    public String toString ()
    {
	String result = "\nActivationFrameImpl:";
	result = toStringThisLevel(result);
	// REVISIT: When I made this an expression it did not work.
	String nextResult;
	if (next == null) {
	    nextResult = "null";
	} else {
	    nextResult = next.toString();
	}
	result += "\n\tnext : " + nextResult;
	return result;
    }

    private String toStringThisLevel (String result)
    {
	for (int i = 0; i < args.length; ++i) {
	    result += "\n\t" + i + " : " + args[i];
	}
	return result;
    }

    public String toStringForBacktrace ()
    {
	return toStringThisLevel("");
    }
}

// End of file.

