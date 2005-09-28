package ecobill.module.base.domain;

import java.text.Collator;

/**
 * Das <code>Person</code> Objekt beinhaltet alle für eine Person spezifischen Daten.
 * <p/>
 * User: rro
 * Date: 21.07.2005
 * Time: 13:31:22
 *
 * @author Roman R&auml;dle
 * @version $Id: Person.java,v 1.2 2005/09/28 15:44:18 raedler Exp $
 * @since EcoBill 1.0
 */
public class Person extends AbstractDomain {

    /**
     * Der Schlüssel einer Anrede.
     * <br/>
     * Bspw. "mr" für "Herr"
     */
    private String titleKey;

    /**
     * Der Schlüssel eines akademischen Titels.
     * <br/>
     * Bspw. "prof_dr" für "Prof. Dr.".
     */
    private String academicTitleKey;

    /**
     * Der Vorname der Person.
     */
    private String firstname;

    /**
     * Der Nachname der Person.
     */
    private String lastname;

    /**
     * Die Telefonnummer der Person.
     */
    private String phone;

    /**
     * Die Faxnummer der Person.
     */
    private String fax;

    /**
     * Die E-Mail Adresse der Person.
     */
    private String email;

    /**
     * Die Adresse der Person.
     */
    private Address address;

    /**
     * Gibt den Schlüssel für die Anrede zurück.
     *
     * @return Der Schlüssel für die Anrede.
     */
    public String getTitleKey() {
        return titleKey;
    }

    /**
     * Setzt den Schlüssel für die Anrede.
     *
     * @param titleKey Der Schlüssel für die Anrede.
     */
    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    /**
     * Gibt den Schlüssel für die Anrede, die in Briefen verwendet wird,
     * zurück.
     *
     * @return Der Schlüssel für die Anrede, die in Briefen verwendet wird.
     */
    public String getLetterTitleKey() {
        return titleKey + "_letter";
    }

    /**
     * Gibt den Schlüssel für den akademischen Titel zurück.
     *
     * @return Der Schlüssel für den akademischen Titel.
     */
    public String getAcademicTitleKey() {
        return academicTitleKey;
    }

    /**
     * Setzt den Schlüssel für den akademischen Titel.
     *
     * @param academicTitleKey Der Schlüssel für den akademischen Titel.
     */
    public void setAcademicTitleKey(String academicTitleKey) {
        this.academicTitleKey = academicTitleKey;
    }

    /**
     * Gibt den Vornamen der Person zurück.
     *
     * @return Der Vorname der Person.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setzt den Vornamen der Person.
     *
     * @param firstname Der Vorname der Person.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gibt den Nachnamen der Person zurück.
     *
     * @return Der Nachname der Person.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setzt den Nachnamen der Person.
     *
     * @param lastname Der Nachname der Person.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gibt die Telefonnummer der Person zurück.
     *
     * @return Die Telefonnummer der Person.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setzt die Telefonnummer der Person.
     *
     * @param phone Die Telefonnummer der Person.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gibt die Telefaxnummer der Person zurück.
     *
     * @return Die Telefaxnummer der Person.
     */
    public String getFax() {
        return fax;
    }

    /**
     * Setzt die Telefaxnummer der Person.
     *
     * @param fax Die Telefaxnummer der Person.
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Gibt die E-Mail Adresse der Person zurück.
     *
     * @return Die E-Mail Adresse der Person.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setzt die E-Mail Adresse der Person.
     *
     * @param email Die E-Mail Adresse der Person.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gibt eine <code>Address</code> zurück. Diese beinhaltet bspw. Straße,
     * Postleitzahl, Stadt, Bundesland oder Bundesstaat und Land.
     *
     * @return Die Adresse in einem <code>Address</code> Objekt.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Setzt die Adresse der Person. Dazu wird ein <code>Address</code>
     * Objekt benötigt.
     *
     * @param address Die Adresse in einem <code>Address</code> Objekt.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Es wird diese <code>Person</code> mit dem eingehenden Objekt auf
     * Gleichheit überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Person person = (Person) o;

        if (this.getAcademicTitleKey() != null ? !this.getAcademicTitleKey().equals(person.getAcademicTitleKey()) : person.getAcademicTitleKey() != null) return false;
        if (this.getAddress() != null ? !this.getAddress().equals(person.getAddress()) : person.getAddress() != null) return false;
        if (this.getEmail() != null ? !this.getEmail().equals(person.getEmail()) : person.getEmail() != null) return false;
        if (this.getFax() != null ? !this.getFax().equals(person.getFax()) : person.getFax() != null) return false;
        if (this.getFirstname() != null ? !this.getFirstname().equals(person.getFirstname()) : person.getFirstname() != null) return false;
        if (this.getLastname() != null ? !this.getLastname().equals(person.getLastname()) : person.getLastname() != null) return false;
        if (this.getPhone() != null ? !this.getPhone().equals(person.getPhone()) : person.getPhone() != null) return false;
        return !(this.getTitleKey() != null ? !this.getTitleKey().equals(person.getTitleKey()) : person.getTitleKey() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (this.getTitleKey() != null ? this.getTitleKey().hashCode() : 0);
        result = 29 * result + (this.getAcademicTitleKey() != null ? this.getAcademicTitleKey().hashCode() : 0);
        result = 29 * result + (this.getFirstname() != null ? this.getFirstname().hashCode() : 0);
        result = 29 * result + (this.getLastname() != null ? this.getLastname().hashCode() : 0);
        result = 29 * result + (this.getPhone() != null ? this.getPhone().hashCode() : 0);
        result = 29 * result + (this.getFax() != null ? this.getFax().hashCode() : 0);
        result = 29 * result + (this.getEmail() != null ? this.getEmail().hashCode() : 0);
        result = 29 * result + (this.getAddress() != null ? this.getAddress().hashCode() : 0);
        return result;
    }
}