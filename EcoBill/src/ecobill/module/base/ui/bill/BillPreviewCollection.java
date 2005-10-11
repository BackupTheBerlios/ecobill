package ecobill.module.base.ui.bill;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: basti
 * Date: 10.10.2005
 * Time: 21:36:49
 * To change this template use File | Settings | File Templates.
 */
public class BillPreviewCollection {
    public String deliveryOrderNumber;
    public Date deliveryOrderDate;
    public double sum;

    public BillPreviewCollection(String dON, Date date, double sum) {
        this.deliveryOrderNumber = dON;
        this.deliveryOrderDate = date;
        this.sum = sum;
    }
    public String getDeliveryOrderNumber() {
        return deliveryOrderNumber;
    }

    public void setDeliveryOrderNumber(String deliveryOrderNumber) {
        this.deliveryOrderNumber = deliveryOrderNumber;
    }

    public Date getDeliveryOrderDate() {
        return deliveryOrderDate;
    }

    public void setDeliveryOrderDate(Date deliveryOrderDate) {
        this.deliveryOrderDate = deliveryOrderDate;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
