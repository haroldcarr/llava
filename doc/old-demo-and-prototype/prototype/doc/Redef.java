/*
(public class Redef
  (public static String sayIt ((String it))
    (return (+ "Hello " it))))
 */

import java.util.Hashtable;

class _Redef_indirect {
  private static  Hashtable methods = new Hashtable();
  protected static void clear() {
    methods.clear();
  }
  protected static void setMethod(String name, Object o) {
    methods.put(name, o);
  }
  public static Object getMethod(String name) { 
    return methods.get(name);
  }
}

interface _Redef_sayIt_interface {
  public String sayIt(String it);
}  

class _Redef_indirect_load_1 {
  static {
    _Redef_indirect.clear();
    _Redef_indirect.setMethod(
      "sayIt",
      new _Redef_sayIt_interface() {
        public String sayIt(String it) {
          return "Hello " + it;
        }
      }
                             );
  }
}

class _Redef_indirect_load_2 {
  static {
    _Redef_indirect.clear();
    _Redef_indirect.setMethod(
      "sayIt",
      new _Redef_sayIt_interface() {
        public String sayIt(String it) {
          return "Goodbye " + it;    // Only difference
        }
      }
                             );
  }
}

public class Redef {
  public static void tryIt(){
    System.out.println(
      ((_Redef_sayIt_interface)_Redef_indirect.getMethod("sayIt")).sayIt("World!")
    );
  }
  public static void main(String[] av){
    new _Redef_indirect_load_1();  // Load methods.
    tryIt();
    new _Redef_indirect_load_2();  // "Redefine" methods.
    tryIt();
  }
}

