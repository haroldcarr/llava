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
 * Created       : 2000 Jan 19 (Wed) 17:30:46 by Harold Carr.
 * Last Modified : 2004 Sep 06 (Mon) 10:58:39 by Harold Carr.
 */

package org.llava.impl;

import org.llava.LlavaVersion;

public class LlavaVersionImpl
    implements
	LlavaVersion
{
    public LlavaVersionImpl ()
    {
    }

    public LlavaVersion newLlavaVersion ()
    {
	return new LlavaVersionImpl();
    }

    public String getName    ()  { return "llava"; }

    public String getYear    ()  { return "2004"; }

    public String getMonth   ()  { return "09"; }
    
    public String getDay     ()  { return "06"; }

    public String getOptional()  { return "alpha"; }

    public String toString ()
    {
	return 
	    getName() + " version " + dateOnly() + sep + getOptional();
    }

    private String sep = "-";

    private String dateOnly ()
    {
	return	getYear() + sep + getMonth() +  sep + getDay();
    }

    public static void main (String[] av)
    {
	String result = null;
	LlavaVersionImpl llavaVersion = new LlavaVersionImpl();

	switch (av.length) {
	case 0 : 
	    result = llavaVersion.toString();
	    break;
	case 1 :
	    if (av[0].equals("dateOnly")) {
		result = llavaVersion.dateOnly();
		break;
	    }
	default :
	    System.err.println(
	       LlavaVersionImpl.class.getName() + ": " + "[ dateOnly ]");
	    System.exit(-1);
	}
	System.out.println(result);
    }
}

// End of file.
