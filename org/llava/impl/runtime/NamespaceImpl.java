//
// Created       : 2000 Oct 21 (Sat) 10:46:48 by Harold Carr.
// Last Modified : 2004 Dec 01 (Wed) 10:15:34 by Harold Carr.
//

package testLava.proto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import lava.F;
import lava.Lava;
import lava.lang.types.Pair;
import lava.lang.types.Symbol;

import libLava.r1.Engine;
import libLava.r1.FR1;
import libLava.r1.procedure.primitive.java.PrimNew;


// REVISIT - general Exception throw - make it specific.

// TODO:
//
// - Implement importJavaClassIntoCurrentPackage
//
// - try/catch should know about current imports
//   (like new does - or rather will)
//
// - Same goes for instanceof.  What else?
//
// - Convert to Java.
//
// - Integrate with Lava.
//
// - new, try/catch/instanceof should take short names if imported
//   long names regardless.

// Think about:
//
// * From any package you must import to enable BOTH short and long references.
//   In other words, only can ref imported things.
//
// * Have import precompute as much reflection as possible on all methods
//   on each class to avoid full DI.



public class NamespaceImpl
    implements
	Namespace
{
    //
    // The interface methods.
    //

    public Namespace newNamespace ()
    {
	NamespaceImpl ns = new NamespaceImpl();
	ns.init();
	return ns;
    }

    /**
     * "package" switches to the given package/class.
     * If that package/class does not exist then it is created.
     */
    public Namespace _package (Object p, Object c)
    {
	String PC = p.toString() + "." + c.toString();
	if (nextPackageShouldBe != null) {
	    if (! PC.equals(nextPackageShouldBe)) {
		throw F.newLavaException("incorrect package: " + PC + 
					 " should be: " + nextPackageShouldBe);
	    }
	}
	currentNamespace = findOrCreateNamespace(PC);
	return currentNamespace;
    }

    // --------------------------------------------------

    //
    // The implementation.
    //

    //
    // Class variables.
    //

    // Maps foo.bar to its namespace representation.
    // Used to find existing namespaces.

    private static Hashtable fullNameNamespaceMap;

    // The root namespace contains the built-in system procedures.
    // It MUST start as null so create call will work during initialization.

    private static NamespaceImpl rootNamespace;

    private static NamespaceImpl replNamespace;

    // "package" switches current.
    private static NamespaceImpl currentNamespace; 


    // When "import" finds a .lva file it will then load that file.
    // The system also ensures the the package statement in that file
    // is correct.

    private static String nextPackageShouldBe;

    //
    // Instance variables.
    //

    private String name;
    private Hashtable map;
    private Vector refList;
    private long fileLastModified; // Non-zero if associated file.
    private boolean classAlreadyImported; // true if associated class.

    // -------------------------

    //
    // Foundation.
    //

    public NamespaceImpl ()
    {
    }

    public NamespaceImpl (String name, Namespace root)
    {
	this.name             = name;
	this.map              = new Hashtable();
	this.refList          = new Vector();
	this.fileLastModified = 0;
	this.classAlreadyImported = false;

	refList.add(0, this); // Self at front.
	refList.add(root);    // root always at end.
    }

    //
    // Initialization.
    //

    public void init ()
    {
	fullNameNamespaceMap = new Hashtable();

	// N.B.: Each namespace always contains the topLevelNamespace
	// at the end of its refList.  During initialization we need to
	// explicitly remove this reference - both because it doesn't
	// exist so it is null - and if it did exist would cause a circular
	// lookup.

	rootNamespace = createNamespace("lava.Lava");
	rootNamespace.getRefList().remove(1);

	// The system starts in the REPL namespace.

	replNamespace = createNamespace("lava.REPL");
	currentNamespace = replNamespace;
    }

    public NamespaceImpl findNamespace (String name)
    {
	return (NamespaceImpl) fullNameNamespaceMap.get(name);
    }

    public NamespaceImpl createNamespace (String name)
    {
	// This should not be called if the name exists.
	// It does not check.  It just overwrites in that case.
	NamespaceImpl ns = new NamespaceImpl(name, rootNamespace);
	fullNameNamespaceMap.put(name, ns);
	return ns;
    }

    public NamespaceImpl findOrCreateNamespace (String name)
    {
	NamespaceImpl ns = findNamespace(name);
	if (ns == null) {
	    return createNamespace(name);
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
if alreadyImportedInPackage?
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

    public String _import (Symbol name)
	throws
	    Exception
    {
	return _import(name.toString());
    }

    public String _import (String name)
	throws
	    Exception
    {
	if (alreadyImportedInPackageP(name, getCurrentNamespace())) {
	    return alreadyImportedInCurrentPackage(name);
	} else {
	    return importIntoCurrentPackage(name);
	}
    }

    public boolean alreadyImportedInPackageP (String name, 
					      NamespaceImpl _package)
    {
	Vector refList = _package.getRefList();
	int size = refList.size();
	for (int i = 0; i < size; ++i) {
	    if (name.equals(((NamespaceImpl)refList.elementAt(i)).getName())) {
		return true;
	    }
	}
	return false;
    }

    public String alreadyImportedInCurrentPackage (String name)
	throws
	    Exception
    {
	// No need to import again. 
	// Stops infinite loading for packages which import each other.
	// Pick up any changes since last load.
	String result = loadLavaFileIfTouched(name);//Does not handle erasures.
	if (result != null) {
	    return result;
	} else {
	    return name;
	}
    }



    public String importIntoCurrentPackage (String name)
	throws
	    Exception
    {
	String result = null;
	result = handleExistsInFullNameNamespaceMap(name);
	if (result != null) 
	    return result;
	result = importJavaClassIntoCurrentPackage(name);
	if (result != null)
	    return result;
	result = importLavaFileIntoCurrentPackage(name);
	if (result != null)
	    return result;
	throw F.newLavaException(
            "importIntoCurrentPackage: should not happen." + name);
    }

    public String handleExistsInFullNameNamespaceMap (String name)
	throws
	    Exception
    {
	NamespaceImpl ns = findNamespace(name);
	String result = null;
	if (ns != null) {
	    // This is useful so one can interactively create packages
	    // and have them import each other.
	    getCurrentNamespace().addToRefList(ns);
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

    public String importJavaClassIntoCurrentPackage (String name)
    {
	try {
	    Class clazz = Class.forName(name);
	    NamespaceImpl savedNamespace = getCurrentNamespace();
	    // REVISIT
	    // probably only use create since it can't exist at this point.
	    setCurrentNamespace(findOrCreateNamespace(name));
	    try {
		// No need to check if already imported since this procedure
		// is only called in that case.
		System.out.println("here we would import the static methods and fields");
		getCurrentNamespace().setClassAlreadyImported(true);
		savedNamespace.addToRefList(getCurrentNamespace());
	    } finally {
		setCurrentNamespace(savedNamespace);
	    }
	    return "class " + name;
	} catch (ClassNotFoundException e) {
		return null;
	}
    }

    public String importLavaFileIntoCurrentPackage (String name)
    {
	return importLavaFileIntoCurrentPackageLoop(name, true, 0);
    }

    public String loadLavaFileIfTouched (String name)
	throws 
	    Exception
    {
	NamespaceImpl ns = findNamespace(name);
	if (ns.classAlreadyImported) {
	    return "already loaded: class " + name;
	}
	try {
	    // This will load the file if has been touched since last load time
	    // or if it is newly existent, i.e., if the import was created
	    // interactively and then later had a file associated with it.
	    return importLavaFileIntoCurrentPackageLoop(
		name, false, ns.getFileLastModified());
	} catch (Exception e) {
	    if (! e.getMessage().equals("Does not exist: " + name)) {
		throw e;
	    }
	    return null;
	}
    }

    public String importLavaFileIntoCurrentPackageLoop(
       String name, boolean addToRefListP, long fileLastModified)
    {
	StringTokenizer pathTokens = getClassPathTokens();
	String loadName = name.replace('.', '/');
	while (pathTokens.hasMoreTokens()) {
	    String currentPath = pathTokens.nextToken();
	    String result = 
		importLavaFileIntoCurrentPackageLoopAux(
		 name, loadName, currentPath, addToRefListP, fileLastModified);
	    if (result != null) {
		return result;
	    }
	}
	throw F.newLavaException("Does not exist: " + name);
    }

    /**
     * Return null if file not found or loaded.
     */
    public String importLavaFileIntoCurrentPackageLoopAux (
        String name, String loadName, String currentPath,
	boolean addToRefListP, long fileLastModified)
    {
	String loadPathAndName = currentPath + "/" + loadName + ".lva";
	File file = new File(loadPathAndName);
	if (! file.exists()) return null;
	if ((fileLastModified == 0) ||
	    (file.lastModified() > fileLastModified))
	{
	    NamespaceImpl savedPackage = null;
	    try {
		savedPackage = getCurrentNamespace();
		nextPackageShouldBe = name;
		// Depends on package procedure setting currentNamespace.
		// REVISIT - fix how this communicates when integrated.
		try {
		    Lava.lava.loadFile(loadPathAndName);
		} catch (Exception e) {
		    // REVISIT - should be FileNotFoundException
		    return null;
		}
		// We loaded the file, either because it was a new import
		// or because it had been touched.
		if (addToRefListP) {
		    savedPackage.addToRefList(getCurrentNamespace());
		}
		findNamespace(name).setFileLastModified(file.lastModified());
		return (addToRefListP ? "(re)load: " : "") + loadPathAndName;
	    } finally {
		setCurrentNamespace(savedPackage);
		nextPackageShouldBe = null;
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

    public Object setE (Symbol x, Object val)
    {
	return setE(x.toString(), val);
    }

    public Object setE (String x, Object val)
    {
	if (isDottedP(x)) {
	    throw F.newLavaException("setE!: no dots allowed: " + x);
	}
	getCurrentNamespace().getMap().put(x, val);
	return val;
    }

    public Object refE (Symbol x)
	throws
	    Exception
    {
	return refE(x.toString());
    }

    public Object refE (String x)
	throws
	    Exception
    {
	if (isDottedP(x)) {
	    return refDotted(packageAndClassOf(x), variableOf(x));
	} else {
	    return getCurrentNamespace().refNotDotted(x);
	}
    }

    public boolean isDottedP (String x)
    {
	return (x.indexOf(".") == -1 ? false : true);
    }

    public String packageAndClassOf (String x)
    {
	return x.substring(0, x.lastIndexOf("."));
    }

    public String variableOf (String x)
    {
	return x.substring(x.lastIndexOf(".") + 1, x.length());
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

    public Object refNotDotted (String v)
    {
	//(_print (list 'refNotDotted v))
	Vector refList = getRefList();
	int size = refList.size();
	for (int i = 0; i < size; i++) {
	    NamespaceImpl current = (NamespaceImpl) refList.elementAt(i);
	    Hashtable map = current.getMap();
	    if (map.containsKey(v)) {
		//(_print (list 'foundIn
		//(getName (elementAt refList i))
		//(get (getMap (elementAt refList i)) v)))
		return map.get(v);
	    }
	}
	throw F.newLavaException("undefined: " + v);
    }

    public String classNameOf (String x)
    {
	return variableOf(x);
    }

    public NamespaceImpl findMatch (String pc)
    {
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

    public Object refDotted(String pc, String m)
	throws 
	    Exception
    {
	String originalPC = pc;
	// Enables .foo shorthand for built-in procedures.
	pc = (pc.equals("") ? "lava.Lava" : pc);
	NamespaceImpl ns = getCurrentNamespace().findMatch(pc);
	//(_print (list "NS" ns m))
	if ((ns != null) && ns.getMap().containsKey(m)) {
	    return ns.refNotDotted(m);
	}
	// REVISIT
	// Should be F.newLavaException
	throw new Exception("refDotted: undefined: " +
			    originalPC + "." + m);
    }

    // --------------------------------------------------

    //
    // Support for "new".
    //

    public Object newE (Symbol x, Pair args)
    {
	return newE (x.toString(), args);
    }

    public Object newE (String x, Pair args)
    {
	if (isDottedP(x)) {
	    // REVISIT
	    PrimNew primNew = FR1.newPrimNew();
	    return primNew.apply(F.cons(F.newSymbol(x), args),
				 (Engine)null);
	}
	return newNotDotted(x, args);
    }

    public Object newNotDotted (String x, Pair args)
    {
	NamespaceImpl ns = getCurrentNamespace().findMatch(x);
	if (ns != null) {
	    // REVISIT
	    PrimNew primNew = FR1.newPrimNew();
	    // REVISIT is newSymbol necessary?
	    return primNew.apply(F.cons(F.newSymbol(ns.getName()), args),
				 (Engine) null);
	}
	throw F.newLavaException("Undefined: " + x);
    }

    // --------------------------------------------------

    //
    // Field accessors.
    //

    public NamespaceImpl getRootNamespace ()
    {
	return rootNamespace;
    }

    public NamespaceImpl getCurrentNamespace ()
    {
	return currentNamespace;
    }

    public NamespaceImpl setCurrentNamespace (NamespaceImpl namespace)
    {
	currentNamespace = namespace;
	return currentNamespace;
    }

    public String getName () 
    {
	return name; 
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
	int i = refList.size() - 1;
	while (i > -1) {
	    result = F.cons(((NamespaceImpl)refList.elementAt(i)).getName(),
			    result);
	    i = i - 1;
	}
	return result;
    }

    // Results in alpha order.

    public Pair getFullNameNamespaceMapKeys ()
    {
	Enumeration keys = fullNameNamespaceMap.keys();
	ArrayList arrayList = new ArrayList();
	while (keys.hasMoreElements()) {
	    arrayList.add(keys.nextElement());
	}
	Collections.sort(arrayList);
	int i = arrayList.size() - 1;
	Pair result = null;
	while (i > -1) {
	    result = F.cons(arrayList.get(i), result);
	    i = i - 1;
	}
	return result;
    }
}

// End of file.
