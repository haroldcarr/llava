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
 * Created       : 1999 Dec 20 (Mon) 21:37:28 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:48 by Harold Carr.
 */

package test;

import org.llava.impl.F;
import org.llava.io.LlavaReader;
import org.llava.lang.types.Pair;
import org.llava.impl.util.List;

public class TestReader
{
    public static void main (String[] av)
    {
	testReader();
    }

    public static Object readFromString (String string)
    {
	LlavaReader reader = F.newLlavaReader();
	try {
	    Object result   = reader.read(string);
	    Test.dsop("read: " + result.toString());
	    return result;
	} catch (Throwable t) {
	    return t;
	}
    }

    public static Object car (Object x)
    {
	return ((Pair)x).car();
    }

    public static Object cdr (Object x)
    {
	return ((Pair)x).cdr();
    }

    public static void testReader ()
    {
	Test.dsop("begin: testReader");

	Object v;
	Object v2;

	v = readFromString("(lambda (x) (quote (list (2 . 6.9) . (1 . 3))))");
	Test.check("1", 
		   new Integer(2),
		   car (car (cdr (car (cdr (car (cdr (cdr (v)))))))));
	Test.check("2", 
		   new Double(6.9),
		   cdr (car (cdr (car (cdr (car (cdr (cdr (v)))))))));

	v2 = readFromString("(lambda . ((x . null) . ((quote . ((list . ((2 . 6.9) . (1 . 3))) . null)) . null)))"
			    );
	Test.check("3", v, v2);

	v = readFromString("(quote (1))");
	v2 = readFromString("(quote . ((1 . null) . null))");
	Test.check("4", v, v2);

	v = readFromString("(1 2 3 4)");
	v2 = readFromString("(1 . (2 . (3 . (4 . null))))");
	Test.check("5", v, v2);

	v = readFromString("(1 2 (3 4) (5 . 6) 7)");
	v2 = readFromString("(1 . (2 . ((3 . (4 . null)) . ((5 . 6) . (7 . null)))))"
		);
	Test.check("6", v, v2);

	v = readFromString("(1 2 (3 4) (5 . 6) 7 . 8)");
	v2 = readFromString("(1 . (2 . ((3 . (4 . null)) . ((5 . 6) . (7 . 8)))))"
			    );
	Test.check("7", v, v2);

	v = readFromString("(if true false true)");
	Test.check("8", F.newBoolean(true), car(cdr(v)));

	Test.check("9", 
		   List.list(new Integer(1),
			     ".",
			     new Integer(2)),
		   readFromString("(1 \".\" 2)"));
	Test.check("9.1", 
		   F.cons(new Integer(1),
			  new Integer(2)),
		   readFromString("(1 . 2)"));

	Test.dsop("end: testReader");
    }
}

