
package hc.lava;

// Start of imports needed by framework.
import com.ibm.jikes.skij.Cons;
import com.ibm.jikes.skij.Scheme;
import com.ibm.jikes.skij.SchemeException;
// End of imports needed by framework.


public class FooImplGen
extends
Foo
{
public  String bar(String x)
throws
SchemeException
{
return
(String) 
Scheme.procedure("hc.lava.FooImplGen.barImpl").apply(Cons.list(this, x));
}
}

// End of file.
