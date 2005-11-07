package ecobill.module.base.domain;

import ecobill.module.base.hbm.sort.OrderPositionComparable;

/**
 * Der <code>ReduplicatedArticle</code> beinhaltet die dazugehörige Anzahl, den eingetragenen
 * Einzelpreis und die Beschreibung von diesem. Dieses Objekt wird für einen Lieferschein
 * und/oder eine Rechnung verwendet.
 * <p/>
 * User: rro
 * Date: 26.07.2005
 * Time: 22:23:14
 *
 * @author Roman R&auml;dle
 * @version $Id: ReduplicatedArticle.java,v 1.7 2005/11/07 00:01:26 raedler Exp $
 * @since EcoBill 1.0
 */
public class ReduplicatedArticle extends AbstractDomain implements OrderPositionComparable, Comparable<ReduplicatedArticle> {

    /**
     * Die Position an der dieser Artikel in einem Lieferschein oder einer Rechnung
     * erscheinen soll.
     */
    private Integer orderPosition;

    /**
     * Die Artikelnummer des Artikels.
     */
    private String articleNumber;

    /**
     * Die Anzahl des Artikels.
     */
    private Double quantity;

    /**
     * Der Einzelpreis des Artikels.
     */
    private Double price;

    /**
     * Die Einheit des duplizierten Artikels.
     */
    private String unit;

    /**
     * Die Beschreibung des Artikels.
     */
    private String description;

    /**
     * Die Delivery_Order_Id des Artikels.
     */
    private DeliveryOrder deliveryOrder;

    /**
     * Gibt die Position an der dieser Artikel in einem Lieferschein oder einer Rechnung
     * erscheinen soll zurück.
     *
     * @return Die Position an der dieser Artikel in einem Lieferschein oder einer Rechnung
     *         erscheinen soll.
     */
    public Integer getOrderPosition() {
        return orderPosition;
    }

    /**
     * Setzt die Position an der dieser Artikel in einem Lieferschein oder einer Rechnung
     * erscheinen soll.
     *
     * @param orderPosition Die Position an der dieser Artikel in einem Lieferschein oder einer
     *                      Rechnung erscheinen soll.
     */
    public void setOrderPosition(Integer orderPosition) {
        this.orderPosition = orderPosition;
    }

    /**
     * Gibt die Artikelnummer des Artikels zurück.
     * Diese Nummer sollte eindeutig sein.
     *
     * @return Die Artikelnummer des Artikels.
     */
    public String getArticleNumber() {
        return articleNumber;
    }

    /**
     * Setzt die Artikelnummer des Artikels.
     *
     * @param articleNumber Die Artikelnummer des Artikels.
     */
    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    /**
     * Gibt die Anzahl des Artikels zurück.
     *
     * @return Die Anzahl des Artikels.
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * Gibt die DeliverOrder des Artikels zurück.
     *
     * @return Die DeliverOrder des Artikels.
     */
    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    /**
     * Setzt die DeliverOrderId des Artikels zurück.
     *
     * @param deliveryOrder DeliverOrder des Artikels
     */
    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    /**
     * Setzt die Anzahl des Artikels.
     *
     * @param quantity Die Anzahl des Artikels.
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    /**
     * Gibt den Einzelpreis des Artikels zurück.
     *
     * @return Der Einzelpreis des Artikels.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Setzt den Einzelpreis des Artikels.
     *
     * @param price Der Einzelpreis des Artikels.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gibt die Einheit des duplizierten Artikels.
     *
     * @return Die Einheit des duplizierten Artikels.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Setzt die Einheit des duplizierten Artikels.
     *
     * @param unit Die Einheit des duplizierten Artikels.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gibt die Beschreibung des Artikels zurück.
     *
     * @return Die Beschreibung des Artikels.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung des Artikels.
     *
     * @param description Die Beschreibung des Artikels.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @see Comparable#compareTo(ReduplicatedArticle)
     */
    public int compareTo(ReduplicatedArticle article) {

        Integer oOrderPosition = article.getOrderPosition();

        return orderPosition.compareTo(oOrderPosition);
    }

    /**
     * Es wird dieser <code>ReduplicatedArticle</code> mit dem eingehenden Objekt
     * auf Gleichheit überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ReduplicatedArticle that = (ReduplicatedArticle) o;

        if (this.getQuantity() != null ? !this.getQuantity().equals(that.getQuantity()) : that.getQuantity() != null) return false;
        if (this.getDescription() != null ? !this.getDescription().equals(that.getDescription()) : that.getDescription() != null) return false;
        return !(this.getPrice() != null ? !this.getPrice().equals(that.getPrice()) : that.getPrice() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (this.getQuantity() != null ? this.getQuantity().hashCode() : 0);
        result = 29 * result + (this.getPrice() != null ? this.getPrice().hashCode() : 0);
        result = 29 * result + (this.getDescription() != null ? this.getDescription().hashCode() : 0);
        return result;
    }
}
