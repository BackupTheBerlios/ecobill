package ecobill.core.system;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.beans.BeansException;

import java.util.Locale;

import ecobill.core.system.exception.WorkAreaNotFoundException;

/**
 * Die <code>WorkArea</code> erm�glicht diverse Einstellungen f�r einen Arbeitsplatz
 * (Benutzer). Es wird auch eine Methode angeboten um Werte aus einem
 * <code>ResourceBundle</code> auszulesen.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 16:41:40
 *
 * @author Roman R&auml;dle
 * @version $Id: WorkArea.java,v 1.4 2005/08/08 21:03:51 raedler Exp $
 * @since EcoBill 1.0
 */
public final class WorkArea implements ApplicationContextAware {

    /**
     * Jeder <code>Thread</code> h�lt implizit eine Kopie der
     * <code>WorkArea</code>.
     */
    private static ThreadLocal<WorkArea> workAreaHolder = new ThreadLocal<WorkArea>();

    /**
     * Der <code>ApplicationContext</code> erm�glicht den Zugang zu allen erzeugten
     * Beans.
     */
    private static ApplicationContext ac = null;

    /**
     * Die, f�r diesen Arbeitsplatz - Benutzer - , eingestellte <code>Locale</code>.
     */
    private static Locale locale = null;

    /**
     * Setzt die <code>WorkArea</code> im workAreaHolder und somit an den
     * <code>ThreadLocal</code>.
     *
     * @param workArea Setzt die <code>WorkArea</code> in den <code>ThreadLocal</code>.
     */
    public static void setWorkArea(WorkArea workArea) {
        workAreaHolder.set(workArea);
    }

    /**
     * Gibt die an den <code>ThreadLocal</code> gebundene <code>WorkArea</code>
     * zur�ck.
     *
     * @return Die an den <code>ThreadLocal</code> gebundene <code>WorkArea</code>
     *         Instanz.
     * @throws WorkAreaNotFoundException Wird geworfen wenn keine <code>WorkArea</code>
     *                                   an den aktuellen <code>Thread</code> gebunden wurde.
     */
    public static WorkArea getWorkArea() throws WorkAreaNotFoundException {
        WorkArea workArea = workAreaHolder.get();
        if (workArea == null)
            throw new WorkAreaNotFoundException("Die WorkArea ist nicht an den aktuellen Thread gebunden.");

        return workArea;
    }

    /**
     * Gibt die <code>WorkArea</code> wieder frei.
     */
    public static void release() {
        setWorkArea(null);
    }

    /**
     * Setzt den <code>ApplicationContext</code>, der alle instanziierten Beans enth�lt.
     *
     * @param applicationContext Der <code>ApplicationContext</code> enth�lt alle instanziierten Beans.
     * @throws BeansException Sollte beim Setzen des <code>ApplicationContext</code> ein Fehler auftreten
     *                        wird eine Exception geworfen.
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    /**
     * Gibt die, f�r diesen Arbeitsplatz, eingestellte <code>Locale</code> zur�ck. Sollte
     * keine <code>Locale</code> gesetzt sein wird die Default <code>Locale</code> zur�ckgegeben.
     *
     * @return Die, f�r diesen Arbeitsplatz, eingestellte <code>Locale</code>.
     */
    public static Locale getLocale() {
        if (locale == null) return Locale.getDefault();
        return locale;
    }

    /**
     * Setzt die <code>Locale</code>, f�r diesen Arbeitsplatz - Benutzer.
     *
     * @param locale Die, f�r diesen Arbeitsplatz, eingestellte <code>Locale</code>.
     */
    public static void setLocale(Locale locale) {
        WorkArea.locale = locale;
    }

    /**
     * Gibt den zu einem Schl�ssel zugeh�rigen Wert aus einem <code>ResourceBundle</code>
     * zur�ck. Wird dieser Schl�ssel in keinem <code>ResourceBundle</code> gefunden, so
     * wird einfach der Default Wert zur�ckgeliefert.
     *
     * @param key            Der Schl�ssel der den Wert enth�lt.
     * @param defaultMessage Dieser Wert wird zur�ckgeliefert wenn der Schl�ssel in keinem
     *                       <code>ResourceBundle</code> gefunden wird.
     * @return Der zu einem Schl�ssel zugeh�rige Wert.
     * @see ApplicationContext#getMessage(String, Object[], String, java.util.Locale)
     * @deprecated Please use the non static {@link ecobill.core.system.WorkArea#getWorkArea()#getMessage(String, String)}
     */
    public static String getStaticMessage(String key, String defaultMessage) {
        return ac.getMessage(key, null, defaultMessage, locale);
    }

    /**
     * Gibt den zu einem Schl�ssel zugeh�rigen Wert aus einem <code>ResourceBundle</code>
     * zur�ck. Wird dieser Schl�ssel in keinem <code>ResourceBundle</code> gefunden, so
     * wird einfach der Default Wert zur�ckgeliefert.
     *
     * @param key            Der Schl�ssel der den Wert enth�lt.
     * @param defaultMessage Dieser Wert wird zur�ckgeliefert wenn der Schl�ssel in keinem
     *                       <code>ResourceBundle</code> gefunden wird.
     * @return Der zu einem Schl�ssel zugeh�rige Wert.
     * @see ApplicationContext#getMessage(String, Object[], String, java.util.Locale)
     */
    public static String getMessage(String key, String defaultMessage) {
        return ac.getMessage(key, null, defaultMessage, locale);
    }

    /**
     * Gibt den zu einem Schl�ssel zugeh�rigen Wert aus einem <code>ResourceBundle</code>
     * zur�ck. Wird dieser Schl�ssel in keinem <code>ResourceBundle</code> gefunden, so
     * wird einfach der Key zur�ckgeliefert.
     *
     * @param key Der Schl�ssel der den Wert enth�lt.
     * @return Der zu einem Schl�ssel zugeh�rige Wert.
     * @see ApplicationContext#getMessage(String, Object[], String, java.util.Locale)
     * @deprecated Please use the non static {@link ecobill.core.system.WorkArea#getWorkArea()#getMessage(String)}
     */
    public static String getStaticMessage(String key) {
        try {
            return ac.getMessage(key, null, locale);
        }
        catch (NoSuchMessageException nsme) {
            return "??? " + key + " ???";
        }
    }

    /**
     * Gibt den zu einem Schl�ssel zugeh�rigen Wert aus einem <code>ResourceBundle</code>
     * zur�ck. Wird dieser Schl�ssel in keinem <code>ResourceBundle</code> gefunden, so
     * wird einfach der Key zur�ckgeliefert.
     *
     * @param key Der Schl�ssel der den Wert enth�lt.
     * @return Der zu einem Schl�ssel zugeh�rige Wert.
     * @see ApplicationContext#getMessage(String, Object[], String, java.util.Locale)
     */
    public String getMessage(String key) {
        try {
            return ac.getMessage(key, null, locale);
        }
        catch (NoSuchMessageException nsme) {
            return "??? " + key + " ???";
        }
    }
}