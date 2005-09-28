package ecobill.module.base.ui.article;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
import ecobill.util.VectorUtils;
import ecobill.util.exception.LocalizerException;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.ArticleDescription;
import ecobill.module.base.domain.SystemLocale;
import ecobill.module.base.domain.SystemUnit;
import org.springframework.beans.factory.InitializingBean;

// @todo document me!

/**
 * ArticleUI.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: ArticleUI.java,v 1.2 2005/09/28 15:57:53 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleUI extends JPanel implements InitializingBean, Internationalization {

    private final static String[] ARTICLE_TABLE_ORDER = new String[]{Constants.ARTICLE_NR,
                                                                     Constants.UNIT,
                                                                     Constants.SINGLE_PRICE,
                                                                     Constants.DESCRIPTION,
                                                                     Constants.IN_STOCK,
                                                                     Constants.BUNDLE_UNIT,
                                                                     Constants.BUNDLE_CAPACITY};

    private final static String[] ARTICLE_DESCRIPTION_TABLE_ORDER = new String[]{Constants.KEY,
                                                                                 Constants.LANGUAGE,
                                                                                 Constants.COUNTRY,
                                                                                 Constants.VARIANT,
                                                                                 Constants.DESCRIPTION};

    /**
     * Die <code>ArticleUI</code> stellt ein Singleton dar, da es immer nur eine
     * Instanz pro Arbeitsplatz geben kann.
     * -> spart kostbare Ressourcen.
     */
    private static ArticleUI singelton = null;

    /**
     * Gibt die einzigste Instanz der <code>ArticleUI</code> zurück um diese
     * dann bspw im Hauptfenster anzeigen zu können.
     *
     * @return Die <code>ArticleUI</code> ist abgeleitet von <code>JInternalFrame</code>
     *         und kann auf einer <code>JDesktopPane</code> angezeigt werden.
     */
    public static ArticleUI getInstance() {
        if (singelton == null) {
            singelton = new ArticleUI();
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
    private TitledBorder localeBorder = BorderFactory.createTitledBorder(new EtchedBorder());

    private JPanel descriptionsP = new JPanel(new BorderLayout());

    /**
     * Alle Labels die nötig sind um die GUI erklärend zu gestalten.
     */
    private JLabel articleNumberL = new JLabel();
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
    private JTextField articleNumberTF = new JTextField();
    private ComboBoxModel unitCBModel = null;
    private JComboBox unitCB = new JComboBox();
    private SpinnerModel priceSpModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.01);
    private JSpinner priceSp = new JSpinner(priceSpModel);
    private SpinnerModel inStockSpModel = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.1);
    private JSpinner inStockSp = new JSpinner(inStockSpModel);
    private ComboBoxModel bundleUnitCBModel = null;
    private JComboBox bundleUnitCB = new JComboBox();
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
    private JButton newB = new JButton();
    private JButton addDescriptionB = new JButton();

    /**
     * Die <code>JScrollPane</code> um die Tabelle scrollen zu können, die Tabelle
     * selbst und das zugehörige Model im MVC Stil.
     * Dazu noch einen <code>Vector</code> der später die internationalisierten Header
     * aufnimmt.
     */
    private JScrollPane articleTableSP = new JScrollPane();
    private Vector<String> articleTableHeaderV = new Vector<String>();
    private Vector<Vector<Object>> articleTableDataV = new Vector<Vector<Object>>();
    private DefaultTableModel articleTableModel = new DefaultTableModel();
    private JTable articleTable = new JTable(articleTableModel);
    //private ArticleTable articleTable2 = null;

    private Vector<String> articleDescriptionTableHeaderV = new Vector<String>();
    private Vector<Vector<Object>> articleDescriptionTableDataV = new Vector<Vector<Object>>();
    private DefaultTableModel articleDescriptionTableModel = new DefaultTableModel();
    private JTable articleDescriptionTable = new JTable(articleDescriptionTableModel);

    /**
     * Standard Konstruktor.
     */
    public ArticleUI() {

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
        this.setClosable(false);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setResizable(true);
        */

        //articleTable2 = new ArticleTable(baseService.getAllArticles(), baseService);

        /*
        * Startet die Initialisierung der Artikel Oberfläche.
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

/*      /*
         * Fügt den unit <code>ComboBox</code> ein Model mit den <code>SystemUnit</code> hinzu.
         */
        unitCBModel = new DefaultComboBoxModel(baseService.getAllSystemUnits().toArray());
        unitCB.setModel(unitCBModel);
        bundleUnitCBModel = new DefaultComboBoxModel(baseService.getAllSystemUnits().toArray());
        bundleUnitCB.setModel(bundleUnitCBModel);

        /*
        * Initilisieren der einzelnen GUI Komponenten.
        */
        this.initOverviewTopP();
        this.initOverviewBottomP();
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

        /*
         * Initialisiert die Labels,... mit der eingestellten Sprache in
         * der <code>WorkArea</code>.
         */
        this.reinitI18N();

        /*
         * Setzt alle Artikel in die Tabelle.
         */
        this.renewArticleTableModel();
    }

    /**
     * Setzt alle Labels, Buttons, Register auf die jeweilige eingestellte Sprache in der
     * <code>WorkArea</code>.
     */
    public void reinitI18N() {

        /*
         * Setzt den I18N Artikel Fenster Titel.
         */
        //this.setTitle(WorkArea.getMessage(Constants.UI_TITLE_ARTICLE));

        /*
         * Setzt die Titel und Tooltips der Registerreiter.
         */
        primaryTP.setTitleAt(0, WorkArea.getMessage(Constants.OVERVIEW));
        primaryTP.setToolTipTextAt(0, WorkArea.getMessage(Constants.OVERVIEW_TOOLTIP));
        primaryTP.setTitleAt(1, WorkArea.getMessage(Constants.DESCRIPTIONS));
        primaryTP.setToolTipTextAt(1, WorkArea.getMessage(Constants.DESCRIPTIONS_TOOLTIP));

        /*
         * Setzt die Titel der <code>TitledBorder</code>.
         */
        dataBorder.setTitle(WorkArea.getMessage(Constants.DATA));
        bundleBorder.setTitle(WorkArea.getMessage(Constants.BUNDLE));
        descriptionBorder.setTitle(WorkArea.getMessage(Constants.DESCRIPTION));
        residualDescriptionsBorder.setTitle(WorkArea.getMessage(Constants.RESIDUAL_DESCRIPTIONS));
        localeBorder.setTitle(WorkArea.getMessage(Constants.LOCALE_SETTING));

        articleNumberL.setText(WorkArea.getMessage(Constants.ARTICLE_NR));
        articleNumberL.setToolTipText(WorkArea.getMessage(Constants.ARTICLE_NR));

        unitL.setText(WorkArea.getMessage(Constants.UNIT));
        unitL.setToolTipText(WorkArea.getMessage(Constants.UNIT_TOOLTIP));

        priceL.setText(WorkArea.getMessage(Constants.PRICE));
        priceL.setToolTipText(WorkArea.getMessage(Constants.PRICE_TOOLTIP));

        inStockL.setText(WorkArea.getMessage(Constants.IN_STOCK));
        inStockL.setToolTipText(WorkArea.getMessage(Constants.IN_STOCK_TOOLTIP));

        bundleUnitL.setText(WorkArea.getMessage(Constants.UNIT));
        bundleUnitL.setToolTipText(WorkArea.getMessage(Constants.UNIT_TOOLTIP));

        bundleCapacityL.setText(WorkArea.getMessage(Constants.CAPACITY));
        bundleCapacityL.setToolTipText(WorkArea.getMessage(Constants.CAPACITY_TOOLTIP));

        languageL.setText(WorkArea.getMessage(Constants.LANGUAGE));
        languageL.setToolTipText(WorkArea.getMessage(Constants.LANGUAGE_TOOLTIP));

        countryL.setText(WorkArea.getMessage(Constants.COUNTRY));
        countryL.setToolTipText(WorkArea.getMessage(Constants.COUNTRY_TOOLTIP));

        variantL.setText(WorkArea.getMessage(Constants.VARIANT));
        variantL.setToolTipText(WorkArea.getMessage(Constants.VARIANT_TOOLTIP));

        /*
        * Setzt die Beschriftung der einzelnen Buttons.
        */
        saveB.setText(WorkArea.getMessage(Constants.SAVE));
        saveB.setToolTipText(WorkArea.getMessage(Constants.SAVE_TOOLTIP));

        newB.setText(WorkArea.getMessage(Constants.NEW));
        newB.setToolTipText(WorkArea.getMessage(Constants.NEW_TOOLTIP));

        addDescriptionB.setText(WorkArea.getMessage(Constants.ADD));
        addDescriptionB.setToolTipText(WorkArea.getMessage(Constants.ADD_TOOLTIP));

        /*
         * Setzt die Überschriften der Artikel Tabelle.
         */
        articleTableModel.setColumnIdentifiers(this.createI18NTableHeader(articleTableHeaderV, ARTICLE_TABLE_ORDER));

        /*
         * Setzt die Überschiften der Artikelbeschreibungen Tabelle.
         */
        articleDescriptionTableModel.setColumnIdentifiers(this.createI18NTableHeader(articleDescriptionTableHeaderV, ARTICLE_DESCRIPTION_TABLE_ORDER));

        this.repaint();
    }

    /**
     * Gibt einen <code>Vector</code> mit den landesspezifischen Tabellen Header
     * Bezeichnungen zurück.
     *
     * @return Ein <code>Vector</code> mit landesspezifischen Tabellen Header
     *         Bezeichnungen.
     */
    private Vector<String> createI18NTableHeader(Vector<String> tableHeaderVector, String[] tableHeaderOrder) {

        // Entfernen aller Elemente im <code>Vector</code>.
        tableHeaderVector.removeAllElements();

        /*
         * Hinzufügen der landesspezifischen Bezeichnungen in der richtigen
         * Reihenfolge.
         */
        for (String tableOrder : tableHeaderOrder) {
            tableHeaderVector.add(WorkArea.getMessage(tableOrder));
        }

        return tableHeaderVector;
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
        articleNumberL.setBounds(12, 20, 120, 20);
        articleP.add(articleNumberL);

        articleNumberTF.setBounds(10, 40, 200, 20);
        articleP.add(articleNumberTF);

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

        c1.gridx = 1;
        c1.gridy = 2;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        gbl.setConstraints(newB, c1);
        overviewTopP.add(newB);

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


        //articleTable.setModel(articleTableModel);

        //JTable b = new JTable();
        /*
        ScrollPaneLayout spl = new ScrollPaneLayout();
        JScrollPane scrollPane = new JScrollPane();

        JViewport viewport = new JScrollPane(articleDescriptionTable).getViewport();

        spl.addLayoutComponent(ScrollPaneLayout.UPPER_RIGHT_CORNER, articleDescriptionTable);
        spl.addLayoutComponent(ScrollPaneLayout.VIEWPORT, viewport);

        scrollPane.setLayout(spl);
        scrollPane.setBackground(Color.BLUE);
        articleDescriptionTable.setBackground(Color.BLUE);
        */

        //JViewport viewport = new JViewport();
        //viewport.setViewSize(new Dimension(50, 300));
        //viewport.setView(articleDescriptionTable);
        //scrollPane.setViewportView(articleDescriptionTable);

        articleDescriptionsP.add(new JScrollPane(articleDescriptionTable));
        //scrollPane.setViewport(viewport);
        //articleDescriptionsP.add(scrollPane, BorderLayout.CENTER);
        /**/
        //articleDescriptionsP.add(articleDescriptionTable);


        /*
        * Setzt den Speichern/Ändern Button auf das Panel.
        */
        //saveB.setBounds(10, 280, 100, 20);
        //overviewP.add(saveB);

        saveB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Article article = ArticleUI.this.saveOrUpdateArticle();
                //ArticleUI.this.addArticleToTableModel(article);
                ArticleUI.this.renewArticleTableModel();
            }
        });
    }

    /**
     * Initialisieren des unteren Teils des Übersicht Panels mit der
     * darin enthaltenen Artikeltabelle und den zugehörigen Listenern
     * der Tabelle.
     */
    private void initOverviewBottomP() {

        /*
         * Fügt die Tabelle in die <code>JScrollPane</code> ein. Dadurch ist es
         * möglich die Artikeltabelle zu scrollen, falls der Bildbereich nicht
         * ausreichen sollte.
         */
        // @todo articleTable adden
        articleTableSP.setViewportView(articleTable);
        articleTable.setModel(articleTableModel);

        /*
         * Fügt der Artikeltabelle einen <code>MouseListener</code> hinzu,
         * der darauf wartet bis die linke Taste der Maus gedrückt wird und
         * dann den markierten Artikel auf den Eingabefeldern anzeigt.
         */
        this.articleTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                /*
                 * Achtet darauf, dass wirklich nur dann ein Artikel angezeigt
                 * wird wenn auch nur die linke Maustaste gedrückt wird.
                 * Alle anderen werden ignoriert.
                 */
                if (e.getButton() == MouseEvent.BUTTON1) {
                    ArticleUI.this.showArticle(getArticleNumberOfSelectedRow(0));
                }
            }
        });

        /*
         * Fügt der Artikeltabelle einen <code>KeyListener</code> hinzu,
         * der darauf wartet bis die Pfeiltaste nach oben oder nach unten
         * gedrückt wird und dann den markierten Artikel auf den Eingabefeldern
         * anzeigt.
         */
        this.articleTable.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                /*
                 * Achtet darauf, dass auch wirklich nur dann ein Artikel angezeigt
                 * wird wenn ein neuer Artikel selektiert wird. Dies geschieht hier
                 * nur wenn die Pfeiltaste nach oben oder nach unten gedrückt wird.
                 */
                String selectedArticleNumber = getArticleNumberOfSelectedRow(0);

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    selectedArticleNumber = getArticleNumberOfSelectedRow(-1);
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    selectedArticleNumber = getArticleNumberOfSelectedRow(1);
                }

                ArticleUI.this.showArticle(selectedArticleNumber);
            }
        });
    }

    // @todo looki
    private void renewArticleDescriptionTableModel(Article article) {

        articleDescriptionTableDataV.removeAllElements();

        Set<ArticleDescription> articleDescriptions = article.getDescriptions();

        for (ArticleDescription articleDescription : articleDescriptions) {

            Vector<Object> lineV = new Vector<Object>();

            SystemLocale systemLocale = articleDescription.getSystemLocale();

            lineV.add(systemLocale.getKey());
            lineV.add(systemLocale.getLanguage());
            lineV.add(systemLocale.getCountry());
            lineV.add(systemLocale.getVariant());
            lineV.add(articleDescription.getDescription());

            articleDescriptionTableDataV.add(lineV);
        }

        // Fügt die neu erzeugten Artikel dem <code>TableModel</code> hinzu.
        this.articleDescriptionTableModel.setDataVector(articleDescriptionTableDataV, this.createI18NTableHeader(articleDescriptionTableHeaderV, ARTICLE_DESCRIPTION_TABLE_ORDER));

        // Zeichnet die Artikelbeschreibungen Tabelle neu.
        articleDescriptionTable.repaint();
    }

    private void renewArticleTableModel() {

        /*
         * Entfernt alle schon vorhandenen Artikel von diesem <code>Vector</code>
         * Dies muss gemacht werden, das sonst alle Einträge die schon vorhanden
         * sind auch nochmal angezeigt werden.
         */
        articleTableDataV.removeAllElements();

        /*
         * Gibt eine <code>List</code> mit allen <code>Article</code> die in der
         * Datenbank gespeichert sind zurück.
         */
        List articles = baseService.getAllArticles();

        /*
         * Iteriert über die Artikel Liste und fügt jeden <code>Article</code> dem
         * Daten <code>Vector</code> hinzu.
         */
        for (Object o : articles) {

            /*
             * Sicherheitsabfrage ob es sich bei diesem <code>Object</code> auch wirklich
             * um eine Instanz der Klasse <code>Article</code> handelt.
             */
            if (o instanceof Article) {

                // Ein Artikel aus der erhaltenen Liste der Datenbank.
                Article article = (Article) o;

                // Fügt den Artikel dem <code>TableModel</code> hinzu.
                Vector<Object> lineV = this.createVectorOfArticle(article);

                articleTableDataV.add(lineV);
            }
        }

        // Fügt die neu erzeugten Artikel dem <code>TableModel</code> hinzu.
        this.articleTableModel.setDataVector(articleTableDataV, this.createI18NTableHeader(articleTableHeaderV, ARTICLE_TABLE_ORDER));

        this.articleTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(VectorUtils.listToVector(baseService.getAllSystemUnits())))));

        // @todo Dieses wird verwendet um in der Tabelle direkt Änderungen am Artikel vorzunehmen. -> verbesserungswürdig
        this.articleTableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {

                if (e.getType() == TableModelEvent.UPDATE) {

                    int rowFirst = e.getFirstRow();
                    int rowLast = e.getLastRow();

                    if (rowFirst != rowLast) {
                        // @todo Werfe eine Exception, da nur ein Artikel ausgewählt und verändert werden können soll.
                    }

                    int col = e.getColumn();

                    TableModel tableModel = null;
                    if (e.getSource() instanceof TableModel) {
                        tableModel = (TableModel) e.getSource();
                    }

                    if (tableModel != null && rowFirst > -1) {
                        System.out.println(rowFirst + " : " + col + " | ARTIKEL ID = " + tableModel.getValueAt(rowFirst, 0) + " | WERT = " + tableModel.getValueAt(rowFirst, col));

                        // Holt den Wert in der Spalte 0 und markierten Reihe.
                        Object articleNumberObj = tableModel.getValueAt(rowFirst, 0);

                        if (articleNumberObj instanceof String) {

                            Object selectedColumnValue = tableModel.getValueAt(rowFirst, col);

                            // @todo Änderungen vornehmen, denn wenn Artikelnummer geändert wird kann diese ja hier nicht mehr gefunden werden.
                            Article article = baseService.getArticleByArticleNumber((String) articleNumberObj);

                            boolean changed = false;

                            String colHeader = ARTICLE_TABLE_ORDER[col];

                            if (colHeader.equals(Constants.ARTICLE_NR)) {
                                article.setArticleNumber((String) selectedColumnValue);
                                changed = true;
                            }
                            else if (colHeader.equals(Constants.UNIT)) {
                                article.setSystemUnit((SystemUnit) selectedColumnValue);
                                changed = true;
                            }
                            else if (colHeader.equals(Constants.SINGLE_PRICE)) {
                                article.setPrice(Double.valueOf((String) selectedColumnValue));
                                changed = true;
                            }
                            else if (colHeader.equals(Constants.DESCRIPTION)) {
                                try {
                                    article.getLocalizedArticleDescription().setDescription((String) selectedColumnValue);
                                }
                                catch (LocalizerException e1) {
                                    ArticleDescription articleDescription = new ArticleDescription();

                                    SystemLocale systemLocale = baseService.getSystemLocaleByLocale(WorkArea.getLocale());

                                    articleDescription.setDescription((String) selectedColumnValue);
                                    articleDescription.setSystemLocale(systemLocale);

                                    article.addArticleDescription(articleDescription);
                                }
                                changed = true;
                            }
                            else if (colHeader.equals(Constants.IN_STOCK)) {
                                article.setInStock(Double.valueOf((String) selectedColumnValue));
                                changed = true;
                            }
                            else if (colHeader.equals(Constants.BUNDLE_UNIT)) {
                                article.setBundleSystemUnit((SystemUnit) selectedColumnValue);
                                changed = true;
                            }
                            else if (colHeader.equals(Constants.BUNDLE_CAPACITY)) {
                                article.setBundleCapacity(Double.valueOf((String) selectedColumnValue));
                                changed = true;
                            }

                            if (changed) {
                                baseService.saveOrUpdateArticle(article);
                            }

                            ArticleUI.this.renewArticleTableModel();
                            ArticleUI.this.renewArticleDescriptionTableModel(article);
                        }
                    }
                }
            }
        });

        // Zeichnet die Tabelle nach hinzufügen des Artikels neu.
        articleTable.repaint();
    }

    /**
     * Fügt einen <code>Article</code> dem <code>TableModel</code> hinzu und zeichnet die
     * Artikeltabelle neu.
     *
     * @param article Der <code>Article</code> der in die Artikeltabelle eingefügt werden soll.
     */
    private void addArticleToTableModel(Article article) {

        // Fügt den <code>Article</code> dem <code>TableModel</code> hinzu.
        this.articleTableModel.addRow(this.createVectorOfArticle(article));

        // Zeichnet die Tabelle nach hinzufügen des Artikels neu.
        articleTable.repaint();
    }

    /**
     * Erzeugt einen <code>Vector</code> des Artikels.
     *
     * @param article Ein <code>Article</code> der in einen <code>Vector</code> umgewandelt
     *                werden soll.
     * @return Gibt den <code>Vector</code> zurück der aus dem <code>Article</code> erzeugt wurde.
     */
    private Vector<Object> createVectorOfArticle(Article article) {
        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> lineV = new Vector<Object>();

        // Setzen der Werte eines <code>Article</code> im Zeilen Datenvektor.
        lineV.add(article.getArticleNumber());
        lineV.add(article.getSystemUnit());
        lineV.add(article.getPrice());
        lineV.add(article.getLocalizedDescription());
        lineV.add(article.getInStock());
        lineV.add(article.getBundleSystemUnit());
        lineV.add(article.getBundleCapacity());

        return lineV;
    }

    /**
     * Gibt die Artikelnummer des markierten Artikels in der Tabelle
     * zurück.
     *
     * @param rowDifferece Die Anzahl um wieviele Zeilen die aktuell
     *                     markierte Zeile verschoben werden soll.
     * @return Die <code>String</code> Artikelnummer des markierten
     *         Artikels in der Tabelle.
     */
    private String getArticleNumberOfSelectedRow(int rowDifferece) {

        // Die markierte Reihe in der Artikeltabelle.
        int row = articleTable.getSelectedRow();

        // Addieren des Verschiebungsgrades.
        row += rowDifferece;

        /*
        * Gibt das <code>Object</code> zurück das im <code>TableModel</code>
        * der Tabelle an der Stelle der markierten Reihe und Spalte 0 steht.
        *
        */
        Object objId = null;
        try {
            objId = ArticleUI.this.articleTableModel.getValueAt(row, 0);
        }
        catch (Exception e) {
            // Falls kein Datensatz an einer Zeile vorhanden ist wird nichts unternommen.
        }

        /*
         * Da das zurückgelieferte <code>Object</code> ein <code>String</code>
         * sein sollte wird es nach der Sicherheitsabfrage in einen <code>String</code>
         * gecastet und zurückgegeben.
         * Jeder Artikel muss eine eindeutige Artikelnummer besitzen, somit kann
         * es auch nicht passieren, dass der <code>String</code> null annehmen
         * kann.
         */
        String articleNumber = null;
        if (objId instanceof String) {
            articleNumber = (String) objId;
        }

        return articleNumber;
    }

    /**
     * Zeigt den Artikel mit der übergebenen Artikelnummer auf dem oberen Teil
     * des Übersichts Panels an. Dadurch kann bspw ein Artikel verändert werden.
     */
    private void showArticle(String articleNumber) {

        /*
         * Der <code>Article</code> der unter der übergebenen Artikelnummer
         * gefunden wurde.
         */
        Article article = baseService.getArticleByArticleNumber(articleNumber);

        // Setzen der einzelnen Werte des Artikels auf die Eingabefelder.
        articleNumberTF.setText(article.getArticleNumber());
        unitCBModel.setSelectedItem(article.getSystemUnit());
        priceSpModel.setValue(article.getPrice());
        inStockSpModel.setValue(article.getInStock());
        bundleUnitCBModel.setSelectedItem(article.getBundleSystemUnit());
        bundleCapacitySpModel.setValue(article.getBundleCapacity());
        descriptionTA.setText(article.getLocalizedDescription());

        this.renewArticleDescriptionTableModel(article);

        baseService.evict(article);

        this.repaint();
    }

    /**
     * Speichert einen neuen Artikel oder ändert einen in der Datenbank schon
     * vorhandenen Artikel. Die Werte dazu werden aus den Eingabefeldern
     * gelesen. Die zu der eingestellten <code>Locale</code> gehörigen Beschreibung
     * wird gespeichert, nachem zu der <code>Locale</code> gehörigen
     * <code>SystemLocale</code> gefunden wurde.
     *
     * @return Gibt den zu speichernden Artikel zurück.
     */
    private Article saveOrUpdateArticle() {

        // Erzeugt einen neuen Artikel und eine zugehörige Default Artikelbeschreibung.
        Article article = new Article();
        ArticleDescription localeArticleDescription = new ArticleDescription();

        // Setzt einige Werte aus den Eingabefeldern in den <code>Article</code>.
        article.setArticleNumber(articleNumberTF.getText());
        article.setSystemUnit((SystemUnit) unitCBModel.getSelectedItem());
        article.setPrice((Double) priceSpModel.getValue());
        article.setInStock((Double) inStockSpModel.getValue());
        article.setBundleSystemUnit((SystemUnit) bundleUnitCBModel.getSelectedItem());
        article.setBundleCapacity((Double) bundleCapacitySpModel.getValue());

        /*
         * Hole die <code>SystemLocale</code> die der eingestellten <code>Locale</code> in
         * der <code>WorkArea</code> am ähnlichsten ist.
         */
        SystemLocale systemLocale = baseService.getSystemLocaleByLocale(WorkArea.getLocale());

        // Setzt die restlichen Werte aus den Eingabefeldern in die Artikelbeschreibung.
        localeArticleDescription.setDescription(descriptionTA.getText());
        localeArticleDescription.setSystemLocale(systemLocale);

        // Fügt diese <code>ArticleDescription</code> dem Artikel hinzu.
        article.addArticleDescription(localeArticleDescription);

        // Speichert oder ändert den <code>Article</code> falls dieser schon vorhanden wäre.
        baseService.saveOrUpdateArticle(article);

        return article;
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

        JPanel descrPanel = new JPanel(new BorderLayout());
        descrPanel.setBorder(descriptionBorder);

        descriptionsTA.setLineWrap(true);
        descriptionsTA.setWrapStyleWord(true);
        descrPanel.add(descriptionsTA, BorderLayout.CENTER);

        descrPanel.setBounds(140, 20, 500, 140);
        localeSettingP.add(descrPanel);

        JPanel descriptionTopP = new JPanel(new BorderLayout());

        descriptionTopP.add(localeSettingP, BorderLayout.CENTER);
        JPanel descriptionsTableP = new JPanel(new BorderLayout());

        descriptionsTableP.add(new JScrollPane(new JTable(new Object[][]{{"a", "a", "a", "a", "a"}, {"b", "b", "b", "b", "b"}}, new Object[]{"test1", "test2", "test3", "test4", "test5"})));

        descriptionTopP.setPreferredSize(new Dimension(400, 200));

        descriptionsP.add(descriptionTopP, BorderLayout.NORTH);
        descriptionsP.add(descriptionsTableP, BorderLayout.CENTER);
    }
}