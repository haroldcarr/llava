/**
 * Created       : @author Peter Norvig, Copyright 1998, http://www.norvig.com/license.html 
 * Last Modified : 2001 Mar 26 (Mon) 15:24:18 by Harold Carr.
 */

package lavaProfile.runtime.syntax;

import lavaProfile.F;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lavaProfile.util.List;
import lava.compiler.Compiler;
import lavaProfile.compiler.EnvironmentLexical;
import lavaProfile.runtime.FR;
import lava.runtime.LavaRuntime;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.code.Code;

public abstract class Syntax
    implements
	Procedure 
{
    protected String name;

    protected Syntax(String name)
    {
	this.name = name;
    }

    public abstract Code compile (Compiler           compiler,
				  Pair               x, 
				  EnvironmentLexical e, 
				  LavaRuntime        runtime);

    public Object apply (Pair args, Engine engine)
    {
	throw F.newLavaException("Cannot apply Syntax: " + name);
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
	// REVISIT: duplicated in GenericProcedureImpl, LambdaImpl, Syntax
	return "{" + getClass().getName() + " " + name + "}";
    }
}

// End of file.

