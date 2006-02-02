package ecobill.module.base.ui.deliveryorder;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.Bill;
import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdValueItem;
import ecobill.core.system.Constants;
import ecobill.core.system.WorkArea;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 11.10.2005
 * Time: 12:35:44
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryOrderTableWithCB extends AbstractTablePanel {

    /**
     * Die id eines Gesch�ftspartners.
     */
    private Long businessPartnerId;

    /**
     * Gibt die Gesch�ftspartner Id zur�ck.
     *
     * @return Die Gesch�ftspartner Id.
     */
    public Long getBusinessPartnerId() {
        return businessPartnerId;
    }

    /**
     * Setzt die Gesch�ftspartner Id.
     *
     * @param businessPartnerId Die Gesch�ftspartner Id.
     */
    public void setBusinessPartnerId(Long businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }

    /**
     * Creates new form BusinessPartnerTable
     */
    public DeliveryOrderTableWithCB(BaseService baseService) {
        super(baseService, true);
    }

    /**
     * Creates new form BusinessPartnerTable
     */
    public DeliveryOrderTableWithCB(Long businessPartnerId, BaseService baseService) {

        super(baseService, false);

        this.businessPartnerId = businessPartnerId;

        renewTableModel();
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
        tableColumnOrder.add(new I18NItem(Constants.CHECKBOX_NEEDED));
        tableColumnOrder.add(new I18NItem(Constants.DELIVERY_ORDER_NUMBER));
        tableColumnOrder.add(new I18NItem(Constants.DELIVERY_ORDER_DATE));
        tableColumnOrder.add(new I18NItem(Constants.CHARACTERISATION_TYP));
        tableColumnOrder.add(new I18NItem(Constants.PREFIX_FREE_TEXT));
        tableColumnOrder.add(new I18NItem(Constants.SUFFIX_FREE_TEXT));

        return tableColumnOrder;
    }

    private Bill bill;

    public void setBill(Bill bill) {
        this.bill = bill;
        renewTableModel();
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection<DeliveryOrder> getDataCollection() {

        if (businessPartnerId != null) {

            BusinessPartner businessPartner = (BusinessPartner) getBaseService().load(BusinessPartner.class, businessPartnerId);

            Set<DeliveryOrder> openDeliveryOrders = businessPartner.getOpenDeliveryOrders();

            // Whether a new created <code>Bill</code> exists all temporarily added
            // <code>DeliveryOrder</code> will be removed from the selection. So you
            // can't add the same <code>DeliveryOrder</code> twice.
            if (bill != null) {

                Set<DeliveryOrder> removableDeliveryOrders = new HashSet<DeliveryOrder>();

                for (DeliveryOrder deliveryOrder : bill.getDeliveryOrders()) {
                    for (DeliveryOrder openDeliveryOrder : openDeliveryOrders) {
                        if (deliveryOrder.getDeliveryOrderNumber().equals(openDeliveryOrder.getDeliveryOrderNumber())) {
                            removableDeliveryOrders.add(openDeliveryOrder);
                        }
                    }
                }

                openDeliveryOrders.removeAll(removableDeliveryOrders);
            }

            return openDeliveryOrders;
        }

        return Collections.EMPTY_SET;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createLineVector(Object)
     */
    protected Vector createLineVector(Object o) {

        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof DeliveryOrder) {

            DeliveryOrder deliveryOrder = (DeliveryOrder) o;

            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();
                if (Constants.CHECKBOX_NEEDED.equals(key)) {
                    line.add(deliveryOrder.isPreparedBill());
                }
                else if (Constants.DELIVERY_ORDER_NUMBER.equals(key)) {
                    line.add(new IdValueItem(deliveryOrder.getId(), deliveryOrder.getDeliveryOrderNumber()));
                }
                else if (Constants.DELIVERY_ORDER_DATE.equals(key)) {
                    line.add(deliveryOrder.getDeliveryOrderDate());
                }
                else if (Constants.CHARACTERISATION_TYP.equals(key)) {
                    line.add(deliveryOrder.getCharacterisationType());
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

    public Set<DeliveryOrder> getSelectedDeliveryOrders() {

        Set<DeliveryOrder> deliveryOrders = new HashSet<DeliveryOrder>();

        // Über alle Zeilen der Lieferscheintabelle gehen
        for (int i = 0; i < getTable().getRowCount(); i++) {

            // Checkbox markiert ??
            if ((getTable().getValueAt(i, 0) instanceof Boolean) && (Boolean) getTable().getValueAt(i, 0)) {

                // Das DeliveryOrder Object zu der Zeile aus der orderTable laden
                DeliveryOrder deliveryOrder = (DeliveryOrder) getBaseService().load(DeliveryOrder.class, ((IdValueItem) getTable().getValueAt(i, 1)).getId());

                deliveryOrders.add(deliveryOrder);
            }
        }

        return deliveryOrders;
    }
}
