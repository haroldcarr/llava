package hc.lava;

import com.ibm.jikes.skij.*;

public class Foo
{
    public String fooBar(String a1)
	throws SchemeException
    {
	String ret = "fooBar " + a1;
	System.out.println(ret);
	return ret;
    }
}
