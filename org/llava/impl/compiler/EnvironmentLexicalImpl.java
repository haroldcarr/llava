/**
 * Created       : 1999 Dec 21 (Tue) 00:12:36 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:38:54 by Harold Carr.
 */

package lavaProfile.compiler;

import lava.lang.types.Pair;
import lava.lang.types.Symbol;

public class EnvironmentLexicalImpl 
    extends
	EnvironmentLexical
{
    private EnvironmentLexicalImpl next = null;
    private Pair            names;

    public EnvironmentLexicalImpl ()
    {
    }

    EnvironmentLexicalImpl (Pair names)
    {
	this.names = names;
    }

    public EnvironmentLexical newEnvironmentLexical (Pair names)
    {
	return new EnvironmentLexicalImpl(names);
    }

    public EnvironmentLexical extend (Pair names)
    {
	return extend(new EnvironmentLexicalImpl(names));
    }

    public EnvironmentLexical extend (EnvironmentLexical frame)
    {
	((EnvironmentLexicalImpl)frame).next = this;
	return frame;
    }

    public LocalVariable determineIfLocalVariable (Symbol name)
    {
	return determineIfLocalVariableAux(0, name);
    }

    private LocalVariable determineIfLocalVariableAux (int i, Symbol name)
    {
	return scan(i, 0, name, names);
    }

    private LocalVariable scan (int i, int j, Symbol name, Object names)
    {
	if (names instanceof Pair) {
	    if (((Pair)names).car().equals(name)) {
		return new LocalVariable(i, j);
	    } else {
		return scan(i, j + 1, name, ((Pair)names).cdr());
	    }
	} else if (names == null && next != null) {
	    return next.determineIfLocalVariableAux(i + 1, name);
	} else if (names != null && names.equals(name)) {
	    // dotted parameter.
	    return new LocalVariable(i, j);
	}
	return null;
    }

    public String toString () 
    {
	String result = "\nEnvironmentLexicalImpl:";
	result += "\n\t" + names.toString();
	result += next == null ? "null" : next.toString();
	return result;
    }
}

// End of file.

