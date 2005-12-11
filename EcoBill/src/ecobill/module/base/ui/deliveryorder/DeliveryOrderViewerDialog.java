package ecobill.module.base.ui.deliveryorder;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;

import ecobill.module.base.service.BaseService;
import ecobill.module.base.domain.BusinessPartner;
import ecobill.module.base.domain.DeliveryOrder;
import ecobill.module.base.domain.Person;
import ecobill.module.base.ui.component.AbstractJasperPrintPanel;
import ecobill.module.base.ui.component.AbstractJasperViewerDialog;
import ecobill.core.ui.MainFrame;
import ecobill.core.system.WorkArea;
import ecobill.core.system.Constants;

import java.util.*;
import java.awt.*;

// @todo document me!

/**
 * DeliveryOrderViewerDialog.
 * <p/>
 * User: Paul Chef
 * Date: 18.08.2005
 * Time: 16:45:41
 *
 * @author Andreas Weiler
 * @version $Id: DeliveryOrderViewerDialog.java,v 1.1 2005/12/11 17:16:01 raedler Exp $
 * @since EcoBill 1.0
 */
public class DeliveryOrderViewerDialog extends AbstractJasperViewerDialog {

    /**
     * TODO: document me!!!
     */
    public DeliveryOrderViewerDialog(MainFrame mainFrame, boolean modal, BaseService baseService, Long id) {
        super(mainFrame, modal, baseService, id);
    }

    /**
     * @see ecobill.core.system.Internationalization#reinitI18N()
     */
    public void reinitI18N() {
        ((TitledBorder) getBorder()).setTitle(WorkArea.getMessage(Constants.DELIVERY_ORDER));
    }

    /**
     * @see ecobill.module.base.ui.component.AbstractJasperPrintPanel#createPanelBorder()
     */
    protected Border createPanelBorder() {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), WorkArea.getMessage(Constants.DELIVERY_ORDER), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 11), new Color(0, 0, 0));
    }

    /**
     * @see AbstractJasperPrintPanel#jasper(Long)
     */
    protected void jasper(Long id) throws Exception {

        getMainFrame().setProgressPercentage(10);

        DeliveryOrder deliveryOrder = (DeliveryOrder) getBaseService().load(DeliveryOrder.class, id);

        BusinessPartner bp = deliveryOrder.getBusinessPartner();
        Person person = bp.getPerson();
        ecobill.module.base.domain.Address address = bp.getAddress();

        Set reduplicatedArticles = deliveryOrder.getArticles();

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

        getJasperViewer().addParameter("DATE", deliveryOrder.getDeliveryOrderDate());
        getJasperViewer().addParameter("CUSTOMER_NUMBER", bp.getCustomerNumber());
        getJasperViewer().addParameter("DELIVERY_ORDER_NUMBER", deliveryOrder.getDeliveryOrderNumber());

        getJasperViewer().addParameter("PREFIX_FREE_TEXT", deliveryOrder.getPrefixFreetext());
        getJasperViewer().addParameter("SUFFIX_FREE_TEXT", deliveryOrder.getSuffixFreetext());

        getMainFrame().setProgressPercentage(50);

        if ("delivery_order".equals(deliveryOrder.getCharacterisationType())) {
            getJasperViewer().view(getMainFrame(), WorkArea.getMessage(Constants.DELIVERY_ORDER_JRXML), reduplicatedArticles);
        }
        else if ("part_delivery_order".equals(deliveryOrder.getCharacterisationType())) {
            getJasperViewer().view(getMainFrame(), WorkArea.getMessage(Constants.PART_DELIVERY_ORDER_JRXML), reduplicatedArticles);
        }

        getMainFrame().setProgressPercentage(100);

        getMainFrame().setProgressPercentage(0);
    }
}

