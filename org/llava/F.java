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

package org.llava;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import org.llava.Lambda;
import org.llava.impl.LambdaImpl;
import org.llava.LlavaException;
import org.llava.LlavaVersion;
import org.llava.impl.LlavaVersionImpl;
import org.llava.Pair;
import org.llava.impl.PairImpl;
import org.llava.Repl;
import org.llava.impl.ReplImpl;
import org.llava.Symbol;
import org.llava.impl.SymbolImpl;
import org.llava.UndefinedIdException;
import org.llava.WrongNumberOfArgumentsException;

import org.llava.compiler.Compiler;
import org.llava.impl.compiler.CompilerImpl;
import org.llava.compiler.EnvironmentLexical;
import org.llava.impl.compiler.EnvironmentLexicalImpl;

import org.llava.io.LlavaEOF;
import org.llava.impl.io.LlavaEOFImpl;
import org.llava.io.LlavaReader;
import org.llava.impl.io.LlavaReaderImpl;
import org.llava.io.LlavaWriter;
import org.llava.impl.io.LlavaWriterImpl;

import org.llava.procedure.GenericProcedure;
import org.llava.impl.procedure.GenericProcedureImpl;

import org.llava.procedure.WrapJavaPrimitive;
import org.llava.impl.procedure.WrapJavaPrimitiveImpl;

import org.llava.runtime.BacktraceHandler;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.Evaluator;
import org.llava.runtime.LlavaRuntime;

import org.llava.runtime.ActivationFrame;
import org.llava.runtime.BacktraceHandler;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.runtime.EnvTopLevelInit;
import org.llava.runtime.Evaluator;
import org.llava.runtime.LlavaRuntime;
import org.llava.runtime.Namespace;
import org.llava.runtime.UndefinedIdHandler;

import org.llava.impl.runtime.ActivationFrameImpl;
import org.llava.impl.runtime.BacktraceHandlerImpl;
import org.llava.impl.runtime.EngineImpl;
import org.llava.impl.runtime.EngineStepImpl; // REVISIT - remove
import org.llava.impl.runtime.EnvTopLevelInitImpl;
import org.llava.impl.runtime.LlavaRuntimeImpl;
import org.llava.impl.runtime.UndefinedIdHandlerImpl;

import org.llava.impl.procedure.PrimEqP;
import org.llava.impl.procedure.PrimEqualP;
import org.llava.impl.procedure.PrimImport;
import org.llava.impl.procedure.PrimNew;
import org.llava.impl.procedure.PrimNewPrim;
import org.llava.impl.procedure.PrimPackage;
import org.llava.impl.procedure.PrimSynchronized;
import org.llava.impl.procedure.PrimThrow;
import org.llava.impl.procedure.PrimTryCatchFinally;

import org.llava.impl.procedure.Prim_BitAnd;
import org.llava.impl.procedure.Prim_BitOr;
import org.llava.impl.procedure.Prim_Divide;
import org.llava.impl.procedure.Prim_EQ;
import org.llava.impl.procedure.Prim_GE;
import org.llava.impl.procedure.Prim_GT;
import org.llava.impl.procedure.Prim_LT;
import org.llava.impl.procedure.Prim_LE;
import org.llava.impl.procedure.Prim_Minus;
import org.llava.impl.procedure.Prim_Modulo;
import org.llava.impl.procedure.Prim_Plus;
import org.llava.impl.procedure.Prim_Times;

import org.llava.impl.procedure.PrimCurrentTimeMillis;
import org.llava.impl.procedure.PrimInstanceof;
import org.llava.impl.procedure.PrimNot;

import org.llava.impl.procedure.PrimAppend;
import org.llava.impl.procedure.PrimApply;
import org.llava.impl.procedure.PrimCallCC;
import org.llava.impl.procedure.PrimCons;
import org.llava.impl.procedure.PrimDefGenInternal;
import org.llava.impl.procedure.PrimEval;
import org.llava.impl.procedure.PrimField;
import org.llava.impl.procedure.PrimInvoke;
import org.llava.impl.procedure.PrimLength;
import org.llava.impl.procedure.PrimList;
import org.llava.impl.procedure.PrimList_;
import org.llava.impl.procedure.PrimLoad;
import org.llava.impl.procedure.PrimNewThread;
import org.llava.impl.procedure.PrimStaticField;
import org.llava.impl.procedure.PrimStaticInvoke;
import org.llava.impl.procedure.PrimStringAppend;

import org.llava.impl.procedure.PrimForEach;
import org.llava.impl.procedure.PrimMap;
import org.llava.impl.procedure.PrimNullP;
import org.llava.impl.procedure.PrimPairP;
import org.llava.impl.procedure.PrimString2Symbol;
import org.llava.impl.procedure.PrimSymbolP;

import org.llava.impl.runtime.Code;
import org.llava.impl.runtime.CodeApplication;
import org.llava.impl.runtime.CodeApplicationArgs;
import org.llava.impl.runtime.CodeAssignment;
import org.llava.impl.runtime.CodeAssignmentDeep;
import org.llava.impl.runtime.CodeAssignmentTopLevel;
import org.llava.impl.runtime.CodeIf;
import org.llava.impl.runtime.CodeLambda;
import org.llava.impl.runtime.CodeLiteral;
import org.llava.impl.runtime.CodeReference;
import org.llava.impl.runtime.CodeReferenceDeep;
import org.llava.impl.runtime.CodeReferenceTopLevel;
import org.llava.impl.runtime.CodeSchemeIf;
import org.llava.impl.runtime.CodeSequence;

import org.llava.impl.syntax.SyntaxDefineSyntax;


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

    ////////////////////////////////////////////////////////////////////////
    //
    // llava types and top-level objects.
    //
    ////////////////////////////////////////////////////////////////////////

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
						 "org.llava.impl.io.LlavaEOFImpl");
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

    // LlavaWriter
    private static LlavaWriter llavaWriterFactory = new LlavaWriterImpl();

    public static LlavaWriter newLlavaWriter() {
        return llavaWriterFactory.newLlavaWriter();
    }

    public static LlavaWriter newLlavaWriter(PrintWriter out) {
        return llavaWriterFactory.newLlavaWriter(out);
    }

    // LlavaVersion
    private static LlavaVersion llavaVersionFactory = new LlavaVersionImpl();

    public static LlavaVersion newLlavaVersion() {
        return llavaVersionFactory.newLlavaVersion();
    }

    // Pair
    private static Pair pairFactory = new PairImpl();

    public static Pair newPair(Object car, Object cdr) {
	// REVISIT - somehow, pairFactory is null when this is
	// called from 	org.llava.impl.io.LlavaReaderImpl.<init>
	// during startup to create the STRING field.
        // return pairFactory.newPair(car, cdr);
	return new PairImpl(car, cdr);
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

    public static Repl newRepl(LlavaReader reader, LlavaWriter out, LlavaWriter err, LlavaRuntime runtime, Compiler compiler) {
        return replFactory.newRepl(reader, out, err, runtime, compiler);
    }

    // Symbol
    private static Symbol symbolFactory = new SymbolImpl();

    public static Symbol newSymbol(String name) {
	// REVISIT - somehow, symbolFactory is null when this is
	// called from org.llava.impl.io.LlavaWriterImpl.<init>
	// during startup to create the SYMBOL fields.
	if (symbolFactory == null) {
	    symbolFactory = new SymbolImpl();
	}
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

    ////////////////////////////////////////////////////////////////////////
    //
    // Compiler objects.
    //
    ////////////////////////////////////////////////////////////////////////

    // Compiler

    private static Compiler compilerFactory =
	new CompilerImpl();
    
    public static Compiler newCompiler ()
    {
	return compilerFactory.newCompiler();
    }

    // EnvironmentLexical

    private static EnvironmentLexical environmentLexicalFactory =
	new EnvironmentLexicalImpl();
    
    public static EnvironmentLexical newEnvironmentLexical (Pair names)
    {
	return environmentLexicalFactory.newEnvironmentLexical(names);
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // Runtime objects.
    //
    ////////////////////////////////////////////////////////////////////////

    // ActivationFrame

    private static ActivationFrame activationFrameFactory =
	new ActivationFrameImpl();
    
    public static ActivationFrame newActivationFrame (EnvironmentTopLevel top)
    {
	return activationFrameFactory.newActivationFrame(top);
    }

    public static ActivationFrame newActivationFrame (int size)
    {
	return activationFrameFactory.newActivationFrame(size);
    }

    // BacktraceHandler

    private static BacktraceHandler backtraceHandlerFactory =
	new BacktraceHandlerImpl();

    public static BacktraceHandler newBacktraceHandler ()
    {
	return backtraceHandlerFactory.newBacktraceHandler();
    }

    // EnvTopLevelInit

    private static EnvTopLevelInit initializerFactory =
	new EnvTopLevelInitImpl();
    
    public static EnvTopLevelInit newEnvTopLevelInit (Repl repl)
    {
	return initializerFactory.newEnvTopLevelInit(repl);
    }

    // EnvironmentTopLevel

    private static EnvironmentTopLevel environmentTopLevelFactory =
	null; //new EnvironmentTopLevelImpl();
    
    public static EnvironmentTopLevel newEnvironmentTopLevel ()
    {
	if (environmentTopLevelFactory == null) {
	    environmentTopLevelFactory =
		(EnvironmentTopLevel)
		F.createFromPropertiesOrUseDefault(
	            "llava.runtime.EnvironmentTopLevelClassName",
		    //"org.llava.impl.runtime.EnvTopLevelInitImpl"
		    "org.llava.impl.runtime.NamespaceImpl"
		                                  );
	}
	return environmentTopLevelFactory.newEnvironmentTopLevel();
    }

    // Evaluator

    private static Evaluator evaluatorFactory =
	new EngineImpl();
        //new EngineStepImpl(); // REVISIT

    public static Evaluator newEvaluator ()
    {
	return evaluatorFactory.newEvaluator();
    }

    // GenericProcedure

    private static GenericProcedure genericProcedureFactory =
	new GenericProcedureImpl();
    
    public static GenericProcedure newGenericProcedure ()
    {
	return genericProcedureFactory.newGenericProcedure();
    }

    public static GenericProcedure newGenericProcedure (String name)
    {
	return genericProcedureFactory.newGenericProcedure(name);
    }

    public static GenericProcedure newGenericProcedure (Lambda lambda)
    {
	return genericProcedureFactory.newGenericProcedure(lambda);
    }

    // WrapJavaPrimitive

    private static WrapJavaPrimitive wrapJavaPrimitiveFactory =
	new WrapJavaPrimitiveImpl();
    
    public static WrapJavaPrimitive newWrapJavaPrimitive ()
    {
	return wrapJavaPrimitiveFactory.newWrapJavaPrimitive();
    }

    // Lambda

    private static Lambda lambdaFactory =
	new LambdaImpl();
    
    public static Lambda newLambda (int numRequired,
				    boolean isDotted,
				    Code code, 
				    ActivationFrame frame)
    {
	return lambdaFactory.newLambda(numRequired, isDotted, code, frame);
    }

    // LlavaRuntime

    private static LlavaRuntime runtimeFactory =
	new LlavaRuntimeImpl();

    public static LlavaRuntime newLlavaRuntime (EnvironmentTopLevel environmentTopLevel,
					      Evaluator           evaluator)
    {
	return runtimeFactory.newLlavaRuntime(environmentTopLevel, evaluator);
    }

    // UndefinedIdException

    public static UndefinedIdException newUndefinedIdException (String name)
    {
	return new UndefinedIdException(name);
    }

    public static UndefinedIdException newUndefinedIdException (Symbol sym)
    {
	return new UndefinedIdException(sym);
    }

    // UndefinedIdHandler

    private static UndefinedIdHandler undefinedIdHandlerFactory =
	new UndefinedIdHandlerImpl();
    
    public static UndefinedIdHandler newUndefinedIdHandler ()
    {
	return undefinedIdHandlerFactory.newUndefinedIdHandler();
    }

    // WrongNumberOfArgumentsException

    public static WrongNumberOfArgumentsException newWrongNumberOfArgumentsException (String name)
    {
	return new WrongNumberOfArgumentsException(name);
    }

    public static WrongNumberOfArgumentsException newWrongNumberOfArgumentsException (Symbol sym)
    {
	return new WrongNumberOfArgumentsException(sym);
    }

    //---------------------------------------------------------------
    //
    // Java Primitives
    //
    //---------------------------------------------------------------

    // PrimEqP

    private static PrimEqP primEqPFactory =
	new PrimEqP();
    
    public static PrimEqP newPrimEqP ()
    {
	return primEqPFactory.newPrimEqP();
    }

    // PrimEqualP

    private static PrimEqualP primEqualPFactory =
	new PrimEqualP();
    
    public static PrimEqualP newPrimEqualP ()
    {
	return primEqualPFactory.newPrimEqualP();
    }

    // PrimImport

    private static PrimImport primImportFactory =
	new PrimImport();
    
    public static PrimImport newPrimImport (Namespace namespace)
    {
	return primImportFactory.newPrimImport(namespace);
    }

    // PrimNew

    private static PrimNew primNewFactory =
	new PrimNew();
    
    public static PrimNew newPrimNew (Namespace namespace, 
				      PrimNewPrim primNewPrim)
    {
	return primNewFactory.newPrimNew(namespace, primNewPrim);
    }

    // PrimNewPrim

    private static PrimNewPrim primNewPrimFactory =
	new PrimNewPrim();
    
    public static PrimNewPrim newPrimNewPrim ()
    {
	return primNewPrimFactory.newPrimNewPrim();
    }

    // PrimPackage

    private static PrimPackage primPackageFactory =
	new PrimPackage();
    
    public static PrimPackage newPrimPackage (Namespace namespace)
    {
	return primPackageFactory.newPrimPackage(namespace);
    }

    // PrimSynchronized

    private static PrimSynchronized primSynchronizedFactory =
	new PrimSynchronized();
    
    public static PrimSynchronized newPrimSynchronized ()
    {
	return primSynchronizedFactory.newPrimSynchronized();
    }

    // PrimThrow

    private static PrimThrow primThrowFactory =
	new PrimThrow();
    
    public static PrimThrow newPrimThrow ()
    {
	return primThrowFactory.newPrimThrow();
    }

    // PrimTryCatchFinally

    private static PrimTryCatchFinally primTryCatchFinallyFactory =
	new PrimTryCatchFinally();
    
    public static PrimTryCatchFinally newPrimTryCatchFinally ()
    {
	return primTryCatchFinallyFactory.newPrimTryCatchFinally();
    }


    // Prim_BitAnd

    private static Prim_BitAnd prim_BitAndFactory =
	new Prim_BitAnd();
    
    public static Prim_BitAnd newPrim_BitAnd ()
    {
	return prim_BitAndFactory.newPrim_BitAnd();
    }

    // Prim_BitOr

    private static Prim_BitOr prim_BitOrFactory =
	new Prim_BitOr();
    
    public static Prim_BitOr newPrim_BitOr ()
    {
	return prim_BitOrFactory.newPrim_BitOr();
    }

    // Prim_Divide

    private static Prim_Divide prim_DivideFactory =
	new Prim_Divide();
    
    public static Prim_Divide newPrim_Divide ()
    {
	return prim_DivideFactory.newPrim_Divide();
    }

    // Prim_EQ

    private static Prim_EQ prim_EQFactory =
	new Prim_EQ();
    
    public static Prim_EQ newPrim_EQ ()
    {
	return prim_EQFactory.newPrim_EQ();
    }

    // Prim_GE

    private static Prim_GE prim_GEFactory =
	new Prim_GE();
    
    public static Prim_GE newPrim_GE ()
    {
	return prim_GEFactory.newPrim_GE();
    }

    // Prim_GT

    private static Prim_GT prim_GTFactory =
	new Prim_GT();
    
    public static Prim_GT newPrim_GT ()
    {
	return prim_GTFactory.newPrim_GT();
    }

    // Prim_LT

    private static Prim_LT prim_LTFactory =
	new Prim_LT();
    
    public static Prim_LT newPrim_LT ()
    {
	return prim_LTFactory.newPrim_LT();
    }


    // Prim_LE

    private static Prim_LE prim_LEFactory =
	new Prim_LE();
    
    public static Prim_LE newPrim_LE ()
    {
	return prim_LEFactory.newPrim_LE();
    }

    // Prim_Minus

    private static Prim_Minus prim_MinusFactory =
	new Prim_Minus();
    
    public static Prim_Minus newPrim_Minus ()
    {
	return prim_MinusFactory.newPrim_Minus();
    }

    // Prim_Modulo

    private static Prim_Modulo prim_ModuloFactory =
	new Prim_Modulo();
    
    public static Prim_Modulo newPrim_Modulo ()
    {
	return prim_ModuloFactory.newPrim_Modulo();
    }

    // Prim_Plus

    private static Prim_Plus prim_PlusFactory =
	new Prim_Plus();
    
    public static Prim_Plus newPrim_Plus ()
    {
	return prim_PlusFactory.newPrim_Plus();
    }

    // Prim_Times

    private static Prim_Times prim_TimesFactory =
	new Prim_Times();
    
    public static Prim_Times newPrim_Times ()
    {
	return prim_TimesFactory.newPrim_Times();
    }

    // java opt

    // PrimCurrentTimeMillis

    private static PrimCurrentTimeMillis primCurrentTimeMillisFactory =
	new PrimCurrentTimeMillis();
    
    public static PrimCurrentTimeMillis newPrimCurrentTimeMillis ()
    {
	return primCurrentTimeMillisFactory.newPrimCurrentTimeMillis();
    }

    // PrimInstanceof

    private static PrimInstanceof primInstanceofFactory =
	new PrimInstanceof();
    
    public static PrimInstanceof newPrimInstanceof (Namespace namespace)
    {
	return primInstanceofFactory.newPrimInstanceof(namespace);
    }

    // PrimNot

    private static PrimNot primNotFactory =
	new PrimNot();
    
    public static PrimNot newPrimNot ()
    {
	return primNotFactory.newPrimNot();
    }

    //---------------------------------------------------------------
    //
    // Llava Primitives
    //
    //---------------------------------------------------------------

    // PrimAppend

    private static PrimAppend primAppendFactory =
	new PrimAppend();
    
    public static PrimAppend newPrimAppend ()
    {
	return primAppendFactory.newPrimAppend();
    }

    // PrimApply

    private static PrimApply primApplyFactory =
	new PrimApply();
    
    public static PrimApply newPrimApply ()
    {
	return primApplyFactory.newPrimApply();
    }

    // PrimCallCC

    private static PrimCallCC primCallCCFactory =
	new PrimCallCC();
    
    public static PrimCallCC newPrimCallCC ()
    {
	return primCallCCFactory.newPrimCallCC();
    }

    // PrimCons

    private static PrimCons primConsFactory =
	new PrimCons();
    
    public static PrimCons newPrimCons ()
    {
	return primConsFactory.newPrimCons();
    }

    // PrimDefGenInternal

    private static PrimDefGenInternal primDefGenInternalFactory =
	new PrimDefGenInternal();
    
    public static PrimDefGenInternal newPrimDefGenInternal ()
    {
	return primDefGenInternalFactory.newPrimDefGenInternal();
    }


    // PrimEval

    private static PrimEval primEvalFactory =
	new PrimEval();
    
    public static PrimEval newPrimEval (EnvironmentTopLevel environmentTopLevel,
					Evaluator           evaluator,
					Compiler            compiler)
    {
	return primEvalFactory.newPrimEval(environmentTopLevel, evaluator, compiler);
    }

    // PrimField

    private static PrimField primFieldFactory =
	new PrimField();
    
    public static PrimField newPrimField ()
    {
	return primFieldFactory.newPrimField();
    }

    // PrimInvoke

    private static PrimInvoke primInvokeFactory =
	new PrimInvoke();
    
    public static PrimInvoke newPrimInvoke ()
    {
	return primInvokeFactory.newPrimInvoke();
    }

    // PrimLength

    private static PrimLength primLengthFactory =
	new PrimLength();
    
    public static PrimLength newPrimLength ()
    {
	return primLengthFactory.newPrimLength();
    }

    // PrimList

    private static PrimList primListFactory =
	new PrimList();
    
    public static PrimList newPrimList ()
    {
	return primListFactory.newPrimList();
    }

    // PrimList_

    private static PrimList_ primList_Factory =
	new PrimList_();
    
    public static PrimList_ newPrimList_ ()
    {
	return primList_Factory.newPrimList_();
    }

    // PrimLoad

    private static PrimLoad primLoadFactory =
	new PrimLoad();
    
    public static PrimLoad newPrimLoad (Repl repl)
    {
	return primLoadFactory.newPrimLoad(repl);
    }

    // PrimNewThread

    private static PrimNewThread primNewThreadFactory =
	new PrimNewThread();
    
    public static PrimNewThread newPrimNewThread ()
    {
	return primNewThreadFactory.newPrimNewThread();
    }

    // PrimStaticField

    private static PrimStaticField primStaticFieldFactory =
	new PrimStaticField();
    
    public static PrimStaticField newPrimStaticField ()
    {
	return primStaticFieldFactory.newPrimStaticField();
    }

    // PrimStaticInvoke

    private static PrimStaticInvoke primStaticInvokeFactory =
	new PrimStaticInvoke();
    
    public static PrimStaticInvoke newPrimStaticInvoke ()
    {
	return primStaticInvokeFactory.newPrimStaticInvoke();
    }

    // PrimStringAppend

    private static PrimStringAppend primStringAppendFactory =
	new PrimStringAppend();
    
    public static PrimStringAppend newPrimStringAppend ()
    {
	return primStringAppendFactory.newPrimStringAppend();
    }

    // llava opt

    // PrimForEach

    private static PrimForEach primForEachFactory =
	new PrimForEach();
    
    public static PrimForEach newPrimForEach ()
    {
	return primForEachFactory.newPrimForEach();
    }

    // PrimMap

    private static PrimMap primMapFactory =
	new PrimMap();
    
    public static PrimMap newPrimMap ()
    {
	return primMapFactory.newPrimMap();
    }

    // PrimNullP

    private static PrimNullP primNullPFactory =
	new PrimNullP();
    
    public static PrimNullP newPrimNullP ()
    {
	return primNullPFactory.newPrimNullP();
    }

    // PrimPairP

    private static PrimPairP primPairPFactory =
	new PrimPairP();
    
    public static PrimPairP newPrimPairP ()
    {
	return primPairPFactory.newPrimPairP();
    }

    // PrimString2Symbol

    private static PrimString2Symbol primString2SymbolFactory =
	new PrimString2Symbol();
    
    public static PrimString2Symbol newPrimString2Symbol ()
    {
	return primString2SymbolFactory.newPrimString2Symbol();
    }

    // PrimSymbolP

    private static PrimSymbolP primSymbolPFactory =
	new PrimSymbolP();
    
    public static PrimSymbolP newPrimSymbolP ()
    {
	return primSymbolPFactory.newPrimSymbolP();
    }

    //---------------------------------------------------------------
    //
    // Syntax
    //
    //---------------------------------------------------------------

    private static SyntaxDefineSyntax syntaxDefineSyntaxFactory =
	new SyntaxDefineSyntax();

    public static SyntaxDefineSyntax newSyntaxDefineSyntax ()
    {
	return syntaxDefineSyntaxFactory.newSyntaxDefineSyntax();
    }

    //---------------------------------------------------------------
    //
    // Code
    //
    //---------------------------------------------------------------

    // CodeApplication

    private static CodeApplication codeApplicationFactory =
	new CodeApplication();
    
    public static CodeApplication newCodeApplication (Object source, Code procCode, Code argCode)
    {
	return codeApplicationFactory.newCodeApplication(source, procCode, argCode);
    }

    // CodeApplicationArgs

    private static CodeApplicationArgs codeApplicationArgsFactory =
	new CodeApplicationArgs();
    
    public static CodeApplicationArgs newCodeApplicationArgs (Object source, Code codeFirst, Code codeRest)
    {
	return codeApplicationArgsFactory.newCodeApplicationArgs(source, codeFirst, codeRest);
    }

    // CodeAssignment

    private static CodeAssignment codeAssignmentFactory =
	new CodeAssignment();
    
    public static CodeAssignment newCodeAssignment (Object source, int slot, Code codeValue)
    {
	return codeAssignmentFactory.newCodeAssignment(source, slot, codeValue);
    }

    // CodeAssignmentDeep

    private static CodeAssignmentDeep codeAssignmentDeepFactory =
	new CodeAssignmentDeep();
    
    public static CodeAssignmentDeep newCodeAssignmentDeep (Object source, int level, int slot, Code codeValue)
    {
	return codeAssignmentDeepFactory.newCodeAssignmentDeep(source, level, slot, codeValue);
    }

    // CodeAssignmentTopLevel

    private static CodeAssignmentTopLevel codeAssignmentTopLevelFactory =
	new CodeAssignmentTopLevel();
    
    public static CodeAssignmentTopLevel newCodeAssignmentTopLevel (Object source, Symbol identifier, Code codeValue)
    {
	return codeAssignmentTopLevelFactory.newCodeAssignmentTopLevel(source, identifier, codeValue);
    }

    // CodeIf

    private static CodeIf codeIfFactory =
	new CodeIf();
    
    public static CodeIf newCodeIf (Object source, Code testCode, Code thenCode, Code elseCode)
    {
	return codeIfFactory.newCodeIf(source, testCode, thenCode, elseCode);
    }

    // CodeLambda

    private static CodeLambda codeLambdaFactory =
	new CodeLambda();
    
    public static CodeLambda newCodeLambda (Object source, int numRequired, boolean isDotted, Code codeSequence)
    {
	return codeLambdaFactory.newCodeLambda(source, numRequired, isDotted, codeSequence);
    }

    // CodeLiteral

    private static CodeLiteral codeLiteralFactory =
	new CodeLiteral();
    
    public static CodeLiteral newCodeLiteral (Object source, Object literal)
    {
	return codeLiteralFactory.newCodeLiteral(source, literal);
    }

    // CodeReference

    private static CodeReference codeReferenceFactory =
	new CodeReference();
    
    public static CodeReference newCodeReference (Object source, int slot)
    {
	return codeReferenceFactory.newCodeReference(source, slot);
    }

    // CodeReferenceDeep

    private static CodeReferenceDeep codeReferenceDeepFactory =
	new CodeReferenceDeep();
    
    public static CodeReferenceDeep newCodeReferenceDeep (Object source, int level, int slot)
    {
	return codeReferenceDeepFactory.newCodeReferenceDeep(source, level, slot);
    }

    // CodeReferenceTopLevel

    private static CodeReferenceTopLevel codeReferenceTopLevelFactory =
	new CodeReferenceTopLevel();
    
    public static CodeReferenceTopLevel newCodeReferenceTopLevel (Object source)
    {
	return codeReferenceTopLevelFactory.newCodeReferenceTopLevel(source);
    }

    // CodeSchemeIf

    private static CodeSchemeIf codeSchemeIfFactory =
	new CodeSchemeIf();
    
    public static CodeSchemeIf newCodeSchemeIf (Object source, Code testCode, Code thenCode, Code elseCode)
    {
	return codeSchemeIfFactory.newCodeSchemeIf(source, testCode, thenCode, elseCode);
    }

    // CodeSequence

    private static CodeSequence codeSequenceFactory =
	new CodeSequence();
    
    public static CodeSequence newCodeSequence (Object source, Code codeFirst, Code codeRest)
    {
	return codeSequenceFactory.newCodeSequence(source, codeFirst, codeRest);
    }

    //---------------------------------------------------------------
    //
    // Derived
    //
    //---------------------------------------------------------------

    private static final String _F = "org.llava.F";



    // DerivedAll

    private static String derivedAllLoadClassname =
	_F;

    private static String derivedAllFilename = 
	"derived/DerivedAll.lva";

    public static Pair newDerivedAll ()
    {
	return F.cons(derivedAllLoadClassname,
		      derivedAllFilename);
    }


    // DerivedJavaFirst

    private static String derivedJavaFirstLoadClassname =
	_F;

    private static String derivedJavaFirstFilename = 
	"derived/DerivedJavaFirst.lva";

    public static Pair newDerivedJavaFirst ()
    {
	return F.cons(derivedJavaFirstLoadClassname,
		      derivedJavaFirstFilename);
    }


    // DerivedJavaSecond

    private static String derivedJavaSecondLoadClassname =
	_F;

    private static String derivedJavaSecondFilename = 
	"derived/DerivedJavaSecond.lva";

    public static Pair newDerivedJavaSecond ()
    {
	return F.cons(derivedJavaSecondLoadClassname,
		      derivedJavaSecondFilename);
    }


    // DerivedJavaTry

    private static String derivedJavaTryLoadClassname =
	_F;

    private static String derivedJavaTryFilename = 
	"derived/DerivedJavaTry.lva";

    public static Pair newDerivedJavaTry ()
    {
	return F.cons(derivedJavaTryLoadClassname,
		      derivedJavaTryFilename);
    }


    // DerivedLlavaBinding

    private static String derivedLlavaBindingLoadClassname =
	_F;

    private static String derivedLlavaBindingFilename = 
	"derived/DerivedLlavaBinding.lva";

    public static Pair newDerivedLlavaBinding ()
    {
	return F.cons(derivedLlavaBindingLoadClassname,
		      derivedLlavaBindingFilename);
    }


    // DerivedLlavaCase

    private static String derivedLlavaCaseLoadClassname =
	_F;

    private static String derivedLlavaCaseFilename = 
	"derived/DerivedLlavaCase.lva";

    public static Pair newDerivedLlavaCase ()
    {
	return F.cons(derivedLlavaCaseLoadClassname,
		      derivedLlavaCaseFilename);
    }


    // DerivedLlavaConditional

    private static String derivedLlavaConditionalLoadClassname =
	_F;

    private static String derivedLlavaConditionalFilename = 
	"derived/DerivedLlavaConditional.lva";

    public static Pair newDerivedLlavaConditional ()
    {
	return F.cons(derivedLlavaConditionalLoadClassname,
		      derivedLlavaConditionalFilename);
    }


    // DerivedLlavaControl

    private static String derivedLlavaControlLoadClassname =
	_F;

    private static String derivedLlavaControlFilename = 
	"derived/DerivedLlavaControl.lva";

    public static Pair newDerivedLlavaControl ()
    {
	return F.cons(derivedLlavaControlLoadClassname,
		      derivedLlavaControlFilename);
    }


    // DerivedLlavaDefineD

    private static String derivedLlavaDefineDLoadClassname =
	_F;

    private static String derivedLlavaDefineDFilename = 
	"derived/DerivedLlavaDefineD.lva";

    public static Pair newDerivedLlavaDefineD ()
    {
	return F.cons(derivedLlavaDefineDLoadClassname,
		      derivedLlavaDefineDFilename);
    }


    // DerivedLlavaIteration

    private static String derivedLlavaIterationLoadClassname =
	_F;

    private static String derivedLlavaIterationFilename = 
	"derived/DerivedLlavaIteration.lva";

    public static Pair newDerivedLlavaIteration ()
    {
	return F.cons(derivedLlavaIterationLoadClassname,
		      derivedLlavaIterationFilename);
    }


    // DerivedLlavaMember

    private static String derivedLlavaMemberLoadClassname =
	_F;

    private static String derivedLlavaMemberFilename = 
	"derived/DerivedLlavaMember.lva";

    public static Pair newDerivedLlavaMember ()
    {
	return F.cons(derivedLlavaMemberLoadClassname,
		      derivedLlavaMemberFilename);
    }


    // DerivedLlavaQuasiquote

    private static String derivedLlavaQuasiquoteLoadClassname =
	_F;

    private static String derivedLlavaQuasiquoteFilename = 
	"derived/DerivedLlavaQuasiquote.lva";

    public static Pair newDerivedLlavaQuasiquote ()
    {
	return F.cons(derivedLlavaQuasiquoteLoadClassname,
		      derivedLlavaQuasiquoteFilename);
    }


    // DerivedScmTypeProcs

    private static String derivedScmTypeProcsLoadClassname =
	_F;

    private static String derivedScmTypeProcsFilename = 
	"derived/DerivedScmTypeProcs.lva";

    public static Pair newDerivedScmTypeProcs ()
    {
	return F.cons(derivedScmTypeProcsLoadClassname,
		      derivedScmTypeProcsFilename);
    }

    // Import

    private static String importLoadClassname =
	_F;

    private static String importFilename = 
	"derived/import.lva";

    public static Pair newImport ()
    {
	return F.cons(importLoadClassname,
		      importFilename);
    }


    // RequireProvide

    private static String requireProvideLoadClassname =
	_F;

    private static String requireProvideFilename = 
	"derived/RequireProvide.lva";

    public static Pair newRequireProvide ()
    {
	return F.cons(requireProvideLoadClassname,
		      requireProvideFilename);
    }


    // DerivedTest

    private static String derivedTestLoadClassname =
	_F;

    private static String derivedTestFilename = 
	"derived/DerivedTest.lva";

    public static Pair newDerivedTest ()
    {
	return F.cons(derivedTestLoadClassname,
		      derivedTestFilename);
    }
}

// End of file.

