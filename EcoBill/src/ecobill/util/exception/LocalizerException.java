package ecobill.util.exception;

/**
 * LocalizerException.
 * <p/>
 * User: rro
 * Date: 26.07.2005
 * Time: 14:49:35
 *
 * @author Roman R&auml;dle
 * @version $Id: LocalizerException.java,v 1.1 2005/07/28 21:03:54 raedler Exp $
 * @since EcoBill 1.0
 */
public class LocalizerException extends Exception {

    /**
     * Erzeugt eine neue <code>LocalizerException</code> mit einer Message.
     *
     * @param message Die Message die dieser Exception zugeordnet ist.
     */
    public LocalizerException(String message) {
        super(message);
    }
}
