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
 * Created       : 1999 Dec 28 (Tue) 05:43:41 by Harold Carr.
 * Last Modified : 2005 May 20 (Fri) 13:42:17 by Harold Carr.
 */

/*
Each of these says it takes an int.  Some will also take a long.

(sleep (-si 'currentThread 'java.lang.Thread) (new 'java.lang.Integer 1000))
(sleep (-si 'currentThread 'java.lang.Thread) (new 'java.lang.Long 1000))
(-si 'toHexString 'java.lang.Integer (new 'java.lang.Integer 15))
(-si 'toHexString 'java.lang.Integer (new 'java.lang.Long 15))
(compareTo 22 (new 'java.lang.Integer 21))
(compareTo 22 (new 'java.lang.Long 21))
(charAt "abc" (new 'java.lang.Integer 1))
(charAt "abc" (new 'java.lang.Long 1))
949513387412
9.49513387413E11
(load "import.scm")
(require 'org/llava/lib/java/lang/import)
(import "java.lang.System")
(System.currentTimeMillis)
(invoke-static 'java.lang.System 'currentTimeMillis)
(-si 'currentTimeMillis 'java.lang.System)
(define *which* 'skij)
(define *which* 'llava)
(define *which* 'silk)
(define (tt)
  (let ((t (case *which*
	     ((skij) (invoke-static 'java.lang.System 'currentTimeMillis))
	     ((llava) (-si 'currentTimeMillis 'java.lang.System))
	     ((silk) (System.currentTimeMillis))
	     (else (error "NO")))))
    (display (list t (+ t 1)))
    (< t (+ t 1))))
(tt)
 */

package test;

import org.llava.F;
import org.llava.Symbol;
import org.llava.procedure.GenericProcedure;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.UndefinedIdHandler;

import org.llava.impl.runtime.*;
import org.llava.impl.procedure.RI;

// Imports used by PrimNewPrim at bottom.

import org.llava.Pair;
import org.llava.runtime.Engine;

import org.llava.impl.procedure.PrimitiveProcedure;
import org.llava.impl.util.List;

public class TestGeneric
{
    public static void testGeneric ()
    {
	Test.dsop("begin: testGeneric");
	testRI();
	testGenericProcedure();
	Test.dsop("end: testGeneric");
    }

    public static void testGenericProcedure ()
    {
	TestCompilerAndEngine.topEnvironment.setUndefinedIdHandler
	    (new UndefinedIdHandlerImpl() { // REVISIT factory
		    public Object handle(EnvironmentTopLevel env, Symbol id) {
			GenericProcedure gp = F.newGenericProcedure();
			env.set(id, gp);
			return gp;
		    }
		}
            );

	TestCompilerAndEngine.topEnvironment.set(F.newSymbol("new"),
						 new PrimNewPrim());

	Test.check("gen1", 
		   new Integer(123),
		   eval("(new (quote java.lang.Integer) 123)"));

	Test.check("gen2", 
		   new Double(123.0),
		   eval("(doubleValue (new (quote java.lang.Integer) 123))"));

    }

    public static Object eval(String s)
    {
	return TestCompilerAndEngine.eval(s);
    }

    public static void testRI ()
    {
	try {
	    Object v;
	    v = RI.newInstance(Integer.class, new Object[] { "123" });
	    Test.check("dyn1", new Integer(123), v);

	    v = RI.invoke("equals",
			  v,
			  new Object[] { new Integer("123") });
	    Test.check("dyn2", new Boolean(true), v);

	    v = RI.invokeStatic("parseInt",
				Integer.class,
				new Object[] { "F", new Integer(16) });
	    Test.check("dyn3", new Integer(15), v);

	    try {
		 v = RI.invokeStatic("test_i_iill",
				     test.TestGeneric.class,
				     new Object[]{new Integer(1),new Long(2),
						  new Integer(3),new Long(4)});
		 // I would like this to be the case, but no...
		 Test.check("dyn4", new Integer(10), v);
	    } catch (NoSuchMethodException e) {
	    }

	    v = RI.invokeStatic("test_l_iill",
				test.TestGeneric.class,
				new Object[]{new Integer(1),new Integer(2),
					     new Long(3),   new Long(4) });
	    // Note - Llava reader builds Integers but Java builds
	    // both Longs and Integers.
	    Test.check("dyn5", new Long(10), v);

	} catch (Throwable t) {
	    Test.bad("TestGeneric", "this should not happen", t);
	}
    }

    public static int test_i_iill (int i1, int i2, long l1, long l2)
    {
	return (int) (i1 + i2 + l1 + l2);
    }

    public static long test_l_iill (int i1, int i2, long l1, long l2)
    {
	return (long) (i1 + i2 + l1 + l2);
    }
}

class PrimNewPrim
    extends
	PrimitiveProcedure
{
    public PrimNewPrim ()
    {
    }

    public Object apply (Pair args, Engine engine)
    {
	try {
	    // REVISIT - no Symbol type checking (i.e., cast)
	    // because also used from PrimNew which sends it a String.
	    return RI.newInstance(args.car().toString(),
				  List.toArray((Pair)args.cdr()));
	} catch (Throwable t) {
	    throw F.newLlavaException(t);
	}
    }
}

// End of file.

