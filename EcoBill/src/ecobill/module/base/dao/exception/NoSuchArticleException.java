package ecobill.module.base.dao.exception;

/**
 * Eine <code>NoSuchArticleException</code> kann geworfen werden, wenn bspw kein <code>Article</code>
 * unter einer gegebenen Artikelnummer gefunden wurde.
 * <p/>
 * User: rro
 * Date: 30.07.2005
 * Time: 11:41:41
 *
 * @author Roman R&auml;dle
 * @version $Id: NoSuchArticleException.java,v 1.1 2005/07/30 11:18:03 raedler Exp $
 * @since EcoBill 1.0
 */
public class NoSuchArticleException extends RuntimeException {

    /**
     * Zu dieser <code>NoSuchArticleException</code> muss eine Message übergeben
     * werden, die den aufgetretenen Fehler etwas genauer beschreibt.
     *
     * @see Exception#Exception(String)
     */
    public NoSuchArticleException(String message) {
        super(message);
    }
}
