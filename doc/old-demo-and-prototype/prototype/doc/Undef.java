/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/

import java.util.Hashtable;

class _JistSystem {
  protected static Object handleUndefinedLookup(String classname,
                                                String methodName) {
    System.out.println("Undefined lookup: " + classname + "." + methodName);
    if (methodName.equals("not"))
      return new _Undef_not_interface() {
                   public String not() {
                     return "Undef NOT";
                   };
             };

    else
      return new _Undef_sayIt_interface_2() {
                   public String sayIt() {
                     return "Undef sayIt";
                   };
             };
  }
}

class _Undef_indirect {
  private static  Hashtable methods = new Hashtable();
  protected static void setMethod(String name, Object o) {
    methods.put(name, o);
  }
  public static Object getMethod(String name) {
    Object result = methods.get(name);
    if (result == null)
      return _JistSystem.handleUndefinedLookup("Undef", name);
    else
      return result;
  }
}

interface _Undef_sayIt_interface {
  public String sayIt(String it);
}  

interface _Undef_sayIt_interface_2 {
  public String sayIt();
}  

interface _Undef_not_interface {
  public String not();
}  

class _Undef_indirect_load_1 {
  static {
    _Undef_indirect.setMethod(
      "sayItString",
      new _Undef_sayIt_interface() {
        public String sayIt(String it) {
          return "Undef " + it;
        }
      }
                             );
  }
}

public class Undef {
  public static void main(String[] av){
    new _Undef_indirect_load_1();  // Load methods.

    System.out.println(
     ((_Undef_sayIt_interface)_Undef_indirect.getMethod("sayItString")).sayIt("World!")
    );

    System.out.println(
     ((_Undef_not_interface)_Undef_indirect.getMethod("not")).not()
    );

    System.out.println(
     ((_Undef_sayIt_interface_2)_Undef_indirect.getMethod("sayItVoid")).sayIt()
    );

  }
}

