package ecobill.module.base.ui.deliveryorder;

import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.ReduplicatedArticle;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.Article;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;
import ecobill.util.VectorUtils;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Vector;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Die <code>BillPreviewTable</code> enthält alle Daten, zur Vorschau der Lieferscheindaten  zur Rechnungsvor
 * schau auf dem Rechnungsübersichtstab
 * <p/>
 * User: sega
 * Date: 09.10.2005
 * Time: 17:49:23
 *
 * @author Sebastian Gath
 * @version $Id: OrderTable.java,v 1.6 2005/12/11 17:16:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class OrderTable extends AbstractTablePanel {

    /**
     * Die id eines Lieferscheines.
     */
    private Long businessPartnerId;

    /**
     * Gibt an ob nur die Lieferscheine angezeigt werden sollen, zu denen noch keine
     * Rechnung besteht.
     */
    private boolean notPreparedBill;

    /**
     * Creates new form BusinessPartnerTable
     */
    public OrderTable(BaseService baseService) {
        this(baseService, false);
    }

    public OrderTable(BaseService baseService, boolean notPreparedBill) {
        super(baseService);

        this.notPreparedBill = notPreparedBill;
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
        tableColumnOrder.add(new I18NItem(Constants.DELIVERY_ORDER_NUMBER));
        tableColumnOrder.add(new I18NItem(Constants.DELIVERY_ORDER_DATE));
        tableColumnOrder.add(new I18NItem(Constants.CHARACTERISATION_TYP));
        tableColumnOrder.add(new I18NItem(Constants.PREFIX_FREE_TEXT));
        tableColumnOrder.add(new I18NItem(Constants.SUFFIX_FREE_TEXT));

        return tableColumnOrder;
    }

    private Collection<DeliveryOrder> dataCollection;

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection<DeliveryOrder> getDataCollection() {

        if (businessPartnerId != null) {

            BusinessPartner businessPartner = (BusinessPartner) getBaseService().load(BusinessPartner.class, businessPartnerId);

            if (notPreparedBill) {
                return businessPartner.getOpenDeliveryOrders();
            }

            return businessPartner.getDeliveryOrders();
        }

        return Collections.EMPTY_SET;
    }

    public void setDataCollection(Collection<DeliveryOrder> dataCollection) {
        this.dataCollection = dataCollection;
    }

    public void setBusinessPartnerId(Long businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
        renewTableModel();
    }

    /**
     * @see AbstractTablePanel#createLineVector(Object)
     */
    protected Vector createLineVector(Object o) {

        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof DeliveryOrder) {

            DeliveryOrder deliveryOrder = (DeliveryOrder) o;

            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();
                if (Constants.DELIVERY_ORDER_NUMBER.equals(key)) {
                    line.add(new IdValueItem(deliveryOrder.getId(), deliveryOrder.getDeliveryOrderNumber(), deliveryOrder));
                }
                else if (Constants.DELIVERY_ORDER_DATE.equals(key)) {
                    line.add(deliveryOrder.getDeliveryOrderDate());
                }
                else if (Constants.CHARACTERISATION_TYP.equals(key)) {
                    line.add(WorkArea.getMessage(deliveryOrder.getCharacterisationType()));
                }
                else if (Constants.PREFIX_FREE_TEXT.equals(key)) {
                    line.add(deliveryOrder.getPrefixFreetext());
                }
                else if (Constants.SUFFIX_FREE_TEXT.equals(key)) {
                    line.add(deliveryOrder.getSuffixFreetext());
                }
            }

            line.add(deliveryOrder);
        }

        return line;
    }


    /**
     * @see AbstractTablePanel#initColumnModelAfterUnpersist(javax.swing.table.TableColumnModel)
     */
/*    protected TableColumnModel createEditoredColumnModelAfterUnpersist(TableColumnModel columnModel) {
          System.out.println("aufgerufen wurde ich ");
          int checkBox = columnModel.getColumnIndex(WorkArea.getMessage(Constants.CHECKBOX_NEEDED));

          columnModel.getColumn(checkBox).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        return columnModel;
    }
  */

    /**
     * Gibt den aktuell selektierten/markierte <code>DeliveryOrder</code> zurück.
     *
     * @return Der aktuell selektierten/markierte <code>DeliveryOrder</code>.
     */
    public DeliveryOrder getSelectedDeliveryOrder() {

        int selectedRow = getTable().getSelectedRow();

        IdValueItem idValueItem = (IdValueItem) getTable().getValueAt(selectedRow, 0);

        return (DeliveryOrder) idValueItem.getOriginalValue();
    }
}
