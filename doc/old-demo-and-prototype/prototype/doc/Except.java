/*
(public class MyExcept extends Exception
  (public MyExcept () (super)))

(public class Except
  (public static String sayIt ((String it)) throws myExcept
    (if (equals it "throw")
        (throw (new (MyExcept)))
        (return (+ "Hello " it)))))
 */

import java.util.Hashtable;

class MyExcept extends Exception {
  public MyExcept() { super(); }
}

class _Except_indirect {
  private static  Hashtable methods = new Hashtable();
  protected static void setMethod(String name, Object o) {
    methods.put(name, o);
  }
  public static Object getMethod(String name) { 
    return methods.get(name);
  }
}

// Every method declared to throw generic exception
// regardless of if none or more specific.

interface _Except_interface {
  public String sayIt(String it) throws Exception;
}  

class _Except_indirect_load_1 {
  static {
    _Except_indirect.setMethod(
      "sayIt",
      new _Except_interface() {
        public String sayIt(String it) throws Exception {
          if (it.equals("throw"))
            throw new MyExcept(); // More specific.
          else
            return "Hello " + it;
        }
      }
                              );
  }
}

class _Except_indirect_load_2 {
  static {
    _Except_indirect.setMethod(
      "sayIt",
      new _Except_interface() {
        public String sayIt(String it) throws Exception {
          return "Hello " + it; // No exception thrown.
        }
      }
                              );
  }
}

public class Except {
  public static void tryIt(String it){
    String result = null;
    try { // This wrapped around outer call to get through compiler.
          // But does must make expressions into explicit sequences.
      try {
        result =
          ((_Except_interface)_Except_indirect.getMethod("sayIt")).sayIt(it);
        System.out.println(result);
      } catch (MyExcept e) {
          System.err.println("Caught it: " + e);
      }
    } catch (Exception e) {
        System.err.println("Should never happen but must fool compiler " + e);
    }
  }
  public static void main(String[] av){
    new _Except_indirect_load_1();  // Load methods.
    tryIt("World!");
    tryIt("throw");
    new _Except_indirect_load_2();  // Redefine.
    tryIt("World!");
    tryIt("throw");
  }
}

