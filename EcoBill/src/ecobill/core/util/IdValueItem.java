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
 * @version $Id: IdValueItem.java,v 1.2 2005/10/11 19:41:26 gath Exp $
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

    public IdValueItem(Long id, Object value)
    {
        this.id = id;
        this.value = value;
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        return value.toString();
    }
}
