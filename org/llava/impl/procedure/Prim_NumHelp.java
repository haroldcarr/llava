/*
Copyright (c) 1997 - 2004 Harold Carr

This work is licensed under the Creative Commons Attribution License.
To view a copy of this license, visit 
  http://creativecommons.org/licenses/by/2.0/
or send a letter to
  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
------------------------------------------------------------------------------
*/


/** A primitive is a procedure that is defined as part of the Scheme report,
 * and is implemented in Java code. 
 * @author Peter Norvig, Copyright 1998, http://www.norvig.com/license.html 
 * @author Modified by Timothy J. Hickey, http://www.cs.brandeis.edu/~tim
**/

package org.llava.impl.runtime.procedure.primitive.java;

import org.llava.impl.F;
import org.llava.lang.types.Pair;

public class Prim_NumHelp
{

    public static Object doOpBoolean (int op, Pair args)
    {
	return 
	    F.newBoolean(doOp(op, args.first(), (Pair)args.rest()) != FALSE);
    }

    public static Object doOp (int op, Object x, Pair args)
    {
	if (x instanceof Long)
	    return doOp(op, numL(x), args);
	if (x instanceof Integer)
	    return doOp(op, numI(x), args);
	return doOp(op, numD(x), args);
    }

    /** 
     * Compute x op arg1 op arg2 op ... argn.
     * Use a long as an accumulator of the intermediate result.
     * For compare ops, returns FALSE for false, and a number for true. 
     **/

    public static Object doOp (int op, long x, Pair args)
    {
	if (args == null) {
	    return num(x);
	}
	if (!(args.first() instanceof Integer)) {
	    return doOp(op, (double) x, args);
	}
	// x is the first number and the accumulator
	int y = numI(args.first());
	switch (op) {
	case GT:     if (!(x >  y)) return FALSE; else x = y; break;
	case LT:     if (!(x <  y)) return FALSE; else x = y; break;
	case EQ:     if (!(x == y)) return FALSE; else x = y; break;
	case LE:     if (!(x <= y)) return FALSE; else x = y; break;
	case GE:     if (!(x >= y)) return FALSE; else x = y; break;
	case MAX:    if (y > x) x = y; break;
	case MIN:    if (y < x) x = y; break;
	case BITAND: x &= y; break;
	case BITOR:  x |= y; break;
	case PLUS:   x += y; break;
	case MINUS:  x -= y; break;
	case TIMES:  x *= y; break;
	case DIVIDE:
	    if (x % y == 0) {
		x /= y; 
	    } else {
		return doOp(op, ((double)x)/y, (Pair)args.rest());
	    }
	    break;
	default:
	    throw F.newLlavaException("internal error: unrecognized op: " + op);
	}
	// If overflow ints, move to doubles
	return 
	    (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE)
	    ? doOp(op, (double)x, (Pair)args.rest())
	    : doOp(op, x,         (Pair)args.rest());
    }


    /** 
     * Same as above but use a double to accumulate intermediate results.
     **/

    public static Object doOp (int op, double x, Pair args)
    {
	if (args == null) {
	    return num(x);
	}
	// x is the first number and the accumulator
	double y = numD(args.first());
	switch (op) {
	case GT:     if (!(x >  y)) return FALSE; else x = y; break;
	case LT:     if (!(x <  y)) return FALSE; else x = y; break;
	case EQ:     if (!(x == y)) return FALSE; else x = y; break;
	case LE:     if (!(x <= y)) return FALSE; else x = y; break;
	case GE:     if (!(x >= y)) return FALSE; else x = y; break;
	case MAX:    if (y > x) x = y; break;
	case MIN:    if (y < x) x = y; break;
	case PLUS:   x += y; break;
	case MINUS:  x -= y; break;
	case TIMES:  x *= y; break;
	case DIVIDE: x /= y; break;
	default:     
	    throw F.newLlavaException("internal error: unrecognized op: " + op);
	}
	return doOp(op, x, (Pair)args.rest());
    }

    /** Convert int to Integer. Caches small ints so that we only ever
     * make one copy of new Integer(0), new Integer(1), etc. **/

    public static Integer num (int i)
    { 
	return F.newInteger(i);
    }

    /** Convert long to Number, either Integer or Double. **/

    public static Number num (long i)
    {
	if (i <= Integer.MAX_VALUE && i >= Integer.MIN_VALUE) {
	    return num((int)i);
	};
	return num((double)i);
    }

    /** Convert double to Double. Caches 0 and 1; makes new for others. **/

    public static Double num(double x) { 
	return F.newDouble(x);
    }

    public static double numD (Object x)
    { 
	if (x instanceof Number) {
	    return ((Number)x).doubleValue();
	}
	throw F.newLlavaException("expected a number, got: " +  x.toString());
    }

    public static int numI (Object x) 
    { 
	if (x instanceof Number) {
	    return ((Number)x).intValue();
	}
	throw F.newLlavaException("expected an number, got: " + x.toString());
    }

    public static long numL (Object x) 
    { 
	if (x instanceof Number) {
	    return ((Number)x).longValue();
	}
	throw F.newLlavaException("expected an number, got: " + x.toString());
    }

    static final int DIVIDE =  0;
    static final int EQ     =  1;
    static final int GE     =  2;
    static final int GT     =  3;
    static final int LE     =  4;
    static final int LT     =  5;
    static final int MAX    =  6;
    static final int MIN    =  7;
    static final int BITAND =  8;
    static final int BITOR  =  9;
    static final int MINUS  = 10;
    static final int PLUS   = 11;
    static final int TIMES  = 12;

    static final Boolean FALSE = F.newBoolean(false);
}

// End of file.
