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
 * Created       : 2000 Feb 10 (Thu) 23:26:38 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:53 by Harold Carr.
 */

package test;

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

