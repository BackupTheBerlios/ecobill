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
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 22.10.2005
 * Time: 20:24:51
 * To change this template use File | Settings | File Templates.
 */
public class BillTable extends AbstractTablePanel {


    /**
     * Die id eines Lieferscheines.
     */
    private Long businessPartnerId;

    /**
     * Creates new form BusinessPartnerTable
     */
    public BillTable(Long businessPartnerId, BaseService baseService) {
        super(baseService);

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

    private Collection<Bill> dataCollection;

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection<Bill> getDataCollection() {

        if (businessPartnerId != null) {
            //DeliveryOrder deliveryOrder = (DeliveryOrder) getBaseService().load(DeliveryOrder.class, businessPartnerId);
            System.out.println(getBaseService());
            BusinessPartner businessPartner = (BusinessPartner) getBaseService().load(BusinessPartner.class, businessPartnerId);

            businessPartnerId = null;

            return businessPartner.getBills();
        }

        if (dataCollection != null) {
            return dataCollection;
        }

        return Collections.EMPTY_SET;

    }

    public void setDataCollection(Collection<Bill> dataCollection) {

        this.dataCollection = dataCollection;
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
                    line.add(new IdValueItem(bill.getId(), bill.getBillNumber().toString()));
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

            line.add(bill);
        }

        return line;
    }

    public void updateDataCollectionFromDB(long id) {
        businessPartnerId = id;
        System.out.println("bpid:" + id);
        Collection<Bill> c;
        c = getDataCollection();
        System.out.println("sizze:" + c.size());
        setDataCollection(c);
    }

}
