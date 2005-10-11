package ecobill.module.base.ui.bill;

import ecobill.core.system.Internationalization;
import ecobill.module.base.ui.deliveryorder.DeliveryOrderUI;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.ReduplicatedArticle;

import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import java.util.Properties;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 10.10.2005
 * Time: 21:10:13
 * To change this template use File | Settings | File Templates.
 */
public class BillRightPanel extends JPanel implements Internationalization {
    /**
     * Erzeugt eine neues <code>BillRight</code> Panel.
     */
    public BillRightPanel(BaseService baseService) {

        this.baseService = baseService;
        billPreviewTable = new BillPreviewTableN();
        dataInputPanel = new Panel();
        initComponents();
        initLayout();

        reinitI18N();
    }

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
        System.out.println("In BillRightPanel");

    }


    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {
        GroupLayout panelRightLayout = new GroupLayout(this);
        this.setLayout(panelRightLayout);
        panelRightLayout.setHorizontalGroup(
            panelRightLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelRightLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelRightLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(billPreviewTable, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .add(dataInputPanel, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)))
        );
        panelRightLayout.setVerticalGroup(
            panelRightLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.LEADING, panelRightLayout.createSequentialGroup()
                .add(dataInputPanel, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(billPreviewTable, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
        );

    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

    }

    private Panel dataInputPanel;
    private BillPreviewTableN billPreviewTable;

    public void addDeliveryOrder(BillPreviewCollection bpc) {
 /*       double sum =0;
        java.util.Set<ReduplicatedArticle> redArticles = dO.getArticles();
        System.out.println("Anzahl an Artikeln =" + redArticles.size());
        while(redArticles.iterator().hasNext()) {
            ReduplicatedArticle ra = redArticles.iterator().next();
            sum = sum + ra.getPrice() * ra.getQuantity();
        }
        System.out.println("sum:" + sum);    */
        //BillPreviewCollection bpc = new BillPreviewCollection(dO.getDeliveryOrderNumber(),dO.getDeliveryOrderDate(),sum);
        billPreviewTable.renewTableModel(bpc);

    }
}
