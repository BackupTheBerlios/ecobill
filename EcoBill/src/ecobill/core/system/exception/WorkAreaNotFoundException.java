package ecobill.core.system.exception;

// @todo document me!

/**
 * Diese <code>WorkAreaNotFoundException</code> wird von der {@link WorkArea}
 * geworfen wenn diese nicht an den aktuellen <code>Thread</code> gebunden
 * ist.
 * <p/>
 * User: rro
 * Date: 04.08.2005
 * Time: 20:16:18
 *
 * @author Roman R&auml;dle
 * @version $Id: WorkAreaNotFoundException.java,v 1.1 2005/08/04 20:24:18 raedler Exp $
 * @since EcoBill 1.0
 */
public class WorkAreaNotFoundException extends RuntimeException {

    public WorkAreaNotFoundException(String message) {
        super(message);
    }
}