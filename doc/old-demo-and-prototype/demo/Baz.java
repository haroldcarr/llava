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
 * Comment before package.
 * Each line in a string.
 */

package hc.llava;

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
Scheme.procedure("hc.llava.Baz.BazImpl").apply(Cons.list(this));
}

/*
 * Static method with no args.
 */

public static void barNo()
throws
SchemeException
{
Scheme.procedure("hc.llava.Baz.barNoImpl").apply(Nil.nil);
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
Scheme.procedure("hc.llava.Baz.barYesImpl").apply(Cons.list(x, y));
}

//
// Virtual method with no args.
//

public  void bazNo()
throws
SchemeException
{
Scheme.procedure("hc.llava.Baz.bazNoImpl").apply(Cons.list(this));
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
Scheme.procedure("hc.llava.Baz.bazYesImpl").apply(Cons.list(this, x));
}
}

// End of file.
