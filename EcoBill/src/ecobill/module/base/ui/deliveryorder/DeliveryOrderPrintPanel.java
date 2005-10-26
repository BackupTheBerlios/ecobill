package ecobill.module.base.ui.deliveryorder;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.Person;
import ecobill.module.base.jasper.JasperViewer;
import ecobill.core.ui.MainFrame;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;

import java.util.*;
import java.awt.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// @todo document me!

/**
 * DeliveryOrderPrintPanel.
 * <p/>
 * User: Paul Chef
 * Date: 18.08.2005
 * Time: 16:45:41
 *
 * @author Andreas Weiler
 * @version $Id: DeliveryOrderPrintPanel.java,v 1.2 2005/10/26 12:10:17 gath Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderPrintPanel extends JPanel implements Internationalization {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(DeliveryOrderPrintPanel.class);

    /**
     * Der Hauptframe der Anwendung.
     */
    private MainFrame mainFrame;

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;

    /**
     * Das Übersichtspanel gibt Auskunft über alle Artikel per <code>JTable</code> und bietet
     * eine Eingabemaske (auch für Änderungen) von Artikeln.
     */
    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.DELIVERY_ORDER), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 0, 0));


    /**
      * Der <code>JasperViewer</code> enthält die Logik zum Füllen und zur Anzeige eines Reports.
      */
     private JasperViewer jasperViewer = new JasperViewer(this);

    /**
     * Standard Konstruktor.
     */
    public DeliveryOrderPrintPanel(MainFrame mainFrame, BaseService baseService) {

        this.mainFrame = mainFrame;
        this.baseService = baseService;

        initComponents();
        initLayout();

        reinitI18N();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        setBorder(border);
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {
        setLayout(new BorderLayout());
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        border.setTitle(WorkArea.getMessage(Constants.DELIVERY_ORDER));
    }

    /**
     * JRViewer
     */
    public void doJasper(Long id) throws Exception {

        // Entferne evtl. schon vorhandene Komponenten.
        removeAll();

        // TODO: Hier wäre es auch möglich direkt von Thread abzuleiten. SINNVOLL?!?
        // Starte nebenläufigen <code>JasperThread</code>.
        new Thread(new JasperThread(id)).start();
    }

    /**
     *
     * @param deliveryOrderId
     * @throws Exception
     */
    private void jasper(Long deliveryOrderId) throws Exception {

        mainFrame.setProgressPercentage(10);

        DeliveryOrder deliveryOrder = (DeliveryOrder) baseService.load(DeliveryOrder.class, deliveryOrderId);

        BusinessPartner bp = deliveryOrder.getBusinessPartner();
        Person person = bp.getPerson();
        ecobill.module.base.domain.Address address = bp.getAddress();

        Set reduplicatedArticles = deliveryOrder.getArticles();

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

        jasperViewer.addParameter("DATE", deliveryOrder.getDeliveryOrderDate());
        jasperViewer.addParameter("CUSTOMER_NUMBER", bp.getCustomerNumber());
        jasperViewer.addParameter("DELIVERY_ORDER_NUMBER", deliveryOrder.getDeliveryOrderNumber());

        jasperViewer.addParameter("PREFIX_FREE_TEXT", deliveryOrder.getPrefixFreetext());
        jasperViewer.addParameter("SUFFIX_FREE_TEXT", deliveryOrder.getSuffixFreetext());

        mainFrame.setProgressPercentage(50);

        if ("delivery_order".equals(deliveryOrder.getCharacterisationType())) {
            jasperViewer.view(mainFrame, WorkArea.getMessage(Constants.DELIVERY_ORDER_JRXML), reduplicatedArticles);
        }
        else if ("part_delivery_order".equals(deliveryOrder.getCharacterisationType())) {
            jasperViewer.view(mainFrame, WorkArea.getMessage(Constants.PART_DELIVERY_ORDER_JRXML), reduplicatedArticles);
        }

        mainFrame.setProgressPercentage(100);

        mainFrame.setProgressPercentage(0);
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

