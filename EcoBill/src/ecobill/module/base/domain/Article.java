package ecobill.module.base.domain;

import ecobill.util.LocalizerUtils;
import ecobill.util.exception.LocalizerException;
import ecobill.core.system.WorkArea;

import java.util.Set;
import java.util.Locale;
import java.util.Collections;
import java.util.List;

/**
 * Ein <code>Article</code> beinhaltet alle Informationen über eine bestimmten Artikel. Es sind
 * bspw der Einzelpreis, die Einheit, der Lagerbestand, Gebindeeinheit und -menge und die
 * verschiedenen landesspezifischen Beschreibungen des Artikels angegeben.
 * Desweiteren enthält diese Klasse Business Methoden um eine landesspezifische Beschreibung zu
 * erhalten.
 * Wird dieses Objekt von <code>AbstractDomain</code> abgeleitet ist es auch möglich jede Instanz
 * von dieser Klasse in der Datenbank und somit Persistent abzulegen.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 00:37:27
 *
 * @author Roman R&auml;dle
 * @version $Id: Article.java,v 1.1 2005/07/28 21:03:48 raedler Exp $
 * @since EcoBill 1.0
 */
public final class Article extends AbstractDomain {

    /**
     * Dieser Schlüssel gibt einen Schlüssel einer Einheit an.
     * <br/>
     * Bspw. "weight" -> Dieser Schlüssel ist in einem <code>ResourceBundle</code>
     * enthalten und kann somit darin landesspezifisch angegeben werden.
     */
    private String unitKey;

    /**
     * Der Einzelpreis des Artikels.
     */
    private double price;

    /**
     * Der aktuelle Lagerbestand des Artikels.
     */
    private double inStock;

    /**
     * Die Gebindemenge des Artikels.
     */
    private double bundleCapacity;

    /**
     * Die Gebindeeinheit des Artikels.
     */
    private String bundleUnitKey;

    /**
     * Dieses <code>Set</code> beinhaltet alle Beschreibungen des Artikels.
     * Jede Beschreibung ist eine Instanz der Klasse <code>ArticleDescription</code>
     * - diese Klasse implementiert das Interface <code>Localizable</code> -
     * somit kann die landesspezifische Beschreibung herausgefiltert werden.
     */
    private Set<ArticleDescription> descriptions;

    /**
     * Gibt den Schlüssel unter diesem man in den
     * <code>ResourceBundle</code> den dazugehörigen Wert finden kann
     * zurück.
     * Der unitKey weist auf eine landesspezifische Einheit wie bspw.
     * (unit) Stück.
     *
     * @return Der Schlüssel der auf den landesspezifischen Wert in
     *         einem <code>ResourceBundle</code> zeigt.
     */
    public String getUnitKey() {
        return unitKey;
    }

    /**
     * Setzt den Schlüssel unter diesem man in den
     * <code>ResourceBundle</code> den dazugehörigen Wert finden kann.
     * Der unitKey weist dann auf eine landesspezifischen Einheit wie
     * bspw. (unit) Stück
     *
     * @param unitKey Der Schlüssel der auf den landesspezifischen Wert
     *                in einem <code>ResourceBundle</code> weist.
     */
    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    /**
     * Gibt den Einzelpreis des Artikels zurück.
     *
     * @return Der Einzelpreis des Artikels.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setzt den Einzelpreis des Artikels.
     *
     * @param price Der Einzelpreis des Artikels.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gibt den aktuellen Lagerbestand des Artikels zurück.
     *
     * @return Der aktuelle Lagerbestand des Artikels.
     */
    public double getInStock() {
        return inStock;
    }

    /**
     * Setzt den aktuellen Lagerbestand des Artikels.
     *
     * @param inStock Der aktuelle Lagerbestand des Artikels.
     */
    public void setInStock(double inStock) {
        this.inStock = inStock;
    }

    /**
     * Gibt die Gebindemenge des Artikels zurück.
     *
     * @return Die Gebindemenge des Artikels.
     */
    public double getBundleCapacity() {
        return bundleCapacity;
    }

    /**
     * Setzt die Gebindemenge des Artikels.
     *
     * @param bundleCapacity Die Gebindemenge des Artikels.
     */
    public void setBundleCapacity(double bundleCapacity) {
        this.bundleCapacity = bundleCapacity;
    }

    /**
     * Gibt die Gebindeeinheit des Artikles zurück.
     *
     * @return Die Gebindeeinheit des Artikels.
     */
    public String getBundleUnitKey() {
        return bundleUnitKey;
    }

    /**
     * Setzt die Gebindeeinheit des Artikles.
     *
     * @param bundleUnitKey Die Gebindeeinheit des Artikels.
     */
    public void setBundleUnitKey(String bundleUnitKey) {
        this.bundleUnitKey = bundleUnitKey;
    }

    /**
     * Gibt das <code>Set</code> aller <code>ArticleDescription</code> zurück
     * die zu diesem <code>Article</code> gehören.
     *
     * @return Ein <code>Set</code> von <code>ArticleDescription</code>.
     */
    public Set<ArticleDescription> getDescriptions() {
        return descriptions;
    }

    /**
     * Setzt ein <code>Set</code> mit <code>ArticleDescription</code>.
     *
     * @param descriptions Ein <code>Set</code> von <code>ArticleDescription</code>.
     */
    public void setDescriptions(Set<ArticleDescription> descriptions) {
        this.descriptions = descriptions;
    }

    /**
     * Gibt die landesspezifische Beschreibung von diesem Artikel zurück.
     *
     * @return Die landesspezifische Beschreibung von diesem Artikel.
     */
    public String getLocalizedDescription() throws LocalizerException {

        /*
         * Hier wird die eingestellte <code>Locale</code> aus der <code>WorkArea</code>
         * geholt. Sollte keine <code>Locale</code> eingestellt sein wird die Default
         * <code>Locale</code> zurückgegeben.
         */
        Locale locale = WorkArea.getLocale();

        /*
         * Hole die landesspezifische Beschreibung die der <code>Locale</code> am meisten
         * ähnelt.
         */
        ArticleDescription articleDescription = (ArticleDescription) LocalizerUtils.getLocalizedObject(this.getDescriptions(), locale);

        /*
         * Hier wird die Beschreibung zurückgegeben.
         */
        return articleDescription.getDescription();
    }

    /**
     * Gibt die landesspezifische <code>ArticleDescription</code> von diesem Artikel zurück.
     *
     * @return Die landesspezifische <code>ArticleDescription</code> von diesem Artikel.
     */
    public ArticleDescription getLocalizedArticleDescription() throws LocalizerException {

        /*
         * Hier wird die eingestellte <code>Locale</code> aus der <code>WorkArea</code>
         * geholt. Sollte keine <code>Locale</code> eingestellt sein wird die Default
         * <code>Locale</code> zurückgegeben.
         */
        Locale locale = WorkArea.getLocale();

        /*
         * Hole die landesspezifische Beschreibung die der <code>Locale</code> am meisten
         * ähnelt und zurückgegeben.
         */
        return (ArticleDescription) LocalizerUtils.getLocalizedObject(this.getDescriptions(), locale);
    }

    /**
     * Es wird dieser <code>Article</code> mit dem eingehenden Objekt auf Gleichheit
     * überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Article article = (Article) o;

        if (Double.compare(article.getBundleCapacity(), this.getBundleCapacity()) != 0) return false;
        if (Double.compare(article.getInStock(), this.getInStock()) != 0) return false;
        if (Double.compare(article.getPrice(), this.getPrice()) != 0) return false;
        if (this.getBundleUnitKey() != null ? !this.getBundleUnitKey().equals(article.getBundleUnitKey()) : article.getBundleUnitKey() != null) return false;
        if (this.getDescriptions() != null ? !this.getDescriptions().equals(article.getDescriptions()) : article.getDescriptions() != null) return false;
        return !(this.getUnitKey() != null ? !this.getUnitKey().equals(article.getUnitKey()) : article.getUnitKey() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        long temp;
        result = (this.getUnitKey() != null ? this.getUnitKey().hashCode() : 0);
        temp = this.getPrice() != +0.0d ? Double.doubleToLongBits(this.getPrice()) : 0L;
        result = 29 * result + (int) (temp ^ (temp >>> 32));
        temp = this.getInStock() != +0.0d ? Double.doubleToLongBits(this.getInStock()) : 0L;
        result = 29 * result + (int) (temp ^ (temp >>> 32));
        temp = this.getBundleCapacity() != +0.0d ? Double.doubleToLongBits(this.getBundleCapacity()) : 0L;
        result = 29 * result + (int) (temp ^ (temp >>> 32));
        result = 29 * result + (this.getBundleUnitKey() != null ? this.getBundleUnitKey().hashCode() : 0);
        result = 29 * result + (this.getDescriptions() != null ? this.getDescriptions().hashCode() : 0);
        return result;
    }
}
