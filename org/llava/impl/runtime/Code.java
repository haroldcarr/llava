/**
 * Created       : 1999 Dec 23 (Thu) 03:33:12 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:06:47 by Harold Carr.
 */

package lavaProfile.runtime.code;

import lavaProfile.runtime.env.ActivationFrame;
import lavaProfile.runtime.Engine;

/**
 * A compiler is a factory for Code. <p>
 *
 * REVISIT:
 * I think Java makes it impossible to make Code pluggable.
 */

abstract public class Code
{
    private Object source;

    protected Code ()
    {
    }

    protected Code (Object source) 
    {
	this.source = source;
    }

    abstract public Object run (ActivationFrame frame, Engine eng);

    public Object getSource ()
    {
	return source;
    }

    public String toString ()
    {
	return "{" + getClass().getName() + " " + source.toString() + "}";
    }
}

// End of file.
