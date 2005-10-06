package ecobill.module.base.hbm.sort;

// @todo document me!

/**
 * Das Interface <code>OrderPositionComparable</code> erlaubt es aus einem Objekt die, für dieses
 * zugewiesene, Order Position zu extrahieren und somit beispielsweise über den
 * <code>OrderPositionComparator</code> ein <code>Set</code> zu sortieren.
 * <p/>
 * User: rro
 * Date: 06.10.2005
 * Time: 10:43:04
 *
 * @author Roman R&auml;dle
 * @version $Id: OrderPositionComparable.java,v 1.1 2005/10/06 14:09:13 raedler Exp $
 * @since EcoBill 1.0
 */
public interface OrderPositionComparable {
    public Integer getOrderPosition();
    public void setOrderPosition(Integer orderPosition);
}
