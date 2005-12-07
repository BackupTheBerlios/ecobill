package ecobill.module.base.ui.bill;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.ui.component.OverviewPanel;
import ecobill.module.base.ui.component.Address;
import ecobill.module.base.ui.deliveryorder.OrderTableWithCB;
import ecobill.module.base.domain.*;
import ecobill.core.util.IdValueItem;
import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.ui.MainFrame;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * DeliveryOrderUI.
 * <p/>
 * User: gs
 * Date: 05.10.2005
 * Time: 16:57:16
 *
 * @author Sebastian Gath
 * @version $Id: BillUI.java,v 1.17 2005/12/07 18:13:41 raedler Exp $
 * @since EcoBill 1.0
 */


public class BillUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(BillUI.class);

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
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() {

        // Initialisieren der Komponenten und des Layouts.
        initComponents();
        initLayout();

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        /*
        try {
            deliveryOrderTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("bill_table")));
            articleTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("bill_table")));
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage());
            }
        }

         */
        reinitI18N();
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        /*
        if (LOG.isInfoEnabled()) {
            LOG.info("Schließe BillUI und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu können.
        billTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
        articleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        */
    }

    // Adresse des Businesspartners
    private Address address;

    // Panel für die Adresse des Businesspartners
    private JPanel addressPanel;

    // Rechnungstablle
    private BillTable billTable;

    // Rechnungsdatum
    private BillData billData;

    // Rechnungsdatenpanel
    private JPanel billDataPanel;

    // Vorschautabelle für Rechnungen
    private BillPreviewTable billPreviewTable;

    // Übersichtspanel
    private JPanel overview;

    // Panel rechte Seite
    private JPanel panelLeft;

    // Panel linke Seite
    private JPanel panelRight;

    // splitPane für erstes Pane
    private JSplitPane splitPane;

    // TabbedPane Adresse
    private JTabbedPane tabbedPane;

    // TabbedPane Daten
    private JTabbedPane tabbedPaneRight;

    // Lieferscheintabelle mit Checkboxen
    private OrderTableWithCB deliveryOrderTableCB;

    // Panel für den Jasperreport
    private BillPrintPanel billPrintPanelOverview;


    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        tabbedPane = new JTabbedPane();
        overview = new JPanel();
        splitPane = new JSplitPane();
        panelLeft = new JPanel();
        billTable = new BillTable(baseService);
        panelRight = new JPanel();
        tabbedPaneRight = new JTabbedPane();
        addressPanel = new JPanel();
        address = new Address();
        billDataPanel = new JPanel();
        billData = new BillData(baseService);
        billPreviewTable = new BillPreviewTable(baseService);

        deliveryOrderTableCB = new OrderTableWithCB(baseService);

        MainFrame mainFrame = (MainFrame) applicationContext.getBean("mainFrame");

        billPrintPanelOverview  = new BillPrintPanel(mainFrame, baseService);

        splitPane.setLeftComponent(deliveryOrderTableCB);
    }

    private JToolBar createBillToolBar() {

        JToolBar toolBar = new JToolBar();

        // Button zum Speichern des aktuellen Lieferscheins hinzufügen
        JButton okBill = new JButton(new ImageIcon("images/delivery_order_ok.png"));
        okBill.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                saveOrUpdateBill();

                billTable.renewTableModel();
                deliveryOrderTableCB.renewTableModel();

                String billNumber = billData.getBillNumber();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.BILL);

                if (numberSequence.compareWithNumber(billNumber) <= -1) {
                    numberSequence.setNumber(billNumber);
                    baseService.saveOrUpdate(numberSequence);
                }
            }
        });

        toolBar.add(okBill);

        return toolBar;
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        splitPane.setBorder(null);
        splitPane.setDividerLocation(350);
        splitPane.setOneTouchExpandable(true);

        GroupLayout panelLeftLayout = new GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
            panelLeftLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelLeftLayout.createSequentialGroup()
                .add(deliveryOrderTableCB, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLeftLayout.setVerticalGroup(
            panelLeftLayout.createParallelGroup(GroupLayout.LEADING)
            .add(deliveryOrderTableCB, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        splitPane.setLeftComponent(panelLeft);

        addressPanel.setLayout(new BorderLayout());

        addressPanel.add(address, BorderLayout.CENTER);

        tabbedPaneRight.addTab(WorkArea.getMessage(Constants.ADDRESS), addressPanel);

        billDataPanel.setLayout(new BorderLayout());

        billDataPanel.add(billData, BorderLayout.CENTER);

        tabbedPaneRight.addTab(WorkArea.getMessage(Constants.DATA), billDataPanel);

        GroupLayout panelRightLayout = new GroupLayout(panelRight);
        panelRight.setLayout(panelRightLayout);
        panelRightLayout.setHorizontalGroup(
            panelRightLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelRightLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelRightLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(billPreviewTable, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .add(tabbedPaneRight, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)))
        );
        panelRightLayout.setVerticalGroup(
            panelRightLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelRightLayout.createSequentialGroup()
                .add(tabbedPaneRight, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(billPreviewTable, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
        );
        splitPane.setRightComponent(panelRight);

        overview.setLayout(new BorderLayout());
        JPanel createBill = new JPanel();

        GroupLayout overviewLayout = new GroupLayout(createBill);
        createBill.setLayout(overviewLayout);
        overviewLayout.setHorizontalGroup(
            overviewLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                .addContainerGap()
                .addPreferredGap(LayoutStyle.RELATED)
                .add(splitPane, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );
        overviewLayout.setVerticalGroup(
            overviewLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, overviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(overviewLayout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, splitPane))
                .addContainerGap())
        );

        overview.add(createBillToolBar(), BorderLayout.NORTH);
        overview.add(createBill, BorderLayout.CENTER);

        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), overview);

        OverviewPanel billOverview = new OverviewPanel(baseService, billTable, billPrintPanelOverview);

        JButton viewBill = new JButton(new ImageIcon("images/jasper_view.png"));
        viewBill.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */

            public void actionPerformed(ActionEvent e) {
                try {
                      billPrintPanelOverview.doJasper(billTable.getIdOfSelectedRow());
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton deleteBill = new JButton(new ImageIcon("images/delivery_order_delete.png"));
        deleteBill.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Long billId = billTable.getIdOfSelectedRow();

                Bill bill = (Bill) baseService.load(Bill.class, billId);
                baseService.delete(bill);

                billTable.renewTableModel();
                 if (billPrintPanelOverview != null) {
                    billPrintPanelOverview.clearViewerPanel();
                }
            }
        });

        JToolBar toolBar = new JToolBar();
        toolBar.add(viewBill);
        toolBar.add(deleteBill);

        JPanel showBills = new JPanel(new BorderLayout());
        showBills.add(toolBar, BorderLayout.NORTH);
        showBills.add(billOverview, BorderLayout.CENTER);

        tabbedPane.addTab(null, showBills);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));
        tabbedPane.setTitleAt(1, WorkArea.getMessage(Constants.MAX_OVERVIEW));

        billData.reinitI18N();

        /* TODO: fix me!!!
        verticalButton.reinitI18N();

        verticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON1_TOOLTIP));
        verticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON2_TOOLTIP));
        verticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON3_TOOLTIP));
        verticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON4_TOOLTIP));
        */
    }

    /**
     * Erneuert das <code>TableModel</code> der Artikel Tabelle.
     */
    public void renewArticleTableModel() {
        billTable.renewTableModel();
    }


    /**
     * Setzt den BusinessPartner
     *
     * @param businessPartner
     */
    public void setBusinessPartner(BusinessPartner businessPartner) {
        address.setBusinessPartner(businessPartner);
    }

    /**
     * Setzt alle Eingabefelder neu
     *
     * @param deliveryOrderNumber
     */
    public void resetInput(String deliveryOrderNumber) {
        billData.setBillNumber(deliveryOrderNumber);
        billData.setBillDate(new Date());
        billData.setPrefix("");
        billData.setSuffix("");

        billPreviewTable.getTableModel().getDataVector().removeAllElements();
        billPreviewTable.renewTableModel();
    }

    /**
     * Erstellt eine neue Rechnung aus der markierten Lieferscheinen.
     */
    public void saveOrUpdateBill() {

        Set<BillPreviewCollection> billPreviewCollections = new HashSet<BillPreviewCollection>();

        Bill bill = new Bill();

        // über alle Zeilen der Lieferscheintabelle gehen
        for (int i = 0; i < deliveryOrderTableCB.getTable().getRowCount(); i++) {

            // Checkbox markiert ??
            if ((deliveryOrderTableCB.getTable().getValueAt(i, 0) instanceof Boolean)
                && ((Boolean) (deliveryOrderTableCB.getTable().getValueAt(i, 0))).booleanValue()) {

                // das DeliveryOrder Object zu der Zeile aus der orderTable laden
                Object o = baseService.load(DeliveryOrder.class, ((IdValueItem) deliveryOrderTableCB.getTable().getValueAt(i, 1)).getId());

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

                    // Collection für die Vorschautabelle erzeugen
                    BillPreviewCollection bpc = new BillPreviewCollection(deliveryOrder.getDeliveryOrderNumber(), deliveryOrder.getDeliveryOrderDate(), sum);

                    // Die Lieferung als in einen Lieferschein eingengangen markieren
                    deliveryOrder.setPreparedBill(true);

                    // Lieferschein der Rechnung anhängen
                    bill.addDeliveryOrder(deliveryOrder);

                    // Collection der Vorschautabelle Übergeben
                    billPreviewCollections.add(bpc);
                }
            }
        }

        billPreviewTable.setDataCollection(billPreviewCollections);
        billPreviewTable.renewTableModel();

        // Rechnungsobjekt füllen
        bill.setBusinessPartner((BusinessPartner) baseService.load(BusinessPartner.class, actualBusinessPartnerId));
        bill.setBillNumber(billData.getBillNumber());
        bill.setBillDate(billData.getBillDate());
        bill.setPrefixFreetext(billData.getPrefix());
        bill.setSuffixFreetext(billData.getSuffix());

        // Rechnungsobjekt speichern
        baseService.saveOrUpdate(bill);

        billPreviewCollections.clear();
    }

    private Long actualBusinessPartnerId;

    /**
     * setzt den akutellen Businesspartner anhand seiner Id
     *
     * @param actualBusinessPartnerId
     */
    public void setActualBusinessPartnerId(Long actualBusinessPartnerId) {

        this.actualBusinessPartnerId = actualBusinessPartnerId;

        billTable.setBusinessPartnerId(actualBusinessPartnerId);
        deliveryOrderTableCB.setBusinessPartnerId(actualBusinessPartnerId);

        billTable.renewTableModel();
        deliveryOrderTableCB.renewTableModel();
    }
}