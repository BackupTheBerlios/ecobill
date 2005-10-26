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
import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.ReduplicatedArticle;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.core.util.FileUtils;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;
import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.ui.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Roman Georg R�dle
 */
public class DeliveryOrderUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k�nnen in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(DeliveryOrderUI.class);

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

        tabbedPane.setEnabledAt(1, false);

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
            LOG.info("Schlie�e DeliveryOrderUI und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu k�nnen.
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
        address = new Address();
        deliveryOrderDataPanel = new JPanel();
        deliveryOrderData = new DeliveryOrderData(baseService);
        deliveryOrderTable = new DeliveryOrderTable(actualDeliveryOrderId, baseService);
        this.orderTable = new OrderTable(baseService);
        detail = new JPanel();

        MainFrame mainFrame = (MainFrame) applicationContext.getBean("mainFrame");

        deliveryOrderPrintPanel = new DeliveryOrderPrintPanel(mainFrame, baseService);
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

                //if (e.getClickCount() == 2) {
                int row = articleTable.getTable().getSelectedRow();

                IdKeyItem idKeyItem = (IdKeyItem) articleTable.getTableModel().getValueAt(row, 0);

                System.out.println("ID: " + idKeyItem.getId());

                showAddArticleDialog(idKeyItem.getId());
                //}
            }
        });

        verticalButton.getButton1().setVisible(true);
        verticalButton.getButton1().setIcon(new ImageIcon("images/delivery_order_new.png"));

        verticalButton.getButton2().setVisible(true);
        verticalButton.getButton2().setIcon(new ImageIcon("images/delivery_order_ok.png"));
        verticalButton.getButton2().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateDeliveryOrder();

                tabbedPane.setEnabledAt(1, true);
            }
        });

        verticalButton.getButton3().setVisible(true);
        verticalButton.getButton3().setIcon(new ImageIcon("images/delivery_order_delete.png"));

        verticalButton.getButton4().setVisible(true);
        verticalButton.getButton4().setIcon(new ImageIcon("images/refresh.png"));

        tabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {

                if (tabbedPane.getSelectedComponent().equals(detail)) {
                    System.out.println("DETAIL ANSICHT");

                    try {
                        deliveryOrderPrintPanel.doJasper(actualDeliveryOrderId);

                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
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

        detail.setLayout(new BorderLayout());

        detail.add(deliveryOrderPrintPanel, BorderLayout.CENTER);

        tabbedPane.addTab(WorkArea.getMessage(Constants.DETAIL), detail);

        OverviewPanel deliveryOrderOverview = new OverviewPanel(orderTable, deliveryOrderPrintPanelOverview);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = orderTable.getTable().getSelectedRow();
                    deliveryOrderPrintPanelOverview.doJasper(((IdValueItem) orderTable.getTable().getValueAt(row,0)).getId());
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        ActionListener actionListenerClose = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    deliveryOrderPrintPanelOverview.removeAll();
                    deliveryOrderPrintPanelOverview.repaint();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        deliveryOrderOverview.addButtonToVerticalButton(1,new ImageIcon("images/open.png"), "Lieferschein in Vorschaufernster anzeigen", actionListener );
        deliveryOrderOverview.addButtonToVerticalButton(2,new ImageIcon("images/exit.png"), "Vorschaufernster schlie�en", actionListenerClose );
        deliveryOrderOverview.init();
        tabbedPane.addTab(null,deliveryOrderOverview);

        add(tabbedPane, BorderLayout.CENTER);

    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));
        tabbedPane.setTitleAt(1, WorkArea.getMessage(Constants.DETAIL));
        tabbedPane.setTitleAt(2, WorkArea.getMessage(Constants.MAX_OVERVIEW));

        verticalButton.reinitI18N();
        deliveryOrderData.reinitI18N();


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

    private Address address;
    private JPanel addressPanel;
    private ArticleTable articleTable;
    private DeliveryOrderData deliveryOrderData;
    private JPanel deliveryOrderDataPanel;
    private DeliveryOrderTable deliveryOrderTable;
    private OrderTable orderTable;
    private JPanel detail;
    private JPanel overview;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JSplitPane splitPane;
    private JTabbedPane tabbedPane;
    private JTabbedPane tabbedPaneRight;
    private VerticalButton verticalButton;

    private DeliveryOrderPrintPanel deliveryOrderPrintPanel;
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

    private Long actualDeliveryOrderId;
    private Long actualBusinessPartnerId;

    private void saveOrUpdateDeliveryOrder() {


        DeliveryOrder deliveryOrder = null;
        if (actualDeliveryOrderId != null) {
            deliveryOrder = (DeliveryOrder) baseService.load(DeliveryOrder.class, actualDeliveryOrderId);
        }

        if (deliveryOrder == null) {
            deliveryOrder = new DeliveryOrder();
        }

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

            ReduplicatedArticle reduplicatedArticle = (ReduplicatedArticle) line.get(5);
            reduplicatedArticle.setOrderPosition(i);

            deliveryOrder.addArticle(reduplicatedArticle);
        }

        baseService.saveOrUpdate(deliveryOrder);

        actualDeliveryOrderId = deliveryOrder.getId();
    }

    public void setActualBusinessPartnerId(Long actualBusinessPartnerId) {
        this.actualBusinessPartnerId = actualBusinessPartnerId;
        orderTable.updateDataCollectionFromDB(actualBusinessPartnerId);
        orderTable.renewTableModel();
        this.validate();

    }


}