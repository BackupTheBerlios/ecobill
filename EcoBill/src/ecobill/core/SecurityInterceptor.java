package ecobill.core;

import ecobill.module.base.service.BaseService;

// @todo document me!

/**
 * SecurityInterceptor.
 * <p/>
 * User: rro
 * Date: 04.08.2005
 * Time: 21:30:05
 *
 * @author Roman R&auml;dle
 * @version $Id: SecurityInterceptor.java,v 1.1 2005/08/04 20:24:18 raedler Exp $
 * @since EcoBill 1.0
 */
public class SecurityInterceptor {

    private BaseService baseService;

    public BaseService getBaseService() {
        return baseService;
    }

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }
}
