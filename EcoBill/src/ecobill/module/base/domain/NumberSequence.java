package ecobill.module.base.domain;

import ecobill.util.NumberGenerator;

// @todo document me!

/**
 * NumberSequence.
 * <p/>
 * User: rro
 * Date: 05.11.2005
 * Time: 21:55:45
 *
 * @author Roman R&auml;dle
 * @version $Id: NumberSequence.java,v 1.1 2005/11/06 01:54:37 raedler Exp $
 * @since EcoBill 1.0
 */
public class NumberSequence extends AbstractDomain {

    private String key;

    private String number;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNextNumber() {
        return NumberGenerator.createNextZerofilledNumber(getNumber());
    }

    public int compareWithNumber(String number) {
        return getNumber().compareTo(number);
    }
}
