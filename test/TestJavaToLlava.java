/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/

/**
 * Created       : 2005 Feb 07 (Mon) 22:14:05 by Harold Carr.
 * Last Modified : 2005 Feb 07 (Mon) 23:22:13 by Harold Carr.
 */

package test;

import org.llava.F;
import org.llava.Llava;
import org.llava.Repl;
import org.llava.Symbol;
import org.llava.impl.util.List;

public class TestJavaToLlava
{
    private Llava llava;
    private Repl  repl;

    public static void main (String[] av)
    {
	Llava llava = new Llava();
	Repl  repl  = llava.getRepl();
	repl.getLlavaWriter().getPrintWriter().println();
	Object result = repl.printResult(repl.readCompileEval("(list 1 2 3)"));
	repl.getLlavaWriter().getPrintWriter().println();
	repl.printResult(repl.eval(repl.compile(
           List.list(F.newSymbol("cadr"),
		     List.list(F.newSymbol("quote"), result)))));
	repl.getLlavaWriter().getPrintWriter().flush();

	TestJavaToLlava lva = new TestJavaToLlava(llava);

	lva.writeln(lva.call("cadr", lva.quote(result)));
    }

    public TestJavaToLlava(Llava llava)
    {
	this.llava = llava;
	this.repl = llava.getRepl();
    }

    public Object quote (Object o)
    {
	return List.list(F.newSymbol("quote"), o);
    }

    public Object q (Object o)
    {
	return quote(o);
    }

    public Symbol symbol (String s)
    {
	return F.newSymbol(s);
    }

    public Symbol sym (String s)
    {
	return symbol(s);
    }

    public Object write (Object o)
    {
	repl.getLlavaWriter().write(o);
	repl.getLlavaWriter().getPrintWriter().flush();
	return o;
    }

    public Object writeln (Object o)
    {
	write(o);
	repl.getLlavaWriter().getPrintWriter().flush();
	return o;
    }

    public Object call (String procedure)
    {
	return call(sym(procedure));
    }

    public Object call (String procedure, Object o1)
    {
	return call(sym(procedure), o1);
    }

    public Object call (String procedure, Object o1, Object o2)
    {
	return call(sym(procedure), o1, o2);
    }

    public Object call (Symbol procedure)
    {
	return compileEval(
	    List.list(procedure));
    }

    public Object call (Symbol procedure, Object o1)
    {
	return compileEval(
	    List.list(procedure, o1));
    }

    public Object call (Symbol procedure, Object o1, Object o2)
    {
	return compileEval(
            List.list(o1, o2));
    }

    //////////////////////////////////////////////////
    //
    // Implementation
    //

    private Object compileEval (Object o)
    {
	return repl.eval(repl.compile(o));
    }
}

// End of file.
