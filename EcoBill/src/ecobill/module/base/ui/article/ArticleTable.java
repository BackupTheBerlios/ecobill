package ecobill.module.base.ui.article;

import ecobill.core.ui.component.GenericTable;
import ecobill.core.system.Constants;
import ecobill.core.system.WorkArea;
import ecobill.core.system.service.Service;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.SystemUnit;
import ecobill.module.base.domain.ArticleDescription;
import ecobill.module.base.domain.SystemLocale;
import ecobill.util.exception.LocalizerException;
import ecobill.util.VectorUtils;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.util.Vector;
import java.util.Collection;

// @todo document me!

/**
 * ArticleTable.
 * <p/>
 * User: rro
 * Date: 09.08.2005
 * Time: 19:38:34
 *
 * @author Roman R&auml;dle
 * @version $Id: ArticleTable.java,v 1.1 2005/08/11 18:10:31 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleTable extends BaseGenericTable {

    /**
     * Die Reihenfolge der Artikeltabelle.
     */
    private final static String[] ARTICLE_TABLE_ORDER = new String[]{Constants.ARTICLE_NR,
                                                                     Constants.UNIT,
                                                                     Constants.SINGLE_PRICE,
                                                                     Constants.DESCRIPTION,
                                                                     Constants.IN_STOCK,
                                                                     Constants.BUNDLE_UNIT,
                                                                     Constants.BUNDLE_CAPACITY};

    /**
     * Erzeugen einer neuen Artikeltabelle mit einer festen Tabellen
     * Header Reihenfolge und einem view <code>Object</code> das die
     * initialen Daten der Tabelle hat.
     *
     * @param view    Die Daten die in der Tabelle angezeigt werden sollen.
     * @param service Dieser <code>Service</code> wird evtl.
     *                benötigt um Daten von der Datenbank zu laden
     *                bzw. wieder dort abzulegen.
     * @see GenericTable#GenericTable(String[], Object)
     */
    public ArticleTable(Object view, Service service) {
        super(ARTICLE_TABLE_ORDER, view, service);
    }

    /**
     * Hier wird für die Artikeltabelle ein Datenvektor erzeugt, der alle
     * Artikel aus einer <code>Collection</code> beinhaltet.
     *
     * @param view Der view besteht in der Artikeltabelle aus einer Collection
     *             von Artikeln.
     * @return Gibt den erzeugten Artikel Datenvektor zurück.
     */
    public Vector<Vector<Object>> reinitTableModel(Object view) {

        /*
         * Prüfen ob es sich hierbei wirklich um eine <code>Collection</code>
         * handelt.
         */
        if (view instanceof Collection) {

            // Expliziter TypeCast in eine <code>Collection</code>.
            Collection collection = (Collection) view;

            // Besorge einen leeren Datenvektor.
            Vector<Vector<Object>> tableData = getEmptyDataVector();

            /*
             * Iteriert über die Artikel <code>Collection</code> und fügt jeden
             * <code>Article</code> dem Daten <code>Vector</code> hinzu.
             */
            for (Object o : collection) {

                /*
                * Sicherheitsabfrage ob es sich bei diesem <code>Object</code> auch wirklich
                * um eine Instanz der Klasse <code>Article</code> handelt.
                */
                if (o instanceof Article) {

                    // Ein Artikel aus der erhaltenen <code>Collection</code> der Datenbank.
                    Article article = (Article) o;

                    // Fügt den Artikel dem <code>TableModel</code> hinzu.
                    Vector<Object> lineV = this.createVectorOfArticle(article);

                    tableData.add(lineV);
                }
            }

            return tableData;
        }

        return null;
    }

    /**
     * Durch die Implementation dieser Methode können der Tabelle Listener
     * hinzugefügt werden.
     *
     * @see ecobill.core.ui.component.GenericTable#initTableListeners()
     */
    protected void initTableListeners() {

        getTableModel().addTableModelListener(new TableModelListener() {

            /**
             * Dieses <code>TableModelEvent</code> wird ausgelöst wenn sich im
             * <code>TableModel</code> etwas ändert. Ein möglicher Status wäre
             * beispielsweise <code>TableModelEvent.UPDATE</code>.
             *
             * @param e Wird ausgelöst wenn sich im <code>TableModel</code> etwas
             *          ändert.
             */
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
                            Article article = ArticleTable.this.getBaseService().getArticleByArticleNumber((String) articleNumberObj);

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

                                    SystemLocale systemLocale = ArticleTable.this.getBaseService().getSystemLocaleByLocale(WorkArea.getLocale());

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
                                getBaseService().saveOrUpdateArticle(article);
                            }

                            reinitTableModel(getBaseService().getAllArticles());
                        }
                    }
                }
            }
        });

    }

    /**
     * Durch die Implementation dieser Methode können der Tabelle (Spalten)
     * <code>CellEditor</code> gesetzt werden.
     * Hier kann das <code>JComponent</code> Array verwendet werden, das dem
     * Konstruktor übergeben wurde.
     *
     * @see GenericTable#initCellEditors(javax.swing.JComponent[])
     */
    protected void initCellEditors(JComponent[] components) {
        // Dies kann leer gelassen werden.
    }

    /**
     * Durch die Implementation dieser Methode können der Tabelle (Spalten)
     * <code>CellEditor</code> gesetzt werden.
     *
     * @see GenericTable#initCellEditors()
     */
    protected void initCellEditors() {

        /*
         * Fügt den <code>TableCellEditor</code> der Spalte 1 hinzu. Dieser CellEditor
         * hat das Aussehen einer <code>JComboBox</code> und enthält alle <code>SystemUnits</code>
         * die in der Datenbank gespeichert sind.
         */
        Vector systemUnits = VectorUtils.listToVector(getBaseService().getAllSystemUnits());
        JComboBox comboBox = new JComboBox(new DefaultComboBoxModel(systemUnits));
        TableCellEditor comboBoxCellEditor = new DefaultCellEditor(comboBox);
        getColumnModel().getColumn(1).setCellEditor(comboBoxCellEditor);
    }

    /**
     * Erzeugt einen <code>Vector</code> des Artikels.
     *
     * @param article Ein <code>Article</code> der in einen <code>Vector</code>
     *                umgewandelt werden soll.
     * @return Gibt den <code>Vector</code> zurück der aus dem <code>Article</code>
     *         erzeugt wurde.
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
}
