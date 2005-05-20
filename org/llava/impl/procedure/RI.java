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
 * Created       : 2000 Jan 26 (Wed) 17:08:19 by Harold Carr.
 * Last Modified : 2005 May 20 (Fri) 13:49:48 by Harold Carr.
 */

package org.llava.impl.procedure;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RI
{
    //
    // Instance creation.
    //

    public static Object newInstance (String targetClassName, Object[] args)
	throws
	    ClassNotFoundException,
	    Throwable
    {
	return newInstance(Class.forName(targetClassName), args);
    }
				  
    public static Object newInstance (Class targetClass, Object[] args) 
	throws 
	    Throwable 
    {
	return newInstanceInternal(targetClass, args);
    }

    //
    // Method invocation.
    //

    public static Object invoke (String methodName, 
				 Object targetObject,
				 Object[] args) 
	throws 
	    NoSuchMethodException, 
	    Throwable 
    {
	return invokeInternal(methodName,
			      targetObject.getClass(),
			      targetObject, 
			      args); 
    }

    public static Object invokeStatic (String methodName, 
				       String targetClassName,
				       Object[] args) 
	throws 
	    ClassNotFoundException,
	    NoSuchMethodException, 
	    Throwable 
    {
	return invokeStatic(methodName, Class.forName(targetClassName), args);
    }

    public static Object invokeStatic (String methodName, 
				       Class targetClass,
				       Object[] args) 
	throws 
	    NoSuchMethodException, 
	    Throwable 
    {
	return invokeInternal(methodName, targetClass, null, args); 
    }

    //
    // Field access.
    //

    public static Object fieldRef (String fieldName, Object targetObject)
	throws 
	    IllegalAccessException,
	    NoSuchFieldException 
    {
	return internalFieldRef(fieldName,
				targetObject.getClass(),
				targetObject);
    }

    public static Object staticFieldRef (String fieldName, String className)
	throws 
	    ClassNotFoundException,
	    IllegalAccessException,
	    NoSuchFieldException 
    {
	return staticFieldRef(fieldName, Class.forName(className));
    }


    public static Object staticFieldRef (String fieldName, Class targetClass)
	throws 
	    IllegalAccessException,
	    NoSuchFieldException 
    {
	return internalFieldRef(fieldName, targetClass, null);
    }

    public static Object fieldSet (String fieldName,
				   Object targetObject,
				   Object value)
	throws 
	    IllegalAccessException, 
	    NoSuchFieldException 
    {
	return internalFieldSet(fieldName, 
				targetObject.getClass(),
				targetObject,
				value); 
    }

    public static Object staticFieldSet (String fieldName,
					 String className,
					 Object value)
	throws 
	    ClassNotFoundException,
	    IllegalAccessException, 
	    NoSuchFieldException 
    {
	return staticFieldSet(fieldName,
			      Class.forName(className),
			      value);
    }

    public static Object staticFieldSet (String fieldName,
					 Class targetClass,
					 Object value)
	throws 
	    IllegalAccessException, 
	    NoSuchFieldException 
    {
	return internalFieldSet(fieldName, targetClass, null, value);
    }

    //
    // Implementation.
    //

    //
    // Instance creation and method invocation.
    //

    private static Object newInstanceInternal (Class targetClass,
					       Object[] args) 
	throws 
	    NoSuchMethodException,
	    Throwable 
    {
	Constructor constructor =findConstructor(targetClass,getClasses(args));

	if (constructor == null) {
	    throw new NoSuchMethodException(targetClass.getName());
	}

	try {
	    return constructor.newInstance(args);
	} catch (InvocationTargetException e) {
	    throw e.getTargetException(); 
	}
    }

    private static Object invokeInternal (String methodName,
					  Class targetClass,
					  Object targetObject,
					  Object[] args) 
	throws 
	    NoSuchMethodException,
	    Throwable 
    {
	Method method = findMethod(methodName, targetClass, getClasses(args));

	if (method == null) {
	    throw new NoSuchMethodException(methodName);
	}

	try {
	    return method.invoke(targetObject, args); 
	} catch (InvocationTargetException e) {
	    throw e.getTargetException(); 
	}
    }

    private static synchronized Method findMethod (String methodName, 
						   Class targetClass, 
						   Class[] argTypes) 
	throws
	    NoSuchMethodException
    {
	Key key = Key.initReusableKey(methodName, targetClass, argTypes);
	Method method = (Method) cachedMethods.get(key);
	if (method != null) {
	    return method;
	}

	method = findMethodFromScratch(methodName, targetClass, argTypes);
	if (method == null) {
	    return null;
	}

	key.remember();
	cachedMethods.put(key, method);
	return method; 
    }

    private static Method findMethodFromScratch (String methodName,
						 Class targetClass,
						 Class[] argTypes) 
	throws 
	    NoSuchMethodException 
    {
	try {
	    Method m = targetClass.getMethod(methodName, argTypes); 
	    m.setAccessible(true);
	    return m;
	} catch (NoSuchMethodException e) {	
	    ;
	}
	return findMethodInSupers(methodName, targetClass, argTypes);
    }

    private static Method findMethodInSupers (String methodName,
					      Class targetClass,
					      Class[] argTypes) 
	throws 
	    NoSuchMethodException 
    {
	List candidates = collectCandidateMethodsFromSupers(methodName,
							    targetClass,
							    argTypes);
	
	Method method;
	switch (candidates.size()) {
	case 0:
	    return null;
	case 1:
	    method = (Method)candidates.get(0);
	    break;
	default:
	    method = mostSpecificMethod(candidates);
	    break;
	}
	method.setAccessible(true);
	return method;
    }

    private static Constructor findConstructor (Class targetClass,
						Class[] argTypes)
	throws
	    NoSuchMethodException
    {
	List candidates = collectCandidateConstructors(targetClass,argTypes);

	Constructor constructor;
	switch (candidates.size()) {
	case 0:
	    return null;
	case 1:
	    constructor = (Constructor)candidates.get(0);
	    break;
	default:
	    constructor = mostSpecificConstructor(candidates);
	    break;
	}
	constructor.setAccessible(true);
	return constructor;
    }

    private static List collectCandidateConstructors(Class targetClass,
						     Class[] argTypes)
    {
	List candidates = new ArrayList();

	Constructor[] constructors = targetClass.getDeclaredConstructors();
	for (int i = 0; i < constructors.length; i++) {
	    if (equalTypes(constructors[i].getParameterTypes(), argTypes)) {
		candidates.add(constructors[i]);
	    }
	}
	return candidates;
    }

    private static List collectCandidateMethodsFromSupers(String methodName,
							  Class targetClass,
							  Class[] argTypes)
	throws
	    NoSuchMethodException
    {
	List candidates = new ArrayList();

	for (; targetClass != null; targetClass = targetClass.getSuperclass()){
	    Method[] methods = targetClass.getDeclaredMethods();
	    for (int i = 0; i < methods.length; i++) {
		Class[] parmTypes = methods[i].getParameterTypes();
		if (methodName.equals(methods[i].getName()) &&
		    equalTypes(parmTypes, argTypes))
		{
		    candidates.add(methods[i]);
		}
	    }
	}
	return candidates;
    }

    //
    // Picking most specific constructors/methods from similar candidates.
    //

    private static Constructor mostSpecificConstructor (List constructors) 
	throws
	    NoSuchMethodException
    {
	for (int i = 0; i < constructors.size(); i++) {
	    for (int j = 0; j < constructors.size(); j++) {
		if ((i != j) &&
		    (isMoreSpecific((Constructor)constructors.get(i), 
				    (Constructor)constructors.get(j)))) 
                {
		    constructors.remove(j);
		    if (i > j) {
			i--;
		    }
		    j--;
		}
	    }
	}
	if (constructors.size() == 1) {
	    return (Constructor)constructors.get(0);
	}
	throw new NoSuchMethodException("more than one most specific constructor");
    }

    private static Method mostSpecificMethod (List methods) 
	throws
	    NoSuchMethodException 
    {
	for (int i = 0; i < methods.size(); i++) {
	    for (int j = 0; j < methods.size(); j++) {
		if ((i != j) &&
		    (isMoreSpecific((Method)methods.get(i),
				    (Method)methods.get(j)))) 
                {
		    methods.remove(j);
		    if (i > j) {
			i--;
		    }
		    j--;
		}
	    }
	}
	if (methods.size() == 1) {
	    return (Method)methods.get(0);
	}
	throw new NoSuchMethodException("more than one most specific method");
    }

    private static boolean isMoreSpecific (Constructor c1, Constructor c2) 
    {
	// True IFF c1 is more specific than c2.
	Class[] parmTypes1 = c1.getParameterTypes();
	Class[] parmTypes2 = c2.getParameterTypes();
	int n = parmTypes1.length;
	for (int i = 0; i < n; i++) {
	    if (equalType(parmTypes1[i], parmTypes2[i])) {
		return false;
	    }
	}
	return true;
    }

    private static boolean isMoreSpecific (Method m1, Method m2) 
    {
	// REVISIT - m2/m1 order

	// True IFF m1 is more specific than m2
	if (! equalType(m2.getDeclaringClass(), m1.getDeclaringClass())) {
	    return false;
	}
	Class[] parmTypes1 = m1.getParameterTypes();
	Class[] parmTypes2 = m2.getParameterTypes();
	int n = parmTypes1.length;
	for (int i = 0; i < n; i++) {
	    if (! equalType(parmTypes2[i], parmTypes1[i])) {
		return false;
	    }
	}
	return true;
    }

    //
    // Field access.
    //

    private static Object internalFieldRef (String fieldName,
					    Class targetClass,
					    Object targetObject)
					    
	throws
	    IllegalAccessException,
	    NoSuchFieldException 
    {
	return findField(fieldName, targetClass).get(targetObject);
    }

    private static Object internalFieldSet (String fieldName,
					    Class targetClass,
					    Object targetObject,
					    Object value)
	throws
	    IllegalAccessException, 
	    NoSuchFieldException 
    {
	findField(fieldName, targetClass).set(targetObject, value); 
	return value;
    }

    private static Field findField (String fieldName, Class targetClass)
	throws
	    NoSuchFieldException 
    {
	Field field;
	try {
	    field = targetClass.getField(fieldName);	   
	} catch (NoSuchFieldException e) {
	    field = findFieldNonPublicAndSupers(fieldName, targetClass);
	}
	field.setAccessible(true);
	return field;
    }

    private static Field findFieldNonPublicAndSupers (String fieldName,
						      Class targetClass)
						      
	throws
	    NoSuchFieldException 
    {
	Field[] localFields = targetClass.getDeclaredFields();
	for (int i = 0; i < localFields.length; i++) {
	    if (fieldName.equals(localFields[i].getName())) {
		return localFields[i];
	    }
	}

	Class superClass = targetClass.getSuperclass();
	if (superClass == null) {
	    throw new NoSuchFieldException(fieldName);
	}
	return findField(fieldName, superClass);
    }

    //
    // Type compatibility.
    //

    private static boolean equalTypes (Class[] parmTypes, 
				       Class[] argTypes) 
    {
	if (parmTypes.length != argTypes.length) {
	    return false;
	}
	for (int i = 0; i < parmTypes.length; i++) {
	    if (!equalType(parmTypes[i], argTypes[i])) {
		return false; 
	    }
	}
	return true;
    }

    private static boolean equalType (Class parmType, Class argType) 
    {
	// Also used by isMoreSpecific so argType could be primitive.
	if (argType.isPrimitive()) {
	    argType = wrapPrim(argType);
	}

	if (parmType.isPrimitive()) {
	    return 
		wrapPrim(parmType) == argType ||
		isPrimAssignableFrom(parmType, argType);
	}

	// parmType is a reference type.
	return 
	    parmType == argType ||
	    parmType.isAssignableFrom(argType) || 
	    argType == NullClass;
    }

    // Refer to Java Language Specification 5.1.2.

    private static boolean isPrimAssignableFrom (Class parmType,
						 Class argType) 
    {
	if (argType == ByteClass) {
	    return
		parmType == shortClass   ||
		parmType == integerClass ||
		parmType == longClass    ||
		parmType == floatClass   ||
		parmType == doubleClass;
	}

	if (argType == IntegerClass || argType == ShortClass || 
	                               argType == CharacterClass) {
	    return
		parmType == integerClass ||
		parmType == longClass    ||
		parmType == floatClass   ||
		parmType == doubleClass;
	}

	if (argType == LongClass || argType == FloatClass) {
	    return
		parmType == floatClass ||
		parmType == doubleClass;
	}

	return false;
    }

    //
    // Utilities.
    //

    private static Class[] getClasses (Object[] args) 
    {
	Class[] classes = new Class[args.length];
	for (int i = 0; i < args.length; i = i + 1) {
	    classes[i] =
		args[i] == null ? NullClass : args[i].getClass();
	}
	return classes; 
    }

    private static Class wrapPrim (Class cl) 
    {
	if (! cl.isPrimitive()) {
	    return cl;
	}

	if (cl == booleanClass)   return BooleanClass;
	if (cl == integerClass)   return IntegerClass;
	if (cl == characterClass) return CharacterClass;
	if (cl == byteClass)      return ByteClass;
	if (cl == shortClass)     return ShortClass;
	if (cl == longClass)      return LongClass;
	if (cl == floatClass)     return FloatClass;
	if (cl == doubleClass)    return DoubleClass;

	throw new Error("DI.wrapPrim: unknown type" + cl);
    }

    //
    // Class variables and initialization.
    //
  
    // 89 - see _Java 2 Performance and Idiom Guide_ p 67.
    private static Map   cachedMethods = new HashMap(89);

    private static Class NullClass;
    private static Class BooleanClass;
    private static Class booleanClass;
    private static Class CharacterClass;
    private static Class characterClass;
    private static Class ByteClass;
    private static Class byteClass;
    private static Class ShortClass;
    private static Class shortClass;
    private static Class IntegerClass;
    private static Class integerClass;
    private static Class LongClass;
    private static Class longClass;
    private static Class FloatClass;
    private static Class floatClass;
    private static Class DoubleClass;
    private static Class doubleClass;

    static {
	try {
	    NullClass      = Class.forName("java.lang.Void");
	    booleanClass   = Boolean.TYPE;
	    BooleanClass   = Class.forName("java.lang.Boolean");
	    characterClass = Character.TYPE;
	    CharacterClass = Class.forName("java.lang.Character");
	    byteClass      = Byte.TYPE;
	    ByteClass      = Class.forName("java.lang.Byte");
	    shortClass     = Short.TYPE;
	    ShortClass     = Class.forName("java.lang.Short");
	    integerClass   = Integer.TYPE;
	    IntegerClass   = Class.forName("java.lang.Integer");
	    longClass      = Long.TYPE;
	    LongClass      = Class.forName("java.lang.Long");
	    floatClass     = Float.TYPE;
	    FloatClass     = Class.forName("java.lang.Float");
	    doubleClass    = Double.TYPE;
	    DoubleClass    = Class.forName("java.lang.Double");
	} catch (ClassNotFoundException e) {
	    System.err.println("DI (static initializer) - should not happen");
	}
    }
}

class Key 
{
    private String  methodName;
    private Class   targetClass;
    private Class[] argTypes;

    private static Key reusableKey = new Key();

    private Key ()
    {
    }

    static Key initReusableKey (String methodName, 
				Class targetClass, 
				Class[] argTypes) 
    {
	reusableKey.methodName  = methodName; 
	reusableKey.targetClass = targetClass; 
	reusableKey.argTypes    = argTypes;
	return reusableKey;
    }

    void remember() 
    {
	reusableKey = new Key();
    }

    public int hashCode () 
    {
	int v = targetClass.hashCode() ^ (37 * methodName.hashCode());
	for (int i = 0; i < argTypes.length; i++) {
	    v = (v * 43) ^ argTypes[i].hashCode();
	}
	return v;
    }

    public boolean equals (Object x) 
    {
	if (! (x instanceof Key)) {
	    return false;
	}
	Key m = (Key)x;
	return
	    targetClass == m.targetClass &&
	    methodName.equals(m.methodName) &&
	    eqTypes(argTypes, m.argTypes);
    }

    private static boolean eqTypes (Class[] types1, Class[] types2) 
    {
	if (types1.length != types2.length) {
	    return false;
	}
	for (int i = 0; i < types1.length; i++) {
	    if (types1[i] != types2[i])
		return false;
	}
	return true; 
    }
}

// End of file.

