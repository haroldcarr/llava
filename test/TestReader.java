/**
 * Created       : 1999 Dec 20 (Mon) 21:37:28 by Harold Carr.
 * Last Modified : 2000 Jan 16 (Sun) 08:55:18 by Harold Carr.
 */

package testLava;

import lava.F;
import lava.io.LavaReader;
import lava.lang.types.Pair;
import lava.util.List;

public class TestReader
{
    public static void main (String[] av)
    {
	testReader();
    }

    public static Object readFromString (String string)
    {
	LavaReader reader = F.newLavaReader();
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
    }
}

