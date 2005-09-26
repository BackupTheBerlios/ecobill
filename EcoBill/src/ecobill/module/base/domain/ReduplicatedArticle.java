package ecobill.module.base.domain;

import java.text.Collator;

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
 * @version $Id: ReduplicatedArticle.java,v 1.2 2005/09/26 15:27:40 gath Exp $
 * @since EcoBill 1.0
 */
public final class ReduplicatedArticle extends AbstractDomain {

    /**
     * Die Anzahl des Artikels.
     */
    private Double amount;

    /**
     * Der Einzelpreis des Artikels.
     */
    private Double price;

    /**
     * Die Beschreibung des Artikels.
     */
    private String description;

    /**
     * Die Delivery_Order_Id des Artikels.
     */
    private DeliveryOrder deliveryOrder;


    /**
     * Gibt die Anzahl des Artikels zurück.
     *
     * @return Die Anzahl des Artikels.
     */
    public Double getAmount() {
        return amount;
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
     * @param deliveryOrder   DeliverOrder des Artikels
     */
    public void setDeliveryOrder(DeliveryOrder dO) {
        this.deliveryOrder = deliveryOrder;
    }
    /**
     * Setzt die Anzahl des Artikels.
     *
     * @param amount Die Anzahl des Artikels.
     */
    public void setAmount(Double amount) {
        this.amount = amount;
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
     * Es wird dieser <code>ReduplicatedArticle</code> mit dem eingehenden Objekt
     * auf Gleichheit überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ReduplicatedArticle that = (ReduplicatedArticle) o;

        if (this.getAmount() != null ? !this.getAmount().equals(that.getAmount()) : that.getAmount() != null) return false;
        if (this.getDescription() != null ? !this.getDescription().equals(that.getDescription()) : that.getDescription() != null) return false;
        return !(this.getPrice() != null ? !this.getPrice().equals(that.getPrice()) : that.getPrice() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (this.getAmount() != null ? this.getAmount().hashCode() : 0);
        result = 29 * result + (this.getPrice() != null ? this.getPrice().hashCode() : 0);
        result = 29 * result + (this.getDescription() != null ? this.getDescription().hashCode() : 0);
        return result;
    }
}
