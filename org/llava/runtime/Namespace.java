//
// Created       : 2000 Oct 23 (Mon) 17:35:17 by Harold Carr.
// Last Modified : 2001 Mar 26 (Mon) 15:06:59 by Harold Carr.
//

package lavaProfile.runtime.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import lava.lang.types.Pair;
import lava.lang.types.Symbol;

public interface Namespace
{
    /**
     * "package" switches to the given package/class.
     * If that package/class does not exist then it is created.
     */

    public Namespace _package (Symbol packagePathAndName, Symbol className);


    /**
     * "import":
     * - creates a namespace if it doesn't exist.
     * - if namespace has associated Lava file it loads it into that namespace
     *   if not already loaded or if it has been touched since last loaded.
     * - if namespace has associated Java class it loads it
     *   if not already loaded and creates procedures in that namespace
     *   for static methods, REVISIT THIS DESCRIPTION.
     */

    public String _import (Symbol classPathAndName);


    /**
     * Used to seal a package.  This means it is illegal to import
     * or set values in a sealed package.
     */

    public boolean setIsSealed (boolean b);

    /**
     * So you can find one and seal it.
     */

    public Namespace findNamespace (Symbol name);


    /**
     * Used to set prompt.
     * Used to grab current namespace when creating a lambda.
     */

    public Namespace getCurrentNamespace ();

    /**
     * Used to set prompt.
     */

    public String getName ();

    /**
     * Used to enable short names for "new", "catch", "instanceof"
     * when import is used.
     */

    public String getFullNameForClass (Object className);

    /**
     *
     */
    public boolean isDottedP (Symbol identifier);

    /**
     *
     */
    public Object setNotDotted(Symbol identifier, Object val);

    /**
     *
     */
    public Object refNotDotted (Symbol identifier);

    /**
     *
     */
    public Object refDotted (Symbol identifier);
}

// End of file.
