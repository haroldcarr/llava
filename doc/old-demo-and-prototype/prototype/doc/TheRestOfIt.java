/*

(import java.util.Hashtable.*)

(public class TheRest
  (private static int (numCreated 0))
  (public  static Integer numberCreated () (return (new (Integer numCreated))))
  (private Hashtable table)
  (public TheRest ()
    (set! numCreated (+ numCreated 1))
    (set! table (new (Hashtable))))
  (public void put ((Object key) (Object value))
    (put table key value))
  (public Object get ((Object key))
    (return (get table key))))
 */

import java.util.Hashtable;

class _TheRest_indirect {
  private static Hashtable methods = new Hashtable();
  protected static void setMethod(String name, Object o) {
    methods.put(name, o);
  }
  public static Object getMethod(String name) { 
    return methods.get(name);
  }
  private static Hashtable staticFields = new Hashtable();
  protected static int getStaticFieldInt(String name) {
    return ((Integer)staticFields.get(name)).intValue();
  }
  protected static void setStaticFieldInt(String name, int value) {
    staticFields.put(name, new Integer(value));
  }
  private static Hashtable fields = new Hashtable();
  protected static Object getField(Object key, String name) {
    return ((Hashtable)fields.get(key)).get(name);
  }
  protected static void setField(Object key, String name, Object value) {
    ((Hashtable)fields.get(key)).put(name, value);
  }
  protected static void allocateFields(Object key) {
    fields.put(key, new Hashtable());
  }
}

class TheRest {}

interface _TheRest_numberCreated_interface_1 {
  public Integer numberCreated();
}  

interface _TheRest_TheRest_interface_1 {
  public TheRest TheRest();
}  

interface _TheRest_put_interface_1 {
  public void put(TheRest _jthis, Object key, Object value);
}  

interface _TheRest_get_interface_1 {
  public Object get(TheRest _jthis, Object key);
}  

class _TheRest_indirect_load_1 {
  static {

    _TheRest_indirect.setStaticFieldInt("numCreated", 0);

    _TheRest_indirect.setMethod(
      "numberCreated",
      new _TheRest_numberCreated_interface_1() {
        public Integer numberCreated() {
          return new Integer(_TheRest_indirect.getStaticFieldInt("numCreated"));
        }
      }
                               );

    _TheRest_indirect.setMethod(
      "TheRest",
      new _TheRest_TheRest_interface_1() {
        public TheRest TheRest() {
          TheRest result = new TheRest();
          _TheRest_indirect.allocateFields(result);
          _TheRest_indirect.setStaticFieldInt(
            "numCreated",
            _TheRest_indirect.getStaticFieldInt("numCreated") + 1);
	  _TheRest_indirect.setField(result, "table", new Hashtable());
          return result;
        }
      }
                               );

    _TheRest_indirect.setMethod(
      "put",
      new _TheRest_put_interface_1() {
        public void put(TheRest _jthis, Object key, Object value) {
          ((Hashtable)_TheRest_indirect.getField(_jthis, "table")).put(key, value);
        }
      }
                               );

    _TheRest_indirect.setMethod(
      "get",
      new _TheRest_get_interface_1() {
        public Object get(TheRest _jthis, Object key) {
          return ((Hashtable)_TheRest_indirect.getField(_jthis, "table")).get(key);
        }
      }
                               );

  }
}

public class TheRestOfIt {
  private static void printNumberCreated() {
    System.out.println(
      ((_TheRest_numberCreated_interface_1)_TheRest_indirect.getMethod("numberCreated")).numberCreated()
                      );
  }

  private static TheRest createOne() {
    return ((_TheRest_TheRest_interface_1)_TheRest_indirect.getMethod("TheRest")).TheRest();
  }

  private static void put(TheRest o, Object key, Object value) {
    ((_TheRest_put_interface_1)_TheRest_indirect.getMethod("put")).put(o, key, value);  
  }

  private static Object get(TheRest o, Object key) {
    return ((_TheRest_get_interface_1)_TheRest_indirect.getMethod("get")).get(o, key);
  }

  public static void main(String[] av){
    new _TheRest_indirect_load_1();  // Load methods.

    printNumberCreated();

    TheRest o1 = null;
    TheRest o2 = null;

    o1 = createOne(); 
    o2 = createOne(); 

    put(o1, "one", new Integer(1));
    put(o1, "two", new Integer(2));
    put(o2, "one", "one");
    put(o2, "two", "two");

    System.out.println(get(o1, "one"));
    System.out.println(get(o1, "two"));
    System.out.println(get(o2, "one"));
    System.out.println(get(o2, "two"));

    printNumberCreated();
    createOne();
    createOne();
    printNumberCreated();
  }
}
