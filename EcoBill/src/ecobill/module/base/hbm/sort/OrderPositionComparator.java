package ecobill.module.base.hbm.sort;

import java.util.Comparator;

/**
 * Mit dem <code>SystemLocaleComparator</code> ist es möglich ein <code>Set</code>, deren Objekte
 * das Interface <code>SystemLocaleComparable</code> implementieren, nach dem Key der
 * <code>SystemLocale</code> zu sortieren.
 * <p/>
 * User: rro
 * Date: 06.10.2005
 * Time: 10:45:45
 *
 * @author Roman R&auml;dle
 * @version $Id: OrderPositionComparator.java,v 1.2 2006/01/30 23:43:13 raedler Exp $
 * @since EcoBill 1.0
 */
public class OrderPositionComparator implements Comparator {

    /**
     * Hier werden zwei Objekte, die beide das Interface <code>OrderPositionComparable</code> implementieren
     * müssen, auf die Reihenfolge (Rangfolge) überprüft.
     *
     * @param o1 Ein Objekt das das Interface <code>OrderPositionComparable</code> implementiert.
     * @param o2 Ein Objekt das das Interface <code>OrderPositionComparable</code> implementiert.
     * @return Der int Wert der Reihenfolge der beiden Objekte.
     * @see Comparator#compare(Object, Object)
     */
    public int compare(Object o1, Object o2) {
        if (o1 instanceof OrderPositionComparable && o2 instanceof OrderPositionComparable) {
            Integer o1OrderPosition = ((OrderPositionComparable) o1).getOrderPosition();
            Integer o2OrderPosition = ((OrderPositionComparable) o2).getOrderPosition();

            return o1OrderPosition.compareTo(o2OrderPosition);
        }

        return 0;
    }
}

