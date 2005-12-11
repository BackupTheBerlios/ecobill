package ecobill.module.base.ui.bill;

import ecobill.module.base.ui.component.AbstractJasperPrintPanel;
import ecobill.module.base.ui.component.AbstractJasperViewerDialog;
import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.Bill;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.Person;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;
import ecobill.core.ui.MainFrame;

import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.LinkedList;

// TODO: document me!!!

/**
 * DeliveryOrderViewerDialog.
 * <p/>
 * User: sega
 * Date: 18.08.2005
 * Time: 16:45:41
 *
 * @author Sebastian Gath
 * @version $Id: BillViewerDialog.java,v 1.1 2005/12/11 17:16:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class BillViewerDialog extends AbstractJasperViewerDialog {

    /**
     * TODO: document me!!!
     *
     * @param mainFrame
     * @param baseService
     */
    public BillViewerDialog(MainFrame mainFrame, boolean modal, BaseService baseService, Long id) {
        super(mainFrame, modal, baseService, id);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        ((TitledBorder) getBorder()).setTitle(WorkArea.getMessage(Constants.BILL));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractJasperPrintPanel#createPanelBorder()
     */
    protected Border createPanelBorder() {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.BILL), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    }

    /**
     * @see AbstractJasperPrintPanel#jasper(Long)
     */
    protected void jasper(Long id) throws Exception {

        getMainFrame().setProgressPercentage(10);

        Bill bill = (Bill) getBaseService().load(Bill.class, id);

        BusinessPartner bp = bill.getBusinessPartner();
        Person person = bp.getPerson();
        ecobill.module.base.domain.Address address = bp.getAddress();

        java.util.List reduplicatedArticles = new LinkedList();

        Set<DeliveryOrder> deliveryOrders = bill.getDeliveryOrders();

        // nimmt die Lieferscheinnummern auf
        String deliveryOrderNumbers = "";

        int i = 0;
        for (DeliveryOrder deliveryOrder : deliveryOrders) {

            if (i == 0) {
                deliveryOrderNumbers = deliveryOrder.getDeliveryOrderNumber();
            }
            else if (i <= deliveryOrders.size() && i > 0) {
                deliveryOrderNumbers = deliveryOrderNumbers + "," + deliveryOrder.getDeliveryOrderNumber();
            }

            i++;

            reduplicatedArticles.addAll(deliveryOrder.getArticles());
        }

        getMainFrame().setProgressPercentage(30);

        // Die Parameter, die an den <code>JasperViewer</code> übergeben werden und zum erstellen des
        // Reports nötig sind.
        getJasperViewer().addParameter("TITLE", bp.getAssuredTitle());
        getJasperViewer().addParameter("FIRSTNAME", person.getFirstname());
        getJasperViewer().addParameter("LASTNAME", person.getLastname());
        getJasperViewer().addParameter("STREET", address.getStreet());
        getJasperViewer().addParameter("ZIP_CODE", address.getZipCode());
        getJasperViewer().addParameter("CITY", address.getCity());
        getJasperViewer().addParameter("COUNTRY", address.getCountry() != null ? address.getCountry().toString() : null);
        getJasperViewer().addParameter("COUNTY", address.getCounty() != null ? address.getCounty().toString() : null);

        getJasperViewer().addParameter("COMPANY_NAME", bp.getCompanyName());
        getJasperViewer().addParameter("BRANCH", bp.getCompanyBranch());
        getJasperViewer().addParameter("FOR_ATTENTION_OF", bp.isForAttentionOf());
        getJasperViewer().addParameter("PERSON_TITLE", person.getTitle() != null ? person.getTitle().toString() : "");

        getJasperViewer().addParameter("DATE", bill.getBillDate());
        getJasperViewer().addParameter("CUSTOMER_NUMBER", bp.getCustomerNumber());
        getJasperViewer().addParameter("BILL_NUMBER", bill.getBillNumber());
        getJasperViewer().addParameter("DELIVERY_ORDER_NUMBERS", deliveryOrderNumbers);

        getJasperViewer().addParameter("PREFIX_FREE_TEXT", bill.getPrefixFreetext());
        getJasperViewer().addParameter("SUFFIX_FREE_TEXT", bill.getSuffixFreetext());

        getMainFrame().setProgressPercentage(50);

        getJasperViewer().view(getMainFrame(), WorkArea.getMessage(Constants.BILL_JRXML), reduplicatedArticles);

        getMainFrame().setProgressPercentage(100);

        getMainFrame().setProgressPercentage(0);
    }
}
