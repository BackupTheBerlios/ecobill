package ecobill.module.base.ui.start;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.Message;
import ecobill.core.util.IdKeyItem;


import javax.swing.*;
import java.util.Properties;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Die <code>BusinessPartnerUI</code> erstellt das User Interface zur Eingabe von Benutzerdaten.
 * <p/>
 * User: aw
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Andreas Weiler
 * @version $Id: StartUI.java,v 1.14 2005/10/07 09:58:07 raedler Exp $
 * @since EcoBill 1.0
 */
public class StartUI extends JPanel implements InitializingBean {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(StartUI.class);

    /**
     * Der <code>ApplicationContext</code> beinhaltet alle Beans die darin angegeben sind
     * und ermöglicht wahlfreien Zugriff auf diese.
     */
    protected ApplicationContext applicationContext;

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;

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

    /**
     * Enthält die Pfade an denen die bestimmten Objekte serialisiert werden
     * sollen.
     */
    private Properties serializeIdentifiers;

    /**
     * Gibt die Pfade, an denen die bestimmten Objekte serialisiert werden
     * sollen, zurück.
     *
     * @return Die Pfade an denen die bestimmten Objekte serialisiert werden
     *         sollen.
     */
    public Properties getSerializeIdentifiers() {
        return serializeIdentifiers;
    }

    /**
     * Setzt die Pfade, an denen die bestimmten Objekte serialisiert werden
     * sollen.
     *
     * @param serializeIdentifiers Die Pfade an denen die bestimmten Objekte
     *                             serialisiert werden sollen.
     */
    public void setSerializeIdentifiers(Properties serializeIdentifiers) {
        this.serializeIdentifiers = serializeIdentifiers;
    }

    /**
     * Creates new form BusinessPartnerUI
     */
    public void afterPropertiesSet() {

        // Initialisieren der Komponenten und des Layouts.
        this.setLayout(new BorderLayout());
        initComponents();

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
       /**try {
            overviewBusinessPartnerTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("business_partner_table")));
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage(), fnfe);
            }
        }*/

        reinitI18N();
    }

    /**
     * Alle Komponenten die man braucht
     */

    private News newsOverview;

    public void initComponents() {

        newsOverview = new ecobill.module.base.ui.start.News(baseService);

        newsOverview.getOverviewVerticalButton().getButton2().addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                saveOrUpdateMessage();

                newsOverview.getjTree1().updateUI();
                newsOverview.getjTree1().repaint();
            }
        });

        newsOverview.getOverviewVerticalButton().getButton3().addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                deleteMessage();
            }
        });


        this.add(newsOverview, BorderLayout.CENTER);

    }

    public void reinitI18N() {

        newsOverview.reinitI18N();

    }


    public void saveOrUpdateMessage() {
        Message message = new Message();

        message.setAddresser(newsOverview.getjTextField1().getText());
        message.setSubject(newsOverview.getjTextField2().getText());
        message.setMessage(newsOverview.getjTextArea1().getText());


        baseService.saveOrUpdate(message);
    }

    public void deleteMessage() {

    }

}
