/**
 * Created       : 1999 Dec 20 (Mon) 00:07:05 by Harold Carr.
 * Last Modified : 2001 Mar 14 (Wed) 22:25:29 by Harold Carr.
 */

package testLava;

public class Test {

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

    public static void check (String msg, Object shouldBe, Object is)
    {
	if (shouldBe == null) {
	    if (shouldBe == is) {
		psop(msg + " : " + is);
	    } else {
		bad(msg, shouldBe, is);
	    }
	} else if (! shouldBe.equals(is)) {
	    bad(msg, shouldBe, is);
	} else {
	    psop(msg + " : " + is);
	}
    }

    public static void bad (String msg, Object shouldBe, Object is)
    {
	numberOfFailures++;
	System.err.println("**BAD*******************************************");
	System.err.println(msg + " :" +
			   " should be: " + shouldBe + 
			   " but got : "  + is);
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

