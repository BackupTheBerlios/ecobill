package ecobill.core.system;

/**
 * Persistable.
 * <p/>
 * User: rro
 * Date: 29.09.2005
 * Time: 14:21:53
 *
 * @author Roman R&auml;dle
 * @version $Id: Persistable.java,v 1.1 2005/09/30 09:00:02 raedler Exp $
 * @since EcoBill 1.0
 */
public interface Persistable {
    public void persist();
    public void unpersist();
}