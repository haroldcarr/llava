;;;; Copyright (c) 1997 - 2004 Harold Carr
;;;;
;;;; This work is licensed under the Creative Commons Attribution License.
;;;; To view a copy of this license, visit 
;;;;   http://creativecommons.org/licenses/by/2.0/
;;;; or send a letter to
;;;;   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA
;;;;---------------------------------------------------------------------------


package hc.llava;
 
import com.ibm.jikes.skij.*;
 
public class FooImpl implements Foo
{
    public String bar(String x) 
	throws SchemeException
    {
	return (String) Scheme.procedure("barImpl").apply(Cons.list(this, x));
    }
}
