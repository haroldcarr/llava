/**
 * Created       : 1999 Dec 17 (Fri) 19:45:09 by Harold Carr.
 * Last Modified : 2000 Jan 15 (Sat) 22:23:33 by Harold Carr.
 */

package lava.lang.types;

public interface Pair
{
    /**
     * Constructor.
     */

    public Pair newPair (Object car, Object cdr);

    /**
     * Common alias for constructor.
     */

    public Pair cons (Object car, Object cdr);

    // Operations.

    public boolean equals (Object x);
    public int length ();

    public Object setCar (Object x);
    public Object setCdr (Object x);

    public Object car ();
    public Object cdr ();
    public Object caar ();
    public Object cadr ();
    public Object cdar ();
    public Object cddr ();
    public Object caddr ();
    public Object cdddr ();
    
    public Object rest ();
    public Object first ();
    public Object second ();
    public Object third ();
    public Object fourth ();
    public Object fifth ();
    public Object sixth ();
}

// End of file.

