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
import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.ui.component.OverviewPanel;
import ecobill.module.base.ui.component.Address;
import ecobill.module.base.ui.component.AbstractJasperPrintPanel;
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
 * @version $Id: BillUI.java,v 1.15 2005/11/08 21:33:05 gath Exp $
 * @since EcoBill 1.0
 */


public class BillUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k�nnen in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(BillUI.class);

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
            LOG.info("Schlie�e BillUI und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu k�nnen.
        billTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
        articleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        */
    }

    // Adresse des Businesspartners
    private Address address;

    // Panel f�r die Adresse des Businesspartners
    private JPanel addressPanel;

    // Rechnungstablle
    private BillTable billTable;

    // Rechnungsdatum
    private BillData billData;

    // Rechnungsdatenpanel
    private JPanel billDataPanel;

    // Vorschautabelle f�r Rechnungen
    private BillPreviewTable billPreviewTable;

    // �bersichtspanel
    private JPanel overview;

    // Panel rechte Seite
    private JPanel panelLeft;

    // Panel linke Seite
    private JPanel panelRight;

    // splitPane f�r erstes Pane
    private JSplitPane splitPane;

    // TabbedPane Adresse
    private JTabbedPane tabbedPane;

    // TabbedPane Daten
    private JTabbedPane tabbedPaneRight;

    // Vertikale Buttongroup
    private VerticalButton verticalButton;

    // Lieferscheintabelle mit Checkboxen
    private OrderTableWithCB deliveryOrderTableCB;

    // Panel f�r den Jasperreport
    private BillPrintPanel billPrintPanelOverview;


    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        tabbedPane = new JTabbedPane();
        overview = new JPanel();
        verticalButton = new VerticalButton();
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

        // Button zum Speichern des aktuellen Lieferscheins hinzuf�gen
        verticalButton.getButton2().setVisible(true);
        verticalButton.getButton2().setIcon(new ImageIcon("images/delivery_order_ok.png"));
        verticalButton.getButton2().addActionListener(new ActionListener() {

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

        // Button refresh hinzuf�gen
        verticalButton.getButton4().setVisible(true);
        verticalButton.getButton4().setIcon(new ImageIcon("images/refresh.png"));

        splitPane.setDividerLocation(200);
        splitPane.setLeftComponent(deliveryOrderTableCB);
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        splitPane.setBorder(null);
        splitPane.setDividerLocation(200);
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

        GroupLayout overviewLayout = new GroupLayout(overview);
        overview.setLayout(overviewLayout);
        overviewLayout.setHorizontalGroup(
            overviewLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(verticalButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(splitPane, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );
        overviewLayout.setVerticalGroup(
            overviewLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, overviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(overviewLayout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, splitPane)
                    .add(GroupLayout.LEADING, verticalButton, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), overview);

        OverviewPanel deliveryOrderOverview = new OverviewPanel(baseService, billTable, billPrintPanelOverview);
        ActionListener actionListener = new ActionListener() {

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
        };

        deliveryOrderOverview.addButtonToVerticalButton(1,new ImageIcon("images/jasper_view.png"), "Rechnung in Vorschaufernster anzeigen", actionListener );

        ActionListener actionListener1 = new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Long billId = billTable.getIdOfSelectedRow();

                Bill bill = (Bill) baseService.load(Bill.class, billId);
                baseService.delete(bill);

                billTable.renewTableModel();
                 if (billPrintPanelOverview instanceof AbstractJasperPrintPanel) {
                    ((AbstractJasperPrintPanel) billPrintPanelOverview).clearViewerPanel();
                }
            }
        };

        deliveryOrderOverview.addButtonToVerticalButton(3,new ImageIcon("images/delivery_order_delete.png"), "Rechnung l�schen", actionListener1 );

        tabbedPane.addTab(null, deliveryOrderOverview);

        add(tabbedPane, BorderLayout.CENTER);

    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));
        tabbedPane.setTitleAt(1, WorkArea.getMessage(Constants.MAX_OVERVIEW));

        verticalButton.reinitI18N();
        billData.reinitI18N();


        verticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON1_TOOLTIP));
        verticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON2_TOOLTIP));
        verticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON3_TOOLTIP));
        verticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON4_TOOLTIP));


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
    }

    /**
     * Erstellt eine neue Rechnung aus der markierten Lieferscheinen.
     */
    public void saveOrUpdateBill() {

        Set<BillPreviewCollection> billPreviewCollections = new HashSet<BillPreviewCollection>();

        Bill bill = new Bill();

        // �ber alle Zeilen der Lieferscheintabelle gehen
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

                    // Collection f�r die Vorschautabelle erzeugen
                    BillPreviewCollection bpc = new BillPreviewCollection(deliveryOrder.getDeliveryOrderNumber(), deliveryOrder.getDeliveryOrderDate(), sum);

                    // Die Lieferung als in einen Lieferschein eingengangen markieren
                    deliveryOrder.setPreparedBill(true);

                    // Lieferschein der Rechnung anh�ngen
                    bill.addDeliveryOrder(deliveryOrder);

                    // Collection der Vorschautabelle �bergeben
                    billPreviewCollections.add(bpc);
                }
            }
        }

        billPreviewTable.setDataCollection(billPreviewCollections);
        billPreviewTable.renewTableModel();

        // Rechnungsobjekt f�llen
        bill.setBusinessPartner((BusinessPartner) baseService.load(BusinessPartner.class, actualBusinessPartnerId));
        bill.setBillNumber(billData.getBillNumber());
        bill.setBillDate(billData.getBillDate());
        bill.setPrefixFreetext(billData.getPrefix());
        bill.setSuffixFreetext(billData.getSuffix());

        // Rechnungsobjekt speichern
        baseService.saveOrUpdate(bill);
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