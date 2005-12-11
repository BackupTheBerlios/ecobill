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
 * @version $Id: IdValueItem.java,v 1.3 2005/12/11 17:16:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class IdValueItem implements Comparable, Serializable {

    private Long id;

    private Object value;

    private Object originalValue;

    public IdValueItem(Long id, Object value) {
        this.id = id;
        this.value = value;
    }

    public IdValueItem(Long id, Object value, Object originalValue) {
        this.id = id;
        this.value = value;
        this.originalValue = originalValue;
    }

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

    public Object getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(Object originalValue) {
        this.originalValue = originalValue;
    }

    /**
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Object o) {
        if (this == o) return 0;

        return toString().compareTo(o.toString());
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        if (value == null) {
            return "";
        }

        return value.toString();
    }
}
