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
 * Created       : 2000 Jan 11 (Tue) 22:35:58 by Harold Carr.
 * Last Modified : 2004 Dec 07 (Tue) 18:57:53 by Harold Carr.
 */

package org.llava.impl.io;

import org.llava.io.LlavaEOF;

public class LlavaEOFImpl
    implements
	LlavaEOF
{
    private LlavaEOF singleton = null;

    public LlavaEOFImpl ()
    {
	singleton = this;
    }

    public LlavaEOF newLlavaEOF ()
    {
	if (singleton == null) {
	    singleton = new LlavaEOFImpl();
	}
	return singleton;
    }

    public String toString ()
    {
	return "#!EOF";
    }
}

// End of file.
