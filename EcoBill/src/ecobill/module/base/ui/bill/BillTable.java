package ecobill.module.base.ui.bill;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.ui.component.AbstractTablePanel;
import ecobill.module.base.domain.Bill;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.util.I18NItem;
import ecobill.core.util.IdKeyItem;
import ecobill.core.util.IdValueItem;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Die <code>BillTable</code> nimmt die Rechnungsdaten auf
 * <p/>
 * User: sega
 * Date: 20.10.2005
 * Time: 17:49:23
 *
 * @author Sebastian Gath
 * @version $Id: BillTable.java,v 1.5 2006/01/30 23:43:14 raedler Exp $
 * @since EcoBill 1.0
 */
public class BillTable extends AbstractTablePanel {

    /**
     * Die id eines Geschäftspartners.
     */
    private Long businessPartnerId;

    /**
     * Gibt die Geschäftspartner Id zur�ck.
     *
     * @return Die Geschäftspartner Id.
     */
    public Long getBusinessPartnerId() {
        return businessPartnerId;
    }

    /**
     * Setzt die Geschäftspartner Id.
     *
     * @param businessPartnerId Die Geschäftspartner Id.
     */
    public void setBusinessPartnerId(Long businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }

    /**
     * Creates new form BusinessPartnerTable
     */
    public BillTable(BaseService baseService) {
        super(baseService, true);
    }

    /**
     * Creates new form BusinessPartnerTable
     */
    public BillTable(Long businessPartnerId, BaseService baseService) {
        super(baseService, true);

        this.businessPartnerId = businessPartnerId;
    }

    /**
     * AbstractTablePanel#createPanelBorder
     */
    protected Border createPanelBorder() {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.BILL), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", Font.PLAIN, 11), new Color(0, 0, 0));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createTableColumnOrder()
     */
    protected Vector<I18NItem> createTableColumnOrder() {

        Vector<I18NItem> tableColumnOrder = new Vector<I18NItem>();

        tableColumnOrder.add(new I18NItem(Constants.BILL_NUMBER));
        tableColumnOrder.add(new I18NItem(Constants.BILL_DATE));
        tableColumnOrder.add(new I18NItem(Constants.DELIVERY_ORDER_NUMBER));

        return tableColumnOrder;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection<Bill> getDataCollection() {

        if (businessPartnerId != null) {

            BusinessPartner businessPartner = (BusinessPartner) getBaseService().load(BusinessPartner.class, businessPartnerId);

            return businessPartner.getBills();
        }

        return Collections.EMPTY_SET;
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createLineVector(Object)
     */
    protected Vector createLineVector(Object o) {

        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof Bill) {
            Bill bill = (Bill) o;

            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();

                if (Constants.BILL_NUMBER.equals(key)) {
                    line.add(new IdValueItem(bill.getId(), bill.getBillNumber(), bill));
                }
                else if (Constants.BILL_DATE.equals(key)) {
                    line.add(bill.getBillDate());
                }
                else if (Constants.DELIVERY_ORDER_NUMBER.equals(key)) {
                    Set deliveryOrders = bill.getDeliveryOrders();
                    Set deliveryOrderNumbers = new HashSet();
                    for (Object object : deliveryOrders) {
                        deliveryOrderNumbers.add(((DeliveryOrder) object).getDeliveryOrderNumber());
                    }
                    line.add(deliveryOrderNumbers);
                }
            }
        }

        return line;
    }

    /**
     * Gibt den aktuell selektierten/markierte <code>Bill</code> zur�ck.
     *
     * @return Der aktuell selektierten/markierte <code>Bill</code>.
     */
    public Bill getSelectedBill() {

        int selectedRow = getTable().getSelectedRow();

        IdValueItem idValueItem = (IdValueItem) getTable().getValueAt(selectedRow, 0);

        return (Bill) idValueItem.getOriginalValue();
    }
}
