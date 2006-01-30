package ecobill.module.base.domain;

import ecobill.util.Localizable;

/**
 * Die <code>ArticleDescription</code> beinhaltet die landesspezifische Artikelbeschreibung und
 * eine <code>SystemLocale</code> - implementiert das Interface <code>Localizable</code> - um
 * die landesspezifische Beschreibung mit Hilfe des <code>LocalizerUtils</code> diese heraus
 * zu filtern.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 14:53:14
 *
 * @author Roman R&auml;dle
 * @version $Id: ArticleDescription.java,v 1.3 2006/01/30 23:43:13 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleDescription extends AbstractDomain implements Localizable {

    /**
     * Die landesspezifische Beschreibung.
     */
    private String description;

    /**
     * Die für diese Beschreibung zugehörige <code>SystemLocale</code>.
     */
    private SystemLocale systemLocale;

    /**
     * Gibt die landesspezifische Beschreibung zurück.
     *
     * @return Die landesspezifische Beschreibung.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die landesspezifische Beschreibung.
     *
     * @param description Die landesspezifische Beschreibung.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gibt die <code>SystemLocale</code> für diese <code>ArticleDescription</code>
     * zurück.
     *
     * @return Die <code>SystemLocale</code> für diese <code>ArticleDescription</code>.
     */
    public SystemLocale getSystemLocale() {
        return systemLocale;
    }

    /**
     * Setzt die <code>SystemLocale</code> für diese <code>ArticleDescription</code>.
     *
     * @param systemLocale Die <code>SystemLocale</code> für diese
     *                     <code>ArticleDescription</code>.
     */
    public void setSystemLocale(SystemLocale systemLocale) {
        this.systemLocale = systemLocale;
    }

    /**
     * Es wird diese <code>ArticleDescription</code> mit dem eingehenden Objekt
     * auf Gleichheit überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ArticleDescription that = (ArticleDescription) o;

        if (this.getDescription() != null ? !this.getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        return !(this.getSystemLocale() != null ? !this.getSystemLocale().equals(that.getSystemLocale()) : that.getSystemLocale() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (this.getDescription() != null ? this.getDescription().hashCode() : 0);
        result = 29 * result + (this.getSystemLocale() != null ? this.getSystemLocale().hashCode() : 0);
        return result;
    }
}