/**
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2000 Feb 11 (Fri) 06:16:09 by Harold Carr.
 */

package libLava.r1.procedure;

import lava.F;
import lava.lang.types.Pair;
import lava.util.List;
import libLava.r1.code.Code;
import libLava.r1.env.ActivationFrame;
import libLava.r1.Engine;

public class LambdaImpl
    implements
	Lambda
{
    private String          name;
    private int             numRequired;
    private boolean         isDotted;
    private Code            code;
    private ActivationFrame savedFrame;

    public LambdaImpl ()
    {
    }

    private LambdaImpl (int numRequired, 
			boolean isDotted,
			Code code, 
			ActivationFrame savedFrame)
    {
	this.name = "";
	this.numRequired = numRequired;
	this.isDotted = isDotted;
	this.code = code;
	this.savedFrame = savedFrame;
    }

    public Lambda newLambda (int numRequired,
			     boolean isDotted,
			     Code code, 
			     ActivationFrame savedFrame)
    {
	return new LambdaImpl(numRequired, isDotted, code, savedFrame);
    }

    public Object apply (Pair args, Engine engine)
    {
	int numArgs = List.length(args);
	if (numArgs < numRequired) {
	    throw F.newLavaException(makeErrorMessage("not enough arguments"));
	}
	if (numArgs > numRequired && !isDotted) {
	    throw F.newLavaException(makeErrorMessage("too many arguments"));
	}
	if (numArgs == numRequired && isDotted) {
	    args = List.nconc(args, List.list(null));
	} else if (isDotted) {
	    args = collectDottedArgs(numRequired, args);
	}
	return engine.tailCall(code, savedFrame.extend(args));
    }

    public String getName ()
    {
	return name;
    }

    public String setName (String name)
    {
	return this.name = name;
    }

    public int             getNumRequired () { return numRequired; }
    public boolean         getIsDotted    () { return isDotted; }
    public Code            getCode        () { return code; }
    public ActivationFrame getSavedFrame  () { return savedFrame; }

    public String toString ()
    {
	// REVISIT - duplicate of code in Syntax.
	return "{" + getClass().getName() + " " + name + "}";
    }

    private Pair collectDottedArgs (int numRequired, Pair args) 
    {
	Pair required = null;
	Pair p;
	int i;
	for (p = args, i = 0; i < numRequired; ++i, p = (Pair)p.cdr()) {
	    required = F.cons(p.car(), required);
	}
	return List.nconc(List.reverse(required),
			  List.list(p));
    }

    private String makeErrorMessage(String specific)
    {
	String generic = 
	    name == "" ? "Anonymous lambda with body: " + code.toString()
	               : name;
	return generic + ": " + specific;
    }
}

// End of file.
