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
 * Created       : 1999 Dec 22 (Wed) 05:25:06 by Harold Carr.
 * Last Modified : 2000 Jan 25 (Tue) 17:15:16 by Harold Carr. 
 */

package org.llava.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;

import org.llava.LlavaVersion;
import org.llava.impl.LlavaVersionImpl;
import org.llava.Repl;
import org.llava.impl.ReplImpl;

import org.llava.compiler.Compiler;

import org.llava.io.LlavaEOF;
import org.llava.io.LlavaEOFImpl;
import org.llava.io.LlavaReader;
import org.llava.impl.io.LlavaReaderImpl;

import org.llava.lang.exceptions.LlavaException;
import org.llava.lang.exceptions.BacktraceHandler;

import org.llava.lang.types.Pair;
import org.llava.lang.types.PairImpl;
import org.llava.lang.types.Symbol;
import org.llava.lang.types.SymbolImpl;

import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.runtime.LlavaRuntime;

/**
 * Factory for llava.* pluggable classes. <p>
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

    // NOTE: if you change the following packag names you will probably
    // need to update testNamespaceInternal.lva.

    // llava package containing built-in procedures.
    public static String llavaPackageName() {
	return "org.llava";
    }

    // package in which repl starts
    public static String initialReplPackageName() {
	return "llava-";
    }

    // LlavaEOF
    private static LlavaEOF llavaEOFFactory = null; // new LlavaEOFImpl();

    public static LlavaEOF newLlavaEOF() {
	if (llavaEOFFactory == null) {
	    llavaEOFFactory = 
		(LlavaEOF)
		createFromPropertiesOrUseDefault("org.llava.io.LlavaEOFClassName",
						 "org.llava.io.LlavaEOFImpl");
	}
        return llavaEOFFactory.newLlavaEOF();
    }

    // LlavaException
    private static LlavaException llavaExceptionFactory = new LlavaException();

    public static LlavaException newLlavaException(Throwable t) {
        return llavaExceptionFactory.newLlavaException(t);
    }

    public static LlavaException newLlavaException(String msg) {
        return llavaExceptionFactory.newLlavaException(msg);
    }

    // LlavaReader
    private static LlavaReader llavaReaderFactory = new LlavaReaderImpl();

    public static LlavaReader newLlavaReader() {
        return llavaReaderFactory.newLlavaReader();
    }

    public static LlavaReader newLlavaReader(Reader in) {
        return llavaReaderFactory.newLlavaReader(in);
    }

    // LlavaVersion
    private static LlavaVersion llavaVersionFactory = new LlavaVersionImpl();

    public static LlavaVersion newLlavaVersion() {
        return llavaVersionFactory.newLlavaVersion();
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

    public static Repl newRepl() {
	return replFactory.newRepl();
    }

    public static Repl newRepl(InputStream in, OutputStream out, OutputStream err) {
	return replFactory.newRepl(in, out, err);
    }

    public static Repl newRepl(LlavaReader reader, PrintWriter out, PrintWriter err, LlavaRuntime runtime, Compiler compiler) {
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

