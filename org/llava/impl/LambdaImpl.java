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
 * Created       : 1999 Dec 23 (Thu) 18:36:42 by Harold Carr.
 * Last Modified : 2004 Dec 01 (Wed) 21:58:34 by Harold Carr.
 */

package org.llava.impl.runtime.procedure;

import org.llava.impl.F;
import org.llava.lang.types.Pair;
import org.llava.impl.util.List;
import org.llava.runtime.EnvironmentTopLevel;
import org.llava.impl.runtime.code.Code;
import org.llava.impl.runtime.env.ActivationFrame;
import org.llava.impl.runtime.env.Namespace;
import org.llava.impl.runtime.Engine;

public class LambdaImpl
    implements
	Lambda
{
    private String          name;
    private int             numRequired;
    private boolean         isDotted;
    private Code            code;
    private ActivationFrame savedFrame;
    private Namespace       namespace;

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
	// If the frame is associated with a namespace, use it.
	// This takes care of LET being expanded inside during runtime.
	// The resulting LAMBDAs need to be created in the saved namespace,
	// not the current runtime namespace (which could be
	// a wrong package).
	this.namespace = savedFrame.getNamespace();
	if (namespace == null) {
	    // This is the usual case of creating lambdas during load.
	    EnvironmentTopLevel env = savedFrame.getEnvironmentTopLevel();
	    this.namespace = 
		env instanceof Namespace 
		? ((Namespace)env).getCurrentNamespace()
		: null;
	}
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
	    throw F.newLlavaException(makeErrorMessage("not enough arguments"));
	}
	if (numArgs > numRequired && !isDotted) {
	    throw F.newLlavaException(makeErrorMessage("too many arguments"));
	}
	if (numArgs == numRequired && isDotted) {
	    args = List.nconc(args, List.list(null));
	} else if (isDotted) {
	    args = collectDottedArgs(numRequired, args);
	}
	return engine.tailCall(code, savedFrame.extend(args, namespace));
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
	// REVISIT: duplicated in GenericProcedureImpl, LambdaImpl, Syntax
	String result = 
	    "{lambda " 
	    + ((namespace != null) ? namespace.getName() + "." : "")
	    + name 
	    + "}";
	return result;
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
