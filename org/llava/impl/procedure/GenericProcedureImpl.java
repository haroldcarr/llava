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
 * Created       : 1999 Dec 28 (Tue) 03:42:27 by Harold Carr.
 * Last Modified : 2005 Mar 12 (Sat) 17:32:08 by Harold Carr.
 */

package org.llava.impl.procedure;

import org.llava.F;
import org.llava.Lambda;
import org.llava.Pair;
import org.llava.procedure.GenericProcedure;
import org.llava.procedure.WrapJavaPrimitive;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.DI;
import org.llava.impl.util.List;

public class GenericProcedureImpl
    implements
	GenericProcedure
{
    protected String name;
    private Lambda defaultLambda;
    private static WrapJavaPrimitive wrapJavaPrimitive = null;

    public GenericProcedureImpl ()
    {
    }

    private GenericProcedureImpl (String name)
    {
	this.name = name;
	if (wrapJavaPrimitive == null) {
	    wrapJavaPrimitive = F.newWrapJavaPrimitive();
	}
    }

    private GenericProcedureImpl (Lambda defaultLambda)
    {
	this("");
	this.defaultLambda = defaultLambda;
    }

    public GenericProcedure newGenericProcedure ()
    {
	return new GenericProcedureImpl("");
    }

    public GenericProcedure newGenericProcedure (String name)
    {
	return new GenericProcedureImpl(name);
    }

    public GenericProcedure newGenericProcedure (Lambda defaultLambda)
    {
	return new GenericProcedureImpl(defaultLambda);
    }

    public Object apply (Pair args, Engine engine)
    {
	Object targetObject;

	// These can NOT be a generic call:
	// Calling a no arg procedure: (foo)
	// Calling a procedure with a null argument (foo null)

	if (args == null || (targetObject = args.car()) == null) {
	    return tryDefaultLambdaOrUndefined(args, engine);
	}

	try {
	    Object[] methodArgs = List.toArray((Pair)args.cdr());
	    Object result = 
		DI.invoke(name, targetObject, methodArgs);
	    return wrapJavaPrimitive.wrapJavaPrimitive(result);
	} catch (NoSuchMethodException e) {
	    return tryDefaultLambdaOrUndefined(args, engine);
	} catch (ThreadDeath td) {
	    // REVISIT - do not wrap so it shows up in automatic
	    // jvm stacktrace.  See EngineImpl.
	    throw td;
	} catch (Throwable t) {
	    throw F.newLlavaException(t);
	}
    }

    private Object tryDefaultLambdaOrUndefined (Pair args, Engine engine)
    {
	if (getDefaultLambda() != null) {
	    return getDefaultLambda().apply(args, engine);
	} else {
	    throw F.newUndefinedIdException(name); // REVISIT
	}
    }

    public String getName ()
    {
	return name;
    }

    public String setName (String name)
    {
	return this.name = name;
    }

    // Duplicated in PrimitiveProcedure.
    protected void checkNumArgs (Pair args, int n)
    {
	if (((n == 0) && (args != null)) ||
	    ((n != 0) && ((args == null) || (args.length() != n))))
        {
		throw F.newWrongNumberOfArgumentsException(getName());
	}
    }

    public Lambda getDefaultLambda ()
    {
	return defaultLambda;
    }

    // REVISIT - used by static invoke, and field procedures.
    public static WrapJavaPrimitive getWrapJavaPrimitive ()
    {
	return wrapJavaPrimitive;
    }

    public String toString ()
    {
	// REVISIT: duplicated in GenericProcedureImpl, LambdaImpl, SyntaxImpl
	String result = 
	    "{generic " 
	    + ((getDefaultLambda() != null) ? getDefaultLambda().toString()
	                                    : name)
	    + "}";
	return result;
    }
}

// End of file.
