package ecobill.module.base.domain;

import java.text.Collator;

/**
 * Die <code>Address</code> beinhaltet alle Informationen die für eine Adresse von Nöten
 * sind. Desweiteren ist diese Klasse von <code>AbstractDomain</code> abgeleitet und kann
 * somit in einer Datenbank abgelegt werden.
 * <p/>
 * User: rro
 * Date: 21.07.2005
 * Time: 13:33:34
 *
 * @author Roman R&auml;dle
 * @version $Id: Address.java,v 1.2 2005/09/28 15:44:18 raedler Exp $
 * @since EcoBill 1.0
 */
public class Address extends AbstractDomain {

    /**
     * Die Straße incl. der Hausnummer.
     */
    private String street;

    /**
     * Die Postleitzahl.
     */
    private String zipCode;

    /**
     * Die Stadt.
     */
    private String city;

    /**
     * Das Bundesland oder der Bundesstaat.
     */
    private String county;

    /**
     * Das Land.
     */
    private String country;

    /**
     * Gibt die Straße der Adresse zurück.
     *
     * @return Die Straße der Adresse.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Setzt die Straße der Adresse.
     *
     * @param street Die Straße der Adresse.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gibt die Postleitzahl der Adresse zurück.
     *
     * @return Die Postleitzahl der Adresse.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Setzt die Postleitzahl der Adresse.
     *
     * @param zipCode Die Postleitzahl der Adresse.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gibt die Stadt der Adresse zurück.
     *
     * @return Die Stadt der Adresse.
     */
    public String getCity() {
        return city;
    }

    /**
     * Setzt die Stadt der Adresse.
     *
     * @param city Die Stadt der Adresse.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gibt das Bundesland oder den Bundesstaat der Adresse zurück.
     *
     * @return Das Bundesland oder der Bundesstaat der Adresse.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Setzt das Bundesland oder den Bundesstaat der Adresse.
     *
     * @param county Das Bundesland oder der Bundesstaat der Adresse.
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * Gibt das Land der Adresse zurück.
     *
     * @return Das Land der Adresse.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setzt das Land der Adresse.
     *
     * @param country Das Land der Adresse.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Es wird diese <code>Address</code> mit dem eingehenden Objekt auf
     * Gleichheit überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Address address = (Address) o;

        if (this.getCity() != null ? !this.getCity().equals(address.getCity()) : address.getCity() != null) return false;
        if (this.getCountry() != null ? !this.getCountry().equals(address.getCountry()) : address.getCountry() != null) return false;
        if (this.getCounty() != null ? !this.getCounty().equals(address.getCounty()) : address.getCounty() != null) return false;
        if (this.getStreet() != null ? !this.getStreet().equals(address.getStreet()) : address.getStreet() != null) return false;
        return !(this.getZipCode() != null ? !this.getZipCode().equals(address.getZipCode()) : address.getZipCode() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (this.getStreet() != null ? this.getStreet().hashCode() : 0);
        result = 29 * result + (this.getZipCode() != null ? this.getZipCode().hashCode() : 0);
        result = 29 * result + (this.getCity() != null ? this.getCity().hashCode() : 0);
        result = 29 * result + (this.getCounty() != null ? this.getCounty().hashCode() : 0);
        result = 29 * result + (this.getCountry() != null ? this.getCountry().hashCode() : 0);
        return result;
    }
}