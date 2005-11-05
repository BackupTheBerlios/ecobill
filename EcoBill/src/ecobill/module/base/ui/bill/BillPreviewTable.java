package ecobill.module.base.ui.bill;

import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.ReduplicatedArticle;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdKeyItem;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.Collection;
import java.util.Collections;

import org.hibernate.mapping.Set;

/**
 * Die <code>BillPreviewTable</code> enthält alle Daten, zur Vorschau der Lieferscheindaten  zur Rechnungsvor
 * schau auf dem Rechnungsübersichtstab
 * <p/>
 * User: sega
 * Date: 10.10.2005
 * Time: 17:49:23
 *
 * @author Sebastian Gath
 * @version $Id: BillPreviewTable.java,v 1.2 2005/11/05 19:34:42 gath Exp $
 * @since EcoBill 1.0
 */
public class BillPreviewTable extends AbstractTablePanel {

    /**
     * Creates new form BusinessPartnerTable
     */
    public BillPreviewTable(BaseService baseService) {
        super(baseService);

    }

    /**
     * AbstractTablePanel#createPanelBorder
     */
    protected Border createPanelBorder() {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.BILL_PREVIEW), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 0, 0));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableColumnOrder()
     */
    protected Vector<I18NItem> createTableColumnOrder() {

        Vector<I18NItem> tableColumnOrder = new Vector<I18NItem>();

        tableColumnOrder.add(new I18NItem(Constants.DELIVERY_ORDER_NUMBER));
        tableColumnOrder.add(new I18NItem(Constants.DELIVERY_ORDER_DATE));
        tableColumnOrder.add(new I18NItem(Constants.DELIVERY_ORDER_AMOUNT));

        return tableColumnOrder;
    }

    private Collection<BillPreviewCollection> dataCollection;

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection<BillPreviewCollection> getDataCollection() {
      /*  double sum=0;
        if (deliveryOrderId != null) {
            DeliveryOrder deliveryOrder = (DeliveryOrder) getBaseService().load(DeliveryOrder.class, deliveryOrderId);

            deliveryOrderId = null;

            java.util.Set<ReduplicatedArticle> redArticles = deliveryOrder.getArticles();
            while(redArticles.iterator().hasNext()) {
                ReduplicatedArticle ra = redArticles.iterator().next();
                sum = sum + ra.getPrice() * ra.getQuantity();
            }
            Collection<BillPreviewCollection> tmp=null;
            tmp.add(new BillPreviewCollection(deliveryOrder.getDeliveryOrderNumber(), deliveryOrder.getDeliveryOrderDate(), sum));
            return tmp;

        }
        */

        if (dataCollection != null) {
            return dataCollection;
        }

        return Collections.EMPTY_SET;

    }

    /**
     * Setzt die DataCollection
     *
     * @param dataCollection
     */
    public void setDataCollection(Collection<BillPreviewCollection> dataCollection) {

        this.dataCollection = dataCollection;
    }

    /**
     * @see AbstractTablePanel#createLineVector(Object)
     */
    protected Vector createLineVector(Object o) {

        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof BillPreviewCollection) {
            BillPreviewCollection bpc = (BillPreviewCollection) o;

            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();

                if (Constants.DELIVERY_ORDER_NUMBER.equals(key)) {
                    line.add(bpc.getDeliveryOrderNumber());
                }
                else if (Constants.DELIVERY_ORDER_DATE.equals(key)) {
                    line.add(bpc.getDeliveryOrderDate());
                }
                else if (Constants.DELIVERY_ORDER_AMOUNT.equals(key)) {
                    line.add(bpc.getSum());
                }
            }

            line.add(bpc);
        }

        return line;
    }

}
