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
 * Created       : 1999 Dec 20 (Mon) 00:07:05 by Harold Carr.
 * Last Modified : 2011 Jan 04 (Tue) 12:49:27 by carr.
 */

package test;

import org.llava.F;
import org.llava.impl.util.List;

public class Test {

    public static Object TRUE = F.newBoolean(true);

    public static boolean printResults    = false;
    public static boolean printProgress   = false;
    //    public static boolean printResults    = true;
    //    public static boolean printProgress   = true;
    public static boolean printStackTrace = false;

    public static int numberOfFailures;

    public static void main (String[] av)
    {
	beginTest();
	TestEnv.testEnv();
	TestReader.testReader();
	TestReflection.testReflection();
	TestCompilerAndEngine.testCompilerAndEngine();
	TestGeneric.testGeneric();
	endTest();

	// This must happen outside the begin/end.
	TestTop.testTop();
    }

    public static void beginTest()
    {
	numberOfFailures = 0;
    }

    public static void endTest()
    {
	if (numberOfFailures == 0) {
	    sop("PASSED");
	} else {
	    sop(numberOfFailures + " tests failed.");
	}
    }

    public static void throwExceptionIfFailures()
	throws Exception
    {
	if (numberOfFailures > 0) {
	    throw new Exception("numberOfFailures = " + numberOfFailures);
	}
    }

    public static void exitIfFailures()
    {
	if (numberOfFailures > 0) {
	    System.exit(1);
	}
    }

    public static void check (String msg, Object shouldBe, Object is)
    {
	if (TRUE.equals(List.equalp(shouldBe, is))) {
	    psop(msg + " : " + is);
	} else {
	    bad(msg, shouldBe, is);
	}
    }

    public static void check (String msg, Object trueOrFalse, Object shouldBe, Object is)
    {
	if (TRUE.equals(trueOrFalse)) {
	    psop(msg + " : " + is);
	} else {
	    bad(msg, shouldBe, is);
	}
    }

    public static void bad (String msg, Object shouldBe, Object is)
    {
	numberOfFailures++;
	System.err.println("**BAD*******************************************");
	System.err.println(msg + " :\n" +
			   "    should be :\n" + 
                           "\t\t" + shouldBe + "\n" +
			   "    but got :\n" +
                           "\t\t" + is);
	System.err.println("************************************************");
    }

    public static void printStackTrace (Throwable t)
    {
	if (printStackTrace) {
	    t.printStackTrace(System.err);
	}
    }

    public static void sop (String msg)
    {
	System.out.println(msg);
    }

    public static void dsop (String msg)
    {
	if (printResults) {
	    sop(msg);
	}
    }

    public static void psop (String msg)
    {
	if (printProgress) {
	    sop(msg);
	}
    }

}

// End of file.

