package ecobill.module.base.domain;

import ecobill.module.base.domain.enumeration.CharacterisationType;

import java.util.Date;
import java.util.Set;
import java.text.Collator;
// @todo document me!

/**
 * Bill.
 * <p/>
 * User: rro
 * Date: 21.07.2005
 * Time: 13:29:28
 *
 * @author Roman R&auml;dle
 * @version $Id: Bill.java,v 1.2 2005/09/28 15:44:18 raedler Exp $
 * @since DAPS INTRA 1.0
 */
public class Bill extends AbstractDomain {

    /**
     * Der Geschäftspartner dem diese Rechnung zugeordnet werden soll.
     */
    private BusinessPartner businessPartner;

    /**
     * Die eindeutige Rechnungsnummer.
     */
    private Long billNumber;

    /**
     * Das Datum an dem diese Rechnung ausgestellt wurde.
     */
    private Date billDate;

    /**
     * Ein <code>Set</code> mit allen Lieferscheinen von der diese Rechnung
     * erstellt wird.
     */
    private Set<DeliveryOrder> deliveryOrders;

    /**
     * Gibt den zu dieser Rechnung dazugehörigen <code>BusinessPartner</code>
     * zurück.
     *
     * @return Der zugehörige <code>BusinessPartner</code>.
     */
    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    /**
     * Setzt den <code>BusinessPartner</code> der zu dieser Rechnung gehört.
     *
     * @param businessPartner Der zugehörige <code>BusinessPartner</code>.
     */
    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;
    }

    /**
     * Gibt die Rechnungsnummer zurück.
     *
     * @return Die Rechnungsnummer.
     */
    public Long getBillNumber() {
        return billNumber;
    }

    /**
     * Setzt die Rechnungsnummer.
     *
     * @param billNumber Die Rechnungsnummer.
     */
    public void setBillNumber(Long billNumber) {
        this.billNumber = billNumber;
    }

    /**
     * Gibt das Rechnungsdatum, an dem diese Rechnung ausgetellt wurde,
     * zurück.
     *
     * @return Das Rechnungsdatum.
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * Setzt das Rechnungsdatum, an dem diese Rechnung ausgestellt wird.
     *
     * @param billDate Das Rechungsdatum.
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * Gibt ein <code>Set</code> mit <code>DeliveryOrder</code> zurück.
     * Von diesen Lieferscheinen wurde diese Rechnung erstellt.
     *
     * @return Ein <code>Set</code> von <code>DeliveryOrder</code>.
     */
    public Set<DeliveryOrder> getDeliveryOrders() {
        return deliveryOrders;
    }

    /**
     * Setzt ein <code>Set</code> mit <code>DeliveryOrder</code>.
     *
     * @param deliveryOrders Ein <code>Set</code> von <code>DeliveryOrder</code>.
     */
    public void setDeliveryOrders(Set<DeliveryOrder> deliveryOrders) {
        this.deliveryOrders = deliveryOrders;
    }

    /**
     * Gibt den <code>CharacterisationType</code> dieser Rechnung zurück.
     *
     * @return Der Rechnungs <code>CharacterisationType</code>.
     */
    public CharacterisationType getCharacterisationType() {
        if (getDeliveryOrders().size() > 1) return CharacterisationType.UNIT_BILLING;
        return CharacterisationType.BILL;
    }

    /**
     * Es wird dieser <code>DeliveryOrder</code> mit dem eingehenden Objekt
     * auf Gleichheit überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Bill bill = (Bill) o;

        if (this.getBillDate() != null ? !this.getBillDate().equals(bill.getBillDate()) : bill.getBillDate() != null) return false;
        if (this.getBillNumber() != null ? !this.getBillNumber().equals(bill.getBillNumber()) : bill.getBillNumber() != null) return false;
        if (this.getBusinessPartner() != null ? !this.getBusinessPartner().equals(bill.getBusinessPartner()) : bill.getBusinessPartner() != null) return false;
        return !(this.getDeliveryOrders() != null ? !this.getDeliveryOrders().equals(bill.getDeliveryOrders()) : bill.getDeliveryOrders() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (this.getBusinessPartner() != null ? this.getBusinessPartner().hashCode() : 0);
        result = 29 * result + (this.getBillNumber() != null ? this.getBillNumber().hashCode() : 0);
        result = 29 * result + (this.getBillDate() != null ? this.getBillDate().hashCode() : 0);
        result = 29 * result + (this.getDeliveryOrders() != null ? this.getDeliveryOrders().hashCode() : 0);
        return result;
    }
}