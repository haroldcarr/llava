//
// Created       : 2000 Oct 23 (Mon) 17:35:17 by Harold Carr.
// Last Modified : 2001 Mar 04 (Sun) 11:24:07 by Harold Carr.
//

package testLava.proto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;

public interface Namespace
{
    public Namespace newNamespace ();

    /**
     * "package" switches to the given package/class.
     * If that package/class does not exist then it is created.
     */
    public Namespace _package (Object packagePathAndName, Object className);

    /**
     * "import":
     * - creates a namespace if it doesn't exist.
     * - if namespace has associated Lava file it loads it into that namespace
     *   if not already loaded or if it has been touched since last loaded.
     * - if namespace has associated Java class it loads it
     *   if not already loaded and creates procedures in that namespace
     *   for static methods, REVISIT THIS DESCRIPTION.
     */
    public String _import (Object classPathAndName);

    /**
     * Sets the value of the given identifier in the current namespace to
     * the given value.
     * Returns the given value.
     */
    public Object set (Object identifier, Object val);

    /**
     * Gets the value of the given identifier:
     * - if .<id> from the root namespace.
     * - if <foo>.<bar>.<id> from the specified namespace.
     * - if <id> then search from current through its reference list.
     */
    public Object get (Object identifier);

}

// End of file.
