/**
 * Created       : 2000 Jan 10 (Mon) 02:06:49 by Harold Carr.
 * Last Modified : 2001 Mar 26 (Mon) 14:44:46 by Harold Carr.
 */

package lavaProfile.runtime.procedure.generic;

public interface WrapJavaPrimitive
{
    public WrapJavaPrimitive newWrapJavaPrimitive ();

    public Object wrapJavaPrimitive (Object x);
}
