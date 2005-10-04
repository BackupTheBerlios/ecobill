package ecobill.module.base.ui.businesspartner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
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
import ecobill.core.system.Internationalization;

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
 * @version $Id: BusinessPartnerUI.java,v 1.7 2005/10/04 20:15:17 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class BusinessPartnerUI extends JPanel implements InitializingBean, DisposableBean, Internationalization {

    /**
     * In diesem <code>Log</code> k�nnen Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben k�nnen in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(BusinessPartnerUI.class);

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

        // Versuche evtl. abgelegte/serialisierte Objekte zu laden.
        try {
            overviewBusinessPartnerTable.unpersist(new FileInputStream(serializeIdentifiers.getProperty("business_partner_table")));
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
            LOG.info("Schlie�e BusinessPartnerUI");
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
    }

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
                        .add(overviewVerticalButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
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
                                .add(GroupLayout.TRAILING, overviewVerticalButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        overviewVerticalButton.reinitI18N();

        overviewVerticalButton.getButton1().setToolTipText(WorkArea.getMessage(Constants.BUTTON1_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton2().setToolTipText(WorkArea.getMessage(Constants.BUTTON2_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton3().setToolTipText(WorkArea.getMessage(Constants.BUTTON3_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton4().setToolTipText(WorkArea.getMessage(Constants.BUTTON4_CUSTOMER_TOOLTIP));
        overviewVerticalButton.getButton6().setToolTipText(WorkArea.getMessage(Constants.BUTTON6_CUSTOMER_TOOLTIP));
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
}
