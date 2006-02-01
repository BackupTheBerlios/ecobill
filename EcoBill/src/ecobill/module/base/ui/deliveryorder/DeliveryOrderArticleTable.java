package ecobill.module.base.ui.deliveryorder;

import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.ReduplicatedArticle;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdValueItem;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import java.util.Collection;
import java.util.LinkedHashSet;

// @todo document me!

/**
 * DeliveryOrderArticleTable.
 * <p/>
 * User: rro
 * Date: 05.10.2005
 * Time: 16:57:16
 *
 * @author Roman R&auml;dle
 * @version $Id: DeliveryOrderArticleTable.java,v 1.2 2006/02/01 12:00:48 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderArticleTable extends AbstractTablePanel {

    // Icons used in this delivery order user interface.
    private final Icon ICON_ARTICLE_UP = new ImageIcon("images/deliveryorder/article_up.png");
    private final Icon ICON_ARTICLE_DOWN = new ImageIcon("images/deliveryorder/article_down.png");
    private final Icon ICON_DELETE = new ImageIcon("images/deliveryorder/delete.png");

    /**
     * Die id eines Lieferscheines.
     */
    private DeliveryOrder deliveryOrder;

    /**
     * Creates new form BusinessPartnerTable
     */
    public DeliveryOrderArticleTable(BaseService baseService) {
        super(baseService, true);
    }

    /**
     * AbstractTablePanel#createPanelBorder
     */
    protected Border createPanelBorder() {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.DELIVERY_ORDER), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 0, 0));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableColumnOrder()
     */
    protected Vector<I18NItem> createTableColumnOrder() {

        Vector<I18NItem> tableColumnOrder = new Vector<I18NItem>();
        tableColumnOrder.add(new I18NItem(Constants.ARTICLE_NR));
        tableColumnOrder.add(new I18NItem(Constants.LABELLING));
        tableColumnOrder.add(new I18NItem(Constants.QUANTITY));
        tableColumnOrder.add(new I18NItem(Constants.UNIT));
        tableColumnOrder.add(new I18NItem(Constants.PRICE));
        tableColumnOrder.add(new I18NItem(Constants.ALL_ROUND_PRICE));

        return tableColumnOrder;
    }

    private Collection<ReduplicatedArticle> dataCollection = new LinkedHashSet<ReduplicatedArticle>();

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection<ReduplicatedArticle> getDataCollection() {

        if (deliveryOrder == null) {
            return new LinkedHashSet<ReduplicatedArticle>();
        }

        return deliveryOrder.getArticles();
    }

    public void addReduplicatedArticle(ReduplicatedArticle article) {
        deliveryOrder.addArticle(article);
        article.setDeliveryOrder(deliveryOrder);
        renewTableModel();
    }

    public void clearDataCollection() {
        this.dataCollection.clear();
        renewTableModel();
    }

    /**
     * @see AbstractTablePanel#createLineVector(Object)
     */
    protected Vector createLineVector(Object o) {

        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof ReduplicatedArticle) {

            ReduplicatedArticle article = (ReduplicatedArticle) o;

            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();
                if (Constants.ARTICLE_NR.equals(key)) {
                    line.add(new IdValueItem(article.getId(), article.getArticleNumber(), article));
                }
                else if (Constants.LABELLING.equals(key)) {
                    line.add(article.getDescription());
                }
                else if (Constants.QUANTITY.equals(key)) {
                    line.add(article.getQuantity());
                }
                else if (Constants.UNIT.equals(key)) {
                    line.add(article.getUnit());
                }
                else if (Constants.PRICE.equals(key)) {
                    line.add(article.getPrice());
                }
                else if (Constants.ALL_ROUND_PRICE.equals(key)) {

                    double allRoundPrice = article.getQuantity() * article.getPrice();

                    line.add(allRoundPrice);
                }
            }
        }

        return line;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableModelListeners()
     */
    protected TableModelListener[] createTableModelListeners() {

        TableModelListener listener = new TableModelListener() {

            public void tableChanged(TableModelEvent e) {

                if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.UPDATE) {

                    int row = e.getFirstRow();

                    if (e.getColumn() == 2 || e.getColumn() == 4) {

                        Double quantity = (Double) getTableModel().getValueAt(row, 2);
                        Double price = (Double) getTableModel().getValueAt(row, 4);

                        Double allRoundPrice = (double) quantity * price;

                        getTableModel().setValueAt(allRoundPrice, e.getFirstRow(), 5);

                        getTable().updateUI();
                    }

                    if (e.getType() == TableModelEvent.UPDATE) {

                        try {
                            IdValueItem idValueItem = (IdValueItem) getTableModel().getValueAt(row, 0);

                            ReduplicatedArticle article = (ReduplicatedArticle) idValueItem.getOriginalValue();

                            Object o = getTableModel().getValueAt(row, e.getColumn());

                            switch (e.getColumn()) {

                                case 0:
                                    article.setArticleNumber((String) o);
                                    break;
                                case 1:
                                    article.setDescription((String) o);
                                    break;
                                case 2:
                                    article.setQuantity((Double) o);
                                    break;
                                case 3:
                                    article.setUnit((String) o);
                                    break;
                                case 4:
                                    article.setPrice((Double) o);
                                    break;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException aioobe) {
                            LOG.error(aioobe.getMessage(), aioobe);
                        }
                    }
                }
            }
        };

        return new TableModelListener[]{listener};
    }

    /**
     * @see AbstractTablePanel#createPopupMenu(javax.swing.JPopupMenu)
     */
    protected JPopupMenu createPopupMenu(JPopupMenu popupMenu) {

        JMenuItem upArticle = new JMenuItem(WorkArea.getMessage(Constants.UP), ICON_ARTICLE_UP);
        upArticle.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                int selectedRow = getTable().getSelectedRow();

                if (selectedRow > 0) {

                    Vector dataVector = getTableModel().getDataVector();

                    Object o1 = dataVector.get(selectedRow);
                    Object o2 = dataVector.get(selectedRow - 1);

                    Vector v1 = (Vector) o1;
                    IdValueItem idValueItem1 = (IdValueItem) v1.get(0);
                    ReduplicatedArticle article1 = (ReduplicatedArticle) idValueItem1.getOriginalValue();

                    Integer oldOrderPosition = article1.getOrderPosition();

                    Vector v2 = (Vector) o2;
                    IdValueItem idValueItem2 = (IdValueItem) v2.get(0);
                    ReduplicatedArticle article2 = (ReduplicatedArticle) idValueItem2.getOriginalValue();

                    article1.setOrderPosition(article2.getOrderPosition());
                    article2.setOrderPosition(oldOrderPosition);

                    dataVector.remove(selectedRow);
                    dataVector.add(selectedRow - 1, o1);

                    getTable().setRowSelectionInterval(selectedRow - 1, selectedRow - 1);

                    getTable().updateUI();
                }
            }
        });

        JMenuItem downArticle = new JMenuItem(WorkArea.getMessage(Constants.DOWN), ICON_ARTICLE_DOWN);
        downArticle.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                int selectedRow = getTable().getSelectedRow();

                if (selectedRow < getTable().getRowCount() - 1) {

                    Vector dataVector = getTableModel().getDataVector();

                    Object o1 = dataVector.get(selectedRow);
                    Object o2 = dataVector.get(selectedRow + 1);

                    Vector v1 = (Vector) o1;
                    IdValueItem idValueItem1 = (IdValueItem) v1.get(0);
                    ReduplicatedArticle article1 = (ReduplicatedArticle) idValueItem1.getOriginalValue();

                    Integer oldOrderPosition = article1.getOrderPosition();

                    Vector v2 = (Vector) o2;
                    IdValueItem idValueItem2 = (IdValueItem) v2.get(0);
                    ReduplicatedArticle article2 = (ReduplicatedArticle) idValueItem2.getOriginalValue();

                    article1.setOrderPosition(article2.getOrderPosition());
                    article2.setOrderPosition(oldOrderPosition);

                    dataVector.remove(selectedRow);
                    dataVector.add(selectedRow + 1, o1);

                    getTable().setRowSelectionInterval(selectedRow + 1, selectedRow + 1);

                    getTable().updateUI();
                }
            }
        });

        JMenuItem delete = new JMenuItem(WorkArea.getMessage(Constants.DELETE), ICON_DELETE);
        delete.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                int selectedRow = getTable().getSelectedRow();

                IdValueItem idValueItem = (IdValueItem) getTableModel().getValueAt(selectedRow, 0);

                // L�scht die markierte Zeile.
                getTableModel().removeRow(selectedRow);

                getDataCollection().remove(idValueItem.getOriginalValue());
            }
        });

        popupMenu.add(upArticle);
        popupMenu.add(downArticle);
        popupMenu.add(delete);

        return popupMenu;
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
        renewTableModel();
    }
}
