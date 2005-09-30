package ecobill.util;

import ecobill.module.base.domain.SystemLocale;
import ecobill.util.exception.LocalizerException;

import java.util.Collection;
import java.util.Locale;

/**
 * Die Klasse <code>LocalizerUtils</code> ermöglicht es über das Interface <code>Localizable</code>
 * aus einer <code>Collection</code> ein landesspezifisches Objekt herauszufiltern.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 16:07:00
 *
 * @author Roman R&auml;dle
 * @version $Id: LocalizerUtils.java,v 1.3 2005/09/30 09:06:23 raedler Exp $
 * @since EcoBill 1.0
 */
public class LocalizerUtils {

    /**
     * Filtert aus einer <code>Collection</code> ein landesspezifisches Objekt heraus und
     * gibt dieses zurück. Zum Vergleich wird die Default <code>Locale</code> herangezogen.
     *
     * @param localizables Eine <code>Collection</code> die Objekte beinhaltet die das
     *                     Interface <code>Localizable</code> implementieren.
     * @return Gibt das landesspezifische Objekt zurück.
     */
    public static Object getLocalizedObject(Collection localizables) throws LocalizerException {

        /*
         * Hier wird das localizedObject abgelegt.
         */
        Object localizedObject;

        /*
         * Filtere das Objekt aus der <code>Collection</code> das der Default <code>Locale</code>
         * am meisten ähnelt.
         */
        localizedObject = localize(localizables, Locale.getDefault());

        /*
         * Wird kein Objekt gefunden das der Default <code>Locale</code> ähnelt wird eine
         * <code>LocalizerException</code> geworfen.
         */
        if (localizedObject == null) throw new LocalizerException("Es ist kein localizedObject in der Collection vorhanden.");

        return localizedObject;
    }

    /**
     * Filtert aus einer <code>Collection</code> ein landesspezifisches Objekt heraus und
     * gibt dieses zurück. Zum Vergleich wird die <code>Locale</code> herangezogen die als
     * Parameter übergeben wird.
     *
     * @param localizables Eine <code>Collection</code> die Objekte beinhaltet die das
     *                     Interface <code>Localizable</code> implementieren.
     * @param locale       Die <code>Locale</code> die einem Objekt in der <code>Collection</code>
     *                     ähneln muss.
     * @return Gibt das landesspezifische Objekt zurück.
     */
    public static Object getLocalizedObject(Collection localizables, Locale locale) throws LocalizerException {

        /*
         * Hier wird das localizedObject abgelegt.
         */
        Object localizedObject;

        /*
         * Filtere das Objekt aus der <code>Collection</code> das der <code>Locale</code>
         * am meisten ähnelt.
         */
        localizedObject = localize(localizables, locale);

        /*
         * Sollte noch kein Objekt gefunden worden sein das der <code>Locale</code> locale entspricht
         * wird das selbe nochmal mit der Default <code>Locale</code> versucht.
         */
        if (localizedObject == null) {
            localizedObject = getLocalizedObject(localizables);
        }

        return localizedObject;
    }

    /**
     * Gibt ein <code>Object</code> aus der <code>Collection</code> zurück, falls eines davon mit der
     * <code>Locale</code> übereinstimmt. Falls es keine Übereinstimmung mit einem <code>Object</code>
     * geben sollte wird eine <code>LocalizerException</code> geworfen.
     *
     * @param localizables Eine <code>Collection</code> die Objekte beinhaltet die das
     *                     Interface <code>Localizable</code> implementieren.
     * @param locale       Die <code>Locale</code> die einem Objekt in der <code>Collection</code>
     *                     exakt gleichen muss.
     * @return Gibt das landesspezifische Objekt zurück.
     * @throws LocalizerException Diese <code>LocalizerException</code> wird geworfen falls kein
     *                            passendes <code>Object</code> gefunden wurde.
     */
    public static Object getExactLocalizedObject(Collection localizables, Locale locale) throws LocalizerException {

        for (Object o : localizables) {

            if (o instanceof Localizable) {
                Localizable localizable = (Localizable) o;

                SystemLocale systemLocale = localizable.getSystemLocale();
                if (systemLocale.equalsLocale(locale)) {
                    return localizable;
                }
            }
        }

        throw new LocalizerException("Es ist kein localizedObject in der Collection vorhanden.");
    }

    /**
     * Diese Methode ermöglicht es einer <code>Collection</code> ein landesspezifisches Objekt
     * herauszufiltern, allerdings müssen die Objekte das Interface <code>Localzibale</code>
     * implementieren.
     *
     * @param localizables Eine <code>Collection</code> die Objekte beinhaltet die das
     *                     Interface <code>Localizable</code> implementieren.
     * @param locale       Die <code>Locale</code> die einem Objekt in der <code>Collection</code>
     *                     ähneln muss.
     * @return Gibt das landesspezifische Objekt zurück.
     */
    private static Object localize(Collection localizables, Locale locale) {

        Object localizedObject = null;

        /*
         * Die Priorität wird auf Maximum gesetzt.
         */
        int priority = Integer.MAX_VALUE;

        /*
         * Iteriere über die <code>Collection</code>.
         */
        for (Object o : localizables) {

            if (o instanceof Localizable) {
                Localizable localizable = (Localizable) o;

                SystemLocale systemLocale = localizable.getSystemLocale();

                int systemLocalePriority;
                if ((systemLocalePriority = systemLocale.compareTo(locale)) < priority) {
                    priority = systemLocalePriority;

                    localizedObject = o;
                }
            }
            else if (o instanceof SystemLocale) {
                SystemLocale systemLocale = (SystemLocale) o;

                int systemLocalePriority;
                if ((systemLocalePriority = systemLocale.compareTo(locale)) < priority) {
                    priority = systemLocalePriority;

                    localizedObject = o;
                }
            }
        }

        return localizedObject;
    }
}