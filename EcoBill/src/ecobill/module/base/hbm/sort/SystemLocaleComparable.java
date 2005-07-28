package ecobill.module.base.hbm.sort;

import ecobill.module.base.domain.SystemLocale;

/**
 * Das Interface <code>SystemLocaleComparable</code> erlaubt es aus einem Objekt die, für dieses
 * zugewiesene, <code>SystemLocale</code> zu extrahieren und somit beispielsweise über den
 * <code>SystemLocaleComparator</code> und den <code>SystemLocale</code> Key ein <code>Set</code>
 * nach dem Key zu sortieren.
 * <p/>
 * User: rro
 * Date: 27.07.2005
 * Time: 11:09:16
 *
 * @author Roman R&auml;dle
 * @version $Id: SystemLocaleComparable.java,v 1.1 2005/07/28 21:03:52 raedler Exp $
 * @since EcoBill 1.0
 */
public interface SystemLocaleComparable {

    /**
     * Gibt die <code>SystemLocale</code> für ein bestimmetes Objekt zurück.
     *
     * @return Die <code>SystemLocale</code> für ein bestimmtes Objekt.
     */
    public SystemLocale getSystemLocale();

    /**
     * Setzt die <code>SystemLocale</code> für ein bestimmetes Objekt.
     *
     * @param systemLocale Die <code>SystemLocale</code> für ein bestimmtes Objekt.
     */
    public void setSystemLocale(SystemLocale systemLocale);
}
