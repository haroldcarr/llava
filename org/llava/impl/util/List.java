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
 * Created       : 1999 Dec 22 (Wed) 06:15:52 by Harold Carr.
 * Last Modified : 2004 Dec 08 (Wed) 09:01:11 by Harold Carr.
 */

package org.llava.impl.util;

import org.llava.impl.F;
import org.llava.lang.types.Pair;

public class List
{
    /**
     *
     */

    public static Pair append (Pair l1, Pair l2) {
	// REVISIT - quick and dirty
	return nconc(reverse(reverse(l1)), l2);
    }

    /**
     *
     */

    public static int length (Pair x) {
	if (x == null) {
	    return 0;
	}
	return x.length();
    }

    /**
     *
     */

    public static Pair list(Object a) {
	return F.cons(a, null);
    }
    public static Pair list(Object a, Object b) {
	return F.cons(a, list(b));
    }
    public static Pair list(Object a, Object b, Object c) {
	return F.cons(a, list(b, c));
    }
    public static Pair list(Object a, Object b, Object c, Object d) {
	return F.cons(a, list(b, c, d));
    }
    public static Pair list(Object a, Object b, Object c, Object d, Object e) {
	return F.cons(a, list(b, c, d, e));
    }

    public static Pair list(Object a, Object b, Object c, Object d, Object e, Object f) {
	return F.cons(a, list(b, c, d, e, f));
    }

    /**
     *
     */

    public static Pair nconc (Pair l1, Pair l2) {
	if (l1 == null) {
	    return l2;
	}
	Pair p;
	for (p = l1; p.cdr() != null; p = (Pair)p.cdr()) {
	    ;
	}
	p.setCdr(l2);
	return l1;
    }

    /**
     *
     */

    public static Pair reverse (Pair list) {
	Pair result = null;
	for (Pair p = list; p != null; p = (Pair)p.cdr()) {
	    result = F.cons(p.car(), result);
	}
	return result;
    }

    /**
     *
     */

    public static Object[] toArray(Pair p) {
	Object[] array = new Object[p == null ? 0 : p.length()];
	int i;
	Pair pptr;
	for (i = 0, pptr = p;
	     pptr != null;
	     ++i, pptr = (Pair)pptr.cdr()) {
	    array[i] = pptr.car();
	}
	return array;
    }

    public static Pair vector2list(Object v)
    {
	Pair result = List.list("dummy");
	Pair rptr   = result;
	int len     = java.lang.reflect.Array.getLength(v);
	for (int i = 0; i < len; i++) {
	    rptr.setCdr(List.list(java.lang.reflect.Array.get(v, i)));
	    rptr = (Pair) rptr.cdr();	    
	}
	return (Pair) result.cdr();
    }
}

// End of file.

