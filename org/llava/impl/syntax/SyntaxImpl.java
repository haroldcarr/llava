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
 * Created       : @author Peter Norvig, Copyright 1998, http://www.norvig.com/license.html 
 * Last Modified : 2005 Mar 12 (Sat) 17:34:30 by Harold Carr.
 */

package org.llava.impl.syntax;

import org.llava.F;
import org.llava.Pair;
import org.llava.Procedure;
import org.llava.Syntax;
import org.llava.compiler.Compiler;
import org.llava.compiler.EnvironmentLexical;
import org.llava.runtime.LlavaRuntime;
import org.llava.runtime.Engine;

import org.llava.impl.runtime.Code;
import org.llava.impl.util.List;

public abstract class SyntaxImpl
    implements
	Procedure,
	Syntax
{
    protected String name;

    protected SyntaxImpl(String name)
    {
	this.name = name;
    }

    public Object apply (Pair args, Engine engine)
    {
	throw F.newLlavaException("Cannot apply Syntax: " + name);
    }

    public String getName ()
    {
	return name;
    }

    public String setName (String name)
    {
	return this.name = name;
    }

    public String toString ()
    {
	// REVISIT: duplicated in GenericProcedureImpl, LambdaImpl, SyntaxImpl
	return "{syntax " + name + "}";
    }
}

// End of file.

