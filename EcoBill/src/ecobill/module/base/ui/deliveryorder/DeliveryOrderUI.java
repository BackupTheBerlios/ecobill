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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author  Roman Georg Rädle
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

        splitPane = new JSplitPane();

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

        deliveryOrderPanel = new JPanel();
        address = new Address();
        deliveryOrderTable = new DeliveryOrderTable(null, baseService);
        verticalButton = new VerticalButton();

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
            }
        });

        verticalButton.getButton3().setVisible(true);
        verticalButton.getButton3().setIcon(new ImageIcon("images/delivery_order_delete.png"));

        verticalButton.getButton4().setVisible(true);
        verticalButton.getButton4().setIcon(new ImageIcon("images/refresh.png"));

        splitPane.setDividerLocation(200);
        splitPane.setLeftComponent(articleTable);
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        GroupLayout jPanel1Layout = new GroupLayout(deliveryOrderPanel);
        deliveryOrderPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.LEADING)
            .add(address, GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
            .add(deliveryOrderTable, GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                .add(address, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(deliveryOrderTable, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
        );
        splitPane.setRightComponent(deliveryOrderPanel);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(verticalButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(splitPane, GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, splitPane, GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                    .add(GroupLayout.LEADING, verticalButton, GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE))
                .addContainerGap())
        );
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
    private ArticleTable articleTable;
    private DeliveryOrderTable deliveryOrderTable;
    private JPanel deliveryOrderPanel;
    private JSplitPane splitPane;
    private VerticalButton verticalButton;

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
        deliveryOrder.setCharacterisationType("delivery");
        deliveryOrder.setDeliveryOrderDate(new Date());
        deliveryOrder.setDeliveryOrderNumber("deli-number");
        deliveryOrder.setPrefixFreetext("PREFIX_TEXT");
        deliveryOrder.setSuffixFreetext("SUFFIX_TEXT");
        deliveryOrder.setPreparedBill(false);

        Vector dataVector = ((DefaultTableModel) deliveryOrderTable.getTable().getModel()).getDataVector();

        Enumeration lines = dataVector.elements();
        while (lines.hasMoreElements()) {

            Vector line = (Vector) lines.nextElement();

            deliveryOrder.addArticle((ReduplicatedArticle) line.get(5));
        }

        baseService.saveOrUpdate(deliveryOrder);
    }
}