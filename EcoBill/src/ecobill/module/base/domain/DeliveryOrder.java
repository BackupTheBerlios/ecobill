package ecobill.module.base.domain;

import ecobill.module.base.hbm.sort.OrderPositionComparator;

import java.util.*;

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
 * @version $Id: DeliveryOrder.java,v 1.6 2006/01/29 23:16:45 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrder extends AbstractDomain {

    /**
     * Der Geschäftspartner dem dieser Lieferschein zugeordnet werden soll.
     */
    private BusinessPartner businessPartner;

    /**
     * Die eindeutige Lieferscheinnummer.
     */
    private String deliveryOrderNumber;

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
     * Ein Freitext der optional angegeben werden kann. Dieser Freitext kann bspw. an den
     * Anfang eines Reports gesetzt werden.
     */
    private String prefixFreetext;

    /**
     * Ein Freitext der optional angegeben werden kann. Dieser Freitext kann bspw. ans Ende
     * eines Reports gesetzt werden.
     */
    private String suffixFreetext;

    /**
     * Gibt an ob von diesem Lieferschein schon eine Rechnung erstellt wurde.
     */
    private boolean preparedBill = false;

    /**
     * Ein <code>Set</code> mit allen Artikeln und die Anzahl jedes dieser
     * Artikel, usw. dieses Lieferscheines.
     */
    private SortedSet<ReduplicatedArticle> articles;

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
    public String getDeliveryOrderNumber() {
        return deliveryOrderNumber;
    }

    /**
     * Setzt die Lieferscheinnummer.
     *
     * @param deliveryOrderNumber Die Lieferscheinnummer.
     */
    public void setDeliveryOrderNumber(String deliveryOrderNumber) {
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
     * Gibt den Freitext, der optional angegeben werden kann, zurück. Dieser Freitext
     * kann bspw. an den Anfang eines Reports gesetzt werden.
     *
     * @return Der Freitext der optional angegeben werden kann.
     */
    public String getPrefixFreetext() {
        return prefixFreetext;
    }

    /**
     * Setzt den Freitext, der optional angegeben werden kann. Dieser Freitext
     * kann bspw. an den Anfang eines Reports gesetzt werden.
     *
     * @param prefixFreetext Der Freitext der optional angegeben werden kann.
     */
    public void setPrefixFreetext(String prefixFreetext) {
        this.prefixFreetext = prefixFreetext;
    }

    /**
     * Gibt den Freitext, der optional angegeben werden kann, zurück. Dieser Freitext
     * kann bspw. ans Ende eines Reports gesetzt werden.
     *
     * @return Der Freitext der optional angegeben werden kann.
     */
    public String getSuffixFreetext() {
        return suffixFreetext;
    }

    /**
     * Setzt den Freitext, der optional angegeben werden kann. Dieser Freitext
     * kann bspw. ans Ende eines Reports gesetzt werden.
     *
     * @param suffixFreetext Der Freitext der optional angegeben werden kann.
     */
    public void setSuffixFreetext(String suffixFreetext) {
        this.suffixFreetext = suffixFreetext;
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

        if (articles == null) {
            articles = new TreeSet<ReduplicatedArticle>(new OrderPositionComparator());
        }

        return articles;
    }

    /**
     * Setzt ein <code>Set</code> mit <code>ReduplicatedArticle</code>.
     *
     * @param articles Ein <code>Set</code> mit <code>ReduplicatedArticle</code>
     */
    public void setArticles(SortedSet<ReduplicatedArticle> articles) {
        this.articles = articles;
    }

    /**
     * Fügt zu den Artikeln des Lieferscheines den <code>ReduplicatedArticle</code> hinzu.
     *
     * @param reduplicatedArticle Der neue <code>ReduplicatedArticle</code>.
     */
    public void addArticle(ReduplicatedArticle reduplicatedArticle) {

        if (articles == null) {
            articles = new TreeSet<ReduplicatedArticle>();
        }

        articles.add(reduplicatedArticle);
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
        result = (this.getBusinessPartner() != null ? this.getBusinessPartner().hashCode() : 0);
        result = 29 * result + (this.getDeliveryOrderNumber() != null ? this.getDeliveryOrderNumber().hashCode() : 0);
        result = 29 * result + (this.getDeliveryOrderDate() != null ? this.getDeliveryOrderDate().hashCode() : 0);
        result = 29 * result + (this.getCharacterisationType() != null ? this.getCharacterisationType().hashCode() : 0);
        result = 29 * result + (this.isPreparedBill() ? 1 : 0);
        result = 29 * result + (this.getArticles() != null ? this.getArticles().hashCode() : 0);
        return result;
    }
}
