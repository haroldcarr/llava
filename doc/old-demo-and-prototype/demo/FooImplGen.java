;;;; Copyright (c) 1997 - 2004 Harold Carr
;;;;
;;;; This work is licensed under the Creative Commons Attribution License.
;;;; To view a copy of this license, visit 
;;;;   http://creativecommons.org/licenses/by/2.0/
;;;; or send a letter to
;;;;   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA
;;;;---------------------------------------------------------------------------


package hc.llava;

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
Scheme.procedure("hc.llava.FooImplGen.barImpl").apply(Cons.list(this, x));
}
}

// End of file.
