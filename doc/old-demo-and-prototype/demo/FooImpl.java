package hc.util;

import com.ibm.jikes.skij.*;

public class FooImpl // implements Foo
{
    public Object bar(Object x) 
	throws SchemeException
    {
	System.out.println("bar::: " + x);
	Procedure p = Scheme.procedure("barImpl");
	System.out.println("p::: " + p);
	Cons c = Cons.list(this, x);
	//Cons c = Cons.list(x);
	System.out.println("c::: " + c);
	return p.apply(c);
	//return p.apply(Nil.nil);
    }
}
