//
// Created       : 2000 Oct 23 (Mon) 17:35:17 by Harold Carr.
// Last Modified : 2001 Mar 02 (Fri) 14:23:31 by Harold Carr.
//

package testLava.proto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import lava.F;
import lava.lang.types.Pair;

public interface Namespace
{
    public Namespace newNamespace ();

    public Namespace _package (Object p, Object c);

}

// End of file.
