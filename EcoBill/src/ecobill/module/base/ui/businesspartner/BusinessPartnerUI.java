package ecobill.module.base.ui.businesspartner;

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
import ecobill.module.base.ui.deliveryorder.DeliveryOrderUI;
import ecobill.module.base.ui.bill.BillUI;
import ecobill.module.base.ui.component.JToolBarButton;
import ecobill.module.base.domain.*;
import ecobill.core.util.FileUtils;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
import ecobill.core.ui.MainFrame;

import javax.swing.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * Die <code>BusinessPartnerUI</code> erstellt das User Interface zur Eingabe von Benutzerdaten.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: BusinessPartnerUI.java,v 1.21 2006/01/29 23:16:45 raedler Exp $
 * @since EcoBill 1.0
 */
public class BusinessPartnerUI extends JPanel implements ApplicationContextAware, InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k�nnen in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(BusinessPartnerUI.class);

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
     * Creates new form BusinessPartnerUI
     */
    public void afterPropertiesSet() {

        // Initialisieren der Komponenten und des Layouts.
        initComponents();
        initLayout();

        add(createToolBar(), BorderLayout.NORTH);

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        try {
            overviewBusinessPartnerTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("business_partner_table")));
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
            LOG.info("Schlie�e BusinessPartnerUI und speichere die Daten.");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu k�nnen.
        overviewBusinessPartnerTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("business_partner_table"))));
    }

    private JPanel overview;
    private BusinessPartnerTable overviewBusinessPartnerTable;
    private Input overviewInput;
    private InputBanking overviewInputBanking;
    private InputContact overviewInputContact;
    private InputFirm overviewInputFirm;
    private JTabbedPane tabbedPane;

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        tabbedPane = new JTabbedPane();
        overview = new JPanel();
        overviewBusinessPartnerTable = new BusinessPartnerTable(this, baseService);
        overviewInputContact = new InputContact();
        overviewInputBanking = new InputBanking();
        overviewInputFirm = new InputFirm();
        overviewInput = new Input(baseService);

        NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.CUSTOMER);
        resetInput(numberSequence.getNextNumber());
    }

    JToolBarButton deliveryOrderB = new JToolBarButton(new ImageIcon("images/delivery_order_new.png"));
    JToolBarButton newBill = new JToolBarButton(new ImageIcon("images/bill_new.png"));

    /**
     * Erzeugt die <code>JToolBar</code> f�r dieses User Interface.
     */
    private JToolBar createToolBar() {

        JToolBar toolBar = new JToolBar();

        JToolBarButton newBusinessPartner = new JToolBarButton(new ImageIcon("images/business_partner_new.png"));
        newBusinessPartner.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // Setzt den Lieferschein Button disabled.
                deliveryOrderB.setEnabled(false);

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.CUSTOMER);

                // L�scht den Inhalt der Eingabefelder.
                resetInput(numberSequence.getNextNumber());
            }
        });

        JToolBarButton okBusinessPartner = new JToolBarButton(new ImageIcon("images/business_partner_ok.png"));
        okBusinessPartner.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // Speichert den aktuell eingegebenen Gesch�ftspartner oder aktualisiert diesen
                // gegebenenfalls.
                saveOrUpdateBusinessPartner();

                // Erneuert die <code>BusinessPartnerTable</code>.
                overviewBusinessPartnerTable.renewTableModel();

                // Setzt den Lieferschein Button enabled.
                deliveryOrderB.setEnabled(true);

                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.CUSTOMER);

                String actualCustomerNumber = overviewInput.getCustomerNumber();

                if (numberSequence.compareWithNumber(actualCustomerNumber) <= -1) {

                    numberSequence.setNumber(actualCustomerNumber);

                    baseService.saveOrUpdate(numberSequence);
                }
            }
        });

        JToolBarButton deleteBusinessPartner = new JToolBarButton(new ImageIcon("images/business_partner_delete.png"));
        deleteBusinessPartner.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // L�scht den aktuell markierten Gesch�ftspartner.
                baseService.delete(BusinessPartner.class, actualBusinessPartnerId);

                // Erneuert die <code>BusinessPartnerTable</code>.
                overviewBusinessPartnerTable.renewTableModel();

                // Setzt den Lieferschein Button disabled.
                deliveryOrderB.setEnabled(false);
                newBill.setEnabled(false);

                // L�scht die Felder nach dem L�schen des Kunden und setzt die
                // n�chste Kundennummer.
                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.CUSTOMER);
                resetInput(numberSequence.getNextNumber());
            }
        });

        JToolBarButton refreshBusinessPartner = new JToolBarButton(new ImageIcon("images/refresh.png"));
        refreshBusinessPartner.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // Erneuert die <code>BusinessPartnerTable</code>.
                overviewBusinessPartnerTable.renewTableModel();
            }
        });

        deliveryOrderB.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // L�dt den aktuell markierten Gesch�ftspartner.
                BusinessPartner businessPartner = (BusinessPartner) baseService.load(BusinessPartner.class, actualBusinessPartnerId);

                // Holt das Lieferschein User Interface aus dem <code>ApplicationContext</code> um den
                // Gesch�ftspartner zu setzen und ihm dann einen Lieferschein auszustellen.
                DeliveryOrderUI deliveryOrderUI = (DeliveryOrderUI) applicationContext.getBean("deliveryOrderUI");
                deliveryOrderUI.setBusinessPartner(businessPartner);
                deliveryOrderUI.setDeliveryOrder(new DeliveryOrder());

                // Wechselt auf das Lieferschein User Interface.
                MainFrame mainFrame = (MainFrame) applicationContext.getBean("mainFrame");
                mainFrame.setSelectedTab(3);
            }
        });
        deliveryOrderB.setEnabled(false);

        newBill.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // L�dt den aktuell markierten Gesch�ftspartner.
                BusinessPartner businessPartner = (BusinessPartner) baseService.load(BusinessPartner.class, actualBusinessPartnerId);

                // Holt das Bill User Interface aus dem <code>ApplicationContext</code> um den
                // Gesch�ftspartner zu setzen und ihm dann eine Rechnung auszustellen.
                BillUI billUI = (BillUI) applicationContext.getBean("billUI");
                billUI.setBusinessPartner(businessPartner);
                billUI.setActualBusinessPartnerId(businessPartner.getId());

                // Setzt die n�chste Rechnungsnummer.
                NumberSequence numberSequence = baseService.getNumberSequenceByKey(Constants.BILL);
                billUI.resetInput(numberSequence.getNextNumber());

                // Wechselt auf das Lieferschein User Interface.
                MainFrame mainFrame = (MainFrame) applicationContext.getBean("mainFrame");
                mainFrame.setSelectedTab(4);
            }
        });
        newBill.setEnabled(false);

        toolBar.add(newBusinessPartner);
        toolBar.add(okBusinessPartner);
        toolBar.add(deleteBusinessPartner);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(refreshBusinessPartner);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(deliveryOrderB);
        toolBar.add(newBill);

        return toolBar;
    }

    /**
     * Initilisiert das Layout und somit die Positionen an denen die Komponenten
     * liegen.
     */
    private void initLayout() {

        setLayout(new BorderLayout());

        GroupLayout overviewLayout = new GroupLayout(overview);

        overview.setLayout(overviewLayout);

        overviewLayout.setHorizontalGroup(
                overviewLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(overviewLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                                .add(overviewBusinessPartnerTable, GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
                                .addContainerGap())
                        .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                        .add(overviewInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(overviewInputFirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(overviewInputContact, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(overviewInputBanking, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .add(10, 10, 10))))
        );
        overviewLayout.setVerticalGroup(
                overviewLayout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(overviewLayout.createParallelGroup(GroupLayout.LEADING, false)
                                .add(GroupLayout.TRAILING, overviewInputFirm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(overviewInputBanking, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(overviewInputContact, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(GroupLayout.TRAILING, overviewInput, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(overviewBusinessPartnerTable, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                        .addContainerGap())
        );
        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), overview);

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));

        overviewInput.reinitI18N();
        overviewInputBanking.reinitI18N();
        overviewInputContact.reinitI18N();
        overviewInputFirm.reinitI18N();

        /* TODO: repair me!!!
        overviewVerticalButton.reinitI18N();

        overviewVerticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.BUTTON1_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.BUTTON2_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.BUTTON3_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.BUTTON4_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton6().setToolTipText(WorkArea.getMessage(Constants.BUTTON6_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton7().setToolTipText(WorkArea.getMessage(Constants.BUTTON7_CUSTOMER_TOOLTIP));
        */
    }

    /**
     * Setzt die Eingabefelder zur�ck und als Kundennummer wird die im Parameter
     * angegebene Nummer gesetzt.
     *
     * @param nextCustomerNumber Setzt im Kundennummer Eingabefeld diese Kundennumer.
     */
    public void resetInput(String nextCustomerNumber) {

        actualBusinessPartnerId = null;

        overviewInput.resetInput();

        overviewInput.setCustomerNumber(nextCustomerNumber);

        overviewInputFirm.resetInput();
        overviewInputContact.resetInput();
        overviewInputBanking.resetInput();
    }

    private Long actualBusinessPartnerId;

    public void showBusinessPartner(Long id) {

        actualBusinessPartnerId = id;

        BusinessPartner businessPartner = (BusinessPartner) baseService.load(BusinessPartner.class, id);

        Person person = null;
        Address address = null;
        Banking banking = null;

        if (businessPartner != null) {
            person = businessPartner.getPerson();
            address = businessPartner.getAddress();
            banking = businessPartner.getBanking();

            overviewInput.setCustomerNumber(businessPartner.getCustomerNumber());

            overviewInputFirm.setTitle(businessPartner.getCompanyTitle());
            overviewInputFirm.setFirm(businessPartner.getCompanyName());
            overviewInputFirm.setBranch(businessPartner.getCompanyBranch());
            overviewInputFirm.setForAttentionOf(businessPartner.isForAttentionOf());
        }

        if (person != null) {
            overviewInput.setTitle(person.getTitle());
            overviewInput.setAcademicTitle(person.getAcademicTitle());
            overviewInput.setFirstname(person.getFirstname());
            overviewInput.setLastname(person.getLastname());

            overviewInputContact.setPhone(person.getPhone());
            overviewInputContact.setFax(person.getFax());
            overviewInputContact.setEmail(person.getEmail());
        }

        if (address != null) {
            overviewInput.setStreet(address.getStreet());
            overviewInput.setZipCode(address.getZipCode());
            overviewInput.setCity(address.getCity());
            overviewInput.setCountry(address.getCountry());
            overviewInput.setCounty(address.getCounty());
        }
        else {
            overviewInput.setStreet("");
            overviewInput.setZipCode("");
            overviewInput.setCity("");
            // TODO: Behandle hier Country und County
        }

        if (banking != null) {
            overviewInputBanking.setAccountNumber(banking.getAccountNumber());
            overviewInputBanking.setBankEstablishment(banking.getBankEstablishment());
            overviewInputBanking.setBankIdentificationNumber(banking.getBankIdentificationNumber());
        }
        else {
            overviewInputBanking.setAccountNumber("");
            overviewInputBanking.setBankEstablishment("");
            overviewInputBanking.setBankIdentificationNumber("");
        }

        deliveryOrderB.setEnabled(true);
        newBill.setEnabled(true);
    }

    private void saveOrUpdateBusinessPartner() {

        BusinessPartner businessPartner = null;
        if (actualBusinessPartnerId != null) {
            businessPartner = (BusinessPartner) baseService.load(BusinessPartner.class, actualBusinessPartnerId);
        }

        if (businessPartner == null) {
            businessPartner = new BusinessPartner();
        }

        businessPartner.setCustomerNumber(overviewInput.getCustomerNumber());

        businessPartner.setCompanyTitle(overviewInputFirm.getTitle());
        businessPartner.setCompanyName(overviewInputFirm.getFirm());
        businessPartner.setCompanyBranch(overviewInputFirm.getBranch());
        businessPartner.setForAttentionOf(overviewInputFirm.isForAttentionOf());

        Person person = businessPartner.getPerson();
        if (person == null) {
            person = new Person();
            businessPartner.setPerson(person);
        }

        person.setTitle(overviewInput.getTitle());
        person.setAcademicTitle(overviewInput.getAcademicTitle());
        person.setFirstname(overviewInput.getFirstname());
        person.setLastname(overviewInput.getLastname());

        person.setPhone(overviewInputContact.getPhone());
        person.setFax(overviewInputContact.getFax());
        person.setEmail(overviewInputContact.getEmail());

        Address address = businessPartner.getAddress();
        if (address == null) {
            address = new Address();
            businessPartner.setAddress(address);
        }

        address.setStreet(overviewInput.getStreet());
        address.setZipCode(overviewInput.getZipCode());
        address.setCity(overviewInput.getCity());
        address.setCountry(overviewInput.getCountry());
        address.setCounty(overviewInput.getCounty());

        Banking banking = businessPartner.getBanking();
        if (banking == null) {
            banking = new Banking();
            businessPartner.setBanking(banking);
        }

        banking.setAccountNumber(overviewInputBanking.getAccountNumber());
        banking.setBankEstablishment(overviewInputBanking.getBankEstablishment());
        banking.setBankIdentificationNumber(overviewInputBanking.getBankIdentificationNumber());

        overviewBusinessPartnerTable.renewTableModel();

        baseService.saveOrUpdate(businessPartner);

        actualBusinessPartnerId = businessPartner.getId();

        DeliveryOrderUI deliveryOrderUI = (DeliveryOrderUI) applicationContext.getBean("deliveryOrderUI");
        deliveryOrderUI.setBusinessPartner(businessPartner);
    }
}
