

/**
 * Comment before package.
 * Each line in a string.
 */

package hc.lava;

/*
 * Comment before import.
 * Another line.
 */


// Start of imports needed by framework.
import com.ibm.jikes.skij.Cons;
import com.ibm.jikes.skij.Scheme;
import com.ibm.jikes.skij.SchemeException;
// End of imports needed by framework.

import com.ibm.jikes.skij.*;
import java.util.Hashtable;

//
// Comment before class.
// Another line.
//


public class Baz
extends
Foo
implements
Wombat
{

/**
 * Constructor with no args.
 */

public   Baz()
throws
SchemeException
{
Scheme.procedure("hc.lava.Baz.BazImpl").apply(Cons.list(this));
}

/*
 * Static method with no args.
 */

public static void barNo()
throws
SchemeException
{
Scheme.procedure("hc.lava.Baz.barNoImpl").apply(Nil.nil);
}

/*
 * Static method with args.
 */

public static Object barYes(Object x, Hashtable y)
throws
SchemeException
{
return
(Object) 
Scheme.procedure("hc.lava.Baz.barYesImpl").apply(Cons.list(x, y));
}

//
// Virtual method with no args.
//

public  void bazNo()
throws
SchemeException
{
Scheme.procedure("hc.lava.Baz.bazNoImpl").apply(Cons.list(this));
}

//
// Virtual method with args.
//

public  Object bazYes(Object x)
throws
SomeException,
SchemeException
{
return
(Object) 
Scheme.procedure("hc.lava.Baz.bazYesImpl").apply(Cons.list(this, x));
}
}

// End of file.
