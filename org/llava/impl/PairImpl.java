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
 * Created       : 1999 Dec 17 (Fri) 19:45:09 by Harold Carr.
 * Last Modified : 2004 Sep 03 (Fri) 15:35:22 by Harold Carr.
 */

package org.llava.lang.types;

import org.llava.lang.types.Pair;

public class PairImpl
    implements
	Pair
{
    private Object car;
    private Object cdr;

    public PairImpl ()
    {
	this.car = this.cdr = null;
    }
    
    public PairImpl (Object car, Object cdr)
    {
	this.car = car;
	this.cdr = cdr;
    }

    public Pair newPair (Object car, Object cdr)
    {
	return new PairImpl(car, cdr);
    }

    public Pair cons (Object car, Object cdr)
    {
	return new PairImpl(car, cdr);
    }

    public boolean equals (Object x) 
    {
	if (x == this) {
	    return true;
	} else if (!(x instanceof PairImpl)) {
	    return false;
	} else {
	    PairImpl that = (PairImpl)x;
	    return
		equalAux(this.car, that.car) &&
		equalAux(this.cdr, that.cdr);
	}
    }

    private static boolean equalAux (Object x, Object y)
    {
	if (x == null || y == null) {
	    return x == y;
	} else {
	    return x.equals(y);
	}
    }

    public int length ()
    {
	// REVISIT: detect circular.
	int i;
	Pair rest;
	for (i = 0, rest = this;
	     rest != null;
	     ++i, rest = (Pair)rest.cdr()) {
	}
	return i;
    }

    public Object setCar (Object x)
    {
	return this.car = x;
    }

    public Object setCdr (Object x)
    {
	return this.cdr = x;
    }

    public String toString ()
    {
	return "(" + toStringList() + ")";
    }

    private String toStringList ()
    {
	boolean isCdrPair = cdr instanceof PairImpl;
	boolean isCdrNull = cdr == null;
	
	String carString = car == null ? "null" : car.toString();
	String maybeDot = isCdrPair ? " " : isCdrNull ? "" : " . ";
	String cdrString = isCdrPair ? ((PairImpl)cdr).toStringList() :
	                               isCdrNull ? "" :
	                                           cdr.toString();
	return carString + maybeDot + cdrString;
    }

    private PairImpl pcar () { return (PairImpl) car; }
    private PairImpl pcdr () { return (PairImpl) cdr; }

    public Object car ()  { return car; }
    public Object cdr ()  { return cdr; }
    public Object caar () { return pcar().car; }
    public Object cadr () { return pcdr().car; }
    public Object cdar () { return pcar().cdr; }
    public Object cddr () { return pcdr().cdr; }
    public Object caddr () { return pcdr().pcdr().car; }
    public Object cdddr () { return pcdr().pcdr().cdr; }

    public Object rest   () { return cdr; }
    public Object first  () { return car; }
    public Object second () { return cadr(); }
    public Object third  () { return caddr(); }
    public Object fourth () { return pcdr().pcdr().pcdr().car; }
    public Object fifth  () { return pcdr().pcdr().pcdr().pcdr().car; }
    public Object sixth  () { return pcdr().pcdr().pcdr().pcdr().pcdr().car; }
}

// End of file.

