import gnu.bytecode.*;

public class bc {
	public static void main(String[] av) {
		ClassType ct = new ClassType("tt");
		ct.setSourceFile("Foo.bar");
		ct.addMethod("df");
		try {
			ct.writeToFile("tt.class");
		} catch (java.io.IOException e) {
		}
		System.out.println(ct.toString());
	}
}