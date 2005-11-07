package ecobill.module.base.ui.deliveryorder;

import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.ui.businesspartner.BusinessPartnerUI;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.ReduplicatedArticle;
import ecobill.core.system.Internationalization;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdKeyItem;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.Collection;
import java.util.Collections;

// @todo document me!

/**
 * DeliveryOrderTable.
 * <p/>
 * User: rro
 * Date: 05.10.2005
 * Time: 16:57:16
 *
 * @author Roman R&auml;dle
 * @version $Id: DeliveryOrderTable.java,v 1.5 2005/11/07 21:49:30 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderTable extends AbstractTablePanel {

    /**
     * Die id eines Lieferscheines.
     */
    private Long deliveryOrderId;

    /**
     * Creates new form BusinessPartnerTable
     */
    public DeliveryOrderTable(BaseService baseService) {
        super(baseService);
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

    private Collection<ReduplicatedArticle> dataCollection;

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection<ReduplicatedArticle> getDataCollection() {

        if (deliveryOrderId != null) {
            DeliveryOrder deliveryOrder = (DeliveryOrder) getBaseService().load(DeliveryOrder.class, deliveryOrderId);

            deliveryOrderId = null;

            return deliveryOrder.getArticles();
        }
        else if (dataCollection != null) {

            Collection<ReduplicatedArticle> returnCollection = dataCollection;

            dataCollection = null;

            return returnCollection;
        }

        return Collections.EMPTY_SET;
    }

    public void setDataCollection(Collection<ReduplicatedArticle> dataCollection) {

        this.dataCollection = dataCollection;
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
                    line.add(new IdKeyItem(article.getId(), article.getArticleNumber()));
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

            line.add(article);
        }

        return line;
    }

    /**
     * @see AbstractTablePanel#createPopupMenu(javax.swing.JPopupMenu)
     */
    protected JPopupMenu createPopupMenu(JPopupMenu popupMenu) {

        JMenuItem delete = new JMenuItem(WorkArea.getMessage(Constants.DELETE), new ImageIcon("./images/delete.png"));
        delete.addActionListener(new ActionListener() {

            /**
             * @see ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            public void actionPerformed(ActionEvent e) {

                // Löscht die markierte Zeile.
                getTableModel().removeRow(getTable().getSelectedRow());
            }
        });
        
        popupMenu.add(delete);

        return popupMenu;
    }
}
