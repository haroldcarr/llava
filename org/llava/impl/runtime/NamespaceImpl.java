//
// Created       : 2000 Oct 23 (Mon) 17:35:17 by .
// Last Modified : 2004 Dec 01 (Wed) 10:15:34 by Harold Carr.
//

package testLava.proto;

import java.util.Hashtable;
import java.util.Vector;

public class Namespace
{
    private String name;
    private Hashtable map;
    private Vector refList;

    public Namespace (String name, Namespace topLevel)
    {
	this.name    = name;
	this.map     = new Hashtable();
	this.refList = new Vector();
	refList.add(0, this); // Self at front.
	refList.add(topLevel); // topLevel always at end.
    }

    public String getName() { return name; }
    public Hashtable getMap() { return map; }
    public Vector getRefList() { return refList; }
}

// End of file.
