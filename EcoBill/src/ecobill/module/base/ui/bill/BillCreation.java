package ecobill.module.base.ui.bill;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.BeansException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.event.TableModelListener;

import ecobill.core.system.Internationalization;
import ecobill.core.util.FileUtils;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;
import ecobill.core.ui.MainFrame;
import ecobill.module.base.ui.deliveryorder.*;
import ecobill.module.base.ui.article.ArticleTable;
import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.*;

import java.util.*;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.*;
import java.awt.event.*;


/**
 * Die <code>BillCreation</code> enth�lt alle Lieferschein, um daraus Rechnungen zu generien
 * <p/>
 * User: sega
 * Date: 10.10.2005
 * Time: 17:49:23
 *
 * @author Sebastian Gath
 * @version $Id: BillCreation.java,v 1.2 2005/11/05 19:34:42 gath Exp $
 * @since EcoBill 1.0
 */
public class BillCreation extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k�nnen in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(BillCreation.class);

    /**
     * Panel in dem die Lieferscheine als �bersicht angezeigt werden
     */
    private OverviewPanel overview;

    /**
     * Enth�lt die Lieferscheine, die markiert werden k�nnen und aus denen dann eine Rechnung generiert wird
     */
    private OrderTableWithCB orderTable;

    /**
     * aktulle BusinessPartnerId
     */
    private long actualBusinessPartnerId;

    /**
     *  Zeigt die rechte Seite, also ein �berischt �ber Ausgew�hlte Lieferscheine und die Vorschau f�r eine Rechnung
     */
    private BillRightPanel billRightPanel;

    /**
     * Mainframe der Anwendung
     */
    private MainFrame mainFrame;

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

    public BillCreation(BaseService baseService, MainFrame mainFrame) {
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
            LOG.info("Schlie�e BillUI und speichere die Daten.");
        }
        /*
                // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
                // zu k�nnen.
                deliveryOrderTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
                articleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        */    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        orderTable = new OrderTableWithCB(actualBusinessPartnerId, baseService) ;

        billRightPanel = new BillRightPanel(true);
        billRightPanel.setMainFrame(mainFrame);
        billRightPanel.setBaseService(baseService);
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        overview = new OverviewPanel(orderTable, billRightPanel);
        ActionListener a1 = new ActionListener() {

            /**
             * Erzeugt eine neue Rechnung, mit anschlie�endem Vorschaufenster
             *
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                try {
                     long billId = createNewBill();
                     System.out.println("BillId: " + billId);
                     billRightPanel.doJasper(billId);
                }
                catch (Exception exception) {

                }
            }
        };

        overview.addButtonToVerticalButton(1, new ImageIcon("images/delivery_order_new.png"), "Neue Rechnung erstellen", a1);
        ActionListener a2 = new ActionListener() {

            /**
             * Erzeugt eine neue Rechnung, ohne anschlie�endes Vorschaufenster
             *
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
               long billNumber = createNewBill();

            }
        };
        overview.addButtonToVerticalButton(2, new ImageIcon("images/delivery_order_ok.png"), "Rechung speichern", a2);

        overview.addButtonToVerticalButton(4, new ImageIcon("images/refresh.png"), "Aktualisieren", null);

        overview.init();
        BillCreation.this.add(overview, BorderLayout.CENTER);
    }

    /**
     * Erstellt eine neue Rechnung aus der markierten Lieferscheinen
     *
     * @return long Id der neuen Rechnung
     */
    public long createNewBill() {

        Bill bill = new Bill();

        // �ber alle Zeilen der Lieferscheintabelle gehen
        for (int i = 0; i < orderTable.getTable().getRowCount(); i++) {

            // Checkbox markiert ??
            if ((orderTable.getTable().getValueAt(i, 0) instanceof Boolean)
                && ((Boolean) (orderTable.getTable().getValueAt(i, 0))).booleanValue()) {

                // das DeliveryOrder Object zu der Zeile aus der orderTable laden
                Object o = baseService.load(DeliveryOrder.class, ((IdValueItem) orderTable.getTable().getValueAt(i, 1)).getId());

                if (o instanceof DeliveryOrder) {

                    DeliveryOrder deliveryOrder = (DeliveryOrder) o;

                    // alle Artikel zu dem Lieferschein laden
                    Set<ReduplicatedArticle> reduplicatedArticles = deliveryOrder.getArticles();

                    // Rechnungsumme
                    double sum = 0;

                    // Rechnungsumme aus allen Preisen und Mengen der Artikel errechnen
                    for (ReduplicatedArticle article : reduplicatedArticles) {

                        sum = sum + article.getPrice() * article.getQuantity();
                    }

                    // Collection f�r die Vorschautabelle erzeugen
                    BillPreviewCollection bpc = new BillPreviewCollection(deliveryOrder.getDeliveryOrderNumber(), deliveryOrder.getDeliveryOrderDate(), sum);

                    // Die Lieferung als in einen Lieferschein eingengangen markieren
                    deliveryOrder.setPreparedBill(true);

                    // Lieferschein der Rechnung anh�ngen
                    bill.addDeliveryOrder(deliveryOrder);

                    // Collection der Vorschautabelle �bergeben
                    billRightPanel.addDeliveryOrder(bpc);
                }
            }


/*            billPreviewTable = new BillPreviewTable(baseService);
            billRightPanel.add(billPreviewTable);
            overview.validate();
*/        }

        // n�chste freie Rechnungsnummer ermitteln
        Long billNumber = baseService.getNextBillNumber();

        // Rechnungsobjekt f�llen
        bill.setBusinessPartner((BusinessPartner) baseService.load(BusinessPartner.class, actualBusinessPartnerId));
        bill.setBillNumber(billNumber);
        bill.setBillDate(Calendar.getInstance().getTime());

        // Rechnungsobjekt speichern
        baseService.saveOrUpdate(bill);
        JOptionPane.showMessageDialog(null, "mit der Rechnungsnummer: " + billNumber + " angelegt.",
                "Es wurde eine neue Rechnung",1);

        return bill.getId();
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

    }

    /**
     * Setzt die aktuelle BusinessPartnerId und l�dt die dazugeh�rigen Lieferscheine
     *
     * @param actualBusinessPartnerId
     */
    public void setActualBusinessPartnerId(long actualBusinessPartnerId) {
        this.actualBusinessPartnerId = actualBusinessPartnerId;
        orderTable.updateDataCollectionFromDB(actualBusinessPartnerId);
        orderTable.renewTableModel();
        validate();
    }


}
