/**
 * Created       : 2000 Jan 26 (Wed) 17:08:19 by Harold Carr.
 * Last Modified : 2000 Feb 16 (Wed) 19:45:34 by Harold Carr.
 */

package libLava.r1.procedure.generic;

import java.lang.reflect.*;
import java.util.Vector;
import java.util.Hashtable;

public class DI {

    //
    // New public API in progress.
    //

    public static Object invoke (String   method,
				 Object   targetObject, 
				 Object[] args)
	throws
	    NoSuchMethodException,
	    Throwable
    {
	return invoke(null, targetObject, method, args);
    }

    public static Object invokeStatic (String   method,
				       Class    targetClass,
				       Object[] args)
	throws
	    NoSuchMethodException,
	    Throwable
    {
	return invoke(targetClass, null, method, args);
    }

    public static Object getField (String field,
				   Object targetObject)
	throws
	    NoSuchFieldException,
	    IllegalAccessException
    {
	return getField(targetObject, field);
    }
				  
				      
    public static Object getStaticField (String field,
					 Class  targetClass)
	throws
	    NoSuchFieldException,
	    IllegalAccessException
    {
	return getField(targetClass, null, field);
    }
				  
				      
    public static Object setField (String field,
				   Object targetObject,
				   Object value)
	throws
	    NoSuchFieldException,
	    IllegalAccessException
    {
	setField(targetObject, field, value);
	return value;
    }
				  
				      
    public static Object setStaticField (String field,
					 Class  targetClass,
					 Object value)
	throws
	    NoSuchFieldException,
	    IllegalAccessException
    {
	setField(targetClass, null, field, value);
	return value;
    }

    //
    // Existing public API.
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
	Class[] argTypes = getClasses(args);    
	Constructor[] constructors = targetClass.getDeclaredConstructors();
	Vector candidates = new Vector();
	for (int i = 0; i < constructors.length; i++) {
	    if (equalTypes(constructors[i].getParameterTypes(), argTypes)) {
		candidates.addElement(constructors[i]);
	    }
	}
	Constructor c;
	switch (candidates.size()) {
	case 0:
	    throw new NoSuchMethodException("no applicable constructor");
	case 1:
	    c = (Constructor)candidates.firstElement();
	    break;
	default:
	    c = mostSpecificConstructor(candidates);
	    break;
	}
	c.setAccessible(true);
	try {
	    return c.newInstance(args);
	} catch (InvocationTargetException e) {
	    throw(e.getTargetException()); 
	}
    }

    public static Object invoke (Object targetObject,
				 String methodName, 
				 Object[] args) 
	throws 
	    NoSuchMethodException, 
	    Throwable 
    {
	return invoke(targetObject.getClass(),targetObject, methodName, args); 
    }

    public static Object invoke (Class targetClass,
				 Object targetObject,
				 String methodName,
				 Object[] args) 
	throws 
	    NoSuchMethodException,
	    Throwable 
    {
	Method method = findMethod(targetClass, methodName, getClasses(args));

	if (method == null) {
	    throw new NoSuchMethodException("DI.invoke: " + methodName);
	}

	try {
	    return method.invoke(targetObject, args); 
	} catch (InvocationTargetException e) {
	    throw(e.getTargetException()); 
	}
    }

    public static void setField (Object targetObject,
				 String fieldName,
				 Object value)
	throws 
	    IllegalAccessException, 
	    NoSuchFieldException 
    {
	setField(targetObject.getClass(), targetObject, fieldName, value); 
    }


    public static void setField (Class targetClass,
				 Object targetObject,
				 String fieldName,
				 Object value)
	throws
	    IllegalAccessException, 
	    NoSuchFieldException 
    {
	findField(targetClass, fieldName).set(targetObject, value); 
    }

    public static Object getField (Object targetObject, String fieldName) 
	throws 
	    IllegalAccessException,
	    NoSuchFieldException 
    {
	return getField(targetObject.getClass(), targetObject, fieldName);
    }

    public static Object getField (Class targetClass,
				   Object targetObject,
				   String fieldName) 
	throws
	    IllegalAccessException,
	    NoSuchFieldException 
    {
	return findField(targetClass, fieldName).get(targetObject);
    }

    //
    // Implementation.
    //

    //
    // Methods.
    //

    private static synchronized Method findMethod (Class target, 
						   String name, 
						   Class[] argTypes) 
	throws 
	    NoSuchMethodException  
    {
	// synchronized because the key is reused.
	MethodKey key = MethodKey.newMethodKey(target, name, argTypes);
	Method result = (Method) cachedMethods.get(key);
	if (result != null) {
	    return result;
	}

	result = findMethodFromScratch(target, name, argTypes);

	if (result == null) {
	    return null;
	}

	key.remember();
	cachedMethods.put(key, result);
	return result; 
    }

    private static Method findMethodFromScratch (Class target,
						 String name,
						 Class[] argTypes) 
	throws 
	    NoSuchMethodException 
    {
	try {
	    Method m = target.getMethod(name, argTypes); 
	    m.setAccessible(true);
	    return m;
	} catch (NoSuchMethodException e) {	
	    ;
	}
	return findMethodInSupers(target, name, argTypes);
    }

    private static Method findMethodInSupers (Class target,
					      String name,
					      Class[] argTypes) 
	throws 
	    NoSuchMethodException 
    {
	Vector goodMethods =
	    collectCandidatesFromSupers(target, name, argTypes);
	
	Method m;
	switch (goodMethods.size()) {
	case 0:
	    return null;
	case 1:
	    m = (Method)goodMethods.firstElement();
	    break;
	default:
	    m = mostSpecificMethod(goodMethods);
	    break;
	}
	m.setAccessible(true);
	return m;
    }

    private static Vector collectCandidatesFromSupers(Class target,
						      String name,
						      Class[] argTypes)
	throws
	    NoSuchMethodException
    {
	Vector candidates = new Vector();

	while (target != null) {
	    Method[] methods = target.getDeclaredMethods();
	    for (int i = 0; i < methods.length; i++) {
		Class[] parmTypes = methods[i].getParameterTypes();
		if (name.equals(methods[i].getName()) &&
		    equalTypes(parmTypes, argTypes))
		{
		    candidates.addElement(methods[i]);
		}
	    }
	    target = target.getSuperclass();
	}
	return candidates;
    }

    //
    // Fields.
    //

    static Field findField (Class targetClass, String fieldName) 
	throws
	    NoSuchFieldException 
    {
	Field field;
	try {
	    field = targetClass.getField(fieldName);	   
	} catch (NoSuchFieldException e) {
	    // Either it is non-public, in a super, or it does not exist.
	    field = findFieldNonPublicAndSupers(targetClass, fieldName);
	}
	field.setAccessible(true);
	return field;
    }

    static Field findFieldNonPublicAndSupers (Class targetClass,
					      String fieldName) 
	throws
	    NoSuchFieldException 
    {
	Field[] localFields = targetClass.getDeclaredFields();
	for (int i = 0; i < localFields.length; i++) {
	    if (fieldName.equals(localFields[i].getName())) {
		return localFields[i];
	    }
	}

	Class sup = targetClass.getSuperclass();
	if (sup == null) {
	    throw new NoSuchFieldException(fieldName);
	}
	return findField(sup, fieldName);
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
	// Also used by moreSpecific so argType could be primitive.
	if (argType.isPrimitive()) {
	    argType = wrappedClass(argType);
	}

	if (parmType.isPrimitive()) {
	    return 
		wrappedClass(parmType) == argType ||
		isPrimAssignableFrom(parmType, argType);
	}

	// parmType is a reference type (reduces to isAssignableFrom)
	return 
	    parmType == argType ||
	    parmType.isAssignableFrom(argType) || 
	    argType == NullClass;
    }


    // parmType is a primitive Class, argType is a wrapper Class
    // Assumes parmType and argType are not "identical" (modulo wrapping).
    // see Widening Primitve Conversions in JLS 5.1.2.

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
    // Picking most specific constructors/methods from similar candidates.
    //

    static Constructor mostSpecificConstructor (Vector constructors) 
	throws
	    Throwable 
    {
	for (int i = 0; i < constructors.size(); i++) {
	    for (int j = 0; j < constructors.size(); j++) {
		if ((i != j) &&
		    (moreSpecific((Constructor)constructors.elementAt(i), 
				  (Constructor)constructors.elementAt(j)))) 
                {
		    constructors.removeElementAt(j);
		    if (i > j) i--;
		    j--;
		}
	    }
	}
	if (constructors.size() == 1) {
	    return (Constructor)constructors.elementAt(0);
	}
	throw new NoSuchMethodException("more than one most specific constructor");
    }

    private static Method mostSpecificMethod (Vector methods) 
	throws
	    NoSuchMethodException 
    {
	for (int i = 0; i < methods.size(); i++) {
	    for (int j = 0; j < methods.size(); j++) {
		if ((i != j) &&
		    (moreSpecific((Method)methods.elementAt(i),
				  (Method)methods.elementAt(j)))) 
                {
		    methods.removeElementAt(j);
		    if (i > j) i--;
		    j--;
		}
	    }
	}
	if (methods.size() == 1) {
	    return (Method)methods.elementAt(0);
	}
	throw new NoSuchMethodException("more than one most specific method");
    }

    // True IFF c1 is more specific than c2

    private static boolean moreSpecific (Constructor c1, Constructor c2) 
    {
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

    // True IFF c1 is more specific than c2

    private static boolean moreSpecific (Method c1, Method c2) 
    {
	if (! equalType(c2.getDeclaringClass(), c1.getDeclaringClass())) {
	    return false;
	}
	Class[] parmTypes1 = c1.getParameterTypes();
	Class[] parmTypes2 = c2.getParameterTypes();
	int n = parmTypes1.length;
	for (int i = 0; i < n; i++) {
	    if (! equalType(parmTypes2[i], parmTypes1[i])) {
		return false;
	    }
	}
	return true;
    }

    //
    // Utilities.
    //

    private static Class[] getClasses (Object[] args) 
    {
	Class[] classes = new Class[args.length];
	for (int i = 0; i < args.length; i = i + 1) {
	    classes[i] = ((args[i] == null) ? NullClass : args[i].getClass());
	}
	return classes; 
    }

    private static Class wrappedClass (Class cl) 
    {
	if (cl.isPrimitive()) {
	    if (cl == booleanClass)   return BooleanClass;
	    if (cl == integerClass)   return IntegerClass;
	    if (cl == characterClass) return CharacterClass;
	    if (cl == byteClass)      return ByteClass;
	    if (cl == shortClass)     return ShortClass;
	    if (cl == longClass)      return LongClass;
	    if (cl == floatClass)     return FloatClass;
	    if (cl == doubleClass)    return DoubleClass;
	    throw new Error("DI.wrappedClass: unknown type" + cl);
	}
	return cl;
    }

    //
    // Class variables and initialization.
    //
  
    // REVISIT - use weak references
    static Hashtable cachedMethods = new Hashtable();

    static Class NullClass;
    static Class nullClass;
    static Class BooleanClass;
    static Class booleanClass;
    static Class CharacterClass;
    static Class characterClass;
    static Class ByteClass;
    static Class byteClass;
    static Class ShortClass;
    static Class shortClass;
    static Class IntegerClass;
    static Class integerClass;
    static Class LongClass;
    static Class longClass;
    static Class FloatClass;
    static Class floatClass;
    static Class DoubleClass;
    static Class doubleClass;

    // have to use static initializer because of exceptions
    static {
	try {
	    nullClass      = Void.TYPE;
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

/*
 * Keys to method cache.
 */

class MethodKey 
{

    private Class   target;
    private String  name;
    private Class[] argTypes;

    private static MethodKey reusableKey; // N.B.: concurrency

    private MethodKey (Class target, String name, Class[] argTypes) 
    {
	this.init(target, name, argTypes);
    }

    private void init (Class target, String name, Class[] argTypes) 
    {
	this.target   = target; 
	this.name     = name; 
	this.argTypes = argTypes;
    }

    static MethodKey newMethodKey (Class target, 
				   String name, 
				   Class[] argTypes) 
    {
	if (reusableKey == null) {
	    reusableKey = new MethodKey(target, name, argTypes);
	} else {
	    reusableKey.init(target, name, argTypes);
	}
	return reusableKey;
    }

    void remember() 
    {
	reusableKey = null;
    }

    public int hashCode () 
    {
	int v = target.hashCode() ^ (37 * name.hashCode());
	for (int i = 0; i < argTypes.length; i++) {
	    v = (v * 43) ^ argTypes[i].hashCode();
	}
	return v;
    }

    public boolean equals (Object x) 
    {
	if (! (x instanceof MethodKey)) {
	    return false;
	}
	MethodKey m = (MethodKey)x;
	return
	    target == m.target &&
	    name.equals(m.name) &&
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

