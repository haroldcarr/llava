/**
 * Created       : 1999 Dec 28 (Tue) 03:42:27 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:23:57 by Harold Carr.
 */

package lavaProfile.runtime.procedure.generic;

import lavaProfile.F;
import lava.lang.types.Pair;
import lavaProfile.util.List;
import lavaProfile.runtime.Engine;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.procedure.Lambda;
import lavaProfile.runtime.procedure.generic.DI;

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
	    wrapJavaPrimitive = FR.newWrapJavaPrimitive();
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
	try {
	    Object targetObject;
	    // When iterating down a list the final arg will be null.
	    if (args == null || (targetObject = args.car()) == null) {
		throw new NoSuchMethodException("try the defaultLambda");
	    }
	    Object[] methodArgs = List.toArray((Pair)args.cdr());
	    Object result = 
		DI.invoke(name, targetObject, methodArgs);
	    return wrapJavaPrimitive.wrapJavaPrimitive(result);
	} catch (NoSuchMethodException e) {
	    if (getDefaultLambda() != null) {
		return getDefaultLambda().apply(args, engine);
	    } else {
		throw FR.newUndefinedIdException(name); // REVISIT
	    }
	} catch (ThreadDeath td) {
	    // REVISIT - do not wrap so it shows up in automatic
	    // jvm stacktrace.  See EngineImpl.
	    throw td;
	} catch (Throwable t) {
	    throw F.newLavaException(t);
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
		throw FR.newWrongNumberOfArgumentsException(getName());
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
	// REVISIT: duplicated in GenericProcedureImpl, LambdaImpl, Syntax
	String result = "{" + getClass().getName() + " " + name;
	result += 
	    ((getDefaultLambda() != null) 
	     ? " " + getDefaultLambda().toString()
	     : "")
	    + "}";
	return result;
    }
}

// End of file.
