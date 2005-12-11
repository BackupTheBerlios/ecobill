package ecobill.core.util;

import ecobill.core.system.WorkArea;

import java.io.Serializable;

// @todo document me!

/**
 * IdKeyItem.
 * <p/>
 * User: rro
 * Date: 29.09.2005
 * Time: 20:33:49
 *
 * @author Roman R&auml;dle
 * @version $Id: IdKeyItem.java,v 1.2 2005/12/11 17:16:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class IdKeyItem implements Comparable, Serializable {

    private Long id;

    private String key;

    public IdKeyItem() {

    }

    public IdKeyItem(Long id, String key) {
        this.id = id;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Object o) {
        if (this == o) return 0;

        return toString().compareTo(o.toString());
    }

    public String toString() {
        return WorkArea.getMessage(getKey());
    }
}
