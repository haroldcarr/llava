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
 * Created       : 1999 Dec 17 (Fri) 20:11:43 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 08:59:54 by Harold Carr.
 */

package org.llava.impl.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.llava.impl.F;
import org.llava.io.LlavaWriter;
import org.llava.lang.types.Pair;
import org.llava.lang.types.Symbol;
import org.llava.impl.util.List;

public class LlavaWriterImpl
    implements
	LlavaWriter
{
    private Symbol QUOTE            = F.newSymbol("quote");
    private Symbol QUASIQUOTE       = F.newSymbol("quasiquote");
    private Symbol UNQUOTE          = F.newSymbol("unquote");
    private Symbol UNQUOTE_SPLICING = F.newSymbol("unquote-splicing");

    private PrintWriter out;

    public LlavaWriterImpl ()
    {
    }

    LlavaWriterImpl (PrintWriter out)
    {
	this.out = out;
    }

    public LlavaWriter newLlavaWriter ()
    {
	return new LlavaWriterImpl();
    }

    public LlavaWriter newLlavaWriter (PrintWriter out)
    {
	return new LlavaWriterImpl(out);
    }

    public Object write (Object x)
    {
	if (x == null) {
	    emit(null);
	} else if (x instanceof java.lang.String) {
	    emit( "\"");
	    emit(x);
	    emit("\"");
	} else if (x instanceof Pair) {
	    writePair((Pair)x);
	} else if (x instanceof java.lang.Character) {
	    emit("#\\");
	    emit(x);
	} else if (x.getClass().isArray()) {
	    emit("#");
	    write(List.vector2list(x));
	} else {
	    emit(x);
	}
	return x;
    }

    public PrintWriter getPrintWriter ()
    {
	return out;
    }

    private Object writePair(Pair pair) 
    {
	String uq = null;

	if (pair.cdr() != null) {
	    Object x = pair.car();
	    if      (x == QUOTE)            uq = "'";
	    else if (x == QUASIQUOTE)       uq = "`";
	    else if (x == UNQUOTE)          uq = ",";
	    else if (x == UNQUOTE_SPLICING) uq = ",@";
	}

	if (uq != null) {
	    emit(uq);
	    write(pair.cadr());
	} else {
	    emit("(");
	    write(pair.car());
	    writeList(pair.cdr());
	}
	return pair;
    }

    private void writeList(Object list)
    {
	if (list == null) {
	    emit(")");
	} else if (! (list instanceof Pair)) {
	    emit(" . ");
	    write(list);
	    emit(")");
	} else {
	    emit(" ");
	    write(((Pair)list).car());
	    writeList(((Pair)list).cdr());
	}
    }

    private void emit(Object x)
    {
	out.print(x);
    }
}

// End of file.

