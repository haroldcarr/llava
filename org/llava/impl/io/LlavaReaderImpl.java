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
 * Last Modified : 2004 Dec 10 (Fri) 21:13:19 by Harold Carr.
 */

package org.llava.impl.io;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.llava.F;
import org.llava.Pair;
import org.llava.Symbol;
import org.llava.io.LlavaEOF;
import org.llava.io.LlavaReader;

import org.llava.impl.util.List;

public class LlavaReaderImpl
    implements
	LlavaReader
{
    private Reader   in;
    private LlavaEOF EOF      = F.newLlavaEOF();
    private Object   CONS_DOT = new Object();
    private Pair     STRING   = F.newPair(null, null);

    // REVISIT: maybe do not allocate until needed.
    private StringBuffer tmpBuff = new StringBuffer();

    public LlavaReaderImpl ()
    {
    }

    LlavaReaderImpl (Reader in)
    {
	this.in = in;
    }

    public LlavaReader newLlavaReader ()
    {
	return new LlavaReaderImpl();
    }

    public LlavaReader newLlavaReader (Reader in)
    {
	return new LlavaReaderImpl(in);
    }

    public Object read ()
    {
	return read(in);
    }

    public Object read (String in)
    {
	// REVISIT: hold on to StringReader in case called again.
	return read(new StringReader(in));
    }

    public Object read (Reader in) 
    {
	Object token = readToken(in); 

	if      (token == "(")      return readTail(in, false);
	else if (token == "'")      return List.list(F.newSymbol("quote"),
						     read(in));
	else if (token == "`")      return List.list(F.newSymbol("quasiquote"),
						     read(in));
	else if (token == ",")      return List.list(F.newSymbol("unquote"),
						     read(in));
	else if (token == ",@")     return List.list(F.newSymbol("unquote-splicing"),
						read(in));
	else if (token == ")")      throw F.newLlavaException("Extra ')'");
	else if (token == STRING)   return STRING.car();
	else if (token == CONS_DOT) throw F.newLlavaException("Extra '.'");
	else                        return token;
    }

    public LlavaEOF getEOFObject ()
    {
	return EOF;
    }

    //////////////////////////////////////////////////////////////////////////
    //
    // Implementation.
    //

    private Object readTail (Reader in, boolean dotOK) 
    {
	Object token = readToken(in); 
	if (token == ")") {
	    return null;
	} else if (token == CONS_DOT && dotOK) {
	    Object result = read(in);
	    token = readToken(in); 
	    if (token != ")") {
		throw F.newLlavaException("Expecting ')' but got: "+ token + " after .");
	    }
	    return result;
	} else if (token == CONS_DOT) {
	    throw F.newLlavaException("Got '.' immediately after '('");
	} else if (token == EOF) {
	    throw F.newLlavaException("EOF during read.");
	} else {
	    pushToken(token);
	    return F.cons(read(in), readTail(in, true));
	}
    }

    private Object readToken (Reader in) 
    {
      try {
	int ch;
	Object token = null;

	// See if we should re-use a pushed char or token
	if (isPushedToken()) {
	    return popToken();
	} else if (isPushedChar()) {
	    ch = popChar();
	} else {
	    ch = in.read();
	}

	// while (Character.isWhitespace((char)ch)) ch = in.read();
	// Treat anything less than #\space as whitespace.

	while (ch <= 32 && ch != -1) {
	    ch = in.read();
	}

	switch(ch) {
	case -1 :  return EOF;
	case '(' : return "(";
	case ')':  return ")";
	case '\'': return "'";
	case '`': return "`";
	case ',': 
	    ch = in.read();
	    if (ch == '@') {
		return ",@";
	    }
	    pushChar(ch);
	    return ",";
	case ';': 
	    // Comment: skip to end of line and then read next token
	    while(ch != -1 && ch != '\n' && ch != '\r') ch = in.read();
	    return readToken(in);
	case '"':
	    // String
	    tmpBuff.setLength(0);
	    while ((ch = in.read()) != '"' && ch != -1) {
		tmpBuff.append((char) ((ch == '\\') ? in.read() : ch));
	    }
	    if (ch == -1) {
		throw F.newLlavaException("EOF inside of a string.");
	    }
	    STRING.setCar(tmpBuff.toString().intern());
	    return STRING;
	case '#' :
	    switch (ch = in.read()) {
	    case 't' :
	    case 'T' : 
		return F.newBoolean(true);
	    case 'f' :
	    case 'F' :
		return F.newBoolean(false);
	    case '(' :
		pushChar('(');
		return List.toArray((Pair)read(in));
	    case '\\': 
		ch = in.read();
		if (ch == 's' || ch == 'S' ||
		    ch == 'n' || ch == 'N')
                {
		    pushChar(ch);
		    token = readToken(in);
		    if (token instanceof Symbol) {
			String lcToken = 
			    ((Symbol)token).toString().toLowerCase();
			if (lcToken.equals("space")) {
			    return F.newCharacter(' ');
			} else if (lcToken.equals("newline")) {
			    return F.newCharacter('\n');
			}
		    } else {
			pushToken(token);
			return F.newCharacter((char)ch);
		    }
		} else {
		    return F.newCharacter((char)ch);
		}
	    // REVISIT: 'e' 'i' 'd' 'b' 'o' 'x' goes here
	    default :
		F.newLlavaException("#" + ((char)ch) + " not recognized");
	    }
		
	default: 
	    // A symbol or a number.
	    tmpBuff.setLength(0);
	    int c = ch;
	    do { // accumulate until next delimiter
		tmpBuff.append((char)ch);
		ch = in.read();
	    } while (!isDelimiter(ch));
	    pushChar(ch);
	    String tok = tmpBuff.toString();
	    if ((c == '.' && tmpBuff.length() > 1) ||
		c == '+' || 
		c == '-' || 
		(c >= '0' && c <= '9')) {
		try {
		    return F.newInteger(Integer.parseInt(tok, 10 /*radix*/));
		} catch (NumberFormatException e) {
		    try { 
			return F.newDouble(tok); 
		    } catch (NumberFormatException e2) { 
			;
		    }
		}
	    } else if (c == '.') {
		// Do not confuse with string ".";
		return CONS_DOT;
	    }
	    if (tok.equals("null")) {
		return null;
	    } else if (tok.equals("true")) {
		return F.newBoolean(true);
	    } else if (tok.equals("false")) {
		return F.newBoolean(false);
	    } else {
		return F.newSymbol(tok);
	    }
	}
      } catch (IOException e) {
	  throw F.newLlavaException(e);
      }
    }

    private boolean isDelimiter (int ch) 
    {
	switch (ch) {
	case -1: 
	case '(': 
	case ')': 
	case '\'': 
	case ';': 
	case ',': 
	case '"': 
	case '`': 
	case ' ': 
	    return true;
	default:
	    return ch <= 32;
	}
    }

    // REVISIT: move into common impl class

    private boolean isPushedChar = false;
    private int     pushedChar = -1;

    private boolean isPushedToken = false;
    private Object  pushedToken = null;

    private boolean isPushedChar ()
    {
	return isPushedChar;
    }

    private int pushChar (int ch) 
    {
	isPushedChar = true;
	return pushedChar = ch;
    }

    private int popChar () 
    {
	isPushedChar = false;
	return pushedChar;
    }

    private boolean isPushedToken ()
    {
	return isPushedToken;
    }

    private Object pushToken (Object x) 
    {
	isPushedToken = true;
	return pushedToken = x;
    }

    private Object popToken () 
    {
	isPushedToken = false;
	return pushedToken;
    }
}

// End of file.

