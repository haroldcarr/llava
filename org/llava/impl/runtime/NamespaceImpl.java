//
// Created       : 2000 Oct 21 (Sat) 10:46:48 by Harold Carr.
// Last Modified : 2004 Dec 01 (Wed) 10:15:34 by Harold Carr.
//

package libLava.r1.env;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import lava.F;
import lava.Repl;
import lava.lang.exceptions.LavaException;
import lava.lang.types.Pair;
import lava.lang.types.Procedure;
import lava.lang.types.Symbol;

import libLava.rt.EnvironmentTopLevel;
import libLava.rt.FR;
import libLava.rt.UndefinedIdHandler;


// TODO:
//
// - Reenable import test in testBuiltIns.
//
// - Reenable/fix selective import
//
// - new, ,catch, instanceof should take short names if imported,
//   long names regardless.
//
// - Make thread safe.

// NOTE:
//
// - macros imported into root do not work.
//   if the only namespace is lava.Lava and it is not sealed and you
//   do:
//
// (import test.tt) ;; contains a macro definition of "m".
// (define (m x) (list x))
//
// right at startup then defGenInternal complains that it is
// expecting a lambda.
//
// I have not tracked this down and do not intend to since the root
// package is now sealed.  It probably has something to do with
// the root needs to be the last in the refList but if you import
// then it screws up the order or makes it circular.


// Think about:
//
// * From any package you must import to enable BOTH short and long references.
//   In other words, only can ref imported things.
//
// * Have import precompute as much reflection as possible on all methods
//   on each class to avoid full DI.

public class NamespaceImpl
    implements
	EnvironmentTopLevel,
	Namespace
{
    private String name;

    private Hashtable map;

    private Vector refList;

    // Non-zero if namespace has an associated .lva file.
    private long fileLastModified;

    // true if namespace has an associated .class file.
    private boolean classAlreadyImported;

    // true if illegal to import or set values in a namespace.
    private boolean isSealed;

    private ClassVariables classVariables;

    /**
     * Variables shared between all class instances whose constructor
     * is passed an instance of ClassVariables.
     *
     * This is so multiple environments can exist independently.
     */
    class ClassVariables
    {
	// Maps <package>[.<package>]* to its namespace representation.
	// Used to find existing namespaces.

	Hashtable fullNameNamespaceMap = new Hashtable();

	// The root namespace contains the built-in system procedures.
	// It MUST start as null so create call will work during
	// initialization.

	NamespaceImpl rootNamespace;

	// "package" switches the current namespace.

	NamespaceImpl currentNamespace;

	// When "import" finds a .lva file it will then load that file.
	// The system ensures the package statement in that file is correct.

	String nextPackageShouldBe = null;

	// Marker to return when values not found in map.

	Object NOT_FOUND = new Object();

	// Marker to indicate a null value is stored in the map.

	Object NULL_VALUE = new Object();

	UndefinedIdHandler undefinedIdHandler = FR.newUndefinedIdHandler();

	Repl repl;

	ClassVariables ()
	{
	    // N.B.: Each namespace always contains the topLevelNamespace
	    // at the end of its refList.  During initialization we need to
	    // explicitly remove this reference - both because it doesn't
	    // exist so it is null - and if it did exist would cause a circular
	    // lookup.

	    rootNamespace = createNamespace("lava.Lava", this);
	    rootNamespace.getRefList().remove(1);
	    currentNamespace = rootNamespace;
	}
    }

    //
    // EnvironmentTopLevel interface methods.
    //

    public EnvironmentTopLevel newEnvironmentTopLevel ()
    {
	NamespaceImpl ns = new NamespaceImpl(new ClassVariables());
	return ns;
    }

    /**
     * Gets the value of the given identifier:
     * - if .<id> from the root namespace.
     * - if <foo>.<bar>.<id> from the specified namespace.
     * - if <id> then search from current through its reference list.
     */
    public Object get (Symbol identifier)
    {
	return get(identifier.toString(), false);
    }

    public Object getNoError (Symbol identifier)
    {
	return get(identifier.toString(), true);
    }

    /**
     * Sets the value of the given identifier in the current namespace to
     * the given value.
     * Returns the given value.
     */
    public Object set (Symbol identifier, Object val)
    {
	return set(identifier.toString(), val);
    }

    public UndefinedIdHandler getUndefinedIdHandler ()
    {
	return classVariables.undefinedIdHandler;
    }

    public UndefinedIdHandler setUndefinedIdHandler (UndefinedIdHandler handler)
    {
	return classVariables.undefinedIdHandler = handler;
    }

    //
    // Namespace interface methods.
    //

    public Namespace _package (Symbol packagePathAndName, Symbol className)
    {
	String PC = packagePathAndName.toString() + "." + className.toString();
	if (classVariables.nextPackageShouldBe != null) {
	    if (! PC.equals(classVariables.nextPackageShouldBe)) {
		throw F.newLavaException("incorrect package: " + PC + 
					 " should be: " +
					 classVariables.nextPackageShouldBe);
	    }
	}
	classVariables.currentNamespace = findOrCreateNamespace(PC);
	return classVariables.currentNamespace;
    }

    public String _import (Symbol classPathAndName)
    {
	return _import(classPathAndName.toString());
    }

    public boolean setIsSealed (boolean b)
    {
	return isSealed = b;
    }

    public Namespace findNamespace (Symbol name)
    {
	return findNamespace(name.toString());
    }

    public Namespace getCurrentNamespace ()
    {
	return classVariables.currentNamespace;
    }

    public String getName () 
    {
	return name; 
    }

    public String getFullNameForClass (Object x)
    {
	return getFullNameForClass(x.toString());
    }

    // --------------------------------------------------

    //
    // The implementation.
    //

    //
    // Foundation.
    //

    public NamespaceImpl ()
    {
    }

    public NamespaceImpl (ClassVariables classVariables)
    {
	this.classVariables = classVariables;
    }

    public NamespaceImpl (String name, Namespace root, 
			  ClassVariables classVariables)
    {
	this.name             = name;
	this.map              = new Hashtable();
	this.refList          = new Vector();
	this.fileLastModified = 0;
	this.classAlreadyImported = false;
	this.isSealed         = false;

	this.refList.add(0, this); // Self at front.
	                      // imports between.
	this.refList.add(root);    // root always at end.
	this.classVariables   = classVariables;
    }

    public NamespaceImpl createNamespace (String name, 
					  ClassVariables classVariables)
    {
	// This should not be called if the name exists.
	// It does not check.  It just overwrites in that case.
	NamespaceImpl ns = new NamespaceImpl(name, 
					     classVariables.rootNamespace,
					     classVariables);
	classVariables.fullNameNamespaceMap.put(name, ns);
	return ns;
    }

    public NamespaceImpl findNamespace (String name)
    {
	return (NamespaceImpl) classVariables.fullNameNamespaceMap.get(name);
    }

    public NamespaceImpl findOrCreateNamespace (String name)
    {
	NamespaceImpl ns = findNamespace(name);
	if (ns == null) {
	    return createNamespace(name, classVariables);
	} else {
	    return ns;
	}
    }

    // --------------------------------------------------

    //
    // Support for import.
    //

    /**
<pre>
if alreadyImportedInNamespace?
    loadLavaFileIfTouched
        if not classAlreadyImported
            ignore FileNotFoundException
	        loadLoop false lastModified
		    if file not exists
		         throw FileNotFoundException
		    if lastModified = 0 || fileTime > lastModified
		         ;; may be 0 if initially created interactively
		         ;; but now has an associated file
		         load file
else existsInFullNameNamespaceMap
    addToRefList
    loadLavaFileIfTouched
else if java class exists
    import class
    classAlreadyImported = true
    addToRefList
else
    loadLoop true 0
        if file not exists
	    throw FileNotFoundException
        load file
	addToRefList
</pre>
    */

    public String _import (String name)
    {
	NamespaceImpl current = (NamespaceImpl) getCurrentNamespace();

	if (current.isSealed) {
	    throw F.newLavaException("package: "
				     + current.getName()
				     + " is sealed.  Cannot import: "
				     + name);
	}

	if (current.alreadyImportedInNamespaceP(name)) {
	    return current.alreadyImportedInNamespace(name);
	} else {
	    return current.importIntoNamespace(name);
	}
    }

    public boolean alreadyImportedInNamespaceP (String name)
    {
	Vector refList = getRefList();
	int size = refList.size();
	for (int i = 0; i < size; ++i) {
	    if (name.equals(((NamespaceImpl)refList.elementAt(i)).getName())) {
		return true;
	    }
	}
	return false;
    }

    public String alreadyImportedInNamespace (String name)
    {
	// No need to import again. 
	// Stops infinite loading for packages which import each other.
	// Pick up any changes since last load.
	// Note: does not handle erasures.
	String result = loadLavaFileIfTouched(name);
	if (result != null) {
	    return result;
	} else {
	    return name;
	}
    }

    public String importIntoNamespace (String name)
    {
	String result = null;
	result = handleExistsInFullNameNamespaceMap(name);
	if (result != null) 
	    return result;
	result = importJavaClassIntoNamespace(name);
	if (result != null)
	    return result;
	result = importLavaFileIntoNamespace(name);
	if (result != null)
	    return result;
	throw F.newLavaException("importIntoNamespace: should not happen." +
				 name);
    }

    public String handleExistsInFullNameNamespaceMap (String name)
    {
	NamespaceImpl ns = findNamespace(name);
	String result = null;
	if (ns != null) {
	    // This is useful so one can interactively create packages
	    // and have them import each other.
	    addToRefList(ns);
	    // Does not handle erasures.
	    result = loadLavaFileIfTouched(name);
	    if (result == null) {
		return name;
	    } else {
		return result;
	    }
	} else {
	    return null;
	}
    }

    /**
     * Note: this should only be called on a namespace which is the
     * currentNamespace.
     */
    public String importJavaClassIntoNamespace (String name)
    {
	try {
	    Class clazz = Class.forName(name);
	    // REVISIT
	    // probably only use create since it can't exist at this point.
	    setCurrentNamespace(findOrCreateNamespace(name));
	    try {
		// No need to check if already imported since this procedure
		// is only called in that case.
		String expr = "(_%importAux '" + name + ")";
		System.out.println(expr); // REVISIT
		getRepl().readCompileEval(expr);
		((NamespaceImpl)getCurrentNamespace())
		    .setClassAlreadyImported(true);
		addToRefList((NamespaceImpl)getCurrentNamespace());
	    } finally {
		setCurrentNamespace(this);
	    }
	    return "class " + name;
	} catch (ClassNotFoundException e) {
		return null;
	}
    }

    public String importLavaFileIntoNamespace (String name)
    {
	return importLavaFileIntoNamespaceLoop(name, true, 0);
    }

    public String loadLavaFileIfTouched (String name)
    {
	NamespaceImpl ns = findNamespace(name);
	if (ns.classAlreadyImported) {
	    return "already loaded: class " + name;
	}
	try {
	    // This will load the file if has been touched since last load time
	    // or if it is newly existent, i.e., if the import was created
	    // interactively and then later had a file associated with it.
	    return importLavaFileIntoNamespaceLoop(
		name, false, ns.getFileLastModified());
	} catch (LavaException e) {
	    if (! e.getThrowable().getMessage()
		    .equals("Does not exist: " + name)) {
		throw e;
	    }
	    return null;
	}
    }

    public String importLavaFileIntoNamespaceLoop(
       String name, boolean addToRefListP, long fileLastModified)
    {
	StringTokenizer pathTokens = getClassPathTokens();
	String loadName = name.replace('.', '/');
	while (pathTokens.hasMoreTokens()) {
	    String currentPath = pathTokens.nextToken();
	    String result = 
		importLavaFileIntoNamespaceLoopAux(
		 name, loadName, currentPath, addToRefListP, fileLastModified);
	    if (result != null) {
		return result;
	    }
	}
	throw F.newLavaException("Does not exist: " + name);
    }

    /**
     * Return null if file not found or loaded.
     *
     * Note: this should only be called on a namespace which is the
     * currentNamespace.
     */
    public String importLavaFileIntoNamespaceLoopAux (
        String name, String loadName, String currentPath,
	boolean addToRefListP, long fileLastModified)
    {
	String loadPathAndName = currentPath + "/" + loadName + ".lva";
	File file = new File(loadPathAndName);
	if (! file.exists()) {
	    return null;
	}
	if ((fileLastModified == 0) ||
	    (file.lastModified() > fileLastModified))
	{
	    try {
		classVariables.nextPackageShouldBe = name;
		getRepl()
		    .readCompileEval("(load \"" + loadPathAndName + "\")");
		// We loaded the file, either because it was a new import
		// or because it had been touched.

		// Make sure it had a correct package definition.
		if (! name.equals(getCurrentNamespace().getName())) {
		    throw F.newLavaException(
                        "Missing or incorrect package statement.  Expecting: "
			+ name);
		}

		if (addToRefListP) {
		    // NOTE: Depends on package procedure setting
		    // currentNamespace during the above load.
		    addToRefList((NamespaceImpl)getCurrentNamespace());
		}
		findNamespace(name).setFileLastModified(file.lastModified());
		return (addToRefListP ? "(re)load: " : "") + loadPathAndName;
	    } finally {
		setCurrentNamespace(this);
		classVariables.nextPackageShouldBe = null;
	    }
	}
	return "no change: " + loadPathAndName;
    }

    public void addToRefList (NamespaceImpl reference)
    {
	Vector refList = getRefList();
	refList.add(refList.size() - 1, reference);
    }

    public StringTokenizer getClassPathTokens ()
    {
	String separator = System.getProperty("path.separator");
	StringTokenizer pathTokens =
	    new StringTokenizer(System.getProperty("java.class.path"),
				separator);
	return pathTokens;
    }

    // --------------------------------------------------

    //
    // Support for ref/set
    //

    public Object set (String identifier, Object val)
    {
	if (isDottedP(identifier)) {
	    throw F.newLavaException("setE!: no dots allowed: " + identifier);
	}

	NamespaceImpl current = (NamespaceImpl) getCurrentNamespace();

	if (current.isSealed) {
	    throw F.newLavaException("package: "
				     + current.getName()
				     + " is sealed.  Cannot set: "
				     + identifier
				     + " to: "
				     + val);
	}


	Hashtable map = ((NamespaceImpl)getCurrentNamespace()).getMap();
	// Hashtable does not allow null values to be put.
	map.put(identifier, 
		val == null ? classVariables.NULL_VALUE : val);
	// REVISIT
	// TestCompilerAndEngine "(list 1)" after setting list to
	// newPrimList fails - because DI does not throw the right
	// error.  I think because then name was null until I
	// added the following code (from EnvironmentTopLevelImpl).
	if (val != null && val instanceof Procedure) {
	    ((Procedure)val).setName(identifier);
	}

	return val;
    }

    public Object get (String identifier, boolean ignoreUndefined)
    {
	if (isDottedP(identifier)) {
	    return refDotted(packageAndClassOf(identifier),
			     variableOf(identifier),
			     ignoreUndefined);
	} else {
	    return ((NamespaceImpl)getCurrentNamespace())
		.refNotDotted(identifier, ignoreUndefined);
	}
    }

    public boolean isDottedP (String identifier)
    {
	return (identifier.indexOf(".") == -1 ? false : true);
    }

    public String packageAndClassOf (String identifier)
    {
	return identifier.substring(0, identifier.lastIndexOf("."));
    }

    public String variableOf (String identifier)
    {
	return identifier.substring(identifier.lastIndexOf(".") + 1,
				    identifier.length());
    }

    /**
     *
     * This is the critical routine.
     * Possibilities:
     * 1. Only look in current namespace.
     *    But this means you do not pick up builtin lava variables.
     * 2. Look in current namespace.
     *    If not found look in lava namespace.
     *    But this means all lava imported functions must have
     *    at least its "class" name (e.g., Aif.aif).
     *    This most closely resembles static class methods/fields accessed.
     *    But it is inconvenient in terms of Lisp.
     * 3. Go through refList until found or undefined.
     *    We choose this one.
     *
     */

    public Object refNotDotted (String identifier, boolean ignoreUndefined)
    {
	Vector refList = getRefList();
	int size = refList.size();
	for (int i = 0; i < size; i++) {
	    NamespaceImpl current = (NamespaceImpl) refList.elementAt(i);
	    Hashtable map = current.getMap();
	    Object result = lookupInMap(map, identifier, 
					classVariables.NOT_FOUND);
	    if (result != classVariables.NOT_FOUND) {
		if (result == classVariables.NULL_VALUE) {
		    return null;
		} else {
		    return result;
		}
	    }
	}
	return
	    ignoreUndefined ?
	    null :
	    classVariables.undefinedIdHandler.handle(this, 
						     F.newSymbol(identifier));
    }

    public Object lookupInMap(Hashtable map, String identifier,
			      Object notFound)
    {
	Object result = map.get(identifier);
	if (result != null) {
	    return result;
	} else {
	    // Null is a valid value so need to differentiate
	    // whether it is a value or just not there.
	    if (map.containsKey(identifier)) {
		return result;
	    } else {
		return notFound;
	    }
	}
    }

    public String classNameOf (String identifier)
    {
	return variableOf(identifier);
    }

    public NamespaceImpl findNamespaceInRefList (String packagePathAndNameAndClassname)
    {
	String pc = packagePathAndNameAndClassname;
	Vector refList = getRefList();
	int size = refList.size();
	for (int i = 0; i < size; i++) {
	    NamespaceImpl current = (NamespaceImpl) refList.elementAt(i);
	    String name = current.getName();
	    if (pc.equals(name) ||
		pc.equals(classNameOf(name))) 
            {
		return current;
	    }
	}
	return null;
    }

    public Object refDotted(String packagePathAndNameAndClassname, 
			    String identifier,
			    boolean ignoreUndefined)
    {
	String pc = packagePathAndNameAndClassname;
	String originalPC = pc;
	// Enables .foo shorthand for built-in procedures.
	pc = (pc.equals("") ? "lava.Lava" : pc);
	NamespaceImpl ns = 
	    ((NamespaceImpl)getCurrentNamespace()).findNamespaceInRefList(pc);
	if (ns != null) {
	    Object result = lookupInMap(ns.getMap(), identifier,
					classVariables.NOT_FOUND);
	    if (result != classVariables.NOT_FOUND) {
		if (result == classVariables.NULL_VALUE) {
		    return null;
		} else {
		    return result;
		}
	    }
	}
	return
	    ignoreUndefined ?
	    null :
	    classVariables.undefinedIdHandler.handle(
                this, 
		F.newSymbol(originalPC 
			    + "." 
			    + identifier));
    }

    // --------------------------------------------------

    //
    // Support for "new", "instanceof", "catch".
    //

    public String getFullNameForClass (String x)
    {
	if (isDottedP(x)) {
	    return x;
	}
	NamespaceImpl ns = 
	    ((NamespaceImpl)getCurrentNamespace()).findNamespaceInRefList(x);
	if (ns == null) {
	    throw F.newLavaException("getFullNameForClass: undefined: "
				     + x);
	}
	return ns.getName();
    }

    // --------------------------------------------------

    //
    // Field accessors.
    //

    public NamespaceImpl getRootNamespace ()
    {
	return classVariables.rootNamespace;
    }

    public NamespaceImpl setCurrentNamespace (NamespaceImpl namespace)
    {
	classVariables.currentNamespace = namespace;
	return classVariables.currentNamespace;
    }

    public Repl getRepl ()
    {
	return classVariables.repl;
    }

    public Repl setRepl (Repl repl)
    {
	return classVariables.repl = repl;
    }

    public Hashtable getMap ()
    {
	return map; 
    }
    
    public Vector getRefList ()
    {
	return refList; 
    }

    public long setFileLastModified (long lastModified)
    {
	fileLastModified = lastModified;
	return fileLastModified;
    }

    public long getFileLastModified ()
    {
	return fileLastModified; 
    }

    public boolean setClassAlreadyImported (boolean b)
    {
	classAlreadyImported = b; 
	return classAlreadyImported;
    }

    public boolean classAlreadyImported ()
    {
	return classAlreadyImported; 
    }

    // --------------------------------------------------

    //
    // Testing routines.
    //

    //
    // Returns a list of the namespaces imported by a namespace
    // in reference order (i.e., most recent to root).
    //

    public Pair getRefListNames () 
    {
	Vector refList = getRefList();
	Pair result = null;
	for (int i = refList.size() - 1; i > -1; i--) {
	    result = F.cons(((NamespaceImpl)refList.elementAt(i)).getName(),
			    result);
	}
	return result;
    }

    // Results in alpha order.

    public Pair getFullNameNamespaceMapKeys ()
    {
	Enumeration keys = classVariables.fullNameNamespaceMap.keys();
	ArrayList arrayList = new ArrayList();
	while (keys.hasMoreElements()) {
	    arrayList.add(keys.nextElement());
	}
	Collections.sort(arrayList);
	Pair result = null;
	for (int i = arrayList.size() - 1; i > -1; i--) {
	    result = F.cons(arrayList.get(i), result);
	}
	return result;
    }

    public String toString ()
    {
	return "{NamespaceImpl:" + name + "}";
    }
}

// End of file.
