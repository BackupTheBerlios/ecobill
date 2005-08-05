package ecobill.module.base.ui;

import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;

import ecobill.module.base.service.BaseService;

import java.awt.*;

// @todo document me!

/**
 * BusinessPartnerUI.
 * <p/>
 * User: rro
 * Date: 05.08.2005
 * Time: 14:20:07
 *
 * @author Roman R&auml;dle
 * @version $Id: BusinessPartnerUI.java,v 1.2 2005/08/05 12:35:16 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class BusinessPartnerUI extends JPanel /*JInternalFrame*/ implements InitializingBean {


    private static BusinessPartnerUI singelton = null;
    /**
     * Gibt die einzigste Instanz der <code>ArticleUI</code> zurück um diese
     * dann bspw im Hauptfenster anzeigen zu können.
     *
     * @return Die <code>ArticleUI</code> ist abgeleitet von <code>JInternalFrame</code>
     *         und kann auf einer <code>JDesktopPane</code> angezeigt werden.
     */
    public static BusinessPartnerUI getInstance() {
        if (singelton == null) {
            singelton = new BusinessPartnerUI();
        }
        return singelton;
    }
    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;

    public BusinessPartnerUI() {
        super();
    }

    public void afterPropertiesSet() throws Exception {
        /*
         * Es wird die Größe, das Layout und verschiedenste Optionen gesetzt.
         */
        this.setSize(new Dimension(870, 525));
        this.setMinimumSize(new Dimension(870, 325));
        this.setLayout(new BorderLayout());
        this.tabbedPane();

    }

    /**
     * Gibt den <code>BaseService</code> und somit die Business Logik zurück.
     *
     * @return Der <code>BaseService</code>.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Setzt den <code>BaseService</code> der die komplette Business Logik enthält
     * um bspw Daten aus der Datenbank zu laden und dorthin auch wieder abzulegen.
     *
     * @param baseService Der <code>BaseService</code>.
     */
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    public void tabbedPane() {
        JTabbedPane jt = new JTabbedPane();
        JComponent tab0 = new JPanel(new BorderLayout());
        JComponent tab1 = new JPanel();

        jt.add(tab0, "Kundendaten");
        jt.add(tab1, "Kundenbestellungen");

        this.add(jt);
    }


    public static void main(String[] args) {
        BusinessPartnerUI panel = new BusinessPartnerUI();
        try {
            panel.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
