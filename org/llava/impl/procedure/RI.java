/**
 * Created       : 2000 Jan 26 (Wed) 17:08:19 by Harold Carr.
 * Last Modified : 2000 Feb 16 (Wed) 17:59:20 by Harold Carr.
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
				  
    public static Object create (Class myClass, Object[] args) 
	throws 
	    Throwable 
    {
	Class[] argTypes = getClasses(args);    
	Constructor[] constructors = myClass.getDeclaredConstructors();
	Vector goodConstructors = new Vector();
	for (int i = 0; i != constructors.length; i++) {
	    if (equalTypes(constructors[i].getParameterTypes(), argTypes)) {
		goodConstructors.addElement(constructors[i]);
	    }
	}
	Constructor c;
	switch (goodConstructors.size()) {
	case 0:
	    throw new NoSuchMethodException("no applicable constructor");
	case 1:
	    c = (Constructor)goodConstructors.firstElement();
	    break;
	default:
	    c = mostSpecific(goodConstructors);
	    break;
	}
	setAccessible(c, true);
	try {
	    return c.newInstance(args);
	} catch (InvocationTargetException e) {
	    throw(e.getTargetException()); 
	}
    }

    public static Object invoke (Object obj, String methodName, Object[] args) 
	throws 
	    NoSuchMethodException, 
	    Throwable 
    {
	return invoke(obj.getClass(), obj, methodName, args); 
    }

    public static Object invoke (Class myClass,
				 Object obj,
				 String methodName,
				 Object[] args) 
	throws 
	    NoSuchMethodException,
	    Throwable 
    {
	Class[] argTypes = getClasses(args);    
	Method method = lookupMethod(myClass, methodName, argTypes);
	if (method == null)
	    throw new NoSuchMethodException("no applicable method");
	else {
	    try {
		return method.invoke(obj, args); 
	    } catch (InvocationTargetException e) {
		throw(e.getTargetException()); 
	    }
	}
    }

    public static void setField (Object object,
				 String fieldName,
				 Object value)
	throws 
	    IllegalAccessException, 
	    NoSuchFieldException 
    {
	Class myClass = object.getClass();
	setField(object.getClass(), object, fieldName, value); 
    }


    public static void setField (Class myClass,
				 Object object,
				 String fieldName,
				 Object value)
	throws
	    IllegalAccessException, 
	    NoSuchFieldException 
    {
	Field field = fieldLookup(myClass, fieldName);
	field.set(object, value); 
    }

    public static Object getField (Object object, String fieldName) 
	throws 
	    IllegalAccessException,
	    NoSuchFieldException 
    {
	return getField(object.getClass(), object, fieldName);
    }

    public static Object getField (Class myClass,
				   Object object,
				   String fieldName) 
	throws
	    IllegalAccessException,
	    NoSuchFieldException 
    {
	Field field = fieldLookup(myClass, fieldName);
	return field.get(object); 
    }

    //
    // Implementation.
    //

    private static Object getMethod (Object obj,
				     String methodName,
				     Object[] args) 
	throws
	    NoSuchMethodException 
    {
	return getMethod(obj.getClass(), obj, methodName, args); 
    }
  

    private static Method getMethod (Class myClass,
				     Object obj,
				     String methodName,
				     Object[] args) 
	throws 
	    NoSuchMethodException 
    {
	Class[] argTypes = getClasses(args);    
	return lookupMethod(myClass, methodName, argTypes);
    }

    // synchronized since the key is reused

    private static synchronized Method lookupMethod (Class target, 
						     String name, 
						     Class[] argTypes) 
	throws 
	    NoSuchMethodException  
    {

	MethodKey key = MethodKey.make(target, name, argTypes);
	Object result = hash.get(key);
	if (result == null) {
	    Method nresult = lookupMethodFromScratch(target, name, argTypes);
	    if (nresult == null)
		return null;
	    else {
		key.keep();
		hash.put(key, nresult);
		return nresult; 
	    }
	} else
	    return (Method)result; 
    }

    private static Method lookupMethodFromScratch (Class target,
						   String name,
						   Class[] argTypes) 
	throws 
	    NoSuchMethodException 
    {

	// Exact match?
	try {
	    Method m = target.getMethod(name, argTypes); 
	    setAccessible(m, true);
	    return m;
	} catch (NoSuchMethodException e) {	
	    ;
	}

	return lookupMethodInSupers(new Vector(),
				       target,
				       name,
				       argTypes);
    }

    private static Method lookupMethodInSupers (Vector goodMethods,
						Class target,
						String name,
						Class[] argTypes) 
	throws 
	    NoSuchMethodException 
    {

	if (target == null) {
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
	    setAccessible(m, true);
	    return m;
	}

	Method[] methods = target.getDeclaredMethods();
	for (int i = 0; i != methods.length; i++) {
	    Class[] parmTypes = methods[i].getParameterTypes();
	    if (name.equals(methods[i].getName()) &&
		equalTypes(parmTypes, argTypes))
	    {
		goodMethods.addElement(methods[i]);
	    }
	}
	return lookupMethodInSupers(goodMethods,
				    target.getSuperclass(),
				    name,
				    argTypes);
    }

    static Field fieldLookup (Class myClass, String fieldName) 
	throws
	    NoSuchFieldException 
    {
	Field field;
	try {
	    field = myClass.getField(fieldName);	   
	} catch (NoSuchFieldException e) {
	    field = fieldLookup0(myClass, fieldName);
	}
	setAccessible(field, true);	// public fields can be inaccessible
	return field;
    }

    // if it's a non-public field, use this uglier method

    static Field fieldLookup0 (Class myClass, String fieldName) 
	throws
	    NoSuchFieldException 
    {
	Field[] localFields = myClass.getDeclaredFields();
	for (int i = 0; i != localFields.length; i++) {
	    if (fieldName.equals(localFields[i].getName())) {
		Field f = localFields[i];
		return f;
	    }
	}
	// didn't find it, go up to superclass
	Class sup = myClass.getSuperclass();
	if (sup == null)
	    throw new NoSuchFieldException(fieldName);
	else
	    return fieldLookup(sup, fieldName);
    }


    // go thru reflection so I can compile this damned thing under 1.1
    private static Method setAccessibleMethod = null;

    private static void setAccessible (Object thing, boolean accessible) 
    {
	try {
	    if (setAccessibleMethod == null) {
		Class aclass = Class.forName("java.lang.reflect.AccessibleObject");
		setAccessibleMethod =
		    aclass.getMethod("setAccessible", new Class[]{ booleanClass });
	    }
	    setAccessibleMethod.invoke(thing, new Object[]{ Boolean.TRUE }); // +++ array can be cached
	} catch (Throwable e) {
	    System.out.println("Error trying to set accessibility for " + thing);
	}
    }

    private static boolean equalTypes (Class[] parmTypes, 
				       Class[] argTypes) 
    {
	if (parmTypes.length != argTypes.length) {
	    return false;
	}
	for (int i = 0; i != parmTypes.length; i++) {
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

    private static Class wrappedClass (Class clazz) 
    {
	if (clazz.isPrimitive()) {
	    Class wrappedClass;
	    if (clazz == booleanClass)   return BooleanClass;
	    if (clazz == integerClass)   return IntegerClass;
	    if (clazz == characterClass) return CharacterClass;
	    if (clazz == byteClass)      return ByteClass;
	    if (clazz == shortClass)     return ShortClass;
	    if (clazz == longClass)      return LongClass;
	    if (clazz == floatClass)     return FloatClass;
	    if (clazz == doubleClass)    return DoubleClass;
	    throw new Error("DI.wrappedClass: unknown type" + clazz);
	}
	return clazz;
    }
  
    static Constructor mostSpecific (Vector constructors) 
	throws
	    Throwable 
    {
	for (int i = 0; i != constructors.size(); i++) {
	    for (int j = 0; j != constructors.size(); j++) {
		if ((i != j) &&
		    (moreSpecific((Constructor)constructors.elementAt(i), (Constructor)constructors.elementAt(j)))) {
		    constructors.removeElementAt(j);
		    if (i > j) i--;
		    j--;
		}
	    }
	}
	if (constructors.size() == 1)
	    return (Constructor)constructors.elementAt(0);
	else
	    throw new NoSuchMethodException(">1 most specific constructor");
    }

    private static Method mostSpecificMethod (Vector methods) 
	throws
	    NoSuchMethodException 
    {
	for (int i = 0; i != methods.size(); i++) {
	    for (int j = 0; j != methods.size(); j++) {
		if ((i != j) &&
		    (moreSpecific((Method)methods.elementAt(i), (Method)methods.elementAt(j)))) {
		    methods.removeElementAt(j);
		    if (i > j) i--;
		    j--;
		}
	    }
	}
	if (methods.size() == 1)
	    return (Method)methods.elementAt(0);
	else
	    throw new NoSuchMethodException(">1 most specific method");
    }

    // True IFF c1 is more specific than c2

    private static boolean moreSpecific (Constructor c1, Constructor c2) 
    {
	Class[] parmTypes1 = c1.getParameterTypes();
	Class[] parmTypes2 = c2.getParameterTypes();
	int n = parmTypes1.length;
	for (int i = 0; i != n; i++) {
	    if (equalType(parmTypes1[i], parmTypes2[i])) {
		return false;
	    }
	}
	return true;
    }

    // True IFF c1 is more specific than c2

    private static boolean moreSpecific (Method c1, Method c2) 
    {
	if (!equalType(c2.getDeclaringClass(), c1.getDeclaringClass())) // needed for jdk12 only
	    return false;
	Class[] parmTypes1 = c1.getParameterTypes();
	Class[] parmTypes2 = c2.getParameterTypes();
	int n = parmTypes1.length;
	for (int i = 0; i != n; i++) {
	    if (!equalType(parmTypes2[i], parmTypes1[i])) {
		return false;
	    }
	}
	return true;
    }

    // given an array of objects, return an array of corresponding classes
    private static Class[] getClasses (Object[] args) 
    {
	Class[] classes = new Class[args.length];
	for (int i = 0; i != args.length; i = i + 1)
	    classes[i] = ((args[i] == null) ? NullClass : args[i].getClass());
	return classes; 
    }

    // REVISIT - use weak references
    static Hashtable hash = new Hashtable();

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

class MethodKey {
    Class target;
    String name;
    Class[] argTypes;

    static MethodKey spareKey;

    // use this instead of a constructor (but beware of concurrency)
    static MethodKey make (Class t,String n, Class[] a) 
    {
	if (spareKey == null)
	    spareKey = new MethodKey(t, n, a);
	else
	    spareKey.setup(t, n, a);
	return spareKey;
    }

    void setup(Class t,String n, Class[] a) {
	target = t; name = n; argTypes = a; }

    void keep() {
	spareKey = null;
    }

    public String toString () 
    {
	return "<<" + target + "." + name + toStringArray(argTypes) + ">>"; 
    }

    // Built-in array printing is lousy, so use this. Could be more generally useful
    static String toStringArray (Object[] array) 
    {
	java.io.StringWriter out = new java.io.StringWriter();
	out.write('[');
	for (int i = 0; i != array.length; i++) {
	    out.write(array[i].toString());
	    out.write(' '); 
	}
	out.write(']');
	return out.toString(); 
    }

    MethodKey (Class t,String n, Class[] a) 
    {
	target = t; name = n; argTypes = a; 
    }

    public boolean equals (Object mx) 
    {
	if (mx instanceof MethodKey) {
	    MethodKey m = (MethodKey)mx;
	    boolean v =
		(target == m.target &&
		 name.equals(m.name) &&
		 arrayEquals(argTypes, m.argTypes));
	    return v; 
	} else
	    return false;
    }

    static boolean arrayEquals (Class[] c1, Class[] c2) 
    {
	if (c1.length == c2.length) {
	    for (int i = 0; i != c1.length; i++) {
		if (c1[i] != c2[i])
		    return false;
	    }
	    return true; 
	} else
	    return false;
    }

    public int hashCode () 
    {
	int v = target.hashCode() ^ (37*name.hashCode());
	for (int i = 0; i != argTypes.length; i++) // added this, but seems to make things slower...
	    v = (v * 43) ^ argTypes[i].hashCode();
	return v;
    }
}

// End of file.

