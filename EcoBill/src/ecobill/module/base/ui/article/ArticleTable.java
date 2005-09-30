package ecobill.module.base.ui.article;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.system.Internationalization;
import ecobill.core.system.Persistable;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdKeyItem;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.SystemUnit;
import ecobill.module.base.domain.SystemLocale;
import ecobill.module.base.domain.ArticleDescription;
import ecobill.module.base.service.BaseService;
import ecobill.util.VectorUtils;
import ecobill.util.exception.LocalizerException;

import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.io.*;

import org.jdesktop.layout.GroupLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Die <code>ArticleTable</code> beinhaltet die Tabelle zur Anzeige der Artikel. Desweiteren
 * kann das <code>TableColumnModel</code> serialisiert und in einer Datei abgelegt werden.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: ArticleTable.java,v 1.4 2005/09/30 09:06:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleTable extends JPanel implements Internationalization, Persistable {

    /**
     * In diesem <code>Log</code> können Fehler, Info oder sonstige Ausgaben erfolgen.
     * Diese Ausgaben können in einem separaten File spezifiziert werden.
     */
    private static final Log LOG = LogFactory.getLog(ArticleTable.class);

    /**
     * Die Reihenfolge wie die Spalten angezeigt werden, sofern das <code>TableColumnModel</code>
     * noch nicht serialisiert wurde.
     */
    private final static Vector<I18NItem> ARTICLE_TABLE_ORDER = new Vector<I18NItem>();

    static {
        ARTICLE_TABLE_ORDER.add(new I18NItem(Constants.ARTICLE_NR));
        ARTICLE_TABLE_ORDER.add(new I18NItem(Constants.UNIT));
        ARTICLE_TABLE_ORDER.add(new I18NItem(Constants.SINGLE_PRICE));
        ARTICLE_TABLE_ORDER.add(new I18NItem(Constants.DESCRIPTION));
        ARTICLE_TABLE_ORDER.add(new I18NItem(Constants.IN_STOCK));
        ARTICLE_TABLE_ORDER.add(new I18NItem(Constants.BUNDLE_UNIT));
        ARTICLE_TABLE_ORDER.add(new I18NItem(Constants.BUNDLE_CAPACITY));
    };

    /**
     * Die <code>ArtikelUI</code> um den Artikel anzeigen zu können.
     */
    private ArticleUI articleUI;

    /**
     * Der <code>BaseService</code> ist die Business Logik. Unter anderem können hierdurch Daten
     * aus der Datenbank ausgelesen und gespeichert werden.
     */
    private BaseService baseService;

    /**
     * Der <code>Border</code> der um das <code>JPanel</code> gelegt wird.
     */
    private TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.ARTICLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));

    /**
     * Das <code>TableModel</code> beinhaltet die eigentlichen Daten, die zur Anzeige verwendet werden
     * sollen.
     */
    private DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Die eigentlich <code>JTable</code> mit ihrem <code>TableModel</code>.
     */
    private JTable table = new JTable(tableModel);

    /**
     * Eine <code>JScrollPane</code> um zu ermöglichen, dass die Tabelle gescrollt werden kann und der
     * Tabellen Header angezeigt wird.
     */
    private JScrollPane tableSP = new JScrollPane();

    /**
     * Erzeugt eine neues Article Table Panel für Artikel.
     */
    public ArticleTable(ArticleUI articleUI, BaseService baseService) {
        this.articleUI = articleUI;
        this.baseService = baseService;

        initComponents();
        initLayout();
        reinitI18N();

        initListeners();

        tableModel.setColumnIdentifiers(ARTICLE_TABLE_ORDER);

        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(VectorUtils.listToVector(baseService.getAllSystemUnits())))));

        renewArticleTableModel();

        unpersist();
    }

    /**
     * Initialisiert die Komponenten.
     */
    private void initComponents() {

        setBorder(border);

        tableSP.setViewportView(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Initialisiert den <code>LayoutManager</code>.
     */
    private void initLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(tableSP, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(tableSP, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

    /**
     * Diese Methode stammt aus dem Interface <code>Internationalization</code> um die Komponente
     * mit der landesspezifischen Sprache zu reinitialisieren.
     */
    public void reinitI18N() {
        border.setTitle(WorkArea.getMessage(Constants.ARTICLE));

        tableModel.setColumnIdentifiers(ARTICLE_TABLE_ORDER);
    }

    public void renewArticleTableModel() {

        /*
         * Entfernt alle schon vorhandenen Artikel von diesem <code>Vector</code>
         * Dies muss gemacht werden, das sonst alle Einträge die schon vorhanden
         * sind auch nochmal angezeigt werden.
         */
        Vector dataVector = tableModel.getDataVector();

        dataVector.removeAllElements();

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

                dataVector.add(lineV);
            }
        }
        
        // Zeichnet die Tabelle nach hinzufügen des Artikels neu.
        table.repaint();
    }

    private Long id;

    private void initListeners() {

        // Ein <code>KeyListener</code> um auf VK_UP und VK_DOWN in der Tabelle
        // geeignet zu reagieren.
        table.addKeyListener(new KeyAdapter() {

            /**
             * @see KeyAdapter#keyPressed(java.awt.event.KeyEvent)
             */
            public void keyPressed(KeyEvent e) {

                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {

                    int row = table.getSelectedRow();

                    if (keyCode == KeyEvent.VK_UP) {
                        --row;
                    }
                    else if (keyCode == KeyEvent.VK_DOWN) {
                        ++row;
                    }

                    try {
                        id = ((IdKeyItem) tableModel.getValueAt(row, 0)).getId();
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(aioobe.getMessage(), aioobe);
                        }
                    }

                    articleUI.showArticle(id);
                }
            }
        });

        // Ein <code>MouseAdapter</code> mit einer implementierten mouseDown Methode
        // um auf Klicks auf die Tabelle geeignet zu reagieren.
        table.addMouseListener(new MouseAdapter() {

            /**
             * @see MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {
                    int row = table.getSelectedRow();

                    id = ((IdKeyItem) tableModel.getValueAt(row, 0)).getId();

                    articleUI.showArticle(id);
                }
            }
        });

        // Ein <code>TableModelListener</code> um die Änderungen der Tabellendaten in der
        // Datenbank zu persistieren.
        this.tableModel.addTableModelListener(new TableModelListener() {

            /**
             * @see TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
             */
            public void tableChanged(TableModelEvent e) {

                // Überprüfe das <code>TableModelEvent</code> auf UPDATE.
                if (e.getType() == TableModelEvent.UPDATE) {

                    // Die Reihe und Spalte des zu verändernden Wertes.
                    int row = e.getFirstRow();
                    int col = e.getColumn();

                    // Sicherheitscheck.
                    if (row > -1 && row < tableModel.getRowCount()) {

                        // Lade betreffenden Artikel von der Datenbank.
                        Article article = (Article) baseService.load(Article.class, id);

                        // Veränderter Wert.
                        Object value = tableModel.getValueAt(row, col);

                        // Der übersetzte Name der Spalte um herauszufinden welcher Wert überhaupt
                        // geändert werden muss.
                        String columnName = tableModel.getColumnName(col);

                        if (columnName.equals(WorkArea.getMessage(Constants.ARTICLE_NR))) {
                            article.setArticleNumber((String) value);
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.UNIT))) {
                            article.setSystemUnit((SystemUnit) value);
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.SINGLE_PRICE))) {
                            article.setPrice(Double.valueOf((String) value));
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.DESCRIPTION))) {
                            try {
                                article.getLocalizedArticleDescription().setDescription((String) value);
                            }
                            catch (LocalizerException le) {
                                ArticleDescription articleDescription = new ArticleDescription();

                                SystemLocale systemLocale = baseService.getSystemLocaleByLocale(WorkArea.getLocale());

                                articleDescription.setDescription((String) value);
                                articleDescription.setSystemLocale(systemLocale);

                                article.addArticleDescription(articleDescription);
                            }
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.IN_STOCK))) {
                            article.setInStock(Double.valueOf((String) value));
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.BUNDLE_UNIT))) {
                            article.setBundleSystemUnit((SystemUnit) value);
                        }
                        else if (columnName.equals(WorkArea.getMessage(Constants.BUNDLE_CAPACITY))) {
                            article.setBundleCapacity(Double.valueOf((String) value));
                        }

                        baseService.saveOrUpdate(article);

                        if (LOG.isDebugEnabled()) {
                            LOG.debug("In der Spalte [" + col + "] und Zeile [" + row + "] wurde für den Artikel [id=\"" + id + "\"] der Wert auf \"" + tableModel.getValueAt(row, col) + "\" geändert.");
                        }

                        renewArticleTableModel();
                        articleUI.showArticle(id);
                    }
                }
            }
        });
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
        for (I18NItem order : ARTICLE_TABLE_ORDER) {

            String key = order.getKey();

            if (Constants.ARTICLE_NR.equals(key)) {
                lineV.add(new IdKeyItem(article.getId(), article.getArticleNumber()));
            }
            else if (Constants.UNIT.equals(key)) {
                lineV.add(article.getSystemUnit());
            }
            else if (Constants.SINGLE_PRICE.equals(key)) {
                lineV.add(article.getPrice());
            }
            else if (Constants.DESCRIPTION.equals(key)) {
                lineV.add(article.getLocalizedDescription());
            }
            else if (Constants.IN_STOCK.equals(key)) {
                lineV.add(article.getInStock());
            }
            else if (Constants.BUNDLE_UNIT.equals(key)) {
                lineV.add(article.getBundleSystemUnit());
            }
            else if (Constants.BUNDLE_CAPACITY.equals(key)) {
                lineV.add(article.getBundleCapacity());
            }
        }

        return lineV;
    }

    /**
     * @see ecobill.core.system.Persistable#persist()
     */
    public void persist() {

        try {
            table.removeEditor();

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.SERIALIZE_PATH + "/article/TableColumnModel.ebs"));
            oos.writeObject(table.getColumnModel());
            oos.flush();
            oos.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @see ecobill.core.system.Persistable#unpersist()
     */
    public void unpersist() {

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(Constants.SERIALIZE_PATH + "/article/TableColumnModel.ebs"));

            TableColumnModel columnModel = (TableColumnModel) ois.readObject();

            int unit = columnModel.getColumnIndex(WorkArea.getMessage(Constants.UNIT));
            int bundleUnit = columnModel.getColumnIndex(WorkArea.getMessage(Constants.BUNDLE_UNIT));

            columnModel.getColumn(unit).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(VectorUtils.listToVector(baseService.getAllSystemUnits())))));
            columnModel.getColumn(bundleUnit).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(VectorUtils.listToVector(baseService.getAllSystemUnits())))));

            table.setColumnModel(columnModel);
        }
        catch (IOException ioe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(ioe.getMessage(), ioe);
            }
        }
        catch (ClassNotFoundException cnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error(cnfe.getMessage(), cnfe);
            }
        }
        finally {
            if (ois != null) {
                try {
                    ois.close();
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}