//
// Created       : 2000 Oct 23 (Mon) 17:35:17 by Harold Carr.
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
    private long fileLastModified; // Non-zero if associated file.
    private boolean classAlreadyImported; // true if associated class.

    public Namespace (String name, Namespace root)
    {
	this.name             = name;
	this.map              = new Hashtable();
	this.refList          = new Vector();
	this.fileLastModified = 0;
	this.classAlreadyImported = false;

	refList.add(0, this); // Self at front.
	refList.add(root);    // root always at end.
    }

    public String getName () 
    {
	return name; 
    }

    public Hashtable getMap ()
    {
	return map; 
    }
    
    public Vector getRefList ()
    {
	return refList; 
    }

    public long setFileLastModified (long lastModified)
    {
	fileLastModified = lastModified;
	return fileLastModified;
    }

    public long getFileLastModified ()
    {
	return fileLastModified; 
    }

    public boolean setClassAlreadyImported (boolean b)
    {
	classAlreadyImported = b; 
	return classAlreadyImported;
    }

    public boolean classAlreadyImported ()
    {
	return classAlreadyImported; 
    }
}

// End of file.
