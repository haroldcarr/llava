/**
 * Created       : 1999 Dec 28 (Tue) 03:42:27 by Harold Carr.
 * Last Modified : 2000 Feb 26 (Sat) 22:53:06 by Harold Carr.
 */

package libLava.r1.procedure.generic;

import lava.F;
import lava.lang.types.Pair;
import lava.util.List;
import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.procedure.Lambda;
import libLava.r1.procedure.generic.DI;

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
	    wrapJavaPrimitive = FR1.newWrapJavaPrimitive();
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
		throw FR1.newUndefinedIdException(name); // REVISIT
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
		throw FR1.newWrongNumberOfArgumentsException(getName());
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
	// REVISIT - duplicate of code in LambdaImpl.
	return "{" + getClass().getName() + " " + name + "}";
    }
}

// End of file.
