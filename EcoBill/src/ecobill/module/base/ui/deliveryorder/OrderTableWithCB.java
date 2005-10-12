package ecobill.module.base.ui.deliveryorder;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.BusinessPartner;
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
import java.util.Vector;
import java.util.Collection;
import java.util.Collections;
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
public class OrderTableWithCB extends AbstractTablePanel {

    /**
     * Die id eines Lieferscheines.
     */
    private Long businessPartnerId;

    /**
     * Creates new form BusinessPartnerTable
     */
    public OrderTableWithCB(Long businessPartnerId, BaseService baseService) {

        super(baseService);

        this.businessPartnerId = businessPartnerId;
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

    private Collection<DeliveryOrder> dataCollection;
    //private Collection<Object> dataCollection;

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#getDataCollection()
     */
    protected Collection<DeliveryOrder> getDataCollection() {

        if (businessPartnerId != null) {
            //DeliveryOrder deliveryOrder = (DeliveryOrder) getBaseService().load(DeliveryOrder.class, businessPartnerId);
            BusinessPartner businessPartner = (BusinessPartner) getBaseService().load(BusinessPartner.class, businessPartnerId);

            businessPartnerId = null;

            return businessPartner.getOpenDeliveryOrders();
        }

        if (dataCollection != null) {
            return dataCollection;
        }

        return Collections.EMPTY_SET;

    }

    public void setDataCollection(Collection<DeliveryOrder> dataCollection) {

        this.dataCollection = dataCollection;
    }

    public void updateDataCollectionFromDB(long id) {
        businessPartnerId = id;
        Collection<DeliveryOrder> c;
        c = getDataCollection();
        setDataCollection(c);
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#createLineVector(Object)
     */
    protected Vector createLineVector(Object o) {
        System.out.println("und jetzt die Zeilen gefüllt");
        // Ein neuer <code>Vector</code> stellt eine Zeile der Tabelle dar.
        Vector<Object> line = new Vector<Object>();

        if (o instanceof DeliveryOrder) {

            DeliveryOrder deliveryOrder = (DeliveryOrder) o;

            for (I18NItem order : getTableColumnOrder()) {

                String key = order.getKey();
                if (Constants.CHECKBOX_NEEDED.equals(key)) {
                    line.add(new Object());
                }
                if (Constants.DELIVERY_ORDER_NUMBER.equals(key)) {
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
            System.out.println("Was gefunden");
        }

        return line;
    }


    /**
     * @see ecobill.module.base.ui.component.AbstractTablePanel#initColumnModelAfterUnpersist(javax.swing.table.TableColumnModel)
     */
    protected TableColumnModel createEditoredColumnModelAfterUnpersist(TableColumnModel columnModel) {
        System.out.println("aufgerufen wurde ich ");

        int checkBox = columnModel.getColumnIndex(WorkArea.getMessage(Constants.CHECKBOX_NEEDED));

        columnModel.getColumn(checkBox).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        columnModel.getColumn(checkBox).setCellRenderer(new Renderer());

        return columnModel;
    }

    public class Renderer extends JCheckBox implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            this.setBackground(Color.WHITE);

            if (value instanceof Boolean) {

                Boolean booleanValue = (Boolean) value;

                this.setSelected(booleanValue);
            }


            return this;
        }
    }
}
