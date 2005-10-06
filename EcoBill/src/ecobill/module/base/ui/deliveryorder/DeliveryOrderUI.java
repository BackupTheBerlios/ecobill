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
import ecobill.core.system.Internationalization;
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
 * @author Roman Georg Rädle
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

        tabbedPane.setEnabledAt(1, false);

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        try {
            deliveryOrderTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("delivery_order_table")));
            articleTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("article_table")));
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage(), fnfe);
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
        splitPanePanelRight = new JPanel();
        address = new Address();
        deliveryOrderData = new DeliveryOrderData();
        deliveryOrderTable = new DeliveryOrderTable(null, baseService);
        detail = new JPanel();

        MainFrame mainFrame = (MainFrame) applicationContext.getBean("mainFrame");

        deliveryOrderPrintPanel = new DeliveryOrderPrintPanel(mainFrame, baseService);

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

        splitPane.setDividerLocation(200);
        splitPane.setLastDividerLocation(84);
        splitPane.setLeftComponent(articleTable);

        GroupLayout splitPanePanelRightLayout = new GroupLayout(splitPanePanelRight);
        splitPanePanelRight.setLayout(splitPanePanelRightLayout);
        splitPanePanelRightLayout.setHorizontalGroup(
            splitPanePanelRightLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, splitPanePanelRightLayout.createSequentialGroup()
                .addContainerGap()
                .add(splitPanePanelRightLayout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, deliveryOrderTable, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                    .add(GroupLayout.TRAILING, splitPanePanelRightLayout.createSequentialGroup()
                        .add(address, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(deliveryOrderData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .add(71, 71, 71))
        );
        splitPanePanelRightLayout.setVerticalGroup(
            splitPanePanelRightLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, splitPanePanelRightLayout.createSequentialGroup()
                .addContainerGap()
                .add(splitPanePanelRightLayout.createParallelGroup(GroupLayout.LEADING, false)
                    .add(address, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(GroupLayout.TRAILING, deliveryOrderData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(deliveryOrderTable, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );
        splitPane.setRightComponent(splitPanePanelRight);

        GroupLayout overviewLayout = new GroupLayout(overview);
        overview.setLayout(overviewLayout);
        overviewLayout.setHorizontalGroup(
            overviewLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(verticalButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(splitPane, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                .addContainerGap())
        );
        overviewLayout.setVerticalGroup(
            overviewLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(overviewLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(splitPane, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                    .add(verticalButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        tabbedPane.addTab("Übersicht", overview);

        detail.setLayout(new BorderLayout());

        detail.add(deliveryOrderPrintPanel, BorderLayout.CENTER);

        tabbedPane.addTab("Detail", detail);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

    }

    /**
     * Erneuert das <code>TableModel</code> der Artikel Tabelle.
     */
    public void renewArticleTableModel() {
        articleTable.renewTableModel();
    }

    private Address address;
    private DeliveryOrderData deliveryOrderData;
    private ArticleTable articleTable;
    private DeliveryOrderTable deliveryOrderTable;
    private JPanel detail;
    private JPanel overview;
    private JSplitPane splitPane;
    private JPanel splitPanePanelRight;
    private JTabbedPane tabbedPane;
    private VerticalButton verticalButton;

    private DeliveryOrderPrintPanel deliveryOrderPrintPanel;

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
        deliveryOrder.setDeliveryOrderDate(new Date());
        deliveryOrder.setDeliveryOrderNumber("deli-number");
        deliveryOrder.setPrefixFreetext("PREFIX_TEXT");
        deliveryOrder.setSuffixFreetext("SUFFIX_TEXT");
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
}