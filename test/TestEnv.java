/**
 * Created       : 1999 Dec 21 (Tue) 20:01:34 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 15:24:34 by Harold Carr.
 */

package testLava;

import lavaProfile.F;
import lava.lang.exceptions.LavaException;
import lava.lang.types.Symbol;
import lavaProfile.util.List;
import lavaProfile.compiler.*;
import lavaProfile.compiler.FC;
import lava.runtime.EnvironmentTopLevel;
import lavaProfile.runtime.FR;
import lavaProfile.runtime.env.*;
import lavaProfile.runtime.exceptions.UndefinedIdException;

public class TestEnv
{
    public static void testEnv ()
    {
	Test.dsop("begin: testEnv");
	testActivationFrame();
	testEnvironmentLexical();
	testEnvironmentTopLevel();
	Test.dsop("begin: testEnv");
    }

    public static void testActivationFrame ()
    {
	ActivationFrame frame2 = FR.newActivationFrame(3);
	ActivationFrame frame1 = frame2.extend(FR.newActivationFrame(2));
	ActivationFrame frame0 = frame1.extend(FR.newActivationFrame(1));

	frame0.set(0, 0, new Double(0.0));

	frame0.set(1, 0, new Double(1.0));
	frame1.set(0, 1, new Double(1.1));

	frame0.set(2, 0, new Double(2.0));
	frame1.set(1, 1, new Double(2.1));
	frame2.set(0, 2, new Double(2.2));

	Test.dsop("Frame2:");
	Test.dsop(frame2.toString());
	Test.dsop("Frame1:");
	Test.dsop(frame1.toString());
	Test.dsop("Frame0:");
	Test.dsop(frame0.toString());

	Test.check("af1", 
		   new Double(0.0),
		   frame0.get(0, 0));
	Test.check("af2", 
		   new Double(1.0),
		   frame0.get(1, 0));
	Test.check("af3", 
		   new Double(1.1),
		   frame0.get(1, 1));
	Test.check("af4", 
		   new Double(2.0),
		   frame0.get(2, 0));
	Test.check("af5", 
		   new Double(2.1),
		   frame0.get(2, 1));
	Test.check("af6", 
		   new Double(2.2),
		   frame0.get(2, 2));
    }

    public static void testEnvironmentLexical ()
    {
	Symbol ZeroZero= F.newSymbol("0.0");
	Symbol OneZero = F.newSymbol("1.0");
	Symbol OneOne  = F.newSymbol("1.1");
	Symbol TwoZero = F.newSymbol("2.0");
	Symbol TwoOne  = F.newSymbol("2.1");
	Symbol TwoTwo  = F.newSymbol("2.2");

	EnvironmentLexical env2 = 
	    FC.newEnvironmentLexical(List.list(TwoZero, TwoOne, TwoTwo));
	EnvironmentLexical env1 = env2.extend(List.list(OneZero, OneOne));
	EnvironmentLexical env0 = env1.extend(List.list(ZeroZero));

	Test.dsop("Env2:");
	Test.dsop(env2.toString());
	Test.dsop("Env1:");
	Test.dsop(env1.toString());
	Test.dsop("Env0:");
	Test.dsop(env0.toString());

	EnvironmentLexical.LocalVariable lv;

	lv = env0.determineIfLocalVariable(ZeroZero);
	Test.check("env1", 
		   new Integer(0),
		   new Integer(lv.getLevel()));
	Test.check("env1.1", 
		   new Integer(0),
		   new Integer(lv.getSlot()));

	lv = env0.determineIfLocalVariable(OneZero);
	Test.check("env2", 
		   new Integer(1),
		   new Integer(lv.getLevel()));
	Test.check("env2.1", 
		   new Integer(0),
		   new Integer(lv.getSlot()));
	lv = env0.determineIfLocalVariable(OneOne);
	Test.check("env3", 
		   new Integer(1),
		   new Integer(lv.getSlot()));

	lv = env0.determineIfLocalVariable(TwoOne);
	Test.check("env4", 
		   new Integer(2),
		   new Integer(lv.getLevel()));
	Test.check("env5", 
		   new Integer(1),
		   new Integer(lv.getSlot()));

	lv = env0.determineIfLocalVariable(F.newSymbol("foo"));
	Test.check("env6", 
		   null,
		   lv);
    }

    private static String undefined = "undefined".intern();

    public static void testEnvironmentTopLevel ()
    {
	Symbol one = F.newSymbol("one");
	Symbol two = F.newSymbol("two");
	Symbol three = F.newSymbol("three");
	Symbol undefinedSymbol = F.newSymbol(undefined);

	EnvironmentTopLevel env = FR.newEnvironmentTopLevel();
	Test.dsop(env.toString());

	Test.check("envtl1", undefined, get(env, one));
	Test.check("envtl2", new Integer(1), env.set(one, new Integer(1)));
	Test.dsop(env.toString());

	Test.check("envtl3", undefined, get(env, two));
	Test.check("envtl4", new Integer(2), env.set(two, new Integer(2)));
	Test.dsop(env.toString());

	Test.check("envtl5", undefined, get(env, three));
	Test.check("envtl6", new Integer(3), env.set(three, new Integer(3)));
	Test.dsop(env.toString());

	Test.check("envtl7", new Double(2.2), env.set(two, new Double(2.2)));
	Test.dsop(env.toString());

	Test.check("envtl8", undefined, get(env, undefinedSymbol));
	env.setUndefinedIdHandler(new UndefinedIdHandlerImpl() { // REVISIT factory
		public Object handle (EnvironmentTopLevel env, Symbol id) {
		    return new Double(-33); }
	    });
	Test.check("envtl9", new Double(-33), get(env, undefinedSymbol));
    }

    private static Object get (EnvironmentTopLevel env, Symbol symbol)
    {
	try {
	    return env.get(symbol);
	} catch (UndefinedIdException e) {
	    return undefined;
	} catch (LavaException e) {
	    return e;
	}
    }
}

// End of file.

