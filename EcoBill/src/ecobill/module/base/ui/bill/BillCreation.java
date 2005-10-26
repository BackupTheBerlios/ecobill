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
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 10.10.2005
 * Time: 19:13:20
 * To change this template use File | Settings | File Templates.
 */
public class BillCreation extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(BillCreation.class);

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
            LOG.info("Schließe BillUI und speichere die Daten.");
        }
        /*
                // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
                // zu können.
                deliveryOrderTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("delivery_order_table"))));
                articleTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("article_table"))));
        */    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        System.out.println("jetzt wird sie zum ersten mal gefüllt" + actualBusinessPartnerId);

        orderTable = new OrderTableWithCB(actualBusinessPartnerId, baseService) /*{
            protected KeyListener[] createKeyListeners() {
                return null;
            }

            protected MouseListener[] createMouseListeners() {
                return null;
            }

            protected TableModelListener[] createTableModelListeners() {
                return null;
            }
        }*/;

        billRightPanel = new BillRightPanel(true);
        billRightPanel.setMainFrame(mainFrame);
        billRightPanel.setBaseService(baseService);

        /*orderTable.getTable().getColumnModel().removeColumnModelListener(orderTable.getTable());

        orderTable.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                //if (e.getClickCount() == 2) {
                int row = orderTable.getTable().getSelectedRow();

                IdValueItem idValueItem = (IdValueItem) orderTable.getTableModel().getValueAt(row, 0);

                System.out.println("ID: " + idValueItem.getId());

  //showAddArticleDialog(idValueItem.getId());
                //}
            }
        });*/

    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {
        setLayout(new BorderLayout());

        //billRightPanel.add(billPreviewTable);

        overview = new OverviewPanel(orderTable, billRightPanel);
        ActionListener a1 = new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                try {
               billRightPanel.doJasper(new Long(4));
                }
                catch (Exception exception) {

                }
            }
        };

        overview.addButtonToVerticalButton(1, new ImageIcon("images/delivery_order_new.png"), "Neue Rechnung erstellen", a1);
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

                            Set<ReduplicatedArticle> reduplicatedArticles = deliveryOrder.getArticles();

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

                    billPreviewTable = new BillPreviewTable(new Long(1),baseService);
                    billRightPanel.add(billPreviewTable);
                    overview.validate();
                }

                Long billNumber = baseService.getNextBillNumber();

                System.out.println("NÄCHSTE RECHNUNGSNUMMER: " + billNumber);

                bill.setBusinessPartner((BusinessPartner) baseService.load(BusinessPartner.class, actualBusinessPartnerId));
                bill.setBillNumber(billNumber);
                bill.setBillDate(Calendar.getInstance().getTime());

                baseService.saveOrUpdate(bill);

            }
        };
        overview.addButtonToVerticalButton(2, new ImageIcon("images/delivery_order_ok.png"), "Rechung speichern", a2);


        overview.addButtonToVerticalButton(4, new ImageIcon("images/refresh.png"), "Aktualisieren", null);

        overview.init();
        BillCreation.this.add(overview, BorderLayout.CENTER);
    }


    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

    }

    private OverviewPanel overview;
    private OrderTableWithCB orderTable;
    private long actualBusinessPartnerId;
    private BillRightPanel billRightPanel;
    private BillPreviewTable billPreviewTable;
    private MainFrame mainFrame;

    public void setActualBusinessPartnerId(long actualBusinessPartnerId) {
        this.actualBusinessPartnerId = actualBusinessPartnerId;
        orderTable.updateDataCollectionFromDB(actualBusinessPartnerId);
        orderTable.renewTableModel();
        validate();
    }


}
