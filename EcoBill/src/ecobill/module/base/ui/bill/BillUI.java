package ecobill.module.base.ui.bill;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import ecobill.module.base.ui.deliveryorder.*;
import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.ui.article.ArticleTable;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.ReduplicatedArticle;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.core.util.FileUtils;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;
import ecobill.core.ui.MainFrame;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.event.*;
import java.awt.*;

/**
 * Die <code>BillUI</code> erstellt das User Interface zur Eingabe von Rechnungsdaten.
 * <p/>
 * User: sega
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Sebastian Gath
 * @version $Id: BillUI.java,v 1.12 2005/11/06 19:01:56 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class BillUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {


    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(DeliveryOrderUI.class);

    /**
     * Der <code>ApplicationContext</code> beinhaltet alle Beans die darin angegeben sind
     * und ermöglicht wahlfreien Zugriff auf diese.
     */
    protected ApplicationContext applicationContext;

    /**
     * Enthält die Tabs Rechnungsübersicht und Detailansicht
     */
    private JTabbedPane tabbedPane;

    /**
     * Der MainFrame der Anwendung
     */
    private MainFrame mainFrame;

    /**
     * Der Pane auf dem die Rechungen druch Markieren von Lieferscheinen erzeugt wird.
     */
    private BillCreation billCreation;

    /**
     * Gibt das <code>BillCreation</code> Panel zurück.
     *
     * @return Das <code>BillCreation</code> Panel.
     */
    public BillCreation getBillCreation() {
        return billCreation;
    }

    /**
     * Panel auf dem alle Rechungen angezeigt werden und zur Detailansicht ausgewählt werden können
     */
    private BillOverviewPanel billOverviewPanel;

    /**
     * Gibt das <code>BillOverviewPanel</code>  zurück.
     *
     * @return Das <code>BillOverviewPanel</code>.
     */
    public BillOverviewPanel getBillOverviewPanel() {
        return billOverviewPanel;
    }

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
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() {

        // Initialisieren der Komponenten und des Layouts.
        initComponents();
        initLayout();

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        reinitI18N();
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        if (LOG.isInfoEnabled()) {
            LOG.info("Schließe OverviewPanel und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu können.
        /*
        if (leftTable instanceof DeliveryOrderTable) {
            leftTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
        }
        else if (leftTable instanceof ArticleTable) {
            leftTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        }
        */

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu können.
        //deliveryOrderTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
        //articleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        mainFrame = (MainFrame) applicationContext.getBean("mainFrame");
        tabbedPane = new JTabbedPane();
        billCreation = new BillCreation(baseService, mainFrame);
        billOverviewPanel = new BillOverviewPanel(baseService, mainFrame);
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        tabbedPane.add(billCreation);
        tabbedPane.add(billOverviewPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));
        tabbedPane.setTitleAt(1, WorkArea.getMessage(Constants.DETAIL));


    }

}
