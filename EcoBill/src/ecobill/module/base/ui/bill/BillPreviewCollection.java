package ecobill.module.base.ui.bill;

import java.util.Date;


/**
 * Die <code>BillPreviewCollection</code> enthält alle Daten, zur Vorschau der Lieferscheindaten  zur Rechnungsvor
 * schau auf dem Rechnungsübersichtstab
 * <p/>
 * User: sega
 * Date: 10.10.2005
 * Time: 17:49:23
 *
 * @author Sebastian Gath
 * @version $Id: BillPreviewCollection.java,v 1.3 2006/01/30 23:43:14 raedler Exp $
 * @since EcoBill 1.0
 */
public class BillPreviewCollection {

    /**
     * Lieferscheinnummer
     */
    public String deliveryOrderNumber;

    /**
     * Lieferscheindatum
     */
    public Date deliveryOrderDate;

    /**
     * Lieferscheinbetrag
     */
    public double sum;

    public BillPreviewCollection(String dON, Date date, double sum) {
        this.deliveryOrderNumber = dON;
        this.deliveryOrderDate = date;
        this.sum = sum;

    }

    public String getDeliveryOrderNumber() {
        return deliveryOrderNumber;
    }

    /**
     * Setzt die Lieferscheinnummer
     *
     * @param deliveryOrderNumber
     */
    public void setDeliveryOrderNumber(String deliveryOrderNumber) {
        this.deliveryOrderNumber = deliveryOrderNumber;
    }

    /**
     * Gibt das Rechnungsdatum zurück
     *
     * @return Date deliveryOrderDate
     */
    public Date getDeliveryOrderDate() {
        return deliveryOrderDate;
    }

    /**
     * Setzt das LieferscheinDatum
     *
     * @param deliveryOrderDate
     */
    public void setDeliveryOrderDate(Date deliveryOrderDate) {
        this.deliveryOrderDate = deliveryOrderDate;
    }


    /**
     * Gibt die Lieferscheinsumme zurück
     *
     * @return
     */
    public double getSum() {
        return sum;
    }

    /**
     * Setzt die Lieferscheinsumme
     *
     * @param sum
     */
    public void setSum(double sum) {
        this.sum = sum;
    }
}
