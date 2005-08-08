package ecobill.module.base.ui;

import ecobill.module.base.service.BaseService;
import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// @todo document me!

/**
 * BusinessPartnerUI.
 * <p/>
 * User: aw
 * Date: 05.08.2005
 * Time: 14:20:07
 *
 * @author Andreas Weiler
 * @version $Id: BusinessPartnerUI.java,v 1.16 2005/08/08 20:22:38 jfuckerweiler Exp $
 * @since EcoBill 1.0
 */
public class BusinessPartnerUI extends JPanel implements InitializingBean {


    /**
     * Die <code>BusinessPartnerUI</code> stellt ein Singleton dar, da es immer nur eine
     * Instanz pro Arbeitsplatz geben kann.
     * -> spart kostbare Ressourcen.
     */
    private static BusinessPartnerUI singelton = null;

    /**
     * Gibt die einzigste Instanz der <code>BusinessPartnerUI</code> zurück um diese
     * dann bspw im Hauptfenster anzeigen zu können.
     *
     * @return Die <code>BusinessPartnerUI</code> ist abgeleitet von <code>JInternalFrame</code>
     *         und kann auf einer <code>JDesktopPane</code> angezeigt werden.
     */
    public static BusinessPartnerUI getInstance() {
        if (singelton == null) {
            singelton = new BusinessPartnerUI();
        }
        return singelton;
    }

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;
    /**
     * Das Primary <code>JTabbedPane</code> beinhaltet alle nötigen Register, Eingabemasken
     * für die Eingabe der Kundendaten und die <code>JTable</code> zur Anzeige aller Kunden.
     */
    private JTabbedPane primaryTP = new JTabbedPane();

    /**
     * Das Übersichtspanel gibt Auskunft über alle Artikel per <code>JTable</code> und bietet
     * eine Eingabemaske (auch für Änderungen) von Artikeln.
     */
    private JPanel overviewP = new JPanel(new BorderLayout());

    /**
     * Dieser Button ist für das Speichern
     */
    private JButton saveB = new JButton("Speichern");

    /**
     * Dieses Panel bietet wie oben genannt die Eingabemaske (auch Änderungen) für Kunden.
     * Sowie die einzelnen betitelten Rahmen um die einzelnen Eingabemasken.
     */
    private JPanel overviewTopP = new JPanel();
    private TitledBorder dataBorder = new TitledBorder(new EtchedBorder());
    private TitledBorder bundleBorder = new TitledBorder(new EtchedBorder());
    private TitledBorder descriptionBorder = new TitledBorder(new EtchedBorder());
    private TitledBorder residualDescriptionsBorder = BorderFactory.createTitledBorder(new EtchedBorder());


    private JPanel descriptionsP = new JPanel(new BorderLayout());

    /**
     * Alle Labels die nötig sind um die GUI erklärend zu gestalten.
     */
    private JLabel id = new JLabel("ID");
    private JLabel title1 = new JLabel("Anrede");
    private JLabel title2 = new JLabel("Titel");
    private JLabel surname = new JLabel("Nachname");
    private JLabel firstname = new JLabel("Vorname");
    private JLabel street = new JLabel("Straße, Hausnr.");
    private JLabel zip = new JLabel("PLZ");
    private JLabel city = new JLabel("Stadt");
    private JLabel county = new JLabel("Bundesland");
    private JLabel country = new JLabel("Land");
    private JLabel phone = new JLabel("Phone");
    private JLabel fax = new JLabel("Fax");
    private JLabel email = new JLabel("Email");

    /**
     * Alle nötigen Eingabemasken, wie <code>JComboBox</code>,...
     */
    private JTextField idTF = new JTextField();
    private JComboBox title1CB = new JComboBox();
    private JComboBox title2CB = new JComboBox();
    private JTextField surnameTF = new JTextField();
    private JTextField firstnameTF = new JTextField();
    private JTextField streetTF = new JTextField();
    private JTextField zipTF = new JTextField();
    private JTextField cityTF = new JTextField();
    private JTextField countyTF = new JTextField();
    private JComboBox countryCOB = new JComboBox();
    private JTextField phoneTF = new JTextField();
    private JTextField faxTF = new JTextField();
    private JTextField emailTF = new JTextField();


    private JTextArea descriptionTA = new JTextArea();
    private JTextArea descriptionsTA = new JTextArea();

    /**
     * Die <code>JScrollPane</code> um die Tabelle scrollen zu können, die Tabelle
     * selbst und das zugehörige Model im MVC Stil.
     * Dazu noch einen <code>Vector</code> der später die internationalisierten Header
     * aufnimmt.
     */
    private JScrollPane articleTableSP = new JScrollPane();
    private DefaultTableModel customerDescriptionTableModel = new DefaultTableModel();
    private JTable customerDescriptionTable = new JTable(customerDescriptionTableModel);

    /**
     * Standard Konstruktor.
     */
    public BusinessPartnerUI() {

    }

    /**
     * Globale Initialisierung des Artikel User Interfaces.
     * Es wird die Größe, minimale Größe des Fensters,... gesetzt.
     * Diese wird nach den gesetzten Properties des <code>ApplicationContext</code>
     * durchgeführt.
     */
    public void afterPropertiesSet() {

        /*
         * Es wird die Größe, das Layout und verschiedenste Optionen gesetzt.
         */
        this.setSize(new Dimension(870, 525));
        this.setMinimumSize(new Dimension(870, 325));
        this.setLayout(new BorderLayout());

        /*
         * Startet die Initialisierung der Kunden Oberfläche.
         */
        this.initUI();
    }

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
     * Initialisieren des Graphical User Interface mit all seinen Panels, Eingabemasken
     * Rahmen, Buttons, usw.
     */
    private void initUI() {

        /*
         * Initilisieren der einzelnen GUI Komponenten.
         */
        this.initOverviewTopP();
        this.initDescriptionP();

        /*
         * Setzt auf das Übersichtspanel die Eingabemaske für Artikel und die
         * Tabelle die alle Artikel beinhält.
         */
        overviewP.add(overviewTopP, BorderLayout.NORTH);
        overviewP.add(articleTableSP, BorderLayout.CENTER);

        /*
         * Fügt das Übersichtspanel und das Beschreibungenpanel der primären <code>JTabbedPane</code>
         * hinzu.
         */
        primaryTP.add("Übersicht",overviewP);
        primaryTP.add("Aufträge",descriptionsP);

        /*
         * Fügt die primäre <code>JTabbedPane</code> diesem <code>JInternalFrame</code> hinzu.
         */
        this.add(primaryTP, BorderLayout.CENTER);

    }

    /**
     * Initialisieren des oberen Teiles der Übersicht. Diese beinhaltet Eingabefelder
     * und einen Speichern/Ändern Button.
     */
    private void initOverviewTopP() {

        GridBagLayout gbl = new GridBagLayout();

        overviewTopP.setLayout(gbl);

        // Setzt einen Rahmen um das obere Übersicht Panel.
        overviewTopP.setBorder(new EtchedBorder());

        // Setzt die Größe des Panels.
        overviewTopP.setPreferredSize(new Dimension(800, 310));

        // Ein Panel für den Artikel und die Hauptdaten.
        JPanel customerP = new JPanel(null);
        dataBorder.setTitleColor(Color.BLACK);
        dataBorder.setTitle("Daten");
        customerP.setBorder(dataBorder);

        // Ein Panel für die Gebindedaten des Artikels.
        JPanel phoneP = new JPanel(null);
        bundleBorder.setTitleColor(Color.BLACK);
        bundleBorder.setTitle("ID/Phone/Fax/Email");
        phoneP.setBorder(bundleBorder);

        // Ein Panel für die Beschreibung der eingestellten Sprache.
        JPanel customerDescriptionP = new JPanel(null);
        descriptionBorder.setTitleColor(Color.BLACK);
        descriptionBorder.setTitle("Beschreibung");
        customerDescriptionP.setBorder(descriptionBorder);

        JPanel customerDescriptionsP = new JPanel(new BorderLayout());

        /*
        * Setzt die Position der einzelnen Komponenten und hinzufügen
        * auf das Artikel Panel.
        */

        title1.setBounds(12, 20, 120, 20);
        customerP.add(title1);

        title1CB.setBounds(10, 40, 70, 20);
        title1CB.addItem("Herr");
        title1CB.addItem("Frau");
        customerP.add(title1CB);

        title2.setBounds(112, 20, 120, 20);
        customerP.add(title2);

        title2CB.setBounds(110, 40, 70, 20);
        title2CB.addItem("-None-");
        title2CB.addItem("Dr.");
        title2CB.addItem("Prof.");
        title2CB.addItem("Prof. Dr.");
        customerP.add(title2CB);

        surname.setBounds(12, 70, 120, 20);
        customerP.add(surname);

        surnameTF.setBounds(10, 90, 100, 20);
        customerP.add(surnameTF);

        firstname.setBounds(132, 70, 120, 20);
        customerP.add(firstname);

        firstnameTF.setBounds(130, 90, 100, 20);
        customerP.add(firstnameTF);

        street.setBounds(12, 120, 120, 20);
        customerP.add(street);

        streetTF.setBounds(10, 140, 200, 20);
        customerP.add(streetTF);

        zip.setBounds(12, 160, 120, 20);
        customerP.add(zip);

        zipTF.setBounds(10, 180, 50, 20);
        customerP.add(zipTF);

        city.setBounds(72, 160, 120, 20);
        customerP.add(city);

        cityTF.setBounds(70, 180, 100, 20);
        customerP.add(cityTF);

        county.setBounds(12, 200, 120, 20);
        customerP.add(county);

        countyTF.setBounds(10, 220, 100, 20);
        customerP.add(countyTF);

        country.setBounds(122, 200, 120, 20);
        customerP.add(country);

        countryCOB.setBounds(120, 220, 100, 20);
        countryCOB.addItem("Argentinia");
        countryCOB.addItem("England");
        countryCOB.addItem("Germany");
        countryCOB.addItem("USA");
        countryCOB.addItem("Venezuela");
        customerP.add(countryCOB);

        /*
         * Setzt die Position der einzelnen Komponenten und hinzufügen
         * auf das Artikel Bundle Panel.
         */


        id.setBounds(12, 20, 100, 20);
        phoneP.add(id);

        idTF.setBounds(10, 40, 150, 20);
        phoneP.add(idTF);

        phone.setBounds(12, 70, 100, 20);
        phoneP.add(phone);

        phoneTF.setBounds(10, 90, 150, 20);
        phoneP.add(phoneTF);

        fax.setBounds(12, 120, 100, 20);
        phoneP.add(fax);

        faxTF.setBounds(10, 140, 150, 20);
        phoneP.add(faxTF);

        email.setBounds(12, 170, 100, 20);
        phoneP.add(email);

        emailTF.setBounds(10, 190, 150, 20);
        phoneP.add(emailTF);

        /*
         * Setzt die Position der Beschreibung <code>JTextArea</code>
         * auf dem Beschreibung Panel. Desweiteren werden diverse Einstellungen
         * der <code>JTextArea</code> vorgenommen.
         */
        descriptionTA.setWrapStyleWord(true);
        descriptionTA.setLineWrap(true);
        JScrollPane descriptionSP = new JScrollPane(descriptionTA);
        descriptionSP.setBounds(10, 20, 350, 20);
        saveB.setBounds(380, 20, 100, 20);
        descriptionSP.setBorder(new BevelBorder(BevelBorder.LOWERED));
        customerDescriptionP.add(saveB);
        customerDescriptionP.add(descriptionSP);

        /*
         * Setzt die Positionen der einzelnen Panels auf dem oberen Überblick
         * Panel.
         */
        customerP.setMinimumSize(new Dimension(250, 250));
        phoneP.setMinimumSize(new Dimension(180, 250));
        customerDescriptionP.setMinimumSize(new Dimension(500, 50));

        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c4 = new GridBagConstraints();

        c1.fill = GridBagConstraints.NONE;
        c1.weightx = 0.0;
        c1.weighty = 0.0;
        c1.anchor = GridBagConstraints.WEST;
        c1.ipadx = 0;
        c1.ipady = 0;
        c1.gridx = 0;
        c1.gridy = 0;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        gbl.setConstraints(customerP, c1);
        overviewTopP.add(customerP);

        c1.gridx = 1;
        c1.gridy = 0;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        gbl.setConstraints(phoneP, c1);
        overviewTopP.add(phoneP);

        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 2;
        c1.gridheight = 1;
        gbl.setConstraints(customerDescriptionP, c1);
        overviewTopP.add(customerDescriptionP);

        c1.gridx = 0;
        c1.gridy = 2;
        c1.gridwidth = 1;
        c1.gridheight = 1;

        c4.fill = GridBagConstraints.BOTH;
        c4.weightx = 1.0;
        c4.weighty = 1.0;
        c4.anchor = GridBagConstraints.CENTER;
        c4.ipadx = 0;
        c4.ipady = 0;
        c4.gridx = 2;
        c4.gridy = 0;
        c4.gridwidth = 1;
        c4.gridheight = 3;
        gbl.setConstraints(customerDescriptionsP, c4);
        overviewTopP.add(customerDescriptionsP);

        residualDescriptionsBorder.setTitleColor(Color.BLACK);
        residualDescriptionsBorder.setTitle("Restliche Beschreibung");
        customerDescriptionsP.setBorder(residualDescriptionsBorder);
        customerDescriptionsP.add(new JScrollPane(customerDescriptionTable));
    }

    private void initDescriptionP() {

        JPanel localeSettingP = new JPanel(null);

        descriptionsTA.setBorder(descriptionBorder);
        descriptionsTA.setLineWrap(true);
        descriptionsTA.setWrapStyleWord(true);

        //GridBagLayout gbl = new GridBagLayout();
        JPanel descriptionTopP = new JPanel(new BorderLayout());

        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.NONE;
        c1.weightx = 0.0;
        c1.weighty = 0.0;
        c1.anchor = GridBagConstraints.WEST;
        c1.ipadx = 0;
        c1.ipady = 0;
        c1.gridx = 0;
        c1.gridy = 0;
        c1.gridwidth = 1;
        c1.gridheight = 1;

        descriptionTopP.add(localeSettingP, BorderLayout.CENTER);


        JPanel descriptionsTableP = new JPanel(new BorderLayout());
        descriptionsTableP.add(new JScrollPane(new JTable(new Object[][]{{"1010", "24.12.1950", "1", "Ja"}, {"1011", "01.01.1951", "2", "Nein"}}, new Object[]{"BestellID", "BestellDatum", "CustomerID", "Bezahlt"})));
        descriptionsTableP.setPreferredSize(new Dimension(400, 600));
        descriptionTopP.setPreferredSize(new Dimension(400, 200));
        descriptionsP.add(descriptionTopP, BorderLayout.NORTH);
        descriptionsP.add(descriptionsTableP, BorderLayout.NORTH);
    }

}
