package ecobill.module.base.domain;

import java.text.Collator;

/**
 * Das <code>Banking</code> Objekt beinhaltet alle nötigen Daten zu einer
 * Bankverbindung.
 * <p/>
 * User: rro
 * Date: 21.07.2005
 * Time: 13:37:24
 *
 * @author Roman R&auml;dle
 * @version $Id: Banking.java,v 1.3 2006/01/30 23:43:13 raedler Exp $
 * @since EcoBill 1.0
 */
public class Banking extends AbstractDomain {

    /**
     * Der Name des Bankinstitutes.
     * <br/>
     * Bspw. Sparkasse Konstanz.
     */
    private String bankEstablishment;

    /**
     * Die Kontonummer.
     * <br/>
     * Bspw. 265795
     */
    private String accountNumber;

    /**
     * Die Bankleitzahl.
     * <br/>
     * Bspw. 50535045
     */
    private String bankIdentificationNumber;

    /**
     * Gibt den Namen des Bankinstitutes zurück.
     *
     * @return Der Name des Bankinstitutes.
     */
    public String getBankEstablishment() {
        return bankEstablishment;
    }

    /**
     * Setzt den Namen des Bankinstitutes.
     *
     * @param bankEstablishment Der Name des Bankinstitutes.
     */
    public void setBankEstablishment(String bankEstablishment) {
        this.bankEstablishment = bankEstablishment;
    }

    /**
     * Gibt die Kontonummer zurück.
     *
     * @return Die Kontonummer.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Setzt die Kontonummer.
     *
     * @param accountNumber Die Kontonummer.
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gibt die Bankleitzahl zurück.
     *
     * @return Die Bankleitzahl.
     */
    public String getBankIdentificationNumber() {
        return bankIdentificationNumber;
    }

    /**
     * Setzt die Bankleitzahl.
     *
     * @param bankIdentificationNumber Die Bankleitzahl.
     */
    public void setBankIdentificationNumber(String bankIdentificationNumber) {
        this.bankIdentificationNumber = bankIdentificationNumber;
    }

    /**
     * Es wird dieses <code>Banking</code> mit dem eingehenden Objekt auf
     * Gleichheit überprüft.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Banking banking = (Banking) o;

        if (this.getAccountNumber() != null ? !this.getAccountNumber().equals(banking.getAccountNumber()) : banking.getAccountNumber() != null)
            return false;
        if (this.getBankEstablishment() != null ? !this.getBankEstablishment().equals(banking.getBankEstablishment()) : banking.getBankEstablishment() != null)
            return false;
        return !(this.getBankIdentificationNumber() != null ? !this.getBankIdentificationNumber().equals(banking.getBankIdentificationNumber()) : banking.getBankIdentificationNumber() != null);
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        int result;
        result = (this.getBankEstablishment() != null ? this.getBankEstablishment().hashCode() : 0);
        result = 29 * result + (this.getAccountNumber() != null ? this.getAccountNumber().hashCode() : 0);
        result = 29 * result + (this.getBankIdentificationNumber() != null ? this.getBankIdentificationNumber().hashCode() : 0);
        return result;
    }
}