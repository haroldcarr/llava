/**
 * Created       : @author Peter Norvig, Copyright 1998, http://www.norvig.com/license.html 
 * Last Modified : 2000 Feb 11 (Fri) 06:17:36 by Harold Carr.
 */

package libLava.r1.syntax;

import lava.F;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.util.List;
import libLava.co.Compiler;
import libLava.c1.EnvironmentLexical;
import libLava.r1.FR1;
import libLava.rt.LavaRuntime;
import libLava.r1.Engine;
import libLava.r1.code.Code;

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
	// REVISIT - duplicate of code in LambdaImpl
	return "{" + getClass().getName() + " " + name + "}";
    }
}

// End of file.

