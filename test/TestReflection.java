/**
 * Created       : 1999 Dec 23 (Thu) 00:11:38 by Harold Carr.
 * Last Modified : 2001 Mar 14 (Wed) 22:00:40 by Harold Carr.
 */

package testLava;

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

