package ecobill.module.base.ui.bill;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import ecobill.module.base.service.BaseService;

import ecobill.module.base.ui.component.OverviewPanel;

import ecobill.core.ui.MainFrame;
import ecobill.core.system.Internationalization;

import javax.swing.*;
import java.util.Properties;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Die <code>BillOverviewPanel</code> enthält die Tabelle der Rechnungen, um sie dann als Report anzeigen zu können
 * <p/>
 * User: sega
 * Date: 22.10.2005
 * Time: 17:49:23
 *
 * @author Sebastian Gath
 * @version $Id: BillOverviewPanel.java,v 1.4 2005/11/06 23:32:32 raedler Exp $
 * @since EcoBill 1.0
 */
public class BillOverviewPanel extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(BillCreation.class);

    /**
     * Tabelle aller Rechnungen
     */
    private BillTable billTable;

    /**
     * aktuelle BusinesspartnerId
     */
    private long actualBusinessPartnerId;

    /**
     * Panel, in dem der Jasperviewer aufgenommen wird, zum Darstellen der Rechnungen
     */
    private BillRightPanel billRightPanel;

    /**
     * Mainframe der Awendung
     */
    private MainFrame mainFrame;

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

    public BillOverviewPanel(BaseService baseService, MainFrame mainFrame) {
        this.baseService = baseService;
        this.mainFrame = mainFrame;
        initComponents();
        initLayout();
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() {

        // Initialisieren der Komponenten und des Layouts.
        initComponents();
        initLayout();

/*        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        try {
            deliveryOrderTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("delivery_order_table")));
            articleTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("article_table")));
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage(), fnfe);
            }
        }
  */
        reinitI18N();
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        if (LOG.isInfoEnabled()) {
            LOG.info("Schließe BillOverviewPanel und speichere die Daten.");
        }
        /*
                // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
                // zu können.
                deliveryOrderTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
                articleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        */    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        billTable = new BillTable(actualBusinessPartnerId, baseService);
        billRightPanel = new BillRightPanel(false);
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        this.setLayout(new BorderLayout());

        // zum Anordnen der Rechnungstabelle und des Vorschaufensters für die Rechnungen
        OverviewPanel billOverview = new OverviewPanel(baseService, billTable, billRightPanel);

        ActionListener actionListener = new ActionListener() {

            /**
             * Erzeugt einen Jasperreport mit den Rechnungsdaten
             *
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */

            public void actionPerformed(ActionEvent e) {

                try {
                    // Datensetzen für den Rechnungsreport
                    billRightPanel.setMainFrame(mainFrame);
                    billRightPanel.setBaseService(baseService);
                    billRightPanel.doJasper(billTable.getIdOfSelectedRow());
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        ActionListener actionListenerClose = new ActionListener() {
            /**
             * den Jasperviewer wieder entfernen
             *
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */

            public void actionPerformed(ActionEvent e) {
                try {

                    billRightPanel.removeAll();
                    billRightPanel.repaint();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        billOverview.addButtonToVerticalButton(1, new ImageIcon("images/open.png"), "Rechnung in Vorschaufernster anzeigen", actionListener);
        billOverview.addButtonToVerticalButton(2, new ImageIcon("images/exit.png"), "Vorschaufernster schließen", actionListenerClose);

        add(billOverview, BorderLayout.CENTER);
    }


    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

    }


    /**
     * Setzen der aktuellen BusinessparterId und neu Laden der Rechnungen
     *
     * @param actualBusinessPartnerId
     */
    public void setActualBusinessPartnerId(long actualBusinessPartnerId) {
        this.actualBusinessPartnerId = actualBusinessPartnerId;
        billTable.updateDataCollectionFromDB(actualBusinessPartnerId);
        billTable.renewTableModel();
        validate();

    }
}
