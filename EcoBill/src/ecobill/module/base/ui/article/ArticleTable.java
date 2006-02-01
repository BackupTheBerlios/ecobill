package ecobill.module.base.ui.article;

import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdValueItem;
import ecobill.module.base.domain.Article;
import ecobill.module.base.domain.SystemUnit;
import ecobill.module.base.domain.SystemLocale;
import ecobill.module.base.domain.ArticleDescription;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.util.VectorUtils;
import ecobill.util.exception.LocalizerException;

import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Die <code>ArticleTable</code> beinhaltet die Tabelle zur Anzeige der Artikel. Desweiteren
 * kann das <code>TableColumnModel</code> serialisiert und in einer Datei abgelegt werden.
 * <p/>
 * User: rro
 * Date: 15.07.2005
 * Time: 17:49:23
 *
 * @author Roman R&auml;dle
 * @version $Id: ArticleTable.java,v 1.19 2006/02/01 12:00:47 raedler Exp $
 * @since EcoBill 1.0
 */
public class ArticleTable extends AbstractTablePanel {

    // Icons used in this article table user interface.
    private final Icon ICON_ARTICLE_UP = new ImageIcon("images/article_up.png");
    private final Icon ICON_ARTICLE_DOWN = new ImageIcon("images/article_down.png");
    private final Icon ICON_DELETE = new ImageIcon("images/delete.png");

    /**
     * Die <code>ArtikelUI</code> um den Artikel anzeigen zu k�nnen.
     */
    private ArticleUI articleUI;

    /**
     * Die id des <code>Article</code> der in der aktuell ausgew�hlt ist.
     */
    private Long articleId;

    /**
     * Erzeugt eine neues Article Table Panel f�r Artikel.
     */
    public ArticleTable(BaseService baseService) {
        this(null, baseService);
    }

    /**
     * Erzeugt eine neues Article Table Panel f�r Artikel.
     */
    public ArticleTable(ArticleUI articleUI, BaseService baseService) {
        super(baseService, true);

        this.articleUI = articleUI;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createPanelBorder()
     */
    protected Border createPanelBorder() {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.ARTICLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableColumnOrder()
     */
    protected Vector<I18NItem> createTableColumnOrder() {

        Vector<I18NItem> tableColumnOrder = new Vector<I18NItem>();

        tableColumnOrder.add(new I18NItem(Constants.ARTICLE_NR));
        tableColumnOrder.add(new I18NItem(Constants.UNIT));
        tableColumnOrder.add(new I18NItem(Constants.SINGLE_PRICE));
        tableColumnOrder.add(new I18NItem(Constants.DESCRIPTION));
        tableColumnOrder.add(new I18NItem(Constants.IN_STOCK));
        tableColumnOrder.add(new I18NItem(Constants.BUNDLE_UNIT));
        tableColumnOrder.add(new I18NItem(Constants.BUNDLE_CAPACITY));

        return tableColumnOrder;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection getDataCollection() {
        return getBaseService().loadAll(Article.class);
    }

    /**
     * @see AbstractTablePanel#createLineVector(Object)
     */
    protected Vector<Object> createLineVector(Object o) {

        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof Article) {

            Article article = (Article) o;

            // Setzen der Werte eines <code>Article</code> im Zeilen Datenvektor.
            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();

                if (Constants.ARTICLE_NR.equals(key)) {
                    line.add(new IdValueItem(article.getId(), article.getArticleNumber()));
                }
                else if (Constants.UNIT.equals(key)) {
                    line.add(article.getUnit());
                }
                else if (Constants.SINGLE_PRICE.equals(key)) {
                    line.add(article.getPrice());
                }
                else if (Constants.DESCRIPTION.equals(key)) {
                    line.add(article.getLocalizedDescription());
                }
                else if (Constants.IN_STOCK.equals(key)) {
                    line.add(article.getInStock());
                }
                else if (Constants.BUNDLE_UNIT.equals(key)) {
                    line.add(article.getBundleUnit());
                }
                else if (Constants.BUNDLE_CAPACITY.equals(key)) {
                    line.add(article.getBundleCapacity());
                }
            }
        }

        return line;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#reinitI18N()
     */
    public void reinitI18N() {
        super.reinitI18N();

        ((TitledBorder) getPanelBorder()).setTitle(WorkArea.getMessage(Constants.ARTICLE));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createKeyListeners()
     */
    protected KeyListener[] createKeyListeners() {

        KeyListener[] keyListeners = new KeyListener[1];

        keyListeners[0] = new KeyAdapter() {

            /**
             * @see KeyAdapter#keyPressed(java.awt.event.KeyEvent)
             */
            public void keyPressed(KeyEvent e) {

                // Hole den KeyCode der gedr�ckten Taste.
                int keyCode = e.getKeyCode();

                // Es soll nur diese Aktion ausgef�hrt werden wenn entweder Key UP oder Key DOWN
                // gedr�ckt wurde.
                if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {

                    // Hole die selektierte Reihe.
                    int row = getRealSelectedRow();

                    // Korrigiere die Key UP oder die Key DOWN Verschiebung.
                    if (keyCode == KeyEvent.VK_UP) {
                        --row;
                    }
                    else if (keyCode == KeyEvent.VK_DOWN) {
                        ++row;
                    }

                    // Fange die <code>ArrayIndexOutOfBoundsException</code> ab, die auftreten kann wenn
                    // durch die Korrektur eine Zeile zur�ckgegeben wird, die aber nicht in der Tabelle
                    // besteht.
                    try {
                        articleId = ((IdValueItem) getTableModel().getValueAt(getRealSelectedRow(), 0)).getId();
                    }
                    catch (ArrayIndexOutOfBoundsException aioobe) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(aioobe.getMessage(), aioobe);
                        }
                    }

                    // Zeige selektierten Artikel an.
                    if (articleUI != null) {
                        articleUI.showArticle(articleId);
                    }
                }
            }
        };

        return keyListeners;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createMouseListeners()
     */
    protected MouseListener[] createMouseListeners() {

        MouseListener[] mouseListeners = new MouseListener[1];

        // Ein <code>MouseAdapter</code> mit einer implementierten mouseDown Methode
        // um auf Klicks auf die Tabelle geeignet zu reagieren.
        mouseListeners[0] = new MouseAdapter() {

            /**
             * @see MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
             */
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {
                    articleId = ((IdValueItem) getTableModel().getValueAt(getRealSelectedRow(), 0)).getId();

                    // Zeige selektierten Artikel an.
                    if (articleUI != null) {
                        articleUI.showArticle(articleId);
                    }
                }
            }
        };

        return mouseListeners;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableModelListeners()
     */
    protected TableModelListener[] createTableModelListeners() {

        TableModelListener[] tableModelListeners = new TableModelListener[1];

        // Ein <code>TableModelListener</code> um die �nderungen der Tabellendaten in der
        // Datenbank zu persistieren.
        tableModelListeners[0] = new TableModelListener() {

            /**
             * @see TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
             */
            public void tableChanged(TableModelEvent e) {

                // �berpr�fe das <code>TableModelEvent</code> auf UPDATE.
                if (e.getType() == TableModelEvent.UPDATE) {

                    if (articleId != null) {

                        // Die Reihe und Spalte des zu ver�ndernden Wertes.
                        int row = e.getFirstRow();
                        int col = e.getColumn();

                        // Sicherheitscheck.
                        if (row > -1 && row < getTableModel().getRowCount()) {

                            // Lade betreffenden Artikel von der Datenbank.
                            Article article = (Article) getBaseService().load(Article.class, articleId);

                            // Ver�nderter Wert.
                            Object value = getTableModel().getValueAt(row, col);

                            // Der �bersetzte Name der Spalte um herauszufinden welcher Wert �berhaupt
                            // ge�ndert werden muss.
                            String columnName = getTableModel().getColumnName(col);

                            if (columnName.equals(WorkArea.getMessage(Constants.ARTICLE_NR))) {
                                article.setArticleNumber((String) value);
                            }
                            else if (columnName.equals(WorkArea.getMessage(Constants.UNIT))) {
                                article.setUnit((SystemUnit) value);
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

                                    SystemLocale systemLocale = getBaseService().getSystemLocaleByLocale(WorkArea.getLocale());

                                    articleDescription.setDescription((String) value);
                                    articleDescription.setSystemLocale(systemLocale);

                                    article.addArticleDescription(articleDescription);
                                }
                            }
                            else if (columnName.equals(WorkArea.getMessage(Constants.IN_STOCK))) {
                                article.setInStock(Double.valueOf((String) value));
                            }
                            else if (columnName.equals(WorkArea.getMessage(Constants.BUNDLE_UNIT))) {
                                article.setBundleUnit((SystemUnit) value);
                            }
                            else if (columnName.equals(WorkArea.getMessage(Constants.BUNDLE_CAPACITY))) {
                                article.setBundleCapacity(Double.valueOf((String) value));
                            }

                            getBaseService().saveOrUpdate(article);

                            if (LOG.isDebugEnabled()) {
                                LOG.debug("In der Spalte [" + col + "] und Zeile [" + row + "] wurde für den Artikel [id=\"" + articleId + "\"] der Wert auf \"" + getTableModel().getValueAt(row, col) + "\" geändert.");
                            }

                            if (articleUI != null) {
                                renewTableModel();
                                articleUI.showArticle(articleId);
                            }
                        }
                    }
                }
            }
        };

        return tableModelListeners;
    }

    /**
     * @see AbstractTablePanel#createPopupMenu(javax.swing.JPopupMenu)
     */
    protected JPopupMenu createPopupMenu(JPopupMenu popupMenu) {

        JMenuItem delete = new JMenuItem(WorkArea.getMessage(Constants.DELETE), ICON_DELETE);
        delete.addActionListener(new ArticleAction(articleUI).DELETE_ACTION);

        popupMenu.add(delete);

        return popupMenu;
    }

    /**
     * @see AbstractTablePanel#createEditoredColumnModelAfterUnpersist(javax.swing.table.TableColumnModel)
     */
    protected TableColumnModel createEditoredColumnModelAfterUnpersist(TableColumnModel columnModel) {

        int unit = columnModel.getColumnIndex(WorkArea.getMessage(Constants.UNIT));
        int bundleUnit = columnModel.getColumnIndex(WorkArea.getMessage(Constants.BUNDLE_UNIT));

        columnModel.getColumn(unit).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(VectorUtils.listToVector(getBaseService().getSystemUnitsByCategory(Constants.SYSTEM_UNIT_UNIT))))));
        columnModel.getColumn(bundleUnit).setCellEditor(new DefaultCellEditor(new JComboBox(new DefaultComboBoxModel(VectorUtils.listToVector(getBaseService().getSystemUnitsByCategory(Constants.SYSTEM_UNIT_BUNDLE_UNIT))))));

        return columnModel;
    }

    /**
     * Gibt den aktuell selektierten/markierte <code>Article</code> zur�ck.
     *
     * @return Der aktuell selektierten/markierte <code>Article</code>.
     */
    public Article getSelectedArticle() {
        return (Article) getBaseService().load(Article.class, articleId);
    }
}