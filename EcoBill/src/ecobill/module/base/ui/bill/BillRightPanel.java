package ecobill.module.base.ui.bill;

import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.ui.MainFrame;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.*;
import ecobill.module.base.jasper.JasperViewer;

import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import java.util.Set;
import java.util.HashSet;
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
    public BillRightPanel(boolean previewTableNeeded) {
        this.previewTableNeeded = previewTableNeeded;
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
    private static final Log LOG = LogFactory.getLog(BillRightPanel.class);

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
        if (!previewTableNeeded) {
            panelRightLayout.setHorizontalGroup(
                    panelRightLayout.createParallelGroup(GroupLayout.LEADING)
                            .add(GroupLayout.LEADING, panelRightLayout.createSequentialGroup()
                            .addContainerGap()
                            .add(panelRightLayout.createParallelGroup(GroupLayout.LEADING)
                            //.add(billPreviewTable, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)))
                            .add(dataInputPanel, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)))
            );
            panelRightLayout.setVerticalGroup(
                    panelRightLayout.createParallelGroup(GroupLayout.LEADING)
                            .add(GroupLayout.LEADING, panelRightLayout.createSequentialGroup()
                            .add(dataInputPanel, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.RELATED))
                    // .add(billPreviewTable, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
            );
        }
        else {
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
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

    }

    /**
     * Der <code>JasperViewer</code> enthält die Logik zum Füllen und zur Anzeige eines Reports.
     */
    private JasperViewer jasperViewer = new JasperViewer(this);
    private boolean previewTableNeeded;

    private Panel dataInputPanel;
    private BillPreviewTableN billPreviewTable;

    /**
     * Der Hauptframe der Anwendung.
     */
    private MainFrame mainFrame;

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

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

    /**
     * JRViewer
     */
    public void doJasper(Long id) throws Exception {

        // Entferne evtl. schon vorhandene Komponenten.
        removeAll();

        // TODO: Hier wäre es auch möglich direkt von Thread abzuleiten. SINNVOLL?!?
        // Starte nebenläufigen <code>JasperThread</code>.
        new Thread(new BillRightPanel.JasperThread(id)).start();
        validate();
    }

    /**
     * @param billId
     * @throws Exception
     */
    private void jasper(Long billId) throws Exception {
        String deliveryOrderNumbers = "";
        mainFrame.setProgressPercentage(10);

        System.out.println("billId:" + billId);
        Bill bill = (Bill) baseService.load(Bill.class, billId);
        System.out.println("BillDate" + bill.getBillDate());

        BusinessPartner bp = bill.getBusinessPartner();
        Person person = bp.getPerson();
        ecobill.module.base.domain.Address address = bp.getAddress();

        Set reduplicatedArticles = new HashSet();
        Set deliveryOrders = bill.getDeliveryOrders();
        System.out.println("Size von deliveryOrders:" + deliveryOrders.size());

        int i = 0;
        for (Object o : deliveryOrders) {
            if (i == 0) {
                deliveryOrderNumbers = ((DeliveryOrder) o).getDeliveryOrderNumber();
            }
            else if (i <= deliveryOrders.size() && i > 0) {
                deliveryOrderNumbers = deliveryOrderNumbers + "," + ((DeliveryOrder) o).getDeliveryOrderNumber();
            }

            DeliveryOrder order = (DeliveryOrder) o;

            System.out.println("IST: [" + order.getDeliveryOrderNumber() + "]" + order.getArticles().size());

            reduplicatedArticles.addAll(((DeliveryOrder) o).getArticles());
        }

        mainFrame.setProgressPercentage(30);

        // Die Parameter, die an den <code>JasperViewer</code> übergeben werden und zum erstellen des
        // Reports nötig sind.
        jasperViewer.addParameter("TITLE", bp.getAssuredTitle());
        jasperViewer.addParameter("FIRSTNAME", person.getFirstname());
        jasperViewer.addParameter("LASTNAME", person.getLastname());
        jasperViewer.addParameter("STREET", address.getStreet());
        jasperViewer.addParameter("ZIP_CODE", address.getZipCode());
        jasperViewer.addParameter("CITY", address.getCity());
        jasperViewer.addParameter("COUNTRY", address.getCountry().toString());
        jasperViewer.addParameter("COUNTY", address.getCounty().toString());

        jasperViewer.addParameter("COMPANY_NAME", bp.getCompanyName());
        jasperViewer.addParameter("BRANCH", bp.getCompanyBranch());
        jasperViewer.addParameter("PERSON_TITLE", person.getTitle().toString());

        jasperViewer.addParameter("DATE", bill.getBillDate());
        jasperViewer.addParameter("CUSTOMER_NUMBER", bp.getCustomerNumber());
        jasperViewer.addParameter("BILL_NUMBER", bill.getBillNumber().toString());
        jasperViewer.addParameter("DELIVERY_ORDER_NUMBERS", deliveryOrderNumbers);

        mainFrame.setProgressPercentage(50);

        System.out.println("SIUZEASDFA: " + reduplicatedArticles.size());

        jasperViewer.view(mainFrame, WorkArea.getMessage(Constants.BILL_JRXML), reduplicatedArticles);

        mainFrame.setProgressPercentage(100);

        mainFrame.setProgressPercentage(0);

        mainFrame.validate();

    }

    public void removeJasperViewer() {
        jasperViewer.remove();
    }

    /**
     * Dieser <code>Thread</code> erzeugt die Report Seiten und zeigt diese auf
     * dem <code>JPanel</code> an. Er ist nebenläufig zum eigentlichen Programm.
     */
    private class JasperThread implements Runnable {

        /**
         * Die id zum Laden des <code>Object</code>.
         */
        private Long id;

        /**
         * Ein neuer <code>JasperThread</code> der eine id zum Laden eines, für den
         * <code>JasperViewer</code> vorgesehenen, <code>Object</code>.
         *
         * @param id Die id zum Laden des <code>Object</code>.
         */
        protected JasperThread(Long id) {
            this.id = id;
        }

        /**
         * @see Runnable#run()
         */
        public void run() {
            try {
                jasper(id);
                validate();
            }
            catch (Exception e) {
                if (LOG.isErrorEnabled()) {
                    LOG.error(e.getMessage(), e);
                }
                e.printStackTrace();
            }
        }
    }

}
