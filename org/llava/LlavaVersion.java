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
 * Last Modified : 2004 Sep 03 (Fri) 15:32:27 by Harold Carr.
 */

package org.llava;

public interface LlavaVersion
{
    public LlavaVersion newLlavaVersion ();

    public String getName ();

    public String getYear ();

    public String getMonth ();
    
    public String getDay ();

    public String getOptional ();
}

// End of file.
