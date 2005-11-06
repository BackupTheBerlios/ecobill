package ecobill.module.base.ui.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.Message;


import javax.swing.*;
import java.util.Properties;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Die <code>MessageUI</code> erstellt das User Interface zur Eingabe von Nachrichten.
 * <p/>
 * User: Andreas Weiler
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Andreas Weiler
 * @version $Id: MessageUI.java,v 1.6 2005/11/06 17:00:27 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class MessageUI extends JPanel implements InitializingBean {

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k�nnen in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(MessageUI.class);

    /**
     * Der <code>ApplicationContext</code> beinhaltet alle Beans die darin angegeben sind
     * und erm�glicht wahlfreien Zugriff auf diese.
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

    /**
     * Enth�lt die Pfade an denen die bestimmten Objekte serialisiert werden
     * sollen.
     */
    private Properties serializeIdentifiers;

    /**
     * Gibt die Pfade, an denen die bestimmten Objekte serialisiert werden
     * sollen, zur�ck.
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
     * Erstellt eine neue MessageUI
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
     * newsOverview aus der Klasse news
     */
    private News newsOverview;

    /**
     * Initialisierung und Anordnung bzw. Gestaltung der ganzen Komponenten
     */

    public void initComponents() {

        newsOverview = new ecobill.module.base.ui.message.News(baseService);

        newsOverview.getOverviewVerticalButton().getButton1().addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                newMessage();
            }
        });

        newsOverview.getOverviewVerticalButton().getButton2().addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                if (checkIfFilled() == true) {

                    saveOrUpdateMessage();
                }

                newsOverview.getNewsTree().updateUI();
            }
        });

        newsOverview.getOverviewVerticalButton().getButton3().addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                deleteMessage();
                newsOverview.getNewsTree().updateUI();
            }
        });

        newsOverview.getOverviewVerticalButton().getButton4().addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                refreshTree();
            }
        });


        this.add(newsOverview, BorderLayout.CENTER);

    }

     /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        newsOverview.reinitI18N();

    }

    /**
     * Methode um eine Nachricht abzuspeichern oder eine Nachricht zu �ndern wird aufgerufen
     * aus Klasse News
     */
    public void saveOrUpdateMessage() {
        Message message = new Message();

        message.setAddresser(newsOverview.getAddresserTextField().getText());
        message.setSubject(newsOverview.getSubjectTextField().getText());
        message.setMessage(newsOverview.getNewsTextArea().getText());


        baseService.saveOrUpdate(message);

        newsOverview.addMessageToTree(message);
    }

    /**
     * Methode um eine Nachricht zu l�schen wird aufgerufen aus Klasse News
     */
    public void deleteMessage() {

        newsOverview.deleteMessage();

    }

    /**
     * Methode um den Baum zu aktualisieren wird aufgerufen aus Klasse News
     */
    public void refreshTree() {

        newsOverview.refreshTree();
    }

    /**
     * Methode um eine neue Nachricht zu generieren wird aufgerufen aus Klasse News
     */
    public void newMessage() {

        newsOverview.newMessage();
    }

    /**
     * Methode um zu �berpr�fen ob die Textfelder ausgef�llt sind
     * wenn Textfelder gef�llt kommt boolean true zur�ck, sonst false
     */
    public boolean checkIfFilled() {

        String addresserCheck = newsOverview.getAddresserTextField().getText();
        String subjectCheck = newsOverview.getSubjectTextField().getText();


        if (addresserCheck.equals("") && subjectCheck.equals("")) {
            JOptionPane.showMessageDialog(newsOverview, "Bitte Absender und Betreff eingeben", "Information", 1);
        } else {

            if (addresserCheck.equals("")) {
                JOptionPane.showMessageDialog(newsOverview, "Bitte Absender eingeben", "Information", 1);
            }

            if (subjectCheck.equals("")) {
                JOptionPane.showMessageDialog(newsOverview, "Bitte Betreff eingeben", "Information", 1);
            }
        }

        if (!addresserCheck.equals("") && !subjectCheck.equals("")) {
            return true;
        }

        return false;

    }
}
