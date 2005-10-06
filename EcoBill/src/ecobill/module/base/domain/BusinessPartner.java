package ecobill.module.base.domain;

import ecobill.core.system.WorkArea;

import java.util.Set;

/**
 * Der <code>BusinessPartner</code> stellt den Geschäftspartner und somit bspw. den Adressaten
 * einer Rechnung oder eines Lieferscheines dar.
 * Zusätzlich beinhaltet der Geschäftspartner alle Lieferscheine und Rechnungen, die auf diesen
 * Geschäftspartner ausgestellt wurden.
 * <p/>
 * User: rro
 * Date: 26.07.2005
 * Time: 18:24:14
 *
 * @author Roman R&auml;dle
 * @version $Id: BusinessPartner.java,v 1.6 2005/10/06 14:07:17 raedler Exp $
 * @since EcoBill 1.0
 */
public class BusinessPartner extends AbstractDomain {

    /**
     * Die Kundennummer des Geschäftspartner.
     */
    private String customerNumber;

    /**
     * Der Schlüssel der Anrede des Unternehmens.
     */
    private String companyTitle;

    /**
     * Der Name des Unternehmens.
     */
    private String companyName;

    /**
     * Die Branche des Unternehmens.
     */
    private String companyBranch;

    /**
     * Falls es sich bei dem Geschäftspartner um eine Firma handelt, dann gibt dieses
     * Flag an ob bspw. auf dem Briefkopf z.H. "Person" stehen soll oder nicht.
     */
    private boolean forAttentionOf = false;

    /**
     * Die Person zu diesem Geschäftspartner.
     */
    private Person person;

    /**
     * Die Addresse des Geschäftspartners.
     */
    private Address address;

    /**
     * Die Bankverbindungsdaten des Geschäftspartners.
     */
    private Banking banking;

    /**
     * Alle Lieferscheine die auf diesen Geschäftspartner ausgestellt worden
     * sind.
     */
    private Set<DeliveryOrder> deliveryOrders;

    /**
     * Alle Rechnungen die an diesen Geschäftspartner ausgestellt sind.
     */
    private Set<Bill> bills;

    /**
     * Gibt die Kundennummer des Geschäftspartner zurück.
     *
     * @return Die Kundennummer des Geschäftspartner.
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * Setzt die Kundennummer des Geschäftspartner.
     *
     * @param customerNumber Die Kundennummer des Geschäftspartner.
     */
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    /**
     * Gibt den Schlüssel der Anrede des Unternehmens zurück.
     *
     * @return Der Schlüssel der Anrede des Unternehmens.
     */
    public String getCompanyTitle() {
        return companyTitle;
    }

    /**
     * Setzt den Schlüssel der Anrede des Unternehmens zurück.
     *
     * @param companyTitleKey Der Schlüssel der Anrede des Unternehmens.
     */
    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    /**
     * Gibt auf jedenfall eine Anrede zurück. Sollte der Geschäftspartner keine Firma sein
     * dann wird die Anrede der Person zurückgegeben.
     *
     * @return Eine Anrede der Firma oder der Person.
     */
    public String getAssuredTitle() {

        if (companyTitle != null && !"".equals(companyTitle)) {
            return companyTitle;
        }

        return getPerson().getLetterTitle();
    }

    /**
     * Gibt den Namen des Unternehmens zurück.
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
     * Gibt die Branche des Unternehmens zurück.
     *
     * @return Die Branche des Unternehmens.
     */
    public String getCompanyBranch() {
        return companyBranch;
    }

    /**
     * Setzt die Branche des Unternehmens.
     *
     * @param companyBranch Die Branche des Unternehmens.
     */
    public void setCompanyBranch(String companyBranch) {
        this.companyBranch = companyBranch;
    }

    /**
     * Falls es sich bei dem Geschäftspartner um eine Firma handelt, dann gibt dieses
     * Flag an ob bspw. auf dem Briefkopf z.H. "Person" stehen soll oder nicht.
     *
     * @return Gibt an ob bswp. auf dem Briefkopf z.H. "Person" stehen soll oder nicht.
     */
    public boolean isForAttentionOf() {
        return forAttentionOf;
    }

    /**
     * Falls es sich bei dem Geschäftspartner um eine Firma handelt, dann gibt dieses
     * Flag an ob bspw. auf dem Briefkopf z.H. "Person" stehen soll oder nicht.
     *
     * @param forAttentionOf Gibt an ob bswp. auf dem Briefkopf z.H. "Person" stehen soll oder
     *                       nicht.
     */
    public void setForAttentionOf(boolean forAttentionOf) {
        this.forAttentionOf = forAttentionOf;
    }

    /**
     * Gibt die zuständige Person des Geschäftspartners
     * zurück.
     *
     * @return Die zuständige Person des Geschäftspartners.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Setzt die zuständige Person des Geschäftspartners.
     *
     * @param person Die zuständige Person des Geschäftspartners.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Gibt die Adresse des Geschäftspartners zurück, falls diese nicht
     * vorhanden ist wird die Adresse der zuständigen Person zurückgegeben.
     *
     * @return Die Adresse des Geschäftspartners, falls diese nicht vorhanden ist
     *         wird die Adresse der zuständigen Person zurückgegeben.
     */
    public Address getAddress() {
        if (address == null) return getPerson().getAddress();

        return address;
    }

    /**
     * Setzt die Adresse des Geschäftspartners.
     *
     * @param address Die Adresse des Geschäftspartners.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gibt die Bankdaten des Geschäftspartners zurück.
     *
     * @return Die Bankdaten des Geschäftspartners.
     */
    public Banking getBanking() {
        return banking;
    }

    /**
     * Setzt die Bankdaten des Geschäftspartners.
     *
     * @param banking Die Bankdaten des Geschäftspartners.
     */
    public void setBanking(Banking banking) {
        this.banking = banking;
    }

    /**
     * Gibt ein <code>Set</code> mit allen Lieferscheinen, die diesem Geschäftspartner
     * ausgestellt wurden.
     *
     * @return Ein <code>Set</code> mit <code>DeliveryOrder</code> Objekten.
     */
    public Set<DeliveryOrder> getDeliveryOrders() {
        return deliveryOrders;
    }

    /**
     * Setzt ein <code>Set</code> mit Lieferscheinen, die diesem Geschäftspartner
     * zugeordnet werden sollen.
     *
     * @param deliveryOrders Ein <code>Set</code> mit <code>DeliveryOrder</code> Objekten.
     */
    public void setDeliveryOrders(Set<DeliveryOrder> deliveryOrders) {
        this.deliveryOrders = deliveryOrders;
    }

    /**
     * Gibt ein <code>Set</code> mit allen Rechnungen, die diesem Geschäftspartner
     * ausgestellt wurden.
     *
     * @return Ein <code>Set</code> mit <code>Bill</code> Objekten.
     */
    public Set<Bill> getBills() {
        return bills;
    }

    /**
     * Setzt ein <code>Set</code> mit Rechnungen, die diesem Geschäftspartner
     * zugeordnet werden sollen.
     *
     * @param bills Ein <code>Set</code> mit <code>Bill</code> Objekten.
     */
    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    /**
     * Es wird dieser <code>BusinessPartner</code> mit dem eingehenden Objekt
     * auf Gleichheit überprüft.
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
    if (this.getCompanyTitle() != null ? !this.getCompanyTitle().equals(that.getCompanyTitle()) : that.getCompanyTitle() != null) return false;
    if (this.getDeliveryOrders() != null ? !this.getDeliveryOrders().equals(that.getDeliveryOrders()) : that.getDeliveryOrders() != null) return false;
    return !(this.getPerson() != null ? !this.getPerson().equals(that.getPerson()) : that.getPerson() != null);
    }
    /**/

    /**
     * @see Object#hashCode()
     *
    public int hashCode() {
    int result;
    result = (this.getCompanyTitle() != null ? this.getCompanyTitle().hashCode() : 0);
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
