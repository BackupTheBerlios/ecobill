package ecobill.module.base.hbm.sort;

import ecobill.module.base.domain.SystemLocale;

import java.util.Comparator;
import java.text.Collator;

/**
 * Mit dem <code>SystemLocaleComparator</code> ist es möglich ein <code>Set</code>, deren Objekte
 * das Interface <code>SystemLocaleComparable</code> implementieren, nach dem Key der
 * <code>SystemLocale</code> zu sortieren.
 * <p/>
 * User: rro
 * Date: 27.07.2005
 * Time: 11:05:11
 *
 * @author Roman R&auml;dle
 * @version $Id: SystemLocaleComparator.java,v 1.2 2006/01/30 23:43:13 raedler Exp $
 * @since EcoBill 1.0
 */
public class SystemLocaleComparator implements Comparator {

    /**
     * Der <code>Collator</code> wird verwendet um zwei gegebene <code>String</code> zu vergleichen
     * und somit die Reihenfolge festzulegen.
     */
    private Collator collator = Collator.getInstance();

    /**
     * Hier werden zwei Objekte, die beide das Interface <code>SystemLocaleComparable</code> implementieren
     * müssen, auf die Reihenfolge (Rangfolge) überprüft.
     *
     * @param o1 Ein Objekt das das Interface <code>SystemLocaleComparable</code> implementiert.
     * @param o2 Ein Objekt das das Interface <code>SystemLocaleComparable</code> implementiert.
     * @return Der int Wert der Reihenfolge der beiden Objekte.
     * @see Comparator#compare(Object, Object)
     */
    public int compare(Object o1, Object o2) {
        if (o1 instanceof SystemLocaleComparable && o2 instanceof SystemLocaleComparable) {
            SystemLocale o1SystemLocale = ((SystemLocaleComparable) o1).getSystemLocale();
            SystemLocale o2SystemLocale = ((SystemLocaleComparable) o2).getSystemLocale();

            return collator.compare(o1SystemLocale.getKey(), o2SystemLocale.getKey());
        }

        return 0;
    }
}
