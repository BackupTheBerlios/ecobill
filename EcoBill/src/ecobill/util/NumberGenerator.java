package ecobill.util;

/**
 * Der <code>NumberGenerator</code> ermöglicht es Operationen auf Nummern auszuführen. Bspw ist es
 * möglich aus einem mit Nullen gefüllten Zahlenstring die nächste fortlaufende Nummer zu erzeugen.
 * <p/>
 * User: rro
 * Date: 25.10.2005
 * Time: 09:50:57
 *
 * @author Roman R&auml;dle
 * @version $Id: NumberGenerator.java,v 1.1 2005/10/25 08:07:55 raedler Exp $
 * @since EcoBill 1.0
 */
public class NumberGenerator {

    /**
     * Es wird ein mit Nullen gefüllter Zahlenstring in die nächste fortlaufende,
     * mit Nullen gefüllte, Zahl erweitert. Es kann hier die Länge der rückkehrenden
     * Nummer angegeben werden.
     *
     * @param actualNumber Die aktuelle, mit Nullen gefüllte, Nummer.
     * @param length Die Länge der Nummer die zurückgegeben wird.
     * @return Die nächste fortlaufende, mit Nullen gefüllte, Nummer.
     */
    public static String createNextZerofilledNumber(String actualNumber, int length) {

        Long number = Long.parseLong(actualNumber);

        number++;

        String zerofilled = number.toString();

        for (int i = zerofilled.length(); i < length; i++) {
            zerofilled = "0" + zerofilled;
        }

        return zerofilled;
    }

    /**
     * Es wird ein mit Nullen gefüllter Zahlenstring in die nächste fortlaufende,
     * mit Nullen gefüllte, Zahl erweitert.
     *
     * @param actualNumber Die aktuelle, mit Nullen gefüllte, Nummer.
     * @return Die nächste fortlaufende, mit Nullen gefüllte, Nummer.
     */
    public static String createNextZerofilledNumber(String actualNumber) {

        int length = actualNumber.length();

        return createNextZerofilledNumber(actualNumber, length);
    }
}