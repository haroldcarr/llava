/**
 * Created       : 1999 Dec 23 (Thu) 00:11:38 by Harold Carr.
 * Last Modified : 1999 Dec 28 (Tue) 06:14:31 by Harold Carr.
 */

package testLava;

import java.lang.reflect.Method;

public class TestReflection 
{
    public static void testReflection ()
    {
	testUnwrapViaReflection();
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

