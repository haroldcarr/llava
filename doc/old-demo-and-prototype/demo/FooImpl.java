package hc.lava;

import com.ibm.jikes.skij.*;

public class FooImpl implements Foo
{
    public String bar(String x) 
	throws SchemeException
    {
	return (String) Scheme.procedure("barImpl").apply(Cons.list(this, x));
    }
}
