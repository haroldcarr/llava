/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/


//
// Created       : 2000 Oct 21 (Sat) 10:46:48 by Harold Carr.
// Last Modified : 2004 Dec 01 (Wed) 10:15:34 by Harold Carr.
//

package org.llava.impl.runtime.env;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;


import org.llava.impl.F;
import org.llava.Repl;
import org.llava.lang.exceptions.LlavaException;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Procedure;
import org.llava.lang.types.Symbol;

import org.llava.runtime.EnvironmentTopLevel;
import org.llava.impl.runtime.FR;
import org.llava.runtime.UndefinedIdHandler;

import org.llava.impl.runtime.procedure.generic.GenericProcedure; // REVISIT


// TODO:
//
// - No name of error message.
// llava-> (foo.bar)
// Error: java.lang.Exception: Undefined top level variable: 
//
// - Reenable import test in testBuiltIns.
//
// - If can't find nested import report the correct cannot find.
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
//   if the only namespace is org.llava and it is not sealed and you
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
//   Long references need to be legal from anywhere.  Otherwise,
//   if you import a package which defines a macro and that macro
//   uses a definition in a package not in the importing package
//   then the definition will be undefined.  The macro *must*
//   both import the needed definition (if it does not define it)
//   and use the fully qualified name in the expansion.
//   That way the definition is loaded AND when expanded in
//   the importing package, available via its full name.
//   Example  - cl.control.dotimes.
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

	// If two llava files each import each other we need to stop
	// circular loading.

	Set llavaFilesCurrentlyBeingLoaded =
	    Collections.synchronizedSet(new HashSet());

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

	    rootNamespace = createNamespace(F.llavaPackageName(), this);
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

    public Namespace _package (Symbol namespacePathAndClassName)
    {
	return _package(namespacePathAndClassName.toString());
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

    public boolean isDottedP (Symbol identifier)
    {
	return isDottedP(identifier.toString());
    }

    public Object setNotDotted(Symbol identifier, Object val)
    {
	return setNotDotted(identifier.toString(), val);
    }

    public Object refNotDotted (Symbol identifier)
    {
	return refNotDotted(identifier.toString(), false);
    }

    public Object refDotted (Symbol id)
    {
	String identifier = id.toString();
	return refDotted(packageAndClassOf(identifier),
			 variableOf(identifier),
			 false);
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
    // Support for package.
    //

    public Namespace _package (String packagePathAndClassName)
    {
	String PC = packagePathAndClassName;
	if (classVariables.nextPackageShouldBe != null) {
	    if (! PC.equals(classVariables.nextPackageShouldBe)) {
		throw F.newLlavaException("incorrect package: " + PC + 
					 " should be: " +
					 classVariables.nextPackageShouldBe);
	    }
	}
	classVariables.currentNamespace = findOrCreateNamespace(PC);
	return classVariables.currentNamespace;
    }


    // --------------------------------------------------

    //
    // Support for import.
    //

    /**
<pre>
if alreadyImportedInNamespace?
    loadLlavaFileIfTouched
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
    loadLlavaFileIfTouched
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
	    throw F.newLlavaException("package: "
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
	String result = loadLlavaFileIfTouched(name);
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
	result = importLlavaFileIntoNamespace(name);
	if (result != null)
	    return result;
	throw F.newLlavaException("importIntoNamespace: should not happen." +
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
	    result = loadLlavaFileIfTouched(name);
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
		//System.out.println(expr); // REVISIT
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

    public String importLlavaFileIntoNamespace (String name)
    {
	return importLlavaFileIntoNamespaceLoop(name, true, 0);
    }

    public String loadLlavaFileIfTouched (String name)
    {
	NamespaceImpl ns = findNamespace(name);
	if (ns.classAlreadyImported) {
	    return "already loaded: class " + name;
	}
	try {
	    // This will load the file if has been touched since last load time
	    // or if it is newly existent, i.e., if the import was created
	    // interactively and then later had a file associated with it.
	    return importLlavaFileIntoNamespaceLoop(
		name, false, ns.getFileLastModified());
	} catch (LlavaException e) {
	    if (! e.getThrowable().getMessage()
		    .equals("Does not exist: " + name)) {
		throw e;
	    }
	    return null;
	}
    }

    public String importLlavaFileIntoNamespaceLoop(
       String name, boolean addToRefListP, long fileLastModified)
    {
	StringTokenizer pathTokens = getClassPathTokens();
	String loadName = name.replace('.', '/');
	while (pathTokens.hasMoreTokens()) {
	    String currentPath = pathTokens.nextToken();
	    String result = 
		importLlavaFileIntoNamespaceLoopAux(
		 name, loadName, currentPath, addToRefListP, fileLastModified);
	    if (result != null) {
		return result;
	    }
	}
	throw F.newLlavaException("Does not exist: " + name);
    }

    /**
     * Return null if file not found or loaded.
     *
     * Note: this should only be called on a namespace which is the
     * currentNamespace.
     */
    public String importLlavaFileIntoNamespaceLoopAux (
        String name, String loadName, String currentPath,
	boolean addToRefListP, long fileLastModified)
    {
	String loadPathAndName = currentPath + "/" + loadName + ".lva";
	File file = new File(loadPathAndName);
	if (! file.exists()) {
	    return null;
	}
	return loadFileWhichExists(file, name, loadPathAndName,
				   addToRefListP, fileLastModified);
    }

    public String loadFileWhichExists (
        File file, String name, String loadPathAndName,
        boolean addToRefListP, long fileLastModified)
    {
	if ((fileLastModified == 0) ||
	    (file.lastModified() > fileLastModified))
	{
	    if (getLlavaFilesCurrentlyBeingLoaded().contains(loadPathAndName)) {
		if (addToRefListP) {
		    addToRefList(
                      findOrCreateNamespace(
		          packagePathAndName(name) + "." + classNameOf(name)));
		}
		return loadPathAndName;
	    }
	    // Needs to be loaded.
	    getLlavaFilesCurrentlyBeingLoaded().add(loadPathAndName);
	    try {
		return loadFileNewOrTouched(file, name, loadPathAndName, 
					    addToRefListP);
	    } finally {
		getLlavaFilesCurrentlyBeingLoaded().remove(loadPathAndName);
	    }
	}
	// REVISIT - duplicate code (see above).
	if (addToRefListP) {
	    addToRefList(
              findOrCreateNamespace(
		  packagePathAndName(name) + "." + classNameOf(name)));
	}
	return "no change: " + loadPathAndName;
    }

    public String loadFileNewOrTouched (
        File file, String name, String loadPathAndName, boolean addToRefListP)
    {
	try {
	    classVariables.nextPackageShouldBe = name;
	    getRepl().readCompileEval("(load \"" + loadPathAndName + "\")");
	    // We loaded the file, either because it was a new
	    // import or because it had been touched.
	    // Make sure it had a correct package definition.
	    if (! name.equals(getCurrentNamespace().getName())) {
		throw F.newLlavaException(
		    "Missing or incorrect package statement.  "
		    + "Expecting: " + name
		    + " when loading: " + loadPathAndName);
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
	    throw F.newLlavaException("setE!: no dots allowed: " + identifier);
	}

	NamespaceImpl current = (NamespaceImpl) getCurrentNamespace();

	return current.setNotDotted(identifier, val);
    }

    public Object setNotDotted (String identifier, Object val)
    {
	if (isSealed) {
	    throw F.newLlavaException("package: "
				     + getName()
				     + " is sealed.  Cannot set: "
				     + identifier
				     + " to: "
				     + val);
	}

	// Hashtable does not allow null values to be put.
	getMap().put(identifier, 
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
	    return ((NamespaceImpl)getCurrentNamespace())
		.refDotted(packageAndClassOf(identifier),
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

    // For references.
    public String packageAndClassOf (String identifier)
    {
	return identifier.substring(0, identifier.lastIndexOf("."));
    }

    // For references.
    public String variableOf (String identifier)
    {
	return identifier.substring(identifier.lastIndexOf(".") + 1,
				    identifier.length());
    }

    // For taking apart a pc internally.
    private String packagePathAndName (String packageAndClass)
    {
	return packageAndClassOf(packageAndClass);
    }

    /**
     *
     * This is the critical routine.
     * Possibilities:
     * 1. Only look in current namespace.
     *    But this means you do not pick up builtin llava variables.
     * 2. Look in current namespace.
     *    If not found look in llava namespace.
     *    But this means all llava imported functions must have
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
		    /*
		} else if (result instanceof GenericProcedure) {
		    // WORKAROUND/SET/UNDEFINED. See EnvTopLevelInitImpl.
		    ;
		    */
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

    public Object refDotted (String packagePathAndNameAndClassname, 
			     String identifier,
			     boolean ignoreUndefined)
    {
	String pc = packagePathAndNameAndClassname;
	String originalPC = pc;

	// Enables .foo shorthand for built-in procedures.
	pc = (pc.equals("") ? F.llavaPackageName() : pc);

	// REVISIT
	// Only ref imported things, even fully qualified:
	//
	// But for imported macros, where the macros themselves
	// ref something they import the macro must use the
	// fully qualified name.  The package that expands the
	// macro may not ref the package the macro depends on.
	// So, the macro imports to get it loaded and uses the
	// fully qualified name to ensure its found when expanded.
	/*
	NamespaceImpl ns = findNamespaceInRefList(pc);
	*/

	// REVISIT
	// Ref anything.
	//
	// REVISIT - swap order?
	// This case picks up ClassFoo.staticMethod.
	NamespaceImpl ns = findNamespaceInRefList(pc);
	if (ns == null) {
	    // This case picks up fully qualified.
	    ns = findNamespace(pc);
	}
	if (ns != null) {
	    Object result = lookupInMap(ns.getMap(), identifier,
					classVariables.NOT_FOUND);
	    if (result != classVariables.NOT_FOUND) {
		if (result == classVariables.NULL_VALUE) {
		    return null;
		    /*
		} else if (result instanceof GenericProcedure) {
		    // WORKAROUND/SET/UNDEFINED. See EnvTopLevelInitImpl.
		    ;
		    */
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
	    throw F.newLlavaException("getFullNameForClass: undefined: "
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

    public Set getLlavaFilesCurrentlyBeingLoaded ()
    {
	return classVariables.llavaFilesCurrentlyBeingLoaded;
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
