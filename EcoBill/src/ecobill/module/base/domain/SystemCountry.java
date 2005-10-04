package ecobill.module.base.domain;

import java.util.Set;

// @todo document me!

/**
 * SystemCountry.
 * <p/>
 * User: rro
 * Date: 28.09.2005
 * Time: 23:58:53
 *
 * @author Roman R&auml;dle
 * @version $Id: SystemCountry.java,v 1.2 2005/10/04 09:21:29 raedler Exp $
 * @since EcoBill 1.0
 */
public class SystemCountry extends AbstractI18NDomain {

    private Set<SystemCounty> systemCounties;

    private SystemLanguage systemLanguage;

    public Set<SystemCounty> getSystemCounties() {
        return systemCounties;
    }

    public void setSystemCounties(Set<SystemCounty> systemCounties) {
        this.systemCounties = systemCounties;
    }

    public SystemLanguage getSystemLanguage() {
        return systemLanguage;
    }

    public void setSystemLanguage(SystemLanguage systemLanguage) {
        this.systemLanguage = systemLanguage;
    }
}
