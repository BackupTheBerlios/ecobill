package ecobill.module.base.ui.businesspartner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.ui.component.VerticalButton;
import ecobill.module.base.ui.article.ArticleTable;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.Person;
import ecobill.module.base.domain.Address;
import ecobill.module.base.domain.Banking;
import ecobill.core.util.FileUtils;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;

import javax.swing.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Roman Georg Rädle
 */
public class BusinessPartnerUI extends JPanel implements InitializingBean, DisposableBean {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(BusinessPartnerUI.class);

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
     * Creates new form BusinessPartnerUI
     */
    public void afterPropertiesSet() {

        // Initialisieren der Komponenten und des Layouts.
        initComponents();
        initLayout();

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        try {
            overviewBusinessPartnerTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("business_partner_table")));
        }
        catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(fnfe.getMessage(), fnfe);
            }
        }
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        if (LOG.isInfoEnabled()) {
            LOG.info("Schließe BusinessPartnerUI");
        }

        // Serialisiere diese Objekte um sie bei einem neuen Start des Programmes wieder laden
        // zu können.
        overviewBusinessPartnerTable.persist(new FileOutputStream(FileUtils.createPathForFile(serializeIdentifiers.getProperty("business_partner_table"))));
    }

    private JPanel overview;
    private BusinessPartnerTable overviewBusinessPartnerTable;
    private Input overviewInput;
    private InputBanking overviewInputBanking;
    private InputContact overviewInputContact;
    private InputFirm overviewInputFirm;
    private VerticalButton overviewVerticalButton;
    private JTabbedPane tabbedPane;

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {
        tabbedPane = new javax.swing.JTabbedPane();
        overview = new javax.swing.JPanel();
        overviewVerticalButton = new ecobill.module.base.ui.component.VerticalButton();
        overviewBusinessPartnerTable = new ecobill.module.base.ui.businesspartner.BusinessPartnerTable(this, baseService);
        overviewInputContact = new ecobill.module.base.ui.businesspartner.InputContact();
        overviewInputBanking = new ecobill.module.base.ui.businesspartner.InputBanking();
        overviewInputFirm = new ecobill.module.base.ui.businesspartner.InputFirm();
        overviewInput = new ecobill.module.base.ui.businesspartner.Input(baseService);

        overviewVerticalButton.getButton1().setVisible(true);
        overviewVerticalButton.getButton1().setIcon(new ImageIcon("images/business_partner_new.png"));
        overviewVerticalButton.getButton1().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

            }
        });

        overviewVerticalButton.getButton2().setVisible(true);
        overviewVerticalButton.getButton2().setIcon(new ImageIcon("images/business_partner_ok.png"));
        overviewVerticalButton.getButton2().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                saveOrUpdateBusinessPartner();
                overviewBusinessPartnerTable.renewTableModel();
            }
        });

        overviewVerticalButton.getButton3().setVisible(true);
        overviewVerticalButton.getButton3().setIcon(new ImageIcon("images/business_partner_delete.png"));
        overviewVerticalButton.getButton3().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                baseService.delete(BusinessPartner.class, actualBusinessPartnerId);
                overviewBusinessPartnerTable.renewTableModel();
            }
        });

        overviewVerticalButton.getButton4().setVisible(true);
        overviewVerticalButton.getButton4().setIcon(new ImageIcon("images/refresh.png"));
        overviewVerticalButton.getButton4().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

            }
        });

        overviewVerticalButton.getButton6().setVisible(true);
        overviewVerticalButton.getButton6().setIcon(new ImageIcon("images/delivery_order_new.png"));
        overviewVerticalButton.getButton6().addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Test");

                ArticleTable articleTable = new ArticleTable(null, baseService);
                articleTable.getTable().removeEditor();

                frame.getContentPane().add(articleTable);

                frame.pack();
                frame.setVisible(true);
            }
        });

        reinitI18N();
    }

    private void initLayout() {
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout overviewLayout = new org.jdesktop.layout.GroupLayout(overview);
        overview.setLayout(overviewLayout);
        overviewLayout.setHorizontalGroup(
                overviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(overviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                                .add(overviewBusinessPartnerTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
                                .addContainerGap())
                        .add(org.jdesktop.layout.GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                        .add(overviewVerticalButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(overviewInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(overviewInputFirm, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(overviewInputContact, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(overviewInputBanking, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(10, 10, 10))))
        );
        overviewLayout.setVerticalGroup(
                overviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, overviewLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(overviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, overviewInputFirm, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, overviewVerticalButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(overviewInputBanking, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(overviewInputContact, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, overviewInput, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(overviewBusinessPartnerTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                        .addContainerGap())
        );
        tabbedPane.addTab(WorkArea.getMessage(Constants.OVERVIEW), overview);

        add(tabbedPane, java.awt.BorderLayout.CENTER);
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
    }

    public void reinitI18N() {

        tabbedPane.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));

        overviewInput.reinitI18N();
        overviewInputBanking.reinitI18N();
        overviewInputContact.reinitI18N();
        overviewInputFirm.reinitI18N();
        overviewVerticalButton.reinitI18N();

        overviewVerticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.BUTTON1_TOOLTIP));

    }
}
