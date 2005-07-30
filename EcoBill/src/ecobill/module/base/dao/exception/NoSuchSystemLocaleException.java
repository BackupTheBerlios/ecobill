package ecobill.module.base.dao.exception;

/**
 * Diese <code>NoSuchSystemLocaleException</code> kann geworfen werden, wenn bspw keine
 * <code>SystemLocale</code> zu einem systemLocaleKey gefunden wurde.
 * <p/>
 * User: rro
 * Date: 30.07.2005
 * Time: 11:37:33
 *
 * @author Roman R&auml;dle
 * @version $Id: NoSuchSystemLocaleException.java,v 1.1 2005/07/30 11:18:03 raedler Exp $
 * @since EcoBill 1.0
 */
public class NoSuchSystemLocaleException extends RuntimeException {

    /**
     * Zu dieser <code>NoSuchSystemLocaleException</code> muss eine Message übergeben
     * werden, die den aufgetretenen Fehler etwas genauer beschreibt.
     *
     * @see Exception#Exception(String)
     */
    public NoSuchSystemLocaleException(String message) {
        super(message);
    }
}
