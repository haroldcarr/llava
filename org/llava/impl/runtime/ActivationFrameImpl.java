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
 * Created       : 1999 Dec 21 (Tue) 00:12:36 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:33:41 by Harold Carr.
 */

package org.llava.impl.runtime.env;

import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;
import org.llava.impl.util.List;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.impl.runtime.FR;
import org.llava.impl.runtime.env.Namespace;

public class ActivationFrameImpl 
    implements
	ActivationFrame
{
    private ActivationFrame     next = null;
    private Object[]            args;
    private EnvironmentTopLevel top;
    private Namespace           namespace;

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

    private ActivationFrameImpl (Pair values, Namespace namespace)
    {
	this.namespace = namespace;
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

    public ActivationFrame extend (Pair args, Namespace namespace)
    {
	return extend(new ActivationFrameImpl(args, namespace));
    }

    public ActivationFrame extend (ActivationFrame frame)
    {
	((ActivationFrameImpl)frame).next = this;
	((ActivationFrameImpl)frame).top  = top;
	return frame;
    }

    public Object get (Symbol identifier)
    {
	if (namespace != null) {
	    if (namespace.isDottedP(identifier)) {
		return namespace.refDotted(identifier);
	    }
	    return namespace.refNotDotted(identifier);
	} 
	return top.get(identifier);
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

    public Object set (Symbol identifier, Object value)
    {
	if (namespace != null) {
	    if (namespace.isDottedP(identifier)) {
		return ((EnvironmentTopLevel)namespace).set(identifier, value);
	    }
	    return namespace.setNotDotted(identifier, value);
	} 
	return top.set(identifier, value);
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

    public EnvironmentTopLevel getEnvironmentTopLevel ()
    {
	return top;
    }

    public Namespace getNamespace ()
    {
	return namespace;
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

