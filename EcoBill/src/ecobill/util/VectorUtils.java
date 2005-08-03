package ecobill.util;

import java.util.Vector;
import java.util.List;

// @todo document me!

/**
 * VectorUtils.
 * <p/>
 * User: rro
 * Date: 31.07.2005
 * Time: 18:20:27
 *
 * @author Roman R&auml;dle
 * @version $Id: VectorUtils.java,v 1.1 2005/08/03 13:06:09 raedler Exp $
 * @since EcoBill 1.0
 */
public class VectorUtils {

    public static Vector<Object> listToVector(List list) {

        Vector<Object> vector = new Vector<Object>();

        for (Object o : list) {
            vector.add(o);
        }

        return vector;
    }
}
