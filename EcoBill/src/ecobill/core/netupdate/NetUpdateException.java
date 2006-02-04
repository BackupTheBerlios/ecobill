package ecobill.core.netupdate;

// @todo document me!

/**
 * <code>NetUpdateException</code>.
 * <p/>
 * User: rro
 * Date: 03.02.2006
 * Time: 15:48:40
 *
 * @author Roman R&auml;dle
 * @version $Id: NetUpdateException.java,v 1.1 2006/02/04 00:46:52 raedler Exp $
 * @since EcoBill 1.1
 */
public class NetUpdateException extends RuntimeException {

    public NetUpdateException(String message) {
        super(message);
    }

    public NetUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}