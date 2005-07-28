package ecobill.util;

import ecobill.module.base.domain.SystemLocale;

/**
 * Jeder Klasse die dieses Interface <code>Localizable</code> implementiert ist es
 * möglich eine <code>SystemLocale</code> zu setzen und somit über die Util Klasse
 * <code>LocalizerUtils</code> ein landesspezifisches Objekt eines <code>Set</code>
 * zu erhalten.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 16:08:22
 *
 * @author Roman R&auml;dle
 * @version $Id: Localizable.java,v 1.1 2005/07/28 21:03:53 raedler Exp $
 * @since EcoBill 1.0
 */
public interface Localizable {

    /**
     * Gibt eine <code>SystemLocale</code> zurück und somit die Sprache, das Land
     * und die Variante der Sprache.
     *
     * @return Ein <code>SystemLocale</code> Objekt.
     */
    public SystemLocale getSystemLocale();

    /**
     * Setzt eine <code>SystemLocale</code>.
     *
     * @param systemLocale Ein <code>SystemLocale</code> Objekt.
     */
    public void setSystemLocale(SystemLocale systemLocale);
}
