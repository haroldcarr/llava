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

import org.llava.F;
import org.llava.Pair;
import org.llava.Symbol;
import org.llava.io.LlavaWriter;

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
    private int vectorPrintLength = Integer.MAX_VALUE;

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
	return write(x, out);
    }

    public Object write (Object x, PrintWriter out)
    {
	if (x == null) {
	    emit(null, out);
	} else if (x instanceof java.lang.String) {
	    emit( "\"", out);
	    emit(x, out);
	    emit("\"", out);
	} else if (x instanceof Pair) {
	    writePair((Pair)x, out);
	} else if (x instanceof java.lang.Character) {
	    emit("#\\", out);
	    emit(x, out);
	} else if (x.getClass().isArray()) {
	    emit("#", out);
	    write(List.vector2list(x, vectorPrintLength), out);
	} else {
	    emit(x, out);
	}
	return x;
    }

    public PrintWriter getPrintWriter ()
    {
	return out;
    }

    public Object setVectorPrintLength (boolean x)
    {
	return setVectorPrintLength( (x ? Integer.MAX_VALUE : 0) );
    }

    public Object setVectorPrintLength (int x)
    {
	vectorPrintLength = x;
	return x;
    }


    //////////////////////////////////////////////////
    //
    // Implementation
    //

    private Object writePair(Pair pair, PrintWriter out) 
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
	    emit(uq, out);
	    write(pair.cadr(), out);
	} else {
	    emit("(", out);
	    write(pair.car(), out);
	    writeList(pair.cdr(), out);
	}
	return pair;
    }

    private void writeList(Object list, PrintWriter out)
    {
	if (list == null) {
	    emit(")", out);
	} else if (! (list instanceof Pair)) {
	    emit(" . ", out);
	    write(list, out);
	    emit(")", out);
	} else {
	    emit(" ", out);
	    write(((Pair)list).car(), out);
	    writeList(((Pair)list).cdr(), out);
	}
    }

    private void emit(Object x, PrintWriter out)
    {
	out.print(x);
    }
}

// End of file.

