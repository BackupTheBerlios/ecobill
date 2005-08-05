package ecobill.module.base.ui;

import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import java.awt.*;

import ecobill.module.base.service.BaseService;

/**
 * Created by IntelliJ IDEA.
 * User: Paul Chef
 * Date: 05.08.2005
 * Time: 13:57:10
 * To change this template use File | Settings | File Templates.
 */
public class CustomerUI extends JPanel implements InitializingBean {

        /**
     * Die <code>CustomerUI</code> stellt ein Singleton dar, da es immer nur eine
     * Instanz pro Arbeitsplatz geben kann.
     * -> spart kostbare Ressourcen.
     */
    private static CustomerUI singelton = null;

    /**
     * Gibt die einzigste Instanz der <code>CustomerUI</code> zur�ck um diese
     * dann bspw im Hauptfenster anzeigen zu k�nnen.
     *
     * @return Die <code>CustomerUI</code> ist abgeleitet von <code>JInternalFrame</code>
     *         und kann auf einer <code>JDesktopPane</code> angezeigt werden.
     */
    public static CustomerUI getInstance() {
        if (singelton == null) {
            singelton = new CustomerUI();
        }
        return singelton;
    }

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;

    /**
     * Standard Konstruktor.
     */
    public CustomerUI() {
        super();
    }
    /**
     * Globale Initialisierung des Artikel User Interfaces.
     * Es wird die Gr��e, minimale Gr��e des Fensters,... gesetzt.
     * Diese wird nach den gesetzten Properties des <code>ApplicationContext</code>
     * durchgef�hrt.
     */
    public void afterPropertiesSet() {

        /*
         * Es wird die Gr��e, das Layout und verschiedenste Optionen gesetzt.
         */
        this.setSize(new Dimension(870, 525));
        this.setMinimumSize(new Dimension(870, 325));
        this.setLayout(new BorderLayout());
    }

        /**
     * Gibt den <code>BaseService</code> und somit die Business Logik zur�ck.
     *
     * @return Der <code>BaseService</code>.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Setzt den <code>BaseService</code> der die komplette Business Logik enth�lt
     * um bspw Daten aus der Datenbank zu laden und dorthin auch wieder abzulegen.
     *
     * @param baseService Der <code>BaseService</code>.
     */
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }
}
