package ecobill.module.base.domain;

import java.util.Set;
import java.text.Collator;

/**
 * Der <code>BusinessPartner</code> stellt den Gesch�ftspartner und somit bspw. den Adressaten
 * einer Rechnung oder eines Lieferscheines dar.
 * Zus�tzlich beinhaltet der Gesch�ftspartner alle Lieferscheine und Rechnungen, die auf diesen
 * Gesch�ftspartner ausgestellt wurden.
 * <p/>
 * User: rro
 * Date: 26.07.2005
 * Time: 18:24:14
 *
 * @author Roman R&auml;dle
 * @version $Id: BusinessPartner.java,v 1.3 2005/09/27 15:07:38 raedler Exp $
 * @since EcoBill 1.0
 */
public final class BusinessPartner extends AbstractDomain {

    /**
     * Der Schl�ssel der Anrede des Unternehmens.
     */
    private String companyTitleKey;

    /**
     * Der Name des Unternehmens.
     */
    private String companyName;

    /**
     * Die Person zu diesem Gesch�ftspartner.
     */
    private Person person;

    /**
     * Die Addresse des Gesch�ftspartners.
     */
    private Address address;

    /**
     * Die Bankverbindungsdaten des Gesch�ftspartners.
     */
    private Banking banking;

    /**
     * Die Id des Gesch�ftspartners.
     */
    private long id;

    /**
     * Alle Lieferscheine die auf diesen Gesch�ftspartner ausgestellt worden
     * sind.
     */
    private Set<DeliveryOrder> deliveryOrders;

    /**
     * Alle Rechnungen die an diesen Gesch�ftspartner ausgestellt sind.
     */
    private Set<Bill> bills;

    /**
     * Gibt den Schl�ssel der Anrede des Unternehmens zur�ck.
     *
     * @return Der Schl�ssel der Anrede des Unternehmens.
     */
    public String getCompanyTitleKey() {
        return companyTitleKey;
    }

    /**
     * Setzt den Schl�ssel der Anrede des Unternehmens zur�ck.
     *
     * @param companyTitleKey Der Schl�ssel der Anrede des Unternehmens.
     */
    public void setCompanyTitleKey(String companyTitleKey) {
        this.companyTitleKey = companyTitleKey;
    }

    /**
     * Gibt den Namen des Unternehmens zur�ck.
     *
     * @return Der Name des Unternehmens.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setzt den Namen des Unternehmens.
     *
     * @param companyName Der Name des Unternehmens.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gibt die zust�ndige Person des Gesch�ftspartners
     * zur�ck.
     *
     * @return Die zust�ndige Person des Gesch�ftspartners.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Setzt die zust�ndige Person des Gesch�ftspartners.
     *
     * @param person Die zust�ndige Person des Gesch�ftspartners.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Gibt die Adresse des Gesch�ftspartners zur�ck, falls diese nicht
     * vorhanden ist wird die Adresse der zust�ndigen Person zur�ckgegeben.
     *
     * @return Die Adresse des Gesch�ftspartners, falls diese nicht vorhanden ist
     *         wird die Adresse der zust�ndigen Person zur�ckgegeben.
     */
    public Address getAddress() {
        if (address == null) return getPerson().getAddress();

        return address;
    }

    /**
     * Setzt die Adresse des Gesch�ftspartners.
     *
     * @param address Die Adresse des Gesch�ftspartners.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gibt die Bankdaten des Gesch�ftspartners zur�ck.
     *
     * @return Die Bankdaten des Gesch�ftspartners.
     */
    public Banking getBanking() {
        return banking;
    }

    /**
     * Setzt die Bankdaten des Gesch�ftspartners.
     *
     * @param banking Die Bankdaten des Gesch�ftspartners.
     */
    public void setBanking(Banking banking) {
        this.banking = banking;
    }

    /**
     * Gibt ein <code>Set</code> mit allen Lieferscheinen, die diesem Gesch�ftspartner
     * ausgestellt wurden.
     *
     * @return Ein <code>Set</code> mit <code>DeliveryOrder</code> Objekten.
     */
    public Set<DeliveryOrder> getDeliveryOrders() {
        return deliveryOrders;
    }

    /**
     * Setzt ein <code>Set</code> mit Lieferscheinen, die diesem Gesch�ftspartner
     * zugeordnet werden sollen.
     *
     * @param deliveryOrders Ein <code>Set</code> mit <code>DeliveryOrder</code> Objekten.
     */
    public void setDeliveryOrders(Set<DeliveryOrder> deliveryOrders) {
        this.deliveryOrders = deliveryOrders;
    }

    /**
     * Gibt ein <code>Set</code> mit allen Rechnungen, die diesem Gesch�ftspartner
     * ausgestellt wurden.
     *
     * @return Ein <code>Set</code> mit <code>Bill</code> Objekten.
     */
    public Set<Bill> getBills() {
        return bills;
    }

    /**
     * Setzt ein <code>Set</code> mit Rechnungen, die diesem Gesch�ftspartner
     * zugeordnet werden sollen.
     *
     * @param bills Ein <code>Set</code> mit <code>Bill</code> Objekten.
     */
    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    /**
     * Es wird dieser <code>BusinessPartner</code> mit dem eingehenden Objekt
     * auf Gleichheit �berpr�ft.
     *
     * @see Object#equals(Object)
     *
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BusinessPartner that = (BusinessPartner) o;

        if (this.getAddress() != null ? !this.getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (this.getBanking() != null ? !this.getBanking().equals(that.getBanking()) : that.getBanking() != null) return false;
        if (this.getBills() != null ? !this.getBills().equals(that.getBills()) : that.getBills() != null) return false;
        if (this.getCompanyName() != null ? !this.getCompanyName().equals(that.getCompanyName()) : that.getCompanyName() != null) return false;
        if (this.getCompanyTitleKey() != null ? !this.getCompanyTitleKey().equals(that.getCompanyTitleKey()) : that.getCompanyTitleKey() != null) return false;
        if (this.getDeliveryOrders() != null ? !this.getDeliveryOrders().equals(that.getDeliveryOrders()) : that.getDeliveryOrders() != null) return false;
        return !(this.getPerson() != null ? !this.getPerson().equals(that.getPerson()) : that.getPerson() != null);
    }
    /**/

    /**
     * @see Object#hashCode()
     *
    public int hashCode() {
        int result;
        result = (this.getCompanyTitleKey() != null ? this.getCompanyTitleKey().hashCode() : 0);
        result = 29 * result + (this.getCompanyName() != null ? this.getCompanyName().hashCode() : 0);
        result = 29 * result + (this.getPerson() != null ? this.getPerson().hashCode() : 0);
        result = 29 * result + (this.getAddress() != null ? this.getAddress().hashCode() : 0);
        result = 29 * result + (this.getBanking() != null ? this.getBanking().hashCode() : 0);
        result = 29 * result + (this.getDeliveryOrders() != null ? this.getDeliveryOrders().hashCode() : 0);
        result = 29 * result + (this.getBills() != null ? getBills().hashCode() : 0);
        return result;
    }
    /**/
}
