/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/

//
// Created       : 2000 Oct 23 (Mon) 17:35:17 by Harold Carr.
// Last Modified : 2004 Dec 07 (Tue) 18:54:38 by Harold Carr.
//

package org.llava.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.llava.Pair;
import org.llava.Symbol;

public interface Namespace
{
    /**
     * "package" switches to the given package/class.
     * If that package/class does not exist then it is created.
     */

    public Namespace _package (Symbol namespacePathAndClassName);


    /**
     * "import":
     * - creates a namespace if it doesn't exist.
     * - if namespace has associated Llava file it loads it into that namespace
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
