package ecobill.module.base.ui;

import org.springframework.beans.factory.InitializingBean;

import javax.swing.*;

import ecobill.module.base.service.BaseService;

// @todo document me!

/**
 * BusinessPartnerUI.
 * <p/>
 * User: rro
 * Date: 05.08.2005
 * Time: 14:20:07
 *
 * @author Roman R&auml;dle
 * @version $Id: BusinessPartnerUI.java,v 1.1 2005/08/05 12:22:25 raedler Exp $
 * @since EcoBill 1.0
 */
public class BusinessPartnerUI extends JPanel /*JInternalFrame*/ implements InitializingBean {

    /**
     * Der <code>BaseService</code> ist die Business Logik.
     */
    private BaseService baseService;

    public BusinessPartnerUI() {
        super();
    }

    public void afterPropertiesSet() throws Exception {
        //hier konstruktorsacha
    }

    /**
     * Gibt den <code>BaseService</code> und somit die Business Logik zurück.
     *
     * @return Der <code>BaseService</code>.
     */
    public BaseService getBaseService() {
        return baseService;
    }

    /**
     * Setzt den <code>BaseService</code> der die komplette Business Logik enthält
     * um bspw Daten aus der Datenbank zu laden und dorthin auch wieder abzulegen.
     *
     * @param baseService Der <code>BaseService</code>.
     */
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }
}
