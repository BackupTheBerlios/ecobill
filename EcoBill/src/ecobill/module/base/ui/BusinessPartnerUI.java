package ecobill.module.base.ui;

import ecobill.module.base.service.BaseService;
import ecobill.util.UnitUtils;
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
 * User: rro
 * Date: 05.08.2005
 * Time: 14:20:07
 *
 * @author Roman R&auml;dle
 * @version $Id: BusinessPartnerUI.java,v 1.4 2005/08/05 14:59:26 jfuckerweiler Exp $
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
     * Gibt die einzigste Instanz der <code>ArticleUI</code> zurück um diese
     * dann bspw im Hauptfenster anzeigen zu können.
     *
     * @return Die <code>ArticleUI</code> ist abgeleitet von <code>JInternalFrame</code>
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
     * für die Eingabe der Artikeldaten und die <code>JTable</code> zur Anzeige aller Artikel.
     */
    private JTabbedPane primaryTP = new JTabbedPane();

    /**
     * Das Übersichtspanel gibt Auskunft über alle Artikel per <code>JTable</code> und bietet
     * eine Eingabemaske (auch für Änderungen) von Artikeln.
     */
    private JPanel overviewP = new JPanel(new BorderLayout());

    /**
     * Dieses Panel bietet wie oben genannt die Eingabemaske (auch Änderungen) für Artikel.
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
    private JLabel surname = new JLabel("Nachname");
    private JLabel firstname = new JLabel("Vorname");
    private JLabel unitL = new JLabel();
    private JLabel priceL = new JLabel();
    private JLabel inStockL = new JLabel();
    private JLabel bundleUnitL = new JLabel();
    private JLabel bundleCapacityL = new JLabel();
    private JLabel languageL = new JLabel();
    private JLabel countryL = new JLabel();
    private JLabel variantL = new JLabel();

    /**
     * Alle nötigen Eingabemasken, wie <code>JComboBox</code>, <code>JSpinner</code>,...
     */
    private JTextField surnameTF = new JTextField();
    private JTextField firstnameTF = new JTextField();
    private ComboBoxModel unitCBModel = new DefaultComboBoxModel(UnitUtils.getAllUnits());
    private JComboBox unitCB = new JComboBox(unitCBModel);
    private SpinnerModel priceSpModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.01);
    private JSpinner priceSp = new JSpinner(priceSpModel);
    private SpinnerModel inStockSpModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.1);
    private JSpinner inStockSp = new JSpinner(inStockSpModel);
    private ComboBoxModel bundleUnitCBModel = new DefaultComboBoxModel(UnitUtils.getAllUnits());
    private JComboBox bundleUnitCB = new JComboBox(bundleUnitCBModel);
    private SpinnerModel bundleCapacitySpModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.1);
    private JSpinner bundleCapacitySp = new JSpinner(bundleCapacitySpModel);
    private JTextArea descriptionTA = new JTextArea();
    private JTextArea descriptionsTA = new JTextArea();
    private JComboBox languageCB = new JComboBox();
    private JComboBox countryCB = new JComboBox();
    private JComboBox variantCB = new JComboBox();

    /**
     * Alle benötigten <code>JButton</code> um bspw. Artikel oder Beschreibungen
     * zu speichern/ändern.
     */
    private JButton saveB = new JButton();

    /**
     * Die <code>JScrollPane</code> um die Tabelle scrollen zu können, die Tabelle
     * selbst und das zugehörige Model im MVC Stil.
     * Dazu noch einen <code>Vector</code> der später die internationalisierten Header
     * aufnimmt.
     */
    private JScrollPane articleTableSP = new JScrollPane();
    private DefaultTableModel articleDescriptionTableModel = new DefaultTableModel();
    private JTable articleDescriptionTable = new JTable(articleDescriptionTableModel);

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
        primaryTP.add(overviewP);
        primaryTP.add(descriptionsP);

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
        JPanel articleP = new JPanel(null);
        dataBorder.setTitleColor(Color.BLACK);
        articleP.setBorder(dataBorder);

        // Ein Panel für die Gebindedaten des Artikels.
        JPanel articleBundleP = new JPanel(null);
        bundleBorder.setTitleColor(Color.BLACK);
        articleBundleP.setBorder(bundleBorder);

        // Ein Panel für die Beschreibung der eingestellten Sprache.
        JPanel articleDescriptionP = new JPanel(null);
        descriptionBorder.setTitleColor(Color.BLACK);
        articleDescriptionP.setBorder(descriptionBorder);

        JPanel articleDescriptionsP = new JPanel(new BorderLayout());

        /*
        * Setzt die Position der einzelnen Komponenten und hinzufügen
        * auf das Artikel Panel.
        */
        surname.setBounds(12, 20, 120, 20);
        articleP.add(surname);

        surnameTF.setBounds(10, 40, 100, 20);
        articleP.add(surnameTF);

        firstname.setBounds(122,20,120,20);
        articleP.add(firstname);

        firstnameTF.setBounds(120,40,100,20);
        articleP.add(firstnameTF);

        unitL.setBounds(12, 70, 100, 20);
        articleP.add(unitL);

        unitCB.setBounds(10, 90, 100, 20);
        articleP.add(unitCB);

        priceL.setBounds(122, 70, 100, 20);
        articleP.add(priceL);

        priceSp.setBounds(120, 90, 100, 20);
        articleP.add(priceSp);

        inStockL.setBounds(232, 70, 100, 20);
        articleP.add(inStockL);

        inStockSp.setBounds(230, 90, 100, 20);
        articleP.add(inStockSp);

        /*
         * Setzt die Position der einzelnen Komponenten und hinzufügen
         * auf das Artikel Bundle Panel.
         */
        bundleUnitL.setBounds(12, 20, 100, 20);
        articleBundleP.add(bundleUnitL);

        bundleUnitCB.setBounds(10, 40, 100, 20);
        articleBundleP.add(bundleUnitCB);

        bundleCapacityL.setBounds(12, 70, 100, 20);
        articleBundleP.add(bundleCapacityL);

        bundleCapacitySp.setBounds(10, 90, 100, 20);
        articleBundleP.add(bundleCapacitySp);

        /*
         * Setzt die Position der Beschreibung <code>JTextArea</code>
         * auf dem Beschreibung Panel. Desweiteren werden diverse Einstellungen
         * der <code>JTextArea</code> vorgenommen.
         */
        descriptionTA.setWrapStyleWord(true);
        descriptionTA.setLineWrap(true);
        JScrollPane descriptionSP = new JScrollPane(descriptionTA);
        descriptionSP.setBounds(10, 25, 440, 100);
        descriptionSP.setBorder(new BevelBorder(BevelBorder.LOWERED));
        articleDescriptionP.add(descriptionSP);

        /*
         * Setzt die Positionen der einzelnen Panels auf dem oberen Überblick
         * Panel.
         */
        //articleP.setPreferredSize(new Dimension(340, 125));
        articleP.setMinimumSize(new Dimension(340, 125));
        articleBundleP.setMinimumSize(new Dimension(120, 125));
        articleDescriptionP.setMinimumSize(new Dimension(460, 140));

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
        gbl.setConstraints(articleP, c1);
        overviewTopP.add(articleP);

        c1.gridx = 1;
        c1.gridy = 0;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        gbl.setConstraints(articleBundleP, c1);
        overviewTopP.add(articleBundleP);

        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 2;
        c1.gridheight = 1;
        gbl.setConstraints(articleDescriptionP, c1);
        overviewTopP.add(articleDescriptionP);

        c1.gridx = 0;
        c1.gridy = 2;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        gbl.setConstraints(saveB, c1);
        overviewTopP.add(saveB);

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
        gbl.setConstraints(articleDescriptionsP, c4);
        overviewTopP.add(articleDescriptionsP);

        residualDescriptionsBorder.setTitleColor(Color.BLACK);
        articleDescriptionsP.setBorder(residualDescriptionsBorder);

        articleDescriptionsP.add(new JScrollPane(articleDescriptionTable));
    }

    private void initDescriptionP() {

        JPanel localeSettingP = new JPanel(null);

        languageL.setBounds(10, 20, 100, 20);
        localeSettingP.add(languageL);

        languageCB.setBounds(10, 40, 100, 20);
        localeSettingP.add(languageCB);

        countryL.setBounds(10, 70, 100, 20);
        localeSettingP.add(countryL);

        countryCB.setBounds(10, 90, 100, 20);
        localeSettingP.add(countryCB);

        variantL.setBounds(10, 120, 100, 20);
        localeSettingP.add(variantL);

        variantCB.setBounds(10, 140, 100, 20);
        localeSettingP.add(variantCB);


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
        descriptionsTableP.add(new JScrollPane(new JTable(new Object[][]{{"a", "a", "a"}, {"b", "b", "b"}}, new Object[]{"test1", "test2", "test3"})));

        descriptionTopP.setPreferredSize(new Dimension(400, 200));

        descriptionsP.add(descriptionTopP, BorderLayout.NORTH);
        descriptionsP.add(descriptionsTableP, BorderLayout.CENTER);
    }

}
