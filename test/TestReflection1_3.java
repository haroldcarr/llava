/**
 * Created       : 2000 Feb 10 (Thu) 23:26:38 by Harold Carr.
 * Last Modified : 2001 Mar 14 (Wed) 21:59:11 by Harold Carr.
 */

package testLava;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestReflection1_3
{
    public static void main (String[] av)
    {
	testReflection1_3();
    }

    public static void testReflection1_3 ()
    {
	Test.dsop("begin: testReflection1_3")
	testProxy();
	Test.dsop("end: testReflection1_3")
    }

    public static void testProxy ()
    {
	try {
	    Class[] interfaces = { Class.forName("java.lang.Comparable") };
	    Comparable proxy = 
		(Comparable)
		Proxy.newProxyInstance(null,
				       interfaces, 
				       new myInvocationHandler(interfaces));
	    System.out.println(proxy.compareTo(interfaces));
	} catch (Exception e) {
	    System.err.println("testProxy: " + e);
	    e.printStackTrace();
	}
    }
}

class myInvocationHandler implements InvocationHandler
{
    private Class[] interfaces;
    public myInvocationHandler (Class[] interfaces)
    {
	this.interfaces = interfaces;
    }
    public Object invoke (Object proxy, Method method, Object[] args)
    {
	System.out.println("proxy" + " " + 
			   method  + " " + 
			   args[0]  + " " +
			   interfaces);
	return new Integer(0);
    }
}

// End of file.

