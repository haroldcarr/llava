/** 
 * Created       : 1999 Dec 22 (Wed) 05:25:06 by Harold Carr.
 * Last Modified : 2000 Jan 25 (Tue) 17:15:16 by Harold Carr. 
 */

package lava;

import java.io.PrintWriter;
import java.io.Reader;
import lava.LavaVersion;
import lava.LavaVersionImpl;
import lava.Repl;
import lava.ReplImpl;
import lava.io.LavaEOF;
import lava.io.LavaEOFImpl;
import lava.io.LavaReader;
import lava.io.LavaReaderImpl;
import lava.lang.exceptions.LavaException;
import lava.lang.exceptions.BacktraceHandler;
import lava.lang.types.Pair;
import lava.lang.types.PairImpl;
import lava.lang.types.Symbol;
import lava.lang.types.SymbolImpl;
import libLava.co.Compiler;
import libLava.rt.EnvironmentTopLevel;
import libLava.rt.Evaluator;
import libLava.rt.LavaRuntime;

/**
 * Factory for lava.* pluggable classes. <p>
 * Currently the implementation classes are wired in.
 * Later they will be pluggable by specifying class names via properties.
 */

public class F {

    public static Object createFromPropertiesOrUseDefault(
				      String propertyNameToFind,
				      String classNameDefault)
    {
	String className = System.getProperty(propertyNameToFind,
					      classNameDefault);
	Object object = null;
	try {
	    object = Class.forName(className).newInstance();
	} catch (Exception e) {
	    System.err.println("F.createFromPropertiesOrUseDefault(" +
			       propertyNameToFind + ", " +
			       classNameDefault + "), " +
			       "exception while creating: " + className +
			       " " + e);
	    System.exit(-1);
	}
	return object;
    }

    // LavaEOF
    private static LavaEOF lavaEOFFactory = null; // new LavaEOFImpl();

    public static LavaEOF newLavaEOF() {
	if (lavaEOFFactory == null) {
	    lavaEOFFactory = 
		(LavaEOF)
		createFromPropertiesOrUseDefault("lava.io.LavaEOFClassName",
						 "lava.io.LavaEOFImpl");
	}
        return lavaEOFFactory.newLavaEOF();
    }

    // LavaException
    private static LavaException lavaExceptionFactory = new LavaException();

    public static LavaException newLavaException(Throwable t) {
        return lavaExceptionFactory.newLavaException(t);
    }

    public static LavaException newLavaException(String msg) {
        return lavaExceptionFactory.newLavaException(msg);
    }

    // LavaReader
    private static LavaReader lavaReaderFactory = new LavaReaderImpl();

    public static LavaReader newLavaReader() {
        return lavaReaderFactory.newLavaReader();
    }

    public static LavaReader newLavaReader(Reader in) {
        return lavaReaderFactory.newLavaReader(in);
    }

    // LavaVersion
    private static LavaVersion lavaVersionFactory = new LavaVersionImpl();

    public static LavaVersion newLavaVersion() {
        return lavaVersionFactory.newLavaVersion();
    }

    // Pair
    private static Pair pairFactory = new PairImpl();

    public static Pair newPair(Object car, Object cdr) {
        return pairFactory.newPair(car, cdr);
    }

    public static Pair cons(Object car, Object cdr) {
        return pairFactory.newPair(car, cdr);
    }

    // Repl
    private static Repl replFactory = new ReplImpl();

    public static Repl newRepl(LavaReader reader, PrintWriter out, PrintWriter err, LavaRuntime runtime, Compiler compiler) {
        return replFactory.newRepl(reader, out, err, runtime, compiler);
    }

    // Symbol
    private static Symbol symbolFactory = new SymbolImpl();

    public static Symbol newSymbol(String name) {
        return symbolFactory.newSymbol(name);
    }

    public static Symbol newSymbolUninterned(String name) {
        return symbolFactory.newSymbolUninterned(name);
    }

    // ----------------------------------------------------------------------
    // Factory operations for Java primitives.
    // ----------------------------------------------------------------------

    // Boolean
    public static Boolean newBoolean(boolean x) {
        return x ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Boolean newBoolean(Boolean x) {
        return newBoolean(x.booleanValue());
    }

    // Byte
    public static Byte newByte(byte x) {
        return new Byte(x);
    }

    public static Byte newByte(Byte x) {
        return x;
    }

    // Character
    public static Character newCharacter(char x) {
        return new Character(x);
    }

    public static Character newCharacter(Character x) {
        return x;
    }

    // Short
    public static Short newShort(short x) {
        return new Short(x);
    }

    public static Short newShort(Short x) {
        return x;
    }

    // Integer
    // REVISIT: cache common range.
    public static Integer newInteger(int x) {
        return new Integer(x);
    }

    public static Integer newInteger(Integer x) {
        return x;
    }

    // Long
    public static Long newLong(long x) {
        return new Long(x);
    }

    public static Long newLong(Long x) {
        return x;
    }

    // Float
    public static Float newFloat(float x) {
        return new Float(x);
    }

    public static Float newFloat(Float x) {
        return x;
    }

    // Double
    public static Double newDouble(double x) {
        return new Double(x);
    }

    public static Double newDouble(Double x) {
        return x;
    }

    public static Double newDouble(String x) {
        return new Double(x);
    }

    // Void
    public static Void newVoid(Void x) {
        return x;
    }
}

// End of file.

