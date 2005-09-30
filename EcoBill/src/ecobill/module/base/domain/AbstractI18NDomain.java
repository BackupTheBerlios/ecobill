package ecobill.module.base.domain;

import ecobill.core.system.WorkArea;

/**
 * Die <code>SystemUnit</code> kann �ber einen landesunabh�ngigen Schl�ssel die
 * jeweilige landesspezifische Einheit �ber die <code>WorkArea</code> herausholen.
 * <p/>
 * User: rro
 * Date: 31.07.2005
 * Time: 17:19:20
 *
 * @author Roman R&auml;dle
 * @version $Id: AbstractI18NDomain.java,v 1.2 2005/09/30 09:02:23 raedler Exp $
 * @since EcoBill 1.0
 */
public abstract class AbstractI18NDomain extends AbstractDomain {

    /**
     * Der i18n Schl�ssel unter dem in einem <code>ResourceBundle</code> der
     * zugeh�rige landesspezifische Wert gefunden werden kann.
     */
    private String key;

    /**
     * Gibt den i18n Schl�ssel, unter dem in einem <code>ResourceBundle</code> der
     * zugeh�rige landesspezifische Wert gefunden werden kann, zur�ck.
     *
     * @return Der i18n Schl�ssel unter dem in einem <code>ResourceBundle</code> der
     * zugeh�rige landesspezifische Wert gefunden werden kann.
     */
    public String getKey() {
        return key;
    }

    /**
     * Setzt den i18n Schl�ssel, unter dem in einem <code>ResourceBundle</code> der
     * zugeh�rige landesspezifische Wert gefunden werden soll.
     *
     * @param key Der i18n Schl�ssel unter dem in einem <code>ResourceBundle</code> der
     * zugeh�rige landesspezifische Wert gefunden werden kann.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Es wird dieser <code>SystemUnit</code> mit dem eingehenden Objekt auf Gleichheit
     * �berpr�ft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AbstractI18NDomain that = (AbstractI18NDomain) o;

        return !(key != null ? !key.equals(that.key) : that.key != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        return (key != null ? key.hashCode() : 0);
    }

    /**
     * @see Object#toString()
     * @see WorkArea#getMessage(String)
     */
    @Override
    public String toString() {
        return WorkArea.getMessage(key);
    }
}