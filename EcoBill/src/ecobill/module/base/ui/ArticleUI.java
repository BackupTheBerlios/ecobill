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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.util.UnitUtils;
import ecobill.util.exception.LocalizerException;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.Article;
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
 * @version $Id: ArticleUI.java,v 1.2 2005/07/29 14:11:28 raedler Exp $
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

    private JPanel descriptionsP = new JPanel(null);

    /**
     * Alle Labels die n�tig sind um die GUI erkl�rend zu gestalten.
     */
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
    private JComboBox unitCB = new JComboBox(UnitUtils.getAllUnits());
    private JSpinner priceSp = new JSpinner(new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.01));
    private JSpinner inStockSp = new JSpinner(new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.1));
    private JComboBox bundleUnitCB = new JComboBox(UnitUtils.getAllUnits());
    private JSpinner bundleCapacitySp = new JSpinner(new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.1));
    private JTextArea descriptionTA = new JTextArea();
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
        overviewTopP.setPreferredSize(new Dimension(300, 120));

        // Ein Panel f�r den Artikel und die Hauptdaten.
        JPanel articleP = new JPanel(null);
        dataBorder.setTitleColor(Color.BLACK);
        articleP.setBorder(dataBorder);

        // Ein Panel f�r die Gebindedaten des Artikels.
        JPanel articleBundleP = new JPanel(null);
        bundleBorder.setTitleColor(Color.BLACK);
        articleBundleP.setBorder(bundleBorder);

        /*
         * Setzt die Position der einzelnen Komponenten und hinzuf�gen
         * auf das Artikel Panel.
         */
        unitL.setBounds(12, 20, 100, 20);
        articleP.add(unitL);

        unitCB.setBounds(10, 40, 100, 20);
        articleP.add(unitCB);

        priceL.setBounds(122, 20, 100, 20);
        articleP.add(priceL);

        priceSp.setBounds(120, 40, 100, 20);
        articleP.add(priceSp);

        inStockL.setBounds(232, 20, 100, 20);
        articleP.add(inStockL);

        inStockSp.setBounds(230, 40, 100, 20);
        articleP.add(inStockSp);

        /*
         * Setzt die Position der einzelnen Komponenten und hinzuf�gen
         * auf das Artikel Bundle Panel.
         */
        bundleUnitL.setBounds(12, 20, 100, 20);
        articleBundleP.add(bundleUnitL);

        bundleUnitCB.setBounds(10, 40, 100, 20);
        articleBundleP.add(bundleUnitCB);

        bundleCapacityL.setBounds(122, 20, 100, 20);
        articleBundleP.add(bundleCapacityL);

        bundleCapacitySp.setBounds(120, 40, 100, 20);
        articleBundleP.add(bundleCapacitySp);

        articleP.setBounds(10, 10, 340, 75);
        overviewTopP.add(articleP);

        articleBundleP.setBounds(360, 10, 230, 75);
        overviewTopP.add(articleBundleP);

        /*
         * Setzt den Speichern/�ndern Button auf das Panel.
         */
        saveB.setBounds(10, 90, 100, 20);
        overviewP.add(saveB);
    }

    private void initOverviewBottomP() {

        /*
         * F�gt die Tabelle in die <code>JScrollPane</code> ein.
         */
        articleTableSP.setViewportView(articleTable);
        articleTable.setModel(articleTableModel);
    }


    // @todo Mache hier mit diesem Tabellen Framework das auf der Galileoseite angegeben ist weiter.
    private void renewArticleTableModel() {

        List articles = baseService.getAllArticles();

        for (Object o : articles) {
            if (o instanceof Article) {

                Article article = (Article) o;

                Vector<Object> lineV = new Vector<Object>();

                lineV.add(article.getId());
                lineV.add(WorkArea.getMessage(article.getUnitKey()));
                lineV.add(article.getPrice());

                try {
                    lineV.add(article.getLocalizedDescription());
                }
                catch (LocalizerException e) {
                    try {
                        lineV.add(article.getDescriptions().iterator().next());
                    }
                    catch (Exception e1) {
                        lineV.add("");
                    }
                }

                lineV.add(article.getInStock());
                lineV.add(WorkArea.getMessage(article.getBundleUnitKey()));
                lineV.add(article.getBundleCapacity());

                articleTableDataV.add(lineV);
            }
        }

        this.articleTableModel.setDataVector(articleTableDataV, createI18NTableHeader());

        this.articleTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = ArticleUI.this.articleTable.getSelectedRow();

                Object objId = ArticleUI.this.articleTableModel.getValueAt(row, 0);

                Long id = null;
                if (objId instanceof Long) {
                    id = (Long) objId;
                }

                System.out.println("ID: " + id);

                ArticleUI.this.showArticle(id);
            }
        });

        this.articleTableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int col = e.getColumn();

                TableModel clazz = (TableModel) e.getSource();

                System.out.println(row + " : " + col + " | ARTIKEL ID = " + clazz.getValueAt(row, 0));
            }
        });
    }

    private void showArticle(Long articleId) {
        Article article = baseService.getArticleById(articleId);

        priceSp.setValue(article.getPrice());
        inStockSp.setValue(article.getInStock());
    }

    private void initDescriptionP() {

        descriptionsP.setBorder(new BevelBorder(BevelBorder.RAISED));

        descriptionTA.setBorder(new BevelBorder(BevelBorder.LOWERED));
        descriptionTA.setLineWrap(true);
        descriptionTA.setWrapStyleWord(true);
        //descriptionTA.setFont(descriptionTA.getFont().deriveFont(11));

        languageL.setBounds(10, 120, 100, 20);
        countryL.setBounds(10, 145, 100, 20);

        descriptionTA.setBounds(10, 10, 360, 100);
        languageCB.setBounds(110, 120, 100, 20);
        countryCB.setBounds(110, 145, 100, 20);
        addDescriptionB.setBounds(240, 145, 100, 20);

        descriptionsP.add(languageL);
        descriptionsP.add(countryL);

        descriptionsP.add(descriptionTA);
        descriptionsP.add(languageCB);
        descriptionsP.add(countryCB);
        descriptionsP.add(addDescriptionB);
    }
}