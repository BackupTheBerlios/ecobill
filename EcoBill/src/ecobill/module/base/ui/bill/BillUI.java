package ecobill.module.base.ui.bill;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
<<<<<<< BillUI.java
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
=======
import org.springframework.beans.BeansException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import javax.swing.*;
import javax.swing.event.TableModelListener;

import ecobill.core.system.Internationalization;
>>>>>>> 1.7
import ecobill.core.util.FileUtils;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;
import ecobill.core.ui.MainFrame;
<<<<<<< BillUI.java
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
=======
import ecobill.module.base.ui.deliveryorder.*;
import ecobill.module.base.ui.article.ArticleTable;
import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.*;
>>>>>>> 1.7

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
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 22.10.2005
 * Time: 13:02:58
 * To change this template use File | Settings | File Templates.
 */
public class BillUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {


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

<<<<<<< BillUI.java
        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.

=======
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

        //tabbedPane.setEnabledAt(1, false);

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        /*
        try {
            if (leftTable instanceof DeliveryOrderTable) {
                leftTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("delivery_order_table")));
            }
            else if (leftTable instanceof ArticleTable)
            {
                leftTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("article_table")));
            }
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage(), fnfe);
            }
        }
        */

>>>>>>> 1.7
        reinitI18N();
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        if (LOG.isInfoEnabled()) {
            LOG.info("Schlie�e OverviewPanel und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu k�nnen.
        if (leftTable instanceof DeliveryOrderTable) {
            leftTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
        }
        else if (leftTable instanceof ArticleTable) {
            leftTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        }
<<<<<<< BillUI.java

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu k�nnen.
        //deliveryOrderTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
        //articleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
    }
=======

    }
>>>>>>> 1.7

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        mainFrame = (MainFrame) applicationContext.getBean("mainFrame");
        tabbedPane = new JTabbedPane();
        billCreation = new BillCreation(baseService, mainFrame);

<<<<<<< BillUI.java
        billOverviewPanel = new BillOverviewPanel(baseService, mainFrame);
=======
        leftTable = new DeliveryOrderTable(null, baseService);
        verticalButton = new VerticalButton();

        tabbedPane = new JTabbedPane();
       // overview = new JPanel();
        splitPane = new JSplitPane();
        panelLeft = new JPanel();

        overviewPanel = new JPanel();
//        detail = new JPanel();

//        MainFrame mainFrame = (MainFrame) applicationContext.getBean("mainFrame");

//        deliveryOrderPrintPanel = new DeliveryOrderPrintPanel(mainFrame, baseService);

        
>>>>>>> 1.7
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

<<<<<<< BillUI.java
        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), billCreation);
        tabbedPane.addTab(WorkArea.getMessage(Constants.DETAIL), billOverviewPanel);
=======
        splitPane.setBorder(null);
        splitPane.setDividerLocation(200);
        splitPane.setOneTouchExpandable(true);

        GroupLayout panelLeftLayout = new GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
            panelLeftLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelLeftLayout.createSequentialGroup()
                .add(leftTable, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLeftLayout.setVerticalGroup(
            panelLeftLayout.createParallelGroup(GroupLayout.LEADING)
            .add(leftTable, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        splitPane.setLeftComponent(panelLeft);
        splitPane.setRightComponent(new BillRightPanel(baseService));

        GroupLayout overviewLayout = new GroupLayout(this);
        this.setLayout(overviewLayout);
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




        //OverviewPanel.this.add(verticalButton);
        //OverviewPanel.this.add(splitPane);

        //billRightPanel = new BillRightPanel(baseService);

        //billRightPanel.add(billPreviewTable);

        //overview = new OverviewPanel(orderTable, billRightPanel);
        //overview.addButtonToVerticalButton(1, new ImageIcon("images/delivery_order_new.png"), "Neue Rechnung erstellen", null);
        ActionListener a2 = new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                Bill bill = new Bill();

                for (int i = 0; i < orderTable.getTable().getRowCount(); i++) {
                    if ((orderTable.getTable().getValueAt(i, 0) instanceof Boolean)
                        && ((Boolean) (orderTable.getTable().getValueAt(i, 0))).booleanValue()) {

                        Object o = baseService.load(DeliveryOrder.class, ((IdValueItem) orderTable.getTable().getValueAt(i, 1)).getId());

                        if (o instanceof DeliveryOrder) {

                            DeliveryOrder deliveryOrder = (DeliveryOrder) o;
>>>>>>> 1.7

        add(tabbedPane, BorderLayout.CENTER);

<<<<<<< BillUI.java
=======
                            double sum = 0;

                            for (ReduplicatedArticle article : reduplicatedArticles) {

                                sum = sum + article.getPrice() * article.getQuantity();
                            }

                            BillPreviewCollection bpc = new BillPreviewCollection(deliveryOrder.getDeliveryOrderNumber(), deliveryOrder.getDeliveryOrderDate(), sum);

                            deliveryOrder.setPreparedBill(true);
                            bill.addDeliveryOrder(deliveryOrder);

                            billRightPanel.addDeliveryOrder(bpc);
                        }
                    }

                    // billPreviewTable = new BillPreviewTable(1,baseService);
                    //billRightPanel.add(billPreviewTable);
                    //overview.validate();
                }

                Long billNumber = baseService.getNextBillNumber();

                System.out.println("N�CHSTE RECHNUNGSNUMMER: " + billNumber);

                bill.setBusinessPartner((BusinessPartner) baseService.load(BusinessPartner.class, actualBusinessPartnerId));
                bill.setBillNumber(billNumber);
                bill.setBillDate(Calendar.getInstance().getTime());

                baseService.saveOrUpdate(bill);

            }
        };
        //overview.addButtonToVerticalButton(2, new ImageIcon("images/delivery_order_ok.png"), "Rechung speichern", a2);
        //overview.addButtonToVerticalButton(4, new ImageIcon("images/refresh.png"), "Aktualisieren", null);

        //overview.init();
        //BillUI.this.add(overview, BorderLayout.CENTER);
>>>>>>> 1.7
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

    }

<<<<<<< BillUI.java
    private JTabbedPane tabbedPane;
    private BillCreation billCreation;
    private MainFrame mainFrame;
    private BillOverviewPanel billOverviewPanel;

    public BillCreation getBillCreation() {
        return billCreation;
=======
    private AbstractTablePanel leftTable;
    private JPanel overviewPanel;
 //   private JPanel detail;
 //   private JPanel overview;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JPanel panelDataRight;
    private JSplitPane splitPane;
    private JTabbedPane tabbedPane;
    private VerticalButton verticalButton;

    //private OverviewPanel overview;
    private OrderTableWithCB orderTable;
    private long actualBusinessPartnerId;
    private BillRightPanel billRightPanel;

    public void setActualBusinessPartnerId(long actualBusinessPartnerId) {
        this.actualBusinessPartnerId = actualBusinessPartnerId;
        orderTable.updateDataCollectionFromDB(actualBusinessPartnerId);
        System.out.println("M�sste ein renewTableModel machen");
        orderTable.renewTableModel();
        validate();
>>>>>>> 1.7
    }

    public BillOverviewPanel getBillOverviewPanel() {
        return billOverviewPanel;
    }
}
