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
 * @version $Id: ReduplicatedArticle.java,v 1.3 2005/09/28 15:44:18 raedler Exp $
 * @since EcoBill 1.0
 */
public class ReduplicatedArticle extends AbstractDomain {

    /**
     * Die Artikelnummer des Artikels.
     */
    private String articleNumber;

    /**
     * Die Anzahl des Artikels.
     */
    private Double amount;

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
     * Der ursprüngliche Artikel aus dem dieser duplizierte Artikel hervorging.
     */
    private Article article;

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
     * @param deliveryOrder DeliverOrder des Artikels
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
     * Gibt den ursprünglichen Artikel, aus dem dieser duplizierte Artikel hervorging,
     * zurück.
     *
     * @return Der ursprünglich Artikel aus dem dieser duplizierte Artikel hervorging.
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Setzt den ursprünglichen Artikel, aus dem dieser duplizierte Artikel hervorging.
     *
     * @param article Der ursprüngliche Artikel aus dem dieser duplizierte Artikel
     *                hervorging.
     */
    public void setArticle(Article article) {
        this.article = article;
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
