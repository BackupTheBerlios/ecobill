package ecobill.module.base.ui.deliveryorder;

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
import ecobill.module.base.ui.article.ArticleTable;
import ecobill.module.base.ui.component.*;
import ecobill.module.base.domain.*;
import ecobill.core.util.FileUtils;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;
import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.ui.MainFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * DeliveryOrderUI.
 * <p/>
 * User: rro
 * Date: 05.10.2005
 * Time: 16:57:16
 *
 * @author Roman R&auml;dle
 * @version $Id: DeliveryOrderUI.java,v 1.15 2005/11/08 18:09:35 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

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
        try {
            deliveryOrderTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("delivery_order_table")));
            articleTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("article_table")));
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage());
            }
        }

        reinitI18N();
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        if (LOG.isInfoEnabled()) {
            LOG.info("Schließe DeliveryOrderUI und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu können.
        deliveryOrderTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
        articleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        tabbedPane = new JTabbedPane();
        overview = new JPanel();
        verticalButton = new VerticalButton();
        splitPane = new JSplitPane();
        panelLeft = new JPanel();
        articleTable = new ArticleTable(baseService);
        panelRight = new JPanel();
        tabbedPaneRight = new JTabbedPane();
        addressPanel = new JPanel();
        address = new ecobill.module.base.ui.component.Address();
        deliveryOrderDataPanel = new JPanel();
        deliveryOrderData = new DeliveryOrderData(baseService);
        deliveryOrderTable = new DeliveryOrderTable(baseService);

        orderTable = new OrderTable(baseService);

        MainFrame mainFrame = (MainFrame) applicationContext.getBean("mainFrame");

        deliveryOrderPrintPanelOverview  = new DeliveryOrderPrintPanel(mainFrame, baseService);

        articleTable = new ArticleTable(null, baseService) {
            protected KeyListener[] createKeyListeners() {
                return null;
            }

            protected MouseListener[] createMouseListeners() {
                return null;
            }

            protected TableModelListener[] createTableModelListeners() {
                return null;
            }
        };

        articleTable.getTable().getColumnModel().removeColumnModelListener(articleTable.getTable());

        articleTable.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int row = articleTable.getTable().getSelectedRow();

                IdKeyItem idKeyItem = (IdKeyItem) articleTable.getTableModel().getValueAt(row, 0);

                showAddArticleDialog(idKeyItem.getId());
            }
        });

        verticalButton.getButton1().setVisible(true);
        verticalButton.getButton1().setIcon(new ImageIcon("images/delivery_order_new.png"));
        verticalButton.getButton1().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                reduplicatedArticles.clear();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.DELIVERY_ORDER);

                resetInput(numberSequence.getNextNumber());
            }
        });

        verticalButton.getButton2().setVisible(true);
        verticalButton.getButton2().setIcon(new ImageIcon("images/delivery_order_ok.png"));
        verticalButton.getButton2().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateDeliveryOrder();

                orderTable.renewTableModel();

                String deliveryOrderNumber = deliveryOrderData.getDeliveryOrderNumber();

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.DELIVERY_ORDER);

                if (numberSequence.compareWithNumber(deliveryOrderNumber) <= -1) {
                    numberSequence.setNumber(deliveryOrderNumber);
                    baseService.saveOrUpdate(numberSequence);
                }

                deliveryOrderTable.getTableModel().getDataVector().removeAllElements();
                resetInput(numberSequence.getNextNumber());
            }
        });

        splitPane.setDividerLocation(200);
        splitPane.setLeftComponent(articleTable);
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
                .add(articleTable, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLeftLayout.setVerticalGroup(
            panelLeftLayout.createParallelGroup(GroupLayout.LEADING)
            .add(articleTable, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        splitPane.setLeftComponent(panelLeft);

        addressPanel.setLayout(new BorderLayout());

        addressPanel.add(address, BorderLayout.CENTER);

        tabbedPaneRight.addTab(WorkArea.getMessage(Constants.ADDRESS), addressPanel);

        deliveryOrderDataPanel.setLayout(new BorderLayout());

        deliveryOrderDataPanel.add(deliveryOrderData, BorderLayout.CENTER);

        tabbedPaneRight.addTab(WorkArea.getMessage(Constants.DATA), deliveryOrderDataPanel);

        GroupLayout panelRightLayout = new GroupLayout(panelRight);
        panelRight.setLayout(panelRightLayout);
        panelRightLayout.setHorizontalGroup(
            panelRightLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelRightLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelRightLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(deliveryOrderTable, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .add(tabbedPaneRight, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)))
        );
        panelRightLayout.setVerticalGroup(
            panelRightLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelRightLayout.createSequentialGroup()
                .add(tabbedPaneRight, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(deliveryOrderTable, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
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

        OverviewPanel deliveryOrderOverview = new OverviewPanel(baseService, orderTable, deliveryOrderPrintPanelOverview);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = orderTable.getTable().getSelectedRow();

                    if (row != -1) {
                        deliveryOrderPrintPanelOverview.doJasper(((IdValueItem) orderTable.getTable().getValueAt(row,0)).getId());
                    }
                    else {
                        JOptionPane.showMessageDialog(DeliveryOrderUI.this, "Datensatz auswählen", "Nachricht", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        deliveryOrderOverview.addButtonToVerticalButton(1,new ImageIcon("images/jasper_view.png"), "Lieferschein in Vorschaufernster anzeigen", actionListener );

        tabbedPane.addTab(null, deliveryOrderOverview);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));
        tabbedPane.setTitleAt(1, WorkArea.getMessage(Constants.MAX_OVERVIEW));

        tabbedPaneRight.setTitleAt(0, WorkArea.getMessage(Constants.ADDRESS));
        tabbedPaneRight.setTitleAt(1, WorkArea.getMessage(Constants.DATA));

        verticalButton.reinitI18N();
        deliveryOrderData.reinitI18N();
        articleTable.reinitI18N();
        address.reinitI18N();
        deliveryOrderData.reinitI18N();
        deliveryOrderTable.reinitI18N();
        deliveryOrderPrintPanelOverview.reinitI18N();

        ((TitledBorder) deliveryOrderTable.getPanelBorder()).setTitle(WorkArea.getMessage(Constants.DELIVERY_ORDER));

        verticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON1_TOOLTIP));
        verticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON2_TOOLTIP));
        verticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON3_TOOLTIP));
        verticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.DORDER_BUTTON4_TOOLTIP));
    }

    /**
     * Erneuert das <code>TableModel</code> der Artikel Tabelle.
     */
    public void renewArticleTableModel() {
        articleTable.renewTableModel();
    }

    private ecobill.module.base.ui.component.Address address;
    private JPanel addressPanel;
    private ArticleTable articleTable;
    private DeliveryOrderData deliveryOrderData;
    private JPanel deliveryOrderDataPanel;
    private DeliveryOrderTable deliveryOrderTable;
    private OrderTable orderTable;
    private JPanel overview;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JSplitPane splitPane;
    private JTabbedPane tabbedPane;
    private JTabbedPane tabbedPaneRight;
    private VerticalButton verticalButton;

    private DeliveryOrderPrintPanel deliveryOrderPrintPanelOverview;

    public void setBusinessPartner(BusinessPartner businessPartner) {
        address.setBusinessPartner(businessPartner);
    }

    private void showAddArticleDialog(Long id) {

        Article article = (Article) baseService.load(Article.class, id);

        JFrame frame = (JFrame) applicationContext.getBean("mainFrame");

        JDialog d = new DeliveryOrderDialog(frame, this, article, true);
        d.setModal(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension size = d.getSize();

        double x = screenSize.getWidth() - size.getWidth();
        double y = screenSize.getHeight() - size.getHeight();

        d.setLocation((int) x / 2, (int) y / 2);

        d.setVisible(true);
    }

    private Set<ReduplicatedArticle> reduplicatedArticles = new HashSet<ReduplicatedArticle>();

    public void addReduplicatedArticle(ReduplicatedArticle reduplicatedArticle) {

        reduplicatedArticles.add(reduplicatedArticle);

        deliveryOrderTable.setDataCollection(reduplicatedArticles);
        deliveryOrderTable.renewTableModel();
    }

    private void saveOrUpdateDeliveryOrder() {


        DeliveryOrder deliveryOrder = new DeliveryOrder();

        deliveryOrder.setBusinessPartner(address.getBusinessPartner());
        deliveryOrder.setCharacterisationType("delivery_order");
        deliveryOrder.setDeliveryOrderNumber(deliveryOrderData.getDeliveryOrderNumber());
        deliveryOrder.setDeliveryOrderDate(deliveryOrderData.getDeliveryOrderDate());
        deliveryOrder.setPrefixFreetext(deliveryOrderData.getPrefix());
        deliveryOrder.setSuffixFreetext(deliveryOrderData.getSuffix());
        deliveryOrder.setPreparedBill(false);

        Vector dataVector = ((DefaultTableModel) deliveryOrderTable.getTable().getModel()).getDataVector();

        Enumeration lines = dataVector.elements();
        for (int i = 1; lines.hasMoreElements(); i++) {

            Vector line = (Vector) lines.nextElement();

            ReduplicatedArticle reduplicatedArticle = (ReduplicatedArticle) line.get(6);
            reduplicatedArticle.setOrderPosition(i);

            deliveryOrder.addArticle(reduplicatedArticle);
        }

        baseService.saveOrUpdate(deliveryOrder);
    }

    public void resetInput(String deliveryOrderNumber) {

        deliveryOrderData.setDeliveryOrderNumber(deliveryOrderNumber);
        deliveryOrderData.setDeliveryOrderDate(new Date());
        deliveryOrderData.setPrefix("");
        deliveryOrderData.setSuffix("");

        JTable table = deliveryOrderTable.getTable();
        ((DefaultTableModel) table.getModel()).getDataVector().removeAllElements();
        table.updateUI();
    }

    public void setActualBusinessPartnerId(Long actualBusinessPartnerId) {
        orderTable.setBusinessPartnerId(actualBusinessPartnerId);
        orderTable.renewTableModel();
    }
}