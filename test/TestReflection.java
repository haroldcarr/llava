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
 * Created       : 1999 Dec 23 (Thu) 00:11:38 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:51 by Harold Carr.
 */

package test;

import java.lang.reflect.Method;

public class TestReflection 
{
    public static void testReflection ()
    {
	Test.dsop("begin: testReflection");
	testUnwrapViaReflection();
	Test.dsop("end: testReflection");
    }

    public static void testUnwrapViaReflection()
    {
	try {
	    Class[] parmClasses = { int.class, double.class };
	    Method fooMethod = 
		TestReflection.class.getMethod("foo", parmClasses);
	    Object[] args = { new Integer(-69), new Double(6.9) };
	    Object result = fooMethod.invoke(null, args);
	    Test.check("ref1", Double.class, result.getClass());
	} catch (Exception e) {
	    System.err.println("testUnwrapViaReflection: " + e);
	}
    }

    public static double foo (int i, double d)
    {
	Test.dsop(i + " " + d);
	return i + d;
    }
}

// End of file.

