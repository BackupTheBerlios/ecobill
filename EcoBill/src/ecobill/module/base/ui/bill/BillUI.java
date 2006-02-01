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
import ecobill.module.base.ui.component.AddressPanel;
import ecobill.module.base.ui.component.FormularDataPanel;
import ecobill.module.base.ui.component.TitleBorderedTextAreaPanel;
import ecobill.module.base.ui.component.JToolBarButton;
import ecobill.module.base.ui.deliveryorder.DeliveryOrderTableWithCB;
import ecobill.module.base.ui.textblock.TextBlockDialog;
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
 * User: raedler
 * Date: 05.10.2005
 * Time: 16:57:16
 *
 * @author R&auml;dle Roman
 * @version $Id: BillUI.java,v 1.23 2006/02/01 22:20:32 raedler Exp $
 * @since EcoBill 1.0
 */
public class BillUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    // Icons used in this bill user interface.
    private final Icon ICON_NEW_BILL = new ImageIcon("images/bill/bill_new.png");
    private final Icon ICON_SAVE_BILL = new ImageIcon("images/bill/bill_save.png");
    private final Icon ICON_DELETE_BILL = new ImageIcon("images/bill/bill_delete.png");
    private final Icon ICON_OPEN_BILL = new ImageIcon("images/bill/bill_open_all.png");
    private final Icon ICON_VIEW_BILL = new ImageIcon("images/bill/bill_view.png");
    private final Icon ICON_PREFIX_TEXT_BLOCK = new ImageIcon("images/bill/textblock_prefix.png");
    private final Icon ICON_SUFFIX_TEXT_BLOCK = new ImageIcon("images/bill/textblock_suffix.png");
    private final Icon ICON_ADD_DELIVERY_ORDER = new ImageIcon("images/bill/delivery_order_add.png");

    // Buttons used in this bill user interface.
    private JToolBarButton newBillB = new JToolBarButton(ICON_NEW_BILL);
    private JToolBarButton saveBillB = new JToolBarButton(ICON_SAVE_BILL);
    private JToolBarButton deleteBillB = new JToolBarButton(ICON_DELETE_BILL);
    private JToolBarButton openBillB = new JToolBarButton(ICON_OPEN_BILL);
    private JToolBarButton viewBillB = new JToolBarButton(ICON_VIEW_BILL);
    private JToolBarButton prefixTextBlockB = new JToolBarButton(ICON_PREFIX_TEXT_BLOCK);
    private JToolBarButton suffixTextBlockB = new JToolBarButton(ICON_SUFFIX_TEXT_BLOCK);
    private JToolBarButton deliveryOrderAddB = new JToolBarButton(ICON_ADD_DELIVERY_ORDER);

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
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

    // Rechnungstablle
    private BillTable billTable;

    // Lieferscheintabelle mit Checkboxen
    private DeliveryOrderTableWithCB deliveryOrderTableCB;

    private JTabbedPane tabbedPane;
    private JPanel overview;

    private AddressPanel addressPanel;
    private BillPreviewTable billPreviewTable;
    private FormularDataPanel formularDataPanel;
    private TitleBorderedTextAreaPanel prefixPanel;
    private TitleBorderedTextAreaPanel suffixPanel;

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        tabbedPane = new JTabbedPane();
        overview = new JPanel();

        billTable = new BillTable(baseService);
        addressPanel = new AddressPanel();
        formularDataPanel = new FormularDataPanel(Constants.DATA, Constants.BILL_NUMBER, Constants.DATE);
        prefixPanel = new TitleBorderedTextAreaPanel(Constants.PREFIX_FREE_TEXT);
        suffixPanel = new TitleBorderedTextAreaPanel(Constants.SUFFIX_FREE_TEXT);

        billPreviewTable = new BillPreviewTable(baseService);

        deliveryOrderTableCB = new DeliveryOrderTableWithCB(baseService);
    }

    private JToolBar createBillToolBar() {

        JToolBar toolBar = new JToolBar();

        // Button zum erzeugen einer neuen Rechnung.
        newBillB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // Setzt den Lieferschein Anzeigeknopf auf disabled da bei einem neuen Lieferschein
                // noch kein View bereit steht.
                viewBillB.setEnabled(false);

                billPreviewTable.getDataCollection().clear();
                billPreviewTable.renewTableModel();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.BILL);

                resetInput(numberSequence.getNextNumber());
            }
        });

        // Button zum Löschen der Rechnung.
        deleteBillB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                int selection = JOptionPane.showConfirmDialog((MainFrame) applicationContext.getBean("mainFrame"), "Rechnung wirklich l\u00f6schen?", "L\u00f6schen Abfrage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (JOptionPane.YES_OPTION == selection) {

                    for (DeliveryOrder deliveryOrder : bill.getDeliveryOrders()) {
                        deliveryOrder.setPreparedBill(false);
                        baseService.saveOrUpdate(deliveryOrder);
                    }

                    baseService.delete(bill);

                    billPreviewTable.getDataCollection().clear();
                    billPreviewTable.renewTableModel();

                    NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.BILL);

                    resetInput(numberSequence.getNextNumber());
                }
            }
        });

        // Button zum Speichern des aktuellen Lieferscheins hinzufuegen
        saveBillB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                //saveOrUpdateBill();

                billTable.renewTableModel();
                deliveryOrderTableCB.renewTableModel();

                String billNumber = formularDataPanel.getNumber();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.BILL);

                if (numberSequence.compareWithNumber(billNumber) <= -1) {
                    numberSequence.setNumber(billNumber);
                    baseService.saveOrUpdate(numberSequence);
                }
            }
        });

        openBillB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    new BillChooseDialog((MainFrame) applicationContext.getBean("mainFrame"), true, BillUI.this, baseService, actualBusinessPartnerId);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        viewBillB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    new BillViewerDialog((MainFrame) applicationContext.getBean("mainFrame"), true, baseService, bill.getId());
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        prefixTextBlockB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new TextBlockDialog((MainFrame) applicationContext.getBean("mainFrame"), true, prefixPanel.getTextArea(), baseService);
            }
        });

        suffixTextBlockB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new TextBlockDialog((MainFrame) applicationContext.getBean("mainFrame"), true, suffixPanel.getTextArea(), baseService);
            }
        });

        deliveryOrderAddB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                new BillDeliveryOrdersDialog((MainFrame) applicationContext.getBean("mainFrame"), true, BillUI.this, baseService, actualBusinessPartnerId);
            }
        });

        toolBar.add(newBillB);
        toolBar.add(saveBillB);
        toolBar.add(deleteBillB);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(openBillB);
        toolBar.add(viewBillB);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(prefixTextBlockB);
        toolBar.add(suffixTextBlockB);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(deliveryOrderAddB);

        return toolBar;
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        overview.setLayout(new BorderLayout());
        JPanel createBillPanel = new JPanel();

        GroupLayout createBillPanelLayout = new GroupLayout(createBillPanel);
        createBillPanel.setLayout(createBillPanelLayout);
        createBillPanelLayout.setHorizontalGroup(createBillPanelLayout.createParallelGroup(GroupLayout.LEADING)
                .add(GroupLayout.LEADING, createBillPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(createBillPanelLayout.createParallelGroup(GroupLayout.TRAILING)
                        .add(GroupLayout.LEADING, billPreviewTable, GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                        .add(GroupLayout.LEADING, createBillPanelLayout.createSequentialGroup()
                        .add(createBillPanelLayout.createParallelGroup(GroupLayout.TRAILING)
                                .add(GroupLayout.LEADING, prefixPanel, 0, 300, Short.MAX_VALUE)
                                .add(addressPanel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(createBillPanelLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(suffixPanel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .add(formularDataPanel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))))
                .add(10, 10, 10)));
        createBillPanelLayout.setVerticalGroup(createBillPanelLayout.createParallelGroup(GroupLayout.LEADING)
                .add(GroupLayout.LEADING, createBillPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(createBillPanelLayout.createParallelGroup(GroupLayout.TRAILING, false)
                        .add(addressPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(formularDataPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(createBillPanelLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(prefixPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .add(suffixPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(billPreviewTable, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap()));

        overview.add(createBillToolBar(), BorderLayout.NORTH);
        overview.add(createBillPanel, BorderLayout.CENTER);

        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), overview);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));

        // Tooltips of each button in this bill user interface.
        newBillB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.bill.BillUI.newBillB"));
        saveBillB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.bill.BillUI.saveBillB"));
        deleteBillB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.bill.BillUI.deleteBillB"));
        openBillB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.bill.BillUI.openBillB"));
        viewBillB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.bill.BillUI.viewBillB"));
        prefixTextBlockB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.bill.BillUI.prefixTextBlockB"));
        suffixTextBlockB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.bill.BillUI.suffixTextBlockB"));
        deliveryOrderAddB.setToolTipText(WorkArea.getMessage("ecobill.module.base.ui.bill.BillUI.deliveryOrderAddB"));
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
        addressPanel.setBusinessPartner(businessPartner);
    }

    /**
     * Setzt alle Eingabefelder neu
     *
     * @param billNumber
     */
    public void resetInput(String billNumber) {
        formularDataPanel.setNumber(billNumber);
        formularDataPanel.setDate(new Date());
        prefixPanel.getTextArea().setText("");
        suffixPanel.getTextArea().setText("");

        billPreviewTable.getTableModel().getDataVector().removeAllElements();
        billPreviewTable.renewTableModel();
    }

    private Bill bill;

    /**
     * Erstellt eine neue Rechnung aus der markierten Lieferscheinen.
     */
    public void saveOrUpdateBill(DeliveryOrderTableWithCB deliveryOrderTableCB) {

        Set<BillPreviewCollection> billPreviewCollections = new HashSet<BillPreviewCollection>();

        Bill bill = new Bill();

        // �ber alle Zeilen der Lieferscheintabelle gehen
        for (int i = 0; i < deliveryOrderTableCB.getTable().getRowCount(); i++) {

            // Checkbox markiert ??
            if ((deliveryOrderTableCB.getTable().getValueAt(i, 0) instanceof Boolean) && ((Boolean) (deliveryOrderTableCB.getTable().getValueAt(i, 0))).booleanValue())
            {

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
        bill.setBillNumber(formularDataPanel.getNumber());
        bill.setBillDate(formularDataPanel.getDate());

        System.out.println("PREFIX: " + prefixPanel.getTextArea().getText());

        bill.setPrefixFreetext(prefixPanel.getTextArea().getText());
        bill.setSuffixFreetext(suffixPanel.getTextArea().getText());

        // Rechnungsobjekt speichern
        baseService.saveOrUpdate(bill);

        this.bill = bill;

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

    public void setBill(Bill bill) {

        viewBillB.setEnabled(true);

        this.bill = bill;
        billPreviewTable.setBill(bill);

        if (bill.getId() == null) {
            resetInput(bill.getBillNumber());
            return;
        }

        formularDataPanel.setNumber(bill.getBillNumber());
        formularDataPanel.setDate(bill.getBillDate());
        prefixPanel.getTextArea().setText(bill.getPrefixFreetext());
        suffixPanel.getTextArea().setText(bill.getSuffixFreetext());
    }
}