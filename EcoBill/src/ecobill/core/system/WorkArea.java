package ecobill.core.system;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.beans.BeansException;

import java.util.Locale;

import ecobill.core.system.exception.WorkAreaNotFoundException;

/**
 * Die <code>WorkArea</code> ermöglicht diverse Einstellungen für einen Arbeitsplatz
 * (Benutzer). Es wird auch eine Methode angeboten um Werte aus einem
 * <code>ResourceBundle</code> auszulesen.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 16:41:40
 *
 * @author Roman R&auml;dle
 * @version $Id: WorkArea.java,v 1.7 2005/09/12 20:42:58 raedler Exp $
 * @since EcoBill 1.0
 */
public final class WorkArea implements ApplicationContextAware {

    /**
     * Der <code>ApplicationContext</code> ermöglicht den Zugang zu allen erzeugten
     * Beans.
     */
    private static ApplicationContext ac = null;

    /**
     * Die, für diesen Arbeitsplatz - Benutzer - , eingestellte <code>Locale</code>.
     */
    private static Locale locale = null;

    /**
     * Setzt den <code>ApplicationContext</code>, der alle instanziierten Beans enthält.
     *
     * @param applicationContext Der <code>ApplicationContext</code> enthält alle instanziierten Beans.
     * @throws BeansException Sollte beim Setzen des <code>ApplicationContext</code> ein Fehler auftreten
     *                        wird eine Exception geworfen.
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    /**
     * Gibt die, für diesen Arbeitsplatz, eingestellte <code>Locale</code> zurück. Sollte
     * keine <code>Locale</code> gesetzt sein wird die Default <code>Locale</code> zurückgegeben.
     *
     * @return Die, für diesen Arbeitsplatz, eingestellte <code>Locale</code>.
     */
    public static Locale getLocale() {
        if (locale == null) return Locale.getDefault();
        return locale;
    }

    /**
     * Setzt die <code>Locale</code>, für diesen Arbeitsplatz - Benutzer.
     *
     * @param locale Die, für diesen Arbeitsplatz, eingestellte <code>Locale</code>.
     */
    public static void setLocale(Locale locale) {
        WorkArea.locale = locale;
    }

    /**
     * Gibt den zu einem Schlüssel zugehörigen Wert aus einem <code>ResourceBundle</code>
     * zurück. Wird dieser Schlüssel in keinem <code>ResourceBundle</code> gefunden, so
     * wird einfach der Default Wert zurückgeliefert.
     *
     * @param key            Der Schlüssel der den Wert enthält.
     * @param defaultMessage Dieser Wert wird zurückgeliefert wenn der Schlüssel in keinem
     *                       <code>ResourceBundle</code> gefunden wird.
     * @return Der zu einem Schlüssel zugehörige Wert.
     * @see ApplicationContext#getMessage(String, Object[], String, java.util.Locale)
     */
    public static String getMessage(String key, String defaultMessage) {
        return ac.getMessage(key, null, defaultMessage, locale);
    }

    /**
     * Gibt den zu einem Schlüssel zugehörigen Wert aus einem <code>ResourceBundle</code>
     * zurück. Wird dieser Schlüssel in keinem <code>ResourceBundle</code> gefunden, so
     * wird einfach der Key zurückgeliefert.
     *
     * @param key Der Schlüssel der den Wert enthält.
     * @return Der zu einem Schlüssel zugehörige Wert.
     * @see ApplicationContext#getMessage(String, Object[], String, java.util.Locale)
     */
    public static String getMessage(String key) {
        try {
            return ac.getMessage(key, null, locale);
        }
        catch (NoSuchMessageException nsme) {
            return "??? " + key + " ???";
        }
    }
}
