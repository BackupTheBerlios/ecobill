package ecobill.module.base.domain;

import java.util.Set;
import java.util.Date;
import java.text.Collator;

/**
 * Die <code>DeliveryOrder</code> ist ein Lieferschein Objekt, dem ein Geschäftspartner zugeordnet wird.
 * Desweiteren muss ein Lieferscheindatum und eine Lieferscheinnummer angegeben werden.
 * Der <code>CharacterisationType</code> gibt den Typ des Lieferscheines an.
 * Zu jedem Lieferschein gehört ein <code>Set</code> von <code>ReduplicatedArticle</code>.
 * <p/>
 * User: rro
 * Date: 26.07.2005
 * Time: 19:51:39
 *
 * @author Roman R&auml;dle
 * @version $Id: DeliveryOrder.java,v 1.1 2005/07/28 21:03:50 raedler Exp $
 * @since EcoBill 1.0
 */
public final class DeliveryOrder extends AbstractDomain {

    /**
     * Der Geschäftspartner dem dieser Lieferschein zugeordnet werden soll.
     */
    private BusinessPartner businessPartner;

    /**
     * Die eindeutige Lieferscheinnummer.
     */
    private Long deliveryOrderNumber;

    /**
     * Das Datum an dem dieser Lieferschein ausgestellt wurde.
     */
    private Date deliveryOrderDate;

    /**
     * Gibt den Lieferscheintyp an.
     */
    // @todo durch ENUM ersetzen
    //private CharacterisationType characterisationType;
    private String characterisationType;

    /**
     * Gibt an ob von diesem Lieferschein schon eine Rechnung erstellt wurde.
     */
    private boolean preparedBill = false;

    /**
     * Ein <code>Set</code> mit allen Artikeln und die Anzahl jedes dieser
     * Artikel, usw. dieses Lieferscheines.
     */
    private Set<ReduplicatedArticle> articles;

    /**
     * Gibt den zu diesem Lieferschein dazugehörigen <code>BusinessPartner</code>
     * zurück.
     *
     * @return Der zugehörige <code>BusinessPartner</code>.
     */
    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    /**
     * Setzt den <code>BusinessPartner</code> der zu diesem Lieferschein gehört.
     *
     * @param businessPartner Der zugehörige <code>BusinessPartner</code>.
     */
    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;
    }

    /**
     * Gibt die Lieferscheinnummer zurück.
     *
     * @return Die Lieferscheinnummer.
     */
    public Long getDeliveryOrderNumber() {
        return deliveryOrderNumber;
    }

    /**
     * Setzt die Lieferscheinnummer.
     *
     * @param deliveryOrderNumber Die Lieferscheinnummer.
     */
    public void setDeliveryOrderNumber(Long deliveryOrderNumber) {
        this.deliveryOrderNumber = deliveryOrderNumber;
    }

    /**
     * Gibt das Lieferscheindatum, an dem dieser Lieferschein ausgetellt wurde,
     * zurück.
     *
     * @return Das Lieferscheindatum.
     */
    public Date getDeliveryOrderDate() {
        return deliveryOrderDate;
    }

    /**
     * Setzt das Lieferscheindatum, an dem dieser Lieferschein ausgestellt wird.
     *
     * @param deliveryOrderDate Das Lieferscheindatum.
     */
    public void setDeliveryOrderDate(Date deliveryOrderDate) {
        this.deliveryOrderDate = deliveryOrderDate;
    }

    /**
     * Gibt den <code>CharacterisationType</code> dieses Lieferscheines zurück.
     *
     * @return Der Lieferschein <code>CharacterisationType</code>.
     */
    // @todo siehe oben
    public String getCharacterisationType() {
        return characterisationType;
    }

    /**
     * Setzt den <code>CharacterisationType</code> dieses Lieferscheines.
     *
     * @param charaterisationType Der Lieferschein <code>CharacterisationType</code>.
     */
    // @todo siehe oben
    public void setCharacterisationType(String charaterisationType) {
        this.characterisationType = charaterisationType;
    }

    /**
     * Gibt an ob von diesem Lieferschein schon eine Rechnung erstellt wurde.
     *
     * @return Es gilt true falls von diesem Lieferschein schon eine Rechnung
     *         erstellt wurde, andernfalls gilt false.
     */
    public boolean isPreparedBill() {
        return preparedBill;
    }

    /**
     * Setzt einen boolschen Wert ob von diesem Lieferschein schon eine Rechnung erstellt
     * wurde.
     *
     * @param preparedBill Es gilt true falls von diesem Lieferschein schon eine Rechnung
     *                     erstellt wurde, andernfalls gilt false.
     */
    public void setPreparedBill(boolean preparedBill) {
        this.preparedBill = preparedBill;
    }

    /**
     * Gibt ein <code>Set</code> mit <code>ReduplicatedArticle</code> zurück.
     *
     * @return Ein <code>Set</code> mit <code>ReduplicatedArticle</code>.
     */
    public Set<ReduplicatedArticle> getArticles() {
        return articles;
    }

    /**
     * Setzt ein <code>Set</code> mit <code>ReduplicatedArticle</code>.
     *
     * @param articles Ein <code>Set</code> mit <code>ReduplicatedArticle</code>
     */
    public void setArticles(Set<ReduplicatedArticle> articles) {
        this.articles = articles;
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

        final DeliveryOrder that = (DeliveryOrder) o;

        if (preparedBill != that.preparedBill) return false;
        if (articles != null ? !articles.equals(that.articles) : that.articles != null) return false;
        if (businessPartner != null ? !businessPartner.equals(that.businessPartner) : that.businessPartner != null) return false;
        if (characterisationType != null ? !characterisationType.equals(that.characterisationType) : that.characterisationType != null) return false;
        if (deliveryOrderDate != null ? !deliveryOrderDate.equals(that.deliveryOrderDate) : that.deliveryOrderDate != null) return false;
        return !(deliveryOrderNumber != null ? !deliveryOrderNumber.equals(that.deliveryOrderNumber) : that.deliveryOrderNumber != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (businessPartner != null ? businessPartner.hashCode() : 0);
        result = 29 * result + (deliveryOrderNumber != null ? deliveryOrderNumber.hashCode() : 0);
        result = 29 * result + (deliveryOrderDate != null ? deliveryOrderDate.hashCode() : 0);
        result = 29 * result + (characterisationType != null ? characterisationType.hashCode() : 0);
        result = 29 * result + (preparedBill ? 1 : 0);
        result = 29 * result + (articles != null ? articles.hashCode() : 0);
        return result;
    }
}
