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
 * Created       : 1999 Dec 23 (Thu) 03:33:12 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 19:30:37 by Harold Carr.
 */

package org.llava.impl.runtime;

import org.llava.runtime.ActivationFrame;
import org.llava.runtime.Engine;

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
