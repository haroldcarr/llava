
/*	Jon Meyer (meyer@cs.nyu.edu), Troy Downing (downing@nyu.edu)
	http://mrl.nyu.edu/
	February 1997

	Feel free to use and modify this code. Please leave
	this header in any derived works.

	Copyright (c) 1997 Jon Meyer and Troy Downing All rights reserved
*/

import java.io.*;
import java.util.Hashtable;

public class SimpleClassLoader extends ClassLoader {
    //
    // getClassFile actually loads the class. It takes the class name,
    // converts the name into a filename (e.g. "foo.Bang" -> "foo/Bang.class")
    // and attempts to read the contents of that file into a byte array. 
    // If it succeeds, the bytes are returned in an array. If something 
    // goes wrong, an IOException is thrown.
    //
    private byte[] getClassFile(String className) throws IOException {
        FileInputStream fin;
        byte classBytes[];

        // open the appropriate file
        fin = new FileInputStream(className.replace('.', File.separatorChar) 
                                  + ".class");
                
        // read the bytes in the file into an array called classBytes
        classBytes = new byte[fin.available()];
        fin.read(classBytes);

        // return the bytes
        return classBytes;
    }

    // The rest of this code implements the semantics for ClassLoaders
    // defined in the JVM specification.

    // we use hashtables to remember the classes we've loaded and resolved.
    //
    private Hashtable loadedClasses   = new Hashtable();
    private Hashtable resolvedClasses = new Hashtable();

    // loadClass is the method called by the system to do all the work
    //
    protected Class loadClass(String className, boolean resolveIt)                        
                           throws ClassNotFoundException {
        print("loadClass(\"" + className + "\", " + resolveIt + ") :");
        printIndent(1);

        // loadClass does two jobs. The first is to load the class. This
        // may entail obtaining the bytes using getClassFile, though we must also
        // check for classes that are already loaded, and for system classes.

        try {
            Class gotClass = null;

            if (loadedClasses.containsKey(className)) {
                // already loaded - don't do anything
                print("(already loaded class " + className + ")");

            } else if (className.startsWith("java")) {
                // wants a system class - call findSystemClass

                print("findSystemClass " + className); 
                printIndent(1);

                gotClass = findSystemClass(className);

                printIndent(-1);
            } else {
                // Some other class name - need to obtain the class as an array
                // of bytes. In our case, we call getClassFile to do this.

                byte classBytes[] = getClassFile(className);

                // now call defineClass, passing it classData
                print("defineClass " + className);
                printIndent(1);

                gotClass = defineClass(classBytes, 0, classBytes.length);

                printIndent(-1);
            }

            // ensure that the className is registered in the hashtable 
            if (gotClass != null) {
                loadedClasses.put(className, gotClass);
            }
        } catch (IOException ioe) {     // couldn't read the file
            System.err.println(ioe);    // so throw a ClassNotFoundException
            throw new ClassNotFoundException();     
        }

        // The second job of loadClass is to call resolveClass on classes
        // whenever it is told to do so (i.e. when resolveIt is true), but only
        // once per class.

        if (resolveIt && !resolvedClasses.containsKey(className)) {
            print("resolveClass " + className); 
            printIndent(1);

            // call resolveClass, passing it the class we have 
            // in the hashtable
            resolveClass((Class)loadedClasses.get(className));

            printIndent(-1);

            // remember that we've resolved it
            resolvedClasses.put(className, "true");
        }

        printIndent(-1);

        // return the class we loaded
        return (Class) loadedClasses.get(className);
    }

    // printing mechanism
    //
    int indentLevel = 0;

    void print(String str) {
        for (int i = indentLevel; i > 0; i--) System.out.print("    ");
        System.out.println(str);        
    }
    void printIndent(int level) { indentLevel += level; }

    // test program
    //
    public static void main(String arg[]) {
        try {
            //
            // To test the class loader, we need to load a class - so we load the
            // SimpleClassLoader class itself! We borrowed this idea from
            // http://magma.Mines.EDU/students/d/drferrin/Cool_Beans. Thanks!
            //
            System.out.println("Testing the class loader");

            SimpleClassLoader loader = new SimpleClassLoader();

            Class myself1 = loader.loadClass("SimpleClassLoader1", true);

            System.out.println("Making an instance of the loaded class");
            myself1.newInstance();


            Class myself = loader.loadClass("SimpleClassLoader", true);

            System.out.println("Making an instance of the loaded class");
            myself.newInstance();

        } catch (Throwable t) {
            t.printStackTrace(System.err);
            System.err.println(t);
        }
    }

}
