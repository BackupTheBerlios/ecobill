package ecobill.core.util;

import java.io.Serializable;

// @todo document me!

/**
 * IdValueItem.
 * <p/>
 * User: rro
 * Date: 07.10.2005
 * Time: 11:31:56
 *
 * @author Roman R&auml;dle
 * @version $Id: IdValueItem.java,v 1.1 2005/10/07 09:59:54 raedler Exp $
 * @since EcoBill 1.0
 */
public class IdValueItem implements Serializable {

    private Long id;

    private Object value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        return value.toString();
    }
}
