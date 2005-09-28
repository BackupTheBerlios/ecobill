package ecobill.module.base.domain;

import java.util.Locale;
import java.text.Collator;

/**
 * Die <code>SystemLocale</code> wird verwendet um diese Anwendung sprachunabhängig zu
 * gestalten. Sie beinhaltet die Sprache, das Land und eine evtl. Variante der Sprache.
 * Desweiteren ist es auch möglich eine <code>SystemLocale</code> mit einer
 * <code>Locale</code> zu vergleichen.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 16:09:34
 *
 * @author Roman R&auml;dle
 * @version $Id: SystemLocale.java,v 1.3 2005/09/28 15:44:18 raedler Exp $
 * @since EcoBill 1.0
 */
public class SystemLocale extends AbstractDomain {

    /**
     * Diese Konstanten werden verwendet um diese <code>SystemLocale</code> mit
     * einer <code>java.util.Locale</code> zu vergleichen. Stimmt eine
     * <code>SystemLocale</code> komplett überein wird 1 zurückgegeben. Sollte
     * diese sich immer mehr bis komplett unterscheiden sinkt die Priorität.
     */
    private static final int LANGUAGE_COUNTRY_VARIANT = 1;
    private static final int LANGUAGE_COUNTRY = 2;
    private static final int LANGUAGE = 3;
    private static final int NOTHING = Integer.MAX_VALUE;

    /**
     * Der Schlüssel der diese <code>SystemLocale</code> eindeutig identifiziert.
     * Bspw. "de_AT" -> Zusammengestzt aus language + "_" + country + "_" + variant.
     */
    private String key;

    /**
     * Die Sprache, bzw. deren Kürzel die zu dieser <code>SystemLocale</code>
     * gehört.
     * Bspw. "de" für Deutsch.
     */
    private String language;

    /**
     * Das Land in dem diese Sprache gesprochen wird.
     * Bspw. "AT" für Österreich.
     */
    private String country;

    /**
     * Die Variante dieser Sprache. Kann ein Dialekt sein.
     * Meist aber nicht angegeben.
     */
    private String variant;

    /**
     * Gibt den Schlüssel zurück der diese <code>SystemLocale</code> indentifiziert.
     * <br/>
     * Bspw. "de_AT" für die Deutsche Sprache und Land Österreich.
     *
     * @return Der Schlüssel der diese <code>SystemLocale</code> identifiziert.
     */
    public String getKey() {
        return key;
    }

    /**
     * Setzt den Schlüssel der diese <code>SystemLocale</code> eindeutig identifiziert.
     * <br/>
     * Bspw. "de_AT" für die Deutsche Sprache und Land Österreich.
     *
     * @param key Der Schlüssel der diese <code>SystemLocale</code> identifiziert.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gibt die Sprache zurück die dieser <code>SystemLocale</code> zugeordndet ist.
     * <br/>
     * Bspw. "de" für die Sprache Deutsch.
     *
     * @return Der Schlüssel der Sprache -> bspw. "en" für English.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setzt die Sprache für diese <code>SystemLocale</code>.
     *
     * @param language Der Schlüssel der Sprache -> bspw. "en" für English.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gibt das Land zurück das dieser <code>SystemLocale</code> zugeordnet ist.
     * <br/>
     * Bspw. "AT" für das Land Österreich.
     *
     * @return Der Schlüssel des Landes -> bspw. "CH" für Schweiz.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setzt das Land für diese <code>SystemLocale</code>.
     *
     * @param country Der Schlüssel des Landes -> bspw. "CH" für Schweiz.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gibt die Variante dieser <code>SystemLocale</code> zurück.
     * Diese Variante kann z.B. ein Dialekt einer Sprache sein.
     * <br/>
     * Bspw. Schwäbisch.
     *
     * @return Der Schlüssel dieser Variante.
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Setzt die Variante für diese <code>SystemLocale</code>.
     * Diese Variante kann z.B. ein Dialekt einer Sprache sein.
     * <br/>
     * Bspw. Schwäbisch.
     *
     * @param variant Der Schlüssel dieser Variante.
     */
    public void setVariant(String variant) {
        this.variant = variant;
    }

    /**
     * Es wird ein <code>java.util.Locale</code> mit dieser <code>SystemLocale</code>
     * verglichen und die Priorität - der Rang - zurückgegeben.
     * Je höher die Priorität, desto mehr stimmt diese <code>Locale</code> mit der
     * <code>SystemLocale</code> überein.
     *
     * @param locale Die <code>Locale</code> mit der diese <code>SystemLocale</code>
     *               verglichen werden soll.
     * @return Gibt die Priorität - Grad der Übereinstimmung - zurück.
     */
    public int compareTo(Locale locale) {
        if (locale.getLanguage().equals(this.getLanguage()) &&
            locale.getCountry().equals(this.getCountry()) &&
            locale.getVariant().equals(this.getVariant())) {
            return LANGUAGE_COUNTRY_VARIANT;
        }
        else if (locale.getLanguage().equals(this.getLanguage()) &&
                 locale.getCountry().equals(this.getCountry())) {
            return LANGUAGE_COUNTRY;
        }
        else if (locale.getLanguage().equals(this.getLanguage())) {
            return LANGUAGE;
        }

        return NOTHING;
    }

    /**
     * Es wird diese <code>SystemLocale</code> mit dem eingehenden Objekt auf
     * Gleichheit überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SystemLocale that = (SystemLocale) o;

        if (this.getCountry() != null ? !this.getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        if (this.getKey() != null ? !this.getKey().equals(that.getKey()) : that.getKey() != null) return false;
        if (this.getLanguage() != null ? !this.getLanguage().equals(that.getLanguage()) : that.getLanguage() != null) return false;
        return !(this.getVariant() != null ? !this.getVariant().equals(that.getVariant()) : that.getVariant() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (this.getKey() != null ? this.getKey().hashCode() : 0);
        result = 29 * result + (this.getLanguage() != null ? this.getLanguage().hashCode() : 0);
        result = 29 * result + (this.getCountry() != null ? this.getCountry().hashCode() : 0);
        result = 29 * result + (this.getVariant() != null ? this.getVariant().hashCode() : 0);
        return result;
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        return "key = " + key + " | country = " + country + " | variant = " + variant;
    }
}