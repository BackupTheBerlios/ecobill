package ecobill.module.base.ui;

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
import ecobill.core.ui.component.BoxItem;
import ecobill.util.UnitUtils;
import ecobill.util.exception.LocalizerException;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.ArticleDescription;
import ecobill.module.base.domain.SystemLocale;
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
 * @version $Id: ArticleUI.java,v 1.4 2005/07/30 11:18:03 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleUI extends JInternalFrame implements InitializingBean {

    /**
     * Die <code>ArticleUI</code> stellt ein Singleton dar, da es immer nur eine
     * Instanz pro Arbeitsplatz geben kann.
     * -> spart kostbare Ressourcen.
     */
    private static ArticleUI singelton = null;

    /**
     * Gibt die einzigste Instanz der <code>ArticleUI</code> zur�ck um diese
     * dann bspw im Hauptfenster anzeigen zu k�nnen.
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
     * Das Primary <code>JTabbedPane</code> beinhaltet alle n�tigen Register, Eingabemasken
     * f�r die Eingabe der Artikeldaten und die <code>JTable</code> zur Anzeige aller Artikel.
     */
    private JTabbedPane primaryTP = new JTabbedPane();

    /**
     * Das �bersichtspanel gibt Auskunft �ber alle Artikel per <code>JTable</code> und bietet
     * eine Eingabemaske (auch f�r �nderungen) von Artikeln.
     */
    private JPanel overviewP = new JPanel(new BorderLayout());

    /**
     * Dieses Panel bietet wie oben genannt die Eingabemaske (auch �nderungen) f�r Artikel.
     * Sowie die einzelnen betitelten Rahmen um die einzelnen Eingabemasken.
     */
    private JPanel overviewTopP = new JPanel(null);
    private TitledBorder dataBorder = new TitledBorder(new EtchedBorder());
    private TitledBorder bundleBorder = new TitledBorder(new EtchedBorder());
    private TitledBorder descriptionBorder = new TitledBorder(new EtchedBorder());

    private JPanel descriptionsP = new JPanel(null);

    /**
     * Alle Labels die n�tig sind um die GUI erkl�rend zu gestalten.
     */
    private JLabel articleNumberL = new JLabel();
    private JLabel unitL = new JLabel();
    private JLabel priceL = new JLabel();
    private JLabel inStockL = new JLabel();
    private JLabel bundleUnitL = new JLabel();
    private JLabel bundleCapacityL = new JLabel();
    private JLabel languageL = new JLabel();
    private JLabel countryL = new JLabel();

    /**
     * Alle n�tigen Eingabemasken, wie <code>JComboBox</code>, <code>JSpinner</code>,...
     */
    private JTextField articleNumberTF = new JTextField();
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

    /**
     * Alle ben�tigten <code>JButton</code> um bspw. Artikel oder Beschreibungen
     * zu speichern/�ndern.
     */
    private JButton saveB = new JButton();
    private JButton addDescriptionB = new JButton();

    /**
     * Die <code>JScrollPane</code> um die Tabelle scrollen zu k�nnen, die Tabelle
     * selbst und das zugeh�rige Model im MVC Stil.
     * Dazu noch einen <code>Vector</code> der sp�ter die internationalisierten Header
     * aufnimmt.
     */
    private JScrollPane articleTableSP = new JScrollPane();
    private Vector<String> articleTableHeaderV = new Vector<String>();
    private Vector<Vector<Object>> articleTableDataV = new Vector<Vector<Object>>();
    private JTable articleTable = new JTable();
    private DefaultTableModel articleTableModel = new DefaultTableModel();

    /**
     * Standard Konstruktor.
     */
    public ArticleUI() {

    }

    /**
     * Globale Initialisierung des Artikel User Interfaces.
     * Es wird die Gr��e, minimale Gr��e des Fensters,... gesetzt.
     * Diese wird nach den gesetzten Properties des <code>ApplicationContext</code>
     * durchgef�hrt.
     */
    public void afterPropertiesSet() {

        /*
         * Es wird die Gr��e, das Layout und verschiedenste Optionen gesetzt.
         */
        this.setSize(new Dimension(615, 525));
        this.setMinimumSize(new Dimension(615, 325));
        this.setLayout(new BorderLayout());
        this.setClosable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setResizable(true);

        /*
         * Startet die Initialisierung der Artikel Oberfl�che.
         */
        this.initUI();
    }

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
     * Initialisieren des Graphical User Interface mit all seinen Panels, Eingabemasken
     * Rahmen, Buttons, usw.
     */
    private void initUI() {

        /*
         * Initilisieren der einzelnen GUI Komponenten.
         */
        this.initOverviewTopP();
        this.initOverviewBottomP();
        this.initDescriptionP();

        /*
         * Setzt auf das �bersichtspanel die Eingabemaske f�r Artikel und die
         * Tabelle die alle Artikel beinh�lt.
         */
        overviewP.add(overviewTopP, BorderLayout.NORTH);
        overviewP.add(articleTableSP, BorderLayout.CENTER);

        /*
         * F�gt das �bersichtspanel und das Beschreibungenpanel der prim�ren <code>JTabbedPane</code>
         * hinzu.
         */
        primaryTP.add(overviewP);
        primaryTP.add(descriptionsP);

        /*
         * F�gt die prim�re <code>JTabbedPane</code> diesem <code>JInternalFrame</code> hinzu.
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
    private void reinitI18N() {

        /*
         * Setzt den I18N Artikel Fenster Titel.
         */
        this.setTitle(WorkArea.getMessage(Constants.UI_TITLE_ARTICLE));

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

        /*
         * Setzt die Beschriftung der einzelnen Buttons.
         */
        saveB.setText(WorkArea.getMessage(Constants.SAVE));
        saveB.setToolTipText(WorkArea.getMessage(Constants.SAVE_TOOLTIP));

        addDescriptionB.setText(WorkArea.getMessage(Constants.ADD));
        addDescriptionB.setToolTipText(WorkArea.getMessage(Constants.ADD_TOOLTIP));

        /*
         * Setzt die �berschriften der Artikel Tabelle.
         */
        articleTableModel.setColumnIdentifiers(this.createI18NTableHeader());

        this.repaint();
    }

    /**
     * Gibt einen <code>Vector</code> mit den landesspezifischen Tabellen Header
     * Bezeichnungen zur�ck.
     *
     * @return Ein <code>Vector</code> mit landesspezifischen Tabellen Header
     *         Bezeichnungen.
     */
    private Vector<String> createI18NTableHeader() {

        // Entfernen aller Elemente im Vector.
        articleTableHeaderV.removeAllElements();

        /*
         * Hinzuf�gen der landesspezifischen Bezeichnungen in der richtigen
         * Reihenfolge.
         */
        articleTableHeaderV.add(WorkArea.getMessage(Constants.ARTICLE_NR));
        articleTableHeaderV.add(WorkArea.getMessage(Constants.UNIT));
        articleTableHeaderV.add(WorkArea.getMessage(Constants.SINGLE_PRICE));
        articleTableHeaderV.add(WorkArea.getMessage(Constants.DESCRIPTION));
        articleTableHeaderV.add(WorkArea.getMessage(Constants.IN_STOCK));
        articleTableHeaderV.add(WorkArea.getMessage(Constants.BUNDLE_UNIT));
        articleTableHeaderV.add(WorkArea.getMessage(Constants.BUNDLE_CAPACITY));

        return articleTableHeaderV;
    }

    /**
     * Initialisieren des oberen Teiles der �bersicht. Diese beinhaltet Eingabefelder
     * und einen Speichern/�ndern Button.
     */
    private void initOverviewTopP() {

        // Setzt einen Rahmen um das obere �bersicht Panel.
        overviewTopP.setBorder(new EtchedBorder());

        // Setzt die Gr��e des Panels.
        overviewTopP.setPreferredSize(new Dimension(300, 320));

        // Ein Panel f�r den Artikel und die Hauptdaten.
        JPanel articleP = new JPanel(null);
        dataBorder.setTitleColor(Color.BLACK);
        articleP.setBorder(dataBorder);

        // Ein Panel f�r die Gebindedaten des Artikels.
        JPanel articleBundleP = new JPanel(null);
        bundleBorder.setTitleColor(Color.BLACK);
        articleBundleP.setBorder(bundleBorder);

        // Ein Panel f�r die Beschreibung der eingestellten Sprache.
        JPanel articleDescriptionP = new JPanel(null);
        descriptionBorder.setTitleColor(Color.BLACK);
        articleDescriptionP.setBorder(descriptionBorder);

        /*
        * Setzt die Position der einzelnen Komponenten und hinzuf�gen
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
         * Setzt die Position der einzelnen Komponenten und hinzuf�gen
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
        descriptionSP.setBounds(10, 25, 450, 100);
        descriptionSP.setBorder(new BevelBorder(BevelBorder.LOWERED));
        articleDescriptionP.add(descriptionSP);

        /*
         * Setzt die Positionen der einzelnen Panels auf dem oberen �berblick
         * Panel.
         */
        articleP.setBounds(10, 10, 340, 125);
        overviewTopP.add(articleP);

        articleBundleP.setBounds(360, 10, 120, 125);
        overviewTopP.add(articleBundleP);

        articleDescriptionP.setBounds(10, 140, 470, 140);
        overviewTopP.add(articleDescriptionP);

        /*
         * Setzt den Speichern/�ndern Button auf das Panel.
         */
        saveB.setBounds(10, 290, 100, 20);
        overviewP.add(saveB);

        saveB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArticleUI.this.saveOrUpdateArticle();
                ArticleUI.this.renewArticleTableModel();
            }
        });
    }

    /**
     * Initialisieren des unteren Teils des �bersicht Panels mit der
     * darin enthaltenen Artikeltabelle und den zugeh�rigen Listenern
     * der Tabelle.
     */
    private void initOverviewBottomP() {

        /*
         * F�gt die Tabelle in die <code>JScrollPane</code> ein.
         */
        articleTableSP.setViewportView(articleTable);
        articleTable.setModel(articleTableModel);

        /*
         * F�gt der Artikeltabelle einen <code>MouseListener</code> hinzu,
         * der darauf wartet bis die linke Taste der Maus gedr�ckt wird und
         * dann den markierten Artikel auf den Eingabefeldern anzeigt.
         */
        this.articleTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                /*
                 * Achtet darauf, dass wirklich nur dann ein Artikel angezeigt
                 * wird wenn auch nur die linke Maustaste gedr�ckt wird.
                 * Alle anderen werden ignoriert.
                 */
                if (e.getButton() == MouseEvent.BUTTON1) {
                    ArticleUI.this.showArticle(getArticleNumberOfSelectedRow());
                }
            }
        });

        /*
         * F�gt der Artikeltabelle einen <code>KeyListener</code> hinzu,
         * der darauf wartet bis die Pfeiltaste nach oben oder nach unten
         * gedr�ckt wird und dann den markierten Artikel auf den Eingabefeldern
         * anzeigt.
         */
        this.articleTable.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                /*
                 * Achtet darauf, dass auch wirklich nur dann ein Artikel angezeigt
                 * wird wenn ein neuer Artikel selektiert wird. Dies geschieht hier
                 * nur wenn die Pfeiltaste nach oben oder nach unten gedr�ckt wird.
                 */
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    ArticleUI.this.showArticle(getArticleNumberOfSelectedRow());
                }
            }
        });
    }

    // @todo Mache hier mit diesem Tabellen Framework das auf der Galileoseite angegeben ist weiter.
    private void renewArticleTableModel() {

        /*
         * Entfernt alle schon vorhandenen Artikel von diesem <code>Vector</code>
         * Dies muss gemacht werden, das sonst alle Eintr�ge die schon vorhanden
         * sind auch nochmal angezeigt werden.
         */
        articleTableDataV.removeAllElements();

        /*
         * Gibt eine <code>List</code> mit allen <code>Article</code> die in der
         * Datenbank gespeichert sind zur�ck.
         */
        List articles = baseService.getAllArticles();

        /*
         * Iteriert �ber die Artikel Liste und f�gt jeden <code>Article</code> dem
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

                // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
                Vector<Object> lineV = new Vector<Object>();

                // Setzen der Werte eines <code>Article</code> im Zeilen Datenvektor.
                lineV.add(article.getArticleNumber());
                lineV.add(WorkArea.getMessage(article.getUnitKey()));
                lineV.add(article.getPrice());
                lineV.add(article.getLocalizedDescription());
                lineV.add(article.getInStock());
                lineV.add(WorkArea.getMessage(article.getBundleUnitKey()));
                lineV.add(article.getBundleCapacity());

                // F�gt die Zeile dem Datenvektor hinzu.
                articleTableDataV.add(lineV);
            }
        }

        // Setzt den Datenvektor und somit die Daten in die Artikeltabelle.
        this.articleTableModel.setDataVector(articleTableDataV, this.createI18NTableHeader());

        // @todo wird das noch ben�tigit?
        this.articleTableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                /*
                int row = e.getFirstRow();
                int col = e.getColumn();

                TableModel clazz = (TableModel) e.getSource();

                System.out.println(row + " : " + col + " | ARTIKEL ID = " + clazz.getValueAt(row, 0));
                */
            }
        });
    }

    /**
     * Gibt die Artikelnummer des markierten Artikels in der Tabelle
     * zur�ck.
     *
     * @return Die <code>String</code> Artikelnummer des markierten
     *         Artikels in der Tabelle.
     */
    private String getArticleNumberOfSelectedRow() {

        // Die markierte Reihe in der Artikeltabelle.
        int row = articleTable.getSelectedRow();

        /*
         * Gibt das <code>Object</code> zur�ck das im <code>TableModel</code>
         * der Tabelle an der Stelle der markierten Reihe und Spalte 0 steht.
         *
         */
        Object objId = ArticleUI.this.articleTableModel.getValueAt(row, 0);

        /*
         * Da das zur�ckgelieferte <code>Object</code> ein <code>String</code>
         * sein sollte wird es nach der Sicherheitsabfrage in einen <code>String</code>
         * gecastet und zur�ckgegeben.
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
     * Zeigt den Artikel mit der �bergebenen Artikelnummer auf dem oberen Teil
     * des �bersichts Panels an. Dadurch kann bspw ein Artikel ver�ndert werden.
     */
    private void showArticle(String articleNumber) {

        /*
         * Der <code>Article</code> der unter der �bergebenen Artikelnummer
         * gefunden wurde.
         */
        Article article = baseService.getArticleByArticleNumber(articleNumber);

        // Setzen der einzelnen Werte des Artikels auf die Eingabefelder.
        articleNumberTF.setText(article.getArticleNumber());
        unitCBModel.setSelectedItem(new BoxItem(article.getUnitKey()));
        priceSpModel.setValue(article.getPrice());
        inStockSpModel.setValue(article.getInStock());
        bundleUnitCBModel.setSelectedItem(new BoxItem(article.getBundleUnitKey()));
        bundleCapacitySpModel.setValue(article.getBundleCapacity());
        descriptionTA.setText(article.getLocalizedDescription());
    }

    /**
     * Speichert einen neuen Artikel oder �ndert einen in der Datenbank schon
     * vorhandenen Artikel. Die Werte dazu werden aus den Eingabefeldern
     * gelesen. Die zu der eingestellten <code>Locale</code> geh�rigen Beschreibung
     * wird gespeichert, nachem zu der <code>Locale</code> geh�rigen
     * <code>SystemLocale</code> gefunden wurde.
     */
    private void saveOrUpdateArticle() {

        Article article = new Article();
        ArticleDescription localeArticleDescription = new ArticleDescription();

        article.setArticleNumber(articleNumberTF.getText());
        article.setUnitKey(((BoxItem) unitCBModel.getSelectedItem()).getName());
        article.setPrice((Double) priceSpModel.getValue());
        article.setInStock((Double) inStockSpModel.getValue());
        article.setBundleUnitKey(((BoxItem) bundleUnitCBModel.getSelectedItem()).getName());
        article.setBundleCapacity((Double) bundleCapacitySpModel.getValue());

        localeArticleDescription.setDescription(descriptionTA.getText());

        SystemLocale systemLocale = baseService.getSystemLocaleByLocale(WorkArea.getLocale());

        System.out.println("SystemLocale: " + systemLocale);

        localeArticleDescription.setSystemLocale(systemLocale);

        article.addArticleDescription(localeArticleDescription);

        baseService.saveOrUpdateArticle(article);
    }

    private void initDescriptionP() {

        descriptionsP.setBorder(new BevelBorder(BevelBorder.RAISED));

        descriptionsTA.setBorder(new BevelBorder(BevelBorder.LOWERED));
        descriptionsTA.setLineWrap(true);
        descriptionsTA.setWrapStyleWord(true);
        //descriptionsTA.setFont(descriptionsTA.getFont().deriveFont(11));

        languageL.setBounds(10, 120, 100, 20);
        countryL.setBounds(10, 145, 100, 20);

        descriptionsTA.setBounds(10, 10, 360, 100);
        languageCB.setBounds(110, 120, 100, 20);
        countryCB.setBounds(110, 145, 100, 20);
        addDescriptionB.setBounds(240, 145, 100, 20);

        descriptionsP.add(languageL);
        descriptionsP.add(countryL);

        descriptionsP.add(descriptionsTA);
        descriptionsP.add(languageCB);
        descriptionsP.add(countryCB);
        descriptionsP.add(addDescriptionB);
    }
}