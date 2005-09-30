package ecobill.core.system;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Persistable.
 * <p/>
 * User: rro
 * Date: 29.09.2005
 * Time: 14:21:53
 *
 * @author Roman R&auml;dle
 * @version $Id: Persistable.java,v 1.2 2005/09/30 14:41:16 raedler Exp $
 * @since EcoBill 1.0
 */
public interface Persistable {
    public void persist(OutputStream outputStream);
    public void unpersist(InputStream inputStream);
}